import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    private String petId;
    private String petName;
    private String species;
    private int age;
    private String ownerName;
    private String contact;
    private List<Appointment> appointments;
    private LocalDate registrationDate;

    public Pet(String petId, String petName, String species,
               int age, String ownerName, String contact) {

        this.petId = petId;
        this.petName = petName;
        this.species = species;
        this.age = age;
        this.ownerName = ownerName;
        this.contact = contact;
        this.appointments = new ArrayList<>();
        this.registrationDate = LocalDate.now();
    }

    public String getPetId() { return petId; }
    public String getPetName() { return petName; }
    public String getSpecies() { return species; }
    public int getAge() { return age; }
    public String getOwnerName() { return ownerName; }
    public String getContact() { return contact; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public List<Appointment> getAppointments() { return appointments; }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    @Override
    public String toString() {

        return "Pet ID: " + petId +
                "\nName: " + petName +
                "\nSpecies: " + species +
                "\nAge: " + age +
                "\nOwner: " + ownerName +
                "\nContact: " + contact +
                "\nRegistered On: " + registrationDate +
                "\nAppointments Count: " + appointments.size();
    }
}
