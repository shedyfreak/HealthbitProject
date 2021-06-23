package ch.bfh.ti.sed.pm.controller;

import java.util.Date;
import ch.bfh.ti.sed.pm.helper.Constants;
import ch.bfh.ti.sed.pm.model.Device;
import ch.bfh.ti.sed.pm.model.ObservationPeriod;
import ch.bfh.ti.sed.pm.model.Patient;
import ch.bfh.ti.sed.pm.persistence.EntityManageable;

public class ObservationPeriodController {
	private EntityManageable em;

	/**
	 * 
	 * @param em EntityManager for managing the persistence
	 */
	public ObservationPeriodController(EntityManageable em) {
		this.em = em;
	}

	/**
	 * startObservation is a method to add an observation period to a patient and
	 * persisting it
	 * 
	 * @param startDate Date of observation begin
	 * @param frequency frequency of measurement
	 * @param ahvNum    patient's ahvnum
	 */
	public boolean startObservationPeriod(Date startDate, int frequency, long ahvNum, long deviceId) {
		System.out.println(ahvNum);
		Patient currentPatient = em.find(Patient.class, ahvNum);

		Device device = em.find(Device.class, deviceId);

		if (currentPatient == null) {
			System.out.println(Constants.ERROR_INEXISTENT_PATIENT);
			return false;
		} else if (device == null) {
			System.out.println(Constants.ERROR_INEXISTENT_DEVICE);
			return false;
		} else {
			try {
				ObservationPeriod observationPeriod = currentPatient.startObservationPeriod(startDate, frequency,
						device);
				em.getTransaction().begin();
				em.persist(observationPeriod);
				em.persist(currentPatient);
				em.getTransaction().commit();
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
	}

	public boolean endObservationPeriod(long ahvNum) {
		Patient currentPatient = em.find(Patient.class, ahvNum);
		if (currentPatient == null) {
			System.out.println(Constants.ERROR_INEXISTENT_PATIENT);
			return false;
		} else if (!currentPatient.isUnderObservation()) {
			System.out.println(Constants.ERROR_PATIENT_NOT_UNDER_OBSERVATION);
			return false;
		} else {
			// If observation period is finished, the device is not busy anymore
			// TODO: Implement services encapsulating this kind of operation instead of
			// modifying objects directly like this
			// also boolean flags are a code smell and should be implemented as states using
			// the State Design Pattern
			em.getTransaction().begin();
			currentPatient.getCurrentObservation().getDevice().changeDeviceState();
			currentPatient.endObservationPeriod();
			em.getTransaction().commit();
			return true;
		}
	}
}