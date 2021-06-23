package ch.bfh.ti.sed.pm;

import ch.bfh.ti.sed.pm.controller.*;
import ch.bfh.ti.sed.pm.model.Doctor;
import ch.bfh.ti.sed.pm.model.ObservationPeriod;
import ch.bfh.ti.sed.pm.model.Patient;
import ch.bfh.ti.sed.pm.persistence.EntityManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

/**
 * PatientView has a form to add new patients and a list of all patients of the
 * current doctor is displayed on the bottom
 */
@Route("AddPatientView")
public class AddPatientView extends VerticalLayout {

	private long loggedDoctorAhvNum;

	private Grid<Patient> grid = new Grid<>(Patient.class);

	// AddPatientForm
	private FormLayout form = new FormLayout();

	private TextField AHVNumber = new TextField("AHV Number");
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private DatePicker birthDate = new DatePicker("Birthdate");
	private Button addButton = new Button("Add Patient");
	private Button observationButton = new Button("Track Patients");
	private Button manageObservations = new Button("Manage Observation Periods");
	private Button logout = new Button("Logout");

	// Test entity Manager
	private EntityManager em;
	private PatientController pc;
	private DeviceController dc;
	private DoctorController doctorController;
	private Doctor doc;
	private static final String PERSISTENCE_UNIT_NAME = "healthbit";

	public AddPatientView() {
		try{
			loggedDoctorAhvNum = (long) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userId");
		}catch(Exception e){
			UI.getCurrent().navigate("");
			UI.getCurrent().getPage().reload();
		}

		em = EntityManager.getInstance();
		pc = new PatientController(em);
		doctorController = new DoctorController(em);
		dc = new DeviceController(em);

			doc = doctorController.getAuthenticatedDoctor(loggedDoctorAhvNum);

		// grid.setColumns("AHV Number","FirstName", "LastName", "Birth date");
		form.add(AHVNumber, firstName, lastName, birthDate);
		HorizontalLayout buttons = new HorizontalLayout(addButton, observationButton, manageObservations);
		HorizontalLayout mainContent = new HorizontalLayout(form);
		addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addButton.addClickListener(event -> addPatient());
		logout.getElement().getStyle().set("position","absolute");
		logout.getElement().getStyle().set("top", "10px");
		logout.getElement().getStyle().set("right", "10px");
		add(logout);
		observationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		manageObservations.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		manageObservations.addClickListener(e -> {
				UI.getCurrent().navigate("ManageObservationPeriodView");
		});
		observationButton.addClickListener(e -> {
			UI.getCurrent().navigate("patientcontrol");
		});
		logout.addClickListener(e -> {
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userId", null);
			UI.getCurrent().navigate("");
		});
		// Add all the the main layout
		add(mainContent, buttons);
		// Create temporary Doctor

		grid.setColumns("ahvNum", "surname", "name", "birthDate");
		grid.getColumnByKey("ahvNum").setHeader("AHV");
		grid.getColumnByKey("surname").setHeader("Surname");
		grid.getColumnByKey("name").setHeader("Name");
		grid.getColumnByKey("birthDate").setHeader("Birth date");
		add(grid);
		if(UI.getCurrent().getSession().getAttribute("addInit") == null){
			UI.getCurrent().getSession().setAttribute("addInit", 1);
			addInitialDevices();
			addInitialPatient();
		}
		setSizeFull();
		updateList();
	}

	private void addInitialPatient() {
		pc.addPatient("Jonnhy", "Bravo", 234234252354L, new Date(1999, 2, 22), doc);
		Patient p = em.find(Patient.class, 234234252354L);

		ObservationPeriodController obc = new ObservationPeriodController(em);
		obc.startObservationPeriod(new Date(2019, 8, 1), 5,234234252354L, 1);
		MeasurementController mc = new MeasurementController(em);
		mc.addMeasurement(37, new Timestamp(new Date().getTime()), p.getCurrentObservation());
		mc.addMeasurement(36, new Timestamp(new Date().getTime()), p.getCurrentObservation());
		mc.addMeasurement(36.5f, new Timestamp(new Date().getTime()), p.getCurrentObservation());
		mc.addMeasurement(36.7f, new Timestamp(new Date().getTime()), p.getCurrentObservation());
	}

	/**
	 * addPatient method which is called when the user pressed the ADD button It
	 * uses the entity manager to persist the input data
	 */
	public void addPatient() {
		boolean success = pc.addPatient(lastName.getValue(), firstName.getValue(), Long.parseLong(AHVNumber.getValue()),
				convertToDateViaSqlDate(birthDate.getValue()), doc);
		if (success) {
			Notification.show("Patient added");
		} else {
			Notification.show("Patient already exists");
		}
		updateList();
	}

	/**
	 * Updates the grid by getting all the patients from the current active doctor
	 */
	private void updateList() {
		System.out.println("updating list");
		grid.setItems(doc.getAssignedPatients());
		grid.getDataProvider().refreshAll();
	}

	/**
	 * Converts a LocalDate attribute to a Date
	 * 
	 * @param dateToConvert LocalDate attribute
	 * @return Date converted from LocalDate
	 */
	private Date convertToDateViaSqlDate(LocalDate dateToConvert) {
		return java.sql.Date.valueOf(dateToConvert);
	}

	private void addInitialDevices(){
		Notification.show("initial added");
		for(int i = 0; i < 10; i++){
			dc.addDevice("");
		}
	}

}
