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
