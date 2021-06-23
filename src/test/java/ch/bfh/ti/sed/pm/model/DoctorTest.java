package ch.bfh.ti.sed.pm.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {
	private static final long TEST_AHV_NUM = 7561234567897L;
	private static final String TEST_NAME = "Mario";
	private static final String TEST_SURNAME = "Bernasconi";
	private static final String TEST_PASSWORD = "mysecretpassword";
	private static final String TEST_EMAIL = "mario.bernasconi@twopointhospital.com";
	private Doctor d;

	@BeforeEach
	void setup() {
		d = new Doctor(TEST_AHV_NUM, TEST_NAME, TEST_SURNAME, TEST_PASSWORD, TEST_EMAIL);
	}

	/**
	 * Test the addition of a doctor to the system
	 */
	@Test
	void addDoctorTest() {
		// Password generated thanks to
		// https://passwordsgenerator.net/sha512-hash-generator/
		assertEquals(d.getAhvNum(), TEST_AHV_NUM);
		assertEquals(d.getName(), TEST_NAME);
		assertEquals(d.getSurname(), TEST_SURNAME);
		assertEquals(d.getEmail(), TEST_EMAIL);
	}

	/**
	 * Test the addition of a doctor to the system
	 */
	@Test
	void validateLoginTest() {
		try {
			assertTrue(d.validateLogin("mario.bernasconi@twopointhospital.com", TEST_PASSWORD));
		} catch (NoSuchAlgorithmException e) {
			fail();
		}
	}

	/**
	 * Test that the hashing method is actually hashing using SHA-512 and converting
	 * the hash to a hex string representation
	 */
	@Test
	public void hashTest() {
		try {
			Field field = d.getClass().getDeclaredField("password");
			field.setAccessible(true);
			Object password = field.get(d);
			// Password generated thanks to
			// https://passwordsgenerator.net/sha512-hash-generator/
			String hashedPassword = "83BEDC699569935A35BB661A352915DA70870375B7C70BE3124D5A3ECF811A6C6EDEE113B04308CA4C01A692CE125FA8BA6CDBB04272EBBCAB4F366DC806B35A";
			assertEquals(password, (String) hashedPassword);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
    public void testMakeDoctor() {
    	Doctor alfaDoc=new Doctor();
        assertNotNull(new Doctor(25,"Heart","Doctor","azerty","doctor@example.com"));
        assertEquals(
                new Doctor(25,"Heart","Doctor","azerty","doctor@example.com").getEmail(),
                "doctor@example.com");
        assertEquals(
                new Doctor(25,"Heart","Doctor","azerty","doctor@example.com").getName(),
                "Heart");
        alfaDoc.setAhvNum(2L);
        alfaDoc.setEmail("Doctor@myexample.com");
        alfaDoc.setPassword("**");
        alfaDoc.setName("hamdi");
        alfaDoc.setSurname("ha");  
        alfaDoc.setAssignedPatients(new HashSet<>());
    }

	@Test
	public void testAddPatient() {
		Doctor beta = new Doctor(25L, "Heart", "Doctor", "azerty", "doctor@example.com");
		try {
			Patient alfaReturn = beta.addPatient( "alfa", "first", 123L, new Date(250L));
			assertNotNull(alfaReturn.getAssignedDoctor());
			assertEquals((long) alfaReturn.getAssignedDoctor().getAhvNum(), 25L);
			assertEquals(alfaReturn.getAssignedDoctor(), beta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}