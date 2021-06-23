package ch.bfh.ti.sed.pm;

import ch.bfh.ti.sed.pm.controller.*;
import ch.bfh.ti.sed.pm.model.Device;
import ch.bfh.ti.sed.pm.model.Doctor;
import ch.bfh.ti.sed.pm.model.Patient;
import ch.bfh.ti.sed.pm.persistence.EntityManager;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Route("ManageObservationPeriodView")
public class ManageObservationPeriodView extends VerticalLayout {
    long loggedDoctorAhvNum;

    private Grid<Patient> grid = new Grid<>(Patient.class);

    // AddPatientForm
    FormLayout form = new FormLayout();
    FormLayout formEnd = new FormLayout();

    Label addObsPeriod = new Label("Add Observation Period");
    Label endObsPeriod = new Label("End Observation Period");
    Select<String> deviceID = new Select<>();
    private TextField AHVNumber = new TextField("AHV Number");
    private TextField AHVNumberEnd = new TextField("AHV Number");
    private TextField frequency = new TextField("Frequency");
    private DatePicker startDate = new DatePicker("Start date");
    private Button addObsButton = new Button("Add Observation Period");
    private Button endObsButton = new Button("End Observation Period");
    private Button returnAddPatientButton = new Button("Add Patients");
    private Button returnTrackPatientButton = new Button("Track Patients");
    private Button logout = new Button("Logout");

    // Test entity Manager
    private EntityManager em;
    private ObservationPeriodController pc;
    private PatientController patientController;
    private MeasurementController mc;
    private DeviceController dc;
    private DoctorController doctorController;
    private Doctor doc;
    SimpleDateFormat sdf;

    private static final String PERSISTENCE_UNIT_NAME = "healthbit";

    /**
     * View to start and end observationb Period
     */
    public ManageObservationPeriodView() {
        em = EntityManager.getInstance();
        pc = new ObservationPeriodController(em);
        dc = new DeviceController(em);
        mc = new MeasurementController(em);
        sdf = new SimpleDateFormat("dd/M/yyyy");
        doctorController = new DoctorController(em);
        patientController = new PatientController(em);
        try{
            loggedDoctorAhvNum = (long) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userId");
        }catch(Exception e){
            UI.getCurrent().navigate("");
            UI.getCurrent().getPage().reload();
        }
            doc = doctorController.getAuthenticatedDoctor(loggedDoctorAhvNum);

        // grid.setColumns("AHV Number","FirstName", "LastName", "Birth date");
        form.add(AHVNumber, deviceID, frequency, startDate);
        formEnd.add(AHVNumberEnd);
        List<String> freeDeviceList = dc.getAllFreeDevices();
        deviceID.setItems(freeDeviceList);
        deviceID.setLabel("Device ID");
        HorizontalLayout labelStart = new HorizontalLayout(addObsPeriod);
        HorizontalLayout labelEnd = new HorizontalLayout(endObsPeriod);
        HorizontalLayout buttonsStart = new HorizontalLayout(addObsButton);
        HorizontalLayout buttonsReturn = new HorizontalLayout(returnAddPatientButton, returnTrackPatientButton);
        HorizontalLayout buttonsEnd = new HorizontalLayout(endObsButton);
        HorizontalLayout mainContent = new HorizontalLayout(form);
        HorizontalLayout mainContent1 = new HorizontalLayout(formEnd);
        addObsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addObsButton.addClickListener(event -> addObservationPeriod());
        endObsButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        endObsButton.addClickListener(event -> endObservationPeriod());
        logout.getElement().getStyle().set("position", "absolute");
        logout.getElement().getStyle().set("top", "10px");
        logout.getElement().getStyle().set("right", "10px");
        add(logout);
        returnAddPatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        returnAddPatientButton.addClickListener(e -> {
            UI.getCurrent().navigate("AddPatientView");
        });
        returnTrackPatientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        returnTrackPatientButton.addClickListener(e -> {
            UI.getCurrent().navigate("patientcontrol");
        });
        logout.addClickListener(e -> {
            VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userId", null);
            UI.getCurrent().navigate("");
        });
        // Add all the the main layout
        add(labelStart,mainContent, buttonsStart,labelEnd,mainContent1,buttonsEnd, buttonsReturn);
        // Create temporary Doctor


        setSizeFull();
    }

    /**
     * Helper function to add an observation period
     */
    public void addObservationPeriod() {
        boolean success = pc.startObservationPeriod(convertToDateViaSqlDate(startDate.getValue()), Integer.parseInt(frequency.getValue()), Long.parseLong(AHVNumber.getValue()), Long.parseLong(deviceID.getValue()));
        if(success){
            //For testing we add two sample measurements
            long offset = Timestamp.valueOf("2018-01-01 00:00:00").getTime();
            long end = Timestamp.valueOf("2020-01-01 00:00:00").getTime();
            long diff = end - offset + 1;
            Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
            mc.addMeasurement(200f , rand, patientController.getPatientByAhvNum(Long.parseLong(AHVNumber.getValue())).getCurrentObservation());
            rand = new Timestamp(offset + (long)(Math.random() * diff));
            mc.addMeasurement(100f , rand, patientController.getPatientByAhvNum(Long.parseLong(AHVNumber.getValue())).getCurrentObservation());
            Notification.show("Observation Period added");
        }else{
            Notification.show("Observation Period could not be added due to the reasons: Patient or Device is already assigned or the input was wrong");

        }
    }

    public void endObservationPeriod(){
        boolean success = pc.endObservationPeriod(Long.parseLong(AHVNumberEnd.getValue()));
        if(success){
            Notification.show("Ended Observation Period successfully");
        }else {
            Notification.show("The input was wrong the AHV could not be found in the system");
        }
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }
}