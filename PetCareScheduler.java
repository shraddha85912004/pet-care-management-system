import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class PetCareScheduler {

    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, Pet> pets = new HashMap<>();

    public static void main(String[] args) {

        loadRecordsFromFile();

        boolean running = true;

        while (running) {

            System.out.println("\n=== PetCare Scheduler ===");
            System.out.println("1. Register Pet");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. Display Pets");
            System.out.println("4. Display Pet Appointments");
            System.out.println("5. Generate Reports");
            System.out.println("6. Save and Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    registerPets();
                    break;

                case "2":
                    schedulingAppointments();
                    break;

                case "3":
                    displayPets();
                    break;

                case "4":
                    displayRecords();
                    break;

                case "5":
                    generateReports();
                    break;

                case "6":
                    saveRecordsToFile();
                    running = false;
                    System.out.println("Data saved successfully. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // ================= REGISTER PET =================

    private static void registerPets() {

        System.out.print("Enter Pet ID: ");
        String petId = scanner.nextLine().trim();

        if (pets.containsKey(petId)) {
            System.out.println("Pet ID already exists.");
            return;
        }

        System.out.print("Enter pet name: ");
        String petName = scanner.nextLine();

        System.out.print("Enter species: ");
        String species = scanner.nextLine();

        System.out.print("Enter age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter owner name: ");
        String ownerName = scanner.nextLine();

        System.out.print("Enter contact: ");
        String contact = scanner.nextLine();

        Pet pet = new Pet(petId, petName, species, age, ownerName, contact);

        pets.put(petId, pet);

        System.out.println("Pet registered successfully!");
    }

    // ================= SCHEDULE APPOINTMENT =================

    private static void schedulingAppointments() {

        System.out.print("Enter Pet ID: ");
        String petId = scanner.nextLine();

        Pet pet = pets.get(petId);

        if (pet == null) {
            System.out.println("Pet not found.");
            return;
        }

        System.out.print("Enter appointment type (vet, grooming, vaccination): ");
        String type = scanner.nextLine().toLowerCase();

        List<String> validTypes = Arrays.asList("vet", "grooming", "vaccination");

        if (!validTypes.contains(type)) {
            System.out.println("Invalid appointment type.");
            return;
        }

        System.out.print("Enter appointment date (YYYY-MM-DD): ");

        LocalDate date;

        try {
            date = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }

        if (date.isBefore(LocalDate.now())) {
            System.out.println("Appointment must be in the future.");
            return;
        }

        System.out.print("Enter notes: ");
        String notes = scanner.nextLine();

        Appointment appt = new Appointment(type, date, notes);

        pet.addAppointment(appt);

        System.out.println("Appointment scheduled successfully!");
    }

    // ================= DISPLAY PETS =================

    private static void displayPets() {

        if (pets.isEmpty()) {
            System.out.println("No pets registered.");
            return;
        }

        for (Pet p : pets.values()) {
            System.out.println("----------------------------");
            System.out.println(p);
        }
    }

    // ================= DISPLAY APPOINTMENTS =================

    private static void displayRecords() {

        System.out.print("Enter Pet ID: ");
        String petId = scanner.nextLine();

        Pet pet = pets.get(petId);

        if (pet == null) {
            System.out.println("Pet not found.");
            return;
        }

        if (pet.getAppointments().isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }

        for (Appointment a : pet.getAppointments()) {
            System.out.println(a);
        }
    }

    // ================= REPORTS =================

    private static void generateReports() {

        LocalDate today = LocalDate.now();

        System.out.println("\nPets with appointments in next 7 days:");

        for (Pet pet : pets.values()) {

            for (Appointment a : pet.getAppointments()) {

                if (!a.getDate().isBefore(today) &&
                        a.getDate().isBefore(today.plusDays(7))) {

                    System.out.println(pet.getPetName() + " -> " + a);
                }
            }
        }

        System.out.println("\nPets overdue for vet visit:");

        for (Pet pet : pets.values()) {

            boolean vetFound = false;

            for (Appointment a : pet.getAppointments()) {

                if (a.getType().equals("vet") &&
                        a.getDate().isAfter(today.minusMonths(6))) {

                    vetFound = true;
                    break;
                }
            }

            if (!vetFound) {
                System.out.println(pet.getPetName());
            }
        }
    }

    // ================= SAVE =================

    private static void saveRecordsToFile() {

        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream("pets.ser"))) {

            out.writeObject(pets);

        } catch (IOException e) {

            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // ================= LOAD =================

    @SuppressWarnings("unchecked")
    private static void loadRecordsFromFile() {

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream("pets.ser"))) {

            pets = (Map<String, Pet>) in.readObject();

            System.out.println("Pet data loaded.");

        } catch (FileNotFoundException e) {

            System.out.println("No saved data found. Starting fresh.");

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Error loading data.");
        }
    }
}
