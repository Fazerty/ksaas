package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.KsaasApp;
import com.upurion.ksaas.domain.FaqEntryGroup;
import com.upurion.ksaas.repository.FaqEntryGroupRepository;
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
 * Integration tests for the {@link FaqEntryGroupResource} REST controller.
 */
@SpringBootTest(classes = KsaasApp.class)
public class FaqEntryGroupResourceIT {

    @Autowired
    private FaqEntryGroupRepository faqEntryGroupRepository;

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

    private MockMvc restFaqEntryGroupMockMvc;

    private FaqEntryGroup faqEntryGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FaqEntryGroupResource faqEntryGroupResource = new FaqEntryGroupResource(faqEntryGroupRepository);
        this.restFaqEntryGroupMockMvc = MockMvcBuilders.standaloneSetup(faqEntryGroupResource)
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
    public static FaqEntryGroup createEntity(EntityManager em) {
        FaqEntryGroup faqEntryGroup = new FaqEntryGroup();
        return faqEntryGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FaqEntryGroup createUpdatedEntity(EntityManager em) {
        FaqEntryGroup faqEntryGroup = new FaqEntryGroup();
        return faqEntryGroup;
    }

    @BeforeEach
    public void initTest() {
        faqEntryGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createFaqEntryGroup() throws Exception {
        int databaseSizeBeforeCreate = faqEntryGroupRepository.findAll().size();

        // Create the FaqEntryGroup
        restFaqEntryGroupMockMvc.perform(post("/api/faq-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntryGroup)))
            .andExpect(status().isCreated());

        // Validate the FaqEntryGroup in the database
        List<FaqEntryGroup> faqEntryGroupList = faqEntryGroupRepository.findAll();
        assertThat(faqEntryGroupList).hasSize(databaseSizeBeforeCreate + 1);
        FaqEntryGroup testFaqEntryGroup = faqEntryGroupList.get(faqEntryGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void createFaqEntryGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = faqEntryGroupRepository.findAll().size();

        // Create the FaqEntryGroup with an existing ID
        faqEntryGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFaqEntryGroupMockMvc.perform(post("/api/faq-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntryGroup)))
            .andExpect(status().isBadRequest());

        // Validate the FaqEntryGroup in the database
        List<FaqEntryGroup> faqEntryGroupList = faqEntryGroupRepository.findAll();
        assertThat(faqEntryGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFaqEntryGroups() throws Exception {
        // Initialize the database
        faqEntryGroupRepository.saveAndFlush(faqEntryGroup);

        // Get all the faqEntryGroupList
        restFaqEntryGroupMockMvc.perform(get("/api/faq-entry-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(faqEntryGroup.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getFaqEntryGroup() throws Exception {
        // Initialize the database
        faqEntryGroupRepository.saveAndFlush(faqEntryGroup);

        // Get the faqEntryGroup
        restFaqEntryGroupMockMvc.perform(get("/api/faq-entry-groups/{id}", faqEntryGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(faqEntryGroup.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFaqEntryGroup() throws Exception {
        // Get the faqEntryGroup
        restFaqEntryGroupMockMvc.perform(get("/api/faq-entry-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFaqEntryGroup() throws Exception {
        // Initialize the database
        faqEntryGroupRepository.saveAndFlush(faqEntryGroup);

        int databaseSizeBeforeUpdate = faqEntryGroupRepository.findAll().size();

        // Update the faqEntryGroup
        FaqEntryGroup updatedFaqEntryGroup = faqEntryGroupRepository.findById(faqEntryGroup.getId()).get();
        // Disconnect from session so that the updates on updatedFaqEntryGroup are not directly saved in db
        em.detach(updatedFaqEntryGroup);

        restFaqEntryGroupMockMvc.perform(put("/api/faq-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFaqEntryGroup)))
            .andExpect(status().isOk());

        // Validate the FaqEntryGroup in the database
        List<FaqEntryGroup> faqEntryGroupList = faqEntryGroupRepository.findAll();
        assertThat(faqEntryGroupList).hasSize(databaseSizeBeforeUpdate);
        FaqEntryGroup testFaqEntryGroup = faqEntryGroupList.get(faqEntryGroupList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingFaqEntryGroup() throws Exception {
        int databaseSizeBeforeUpdate = faqEntryGroupRepository.findAll().size();

        // Create the FaqEntryGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFaqEntryGroupMockMvc.perform(put("/api/faq-entry-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(faqEntryGroup)))
            .andExpect(status().isBadRequest());

        // Validate the FaqEntryGroup in the database
        List<FaqEntryGroup> faqEntryGroupList = faqEntryGroupRepository.findAll();
        assertThat(faqEntryGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFaqEntryGroup() throws Exception {
        // Initialize the database
        faqEntryGroupRepository.saveAndFlush(faqEntryGroup);

        int databaseSizeBeforeDelete = faqEntryGroupRepository.findAll().size();

        // Delete the faqEntryGroup
        restFaqEntryGroupMockMvc.perform(delete("/api/faq-entry-groups/{id}", faqEntryGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FaqEntryGroup> faqEntryGroupList = faqEntryGroupRepository.findAll();
        assertThat(faqEntryGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
