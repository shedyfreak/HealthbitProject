package ch.bfh.ti.sed.pm;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import ch.bfh.ti.sed.pm.controller.DoctorController;
import ch.bfh.ti.sed.pm.controller.PatientController;
import ch.bfh.ti.sed.pm.model.Doctor;
import ch.bfh.ti.sed.pm.model.Measurement;
import ch.bfh.ti.sed.pm.model.ObservationPeriod;
import ch.bfh.ti.sed.pm.model.Patient;
import ch.bfh.ti.sed.pm.persistence.EntityManager;

import java.util.Set;

@Route("patientcontrol")
public class PatientControl extends VerticalLayout {
	
	long loggedDoctorAhvNum;
	private EntityManager em = EntityManager.getInstance();
	private DoctorController doctorController = new DoctorController(em);
	private PatientController patientController =new PatientController(em);
	private Button logout = new Button("Logout");
	private Button returnAddPatientButton = new Button("Add Patients");
	private Button returnManageObservationButton = new Button("Manage Observation Period");

	public PatientControl() {
		try{
			loggedDoctorAhvNum = (long) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userId");
		}catch(Exception e){
			UI.getCurrent().navigate("");
			UI.getCurrent().getPage().reload();
		}
		logout.getElement().getStyle().set("position","absolute");
		logout.getElement().getStyle().set("top", "10px");
		logout.getElement().getStyle().set("right", "10px");
		add(logout);
		logout.addClickListener(e -> {
			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userId", null);
			UI.getCurrent().navigate("");
		});
		
		Grid<Patient> patientsGrid = new Grid<>(Patient.class);
		Grid<ObservationPeriod> obsevationsGrid= new Grid<>(ObservationPeriod.class);
		Grid<Measurement> measurmentsGrid = new Grid<>(Measurement.class);
		HorizontalLayout buttonsReturn = new HorizontalLayout(returnAddPatientButton, returnManageObservationButton);
		returnAddPatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		returnAddPatientButton.addClickListener(e -> {
			UI.getCurrent().navigate("AddPatientView");
		});
		returnManageObservationButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		returnManageObservationButton.addClickListener(e -> {
			UI.getCurrent().navigate("ManageObservationPeriodView");
		});
		patientsGrid.setColumns("ahvNum", "surname", "name", "birthDate");
		patientsGrid.getColumnByKey("ahvNum").setHeader("AHV");
		patientsGrid.getColumnByKey("surname").setHeader("Surname");
		patientsGrid.getColumnByKey("name").setHeader("Name");
		patientsGrid.getColumnByKey("birthDate").setHeader("Birth date");
		Label patientsList=new Label("Patients List");
		patientsList.getElement().getStyle().set("fontWeight","bold");
		add(patientsList);
		add(patientsGrid);
		Label oldObservations=new Label("History of Observation Periods");
		oldObservations.getElement().getStyle().set("fontWeight","bold");
		obsevationsGrid.setColumns("device", "startDate", "endDate", "frequency");
		obsevationsGrid.getColumnByKey("startDate").setHeader("Date of start");
		obsevationsGrid.getColumnByKey("endDate").setHeader("Date of end");
		obsevationsGrid.getColumnByKey("frequency").setHeader("Frequency");
		add(oldObservations);
		add(obsevationsGrid);
		measurmentsGrid.setColumns("timestamp", "temperature");
		measurmentsGrid.getColumnByKey("timestamp").setHeader("timestamp");
		measurmentsGrid.getColumnByKey("temperature").setHeader("temperature");
		Label currentObs=new Label("Current Measurements");
		currentObs.getElement().getStyle().set("fontWeight","bold");
		add(currentObs);
		add(measurmentsGrid);
		add(buttonsReturn);
		Doctor doc=doctorController.getAuthenticatedDoctor(loggedDoctorAhvNum);
		patientsGrid.setItems(doc.getAssignedPatients());
		// Get all measurements

		patientsGrid.addItemClickListener(
		        event ->{
		        	Patient p=patientController.getPatientByAhvNum(event.getItem().getAhvNum());
		        	System.out.println(p.getCurrentObservation());
		        	obsevationsGrid.setItems(p.getCurrentObservation());
		        	obsevationsGrid.setItems(p.getObservationPeriods());
					Set<Measurement> measurements = p.getCurrentObservation().getMeasurements();
		        	measurmentsGrid.setItems(measurements);
		     	});
		obsevationsGrid.addItemClickListener(
				event -> {
					measurmentsGrid.setItems(event.getItem().getMeasurements());
				});
	}

}
