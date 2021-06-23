package ch.bfh.ti.sed.pm.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Doctor is the main entity that interacts with the system we store his informations
 * in a class , which will be an entity also in our data base
 *
 */
@Entity
public class Doctor {
	/**
	 * an int to represent AHV Number  assuming
	 * that the patient monitoring would be implemented on a swiss hospital
	 */
    @Id
    private long ahvNum;
	/**
	 * a String to represent Doctor name
	 */
    private String name;
	/**
	 * a String to represent Doctor surname
	 */
    private String surname;
	/**
	 * a String to represent Doctor Password
	 */
    private String password;
	/**
	 * Email belonging to the doctor. The doctor will use the email in conjunction with password to authenticate into the system.
	 */
	@Column(unique=true)
    private String email;
	/**
	 * A set to represent Assigned Patients to doctor
	 */
    @OneToMany(mappedBy = "assignedDoctor", cascade = CascadeType.PERSIST)
    private Set<Patient> assignedPatients = new HashSet<>();

	/**
	 * the Class Constructor
	 *
	 * @param ahvNum Doctor's ahvNum
	 * @param name	Doctor's name
	 * @param surname Doctor's surname
	 * @param password Doctor's password (in cleartext, it will be hashed before storing it)
	 * @param email Doctor's email
	 */
    public Doctor(long ahvNum, String name, String surname, String password, String email) {
        this.ahvNum = ahvNum;
        this.surname = surname;
        this.name = name;
        setPassword(password);
        this.email = email;
        //this.assignedPatients = new HashSet<>();
    }

	/**
	 * Empty constructor
	 */
	public Doctor() {

	}

	/**
	 * Getter for AhvNum
	 * @return a String representing AhvNum
	 */
    public long getAhvNum() {
    	return ahvNum;
    }

	/**
	 * Getter for surname
	 * @return a string representing patient surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Getter for Name
	 * @return a string representing Doctor name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for email
	 * @return a string representing the Doctor E_mail
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Getter for assignedPatients
	 * @return a set of assigned patient to the Doctor
	 */
	public Set<Patient> getAssignedPatients() {
		return assignedPatients;
	}

	/**
	 * Setter for ahvNum
	 * @param ahvNum for Doctor
	 */
    public void setAhvNum(long ahvNum) {
    	this.ahvNum = ahvNum;
    }

	/**
	 * Setter for name
	 * @param name sets name to Doctor name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for surname
	 * @param surname set surname to the doctor
	 */
    public void setSurname(String surname) {
    	this.surname = surname;
    }

	/**
	 * Setter for Email
	 * @param email the email to be set for Doctor
	 */
    public void setEmail(String email) {
    	this.email = email;
    }

	/**
	 * Setter for assignedPatietns
	 * @param assignedPatients set patients Set to the doctor
	 */
    public void setAssignedPatients(Set<Patient> assignedPatients) {
    	this.assignedPatients = assignedPatients;
    }

	/**
	 * Setter for Password
	 * @param password set password to Doctor password
	 */
    public void setPassword(String password) throws IllegalArgumentException {
    	if (password.isEmpty()) throw new IllegalArgumentException("Password cannot be null.");
        try {
            this.password = hash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

	/**
	 * set the attribute ahvNum
	 * @param ahvNum for Doctor
	 */
	public void setAhvNum(int ahvNum) {
		this.ahvNum = ahvNum;
	}

	/**
	 * Adds the patient to assignedPatients and assignedDoctor in the Patient is
	 * updated to the right doctor.
	 * If the patient already exists he is not added again
	 *
	 * @param name the patients name
	 * @param surname the patients surname
	 * @param ahv the patients AHV number
	 * @param birthDate the patients date of birth
	 * @return the patient that will be added 
	 */
	public Patient addPatient(String name, String surname, long ahv, Date birthDate) {
		Patient patient = new Patient(ahv,name,surname,birthDate);
		if (!assignedPatients.contains(patient)) {
			assignedPatients.add(patient);
			patient.setAssignedDoctor(this);
		}

		return patient;
	}

	/**
	 * Checks if email and password are the same as the Doctor's credentials
	 * @param email Given email
	 * @param clearTextPassword Given clearTextPassword which will be hashed and confronted with this Doctor's hashed password
	 * @return true if the given credentials authenticate with this doctor
	 * @throws NoSuchAlgorithmException If no hashing algorithm is found
	 */
	public boolean validateLogin(String email, String clearTextPassword) throws NoSuchAlgorithmException {
		return this.email.equalsIgnoreCase(email) && this.password.equals(hash(clearTextPassword));
	}

	/**
	 *
	 * @param cleartext
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private String hash(String cleartext) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		md.update(cleartext.getBytes());
		byte[] digest = md.digest();

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, digest);

		// Convert message digest into hex value
		StringBuilder cipherText = new StringBuilder(no.toString(16));

		// Add preceding 0s to make it 32 bit
		while (cipherText.length() < 32) {
			cipherText.insert(0, "0");
		}
		// Return hashed password
		return cipherText.toString().toUpperCase();
	}
}
