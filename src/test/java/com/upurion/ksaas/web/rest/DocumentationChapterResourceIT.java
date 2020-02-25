package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.DocumentationChapter;
import com.upurion.ksaas.repository.DocumentationChapterRepository;
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
 * Integration tests for the {@link DocumentationChapterResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class DocumentationChapterResourceIT {

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private DocumentationChapterRepository documentationChapterRepository;

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

    private MockMvc restDocumentationChapterMockMvc;

    private DocumentationChapter documentationChapter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentationChapterResource documentationChapterResource = new DocumentationChapterResource(documentationChapterRepository);
        this.restDocumentationChapterMockMvc = MockMvcBuilders.standaloneSetup(documentationChapterResource)
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
    public static DocumentationChapter createEntity(EntityManager em) {
        DocumentationChapter documentationChapter = new DocumentationChapter()
            .info(DEFAULT_INFO);
        return documentationChapter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentationChapter createUpdatedEntity(EntityManager em) {
        DocumentationChapter documentationChapter = new DocumentationChapter()
            .info(UPDATED_INFO);
        return documentationChapter;
    }

    @BeforeEach
    public void initTest() {
        documentationChapter = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentationChapter() throws Exception {
        int databaseSizeBeforeCreate = documentationChapterRepository.findAll().size();

        // Create the DocumentationChapter
        restDocumentationChapterMockMvc.perform(post("/api/documentation-chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationChapter)))
            .andExpect(status().isCreated());

        // Validate the DocumentationChapter in the database
        List<DocumentationChapter> documentationChapterList = documentationChapterRepository.findAll();
        assertThat(documentationChapterList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentationChapter testDocumentationChapter = documentationChapterList.get(documentationChapterList.size() - 1);
        assertThat(testDocumentationChapter.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createDocumentationChapterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentationChapterRepository.findAll().size();

        // Create the DocumentationChapter with an existing ID
        documentationChapter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentationChapterMockMvc.perform(post("/api/documentation-chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationChapter)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationChapter in the database
        List<DocumentationChapter> documentationChapterList = documentationChapterRepository.findAll();
        assertThat(documentationChapterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentationChapterRepository.findAll().size();
        // set the field null
        documentationChapter.setInfo(null);

        // Create the DocumentationChapter, which fails.

        restDocumentationChapterMockMvc.perform(post("/api/documentation-chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationChapter)))
            .andExpect(status().isBadRequest());

        List<DocumentationChapter> documentationChapterList = documentationChapterRepository.findAll();
        assertThat(documentationChapterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentationChapters() throws Exception {
        // Initialize the database
        documentationChapterRepository.saveAndFlush(documentationChapter);

        // Get all the documentationChapterList
        restDocumentationChapterMockMvc.perform(get("/api/documentation-chapters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentationChapter.getId().intValue())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }
    
    @Test
    @Transactional
    public void getDocumentationChapter() throws Exception {
        // Initialize the database
        documentationChapterRepository.saveAndFlush(documentationChapter);

        // Get the documentationChapter
        restDocumentationChapterMockMvc.perform(get("/api/documentation-chapters/{id}", documentationChapter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentationChapter.getId().intValue()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentationChapter() throws Exception {
        // Get the documentationChapter
        restDocumentationChapterMockMvc.perform(get("/api/documentation-chapters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentationChapter() throws Exception {
        // Initialize the database
        documentationChapterRepository.saveAndFlush(documentationChapter);

        int databaseSizeBeforeUpdate = documentationChapterRepository.findAll().size();

        // Update the documentationChapter
        DocumentationChapter updatedDocumentationChapter = documentationChapterRepository.findById(documentationChapter.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentationChapter are not directly saved in db
        em.detach(updatedDocumentationChapter);
        updatedDocumentationChapter
            .info(UPDATED_INFO);

        restDocumentationChapterMockMvc.perform(put("/api/documentation-chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentationChapter)))
            .andExpect(status().isOk());

        // Validate the DocumentationChapter in the database
        List<DocumentationChapter> documentationChapterList = documentationChapterRepository.findAll();
        assertThat(documentationChapterList).hasSize(databaseSizeBeforeUpdate);
        DocumentationChapter testDocumentationChapter = documentationChapterList.get(documentationChapterList.size() - 1);
        assertThat(testDocumentationChapter.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentationChapter() throws Exception {
        int databaseSizeBeforeUpdate = documentationChapterRepository.findAll().size();

        // Create the DocumentationChapter

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentationChapterMockMvc.perform(put("/api/documentation-chapters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationChapter)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationChapter in the database
        List<DocumentationChapter> documentationChapterList = documentationChapterRepository.findAll();
        assertThat(documentationChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentationChapter() throws Exception {
        // Initialize the database
        documentationChapterRepository.saveAndFlush(documentationChapter);

        int databaseSizeBeforeDelete = documentationChapterRepository.findAll().size();

        // Delete the documentationChapter
        restDocumentationChapterMockMvc.perform(delete("/api/documentation-chapters/{id}", documentationChapter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentationChapter> documentationChapterList = documentationChapterRepository.findAll();
        assertThat(documentationChapterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
