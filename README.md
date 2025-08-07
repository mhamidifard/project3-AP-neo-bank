# AP Neo Bank – Java Banking Application

A Java-based banking application (Project 3 – AP Neo Bank) that simulates core banking functionalities, offering a solid foundation for learning and extending financial systems in Java.

##  Key Features

- **Account Management**: Create, view, and manage customer accounts.
- **Transactions**: Deposit, withdraw, and transfer between accounts.
- **Secure Operations**: (Optional) User authentication or role-based access if implemented.
- Clean, modular codebase following **OOP best practices**.

##  Technology Stack

- **Language**: Java (e.g., Java 17 or Java 11)  
- **Build Tool**: Gradle (see `build.gradle`)  
- **Testing**: JUnit  
- **Version Control**: Git  
- **CI/CD**: (Add if configured, e.g., GitHub Actions or GitLab CI/CD via `.gitlab-ci.yml`)

##  Getting Started

### Prerequisites

- Java SDK (JDK 11 or 17) installed  
- Gradle wrapper included (`gradlew`)  

### Build & Run

1. Clone the repository:
   ```bash
   git clone https://github.com/mhamidifard/project3-AP-neo-bank.git
   cd project3-AP-neo-bank
   ```

2. Build the project:
   ```bash
   ./gradlew clean build
   ```

3. Run the application (if a main class is defined, replace `com.example.Main` accordingly):
   ```bash
   java -cp build/libs/project3-AP-neo-bank.jar com.example.Main
   ```

##  Project Structure

```
.
├── src
│   ├── main
│   │   └── java
│   │       └── …        # Your Java packages and classes
│   └── test
│       └── java
│           └── …        # JUnit test classes
├── build.gradle         # Project build configuration
├── gradlew/gradlew.bat  # Gradle wrappers
├── settings.gradle
├── .gitlab-ci.yml       # (Optional) CI/CD configuration
└── README.md
```

##  Running Tests

Execute the full test suite:
```bash
./gradlew test
```
