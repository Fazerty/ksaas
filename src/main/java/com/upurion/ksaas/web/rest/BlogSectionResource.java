package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.BlogSection;
import com.upurion.ksaas.domain.enumeration.Language;
import com.upurion.ksaas.repository.BlogSectionRepository;
import com.upurion.ksaas.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;




/**
 * REST controller for managing {@link com.upurion.ksaas.domain.BlogSection}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BlogSectionResource {

    private final Logger log = LoggerFactory.getLogger(BlogSectionResource.class);

    private static final String ENTITY_NAME = "blogSection";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogSectionRepository blogSectionRepository;

    public BlogSectionResource(BlogSectionRepository blogSectionRepository) {
        this.blogSectionRepository = blogSectionRepository;
    }

    /**
     * {@code POST  /blog-sections} : Create a new blogSection.
     *
     * @param blogSection the blogSection to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogSection, or with status {@code 400 (Bad Request)} if the blogSection has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blog-sections")
    public ResponseEntity<BlogSection> createBlogSection(@Valid @RequestBody BlogSection blogSection) throws URISyntaxException {
        log.debug("REST request to save BlogSection : {}", blogSection);
        if (blogSection.getId() != null) {
            throw new BadRequestAlertException("A new blogSection cannot already have an ID", ENTITY_NAME, "idexists");
        }
        // names can't use a language more thance once.
        List<Language> usedLanguages = new ArrayList<Language>();
        log.debug("getNames", blogSection.getNames());
        blogSection.getNames().forEach(name ->  {if (usedLanguages.indexOf(name.getLanguage()) != -1){
            throw new BadRequestAlertException("A new blogSection cannot have multiple names in the same language", ENTITY_NAME, "namesinsamelanguage");
        } else {
            log.debug("usedLanguages", usedLanguages);
            usedLanguages.add(name.getLanguage());
        } });

        BlogSection result = blogSectionRepository.save(blogSection);
        return ResponseEntity.created(new URI("/api/blog-sections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blog-sections} : Updates an existing blogSection.
     *
     * @param blogSection the blogSection to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogSection,
     * or with status {@code 400 (Bad Request)} if the blogSection is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogSection couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blog-sections")
    public ResponseEntity<BlogSection> updateBlogSection(@Valid @RequestBody BlogSection blogSection) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to update BlogSection : {}", blogSection);
        if (blogSection.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        // names can't use a language more thance once.
        List<Language> usedLanguages = new ArrayList<Language>();
        blogSection.getNames().forEach(name ->  {if (usedLanguages.indexOf(name.getLanguage()) != -1){
                    throw new BadRequestAlertException("A new blogSection cannot have multiple names in the same language", ENTITY_NAME, "namesinsamelanguage");
                } else {
                    log.debug("usedLanguages", usedLanguages);
                    usedLanguages.add(name.getLanguage());
        } });
        BlogSection result = blogSectionRepository.save(blogSection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogSection.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blog-sections} : get all the blogSections.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogSections in body.
     */
    @GetMapping("/blog-sections")
    public List<BlogSection> getAllBlogSections() {
        log.debug("REST request to get all BlogSections");
        return blogSectionRepository.findAll();
    }

    /**
     * {@code GET  /blog-sections/:id} : get the "id" blogSection.
     *
     * @param id the id of the blogSection to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogSection, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blog-sections/{id}")
    public ResponseEntity<BlogSection> getBlogSection(@PathVariable Long id) {
        log.debug("REST request to get BlogSection : {}", id);
        Optional<BlogSection> blogSection = blogSectionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(blogSection);
    }

    /**
     * {@code DELETE  /blog-sections/:id} : delete the "id" blogSection.
     *
     * @param id the id of the blogSection to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blog-sections/{id}")
    public ResponseEntity<Void> deleteBlogSection(@PathVariable Long id) {
        log.debug("REST request to delete BlogSection : {}", id);
        blogSectionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
