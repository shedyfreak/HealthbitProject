package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.helper.Constants;
import ch.bfh.ti.sed.pm.model.Doctor;

import ch.bfh.ti.sed.pm.persistence.EntityManageable;

public class DoctorController {
	private EntityManageable em;

	public DoctorController(EntityManageable em) {
		this.em = em;
	}

	/**
	 * TODO: Needed for Doctor registration and application testing!
	 * 
	 * @param ahvNum   Ahv num of doctor
	 * @param name     name of doctor
	 * @param surname  surname of doctor
	 * @param password password of doctor
	 * @param email    email of doctor
	 */
	public void addDoctor(long ahvNum, String name, String surname, String password, String email) {
		if (em.find(Doctor.class, ahvNum) == null) {
			em.getTransaction().begin();
			em.persist(new Doctor(ahvNum, name, surname, password, email));
			em.getTransaction().commit();
		} else {
			// TODO: Refactor into an exception handled by the GUI
			System.out.println(Constants.ERROR_DUPLICATE_DOCTOR);
		}
	}

	public Doctor getAuthenticatedDoctor(long ahvNum) {
		return em.find(Doctor.class, ahvNum);
	}
}