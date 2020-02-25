package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.TranslatedName;
import com.upurion.ksaas.repository.TranslatedNameRepository;
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
 * REST controller for managing {@link com.upurion.ksaas.domain.TranslatedName}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TranslatedNameResource {

    private final Logger log = LoggerFactory.getLogger(TranslatedNameResource.class);

    private static final String ENTITY_NAME = "translatedName";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TranslatedNameRepository translatedNameRepository;

    public TranslatedNameResource(TranslatedNameRepository translatedNameRepository) {
        this.translatedNameRepository = translatedNameRepository;
    }

    /**
     * {@code POST  /translated-names} : Create a new translatedName.
     *
     * @param translatedName the translatedName to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new translatedName, or with status {@code 400 (Bad Request)} if the translatedName has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/translated-names")
    public ResponseEntity<TranslatedName> createTranslatedName(@Valid @RequestBody TranslatedName translatedName) throws URISyntaxException {
        log.debug("REST request to save TranslatedName : {}", translatedName);
        if (translatedName.getId() != null) {
            throw new BadRequestAlertException("A new translatedName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranslatedName result = translatedNameRepository.save(translatedName);
        return ResponseEntity.created(new URI("/api/translated-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /translated-names} : Updates an existing translatedName.
     *
     * @param translatedName the translatedName to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated translatedName,
     * or with status {@code 400 (Bad Request)} if the translatedName is not valid,
     * or with status {@code 500 (Internal Server Error)} if the translatedName couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/translated-names")
    public ResponseEntity<TranslatedName> updateTranslatedName(@Valid @RequestBody TranslatedName translatedName) throws URISyntaxException {
        log.debug("REST request to update TranslatedName : {}", translatedName);
        if (translatedName.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TranslatedName result = translatedNameRepository.save(translatedName);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, translatedName.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /translated-names} : get all the translatedNames.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of translatedNames in body.
     */
    @GetMapping("/translated-names")
    public List<TranslatedName> getAllTranslatedNames() {
        log.debug("REST request to get all TranslatedNames");
        return translatedNameRepository.findAll();
    }

    /**
     * {@code GET  /translated-names/:id} : get the "id" translatedName.
     *
     * @param id the id of the translatedName to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the translatedName, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/translated-names/{id}")
    public ResponseEntity<TranslatedName> getTranslatedName(@PathVariable Long id) {
        log.debug("REST request to get TranslatedName : {}", id);
        Optional<TranslatedName> translatedName = translatedNameRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(translatedName);
    }

    /**
     * {@code DELETE  /translated-names/:id} : delete the "id" translatedName.
     *
     * @param id the id of the translatedName to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/translated-names/{id}")
    public ResponseEntity<Void> deleteTranslatedName(@PathVariable Long id) {
        log.debug("REST request to delete TranslatedName : {}", id);
        translatedNameRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
