package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.bfh.ti.sed.pm.persistence.EntityManageable;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginControllerTest {
    private static final String PERSISTENCE_UNIT_NAME = "healthbit";
    private static final String VALID_EMAIL = "m.j@sacredhearthospital.com";
    private static final String VALID_PASSWORD = "safepassword";

    private EntityManageable em;

    private DoctorController doctorController;
    private LoginController loginController;

    @BeforeEach
    public void beforeEach() {
        em = EntityManager.getInstance();

        doctorController = new DoctorController(em);
        doctorController.addDoctor(123L, "Micheal", "Jackson", VALID_PASSWORD, VALID_EMAIL);

        loginController = new LoginController(em);
    }

    @AfterEach
    public void afterEach() {
        // Make sure resources are released
        em.close();
    }

    @Test
    public void loginTest() throws NoSuchAlgorithmException {
        assertTrue(loginController.login(VALID_EMAIL, VALID_PASSWORD));
        assertFalse(loginController.login("wrong@email.com", "wrongpassword"));
        assertFalse(loginController.login(VALID_EMAIL, "wrongpassword"));
        assertFalse(loginController.login("wrong@email.com", VALID_PASSWORD));
    }
}
