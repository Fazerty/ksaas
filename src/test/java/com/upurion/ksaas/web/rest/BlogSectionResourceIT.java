package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.BlogSection;
import com.upurion.ksaas.repository.BlogSectionRepository;
import com.upurion.ksaas.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.upurion.ksaas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BlogSectionResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class BlogSectionResourceIT {

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private BlogSectionRepository blogSectionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBlogSectionMockMvc;

    private BlogSection blogSection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlogSectionResource blogSectionResource = new BlogSectionResource(blogSectionRepository);
        this.restBlogSectionMockMvc = MockMvcBuilders.standaloneSetup(blogSectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogSection createEntity(EntityManager em) {
        BlogSection blogSection = new BlogSection()
            .info(DEFAULT_INFO);
        return blogSection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogSection createUpdatedEntity(EntityManager em) {
        BlogSection blogSection = new BlogSection()
            .info(UPDATED_INFO);
        return blogSection;
    }

    @BeforeEach
    public void initTest() {
        blogSection = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlogSection() throws Exception {
        int databaseSizeBeforeCreate = blogSectionRepository.findAll().size();

        // Create the BlogSection
        restBlogSectionMockMvc.perform(post("/api/blog-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogSection)))
            .andExpect(status().isCreated());

        // Validate the BlogSection in the database
        List<BlogSection> blogSectionList = blogSectionRepository.findAll();
        assertThat(blogSectionList).hasSize(databaseSizeBeforeCreate + 1);
        BlogSection testBlogSection = blogSectionList.get(blogSectionList.size() - 1);
        assertThat(testBlogSection.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createBlogSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogSectionRepository.findAll().size();

        // Create the BlogSection with an existing ID
        blogSection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogSectionMockMvc.perform(post("/api/blog-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogSection)))
            .andExpect(status().isBadRequest());

        // Validate the BlogSection in the database
        List<BlogSection> blogSectionList = blogSectionRepository.findAll();
        assertThat(blogSectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = blogSectionRepository.findAll().size();
        // set the field null
        blogSection.setInfo(null);

        // Create the BlogSection, which fails.

        restBlogSectionMockMvc.perform(post("/api/blog-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogSection)))
            .andExpect(status().isBadRequest());

        List<BlogSection> blogSectionList = blogSectionRepository.findAll();
        assertThat(blogSectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBlogSections() throws Exception {
        // Initialize the database
        blogSectionRepository.saveAndFlush(blogSection);

        // Get all the blogSectionList
        restBlogSectionMockMvc.perform(get("/api/blog-sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogSection.getId().intValue())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }
    
    @Test
    @Transactional
    public void getBlogSection() throws Exception {
        // Initialize the database
        blogSectionRepository.saveAndFlush(blogSection);

        // Get the blogSection
        restBlogSectionMockMvc.perform(get("/api/blog-sections/{id}", blogSection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blogSection.getId().intValue()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    public void getNonExistingBlogSection() throws Exception {
        // Get the blogSection
        restBlogSectionMockMvc.perform(get("/api/blog-sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogSection() throws Exception {
        // Initialize the database
        blogSectionRepository.saveAndFlush(blogSection);

        int databaseSizeBeforeUpdate = blogSectionRepository.findAll().size();

        // Update the blogSection
        BlogSection updatedBlogSection = blogSectionRepository.findById(blogSection.getId()).get();
        // Disconnect from session so that the updates on updatedBlogSection are not directly saved in db
        em.detach(updatedBlogSection);
        updatedBlogSection
            .info(UPDATED_INFO);

        restBlogSectionMockMvc.perform(put("/api/blog-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlogSection)))
            .andExpect(status().isOk());

        // Validate the BlogSection in the database
        List<BlogSection> blogSectionList = blogSectionRepository.findAll();
        assertThat(blogSectionList).hasSize(databaseSizeBeforeUpdate);
        BlogSection testBlogSection = blogSectionList.get(blogSectionList.size() - 1);
        assertThat(testBlogSection.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingBlogSection() throws Exception {
        int databaseSizeBeforeUpdate = blogSectionRepository.findAll().size();

        // Create the BlogSection

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogSectionMockMvc.perform(put("/api/blog-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogSection)))
            .andExpect(status().isBadRequest());

        // Validate the BlogSection in the database
        List<BlogSection> blogSectionList = blogSectionRepository.findAll();
        assertThat(blogSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlogSection() throws Exception {
        // Initialize the database
        blogSectionRepository.saveAndFlush(blogSection);

        int databaseSizeBeforeDelete = blogSectionRepository.findAll().size();

        // Delete the blogSection
        restBlogSectionMockMvc.perform(delete("/api/blog-sections/{id}", blogSection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogSection> blogSectionList = blogSectionRepository.findAll();
        assertThat(blogSectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
