package ch.bfh.ti.sed.pm.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PatientTest {

	Patient alfa;
	Patient bert;
	@BeforeEach
	public void init(){
		alfa = new Patient(123L, "alfa", "first", new Date(250L));
		bert = new Patient(1234L, "bert", "second", new Date(251L));
	}

	@Test
	public void PatientFirstTest() {
		try {
			assertNotNull(alfa);
			assertEquals(123L, alfa.getAhvNum());
			Device myDev = new Device("20");
		} catch (Exception e) {
			System.out.println("Patient Null");
		}

	}

	@Test
	void AddPatientTest() {
		try {
			Doctor doc = new Doctor(25L, "Heart", "Doctor", "azerty", "doctor@example.com");
			Patient alfaReturn = doc.addPatient( "alfa", "first",123L, new Date(250L)); //add alfa
			Patient bertReturn = doc.addPatient( "bert", "second",1234L, new Date(251L));
			assertNotNull(alfaReturn);
			Iterator iter = doc.getAssignedPatients().iterator();
			Set<Patient> assignedPatients = doc.getAssignedPatients();
			assertTrue(assignedPatients.contains(alfaReturn));
			assertTrue(assignedPatients.contains(bertReturn));
			assertEquals(doc, bertReturn.getAssignedDoctor());
			assertEquals(doc, alfaReturn.getAssignedDoctor());
			alfaReturn.isTreatedBy(doc);
			alfaReturn.setAhvNum(1);
			alfaReturn.setBirthDate(new Date(20L));
			alfaReturn.setSurname("none");
			alfaReturn.setName("yes");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testRelationShipToDoctor() {
		try {
			Patient bert = new Patient(1234L, "bert", "second", new Date(251L));
			Doctor doc = new Doctor(25L, "Heart", "Doctor", "azerty", "doctor@example.com");
			Patient bertReturn = doc.addPatient("bert", "second",1234L, new Date(251L));
			assertEquals(doc, bertReturn.getAssignedDoctor());
			assertEquals(bertReturn, doc.getAssignedPatients().iterator().next());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Test
	public void testRelationToObservationPeriod() {
		try {
			Patient bert = new Patient(1234L, "bert", "second", new Date(251L));
			bert.startObservationPeriod(new Date(30L), 10, new Device());

			assertNotNull(bert.getObservationPeriods());
			assertNotNull(bert.getCurrentObservation());
			assertEquals(bert.getCurrentObservation(), bert.getObservationPeriods().iterator().next());

			bert.endObservationPeriod();
			assertNull(bert.getCurrentObservation());
		} catch (Exception e) {
			System.out.println(e);
		}

	}
}
