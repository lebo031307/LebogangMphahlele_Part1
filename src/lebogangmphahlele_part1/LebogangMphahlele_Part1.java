package lebogangmphahlele_part1;

import java.util.Scanner;

public class LebogangMphahlele_Part1 {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String firstName;
        String lastName;
        String username;
        String password;
        String cellPhone;

        System.out.println("===== Registration =====");

        // Get user's first name
        System.out.println("Enter your first name: ");
        firstName = input.nextLine();

        // Get user's last name
        System.out.println("Enter your last name: ");
        lastName = input.nextLine();

        // Get and validate username
        System.out.println("Enter your username: ");
        username = input.nextLine();

        while (!Login.checkUserName(username)) {
            System.out.println("Username is not correctly formatted; "
                    + "please ensure that your username contains an "
                    + "underscore and is no more than five characters "
                    + "in length.");
            System.out.println("Enter your username again: ");
            username = input.nextLine();
        }
        System.out.println("Username successfully captured.");

        // Get and validate password
        System.out.println("Enter your password: ");
        password = input.nextLine();

        while (!Login.checkPasswordComplexity(password)) {
            System.out.println("Password is not correctly formatted; "
                    + "please ensure that the password contains at "
                    + "least eight characters, a capital letter, "
                    + "a number, and a special character.");
            System.out.println("Enter your password again: ");
            password = input.nextLine();
        }
        System.out.println("Password successfully captured.");

        // Get and validate cellphone number
        System.out.println("Enter your cellphone number: ");
        cellPhone = input.nextLine();

        while (!Login.checkCellPhoneNumber(cellPhone)) {
            System.out.println("Cell number is incorrectly formatted "
                    + "or does not contain an international code; "
                    + "please correct the number and try again");
            System.out.println("Enter your cellphone number again: ");
            cellPhone = input.nextLine();
        }
        System.out.println("Cell number successfully captured.");

        // Register the user
        String registrationMessage =
                Login.registerUser(username, password, cellPhone);

        System.out.println(registrationMessage);

        // ===== Login Section =====
        System.out.println();
        System.out.println("===== Login =====");

        System.out.println("Enter your username: ");
        String enteredUsername = input.nextLine();

        System.out.println("Enter your password: ");
        String enteredPassword = input.nextLine();

        // Check login details
        boolean loginSuccessful = Login.loginUser(
                username,
                password,
                enteredUsername,
                enteredPassword);

        // Display login message
        String loginMessage = Login.returnLoginStatus(
                loginSuccessful,
                firstName,
                lastName);

        System.out.println(loginMessage);

        input.close();
    }
}
