package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.DocumentationChapter;
import com.upurion.ksaas.repository.DocumentationChapterRepository;
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
 * REST controller for managing {@link com.upurion.ksaas.domain.DocumentationChapter}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DocumentationChapterResource {

    private final Logger log = LoggerFactory.getLogger(DocumentationChapterResource.class);

    private static final String ENTITY_NAME = "documentationChapter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocumentationChapterRepository documentationChapterRepository;

    public DocumentationChapterResource(DocumentationChapterRepository documentationChapterRepository) {
        this.documentationChapterRepository = documentationChapterRepository;
    }

    /**
     * {@code POST  /documentation-chapters} : Create a new documentationChapter.
     *
     * @param documentationChapter the documentationChapter to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new documentationChapter, or with status {@code 400 (Bad Request)} if the documentationChapter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/documentation-chapters")
    public ResponseEntity<DocumentationChapter> createDocumentationChapter(@Valid @RequestBody DocumentationChapter documentationChapter) throws URISyntaxException {
        log.debug("REST request to save DocumentationChapter : {}", documentationChapter);
        if (documentationChapter.getId() != null) {
            throw new BadRequestAlertException("A new documentationChapter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentationChapter result = documentationChapterRepository.save(documentationChapter);
        return ResponseEntity.created(new URI("/api/documentation-chapters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /documentation-chapters} : Updates an existing documentationChapter.
     *
     * @param documentationChapter the documentationChapter to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated documentationChapter,
     * or with status {@code 400 (Bad Request)} if the documentationChapter is not valid,
     * or with status {@code 500 (Internal Server Error)} if the documentationChapter couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/documentation-chapters")
    public ResponseEntity<DocumentationChapter> updateDocumentationChapter(@Valid @RequestBody DocumentationChapter documentationChapter) throws URISyntaxException {
        log.debug("REST request to update DocumentationChapter : {}", documentationChapter);
        if (documentationChapter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DocumentationChapter result = documentationChapterRepository.save(documentationChapter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, documentationChapter.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /documentation-chapters} : get all the documentationChapters.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of documentationChapters in body.
     */
    @GetMapping("/documentation-chapters")
    public List<DocumentationChapter> getAllDocumentationChapters() {
        log.debug("REST request to get all DocumentationChapters");
        return documentationChapterRepository.findAll();
    }

    /**
     * {@code GET  /documentation-chapters/:id} : get the "id" documentationChapter.
     *
     * @param id the id of the documentationChapter to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the documentationChapter, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/documentation-chapters/{id}")
    public ResponseEntity<DocumentationChapter> getDocumentationChapter(@PathVariable Long id) {
        log.debug("REST request to get DocumentationChapter : {}", id);
        Optional<DocumentationChapter> documentationChapter = documentationChapterRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(documentationChapter);
    }

    /**
     * {@code DELETE  /documentation-chapters/:id} : delete the "id" documentationChapter.
     *
     * @param id the id of the documentationChapter to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/documentation-chapters/{id}")
    public ResponseEntity<Void> deleteDocumentationChapter(@PathVariable Long id) {
        log.debug("REST request to delete DocumentationChapter : {}", id);
        documentationChapterRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
