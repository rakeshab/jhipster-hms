package com.hospital.management.web.rest;

import com.hospital.management.HospitalManagementApp;

import com.hospital.management.domain.Doctor;
import com.hospital.management.repository.DoctorRepository;
import com.hospital.management.service.DoctorService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DoctorResource REST controller.
 *
 * @see DoctorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HospitalManagementApp.class)
public class DoctorResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_QUALIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_QUALIFICATION = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIALIZATION = "AAAAAAAAAA";
    private static final String UPDATED_SPECIALIZATION = "BBBBBBBBBB";

    private static final Long DEFAULT_PHONENUMBER = 1L;
    private static final Long UPDATED_PHONENUMBER = 2L;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    @Inject
    private DoctorRepository doctorRepository;

    @Inject
    private DoctorService doctorService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DoctorResource doctorResource = new DoctorResource();
        ReflectionTestUtils.setField(doctorResource, "doctorService", doctorService);
        this.restDoctorMockMvc = MockMvcBuilders.standaloneSetup(doctorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity(EntityManager em) {
        Doctor doctor = new Doctor()
                .name(DEFAULT_NAME)
                .age(DEFAULT_AGE)
                .qualification(DEFAULT_QUALIFICATION)
                .specialization(DEFAULT_SPECIALIZATION)
                .phonenumber(DEFAULT_PHONENUMBER)
                .email(DEFAULT_EMAIL)
                .gender(DEFAULT_GENDER);
        return doctor;
    }

    @Before
    public void initTest() {
        doctor = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctor() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // Create the Doctor

        restDoctorMockMvc.perform(post("/api/doctors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(doctor)))
                .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctors = doctorRepository.findAll();
        assertThat(doctors).hasSize(databaseSizeBeforeCreate + 1);
        Doctor testDoctor = doctors.get(doctors.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctor.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDoctor.getQualification()).isEqualTo(DEFAULT_QUALIFICATION);
        assertThat(testDoctor.getSpecialization()).isEqualTo(DEFAULT_SPECIALIZATION);
        assertThat(testDoctor.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testDoctor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDoctor.getGender()).isEqualTo(DEFAULT_GENDER);
    }

    @Test
    @Transactional
    public void getAllDoctors() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctors
        restDoctorMockMvc.perform(get("/api/doctors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].qualification").value(hasItem(DEFAULT_QUALIFICATION.toString())))
                .andExpect(jsonPath("$.[*].specialization").value(hasItem(DEFAULT_SPECIALIZATION.toString())))
                .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER.intValue())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())));
    }

    @Test
    @Transactional
    public void getDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.qualification").value(DEFAULT_QUALIFICATION.toString()))
            .andExpect(jsonPath("$.specialization").value(DEFAULT_SPECIALIZATION.toString()))
            .andExpect(jsonPath("$.phonenumber").value(DEFAULT_PHONENUMBER.intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get("/api/doctors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctor() throws Exception {
        // Initialize the database
        doctorService.save(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findOne(doctor.getId());
        updatedDoctor
                .name(UPDATED_NAME)
                .age(UPDATED_AGE)
                .qualification(UPDATED_QUALIFICATION)
                .specialization(UPDATED_SPECIALIZATION)
                .phonenumber(UPDATED_PHONENUMBER)
                .email(UPDATED_EMAIL)
                .gender(UPDATED_GENDER);

        restDoctorMockMvc.perform(put("/api/doctors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDoctor)))
                .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctors = doctorRepository.findAll();
        assertThat(doctors).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctors.get(doctors.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctor.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDoctor.getQualification()).isEqualTo(UPDATED_QUALIFICATION);
        assertThat(testDoctor.getSpecialization()).isEqualTo(UPDATED_SPECIALIZATION);
        assertThat(testDoctor.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testDoctor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDoctor.getGender()).isEqualTo(UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void deleteDoctor() throws Exception {
        // Initialize the database
        doctorService.save(doctor);

        int databaseSizeBeforeDelete = doctorRepository.findAll().size();

        // Get the doctor
        restDoctorMockMvc.perform(delete("/api/doctors/{id}", doctor.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Doctor> doctors = doctorRepository.findAll();
        assertThat(doctors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
