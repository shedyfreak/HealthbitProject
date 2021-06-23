package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.model.Doctor;
import ch.bfh.ti.sed.pm.model.Patient;

import ch.bfh.ti.sed.pm.persistence.EntityManageable;
import java.util.Date;
import java.util.List;

/**
 *
 * A Controller class to handle add patient event
 *
 */
public class PatientController {
	private EntityManageable em;

	public PatientController(EntityManageable em) {
		this.em = em;
	}

	/**
	 * addPatient called to add a Patient to the Database with the entity Manager
	 *
	 * @param name      the patients name
	 * @param surname   the patients surname
	 * @param ahv       the patients AHV number
	 * @param birthDate the patients date of birth
	 * @param doc       the doctor which assigns the patient to himself
	 */
	public boolean addPatient(String name, String surname, long ahv, Date birthDate, Doctor doc) {
		// Create the Patient in the Entity Manager
		System.out.println(ahv);
		if (em.find(Patient.class, ahv) == null) {
			// Create the Patient in the doctor list
			Patient patient = doc.addPatient(name, surname, ahv, birthDate);
			em.getTransaction().begin();
			em.persist(patient);
			em.getTransaction().commit();
			return true;
		} else {
			// TODO: Refactor into an exception handled by the GUI
			// System.out.println(Constants.ERROR_DUPLICATE_PATIENT);
			return false;
		}
	}

	public Patient getPatientByAhvNum(long ahvNum) {
		// TODO Auto-generated method stub
		return em.find(Patient.class, ahvNum);
	}
}
