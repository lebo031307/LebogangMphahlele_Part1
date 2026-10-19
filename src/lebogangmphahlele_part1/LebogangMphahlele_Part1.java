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

        // Part 2: messages can only be sent after a successful login.
        // References: Farrell (Java Programming) for while and for loops;
        // W3Schools "Java While Loop" and "Java For Loop".
        if (loginSuccessful) {
            System.out.println();
            System.out.println("Welcome to QuickChat.");

            // The user sets the number of messages once, when QuickChat starts.
            System.out.println("How many messages do you wish to enter? ");
            int numberOfMessages = 0;
            boolean validNumber = false;

            while (!validNumber) {
                try {
                    numberOfMessages = Integer.parseInt(input.nextLine());

                    if (numberOfMessages > 0) {
                        validNumber = true;
                    } else {
                        System.out.println("Enter a number greater than zero: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid whole number: ");
                }
            }

            boolean messagesEntered = false;
            boolean quit = false;

            // The application runs until the user chooses 3 (Quit).
            while (!quit) {
                System.out.println();
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.println("Choose an option: ");

                String choice = input.nextLine();

                if (choice.equals("1")) {

                    if (messagesEntered) {
                        System.out.println("You have already entered your "
                                + numberOfMessages + " message(s).");
                    } else {

                        // The loop runs once for each message the user set.
                        // The loop counter i is used as the message number.
                        for (int i = 0; i < numberOfMessages; i++) {
                            System.out.println();
                            System.out.println("Message " + (i + 1)
                                    + " of " + numberOfMessages);

                            System.out.println("Enter recipient number (+27...): ");
                            String recipient = input.nextLine();

                            while (!Message.checkRecipientCell(recipient)
                                    .equals("Cell phone number successfully captured.")) {
                                System.out.println(
                                        Message.checkRecipientCell(recipient));
                                System.out.println("Enter recipient number again: ");
                                recipient = input.nextLine();
                            }
                            System.out.println(
                                    Message.checkRecipientCell(recipient));

                            System.out.println("Enter your message: ");
                            String messageText = input.nextLine();

                            while (!Message.checkMessageLength(messageText)
                                    .equals("Message ready to send.")) {
                                System.out.println(
                                        Message.checkMessageLength(messageText));
                                System.out.println("Enter your message again: ");
                                messageText = input.nextLine();
                            }
                            System.out.println(
                                    Message.checkMessageLength(messageText));

                            Message newMessage = new Message(
                                    i, recipient, messageText);

                            System.out.println(newMessage.getMessageIDStatus());
                            System.out.println("Message Hash: "
                                    + newMessage.createMessageHash());

                            String result = newMessage.SentMessage(input);

                            System.out.println(result);

                            if (result.equals("Message successfully sent.")) {
                                System.out.println(newMessage.printMessageDetails());
                            }
                        }

                        messagesEntered = true;

                        System.out.println();
                        System.out.println("Total messages sent: "
                                + Message.returnTotalMessages());
                    }

                } else if (choice.equals("2")) {
                    System.out.println("Coming Soon.");
                } else if (choice.equals("3")) {
                    quit = true;
                    System.out.println("Thank you for using QuickChat.");
                } else {
                    System.out.println("Invalid option. Please choose 1, 2 or 3.");
                }
            }
        }

        input.close();
    }
}
