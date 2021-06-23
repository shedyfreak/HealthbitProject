## Domain model

- Measurement is a recorded value of the patients temperature.

- Observation Period is the time frame within the measurements are taken for the patient.

- Device is attached to the patient and records the temperature.

- The admin creates the account for each doctor.
- All doctors have only one common admin.

- A Patient is providing multiple measurements.
- A measurement is obtained by one person.

- A patient can wear multiple devices when several observations are made.

- A patient can be assigned to more than one observation periods.
- Each observation period belongs to one patient.

- A device can be used for multiple observation periods.
- For one observation period only one device is used for a patient.

- Multiple measurements can be stored in one observation period.

- A device records zero or multiple measurements from the patient.
- A measurement is taken from one device.

- A device is active for one observation period and is reassigned after this period.

![Domain Model Diagram](images/domain_model.jpg)