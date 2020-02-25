package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.FaqSection;
import com.upurion.ksaas.repository.FaqSectionRepository;
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
 * Integration tests for the {@link FaqSectionResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class FaqSectionResourceIT {

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private FaqSectionRepository faqSectionRepository;

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

    private MockMvc restFaqSectionMockMvc;

    private FaqSection faqSection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaqSectionResource faqSectionResource = new FaqSectionResource(faqSectionRepository);
        this.restFaqSectionMockMvc = MockMvcBuilders.standaloneSetup(faqSectionResource)
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
    public static FaqSection createEntity(EntityManager em) {
        FaqSection faqSection = new FaqSection()
            .info(DEFAULT_INFO);
        return faqSection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FaqSection createUpdatedEntity(EntityManager em) {
        FaqSection faqSection = new FaqSection()
            .info(UPDATED_INFO);
        return faqSection;
    }

    @BeforeEach
    public void initTest() {
        faqSection = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaqSection() throws Exception {
        int databaseSizeBeforeCreate = faqSectionRepository.findAll().size();

        // Create the FaqSection
        restFaqSectionMockMvc.perform(post("/api/faq-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqSection)))
            .andExpect(status().isCreated());

        // Validate the FaqSection in the database
        List<FaqSection> faqSectionList = faqSectionRepository.findAll();
        assertThat(faqSectionList).hasSize(databaseSizeBeforeCreate + 1);
        FaqSection testFaqSection = faqSectionList.get(faqSectionList.size() - 1);
        assertThat(testFaqSection.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createFaqSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faqSectionRepository.findAll().size();

        // Create the FaqSection with an existing ID
        faqSection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaqSectionMockMvc.perform(post("/api/faq-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqSection)))
            .andExpect(status().isBadRequest());

        // Validate the FaqSection in the database
        List<FaqSection> faqSectionList = faqSectionRepository.findAll();
        assertThat(faqSectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = faqSectionRepository.findAll().size();
        // set the field null
        faqSection.setInfo(null);

        // Create the FaqSection, which fails.

        restFaqSectionMockMvc.perform(post("/api/faq-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqSection)))
            .andExpect(status().isBadRequest());

        List<FaqSection> faqSectionList = faqSectionRepository.findAll();
        assertThat(faqSectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaqSections() throws Exception {
        // Initialize the database
        faqSectionRepository.saveAndFlush(faqSection);

        // Get all the faqSectionList
        restFaqSectionMockMvc.perform(get("/api/faq-sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faqSection.getId().intValue())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }
    
    @Test
    @Transactional
    public void getFaqSection() throws Exception {
        // Initialize the database
        faqSectionRepository.saveAndFlush(faqSection);

        // Get the faqSection
        restFaqSectionMockMvc.perform(get("/api/faq-sections/{id}", faqSection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faqSection.getId().intValue()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    public void getNonExistingFaqSection() throws Exception {
        // Get the faqSection
        restFaqSectionMockMvc.perform(get("/api/faq-sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaqSection() throws Exception {
        // Initialize the database
        faqSectionRepository.saveAndFlush(faqSection);

        int databaseSizeBeforeUpdate = faqSectionRepository.findAll().size();

        // Update the faqSection
        FaqSection updatedFaqSection = faqSectionRepository.findById(faqSection.getId()).get();
        // Disconnect from session so that the updates on updatedFaqSection are not directly saved in db
        em.detach(updatedFaqSection);
        updatedFaqSection
            .info(UPDATED_INFO);

        restFaqSectionMockMvc.perform(put("/api/faq-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaqSection)))
            .andExpect(status().isOk());

        // Validate the FaqSection in the database
        List<FaqSection> faqSectionList = faqSectionRepository.findAll();
        assertThat(faqSectionList).hasSize(databaseSizeBeforeUpdate);
        FaqSection testFaqSection = faqSectionList.get(faqSectionList.size() - 1);
        assertThat(testFaqSection.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingFaqSection() throws Exception {
        int databaseSizeBeforeUpdate = faqSectionRepository.findAll().size();

        // Create the FaqSection

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaqSectionMockMvc.perform(put("/api/faq-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqSection)))
            .andExpect(status().isBadRequest());

        // Validate the FaqSection in the database
        List<FaqSection> faqSectionList = faqSectionRepository.findAll();
        assertThat(faqSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaqSection() throws Exception {
        // Initialize the database
        faqSectionRepository.saveAndFlush(faqSection);

        int databaseSizeBeforeDelete = faqSectionRepository.findAll().size();

        // Delete the faqSection
        restFaqSectionMockMvc.perform(delete("/api/faq-sections/{id}", faqSection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FaqSection> faqSectionList = faqSectionRepository.findAll();
        assertThat(faqSectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
