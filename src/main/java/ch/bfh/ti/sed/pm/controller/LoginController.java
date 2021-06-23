package ch.bfh.ti.sed.pm.controller;

import ch.bfh.ti.sed.pm.model.Doctor;

import ch.bfh.ti.sed.pm.persistence.EntityManageable;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 
 * a Login controller to handle login event 
 *
 */
public class LoginController {
    private EntityManageable em;

    public LoginController(EntityManageable em) {
        this.em = em;
    }

    /**
     * Check if the credentials provided match a doctor in the database
     * Attention: email is not the id
     * @param email The doctor's email
     * @param password The doctor's password (provide in cleartext; validateLogin will hash it)
     * @return true if the credentials provided match a doctor in the database
     * @throws NoSuchAlgorithmException if no hashing algorithm is found on the executing machine
     */
    public boolean login(String email, String password) throws NoSuchAlgorithmException {
        // Creates a TypedQuery, the result is assured to be unique because the "email" attribute has a unique constraint set in its model definition
        List<Doctor> results = em.createQuery("SELECT d FROM Doctor d WHERE d.email LIKE :email", Doctor.class)
            .setParameter("email", email)
            .getResultList();
        // No occurrence found, login failed
        if (results.isEmpty()) return false;
        // User with given email is found, check for password
        // TODO: Email is probably redundant
        return results.get(0).validateLogin(email, password);
    }
}