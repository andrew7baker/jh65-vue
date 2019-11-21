package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.SysUser;
import com.mycompany.myapp.repository.SysUserRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.SysUser}.
 */
@RestController
@RequestMapping("/api")
public class SysUserResource {

    private final Logger log = LoggerFactory.getLogger(SysUserResource.class);

    private static final String ENTITY_NAME = "sysUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysUserRepository sysUserRepository;

    public SysUserResource(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    /**
     * {@code POST  /sys-users} : Create a new sysUser.
     *
     * @param sysUser the sysUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysUser, or with status {@code 400 (Bad Request)} if the sysUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-users")
    public ResponseEntity<SysUser> createSysUser(@RequestBody SysUser sysUser) throws URISyntaxException {
        log.debug("REST request to save SysUser : {}", sysUser);
        if (sysUser.getId() != null) {
            throw new BadRequestAlertException("A new sysUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysUser result = sysUserRepository.save(sysUser);
        return ResponseEntity.created(new URI("/api/sys-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-users} : Updates an existing sysUser.
     *
     * @param sysUser the sysUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysUser,
     * or with status {@code 400 (Bad Request)} if the sysUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-users")
    public ResponseEntity<SysUser> updateSysUser(@RequestBody SysUser sysUser) throws URISyntaxException {
        log.debug("REST request to update SysUser : {}", sysUser);
        if (sysUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysUser result = sysUserRepository.save(sysUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sysUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-users} : get all the sysUsers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysUsers in body.
     */
    @GetMapping("/sys-users")
    public List<SysUser> getAllSysUsers() {
        log.debug("REST request to get all SysUsers");
        return sysUserRepository.findAll();
    }

    /**
     * {@code GET  /sys-users/:id} : get the "id" sysUser.
     *
     * @param id the id of the sysUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-users/{id}")
    public ResponseEntity<SysUser> getSysUser(@PathVariable Long id) {
        log.debug("REST request to get SysUser : {}", id);
        Optional<SysUser> sysUser = sysUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sysUser);
    }

    /**
     * {@code DELETE  /sys-users/:id} : delete the "id" sysUser.
     *
     * @param id the id of the sysUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-users/{id}")
    public ResponseEntity<Void> deleteSysUser(@PathVariable Long id) {
        log.debug("REST request to delete SysUser : {}", id);
        sysUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
