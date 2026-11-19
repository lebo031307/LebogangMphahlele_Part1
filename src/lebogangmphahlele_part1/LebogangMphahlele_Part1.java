package lebogangmphahlele_part1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class for the PROG5121 POE (Parts 1, 2 and 3).
 *
 * Part 1: the user registers and logs in.
 * Part 2: after a successful login, the user can send, disregard or store
 *         messages in QuickChat.
 * Part 3: the messages are kept in arrays (see StoredMessages) and the user can
 *         search, delete and report on the stored messages.
 *
 * References: Farrell (Java Programming) for strings, loops and arrays;
 * W3Schools "Java Tutorial" (https://www.w3schools.com/java/).
 */
public class LebogangMphahlele_Part1 {

    /**
     * Runs the program: registration, login and then QuickChat.
     *
     * @param args the command line arguments (not used)
     */
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
            System.out.print("Enter your username again: ");
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
            System.out.print("Enter your password again: ");
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
            System.out.print("Enter your cellphone number again: ");
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

            // Part 3: read the messages stored in earlier runs from the JSON
            // file, so that new stored messages are added to them and the
            // file is not overwritten.
            StoredMessages.loadStoredMessages();

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
            // Option 4 (Stored Messages) was added in Part 3.
            while (!quit) {
                System.out.println();
                System.out.println("1) Send Messages");
                System.out.println("2) Show recently sent messages");
                System.out.println("3) Quit");
                System.out.println("4) Stored Messages");
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
                    // Part 3: this option now shows the sent messages report.
                    System.out.println(Message.printMessages());
                } else if (choice.equals("3")) {
                    quit = true;
                    System.out.println("Thank you for using QuickChat.");
                } else if (choice.equals("4")) {
                    runStoredMessagesMenu(input, firstName + " " + lastName);
                } else {
                    System.out.println("Invalid option. Please choose 1, 2, 3 or 4.");
                }
            }
        }

        input.close();
    }

    /**
     * Runs the "Stored Messages" menu (main menu option 4, added in Part 3).
     * It first reads the JSON file into the stored messages array and shows
     * what was read. Then it lets the user choose one of six actions, and
     * keeps asking until the user chooses to go back to the main menu.
     *
     * References: Farrell (Java Programming) for arrays and loops;
     * W3Schools "Java ArrayList" and "Java While Loop".
     *
     * @param input  the Scanner used to read the user's choices
     * @param sender the name of the logged-in user, shown as the sender
     */
    public static void runStoredMessagesMenu(Scanner input, String sender) {

        System.out.println();
        System.out.println("===== Stored Messages =====");

        // Read the JSON file into the stored messages array and display it.
        if (StoredMessages.loadStoredMessages()) {
            ArrayList<String> stored = StoredMessages.getStoredMessages();

            System.out.println("Messages read from the JSON file: " + stored.size());
            for (int i = 0; i < stored.size(); i++) {
                System.out.println((i + 1) + ") " + stored.get(i));
            }
        } else {
            System.out.println("No stored messages could be read.");
        }

        boolean back = false;

        while (!back) {
            System.out.println();
            System.out.println("1) Display the sender and recipient of all stored messages");
            System.out.println("2) Display the longest stored message");
            System.out.println("3) Search for a message ID");
            System.out.println("4) Search for all messages to a recipient");
            System.out.println("5) Delete a message using its message hash");
            System.out.println("6) Display a report of all stored messages");
            System.out.println("7) Back to the main menu");
            System.out.println("Choose an option: ");

            String choice = input.nextLine();

            if (choice.equals("1")) {

                ArrayList<String> lines = StoredMessages.getSendersAndRecipients(sender);

                if (lines.isEmpty()) {
                    System.out.println("There are no stored messages.");
                }
                for (int i = 0; i < lines.size(); i++) {
                    System.out.println(lines.get(i));
                }

            } else if (choice.equals("2")) {

                String longest = StoredMessages.getLongestStoredMessage();

                if (longest == null) {
                    System.out.println("There are no stored messages.");
                } else {
                    System.out.println("Longest stored message: " + longest);
                }

            } else if (choice.equals("3")) {

                System.out.println("Enter the message ID: ");
                String id = input.nextLine();

                String message = StoredMessages.searchByMessageID(id);

                if (message == null) {
                    System.out.println("Message ID not found.");
                } else {
                    System.out.println("Recipient: "
                            + StoredMessages.getRecipientByMessageID(id));
                    System.out.println("Message: " + message);
                }

            } else if (choice.equals("4")) {

                System.out.println("Enter the recipient's cell number: ");
                String recipient = input.nextLine();

                ArrayList<String> found = StoredMessages.searchByRecipient(recipient);

                if (found.isEmpty()) {
                    System.out.println("No messages found for that recipient.");
                }
                for (int i = 0; i < found.size(); i++) {
                    System.out.println((i + 1) + ") " + found.get(i));
                }

            } else if (choice.equals("5")) {

                System.out.println("Enter the message hash: ");
                String hash = input.nextLine();

                System.out.println(StoredMessages.deleteMessageByHash(hash));

            } else if (choice.equals("6")) {

                System.out.println(StoredMessages.getStoredReport());

            } else if (choice.equals("7")) {
                back = true;
            } else {
                System.out.println("Invalid option. Please choose 1 to 7.");
            }
        }
    }
}