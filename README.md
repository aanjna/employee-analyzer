# employee-analyzer

# Employee Organizational Analyzer (Spring Boot)

This project is a Spring Boot application designed for analyzing employee hierarchies and organizational salary structures based on CSV employee data. The service:
- Reads employee records from a CSV file.
- Analyzes whether managers are overpaid/underpaid relative to direct subordinates.
- Identifies reporting lines that are too long.
- Exposes all functionality via clean REST APIs.

---

## Prerequisites

- Java 17+ (Java 11+ should also work)
- Maven 3.x

---

## Build & Run

1. **Clone the repository** and place your `employees.csv` file under `src/main/resources/`.

2. **Build the project:**

3. **Run the application:**


## REST API Usage

### When running locally, the following endpoints are available (default: `http://localhost:8080`):

| API                                         | Description                                         | Example Call                                                         |
|---------------------------------------------|-----------------------------------------------------|----------------------------------------------------------------------|
| `/api/underpaid-managers`                   | Lists underpaid managers                            | `GET http://localhost:8080/api/underpaid-managers`                   |
| `/api/overpaid-managers`                    | Lists overpaid managers                             | `GET http://localhost:8080/api/overpaid-managers`                    |
| `/api/reporting-line-too-long?maxAllowed=2` | Employees with a too-long reporting line | `GET http://localhost:8080/api/reporting-line-too-long?maxAllowed=2` |

Test these with:
- `curl`
- Postman
- A browser (for simple GET calls)

---

## Customizing Test Data

- To change test results, edit `src/main/resources/employees.csv` and restart the app.

---

## Example

Fetch underpaid managers:
curl http://localhost:8080/api/underpaid-managers

Run all unit and integration tests:

