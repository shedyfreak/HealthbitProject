package ch.bfh.ti.sed.pm.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import org.junit.jupiter.api.Test;

class MeasurmentTest {

	@Test
	void measurmentAndObservationPeriodTest() {
		String storageRoom = "666";
		Device device = new Device(storageRoom);
		ObservationPeriod obs = new ObservationPeriod(new Date(2019, 1, 1), 3, device);
		Measurement m = new Measurement((float) 20.5, new Timestamp(30L), obs);
		assertEquals(obs.getStartDate(), new Date(2019, 1, 1));
		obs.setEndDate(new Date(2019, 5, 5));
		assertEquals(obs.getEndDate(), new Date(2019, 5, 5));
		assertEquals(m.getTemperature(), 20.5, 0.1);
		assertEquals(m.getTimestamp(), new Timestamp(30L));
		m.setTimestamp(new Timestamp(20L));
		m.setId("1");
		assertEquals(m.getId(), "1");
		m.setObservationPeriod(new ObservationPeriod());
		m.setTemperature(20);
		obs.addMeasurement((float) 20.5, new Timestamp(30L), obs);
		assertEquals(obs.getFrequency(), obs.getFrequency());
		obs.setId(1L);
		assertEquals(obs.getId(), 1L);
		Patient me = new Patient();
		obs.addMeasurement((float) 20.5, new Timestamp(30L), obs);
		assertNotNull(obs.getMeasurements());
		obs.setPatient(me);
		assertEquals(obs.getPatient(), me);
		obs.setFrequency(3);
		obs.setEndDate(new Date(2019, 1, 1));
		obs.setStartDate(new Date(2019, 1, 1));
		assertEquals(storageRoom, obs.getDevice().getStorageRoom(), storageRoom);
	}
}
