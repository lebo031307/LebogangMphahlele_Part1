# LebogangMphahlele_Part1
## Project Description
This project is a Java console application created for the PROG5121 Part 1 POE. 

The application allows a user to register an account and then log in using the details that they registered with.

## Main Features
- User registration
- Username validation
- Password complexity validation
- South African cellphone number validation using Regex
- User login
- Login success and error messages
- JUnit testing

## Validation Requirements

### Username Requirements
The username must:
- Contain an underscore (_)
- Be no more than 5 characters long

Success message: "Username successfully captured."

### Password Requirements
The password must:
- Contain at least 8 characters
- Have a capital letter
- Have a number
- Have a special character

Success message: "Password successfully captured."

### Cellphone Number Requirements
The cellphone number must use the South African international code and is validated using a Java Regex pattern.

- *Example valid number:* +27838968976
- *Success message:* "Cell number successfully captured."

## Login
After registering, the user can enter their username and password to log in.
- *Success:* Displays a personalized welcome message.
- *Failure:* Displays "Username or password incorrect, please try again".

## Project Structure
- LebogangMphahlele_Part1.java: The main class controlling program flow and console interaction.
- Login.java: Contains validation logic for registration and authentication.
- LoginTest.java: Contains JUnit tests to verify the Login class methods.

## Testing
JUnit tests verify username, password, cellphone number, registration, and login functionality using both passing and failing test cases.

## Technologies Used
- Java
- NetBeans IDE
- JUnit
- Git & GitHub

## How to Run the Project
1. Open the project in NetBeans.
2. Ensure the correct JDK is selected.
3. Run LebogangMphahlele_Part1.java.
4. Follow the console prompts to register and log in.
5. Run LoginTest.java to execute the JUnit unit tests.

## References
- W3Schools - Java Regular Expressions


## Part 2 - QuickChat (PROG5121 POE Part 2)


### About this part

Part 2 adds a messaging feature called QuickChat to the registration and login program from Part 1. A user can only send messages after logging in successfully.

### Features

- Shows "Welcome to QuickChat." after a successful login.
- Asks how many messages the user wants to enter, once, when QuickChat starts.
- Numeric menu that keeps running until the user chooses Quit:
  1. Send Messages
  2. Show recently sent messages (shows "Coming Soon.")
  3. Quit
- For each message the program checks the recipient cell number (+27 followed by 9 digits), checks the message is not longer than 250 characters, generates a ten-digit Message ID, creates a Message Hash such as 00:0:HITONIGHT, and lets the user send, disregard or store the message.
- Shows the Message ID, Message Hash, Recipient and Message after a message is sent.
- Shows the total number of messages sent.
- Stored messages are written to a JSON file (messages.json).

### Message class methods

- checkMessageID() - checks the ID is not longer than ten characters
- checkRecipientCell() - checks the recipient number has the +27 international code
- createMessageHash() - builds the hash from the ID, message number, first and last word
- SentMessage() - lets the user send, disregard or store a message
- printMessages() - returns all messages sent while the program is running
- returnTotalMessages() - returns the total number of messages sent
- storeMessage() - stores messages in a JSON file

### Project structure

- src/lebogangmphahlele_part1 - Login.java, Message.java, LebogangMphahlele_Part1.java
- test/lebogangmphahlele_part1 - LoginTest.java, MessageTest.java
- lib - junit-4.12.jar and hamcrest-core-1.3.jar (used by GitHub Actions)
- .github/workflows - TestJava.yml

### How to run

1. Open the project in NetBeans (JDK 1.8).
2. Run LebogangMphahlele_Part1.java.
3. Register, log in, then use the QuickChat menu.

### Test data used

- Message 1: recipient +27718693002, message "Hi Mike, can you join us for dinner tonight?", choice Send.
- Message 2: recipient 08575975889 (rejected, no international code), message "Hi Keegan, did you receive the payment?", choice Disregard.

### Unit tests

Run MessageTest.java in NetBeans (right-click, Test File). The tests cover message length (success and failure), recipient number (success and failure), the message hash for Test Case 1 and the other hashes in a loop, the Message ID, send, disregard and store, the total number of messages sent, and printing the sent messages. The tests also run automatically on GitHub through GitHub Actions (TestJava.yml) every time code is pushed.

### References

 * Farrell, J. (2019) Java Programming. 9th edn. Boston: Cengage Learning.
 * W3Schools (n.d.) Java Tutorial. Available at: https://www.w3schools.com/java/ (Accessed: 17 October 2026).
 * W3Schools (n.d.) Java Create and Write To Files. Available at: https://www.w3schools.com/java/java_files_create.asp (Accessed: 17 October 2026

# QuickChat - PROG5121 POE (Parts 1, 2 and 3)

## About the project

QuickChat is a Java console application built in three parts. Every part adds
to the same project, so the final version has Part 1, Part 2 and Part 3 working
together: the user registers, logs in, sends messages, and then works with the
stored messages.

## How the project grew

### Part 1 - Registration and login
- The user enters a first name, last name, username, password and cell number.
- The username must contain an underscore and be at most five characters.
- The password must have at least eight characters, a capital letter, a number
  and a special character.
- The cell number must be +27 followed by nine digits (checked with a regex).
- The user then logs in and gets a welcome or an error message.
- LoginTest checks each of these methods.

### Part 2 - QuickChat messaging
- QuickChat only starts after a successful login and shows "Welcome to QuickChat."
- The user sets how many messages to enter, once, when QuickChat starts.
- A numeric menu runs in a while loop: 1 Send Messages, 2 Show recently sent
  messages, 3 Quit.
- A for loop handles each message. The loop counter is the message number.
- Each message has a recipient check, a 250-character limit, a random ten-digit
  ID built in a loop, and a hash such as 00:0:HITONIGHT.
- The user can send, disregard or store each message. Stored messages are
  written to messages.json.
- MessageTest checks the Message class methods.

### Part 3 - Arrays, stored messages and reports
- Every message is kept in arrays, and a new fourth main menu option, Stored
  Messages, lets the user search, delete and report on them.

## What changed from Parts 1 and 2, and why

| Area | In Parts 1 and 2 | In Part 3 | Why |
|---|---|---|---|
| Where messages are kept | The Message class had its own lists and a counter | The new StoredMessages class keeps parallel arrays (ID, hash, recipient, text, flag) and sent, disregarded and stored arrays. Message now uses them | One place for the data, so searching and deleting never leave a second copy behind |
| Main menu option 2 | Showed "Coming Soon." | Shows a report of all sent messages | The feature the Part 2 brief described as still in development is now finished |
| Main menu option 4 | Did not exist | Stored Messages menu with six actions | Required by Part 3 |
| JSON file | Only written. Each run saved only that run's stored messages | Read back into the stored array when QuickChat starts and when the Stored Messages menu opens. Deleting a message updates the file | Stored messages now survive a restart |
| Disregarded messages | Only displayed "Press 0 to delete the message." | Also saved in the disregarded array for the session | Part 3 needs an array of disregarded messages |
| Recipient check in Message | +27 followed by nine digits only | Also accepts 0 followed by nine digits | The Part 3 test data uses a local number (0838884567) |
| Unit tests | LoginTest and MessageTest | Same tests, still passing, plus StoredMessagesTest | Tests the arrays, JSON reading, searches, delete and reports with the POE test data |
| GitHub Actions | Ran two test classes | Runs LoginTest, MessageTest and StoredMessagesTest. The lib folder also holds json-simple | Reading the JSON file needs the json-simple jar |
| Comments | Short comments | Every class and method has a comment, and sources are listed | Easier to read and the sources are credited |

Part 1 (registration and login) was not changed.

## Stored Messages menu (main menu option 4)
When the menu opens, messages.json is read into the stored messages array and
shown. The user can then:
1. Display the sender and recipient of all stored messages
2. Display the longest stored message
3. Search for a message ID and show the recipient and message
4. Search for all sent or stored messages for a recipient
5. Delete a stored message using its message hash
6. Display a report of all stored messages
7. Go back to the main menu

## Test data
| No | Recipient | Message | Action |
|---|---|---|---|
| 1 | +27834557896 | Did you get the cake? | Sent |
| 2 | +27838884567 | Where are you? You are late! I have asked you to be on time. | Stored |
| 3 | +27834484567 | Yohoooo, I am at your gate. | Disregarded |
| 4 | 0838884567 | It is dinner time! | Sent |
| 5 | +27838884567 | Ok, I am leaving without you. | Stored |

## Project structure
- src/lebogangmphahlele_part1: LebogangMphahlele_Part1.java (main), Login.java,
  Message.java, StoredMessages.java
- test/lebogangmphahlele_part1: LoginTest.java, MessageTest.java,
  StoredMessagesTest.java
- lib: junit-4.12.jar, hamcrest-core-1.3.jar, json-simple-1.1.jar
- .github/workflows/TestJava.yml: runs all tests on every push

## How to run
1. Open the project in NetBeans (JDK 1.8). json-simple-1.1.jar must be in the
   project Libraries.
2. Run LebogangMphahlele_Part1.java.
3. Register, log in, then use the QuickChat menu.
4. To run the tests, right-click a test file and choose Test File.

## Assumptions
- The sender of a message is the logged-in user's full name.
- The recipient search covers sent and stored messages, not disregarded ones.
- Only stored messages can be deleted by hash. The success message leaves out
  the final full stop, like the example in the POE.
- The unit tests use 0838884567 as the ID of message 4 and a placeholder
  recipient, because the POE gives no recipient for that message.

## References
- Farrell, J. (n.d.) Java Programming. Cengage.
- W3Schools (n.d.) Java Tutorial. Available at: https://www.w3schools.com/java/
- W3Schools (n.d.) Java ArrayList. Available at: https://www.w3schools.com/java/java_arraylist.asp
- json-simple library. Available at: https://github.com/fangyidong/json-simple
