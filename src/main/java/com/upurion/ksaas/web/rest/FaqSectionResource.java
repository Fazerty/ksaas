package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.FaqSection;
import com.upurion.ksaas.repository.FaqSectionRepository;
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
 * REST controller for managing {@link com.upurion.ksaas.domain.FaqSection}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FaqSectionResource {

    private final Logger log = LoggerFactory.getLogger(FaqSectionResource.class);

    private static final String ENTITY_NAME = "faqSection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FaqSectionRepository faqSectionRepository;

    public FaqSectionResource(FaqSectionRepository faqSectionRepository) {
        this.faqSectionRepository = faqSectionRepository;
    }

    /**
     * {@code POST  /faq-sections} : Create a new faqSection.
     *
     * @param faqSection the faqSection to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new faqSection, or with status {@code 400 (Bad Request)} if the faqSection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/faq-sections")
    public ResponseEntity<FaqSection> createFaqSection(@Valid @RequestBody FaqSection faqSection) throws URISyntaxException {
        log.debug("REST request to save FaqSection : {}", faqSection);
        if (faqSection.getId() != null) {
            throw new BadRequestAlertException("A new faqSection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FaqSection result = faqSectionRepository.save(faqSection);
        return ResponseEntity.created(new URI("/api/faq-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /faq-sections} : Updates an existing faqSection.
     *
     * @param faqSection the faqSection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated faqSection,
     * or with status {@code 400 (Bad Request)} if the faqSection is not valid,
     * or with status {@code 500 (Internal Server Error)} if the faqSection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/faq-sections")
    public ResponseEntity<FaqSection> updateFaqSection(@Valid @RequestBody FaqSection faqSection) throws URISyntaxException {
        log.debug("REST request to update FaqSection : {}", faqSection);
        if (faqSection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FaqSection result = faqSectionRepository.save(faqSection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, faqSection.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /faq-sections} : get all the faqSections.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of faqSections in body.
     */
    @GetMapping("/faq-sections")
    public List<FaqSection> getAllFaqSections() {
        log.debug("REST request to get all FaqSections");
        return faqSectionRepository.findAll();
    }

    /**
     * {@code GET  /faq-sections/:id} : get the "id" faqSection.
     *
     * @param id the id of the faqSection to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the faqSection, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/faq-sections/{id}")
    public ResponseEntity<FaqSection> getFaqSection(@PathVariable Long id) {
        log.debug("REST request to get FaqSection : {}", id);
        Optional<FaqSection> faqSection = faqSectionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(faqSection);
    }

    /**
     * {@code DELETE  /faq-sections/:id} : delete the "id" faqSection.
     *
     * @param id the id of the faqSection to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/faq-sections/{id}")
    public ResponseEntity<Void> deleteFaqSection(@PathVariable Long id) {
        log.debug("REST request to delete FaqSection : {}", id);
        faqSectionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
