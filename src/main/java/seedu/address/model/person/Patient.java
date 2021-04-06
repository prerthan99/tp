package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.medical.Appointment;
import seedu.address.model.medical.MedicalRecord;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in DocBob.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient implements Comparable<Patient> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    //Health-related data fields
    private final Height height;
    private final Weight weight;

    // Medical fields
    private final List<MedicalRecord> records = new ArrayList<>();
    private final List<Appointment> appointments = new ArrayList<>();

    // State field
    private boolean isArchived;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Height height, Weight weight, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, height, weight, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.tags.addAll(tags);
        this.isArchived = false;
    }

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Height height, Weight weight,
                   Set<Tag> tags, List<Appointment>appointments) {
        requireAllNonNull(name, phone, email, address, height, weight, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.tags.addAll(tags);
        this.appointments.addAll(appointments);
        this.isArchived = false;
    }

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Height height, Weight weight,
                   Set<Tag> tags, List<MedicalRecord> records ,List<Appointment>appointments) {
        requireAllNonNull(name, phone, email, address, height, weight, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.height = height;
        this.weight = weight;
        this.tags.addAll(tags);
        this.records.addAll(records);
        this.appointments.addAll(appointments);
        this.isArchived = false;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<Appointment> getAppointments() {
        this.appointments.sort(Comparator.comparing(Appointment::getDate));
        return Collections.unmodifiableList(appointments);
    }

    public List<MedicalRecord> getRecords() {
        this.records.sort(Comparator.comparing(MedicalRecord::getDate));
        return Collections.unmodifiableList(records);
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getEmail().equals(getEmail())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getHeight().equals(getHeight())
                && otherPatient.getWeight().equals(getWeight())
                && otherPatient.getTags().equals(getTags())
                && otherPatient.getAppointments().equals(getAppointments())
                && otherPatient.getRecords().equals(getRecords())
                && (otherPatient.isArchived() == isArchived());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, height, weight, tags, appointments,
                records, isArchived);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Height: ")
                .append(getHeight())
                .append("; Weight: ")
                .append(getWeight());

        List<Appointment> appts = getAppointments();
        if (!appts.isEmpty()) {
            builder.append("; Appts: ");
            appts.forEach(builder::append);
        }

        List<MedicalRecord> mrecs = getRecords();
        if (!mrecs.isEmpty()) {
            builder.append("; Mrecs: ");
            mrecs.forEach(builder::append);
        }

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

    /**
     * Adds an appointment to this Patient
     */
    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        this.appointments.sort(Comparator.comparing(Appointment::getDate));
    }

    /**
     * Adds a medical record if it is new, or replace the record if its an edited old record
     */
    public void addMedicalRecord(MedicalRecord newRecord) {
        for (MedicalRecord oldRecord : this.records) {
            if (newRecord.equals(oldRecord)) {
                this.records.set(this.records.indexOf(oldRecord), newRecord);
                return;
            }
        }
        this.records.add(newRecord);
        this.records.sort(Comparator.comparing(MedicalRecord::getDate));
    }

    @Override
    public int compareTo(Patient p) {
        return this.name.fullName.compareTo(p.name.fullName);
    }
}
