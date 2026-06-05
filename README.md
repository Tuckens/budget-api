# Personal Budget API

[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/Tuckens/budget-api)

This is a RESTful API for managing a personal budget, built with Spring Boot. It allows users to manage accounts, track income and expenses, and get summarized financial reports.

The application is containerized using Docker and Docker Compose for easy setup and deployment.

## Features

*   **Account Management**: Create, retrieve, and delete spending/savings accounts.
*   **Transaction Tracking**: Record income and expense transactions for each account.
*   **Automatic Balance Calculation**: Account balances are automatically updated when transactions are added or removed.
*   **Data Filtering**: Fetch transactions based on category, date range, or specific account.
*   **Financial Summary**: Get a summary of total income, total expenses, and spending grouped by category.
*   **API Documentation**: Interactive API documentation is provided using OpenAPI (Swagger UI).

## Technologies Used

*   **Backend**: Java 17, Spring Boot 3, Spring Data JPA
*   **Database**: PostgreSQL, H2 (for default profile)
*   **Build Tool**: Maven
*   **Containerization**: Docker, Docker Compose
*   **API Documentation**: Springdoc OpenAPI
*   **Utilities**: Lombok

## Getting Started

### Prerequisites

*   Java 17 or later
*   Maven
*   Docker and Docker Compose

### Running with Docker (Recommended)

The easiest way to get the application and its database running is by using Docker Compose.

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/Tuckens/budget-api.git
    cd budget-api
    ```

2.  **Build and run the services:**
    ```sh
    docker-compose up --build -d
    ```

This command will:
*   Build the Spring Boot application into a JAR file.
*   Create a Docker image for the application.
*   Start two containers: `budget_app` (the API) and `budget_postgres` (the PostgreSQL database).
*   The API will be available at `http://localhost:8080`.
*   The PostgreSQL database will be accessible on port `5432`.

### Running Locally (without Docker)

You can also run the application directly using the Maven wrapper.

1.  **Run with the default H2 in-memory database:**
    No configuration is needed. The data will be lost when the application stops.
    ```sh
    ./mvnw spring-boot:run
    ```

2.  **Run with a local PostgreSQL database:**
    *   Ensure you have a PostgreSQL server running.
    *   Create a database, user, and password matching the credentials in `docker-compose.yml` or update `src/main/resources/application-postgres.properties` with your own credentials.
    *   Run the application with the `postgres` profile active:
    ```sh
    ./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
    ```

## API Documentation

Once the application is running, you can access the interactive Swagger UI to explore and test the API endpoints.

*   **Swagger UI**: `http://localhost:8080/swagger-ui.html`
*   **OpenAPI Spec**: `http://localhost:8080/api-docs`

## API Endpoints

### Accounts (`/api/accounts`)

| Method   | Endpoint      | Description                                          |
| :------- | :------------ | :--------------------------------------------------- |
| `POST`   | `/`           | Creates a new account.                               |
| `GET`    | `/`           | Retrieves a list of all accounts.                    |
| `GET`    | `/{id}`       | Retrieves a specific account by its ID.              |
| `DELETE` | `/{id}`       | Deletes an account (only if it has no transactions). |

### Transactions (`/api/transactions`)

| Method   | Endpoint      | Description                                                                                                                              |
| :------- | :------------ | :--------------------------------------------------------------------------------------------------------------------------------------- |
| `POST`   | `/`           | Adds a new transaction (income or expense) and updates the account balance.                                                              |
| `GET`    | `/`           | Retrieves a list of transactions. Can be filtered by query parameters: `category`, `accountId`, `from` (YYYY-MM-DD), `to` (YYYY-MM-DD).    |
| `GET`    | `/{id}`       | Retrieves a specific transaction by its ID.                                                                                              |
| `DELETE` | `/{id}`       | Deletes a transaction and reverts the account balance.                                                                                   |
| `GET`    | `/summary`    | Provides a summary of income, expenses, and expenses by category. Can be filtered by `accountId`, `from` (YYYY-MM-DD), `to` (YYYY-MM-DD). |