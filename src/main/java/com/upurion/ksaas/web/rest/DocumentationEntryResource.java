package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.DocumentationEntry;
import com.upurion.ksaas.repository.DocumentationEntryRepository;
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
 * REST controller for managing {@link com.upurion.ksaas.domain.DocumentationEntry}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentationEntryResource {

    private final Logger log = LoggerFactory.getLogger(DocumentationEntryResource.class);

    private static final String ENTITY_NAME = "documentationEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentationEntryRepository documentationEntryRepository;

    public DocumentationEntryResource(DocumentationEntryRepository documentationEntryRepository) {
        this.documentationEntryRepository = documentationEntryRepository;
    }

    /**
     * {@code POST  /documentation-entries} : Create a new documentationEntry.
     *
     * @param documentationEntry the documentationEntry to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentationEntry, or with status {@code 400 (Bad Request)} if the documentationEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentation-entries")
    public ResponseEntity<DocumentationEntry> createDocumentationEntry(@Valid @RequestBody DocumentationEntry documentationEntry) throws URISyntaxException {
        log.debug("REST request to save DocumentationEntry : {}", documentationEntry);
        if (documentationEntry.getId() != null) {
            throw new BadRequestAlertException("A new documentationEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentationEntry result = documentationEntryRepository.save(documentationEntry);
        return ResponseEntity.created(new URI("/api/documentation-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentation-entries} : Updates an existing documentationEntry.
     *
     * @param documentationEntry the documentationEntry to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentationEntry,
     * or with status {@code 400 (Bad Request)} if the documentationEntry is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentationEntry couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentation-entries")
    public ResponseEntity<DocumentationEntry> updateDocumentationEntry(@Valid @RequestBody DocumentationEntry documentationEntry) throws URISyntaxException {
        log.debug("REST request to update DocumentationEntry : {}", documentationEntry);
        if (documentationEntry.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentationEntry result = documentationEntryRepository.save(documentationEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentationEntry.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documentation-entries} : get all the documentationEntries.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentationEntries in body.
     */
    @GetMapping("/documentation-entries")
    public List<DocumentationEntry> getAllDocumentationEntries() {
        log.debug("REST request to get all DocumentationEntries");
        return documentationEntryRepository.findAll();
    }

    /**
     * {@code GET  /documentation-entries/:id} : get the "id" documentationEntry.
     *
     * @param id the id of the documentationEntry to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentationEntry, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentation-entries/{id}")
    public ResponseEntity<DocumentationEntry> getDocumentationEntry(@PathVariable Long id) {
        log.debug("REST request to get DocumentationEntry : {}", id);
        Optional<DocumentationEntry> documentationEntry = documentationEntryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentationEntry);
    }

    /**
     * {@code DELETE  /documentation-entries/:id} : delete the "id" documentationEntry.
     *
     * @param id the id of the documentationEntry to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentation-entries/{id}")
    public ResponseEntity<Void> deleteDocumentationEntry(@PathVariable Long id) {
        log.debug("REST request to delete DocumentationEntry : {}", id);
        documentationEntryRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
