package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhvueApp;
import com.mycompany.myapp.domain.SysRoleResources;
import com.mycompany.myapp.repository.SysRoleResourcesRepository;
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
 * Integration tests for the {@link SysRoleResourcesResource} REST controller.
 */
@SpringBootTest(classes = JhvueApp.class)
public class SysRoleResourcesResourceIT {

    private static final String DEFAULT_ROLE_ID = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCES_ID = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCES_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SysRoleResourcesRepository sysRoleResourcesRepository;

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

    private MockMvc restSysRoleResourcesMockMvc;

    private SysRoleResources sysRoleResources;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysRoleResourcesResource sysRoleResourcesResource = new SysRoleResourcesResource(sysRoleResourcesRepository);
        this.restSysRoleResourcesMockMvc = MockMvcBuilders.standaloneSetup(sysRoleResourcesResource)
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
    public static SysRoleResources createEntity(EntityManager em) {
        SysRoleResources sysRoleResources = new SysRoleResources()
            .roleId(DEFAULT_ROLE_ID)
            .resourcesId(DEFAULT_RESOURCES_ID)
            .createTime(DEFAULT_CREATE_TIME)
            .updateTime(DEFAULT_UPDATE_TIME);
        return sysRoleResources;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysRoleResources createUpdatedEntity(EntityManager em) {
        SysRoleResources sysRoleResources = new SysRoleResources()
            .roleId(UPDATED_ROLE_ID)
            .resourcesId(UPDATED_RESOURCES_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);
        return sysRoleResources;
    }

    @BeforeEach
    public void initTest() {
        sysRoleResources = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysRoleResources() throws Exception {
        int databaseSizeBeforeCreate = sysRoleResourcesRepository.findAll().size();

        // Create the SysRoleResources
        restSysRoleResourcesMockMvc.perform(post("/api/sys-role-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysRoleResources)))
            .andExpect(status().isCreated());

        // Validate the SysRoleResources in the database
        List<SysRoleResources> sysRoleResourcesList = sysRoleResourcesRepository.findAll();
        assertThat(sysRoleResourcesList).hasSize(databaseSizeBeforeCreate + 1);
        SysRoleResources testSysRoleResources = sysRoleResourcesList.get(sysRoleResourcesList.size() - 1);
        assertThat(testSysRoleResources.getRoleId()).isEqualTo(DEFAULT_ROLE_ID);
        assertThat(testSysRoleResources.getResourcesId()).isEqualTo(DEFAULT_RESOURCES_ID);
        assertThat(testSysRoleResources.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testSysRoleResources.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createSysRoleResourcesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysRoleResourcesRepository.findAll().size();

        // Create the SysRoleResources with an existing ID
        sysRoleResources.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysRoleResourcesMockMvc.perform(post("/api/sys-role-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysRoleResources)))
            .andExpect(status().isBadRequest());

        // Validate the SysRoleResources in the database
        List<SysRoleResources> sysRoleResourcesList = sysRoleResourcesRepository.findAll();
        assertThat(sysRoleResourcesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysRoleResources() throws Exception {
        // Initialize the database
        sysRoleResourcesRepository.saveAndFlush(sysRoleResources);

        // Get all the sysRoleResourcesList
        restSysRoleResourcesMockMvc.perform(get("/api/sys-role-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysRoleResources.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleId").value(hasItem(DEFAULT_ROLE_ID)))
            .andExpect(jsonPath("$.[*].resourcesId").value(hasItem(DEFAULT_RESOURCES_ID)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSysRoleResources() throws Exception {
        // Initialize the database
        sysRoleResourcesRepository.saveAndFlush(sysRoleResources);

        // Get the sysRoleResources
        restSysRoleResourcesMockMvc.perform(get("/api/sys-role-resources/{id}", sysRoleResources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysRoleResources.getId().intValue()))
            .andExpect(jsonPath("$.roleId").value(DEFAULT_ROLE_ID))
            .andExpect(jsonPath("$.resourcesId").value(DEFAULT_RESOURCES_ID))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSysRoleResources() throws Exception {
        // Get the sysRoleResources
        restSysRoleResourcesMockMvc.perform(get("/api/sys-role-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysRoleResources() throws Exception {
        // Initialize the database
        sysRoleResourcesRepository.saveAndFlush(sysRoleResources);

        int databaseSizeBeforeUpdate = sysRoleResourcesRepository.findAll().size();

        // Update the sysRoleResources
        SysRoleResources updatedSysRoleResources = sysRoleResourcesRepository.findById(sysRoleResources.getId()).get();
        // Disconnect from session so that the updates on updatedSysRoleResources are not directly saved in db
        em.detach(updatedSysRoleResources);
        updatedSysRoleResources
            .roleId(UPDATED_ROLE_ID)
            .resourcesId(UPDATED_RESOURCES_ID)
            .createTime(UPDATED_CREATE_TIME)
            .updateTime(UPDATED_UPDATE_TIME);

        restSysRoleResourcesMockMvc.perform(put("/api/sys-role-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSysRoleResources)))
            .andExpect(status().isOk());

        // Validate the SysRoleResources in the database
        List<SysRoleResources> sysRoleResourcesList = sysRoleResourcesRepository.findAll();
        assertThat(sysRoleResourcesList).hasSize(databaseSizeBeforeUpdate);
        SysRoleResources testSysRoleResources = sysRoleResourcesList.get(sysRoleResourcesList.size() - 1);
        assertThat(testSysRoleResources.getRoleId()).isEqualTo(UPDATED_ROLE_ID);
        assertThat(testSysRoleResources.getResourcesId()).isEqualTo(UPDATED_RESOURCES_ID);
        assertThat(testSysRoleResources.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testSysRoleResources.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSysRoleResources() throws Exception {
        int databaseSizeBeforeUpdate = sysRoleResourcesRepository.findAll().size();

        // Create the SysRoleResources

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysRoleResourcesMockMvc.perform(put("/api/sys-role-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysRoleResources)))
            .andExpect(status().isBadRequest());

        // Validate the SysRoleResources in the database
        List<SysRoleResources> sysRoleResourcesList = sysRoleResourcesRepository.findAll();
        assertThat(sysRoleResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysRoleResources() throws Exception {
        // Initialize the database
        sysRoleResourcesRepository.saveAndFlush(sysRoleResources);

        int databaseSizeBeforeDelete = sysRoleResourcesRepository.findAll().size();

        // Delete the sysRoleResources
        restSysRoleResourcesMockMvc.perform(delete("/api/sys-role-resources/{id}", sysRoleResources.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysRoleResources> sysRoleResourcesList = sysRoleResourcesRepository.findAll();
        assertThat(sysRoleResourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysRoleResources.class);
        SysRoleResources sysRoleResources1 = new SysRoleResources();
        sysRoleResources1.setId(1L);
        SysRoleResources sysRoleResources2 = new SysRoleResources();
        sysRoleResources2.setId(sysRoleResources1.getId());
        assertThat(sysRoleResources1).isEqualTo(sysRoleResources2);
        sysRoleResources2.setId(2L);
        assertThat(sysRoleResources1).isNotEqualTo(sysRoleResources2);
        sysRoleResources1.setId(null);
        assertThat(sysRoleResources1).isNotEqualTo(sysRoleResources2);
    }
}
