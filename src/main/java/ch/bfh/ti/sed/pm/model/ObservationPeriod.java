package ch.bfh.ti.sed.pm.model;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Observation period Class
 */
@Entity
public class ObservationPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
	// For observation Period we have a set of measurements
    @OneToMany(mappedBy = "observationPeriod", cascade = CascadeType.PERSIST)
    private Set<Measurement> measurements;
    //Start and End Date of observation Period
    private Date startDate;
    private Date endDate;
    //frequency is the time range to record a measurements
    private int frequency;

    @ManyToOne
    private Patient patient;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Device device;

    /**
     * Class constructor
     * @param startDate date of start
     * @param frequency	frequency of measurement recording
     */
    public ObservationPeriod(Date startDate, int frequency, Device device) {
        this.startDate = startDate;
        this.frequency = frequency;
        this.device = device;
        measurements = new HashSet<>();
    }

    /**
     * No-args constructor
     */
    public ObservationPeriod() {

    }

    /**
     * Getter for startDate
     * @return a Date Format for startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Getter for endDate
     * @return a Date for a measurement endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Getter for frequency
     * @return The frequency
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Getter for id
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Getter for patient
     * @return the patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Setter for id
     * @param id Unique identifier
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * set a startDate for ObservationPeriod
     * @param startDate Date of start
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Setter for endDate
     * @param endDate end date of the observations
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Setter for frequency
     * @param frequency The frequency
     */
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    /**
     * Setter for patient
     * @param patient The patient
     */
    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    /**
     * Adds a measurement to the observation period
     * @param temperature Temperature of the patient
     * @param timestamp timestamp when the measurement was taken
     * @param observationPeriod Temperature of the patient
     * @return measurement that has been added
     */
    public Measurement addMeasurement(float temperature, Timestamp timestamp,ObservationPeriod observationPeriod) {
        Measurement measurement = new Measurement(temperature, timestamp, observationPeriod);
        measurements.add(measurement);
        return measurement;
    }

    /**
     * Returns an iterator for the measurements
     * @return an iterator on the measurments
     */
    public Set<Measurement> getMeasurements() {
        return measurements;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    @Override
	public String toString(){
    	return "hey I work";
    }
}
