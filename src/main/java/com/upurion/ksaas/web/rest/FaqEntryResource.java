package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.FaqEntry;
import com.upurion.ksaas.repository.FaqEntryRepository;
import com.upurion.ksaas.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.upurion.ksaas.domain.FaqEntry}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FaqEntryResource {

    private final Logger log = LoggerFactory.getLogger(FaqEntryResource.class);

    private static final String ENTITY_NAME = "faqEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FaqEntryRepository faqEntryRepository;

    public FaqEntryResource(FaqEntryRepository faqEntryRepository) {
        this.faqEntryRepository = faqEntryRepository;
    }

    /**
     * {@code POST  /faq-entries} : Create a new faqEntry.
     *
     * @param faqEntry the faqEntry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faqEntry, or with status {@code 400 (Bad Request)} if the faqEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/faq-entries")
    public ResponseEntity<FaqEntry> createFaqEntry(@Valid @RequestBody FaqEntry faqEntry) throws URISyntaxException {
        log.debug("REST request to save FaqEntry : {}", faqEntry);
        if (faqEntry.getId() != null) {
            throw new BadRequestAlertException("A new faqEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaqEntry result = faqEntryRepository.save(faqEntry);
        return ResponseEntity.created(new URI("/api/faq-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /faq-entries} : Updates an existing faqEntry.
     *
     * @param faqEntry the faqEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faqEntry,
     * or with status {@code 400 (Bad Request)} if the faqEntry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the faqEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/faq-entries")
    public ResponseEntity<FaqEntry> updateFaqEntry(@Valid @RequestBody FaqEntry faqEntry) throws URISyntaxException {
        log.debug("REST request to update FaqEntry : {}", faqEntry);
        if (faqEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FaqEntry result = faqEntryRepository.save(faqEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, faqEntry.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /faq-entries} : get all the faqEntries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faqEntries in body.
     */
    @GetMapping("/faq-entries")
    public List<FaqEntry> getAllFaqEntries() {
        log.debug("REST request to get all FaqEntries");
        return faqEntryRepository.findAll();
    }

    /**
     * {@code GET  /faq-entries/:id} : get the "id" faqEntry.
     *
     * @param id the id of the faqEntry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faqEntry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/faq-entries/{id}")
    public ResponseEntity<FaqEntry> getFaqEntry(@PathVariable Long id) {
        log.debug("REST request to get FaqEntry : {}", id);
        Optional<FaqEntry> faqEntry = faqEntryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(faqEntry);
    }

    /**
     * {@code DELETE  /faq-entries/:id} : delete the "id" faqEntry.
     *
     * @param id the id of the faqEntry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/faq-entries/{id}")
    public ResponseEntity<Void> deleteFaqEntry(@PathVariable Long id) {
        log.debug("REST request to delete FaqEntry : {}", id);
        faqEntryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
