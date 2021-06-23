package ch.bfh.ti.sed.pm.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ObservationPeriodTest {

    @Test
    void observationPeriodTest() {
        Device d = new Device("666");
        Date today = new Date();
        Patient p = new Patient();
        ObservationPeriod obs = new ObservationPeriod(today, 10, d);
        assertEquals(obs.getStartDate(), today);
        assertEquals(obs.getFrequency(), 10);
        assertEquals(obs.getDevice(), d);
        obs.setPatient(p);
        assertEquals(obs.getPatient(), p);
    }
}
