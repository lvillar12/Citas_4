class Doctor {
    private String id;
    private String name;

    public Doctor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + name;
    }
}

class Patient {
    private String id;
    private String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + name;
    }
}

class Appointment {
    private String date;
    private String time;
    private Doctor doctor;
    private Patient patient;

    public Appointment(String date, String time, Doctor doctor, Patient patient) {
        this.date = date;
        this.time = time;
        this.doctor = doctor;
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "Fecha: " + date + ", Hora: " + time + ", Doctor: " + doctor.getName() + ", Paciente: " + patient.getName();
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
}

