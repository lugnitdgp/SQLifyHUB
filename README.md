
# SQLifyHUB

SQLifyHUB is a Java-based desktop application that provides a user-friendly interface for interacting with and managing PostgreSQL databases. It allows users to connect to a PostgreSQL database, create tables, add, update, and delete rows and columns, and manage database schemas, similar to the functionality offered by pgAdmin. The app leverages JavaFX for the user interface and JDBC for database connectivity.


## Tech Stack

Java, JavaFX, PostgreSQL JDBC Driver, Gradle


## Features

- **Database Connection**: Connect to any PostgreSQL database using credentials.
- **Table Management**: Create, update, and delete tables directly.
- **Row Operations**: Add, update, and delete rows with an intuitive interface.
- **Column Management**: Add or remove columns dynamically from existing tables.
- **Table Visualization**: Display and edit table contents in real-time.
- **Custom Queries**: Execute SQL queries directly on the database.
- **Role-based Access**: Read-only and full access modes for user roles.

## Setup Instructions

### Step 1: Install Prerequisites
 Ensure that you have the following installed on your system:

- Java: JDK 17 or later
- PostgreSQL: Install and configure PostgreSQL server locally.
- Maven: To handle dependencies.

### Step 2: Clone the Repository

```bash
git clone https://github.com/dawipayandas/PgAdmin_Java.git
cd PgAdmin_Java
```
### Step 3: Configure PostgreSQL Database

Ensure your PostgreSQL server is running. Create a new database using the following command:

```bash
psql -U postgres
CREATE DATABASE <db_name>;
```
You will use the name of this database when connecting to the app.

### Step 4: Build the Application
Use Gradle to build the project and resolve dependencies.

```bash
./gradlew build
```
### Step 5: Run the Application

After building, run application:

```bash
./gradlew run
```
Alternatively, if running from an IDE (such as IntelliJ IDEA or Eclipse), make sure to configure JavaFX correctly in the project settings and run the App.java class.
### Step 6: Connect to the Database

Once the application starts:

- Enter the Database name, Username, and Password in the respective fields.
-  Click the Connect button to establish the connection.

### Step 7: Start Managing Tables

-   Use the interface to create, update, delete, and view tables.
-   Perform CRUD operations on rows and columns via the intuitive GUI.
## Classes Description

- Login.java: Manages user connection to the PostgreSQL database. It captures database credentials and establishes a JDBC connection.

- Table.java: Handles the creation, modification, and deletion of database tables, columns, and rows. It includes various operations like adding new rows, removing columns, and updating existing records.

- Students.java: A sample model class representing a table structure with attributes like ID, Name, Regd, and Roll.

- TableTile.java: Manages and displays each table as a tile in the dashboard, allowing users to navigate to individual table views.
## Project Structure- Important functions
```bash
.
├── src
    ├── main
         ├── java
            └── tdoc_java
                ├── App.java                  # Main Application class
                ├── Login.java                # Manages PostgreSQL connection
                ├── Table.java                # Handles table operations (CRUD)
                ├── Students.java             # Sample class to represent table 
```