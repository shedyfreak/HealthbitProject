package ch.bfh.ti.sed.pm;

import ch.bfh.ti.sed.pm.controller.DoctorController;
import ch.bfh.ti.sed.pm.controller.LoginController;
import ch.bfh.ti.sed.pm.controller.ObservationPeriodController;
import ch.bfh.ti.sed.pm.controller.PatientController;
import ch.bfh.ti.sed.pm.model.Doctor;
import ch.bfh.ti.sed.pm.persistence.EntityManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * The main view contains a button and a click listener.
 */
@Route
@PWA(name = "HealthBit Vital Signs Monitoring System", shortName = "HealthBit")
public class MainView extends VerticalLayout {
    public MainView() {
        EntityManager em = EntityManager.getInstance();

        EmailField emailField = new EmailField();
        emailField.setLabel("Email");
        emailField.setPlaceholder("doctor@mail.ch");
        PasswordField passField = new PasswordField();
        passField.setLabel("Password");
        Button loginButton = new Button("Login");

        FormLayout loginForm = new FormLayout();
        loginForm.add(emailField, passField, loginButton);

        LoginForm component = new LoginForm();
        DoctorController docController = new DoctorController(em);
        LoginController loginController = new LoginController(em);
        component.addLoginListener(e -> {
            boolean isAuthenticated = false;
            try {
                System.out.println("DEBUG: " + e.getUsername() + " " + e.getPassword());
                DoctorController doctorController = new DoctorController(em);
                doctorController.addDoctor(4398839485L, "test", "test", "mypassword", "my.name@bfh.ch");
                isAuthenticated = (loginController.login(e.getUsername(), e.getPassword()));
                System.out.println("BOOLEAN" + isAuthenticated);
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
            if (isAuthenticated) {
            	//start of test
            	Doctor tmp;
            	tmp=em.find(Doctor.class, 4398839485L);
            	PatientController patientcont=new PatientController(em);
            	patientcont.addPatient("cool", "patient", 1005L, new Date(), tmp);
            	ObservationPeriodController obs=new ObservationPeriodController(em);
            	obs.startObservationPeriod(new Date(), 20, 1005L, 10);
            	obs.endObservationPeriod(1005L);
            	obs.startObservationPeriod(new Date(), 20, 1005L, 15);
            	//end of test/
            	VaadinService.getCurrentRequest().getWrappedSession()
                .setAttribute("userId", 4398839485L);
                System.out.println("Authenticate");
                UI.getCurrent().navigate("AddPatientView");
               // new PatientControl();
            } else {
                component.setError(true);
            }
        });
        add(
                component
        );
    }
}