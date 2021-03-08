/*
 * Name     :   User.java
 * Author$  :   joeca
 * Date     :   05 Mar 2021
 */
package profile;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * User class containing basic information about a user
 */
public class User {

    private final UUID userID;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String formattedDateOfBirth;
    private int age;

    public User(String firstName, String lastName, LocalDate dateOfBirth) {
        this.userID = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        formatAge();
    }

    public static void main(String[] args) {
        User u1 = new User("joe", "cartwright", LocalDate.of(1996,10,23));
        System.out.println(u1.toString());
    }

    /**
     * @return the user ID
     */
    public UUID getUserID() {
        return userID;
    }

    /**
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the formatted date of birth of a user
     */
    public String getFormattedDateOfBirth() {
        return formattedDateOfBirth;
    }

    /**
     * @return the age of a user in years
     */
    public int getAge() {
        return age;
    }

    /**
     * Set the first name of the user
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set the last name of the user
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Set the date of birth of a user
     * @param dateOfBirth the date of birth of a user
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * formats the date of birth of a user and calculates the age in years.
     */
    public void formatAge() {
        formattedDateOfBirth = dateOfBirth.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
        age = Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", formattedDateOfBirth='" + formattedDateOfBirth + '\'' +
                ", age=" + age +
                '}';
    }
}
