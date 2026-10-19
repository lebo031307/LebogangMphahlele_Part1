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
-
