package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.DocumentationEntryGroup;
import com.upurion.ksaas.repository.DocumentationEntryGroupRepository;
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
 * Integration tests for the {@link DocumentationEntryGroupResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class DocumentationEntryGroupResourceIT {

    @Autowired
    private DocumentationEntryGroupRepository documentationEntryGroupRepository;

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

    private MockMvc restDocumentationEntryGroupMockMvc;

    private DocumentationEntryGroup documentationEntryGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocumentationEntryGroupResource documentationEntryGroupResource = new DocumentationEntryGroupResource(documentationEntryGroupRepository);
        this.restDocumentationEntryGroupMockMvc = MockMvcBuilders.standaloneSetup(documentationEntryGroupResource)
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
    public static DocumentationEntryGroup createEntity(EntityManager em) {
        DocumentationEntryGroup documentationEntryGroup = new DocumentationEntryGroup();
        return documentationEntryGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumentationEntryGroup createUpdatedEntity(EntityManager em) {
        DocumentationEntryGroup documentationEntryGroup = new DocumentationEntryGroup();
        return documentationEntryGroup;
    }

    @BeforeEach
    public void initTest() {
        documentationEntryGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocumentationEntryGroup() throws Exception {
        int databaseSizeBeforeCreate = documentationEntryGroupRepository.findAll().size();

        // Create the DocumentationEntryGroup
        restDocumentationEntryGroupMockMvc.perform(post("/api/documentation-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntryGroup)))
            .andExpect(status().isCreated());

        // Validate the DocumentationEntryGroup in the database
        List<DocumentationEntryGroup> documentationEntryGroupList = documentationEntryGroupRepository.findAll();
        assertThat(documentationEntryGroupList).hasSize(databaseSizeBeforeCreate + 1);
        DocumentationEntryGroup testDocumentationEntryGroup = documentationEntryGroupList.get(documentationEntryGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void createDocumentationEntryGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = documentationEntryGroupRepository.findAll().size();

        // Create the DocumentationEntryGroup with an existing ID
        documentationEntryGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumentationEntryGroupMockMvc.perform(post("/api/documentation-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntryGroup)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationEntryGroup in the database
        List<DocumentationEntryGroup> documentationEntryGroupList = documentationEntryGroupRepository.findAll();
        assertThat(documentationEntryGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDocumentationEntryGroups() throws Exception {
        // Initialize the database
        documentationEntryGroupRepository.saveAndFlush(documentationEntryGroup);

        // Get all the documentationEntryGroupList
        restDocumentationEntryGroupMockMvc.perform(get("/api/documentation-entry-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documentationEntryGroup.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getDocumentationEntryGroup() throws Exception {
        // Initialize the database
        documentationEntryGroupRepository.saveAndFlush(documentationEntryGroup);

        // Get the documentationEntryGroup
        restDocumentationEntryGroupMockMvc.perform(get("/api/documentation-entry-groups/{id}", documentationEntryGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(documentationEntryGroup.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocumentationEntryGroup() throws Exception {
        // Get the documentationEntryGroup
        restDocumentationEntryGroupMockMvc.perform(get("/api/documentation-entry-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocumentationEntryGroup() throws Exception {
        // Initialize the database
        documentationEntryGroupRepository.saveAndFlush(documentationEntryGroup);

        int databaseSizeBeforeUpdate = documentationEntryGroupRepository.findAll().size();

        // Update the documentationEntryGroup
        DocumentationEntryGroup updatedDocumentationEntryGroup = documentationEntryGroupRepository.findById(documentationEntryGroup.getId()).get();
        // Disconnect from session so that the updates on updatedDocumentationEntryGroup are not directly saved in db
        em.detach(updatedDocumentationEntryGroup);

        restDocumentationEntryGroupMockMvc.perform(put("/api/documentation-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocumentationEntryGroup)))
            .andExpect(status().isOk());

        // Validate the DocumentationEntryGroup in the database
        List<DocumentationEntryGroup> documentationEntryGroupList = documentationEntryGroupRepository.findAll();
        assertThat(documentationEntryGroupList).hasSize(databaseSizeBeforeUpdate);
        DocumentationEntryGroup testDocumentationEntryGroup = documentationEntryGroupList.get(documentationEntryGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingDocumentationEntryGroup() throws Exception {
        int databaseSizeBeforeUpdate = documentationEntryGroupRepository.findAll().size();

        // Create the DocumentationEntryGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumentationEntryGroupMockMvc.perform(put("/api/documentation-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(documentationEntryGroup)))
            .andExpect(status().isBadRequest());

        // Validate the DocumentationEntryGroup in the database
        List<DocumentationEntryGroup> documentationEntryGroupList = documentationEntryGroupRepository.findAll();
        assertThat(documentationEntryGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocumentationEntryGroup() throws Exception {
        // Initialize the database
        documentationEntryGroupRepository.saveAndFlush(documentationEntryGroup);

        int databaseSizeBeforeDelete = documentationEntryGroupRepository.findAll().size();

        // Delete the documentationEntryGroup
        restDocumentationEntryGroupMockMvc.perform(delete("/api/documentation-entry-groups/{id}", documentationEntryGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumentationEntryGroup> documentationEntryGroupList = documentationEntryGroupRepository.findAll();
        assertThat(documentationEntryGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
