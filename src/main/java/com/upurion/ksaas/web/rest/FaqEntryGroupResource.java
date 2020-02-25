package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.FaqEntryGroup;
import com.upurion.ksaas.repository.FaqEntryGroupRepository;
import com.upurion.ksaas.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.upurion.ksaas.domain.FaqEntryGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FaqEntryGroupResource {

    private final Logger log = LoggerFactory.getLogger(FaqEntryGroupResource.class);

    private static final String ENTITY_NAME = "faqEntryGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FaqEntryGroupRepository faqEntryGroupRepository;

    public FaqEntryGroupResource(FaqEntryGroupRepository faqEntryGroupRepository) {
        this.faqEntryGroupRepository = faqEntryGroupRepository;
    }

    /**
     * {@code POST  /faq-entry-groups} : Create a new faqEntryGroup.
     *
     * @param faqEntryGroup the faqEntryGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faqEntryGroup, or with status {@code 400 (Bad Request)} if the faqEntryGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/faq-entry-groups")
    public ResponseEntity<FaqEntryGroup> createFaqEntryGroup(@RequestBody FaqEntryGroup faqEntryGroup) throws URISyntaxException {
        log.debug("REST request to save FaqEntryGroup : {}", faqEntryGroup);
        if (faqEntryGroup.getId() != null) {
            throw new BadRequestAlertException("A new faqEntryGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaqEntryGroup result = faqEntryGroupRepository.save(faqEntryGroup);
        return ResponseEntity.created(new URI("/api/faq-entry-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /faq-entry-groups} : Updates an existing faqEntryGroup.
     *
     * @param faqEntryGroup the faqEntryGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faqEntryGroup,
     * or with status {@code 400 (Bad Request)} if the faqEntryGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the faqEntryGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/faq-entry-groups")
    public ResponseEntity<FaqEntryGroup> updateFaqEntryGroup(@RequestBody FaqEntryGroup faqEntryGroup) throws URISyntaxException {
        log.debug("REST request to update FaqEntryGroup : {}", faqEntryGroup);
        if (faqEntryGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FaqEntryGroup result = faqEntryGroupRepository.save(faqEntryGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, faqEntryGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /faq-entry-groups} : get all the faqEntryGroups.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faqEntryGroups in body.
     */
    @GetMapping("/faq-entry-groups")
    public List<FaqEntryGroup> getAllFaqEntryGroups() {
        log.debug("REST request to get all FaqEntryGroups");
        return faqEntryGroupRepository.findAll();
    }

    /**
     * {@code GET  /faq-entry-groups/:id} : get the "id" faqEntryGroup.
     *
     * @param id the id of the faqEntryGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faqEntryGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/faq-entry-groups/{id}")
    public ResponseEntity<FaqEntryGroup> getFaqEntryGroup(@PathVariable Long id) {
        log.debug("REST request to get FaqEntryGroup : {}", id);
        Optional<FaqEntryGroup> faqEntryGroup = faqEntryGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(faqEntryGroup);
    }

    /**
     * {@code DELETE  /faq-entry-groups/:id} : delete the "id" faqEntryGroup.
     *
     * @param id the id of the faqEntryGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/faq-entry-groups/{id}")
    public ResponseEntity<Void> deleteFaqEntryGroup(@PathVariable Long id) {
        log.debug("REST request to delete FaqEntryGroup : {}", id);
        faqEntryGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
