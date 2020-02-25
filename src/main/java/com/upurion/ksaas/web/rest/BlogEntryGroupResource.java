package com.upurion.ksaas.web.rest;

import com.upurion.ksaas.domain.BlogEntryGroup;
import com.upurion.ksaas.repository.BlogEntryGroupRepository;
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
 * REST controller for managing {@link com.upurion.ksaas.domain.BlogEntryGroup}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BlogEntryGroupResource {

    private final Logger log = LoggerFactory.getLogger(BlogEntryGroupResource.class);

    private static final String ENTITY_NAME = "blogEntryGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BlogEntryGroupRepository blogEntryGroupRepository;

    public BlogEntryGroupResource(BlogEntryGroupRepository blogEntryGroupRepository) {
        this.blogEntryGroupRepository = blogEntryGroupRepository;
    }

    /**
     * {@code POST  /blog-entry-groups} : Create a new blogEntryGroup.
     *
     * @param blogEntryGroup the blogEntryGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new blogEntryGroup, or with status {@code 400 (Bad Request)} if the blogEntryGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/blog-entry-groups")
    public ResponseEntity<BlogEntryGroup> createBlogEntryGroup(@RequestBody BlogEntryGroup blogEntryGroup) throws URISyntaxException {
        log.debug("REST request to save BlogEntryGroup : {}", blogEntryGroup);
        if (blogEntryGroup.getId() != null) {
            throw new BadRequestAlertException("A new blogEntryGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BlogEntryGroup result = blogEntryGroupRepository.save(blogEntryGroup);
        return ResponseEntity.created(new URI("/api/blog-entry-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /blog-entry-groups} : Updates an existing blogEntryGroup.
     *
     * @param blogEntryGroup the blogEntryGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated blogEntryGroup,
     * or with status {@code 400 (Bad Request)} if the blogEntryGroup is not valid,
     * or with status {@code 500 (Internal Server Error)} if the blogEntryGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/blog-entry-groups")
    public ResponseEntity<BlogEntryGroup> updateBlogEntryGroup(@RequestBody BlogEntryGroup blogEntryGroup) throws URISyntaxException {
        log.debug("REST request to update BlogEntryGroup : {}", blogEntryGroup);
        if (blogEntryGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BlogEntryGroup result = blogEntryGroupRepository.save(blogEntryGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, blogEntryGroup.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /blog-entry-groups} : get all the blogEntryGroups.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of blogEntryGroups in body.
     */
    @GetMapping("/blog-entry-groups")
    public List<BlogEntryGroup> getAllBlogEntryGroups() {
        log.debug("REST request to get all BlogEntryGroups");
        return blogEntryGroupRepository.findAll();
    }

    /**
     * {@code GET  /blog-entry-groups/:id} : get the "id" blogEntryGroup.
     *
     * @param id the id of the blogEntryGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the blogEntryGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/blog-entry-groups/{id}")
    public ResponseEntity<BlogEntryGroup> getBlogEntryGroup(@PathVariable Long id) {
        log.debug("REST request to get BlogEntryGroup : {}", id);
        Optional<BlogEntryGroup> blogEntryGroup = blogEntryGroupRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(blogEntryGroup);
    }

    /**
     * {@code DELETE  /blog-entry-groups/:id} : delete the "id" blogEntryGroup.
     *
     * @param id the id of the blogEntryGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/blog-entry-groups/{id}")
    public ResponseEntity<Void> deleteBlogEntryGroup(@PathVariable Long id) {
        log.debug("REST request to delete BlogEntryGroup : {}", id);
        blogEntryGroupRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
