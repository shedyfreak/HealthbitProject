package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.persistence.EntityManager;
import ch.bfh.ti.sed.pm.model.*;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import ch.bfh.ti.sed.pm.persistence.EntityManageable;

import javax.persistence.Query;

public class PatientControllerTest {
	private EntityManageable em;

	private PatientController patientController;
	private DoctorController doctorController;

	Doctor doc;

	@BeforeEach
	public void setUp() {
		em = EntityManager.getInstance();
		cleanup();
		patientController = new PatientController(em);
		doctorController = new DoctorController(em);
		doc = new Doctor(3L, "Heart", "Doctor", "azerty", "doctor@example.com");
	}

	@AfterEach
	public void afterEach() {
		// Make sure resources are released
		em.close();
	}

	public void cleanup() {
		em.getTransaction().begin();
		Query query = em.createQuery("delete from Patient");
		query.executeUpdate();
		em.getTransaction().commit();
	}

	@Test
	public void testAddPatient() throws Exception {
		patientController.addPatient("bert", "second", 1L, new Date(251L), doc);
		Query q = em.createQuery("select p from Patient p");
		assertEquals(q.getResultList().size(), 1);
		Patient patientInDb = (Patient) q.getResultList().iterator().next();
		assertEquals(patientInDb.getAhvNum(), 1L);
		assertEquals(patientInDb.getName(), "bert");
		assertEquals(patientInDb.getSurname(), "second");
		assertEquals(patientInDb.getBirthDate(), new Date(251L));
		assertEquals(patientInDb.getAssignedDoctor().getAhvNum(), doc.getAhvNum());
	}
}