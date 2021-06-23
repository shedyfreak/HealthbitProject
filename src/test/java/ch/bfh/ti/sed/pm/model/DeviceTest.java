package ch.bfh.ti.sed.pm.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DeviceTest {

	@Test
	void deviceTest() {
		Device aDevice = new Device("666");
		assertEquals(aDevice.getStorageRoom(), "666");
		assertFalse(aDevice.isBusy());
		aDevice.changeDeviceState();
		assertTrue(aDevice.isBusy());
		aDevice.setStorageRoom("777");
		assertEquals(aDevice.getStorageRoom(), "777");
	}

}
