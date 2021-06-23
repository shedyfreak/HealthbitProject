package ch.bfh.ti.sed.pm.model;

import ch.bfh.ti.sed.pm.helper.Constants;
import com.vaadin.flow.component.datepicker.DatePicker;

import javax.persistence.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A patient Class that will represent patients and their informations this
 * class is also an entity in Database
 *
 */

@Entity
public class Patient {
	@Id
	private long ahvNum;
	private String name;
	private String surname;
	private Date birthDate;

	@OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
	private Set<ObservationPeriod> observationPeriods;

	@OneToOne(mappedBy = "patient", cascade = CascadeType.PERSIST)
	private ObservationPeriod currentObservation;

	@ManyToOne(cascade = CascadeType.PERSIST)
	private Doctor assignedDoctor;

	/**
	 * Class Constructor throws an exception if patient already exists
	 * 
	 * @param ahvNum    Patient's ahvNum
	 * @param name      Patient's name
	 * @param surname   Patient's surnmae
	 * @param birthDate Patient's Birthdate
	 */
	public Patient(long ahvNum, String name, String surname, Date birthDate) {
		this.ahvNum = ahvNum;
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.observationPeriods = new HashSet<>();
	}

	/**
	 * No-args Constructor
	 */
	public Patient() {

	}

	/**
	 * Getter for ahvNum
	 * 
	 * @return the AHV number
	 */
	public long getAhvNum() {
		return ahvNum;
	}

	/**
	 * Getter for name
	 * 
	 * @return a String representing the patient name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for surname
	 * 
	 * @return a String representing Patient surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Getter for birthdate
	 * 
	 * @return a Date for Patient birth day
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Getter for assignedDoctor
	 * 
	 * @return assignedDoctor
	 */
	public Doctor getAssignedDoctor() {
		return assignedDoctor;
	}

	public ObservationPeriod getCurrentObservation() {
		return currentObservation;
	}

	/**
	 * Setter for observationPeriods
	 * 
	 * @return a Set Of Observation Periods of the Patient
	 */
	public Set<ObservationPeriod> getObservationPeriods() {
		return observationPeriods;
	}

	/**
	 * Setter for ahvNum
	 * 
	 * @param ahvNum an int to set ahvNum for patient
	 */
	public void setAhvNum(int ahvNum) {
		this.ahvNum = ahvNum;
	}

	/**
	 * Setter for ahvNum
	 * 
	 * @param name to set name to patient
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for surname
	 * 
	 * @param surname to set a Patient surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Setter for Birthday
	 * 
	 * @param birthDate to set a Patient birth day
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Setter for Doctor
	 * 
	 * @param doctor Doctor assigned to patient
	 */
	public void setAssignedDoctor(Doctor doctor) {
		assignedDoctor = doctor;
	}

	/**
	 *
	 * @param newDoctor The method to assign a Patient to a doctor.
	 * @throws Exception Patient have already a doctor The Patient is added to the
	 *                   assignedPatients List in the new Doctor.
	 */
	public void isTreatedBy(Doctor newDoctor) throws Exception {
		assignedDoctor = newDoctor;
		newDoctor.addPatient(this.name, this.surname, this.ahvNum, this.birthDate);
	}

	/**
	 * if patient is not under observation already , an observation is added as a
	 * current observation otherwise nothing happen
	 * 
	 * @param startDate date of beginning
	 * @param frequency frequency of measurement
	 * @return true if the observation is added , false if not
	 */
	public ObservationPeriod startObservationPeriod(Date startDate, int frequency, Device device)
			throws IllegalStateException {
		if (isUnderObservation()) {
			throw new IllegalStateException(Constants.ERROR_PATIENT_ALREADY_UNDER_OBSERVATION);
		} else if (device.isBusy()) {
			throw new IllegalStateException(Constants.ERROR_DEVICE_IS_BUSY);
		} else {
			this.currentObservation = new ObservationPeriod(startDate, frequency, device);
			device.changeDeviceState();
			observationPeriods.add(currentObservation);

		}
		return this.currentObservation;
	}

	/**
	 * end an observationPeriod sets current observation period to null adding the
	 * ended observation to the set of observationPeriods
	 */
	public void endObservationPeriod() throws IllegalStateException {
		if (!isUnderObservation()) {
			throw new IllegalStateException(Constants.ERROR_PATIENT_NOT_UNDER_OBSERVATION);
		} else {
			LocalDateTime now = LocalDateTime.now();
			int year = now.getYear();
			int month = now.getMonthValue();
			int day = now.getDayOfMonth();
			LocalDate localDate = LocalDate.of(year, month, day);
			observationPeriods.remove(currentObservation);
			currentObservation.setEndDate(convertToDateViaSqlDate(localDate));
			observationPeriods.add(currentObservation);
			currentObservation = null;
		}
	}

	/**
	 * Method to convert a localdate to Date
	 * It is needed because Date and a cast from Localdate have not the same format
	 * @param dateToConvert the Localdate to be converted
	 * @return the same date as Date
	 */
	private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
		return java.sql.Date.valueOf(dateToConvert);
	}

	/**
	 * Checks if the patient is under observation
	 * @return true or false if patient is under observation
	 */
	public boolean isUnderObservation() {
		return currentObservation != null;
	}

	/**
	 *
	 * @return Returns the String of the patiet
	 */
	@Override
	public String toString() {
		return "work";
	}

}