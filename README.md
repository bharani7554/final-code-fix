# CSV to Excel Processor

Maven Java application that reads employee data from CSV, calculates payroll values, and writes an Excel report. The project is configured for SonarQube analysis and GitHub Actions quality-gate checks.

## Requirements

- JDK 17+
- Maven 3.8+, or the included Maven Wrapper

## Run

Windows:

```bat
mvnw.cmd -q clean verify
mvnw.cmd -q exec:java
```

Linux/macOS:

```bash
./mvnw -q clean verify
./mvnw -q exec:java
```

The default output is `payroll-report.xlsx`.

You can also provide custom input and output paths:

```bash
java -jar target/csv-excel-processor-1.0.0-SNAPSHOT.jar path/to/employees.csv payroll-report.xlsx
```

## Configuration and security

No passwords or API keys are committed to the source code. Optional database settings are read from environment variables:

- `HR_DB_URL`
- `HR_DB_USER`
- `HR_DB_PASSWORD`
- `REPORT_ADMIN_PASSWORD`
- `REPORT_API_KEY_PREFIX`

The database helper uses a prepared statement and does not execute operating-system commands from user input.

## SonarQube

Run a local scan with:

```bash
./mvnw -q clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=<TOKEN>
```

JaCoCo generates the XML coverage report at `target/site/jacoco/jacoco.xml`, which is used by SonarQube.

## GitHub Actions

`.github/workflows/quality-gate.yml` starts SonarQube Community LTS as a service, runs the tests and coverage analysis, scans the project, waits for the quality gate, and uploads the Sonar report as an artifact.

The workflow is triggered on pushes to `main`, pull requests, and manual `workflow_dispatch`.

The repository should be pushed with the updated source and test files from this package so that the quality-gate run analyzes the corrected code rather than the old baseline.
