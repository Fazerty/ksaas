package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.DocumentationSection;
import com.upurion.ksaas.repository.DocumentationSectionRepository;
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
 * Integration tests for the {@link DocumentationSectionResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class DocumentationSectionResourceIT {

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private DocumentationSectionRepository documentationSectionRepository;

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

    private MockMvc restDocumentationSectionMockMvc;

    private DocumentationSection documentationSection;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentationSectionResource documentationSectionResource = new DocumentationSectionResource(documentationSectionRepository);
        this.restDocumentationSectionMockMvc = MockMvcBuilders.standaloneSetup(documentationSectionResource)
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
    public static DocumentationSection createEntity(EntityManager em) {
        DocumentationSection documentationSection = new DocumentationSection()
            .info(DEFAULT_INFO);
        return documentationSection;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentationSection createUpdatedEntity(EntityManager em) {
        DocumentationSection documentationSection = new DocumentationSection()
            .info(UPDATED_INFO);
        return documentationSection;
    }

    @BeforeEach
    public void initTest() {
        documentationSection = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentationSection() throws Exception {
        int databaseSizeBeforeCreate = documentationSectionRepository.findAll().size();

        // Create the DocumentationSection
        restDocumentationSectionMockMvc.perform(post("/api/documentation-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationSection)))
            .andExpect(status().isCreated());

        // Validate the DocumentationSection in the database
        List<DocumentationSection> documentationSectionList = documentationSectionRepository.findAll();
        assertThat(documentationSectionList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentationSection testDocumentationSection = documentationSectionList.get(documentationSectionList.size() - 1);
        assertThat(testDocumentationSection.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createDocumentationSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentationSectionRepository.findAll().size();

        // Create the DocumentationSection with an existing ID
        documentationSection.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentationSectionMockMvc.perform(post("/api/documentation-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationSection)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationSection in the database
        List<DocumentationSection> documentationSectionList = documentationSectionRepository.findAll();
        assertThat(documentationSectionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkInfoIsRequired() throws Exception {
        int databaseSizeBeforeTest = documentationSectionRepository.findAll().size();
        // set the field null
        documentationSection.setInfo(null);

        // Create the DocumentationSection, which fails.

        restDocumentationSectionMockMvc.perform(post("/api/documentation-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationSection)))
            .andExpect(status().isBadRequest());

        List<DocumentationSection> documentationSectionList = documentationSectionRepository.findAll();
        assertThat(documentationSectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDocumentationSections() throws Exception {
        // Initialize the database
        documentationSectionRepository.saveAndFlush(documentationSection);

        // Get all the documentationSectionList
        restDocumentationSectionMockMvc.perform(get("/api/documentation-sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentationSection.getId().intValue())))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
    }
    
    @Test
    @Transactional
    public void getDocumentationSection() throws Exception {
        // Initialize the database
        documentationSectionRepository.saveAndFlush(documentationSection);

        // Get the documentationSection
        restDocumentationSectionMockMvc.perform(get("/api/documentation-sections/{id}", documentationSection.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentationSection.getId().intValue()))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentationSection() throws Exception {
        // Get the documentationSection
        restDocumentationSectionMockMvc.perform(get("/api/documentation-sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentationSection() throws Exception {
        // Initialize the database
        documentationSectionRepository.saveAndFlush(documentationSection);

        int databaseSizeBeforeUpdate = documentationSectionRepository.findAll().size();

        // Update the documentationSection
        DocumentationSection updatedDocumentationSection = documentationSectionRepository.findById(documentationSection.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentationSection are not directly saved in db
        em.detach(updatedDocumentationSection);
        updatedDocumentationSection
            .info(UPDATED_INFO);

        restDocumentationSectionMockMvc.perform(put("/api/documentation-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentationSection)))
            .andExpect(status().isOk());

        // Validate the DocumentationSection in the database
        List<DocumentationSection> documentationSectionList = documentationSectionRepository.findAll();
        assertThat(documentationSectionList).hasSize(databaseSizeBeforeUpdate);
        DocumentationSection testDocumentationSection = documentationSectionList.get(documentationSectionList.size() - 1);
        assertThat(testDocumentationSection.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentationSection() throws Exception {
        int databaseSizeBeforeUpdate = documentationSectionRepository.findAll().size();

        // Create the DocumentationSection

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentationSectionMockMvc.perform(put("/api/documentation-sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationSection)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationSection in the database
        List<DocumentationSection> documentationSectionList = documentationSectionRepository.findAll();
        assertThat(documentationSectionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentationSection() throws Exception {
        // Initialize the database
        documentationSectionRepository.saveAndFlush(documentationSection);

        int databaseSizeBeforeDelete = documentationSectionRepository.findAll().size();

        // Delete the documentationSection
        restDocumentationSectionMockMvc.perform(delete("/api/documentation-sections/{id}", documentationSection.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentationSection> documentationSectionList = documentationSectionRepository.findAll();
        assertThat(documentationSectionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
