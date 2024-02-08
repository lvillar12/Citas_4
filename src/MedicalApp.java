import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MedicalApp {
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String DOCTORS_FILE = "db/doctors.txt";
    private static final String PATIENTS_FILE = "db/patients.txt";
    private static final String APPOINTMENTS_FILE = "db/appointments.txt";
    private static List<Doctor> doctors = new ArrayList<>();
    private static List<Patient> patients = new ArrayList<>();
    private static List<Appointment> appointments = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el usuario administrador:");
        String usernameInput = scanner.nextLine();
        System.out.println("Ingrese la contraseña:");
        String passwordInput = scanner.nextLine();

        if (!usernameInput.equals(USERNAME) || !passwordInput.equals(PASSWORD)) {
            System.out.println("Usuario o contraseña incorrectos. Saliendo del programa.");
            return;
        }

        loadDoctors();
        loadPatients();
        loadAppointments();

        int choice;

        do {
            System.out.println("\n--- Sistema de Citas Médicas ---");
            System.out.println("1. Dar de alta médico");
            System.out.println("2. Dar de alta paciente");
            System.out.println("3. Crear cita médica");
            System.out.println("4. Ver citas médicas");
            System.out.println("5. Lista de doctores");
            System.out.println("6. Lista de pacientes");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume el salto de línea

            switch (choice) {
                case 1:
                    addDoctor(scanner);
                    break;
                case 2:
                    addPatient(scanner);
                    break;
                case 3:
                    createAppointment(scanner);
                    break;
                case 4:
                    showAppointments();
                    break;
                case 5:
                    listDoctors();
                    break;
                case 6:
                    listPatients();
                    break;
                case 7:
                    saveDoctors();
                    savePatients();
                    saveAppointments();
                    System.out.println("¡Gracias por usar el Sistema de Citas Médicas!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (choice != 7);
    }

    private static void addDoctor(Scanner scanner) {
        System.out.print("Ingrese el ID del médico: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre del médico: ");
        String name = scanner.nextLine();
        doctors.add(new Doctor(id, name));
        System.out.println("Médico dado de alta con éxito.");
    }

    private static void addPatient(Scanner scanner) {
        System.out.print("Ingrese el ID del paciente: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre del paciente: ");
        String name = scanner.nextLine();
        patients.add(new Patient(id, name));
        System.out.println("Paciente dado de alta con éxito.");
    }

    private static void createAppointment(Scanner scanner) {
        System.out.print("Ingrese la fecha de la cita (DD/MM/YYYY): ");
        String date = scanner.nextLine();
        System.out.print("Ingrese la hora de la cita: ");
        String time = scanner.nextLine();
        System.out.println("Doctores disponibles:");
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
        System.out.print("Seleccione el ID del médico: ");
        String doctorId = scanner.nextLine();
        Doctor selectedDoctor = findDoctorById(doctorId);
        if (selectedDoctor == null) {
            System.out.println("Médico no encontrado.");
            return;
        }
        System.out.println("Pacientes disponibles:");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
        System.out.print("Seleccione el ID del paciente: ");
        String patientId = scanner.nextLine();
        Patient selectedPatient = findPatientById(patientId);
        if (selectedPatient == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }
        appointments.add(new Appointment(date, time, selectedDoctor, selectedPatient));
        System.out.println("Cita creada con éxito.");
    }

    private static void showAppointments() {
        System.out.println("\nCitas Programadas:");
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }

    private static void listDoctors() {
        System.out.println("\nLista de Doctores:");
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }

    private static void listPatients() {
        System.out.println("\nLista de Pacientes:");
        for (Patient patient : patients) {
            System.out.println(patient);
        }
    }

    private static Doctor findDoctorById(String id) {
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(id)) {
                return doctor;
            }
        }
        return null;
    }

    private static Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) {
                return patient;
            }
        }
        return null;
    }

    private static void loadDoctors() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DOCTORS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                doctors.add(new Doctor(parts[0], parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los médicos: " + e.getMessage());
        }
    }

    private static void loadPatients() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PATIENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                patients.add(new Patient(parts[0], parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
        }
    }

    private static void loadAppointments() {
        try (BufferedReader reader = new BufferedReader(new FileReader(APPOINTMENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Doctor doctor = findDoctorById(parts[2]);
                Patient patient = findPatientById(parts[3]);
                if (doctor != null && patient != null) {
                    appointments.add(new Appointment(parts[0], parts[1], doctor, patient));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar las citas: " + e.getMessage());
        }
    }

    private static void saveDoctors() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DOCTORS_FILE))) {
            for (Doctor doctor : doctors) {
                writer.println(doctor.getId() + "," + doctor.getName());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los médicos: " + e.getMessage());
        }
    }

    private static void savePatients() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PATIENTS_FILE))) {
            for (Patient patient : patients) {
                writer.println(patient.getId() + "," + patient.getName());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los pacientes: " + e.getMessage());
        }
    }

    private static void saveAppointments() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(APPOINTMENTS_FILE))) {
            for (Appointment appointment : appointments) {
                writer.println(appointment.getDate() + "," + appointment.getTime() + "," +
                        appointment.getDoctor().getId() + "," + appointment.getPatient().getId());
            }
        } catch (IOException e) {
            System.out.println("Error al guardar las citas: " + e.getMessage());
        }
    }
}
