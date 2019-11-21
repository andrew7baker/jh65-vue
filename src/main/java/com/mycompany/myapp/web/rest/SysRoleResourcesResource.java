package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SysRoleResources;
import com.mycompany.myapp.repository.SysRoleResourcesRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SysRoleResources}.
 */
@RestController
@RequestMapping("/api")
public class SysRoleResourcesResource {

    private final Logger log = LoggerFactory.getLogger(SysRoleResourcesResource.class);

    private static final String ENTITY_NAME = "sysRoleResources";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysRoleResourcesRepository sysRoleResourcesRepository;

    public SysRoleResourcesResource(SysRoleResourcesRepository sysRoleResourcesRepository) {
        this.sysRoleResourcesRepository = sysRoleResourcesRepository;
    }

    /**
     * {@code POST  /sys-role-resources} : Create a new sysRoleResources.
     *
     * @param sysRoleResources the sysRoleResources to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysRoleResources, or with status {@code 400 (Bad Request)} if the sysRoleResources has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-role-resources")
    public ResponseEntity<SysRoleResources> createSysRoleResources(@RequestBody SysRoleResources sysRoleResources) throws URISyntaxException {
        log.debug("REST request to save SysRoleResources : {}", sysRoleResources);
        if (sysRoleResources.getId() != null) {
            throw new BadRequestAlertException("A new sysRoleResources cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysRoleResources result = sysRoleResourcesRepository.save(sysRoleResources);
        return ResponseEntity.created(new URI("/api/sys-role-resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-role-resources} : Updates an existing sysRoleResources.
     *
     * @param sysRoleResources the sysRoleResources to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysRoleResources,
     * or with status {@code 400 (Bad Request)} if the sysRoleResources is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysRoleResources couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-role-resources")
    public ResponseEntity<SysRoleResources> updateSysRoleResources(@RequestBody SysRoleResources sysRoleResources) throws URISyntaxException {
        log.debug("REST request to update SysRoleResources : {}", sysRoleResources);
        if (sysRoleResources.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysRoleResources result = sysRoleResourcesRepository.save(sysRoleResources);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysRoleResources.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-role-resources} : get all the sysRoleResources.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysRoleResources in body.
     */
    @GetMapping("/sys-role-resources")
    public List<SysRoleResources> getAllSysRoleResources() {
        log.debug("REST request to get all SysRoleResources");
        return sysRoleResourcesRepository.findAll();
    }

    /**
     * {@code GET  /sys-role-resources/:id} : get the "id" sysRoleResources.
     *
     * @param id the id of the sysRoleResources to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysRoleResources, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-role-resources/{id}")
    public ResponseEntity<SysRoleResources> getSysRoleResources(@PathVariable Long id) {
        log.debug("REST request to get SysRoleResources : {}", id);
        Optional<SysRoleResources> sysRoleResources = sysRoleResourcesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sysRoleResources);
    }

    /**
     * {@code DELETE  /sys-role-resources/:id} : delete the "id" sysRoleResources.
     *
     * @param id the id of the sysRoleResources to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-role-resources/{id}")
    public ResponseEntity<Void> deleteSysRoleResources(@PathVariable Long id) {
        log.debug("REST request to delete SysRoleResources : {}", id);
        sysRoleResourcesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
