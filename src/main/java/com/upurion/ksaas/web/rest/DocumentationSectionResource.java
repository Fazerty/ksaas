package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.DocumentationSection;
import com.upurion.ksaas.repository.DocumentationSectionRepository;
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
 * REST controller for managing {@link com.upurion.ksaas.domain.DocumentationSection}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentationSectionResource {

    private final Logger log = LoggerFactory.getLogger(DocumentationSectionResource.class);

    private static final String ENTITY_NAME = "documentationSection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentationSectionRepository documentationSectionRepository;

    public DocumentationSectionResource(DocumentationSectionRepository documentationSectionRepository) {
        this.documentationSectionRepository = documentationSectionRepository;
    }

    /**
     * {@code POST  /documentation-sections} : Create a new documentationSection.
     *
     * @param documentationSection the documentationSection to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentationSection, or with status {@code 400 (Bad Request)} if the documentationSection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentation-sections")
    public ResponseEntity<DocumentationSection> createDocumentationSection(@Valid @RequestBody DocumentationSection documentationSection) throws URISyntaxException {
        log.debug("REST request to save DocumentationSection : {}", documentationSection);
        if (documentationSection.getId() != null) {
            throw new BadRequestAlertException("A new documentationSection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentationSection result = documentationSectionRepository.save(documentationSection);
        return ResponseEntity.created(new URI("/api/documentation-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentation-sections} : Updates an existing documentationSection.
     *
     * @param documentationSection the documentationSection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentationSection,
     * or with status {@code 400 (Bad Request)} if the documentationSection is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentationSection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentation-sections")
    public ResponseEntity<DocumentationSection> updateDocumentationSection(@Valid @RequestBody DocumentationSection documentationSection) throws URISyntaxException {
        log.debug("REST request to update DocumentationSection : {}", documentationSection);
        if (documentationSection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentationSection result = documentationSectionRepository.save(documentationSection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentationSection.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documentation-sections} : get all the documentationSections.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentationSections in body.
     */
    @GetMapping("/documentation-sections")
    public List<DocumentationSection> getAllDocumentationSections() {
        log.debug("REST request to get all DocumentationSections");
        return documentationSectionRepository.findAll();
    }

    /**
     * {@code GET  /documentation-sections/:id} : get the "id" documentationSection.
     *
     * @param id the id of the documentationSection to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentationSection, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentation-sections/{id}")
    public ResponseEntity<DocumentationSection> getDocumentationSection(@PathVariable Long id) {
        log.debug("REST request to get DocumentationSection : {}", id);
        Optional<DocumentationSection> documentationSection = documentationSectionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentationSection);
    }

    /**
     * {@code DELETE  /documentation-sections/:id} : delete the "id" documentationSection.
     *
     * @param id the id of the documentationSection to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentation-sections/{id}")
    public ResponseEntity<Void> deleteDocumentationSection(@PathVariable Long id) {
        log.debug("REST request to delete DocumentationSection : {}", id);
        documentationSectionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
