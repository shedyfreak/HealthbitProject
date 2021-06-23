package ch.bfh.ti.sed.pm.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Represents measurement observed in an exact timestamp
 */
@Entity
public class Measurement {
    @Id
    private String id;
    //Data to Observe in timestamp
    private float temperature;
    //Time in which temperature was recorded
    private Timestamp timestamp;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private ObservationPeriod observationPeriod;

    /**
     * Class constructor
     * @param temperature recorded
     * @param timestamp on which temperature
     * @param observationPeriod of measurment
     */
    public Measurement(float temperature, Timestamp timestamp, ObservationPeriod observationPeriod) {
        this.temperature = temperature;
        this.timestamp = timestamp;
        this.observationPeriod = observationPeriod;
        this.id = timestamp.toString() + " " + observationPeriod.toString();
    }

    /**
     * No-args constructor
     */
    public Measurement() {

    }

    /**
     * 
     * @return a float representing temperature recorded
     */
    public float getTemperature() {
        return temperature;
    }

    /**
     * 
     * @return a float timestamp in which a certain measurement is recorded
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Getter for id
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for observationPeriod
     * @return The observationPeriod
     */
    public ObservationPeriod getObservationPeriod() {
        return observationPeriod;
    }

    /**
     * sets a timestamp for a measurement
     * @param timestamp time of record
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * sets temperature for a measurement
     * @param temperature temperature recorded
     */
    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    /**
     * Setter for id
     * @param id Unique identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter for observationPeriod
     * @param observationPeriod The observationPeriod
     */
    public void setObservationPeriod(ObservationPeriod observationPeriod) {
        this.observationPeriod = observationPeriod;
    }
}