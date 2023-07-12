# yumbook

This is a README file for Yumbook. It provides instructions on how to run the application locally.

## Prerequisites

Before running the application, ensure that you have the following installed on your machine:

- Java Development Kit (JDK) 17 or higher
- Maven build tool
- Docker

## Getting Started

Follow these steps to run the application locally:

1. Clone the repository:

   ```bash
   git clone https://github.com/MozThinker/yumbook.git
   cd yumbook
   ```

2. Build the application using Maven:

   ```bash
   mvn clean install
   ```

3. Set up the database:
    
   ```bash
    docker compose up -d
    ```    
    - make sure yumbook container is installed and running on your machine(it contains postgres DB and pgAmin).
    - Access the pgAdmin page: http://localhost:5050/browser/ to create the Server (yumbook) and Database (recipe). The password is: "password" without quotes.
    - Update the database configuration in `application.properties` or `application.yml` if needed.

4. Run the application:

   ```bash
   java -jar target/yumbook.jar
   ```

   The application will start and listen on the default port (e.g., 8080).

5. Access the application Swagger page to test the API:

   Open a web browser and visit http://localhost:8080/swagger-ui/index.html to access the application.

## Configuration

The application can be configured using the following environment variables or application properties:

- `APP_PORT`: The port on which the application listens (default: 8080).
- `DB_URL`: The URL of the database "jdbc:postgresql://localhost:5432/recipe".
- `DB_USERNAME`: The username for the database connection "yumbook".
- `DB_PASSWORD`: The password for the database connection "password".

You can set these variables in your environment or update the `application.yml` file.

## Testing

To run the automated tests for the application, use the following command:

```bash
mvn test
```


## Contact

If you have any questions or need further assistance, please contact [edson.mutombene@gmail.com](mailto:edson.mutombene@gmail.com).