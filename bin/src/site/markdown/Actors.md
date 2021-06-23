## Actors

### Primary actor
## Doctor
Doctors are responsible of implanting the HealthBit sensor on the Patients. The doctor adds patients to the system, specify an observation period and are able to examine each measurement supplied by the HealthBit sensor.
## Administrator
The administrator is responsible of adding doctors to the system.
## Application
The application is composed of an interface for doctors and an underlying business logic and persistence layer.
### Supporting actor
## HealthBit device
The HealthBit device gathers vital signs from the patients and make them available to the system, consequently to the doctors.
## E-Mail Service
The E-Mail service is needed to provide authentication infos to the doctors the first time they register.
### Offstage actor
## Patient
Patients must be registered in the HealthBit system with some of their personal information. Each patient carries a HealthBit device when they're in an observation period. Patients don't directly interact with the system, they do so through the their doctor.