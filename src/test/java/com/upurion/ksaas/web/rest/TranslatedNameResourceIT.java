package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.TranslatedName;
import com.upurion.ksaas.repository.TranslatedNameRepository;
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

import com.upurion.ksaas.domain.enumeration.Language;
/**
 * Integration tests for the {@link TranslatedNameResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class TranslatedNameResourceIT {

    private static final Language DEFAULT_LANGUAGE = Language.FR;
    private static final Language UPDATED_LANGUAGE = Language.EN;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SLUG = "AAAAAAAAAA";
    private static final String UPDATED_SLUG = "BBBBBBBBBB";

    @Autowired
    private TranslatedNameRepository translatedNameRepository;

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

    private MockMvc restTranslatedNameMockMvc;

    private TranslatedName translatedName;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TranslatedNameResource translatedNameResource = new TranslatedNameResource(translatedNameRepository);
        this.restTranslatedNameMockMvc = MockMvcBuilders.standaloneSetup(translatedNameResource)
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
    public static TranslatedName createEntity(EntityManager em) {
        TranslatedName translatedName = new TranslatedName()
            .language(DEFAULT_LANGUAGE)
            .name(DEFAULT_NAME)
            .slug(DEFAULT_SLUG);
        return translatedName;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TranslatedName createUpdatedEntity(EntityManager em) {
        TranslatedName translatedName = new TranslatedName()
            .language(UPDATED_LANGUAGE)
            .name(UPDATED_NAME)
            .slug(UPDATED_SLUG);
        return translatedName;
    }

    @BeforeEach
    public void initTest() {
        translatedName = createEntity(em);
    }

    @Test
    @Transactional
    public void createTranslatedName() throws Exception {
        int databaseSizeBeforeCreate = translatedNameRepository.findAll().size();

        // Create the TranslatedName
        restTranslatedNameMockMvc.perform(post("/api/translated-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translatedName)))
            .andExpect(status().isCreated());

        // Validate the TranslatedName in the database
        List<TranslatedName> translatedNameList = translatedNameRepository.findAll();
        assertThat(translatedNameList).hasSize(databaseSizeBeforeCreate + 1);
        TranslatedName testTranslatedName = translatedNameList.get(translatedNameList.size() - 1);
        assertThat(testTranslatedName.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testTranslatedName.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTranslatedName.getSlug()).isEqualTo(DEFAULT_SLUG);
    }

    @Test
    @Transactional
    public void createTranslatedNameWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = translatedNameRepository.findAll().size();

        // Create the TranslatedName with an existing ID
        translatedName.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranslatedNameMockMvc.perform(post("/api/translated-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translatedName)))
            .andExpect(status().isBadRequest());

        // Validate the TranslatedName in the database
        List<TranslatedName> translatedNameList = translatedNameRepository.findAll();
        assertThat(translatedNameList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = translatedNameRepository.findAll().size();
        // set the field null
        translatedName.setName(null);

        // Create the TranslatedName, which fails.

        restTranslatedNameMockMvc.perform(post("/api/translated-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translatedName)))
            .andExpect(status().isBadRequest());

        List<TranslatedName> translatedNameList = translatedNameRepository.findAll();
        assertThat(translatedNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSlugIsRequired() throws Exception {
        int databaseSizeBeforeTest = translatedNameRepository.findAll().size();
        // set the field null
        translatedName.setSlug(null);

        // Create the TranslatedName, which fails.

        restTranslatedNameMockMvc.perform(post("/api/translated-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translatedName)))
            .andExpect(status().isBadRequest());

        List<TranslatedName> translatedNameList = translatedNameRepository.findAll();
        assertThat(translatedNameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTranslatedNames() throws Exception {
        // Initialize the database
        translatedNameRepository.saveAndFlush(translatedName);

        // Get all the translatedNameList
        restTranslatedNameMockMvc.perform(get("/api/translated-names?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(translatedName.getId().intValue())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].slug").value(hasItem(DEFAULT_SLUG)));
    }
    
    @Test
    @Transactional
    public void getTranslatedName() throws Exception {
        // Initialize the database
        translatedNameRepository.saveAndFlush(translatedName);

        // Get the translatedName
        restTranslatedNameMockMvc.perform(get("/api/translated-names/{id}", translatedName.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(translatedName.getId().intValue()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.slug").value(DEFAULT_SLUG));
    }

    @Test
    @Transactional
    public void getNonExistingTranslatedName() throws Exception {
        // Get the translatedName
        restTranslatedNameMockMvc.perform(get("/api/translated-names/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTranslatedName() throws Exception {
        // Initialize the database
        translatedNameRepository.saveAndFlush(translatedName);

        int databaseSizeBeforeUpdate = translatedNameRepository.findAll().size();

        // Update the translatedName
        TranslatedName updatedTranslatedName = translatedNameRepository.findById(translatedName.getId()).get();
        // Disconnect from session so that the updates on updatedTranslatedName are not directly saved in db
        em.detach(updatedTranslatedName);
        updatedTranslatedName
            .language(UPDATED_LANGUAGE)
            .name(UPDATED_NAME)
            .slug(UPDATED_SLUG);

        restTranslatedNameMockMvc.perform(put("/api/translated-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTranslatedName)))
            .andExpect(status().isOk());

        // Validate the TranslatedName in the database
        List<TranslatedName> translatedNameList = translatedNameRepository.findAll();
        assertThat(translatedNameList).hasSize(databaseSizeBeforeUpdate);
        TranslatedName testTranslatedName = translatedNameList.get(translatedNameList.size() - 1);
        assertThat(testTranslatedName.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testTranslatedName.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTranslatedName.getSlug()).isEqualTo(UPDATED_SLUG);
    }

    @Test
    @Transactional
    public void updateNonExistingTranslatedName() throws Exception {
        int databaseSizeBeforeUpdate = translatedNameRepository.findAll().size();

        // Create the TranslatedName

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranslatedNameMockMvc.perform(put("/api/translated-names")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(translatedName)))
            .andExpect(status().isBadRequest());

        // Validate the TranslatedName in the database
        List<TranslatedName> translatedNameList = translatedNameRepository.findAll();
        assertThat(translatedNameList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTranslatedName() throws Exception {
        // Initialize the database
        translatedNameRepository.saveAndFlush(translatedName);

        int databaseSizeBeforeDelete = translatedNameRepository.findAll().size();

        // Delete the translatedName
        restTranslatedNameMockMvc.perform(delete("/api/translated-names/{id}", translatedName.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TranslatedName> translatedNameList = translatedNameRepository.findAll();
        assertThat(translatedNameList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
