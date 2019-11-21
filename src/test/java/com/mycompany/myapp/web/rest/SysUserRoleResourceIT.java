package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhvueApp;
import com.mycompany.myapp.domain.SysUserRole;
import com.mycompany.myapp.repository.SysUserRoleRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SysUserRoleResource} REST controller.
 */
@SpringBootTest(classes = JhvueApp.class)
public class SysUserRoleResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ROLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SysUserRoleRepository sysUserRoleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restSysUserRoleMockMvc;

    private SysUserRole sysUserRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysUserRoleResource sysUserRoleResource = new SysUserRoleResource(sysUserRoleRepository);
        this.restSysUserRoleMockMvc = MockMvcBuilders.standaloneSetup(sysUserRoleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysUserRole createEntity(EntityManager em) {
        SysUserRole sysUserRole = new SysUserRole()
            .userId(DEFAULT_USER_ID)
            .roleId(DEFAULT_ROLE_ID)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return sysUserRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysUserRole createUpdatedEntity(EntityManager em) {
        SysUserRole sysUserRole = new SysUserRole()
            .userId(UPDATED_USER_ID)
            .roleId(UPDATED_ROLE_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return sysUserRole;
    }

    @BeforeEach
    public void initTest() {
        sysUserRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysUserRole() throws Exception {
        int databaseSizeBeforeCreate = sysUserRoleRepository.findAll().size();

        // Create the SysUserRole
        restSysUserRoleMockMvc.perform(post("/api/sys-user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysUserRole)))
            .andExpect(status().isCreated());

        // Validate the SysUserRole in the database
        List<SysUserRole> sysUserRoleList = sysUserRoleRepository.findAll();
        assertThat(sysUserRoleList).hasSize(databaseSizeBeforeCreate + 1);
        SysUserRole testSysUserRole = sysUserRoleList.get(sysUserRoleList.size() - 1);
        assertThat(testSysUserRole.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testSysUserRole.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testSysUserRole.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testSysUserRole.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createSysUserRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysUserRoleRepository.findAll().size();

        // Create the SysUserRole with an existing ID
        sysUserRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysUserRoleMockMvc.perform(post("/api/sys-user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysUserRole)))
            .andExpect(status().isBadRequest());

        // Validate the SysUserRole in the database
        List<SysUserRole> sysUserRoleList = sysUserRoleRepository.findAll();
        assertThat(sysUserRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysUserRoles() throws Exception {
        // Initialize the database
        sysUserRoleRepository.saveAndFlush(sysUserRole);

        // Get all the sysUserRoleList
        restSysUserRoleMockMvc.perform(get("/api/sys-user-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysUserRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSysUserRole() throws Exception {
        // Initialize the database
        sysUserRoleRepository.saveAndFlush(sysUserRole);

        // Get the sysUserRole
        restSysUserRoleMockMvc.perform(get("/api/sys-user-roles/{id}", sysUserRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysUserRole.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysUserRole() throws Exception {
        // Get the sysUserRole
        restSysUserRoleMockMvc.perform(get("/api/sys-user-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysUserRole() throws Exception {
        // Initialize the database
        sysUserRoleRepository.saveAndFlush(sysUserRole);

        int databaseSizeBeforeUpdate = sysUserRoleRepository.findAll().size();

        // Update the sysUserRole
        SysUserRole updatedSysUserRole = sysUserRoleRepository.findById(sysUserRole.getId()).get();
        // Disconnect from session so that the updates on updatedSysUserRole are not directly saved in db
        em.detach(updatedSysUserRole);
        updatedSysUserRole
            .userId(UPDATED_USER_ID)
            .roleId(UPDATED_ROLE_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restSysUserRoleMockMvc.perform(put("/api/sys-user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSysUserRole)))
            .andExpect(status().isOk());

        // Validate the SysUserRole in the database
        List<SysUserRole> sysUserRoleList = sysUserRoleRepository.findAll();
        assertThat(sysUserRoleList).hasSize(databaseSizeBeforeUpdate);
        SysUserRole testSysUserRole = sysUserRoleList.get(sysUserRoleList.size() - 1);
        assertThat(testSysUserRole.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testSysUserRole.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testSysUserRole.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testSysUserRole.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSysUserRole() throws Exception {
        int databaseSizeBeforeUpdate = sysUserRoleRepository.findAll().size();

        // Create the SysUserRole

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysUserRoleMockMvc.perform(put("/api/sys-user-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysUserRole)))
            .andExpect(status().isBadRequest());

        // Validate the SysUserRole in the database
        List<SysUserRole> sysUserRoleList = sysUserRoleRepository.findAll();
        assertThat(sysUserRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysUserRole() throws Exception {
        // Initialize the database
        sysUserRoleRepository.saveAndFlush(sysUserRole);

        int databaseSizeBeforeDelete = sysUserRoleRepository.findAll().size();

        // Delete the sysUserRole
        restSysUserRoleMockMvc.perform(delete("/api/sys-user-roles/{id}", sysUserRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysUserRole> sysUserRoleList = sysUserRoleRepository.findAll();
        assertThat(sysUserRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysUserRole.class);
        SysUserRole sysUserRole1 = new SysUserRole();
        sysUserRole1.setId(1L);
        SysUserRole sysUserRole2 = new SysUserRole();
        sysUserRole2.setId(sysUserRole1.getId());
        assertThat(sysUserRole1).isEqualTo(sysUserRole2);
        sysUserRole2.setId(2L);
        assertThat(sysUserRole1).isNotEqualTo(sysUserRole2);
        sysUserRole1.setId(null);
        assertThat(sysUserRole1).isNotEqualTo(sysUserRole2);
    }
}
