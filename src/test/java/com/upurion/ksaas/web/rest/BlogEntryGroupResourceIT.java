package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.BlogEntryGroup;
import com.upurion.ksaas.repository.BlogEntryGroupRepository;
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
 * Integration tests for the {@link BlogEntryGroupResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class BlogEntryGroupResourceIT {

    @Autowired
    private BlogEntryGroupRepository blogEntryGroupRepository;

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

    private MockMvc restBlogEntryGroupMockMvc;

    private BlogEntryGroup blogEntryGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BlogEntryGroupResource blogEntryGroupResource = new BlogEntryGroupResource(blogEntryGroupRepository);
        this.restBlogEntryGroupMockMvc = MockMvcBuilders.standaloneSetup(blogEntryGroupResource)
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
    public static BlogEntryGroup createEntity(EntityManager em) {
        BlogEntryGroup blogEntryGroup = new BlogEntryGroup();
        return blogEntryGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BlogEntryGroup createUpdatedEntity(EntityManager em) {
        BlogEntryGroup blogEntryGroup = new BlogEntryGroup();
        return blogEntryGroup;
    }

    @BeforeEach
    public void initTest() {
        blogEntryGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createBlogEntryGroup() throws Exception {
        int databaseSizeBeforeCreate = blogEntryGroupRepository.findAll().size();

        // Create the BlogEntryGroup
        restBlogEntryGroupMockMvc.perform(post("/api/blog-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogEntryGroup)))
            .andExpect(status().isCreated());

        // Validate the BlogEntryGroup in the database
        List<BlogEntryGroup> blogEntryGroupList = blogEntryGroupRepository.findAll();
        assertThat(blogEntryGroupList).hasSize(databaseSizeBeforeCreate + 1);
        BlogEntryGroup testBlogEntryGroup = blogEntryGroupList.get(blogEntryGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void createBlogEntryGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = blogEntryGroupRepository.findAll().size();

        // Create the BlogEntryGroup with an existing ID
        blogEntryGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBlogEntryGroupMockMvc.perform(post("/api/blog-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogEntryGroup)))
            .andExpect(status().isBadRequest());

        // Validate the BlogEntryGroup in the database
        List<BlogEntryGroup> blogEntryGroupList = blogEntryGroupRepository.findAll();
        assertThat(blogEntryGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBlogEntryGroups() throws Exception {
        // Initialize the database
        blogEntryGroupRepository.saveAndFlush(blogEntryGroup);

        // Get all the blogEntryGroupList
        restBlogEntryGroupMockMvc.perform(get("/api/blog-entry-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(blogEntryGroup.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getBlogEntryGroup() throws Exception {
        // Initialize the database
        blogEntryGroupRepository.saveAndFlush(blogEntryGroup);

        // Get the blogEntryGroup
        restBlogEntryGroupMockMvc.perform(get("/api/blog-entry-groups/{id}", blogEntryGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(blogEntryGroup.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBlogEntryGroup() throws Exception {
        // Get the blogEntryGroup
        restBlogEntryGroupMockMvc.perform(get("/api/blog-entry-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBlogEntryGroup() throws Exception {
        // Initialize the database
        blogEntryGroupRepository.saveAndFlush(blogEntryGroup);

        int databaseSizeBeforeUpdate = blogEntryGroupRepository.findAll().size();

        // Update the blogEntryGroup
        BlogEntryGroup updatedBlogEntryGroup = blogEntryGroupRepository.findById(blogEntryGroup.getId()).get();
        // Disconnect from session so that the updates on updatedBlogEntryGroup are not directly saved in db
        em.detach(updatedBlogEntryGroup);

        restBlogEntryGroupMockMvc.perform(put("/api/blog-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBlogEntryGroup)))
            .andExpect(status().isOk());

        // Validate the BlogEntryGroup in the database
        List<BlogEntryGroup> blogEntryGroupList = blogEntryGroupRepository.findAll();
        assertThat(blogEntryGroupList).hasSize(databaseSizeBeforeUpdate);
        BlogEntryGroup testBlogEntryGroup = blogEntryGroupList.get(blogEntryGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBlogEntryGroup() throws Exception {
        int databaseSizeBeforeUpdate = blogEntryGroupRepository.findAll().size();

        // Create the BlogEntryGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBlogEntryGroupMockMvc.perform(put("/api/blog-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(blogEntryGroup)))
            .andExpect(status().isBadRequest());

        // Validate the BlogEntryGroup in the database
        List<BlogEntryGroup> blogEntryGroupList = blogEntryGroupRepository.findAll();
        assertThat(blogEntryGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBlogEntryGroup() throws Exception {
        // Initialize the database
        blogEntryGroupRepository.saveAndFlush(blogEntryGroup);

        int databaseSizeBeforeDelete = blogEntryGroupRepository.findAll().size();

        // Delete the blogEntryGroup
        restBlogEntryGroupMockMvc.perform(delete("/api/blog-entry-groups/{id}", blogEntryGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BlogEntryGroup> blogEntryGroupList = blogEntryGroupRepository.findAll();
        assertThat(blogEntryGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
