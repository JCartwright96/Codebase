/*
 * Name     :   UserTest.java
 * Author$  :   joeca
 * Date     :   08 Mar 2021
 */
package profile;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UserTest {

    private User testUser;
    private final String FIRST_NAME = "Joe";
    private final String LAST_NAME = "Cartwright";
    private final LocalDate DOB = LocalDate.of(1996, 10, 23);
    private final String UPDATED_FIRST_NAME = "Bob";
    private final String UPDATED_LAST_NAME = "Robertson";
    private final LocalDate UPDATED_DOB = LocalDate.of(1995, 10, 23);

    @Before
    public void setUp() {
        testUser = new User(FIRST_NAME, LAST_NAME, DOB);
    }

    @Test
    public void testConstructor() {
        assertNotNull(testUser.getUserID());
        assertEquals(FIRST_NAME, testUser.getFirstName());
        assertEquals(LAST_NAME, testUser.getLastName());
        assertEquals(24, testUser.getAge());
        assertEquals("23/10/96", testUser.getFormattedDateOfBirth());
    }

    @Test
    public void testSetterMethods() {
        testUser.setFirstName(UPDATED_FIRST_NAME);
        testUser.setLastName(UPDATED_LAST_NAME);
        testUser.setDateOfBirth(UPDATED_DOB);

        assertEquals(UPDATED_FIRST_NAME, testUser.getFirstName());
        assertEquals(UPDATED_LAST_NAME, testUser.getLastName());
        assertEquals(25, testUser.getAge());
        assertEquals("23/10/95", testUser.getFormattedDateOfBirth());
    }

    @Test
    public void testToString() {
        assertEquals("User{" +
                "userID=" + testUser.getUserID() +
                ", firstName='" + FIRST_NAME + '\'' +
                ", lastName='" + LAST_NAME + '\'' +
                ", dateOfBirth=" + DOB +
                ", formattedDateOfBirth='" + "23/10/96" + '\'' +
                ", age=" + 24 +
                '}', testUser.toString());
    }
}
