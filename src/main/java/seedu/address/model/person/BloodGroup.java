package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's blood group in DocBob.
 * Guarantees: immutable; is valid as declared in {@link #isValidBloodGroup(String)}
 */
public class BloodGroup {
    public static final String MESSAGE_CONSTRAINTS =
            "Blood group must be either A, B, AB or O and include Rh factor (+ or -)";
    public static final String VALIDATION_REGEX ="^[ABO+-]*";

    public final String bloodGroup;

    /**
     * Constructs a {@code Blood group}.
     *
     * @param bGroup A valid blood group.
     */
    public BloodGroup(String bGroup) {
        requireNonNull(bGroup);
        checkArgument(isValidBloodGroup(bGroup), MESSAGE_CONSTRAINTS);
        bloodGroup = bGroup;
    }

    /**
     * Returns true if a given string is a valid blood group.
     */
    public static boolean isValidBloodGroup(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return bloodGroup;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BloodGroup // instanceof handles nulls
                && bloodGroup.equals(((BloodGroup) other).bloodGroup)); // state check
    }

    @Override
    public int hashCode() {
        return bloodGroup.hashCode();
    }
}
