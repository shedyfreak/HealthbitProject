package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.model.Device;
import ch.bfh.ti.sed.pm.model.Measurement;
import ch.bfh.ti.sed.pm.model.ObservationPeriod;
import ch.bfh.ti.sed.pm.persistence.EntityManageable;
import ch.bfh.ti.sed.pm.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.util.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MeasurementControllerTest {

    private EntityManageable em;

    private MeasurementController measurementController;
    ObservationPeriod obs;
    Measurement measurement;
    float temp;
    Timestamp stamp;

    @BeforeEach
    public void setUp() {
        em = EntityManager.getInstance();
        measurementController = new MeasurementController(em);
        obs = new ObservationPeriod(new Date(1), 10, new Device());
        temp = 1;
        stamp = new Timestamp(1);
    }

    @AfterEach
    public void afterEach() {
        // Make sure resources are released
        em.close();
    }

    @Test
    public void testAddMeasurement(){
        measurementController.addMeasurement(1, new Timestamp(1), obs);
        assertTrue(obs.getMeasurements().iterator().hasNext());
        assertEquals(obs.getMeasurements().iterator().next().getId(), stamp.toString() + " " + obs.toString());
        Query q = em.createQuery("select m from Measurement m");
        assertEquals(q.getResultList().size(), 1);
        Measurement mDB = (Measurement) q.getResultList().get(0);
        assertEquals(mDB.getTemperature(), temp);
        assertEquals(mDB.getTimestamp(), stamp);
        assertEquals(mDB.getObservationPeriod(), obs);
    }
}
