# Cinema Application

## Introduction
The Cinema Application is a console-based system designed to assist cinema employees in managing movies, sessions, and controlling viewers. It streamlines various operations for efficiency and better customer service.

## Features

- **User Authentication**: Provides secure registration and login functionality for employees.
- **Movie Management**: Allows employees to add new movies, edit details of existing ones, or remove movies from the listings.
- **Session Management**: Enables scheduling of new sessions, updating details for current sessions, and removing past sessions.
- **Ticketing**: Facilitates ticket sales, returns, and scanning, ensuring a seamless transaction process for both staff and customers.

## Usage

After logging in, employees can perform the following actions:

1. **Add a Movie**: Create a new movie listing by specifying the title, duration, and description.
2. **Edit Movies**: Modify the details of existing movies.
3. **Add a Session**: Schedule a new session by selecting a movie and setting the start time.
4. **Edit Sessions**: Change the details of existing sessions.
5. **Sell a Ticket**: Reserve a seat by choosing the row and seat number.
6. **Scan a Ticket**: Check in a customer by marking a seat as occupied.
7. **Return a Ticket**: Cancel a reservation and free up a previously booked seat.

To perform an action, input the corresponding number in the console.

For an in-depth look at the system's structure, refer to the diagrams located in `/diagrams`.

## Installation

- Clone or download the repository.
- Navigate to the project's root directory.
- Run `src/main/kotlin/Main.kt` to start the application.

## Security

- Passwords are securely encrypted using BCrypt to ensure data safety and privacy.

## Data Storage

- All movie and session information is stored in JSON files within the `/appData` folder.
- JSON is chosen for its ease of parsing, compact syntax compared to XML, support for UTF-8 encoding, and its ability to handle hierarchical data, unlike CSV.

