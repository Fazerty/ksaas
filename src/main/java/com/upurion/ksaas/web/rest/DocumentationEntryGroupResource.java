package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.DocumentationEntryGroup;
import com.upurion.ksaas.repository.DocumentationEntryGroupRepository;
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
 * REST controller for managing {@link com.upurion.ksaas.domain.DocumentationEntryGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentationEntryGroupResource {

    private final Logger log = LoggerFactory.getLogger(DocumentationEntryGroupResource.class);

    private static final String ENTITY_NAME = "documentationEntryGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentationEntryGroupRepository documentationEntryGroupRepository;

    public DocumentationEntryGroupResource(DocumentationEntryGroupRepository documentationEntryGroupRepository) {
        this.documentationEntryGroupRepository = documentationEntryGroupRepository;
    }

    /**
     * {@code POST  /documentation-entry-groups} : Create a new documentationEntryGroup.
     *
     * @param documentationEntryGroup the documentationEntryGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentationEntryGroup, or with status {@code 400 (Bad Request)} if the documentationEntryGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentation-entry-groups")
    public ResponseEntity<DocumentationEntryGroup> createDocumentationEntryGroup(@RequestBody DocumentationEntryGroup documentationEntryGroup) throws URISyntaxException {
        log.debug("REST request to save DocumentationEntryGroup : {}", documentationEntryGroup);
        if (documentationEntryGroup.getId() != null) {
            throw new BadRequestAlertException("A new documentationEntryGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentationEntryGroup result = documentationEntryGroupRepository.save(documentationEntryGroup);
        return ResponseEntity.created(new URI("/api/documentation-entry-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentation-entry-groups} : Updates an existing documentationEntryGroup.
     *
     * @param documentationEntryGroup the documentationEntryGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentationEntryGroup,
     * or with status {@code 400 (Bad Request)} if the documentationEntryGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentationEntryGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentation-entry-groups")
    public ResponseEntity<DocumentationEntryGroup> updateDocumentationEntryGroup(@RequestBody DocumentationEntryGroup documentationEntryGroup) throws URISyntaxException {
        log.debug("REST request to update DocumentationEntryGroup : {}", documentationEntryGroup);
        if (documentationEntryGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentationEntryGroup result = documentationEntryGroupRepository.save(documentationEntryGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentationEntryGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documentation-entry-groups} : get all the documentationEntryGroups.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentationEntryGroups in body.
     */
    @GetMapping("/documentation-entry-groups")
    public List<DocumentationEntryGroup> getAllDocumentationEntryGroups() {
        log.debug("REST request to get all DocumentationEntryGroups");
        return documentationEntryGroupRepository.findAll();
    }

    /**
     * {@code GET  /documentation-entry-groups/:id} : get the "id" documentationEntryGroup.
     *
     * @param id the id of the documentationEntryGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentationEntryGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentation-entry-groups/{id}")
    public ResponseEntity<DocumentationEntryGroup> getDocumentationEntryGroup(@PathVariable Long id) {
        log.debug("REST request to get DocumentationEntryGroup : {}", id);
        Optional<DocumentationEntryGroup> documentationEntryGroup = documentationEntryGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentationEntryGroup);
    }

    /**
     * {@code DELETE  /documentation-entry-groups/:id} : delete the "id" documentationEntryGroup.
     *
     * @param id the id of the documentationEntryGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentation-entry-groups/{id}")
    public ResponseEntity<Void> deleteDocumentationEntryGroup(@PathVariable Long id) {
        log.debug("REST request to delete DocumentationEntryGroup : {}", id);
        documentationEntryGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
