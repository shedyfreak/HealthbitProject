package ch.bfh.ti.sed.pm.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import ch.bfh.ti.sed.pm.model.Device;
import ch.bfh.ti.sed.pm.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.bfh.ti.sed.pm.model.Doctor;
import ch.bfh.ti.sed.pm.persistence.EntityManageable;
import ch.bfh.ti.sed.pm.persistence.EntityManager;

class ObservationPeriodControllerTest {
	private static final String PERSISTENCE_UNIT_NAME = "healthbit";
	private static final Date VALID_START_DATE = new Date(2019,1,1);
	private static final Date VALID_END_DATE = new Date(2019,2,2);
	private static final int VALID_FREQUENCY = 5;
	private static final long VALID_AHV = 90L;
	private static final long INVALID_AHV = 15L;
	private PatientController patientController;
	private EntityManageable em;
	private Doctor doc = new Doctor();
	private ObservationPeriodController obsController;
	private DoctorController doctorController;
	private DeviceController deviceController;

	@BeforeEach
	public void beforeEach() {
		em = EntityManager.getInstance();
		obsController = new ObservationPeriodController(em);
		patientController =new PatientController(em);
		doctorController = new DoctorController(em);
		deviceController = new DeviceController(em);
	}

	@AfterEach
	public void afterEach() {
		// Make sure resources are released
		em.close();
	}

	@Test
	void observationsAddValidtest() {
		String storageRoom = "N633";
		doctorController.addDoctor(663L, "chabacha", "Jackson", "mypassword", "forme335@gmail.com");
		deviceController.addDevice(storageRoom);

		Doctor doctor = em.find(Doctor.class, 663L);
		Device device = em.createQuery("SELECT d FROM Device d WHERE d.storageRoom LIKE :storageRoom", Device.class)
				.setParameter("storageRoom", storageRoom)
				.getResultList()
				.get(0);

		patientController.addPatient( "Heart", "Doctor", VALID_AHV, new Date(2019, 1, 1), doctor);
		obsController.startObservationPeriod(VALID_START_DATE, VALID_FREQUENCY, VALID_AHV, device.getDeviceId());

		Patient p = em.find(Patient.class, VALID_AHV);
		System.out.println(p.getCurrentObservation().getStartDate());
	}
	@Test
	void ObservationsAddInvalidtest() {
		//obsController.StartObservationPeriod(VALID_START_DATE, VALID_END_DATE, VALID_FREQUENCY, INVALID_AHV);
		assertTrue(true);
	}



}
