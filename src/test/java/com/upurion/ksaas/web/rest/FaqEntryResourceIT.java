package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.FaqEntry;
import com.upurion.ksaas.repository.FaqEntryRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.upurion.ksaas.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.upurion.ksaas.domain.enumeration.Language;
/**
 * Integration tests for the {@link FaqEntryResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class FaqEntryResourceIT {

    private static final Language DEFAULT_LANGUAGE = Language.FR;
    private static final Language UPDATED_LANGUAGE = Language.EN;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private FaqEntryRepository faqEntryRepository;

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

    private MockMvc restFaqEntryMockMvc;

    private FaqEntry faqEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaqEntryResource faqEntryResource = new FaqEntryResource(faqEntryRepository);
        this.restFaqEntryMockMvc = MockMvcBuilders.standaloneSetup(faqEntryResource)
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
    public static FaqEntry createEntity(EntityManager em) {
        FaqEntry faqEntry = new FaqEntry()
            .language(DEFAULT_LANGUAGE)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .date(DEFAULT_DATE);
        return faqEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FaqEntry createUpdatedEntity(EntityManager em) {
        FaqEntry faqEntry = new FaqEntry()
            .language(UPDATED_LANGUAGE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);
        return faqEntry;
    }

    @BeforeEach
    public void initTest() {
        faqEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaqEntry() throws Exception {
        int databaseSizeBeforeCreate = faqEntryRepository.findAll().size();

        // Create the FaqEntry
        restFaqEntryMockMvc.perform(post("/api/faq-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntry)))
            .andExpect(status().isCreated());

        // Validate the FaqEntry in the database
        List<FaqEntry> faqEntryList = faqEntryRepository.findAll();
        assertThat(faqEntryList).hasSize(databaseSizeBeforeCreate + 1);
        FaqEntry testFaqEntry = faqEntryList.get(faqEntryList.size() - 1);
        assertThat(testFaqEntry.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testFaqEntry.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFaqEntry.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testFaqEntry.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createFaqEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faqEntryRepository.findAll().size();

        // Create the FaqEntry with an existing ID
        faqEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaqEntryMockMvc.perform(post("/api/faq-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntry)))
            .andExpect(status().isBadRequest());

        // Validate the FaqEntry in the database
        List<FaqEntry> faqEntryList = faqEntryRepository.findAll();
        assertThat(faqEntryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = faqEntryRepository.findAll().size();
        // set the field null
        faqEntry.setTitle(null);

        // Create the FaqEntry, which fails.

        restFaqEntryMockMvc.perform(post("/api/faq-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntry)))
            .andExpect(status().isBadRequest());

        List<FaqEntry> faqEntryList = faqEntryRepository.findAll();
        assertThat(faqEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = faqEntryRepository.findAll().size();
        // set the field null
        faqEntry.setDate(null);

        // Create the FaqEntry, which fails.

        restFaqEntryMockMvc.perform(post("/api/faq-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntry)))
            .andExpect(status().isBadRequest());

        List<FaqEntry> faqEntryList = faqEntryRepository.findAll();
        assertThat(faqEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFaqEntries() throws Exception {
        // Initialize the database
        faqEntryRepository.saveAndFlush(faqEntry);

        // Get all the faqEntryList
        restFaqEntryMockMvc.perform(get("/api/faq-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faqEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getFaqEntry() throws Exception {
        // Initialize the database
        faqEntryRepository.saveAndFlush(faqEntry);

        // Get the faqEntry
        restFaqEntryMockMvc.perform(get("/api/faq-entries/{id}", faqEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faqEntry.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFaqEntry() throws Exception {
        // Get the faqEntry
        restFaqEntryMockMvc.perform(get("/api/faq-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaqEntry() throws Exception {
        // Initialize the database
        faqEntryRepository.saveAndFlush(faqEntry);

        int databaseSizeBeforeUpdate = faqEntryRepository.findAll().size();

        // Update the faqEntry
        FaqEntry updatedFaqEntry = faqEntryRepository.findById(faqEntry.getId()).get();
        // Disconnect from session so that the updates on updatedFaqEntry are not directly saved in db
        em.detach(updatedFaqEntry);
        updatedFaqEntry
            .language(UPDATED_LANGUAGE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);

        restFaqEntryMockMvc.perform(put("/api/faq-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaqEntry)))
            .andExpect(status().isOk());

        // Validate the FaqEntry in the database
        List<FaqEntry> faqEntryList = faqEntryRepository.findAll();
        assertThat(faqEntryList).hasSize(databaseSizeBeforeUpdate);
        FaqEntry testFaqEntry = faqEntryList.get(faqEntryList.size() - 1);
        assertThat(testFaqEntry.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testFaqEntry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFaqEntry.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testFaqEntry.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFaqEntry() throws Exception {
        int databaseSizeBeforeUpdate = faqEntryRepository.findAll().size();

        // Create the FaqEntry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaqEntryMockMvc.perform(put("/api/faq-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntry)))
            .andExpect(status().isBadRequest());

        // Validate the FaqEntry in the database
        List<FaqEntry> faqEntryList = faqEntryRepository.findAll();
        assertThat(faqEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaqEntry() throws Exception {
        // Initialize the database
        faqEntryRepository.saveAndFlush(faqEntry);

        int databaseSizeBeforeDelete = faqEntryRepository.findAll().size();

        // Delete the faqEntry
        restFaqEntryMockMvc.perform(delete("/api/faq-entries/{id}", faqEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FaqEntry> faqEntryList = faqEntryRepository.findAll();
        assertThat(faqEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
