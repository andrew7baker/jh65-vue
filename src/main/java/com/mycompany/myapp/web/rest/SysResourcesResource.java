package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SysResources;
import com.mycompany.myapp.repository.SysResourcesRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.SysResources}.
 */
@RestController
@RequestMapping("/api")
public class SysResourcesResource {

    private final Logger log = LoggerFactory.getLogger(SysResourcesResource.class);

    private static final String ENTITY_NAME = "sysResources";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysResourcesRepository sysResourcesRepository;

    public SysResourcesResource(SysResourcesRepository sysResourcesRepository) {
        this.sysResourcesRepository = sysResourcesRepository;
    }

    /**
     * {@code POST  /sys-resources} : Create a new sysResources.
     *
     * @param sysResources the sysResources to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysResources, or with status {@code 400 (Bad Request)} if the sysResources has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-resources")
    public ResponseEntity<SysResources> createSysResources(@RequestBody SysResources sysResources) throws URISyntaxException {
        log.debug("REST request to save SysResources : {}", sysResources);
        if (sysResources.getId() != null) {
            throw new BadRequestAlertException("A new sysResources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysResources result = sysResourcesRepository.save(sysResources);
        return ResponseEntity.created(new URI("/api/sys-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-resources} : Updates an existing sysResources.
     *
     * @param sysResources the sysResources to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysResources,
     * or with status {@code 400 (Bad Request)} if the sysResources is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysResources couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-resources")
    public ResponseEntity<SysResources> updateSysResources(@RequestBody SysResources sysResources) throws URISyntaxException {
        log.debug("REST request to update SysResources : {}", sysResources);
        if (sysResources.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysResources result = sysResourcesRepository.save(sysResources);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysResources.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-resources} : get all the sysResources.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysResources in body.
     */
    @GetMapping("/sys-resources")
    public List<SysResources> getAllSysResources() {
        log.debug("REST request to get all SysResources");
        return sysResourcesRepository.findAll();
    }

    /**
     * {@code GET  /sys-resources/:id} : get the "id" sysResources.
     *
     * @param id the id of the sysResources to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysResources, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-resources/{id}")
    public ResponseEntity<SysResources> getSysResources(@PathVariable Long id) {
        log.debug("REST request to get SysResources : {}", id);
        Optional<SysResources> sysResources = sysResourcesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sysResources);
    }

    /**
     * {@code DELETE  /sys-resources/:id} : delete the "id" sysResources.
     *
     * @param id the id of the sysResources to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-resources/{id}")
    public ResponseEntity<Void> deleteSysResources(@PathVariable Long id) {
        log.debug("REST request to delete SysResources : {}", id);
        sysResourcesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
