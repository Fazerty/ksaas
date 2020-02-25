package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.DocumentationEntry;
import com.upurion.ksaas.repository.DocumentationEntryRepository;
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
 * Integration tests for the {@link DocumentationEntryResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class DocumentationEntryResourceIT {

    private static final Language DEFAULT_LANGUAGE = Language.FR;
    private static final Language UPDATED_LANGUAGE = Language.EN;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DocumentationEntryRepository documentationEntryRepository;

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

    private MockMvc restDocumentationEntryMockMvc;

    private DocumentationEntry documentationEntry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentationEntryResource documentationEntryResource = new DocumentationEntryResource(documentationEntryRepository);
        this.restDocumentationEntryMockMvc = MockMvcBuilders.standaloneSetup(documentationEntryResource)
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
    public static DocumentationEntry createEntity(EntityManager em) {
        DocumentationEntry documentationEntry = new DocumentationEntry()
            .language(DEFAULT_LANGUAGE)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .date(DEFAULT_DATE);
        return documentationEntry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentationEntry createUpdatedEntity(EntityManager em) {
        DocumentationEntry documentationEntry = new DocumentationEntry()
            .language(UPDATED_LANGUAGE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);
        return documentationEntry;
    }

    @BeforeEach
    public void initTest() {
        documentationEntry = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentationEntry() throws Exception {
        int databaseSizeBeforeCreate = documentationEntryRepository.findAll().size();

        // Create the DocumentationEntry
        restDocumentationEntryMockMvc.perform(post("/api/documentation-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntry)))
            .andExpect(status().isCreated());

        // Validate the DocumentationEntry in the database
        List<DocumentationEntry> documentationEntryList = documentationEntryRepository.findAll();
        assertThat(documentationEntryList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentationEntry testDocumentationEntry = documentationEntryList.get(documentationEntryList.size() - 1);
        assertThat(testDocumentationEntry.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testDocumentationEntry.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testDocumentationEntry.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testDocumentationEntry.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDocumentationEntryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentationEntryRepository.findAll().size();

        // Create the DocumentationEntry with an existing ID
        documentationEntry.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentationEntryMockMvc.perform(post("/api/documentation-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntry)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationEntry in the database
        List<DocumentationEntry> documentationEntryList = documentationEntryRepository.findAll();
        assertThat(documentationEntryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentationEntryRepository.findAll().size();
        // set the field null
        documentationEntry.setTitle(null);

        // Create the DocumentationEntry, which fails.

        restDocumentationEntryMockMvc.perform(post("/api/documentation-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntry)))
            .andExpect(status().isBadRequest());

        List<DocumentationEntry> documentationEntryList = documentationEntryRepository.findAll();
        assertThat(documentationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentationEntryRepository.findAll().size();
        // set the field null
        documentationEntry.setDate(null);

        // Create the DocumentationEntry, which fails.

        restDocumentationEntryMockMvc.perform(post("/api/documentation-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntry)))
            .andExpect(status().isBadRequest());

        List<DocumentationEntry> documentationEntryList = documentationEntryRepository.findAll();
        assertThat(documentationEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentationEntries() throws Exception {
        // Initialize the database
        documentationEntryRepository.saveAndFlush(documentationEntry);

        // Get all the documentationEntryList
        restDocumentationEntryMockMvc.perform(get("/api/documentation-entries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentationEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDocumentationEntry() throws Exception {
        // Initialize the database
        documentationEntryRepository.saveAndFlush(documentationEntry);

        // Get the documentationEntry
        restDocumentationEntryMockMvc.perform(get("/api/documentation-entries/{id}", documentationEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentationEntry.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentationEntry() throws Exception {
        // Get the documentationEntry
        restDocumentationEntryMockMvc.perform(get("/api/documentation-entries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentationEntry() throws Exception {
        // Initialize the database
        documentationEntryRepository.saveAndFlush(documentationEntry);

        int databaseSizeBeforeUpdate = documentationEntryRepository.findAll().size();

        // Update the documentationEntry
        DocumentationEntry updatedDocumentationEntry = documentationEntryRepository.findById(documentationEntry.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentationEntry are not directly saved in db
        em.detach(updatedDocumentationEntry);
        updatedDocumentationEntry
            .language(UPDATED_LANGUAGE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .date(UPDATED_DATE);

        restDocumentationEntryMockMvc.perform(put("/api/documentation-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentationEntry)))
            .andExpect(status().isOk());

        // Validate the DocumentationEntry in the database
        List<DocumentationEntry> documentationEntryList = documentationEntryRepository.findAll();
        assertThat(documentationEntryList).hasSize(databaseSizeBeforeUpdate);
        DocumentationEntry testDocumentationEntry = documentationEntryList.get(documentationEntryList.size() - 1);
        assertThat(testDocumentationEntry.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testDocumentationEntry.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testDocumentationEntry.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testDocumentationEntry.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentationEntry() throws Exception {
        int databaseSizeBeforeUpdate = documentationEntryRepository.findAll().size();

        // Create the DocumentationEntry

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentationEntryMockMvc.perform(put("/api/documentation-entries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntry)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationEntry in the database
        List<DocumentationEntry> documentationEntryList = documentationEntryRepository.findAll();
        assertThat(documentationEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentationEntry() throws Exception {
        // Initialize the database
        documentationEntryRepository.saveAndFlush(documentationEntry);

        int databaseSizeBeforeDelete = documentationEntryRepository.findAll().size();

        // Delete the documentationEntry
        restDocumentationEntryMockMvc.perform(delete("/api/documentation-entries/{id}", documentationEntry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentationEntry> documentationEntryList = documentationEntryRepository.findAll();
        assertThat(documentationEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
