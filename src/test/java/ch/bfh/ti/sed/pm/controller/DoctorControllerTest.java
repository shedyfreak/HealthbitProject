package ch.bfh.ti.sed.pm.controller;

import static org.junit.jupiter.api.Assertions.*;

import ch.bfh.ti.sed.pm.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.bfh.ti.sed.pm.model.Doctor;
import ch.bfh.ti.sed.pm.persistence.EntityManageable;

class DoctorControllerTest {
	private static final String PERSISTENCE_UNIT_NAME = "healthbit";
	private static final String VALID_EMAIL = "m.j@sacredhearthospital.com";
	private static final String VALID_PASSWORD = "safepassword";

	private EntityManageable em;
	private DoctorController doctorController;

	@BeforeEach
	public void beforeEach() {
		em = EntityManager.getInstance();
		doctorController = new DoctorController(em);
		doctorController.addDoctor(663L, "chabacha", "Jackson", VALID_PASSWORD, "forme335@gmail.com");
	}

	@AfterEach
	public void afterEach() {
		// Make sure resources are released
		em.close();
	}

	@Test
	void testAddDoctor() {
	   assertNotNull(em.find(Doctor.class, 663L));
	}

}
