package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhvueApp;
import com.mycompany.myapp.domain.SysRole;
import com.mycompany.myapp.repository.SysRoleRepository;
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
 * Integration tests for the {@link SysRoleResource} REST controller.
 */
@SpringBootTest(classes = JhvueApp.class)
public class SysRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVAILABLE = 1;
    private static final Integer UPDATED_AVAILABLE = 2;

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SysRoleRepository sysRoleRepository;

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

    private MockMvc restSysRoleMockMvc;

    private SysRole sysRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysRoleResource sysRoleResource = new SysRoleResource(sysRoleRepository);
        this.restSysRoleMockMvc = MockMvcBuilders.standaloneSetup(sysRoleResource)
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
    public static SysRole createEntity(EntityManager em) {
        SysRole sysRole = new SysRole()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .available(DEFAULT_AVAILABLE)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return sysRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysRole createUpdatedEntity(EntityManager em) {
        SysRole sysRole = new SysRole()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .available(UPDATED_AVAILABLE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return sysRole;
    }

    @BeforeEach
    public void initTest() {
        sysRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysRole() throws Exception {
        int databaseSizeBeforeCreate = sysRoleRepository.findAll().size();

        // Create the SysRole
        restSysRoleMockMvc.perform(post("/api/sys-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysRole)))
            .andExpect(status().isCreated());

        // Validate the SysRole in the database
        List<SysRole> sysRoleList = sysRoleRepository.findAll();
        assertThat(sysRoleList).hasSize(databaseSizeBeforeCreate + 1);
        SysRole testSysRole = sysRoleList.get(sysRoleList.size() - 1);
        assertThat(testSysRole.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSysRole.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSysRole.getAvailable()).isEqualTo(DEFAULT_AVAILABLE);
        assertThat(testSysRole.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testSysRole.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createSysRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysRoleRepository.findAll().size();

        // Create the SysRole with an existing ID
        sysRole.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysRoleMockMvc.perform(post("/api/sys-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysRole)))
            .andExpect(status().isBadRequest());

        // Validate the SysRole in the database
        List<SysRole> sysRoleList = sysRoleRepository.findAll();
        assertThat(sysRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysRoles() throws Exception {
        // Initialize the database
        sysRoleRepository.saveAndFlush(sysRole);

        // Get all the sysRoleList
        restSysRoleMockMvc.perform(get("/api/sys-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].available").value(hasItem(DEFAULT_AVAILABLE)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSysRole() throws Exception {
        // Initialize the database
        sysRoleRepository.saveAndFlush(sysRole);

        // Get the sysRole
        restSysRoleMockMvc.perform(get("/api/sys-roles/{id}", sysRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.available").value(DEFAULT_AVAILABLE))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysRole() throws Exception {
        // Get the sysRole
        restSysRoleMockMvc.perform(get("/api/sys-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysRole() throws Exception {
        // Initialize the database
        sysRoleRepository.saveAndFlush(sysRole);

        int databaseSizeBeforeUpdate = sysRoleRepository.findAll().size();

        // Update the sysRole
        SysRole updatedSysRole = sysRoleRepository.findById(sysRole.getId()).get();
        // Disconnect from session so that the updates on updatedSysRole are not directly saved in db
        em.detach(updatedSysRole);
        updatedSysRole
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .available(UPDATED_AVAILABLE)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restSysRoleMockMvc.perform(put("/api/sys-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSysRole)))
            .andExpect(status().isOk());

        // Validate the SysRole in the database
        List<SysRole> sysRoleList = sysRoleRepository.findAll();
        assertThat(sysRoleList).hasSize(databaseSizeBeforeUpdate);
        SysRole testSysRole = sysRoleList.get(sysRoleList.size() - 1);
        assertThat(testSysRole.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSysRole.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSysRole.getAvailable()).isEqualTo(UPDATED_AVAILABLE);
        assertThat(testSysRole.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testSysRole.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSysRole() throws Exception {
        int databaseSizeBeforeUpdate = sysRoleRepository.findAll().size();

        // Create the SysRole

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysRoleMockMvc.perform(put("/api/sys-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysRole)))
            .andExpect(status().isBadRequest());

        // Validate the SysRole in the database
        List<SysRole> sysRoleList = sysRoleRepository.findAll();
        assertThat(sysRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysRole() throws Exception {
        // Initialize the database
        sysRoleRepository.saveAndFlush(sysRole);

        int databaseSizeBeforeDelete = sysRoleRepository.findAll().size();

        // Delete the sysRole
        restSysRoleMockMvc.perform(delete("/api/sys-roles/{id}", sysRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysRole> sysRoleList = sysRoleRepository.findAll();
        assertThat(sysRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysRole.class);
        SysRole sysRole1 = new SysRole();
        sysRole1.setId(1L);
        SysRole sysRole2 = new SysRole();
        sysRole2.setId(sysRole1.getId());
        assertThat(sysRole1).isEqualTo(sysRole2);
        sysRole2.setId(2L);
        assertThat(sysRole1).isNotEqualTo(sysRole2);
        sysRole1.setId(null);
        assertThat(sysRole1).isNotEqualTo(sysRole2);
    }
}
