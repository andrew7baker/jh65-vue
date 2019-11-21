package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhvueApp;
import com.mycompany.myapp.domain.SysResources;
import com.mycompany.myapp.repository.SysResourcesRepository;
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
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SysResourcesResource} REST controller.
 */
@SpringBootTest(classes = JhvueApp.class)
public class SysResourcesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PERMISSION = "AAAAAAAAAA";
    private static final String UPDATED_PERMISSION = "BBBBBBBBBB";

    private static final Long DEFAULT_PARENT_ID = 1L;
    private static final Long UPDATED_PARENT_ID = 2L;

    private static final Integer DEFAULT_SORT = 1;
    private static final Integer UPDATED_SORT = 2;

    @Autowired
    private SysResourcesRepository sysResourcesRepository;

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

    private MockMvc restSysResourcesMockMvc;

    private SysResources sysResources;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SysResourcesResource sysResourcesResource = new SysResourcesResource(sysResourcesRepository);
        this.restSysResourcesMockMvc = MockMvcBuilders.standaloneSetup(sysResourcesResource)
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
    public static SysResources createEntity(EntityManager em) {
        SysResources sysResources = new SysResources()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .url(DEFAULT_URL)
            .permission(DEFAULT_PERMISSION)
            .parentId(DEFAULT_PARENT_ID)
            .sort(DEFAULT_SORT);
        return sysResources;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysResources createUpdatedEntity(EntityManager em) {
        SysResources sysResources = new SysResources()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .permission(UPDATED_PERMISSION)
            .parentId(UPDATED_PARENT_ID)
            .sort(UPDATED_SORT);
        return sysResources;
    }

    @BeforeEach
    public void initTest() {
        sysResources = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysResources() throws Exception {
        int databaseSizeBeforeCreate = sysResourcesRepository.findAll().size();

        // Create the SysResources
        restSysResourcesMockMvc.perform(post("/api/sys-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysResources)))
            .andExpect(status().isCreated());

        // Validate the SysResources in the database
        List<SysResources> sysResourcesList = sysResourcesRepository.findAll();
        assertThat(sysResourcesList).hasSize(databaseSizeBeforeCreate + 1);
        SysResources testSysResources = sysResourcesList.get(sysResourcesList.size() - 1);
        assertThat(testSysResources.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSysResources.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSysResources.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testSysResources.getPermission()).isEqualTo(DEFAULT_PERMISSION);
        assertThat(testSysResources.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testSysResources.getSort()).isEqualTo(DEFAULT_SORT);
    }

    @Test
    @Transactional
    public void createSysResourcesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysResourcesRepository.findAll().size();

        // Create the SysResources with an existing ID
        sysResources.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysResourcesMockMvc.perform(post("/api/sys-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysResources)))
            .andExpect(status().isBadRequest());

        // Validate the SysResources in the database
        List<SysResources> sysResourcesList = sysResourcesRepository.findAll();
        assertThat(sysResourcesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysResources() throws Exception {
        // Initialize the database
        sysResourcesRepository.saveAndFlush(sysResources);

        // Get all the sysResourcesList
        restSysResourcesMockMvc.perform(get("/api/sys-resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysResources.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].permission").value(hasItem(DEFAULT_PERMISSION)))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].sort").value(hasItem(DEFAULT_SORT)));
    }
    
    @Test
    @Transactional
    public void getSysResources() throws Exception {
        // Initialize the database
        sysResourcesRepository.saveAndFlush(sysResources);

        // Get the sysResources
        restSysResourcesMockMvc.perform(get("/api/sys-resources/{id}", sysResources.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sysResources.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.permission").value(DEFAULT_PERMISSION))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID.intValue()))
            .andExpect(jsonPath("$.sort").value(DEFAULT_SORT));
    }

    @Test
    @Transactional
    public void getNonExistingSysResources() throws Exception {
        // Get the sysResources
        restSysResourcesMockMvc.perform(get("/api/sys-resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysResources() throws Exception {
        // Initialize the database
        sysResourcesRepository.saveAndFlush(sysResources);

        int databaseSizeBeforeUpdate = sysResourcesRepository.findAll().size();

        // Update the sysResources
        SysResources updatedSysResources = sysResourcesRepository.findById(sysResources.getId()).get();
        // Disconnect from session so that the updates on updatedSysResources are not directly saved in db
        em.detach(updatedSysResources);
        updatedSysResources
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .url(UPDATED_URL)
            .permission(UPDATED_PERMISSION)
            .parentId(UPDATED_PARENT_ID)
            .sort(UPDATED_SORT);

        restSysResourcesMockMvc.perform(put("/api/sys-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSysResources)))
            .andExpect(status().isOk());

        // Validate the SysResources in the database
        List<SysResources> sysResourcesList = sysResourcesRepository.findAll();
        assertThat(sysResourcesList).hasSize(databaseSizeBeforeUpdate);
        SysResources testSysResources = sysResourcesList.get(sysResourcesList.size() - 1);
        assertThat(testSysResources.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSysResources.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSysResources.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testSysResources.getPermission()).isEqualTo(UPDATED_PERMISSION);
        assertThat(testSysResources.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testSysResources.getSort()).isEqualTo(UPDATED_SORT);
    }

    @Test
    @Transactional
    public void updateNonExistingSysResources() throws Exception {
        int databaseSizeBeforeUpdate = sysResourcesRepository.findAll().size();

        // Create the SysResources

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysResourcesMockMvc.perform(put("/api/sys-resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sysResources)))
            .andExpect(status().isBadRequest());

        // Validate the SysResources in the database
        List<SysResources> sysResourcesList = sysResourcesRepository.findAll();
        assertThat(sysResourcesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysResources() throws Exception {
        // Initialize the database
        sysResourcesRepository.saveAndFlush(sysResources);

        int databaseSizeBeforeDelete = sysResourcesRepository.findAll().size();

        // Delete the sysResources
        restSysResourcesMockMvc.perform(delete("/api/sys-resources/{id}", sysResources.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysResources> sysResourcesList = sysResourcesRepository.findAll();
        assertThat(sysResourcesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysResources.class);
        SysResources sysResources1 = new SysResources();
        sysResources1.setId(1L);
        SysResources sysResources2 = new SysResources();
        sysResources2.setId(sysResources1.getId());
        assertThat(sysResources1).isEqualTo(sysResources2);
        sysResources2.setId(2L);
        assertThat(sysResources1).isNotEqualTo(sysResources2);
        sysResources1.setId(null);
        assertThat(sysResources1).isNotEqualTo(sysResources2);
    }
}
