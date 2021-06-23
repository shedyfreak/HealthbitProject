package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.model.Measurement;
import ch.bfh.ti.sed.pm.model.ObservationPeriod;
import ch.bfh.ti.sed.pm.persistence.EntityManageable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * A controller to handle measurement events
 */
public class MeasurementController {

    private EntityManageable em;

    public MeasurementController(EntityManageable em) {
        this.em = em;
    }

    /**
     * Method to add a measurement to an observation period
     * The measurement is persisted by the entitiy manager
     *
     * @param temperature the temprature of the patient
     * @param timestamp when the measurement was taken
     * @param observationPeriod to which observation period the measurment belongs
     */
    public void addMeasurement(float temperature, Timestamp timestamp, ObservationPeriod observationPeriod) {
        // Create the Patient in the Entity Manager
        //Create the Patient in the doctor list
        Measurement m = observationPeriod.addMeasurement(temperature, timestamp, observationPeriod);
        em.getTransaction().begin();
        em.persist(m);
        em.getTransaction().commit();
    }
}
