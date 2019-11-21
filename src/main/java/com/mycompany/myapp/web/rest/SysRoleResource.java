package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SysRole;
import com.mycompany.myapp.repository.SysRoleRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SysRole}.
 */
@RestController
@RequestMapping("/api")
public class SysRoleResource {

    private final Logger log = LoggerFactory.getLogger(SysRoleResource.class);

    private static final String ENTITY_NAME = "sysRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysRoleRepository sysRoleRepository;

    public SysRoleResource(SysRoleRepository sysRoleRepository) {
        this.sysRoleRepository = sysRoleRepository;
    }

    /**
     * {@code POST  /sys-roles} : Create a new sysRole.
     *
     * @param sysRole the sysRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysRole, or with status {@code 400 (Bad Request)} if the sysRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-roles")
    public ResponseEntity<SysRole> createSysRole(@RequestBody SysRole sysRole) throws URISyntaxException {
        log.debug("REST request to save SysRole : {}", sysRole);
        if (sysRole.getId() != null) {
            throw new BadRequestAlertException("A new sysRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysRole result = sysRoleRepository.save(sysRole);
        return ResponseEntity.created(new URI("/api/sys-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-roles} : Updates an existing sysRole.
     *
     * @param sysRole the sysRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysRole,
     * or with status {@code 400 (Bad Request)} if the sysRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-roles")
    public ResponseEntity<SysRole> updateSysRole(@RequestBody SysRole sysRole) throws URISyntaxException {
        log.debug("REST request to update SysRole : {}", sysRole);
        if (sysRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysRole result = sysRoleRepository.save(sysRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-roles} : get all the sysRoles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysRoles in body.
     */
    @GetMapping("/sys-roles")
    public List<SysRole> getAllSysRoles() {
        log.debug("REST request to get all SysRoles");
        return sysRoleRepository.findAll();
    }

    /**
     * {@code GET  /sys-roles/:id} : get the "id" sysRole.
     *
     * @param id the id of the sysRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-roles/{id}")
    public ResponseEntity<SysRole> getSysRole(@PathVariable Long id) {
        log.debug("REST request to get SysRole : {}", id);
        Optional<SysRole> sysRole = sysRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sysRole);
    }

    /**
     * {@code DELETE  /sys-roles/:id} : delete the "id" sysRole.
     *
     * @param id the id of the sysRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-roles/{id}")
    public ResponseEntity<Void> deleteSysRole(@PathVariable Long id) {
        log.debug("REST request to delete SysRole : {}", id);
        sysRoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
