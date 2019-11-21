package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SysUserRole;
import com.mycompany.myapp.repository.SysUserRoleRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SysUserRole}.
 */
@RestController
@RequestMapping("/api")
public class SysUserRoleResource {

    private final Logger log = LoggerFactory.getLogger(SysUserRoleResource.class);

    private static final String ENTITY_NAME = "sysUserRole";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysUserRoleRepository sysUserRoleRepository;

    public SysUserRoleResource(SysUserRoleRepository sysUserRoleRepository) {
        this.sysUserRoleRepository = sysUserRoleRepository;
    }

    /**
     * {@code POST  /sys-user-roles} : Create a new sysUserRole.
     *
     * @param sysUserRole the sysUserRole to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysUserRole, or with status {@code 400 (Bad Request)} if the sysUserRole has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-user-roles")
    public ResponseEntity<SysUserRole> createSysUserRole(@RequestBody SysUserRole sysUserRole) throws URISyntaxException {
        log.debug("REST request to save SysUserRole : {}", sysUserRole);
        if (sysUserRole.getId() != null) {
            throw new BadRequestAlertException("A new sysUserRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysUserRole result = sysUserRoleRepository.save(sysUserRole);
        return ResponseEntity.created(new URI("/api/sys-user-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-user-roles} : Updates an existing sysUserRole.
     *
     * @param sysUserRole the sysUserRole to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysUserRole,
     * or with status {@code 400 (Bad Request)} if the sysUserRole is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysUserRole couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-user-roles")
    public ResponseEntity<SysUserRole> updateSysUserRole(@RequestBody SysUserRole sysUserRole) throws URISyntaxException {
        log.debug("REST request to update SysUserRole : {}", sysUserRole);
        if (sysUserRole.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysUserRole result = sysUserRoleRepository.save(sysUserRole);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysUserRole.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-user-roles} : get all the sysUserRoles.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysUserRoles in body.
     */
    @GetMapping("/sys-user-roles")
    public List<SysUserRole> getAllSysUserRoles() {
        log.debug("REST request to get all SysUserRoles");
        return sysUserRoleRepository.findAll();
    }

    /**
     * {@code GET  /sys-user-roles/:id} : get the "id" sysUserRole.
     *
     * @param id the id of the sysUserRole to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysUserRole, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-user-roles/{id}")
    public ResponseEntity<SysUserRole> getSysUserRole(@PathVariable Long id) {
        log.debug("REST request to get SysUserRole : {}", id);
        Optional<SysUserRole> sysUserRole = sysUserRoleRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sysUserRole);
    }

    /**
     * {@code DELETE  /sys-user-roles/:id} : delete the "id" sysUserRole.
     *
     * @param id the id of the sysUserRole to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-user-roles/{id}")
    public ResponseEntity<Void> deleteSysUserRole(@PathVariable Long id) {
        log.debug("REST request to delete SysUserRole : {}", id);
        sysUserRoleRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
