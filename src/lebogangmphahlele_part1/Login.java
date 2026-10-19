package lebogangmphahlele_part1;

public class Login {

    // Check if the username contains an underscore
    // and is no more than 5 characters long.
    public static boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Add password complixity validation
    // Check password complexity 
    public static boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasCapitalLetter = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (int i = 0; i < password.length(); i++) {
            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                hasCapitalLetter = true;
            }
            if (Character.isDigit(character)) {
                hasNumber = true;
            }
            if (!Character.isLetterOrDigit(character)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapitalLetter && hasNumber && hasSpecialCharacter;
    }

    // Add cellphone number validation
    // Check South African cellphone number using Regex.
    // Regex reference: W3Schools Java Regular Expressions
    public static boolean checkCellPhoneNumber(String cellPhone) {
        String cellPhonePattern = "^\\+27[0-9]{9}$";
        return cellPhone.matches(cellPhonePattern);
    }

    // Register the user
    public static String registerUser(String username,
            String password, String cellPhone) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure "
                    + "that your username contains an underscore and is "
                    + "no more than five characters in length.";

        } else if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure "
                    + "that the password contains at least eight characters, "
                    + "a capital letter, a number, and a special character.";

        } else if (!checkCellPhoneNumber(cellPhone)) {
            return "Cell number is incorrectly formatted or does not "
                    + "contain an international code; please correct the "
                    + "number and try again";

        } else {
            return "The user has been registered successfully.";
        }
    }

    // Check if the login details match the registered details
    public static boolean loginUser(String registeredUsername,
            String registeredPassword,
            String enteredUsername,
            String enteredPassword) {

        return registeredUsername.equals(enteredUsername)
                && registeredPassword.equals(enteredPassword);
    }

    // Return the login status message
    public static String returnLoginStatus(boolean isLoggedIn,
            String firstName, String lastName) {

        if (isLoggedIn) {
            return "Welcome " + firstName + ", "
                    + lastName
                    + " it is great to see you again.";

        } else {
            return "Username or password incorrect, please try again";
        }
    }
}
