package lebogangmphahlele_part1;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void testUsernameSuccess() {
        boolean expected = true;
        boolean actual = Login.checkUserName("kyl_1");
        assertEquals(expected, actual);
    }

    @Test
    public void testUsernameFailure() {
        boolean expected = false;
        boolean actual = Login.checkUserName("kyle!!!!!!!");
        assertEquals(expected, actual);
    }

    @Test
    public void testPasswordSuccess() {
        boolean expected = true;
        boolean actual = Login.checkPasswordComplexity("Ch&&sec@ke99!");
        assertEquals(expected, actual);
    }

    @Test
    public void testPasswordFailure() {
        boolean expected = false;
        boolean actual = Login.checkPasswordComplexity("password");
        assertEquals(expected, actual);
    }

    @Test
    public void testCellPhoneSuccess() {
        boolean expected = true;
        boolean actual = Login.checkCellPhoneNumber("+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testCellPhoneFailure() {
        boolean expected = false;
        boolean actual = Login.checkCellPhoneNumber("08966553");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUserSuccess() {
        String expected = "The user has been registered successfully.";

        String actual = Login.registerUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976");

        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUserUsernameFailure() {
        String expected =
                "Username is not correctly formatted; please ensure "
                + "that your username contains an underscore and is "
                + "no more than five characters in length.";

        String actual = Login.registerUser(
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976");

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginSuccess() {
        boolean expected = true;

        boolean actual = Login.loginUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "kyl_1",
                "Ch&&sec@ke99!");

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginFailure() {
        boolean expected = false;

        boolean actual = Login.loginUser(
                "kyl_1",
                "Ch&&sec@ke99!",
                "wrong",
                "wrong");

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginStatusSuccess() {
        String expected =
                "Welcome Lebogang, Mphahlele it is great to see you again.";

        String actual = Login.returnLoginStatus(
                        true,
                        "Lebogang",
                        "Mphahlele");

        assertEquals(expected, actual);
    }

    @Test
    public void testLoginStatusFailure() {
        String expected =
                "Username or password incorrect, please try again";

        String actual = Login.returnLoginStatus(
                        false,
                        "Lebogang",
                        "Mphahlele");

        assertEquals(expected, actual);
    }
}