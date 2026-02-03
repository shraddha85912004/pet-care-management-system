import java.time.LocalDateTime;
import java.io.Serializable;

public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appointmentType;
    private LocalDateTime dateTime;
    private String notes;

    public Appointment(String appointmentType, LocalDateTime dateTime, String notes) {

        this.appointmentType = appointmentType;
        this.dateTime = dateTime;
        this.notes = notes;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getNotes() {
        return notes;
    }

    @Override
    public String toString() {

        return "-------------------------" +
                "\nAppointment Type: " + appointmentType +
                "\nDate & Time: " + dateTime +
                "\nNotes: " + notes;
    }
}
