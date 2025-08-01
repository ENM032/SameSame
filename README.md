# SameSame - Secure Password Comparator

A lightweight, modern, and secure desktop application for comparing password inputs to ensure they match exactly. Built with Java and JavaFX, featuring a clean UI and robust security measures.

## Features

### 🔒 Security
- **Constant-time comparison** - Prevents timing attacks
- **Memory clearing** - Sensitive data is cleared from memory after use
- **No password storage** - Passwords are never stored or logged
- **Secure hashing** - SHA-256 hashing capability for additional security operations

### 🎨 User Interface
- **Modern design** - Clean, professional appearance with CSS styling
- **Real-time comparison** - Instant feedback as you type (debounced for performance)
- **Password visibility toggle** - Option to show/hide passwords
- **Password strength indicator** - Real-time strength evaluation
- **Responsive layout** - Adapts to different screen sizes

### 🚀 Performance
- **Lightweight** - Minimal resource usage
- **Fast comparison** - Optimized algorithms for quick response
- **Debounced input** - Prevents excessive processing during typing

### 🧪 Quality Assurance
- **Comprehensive testing** - Full test suite with edge cases
- **Security testing** - Tests for timing attacks and memory safety
- **Performance testing** - Ensures fast response times

## Technology Stack

- **Java 24.0.2** - Latest Java with cutting-edge features
- **JavaFX 19** - Rich desktop UI framework
- **Maven** - Dependency management and build tool
- **JUnit 5** - Testing framework
- **CSS3** - Modern styling

## Project Structure

```
SameSame/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/samesame/
│   │   │       ├── PasswordComparatorApp.java     # Main application class
│   │   │       ├── controller/
│   │   │       │   └── MainController.java        # UI controller
│   │   │       └── service/
│   │   │           └── PasswordComparator.java    # Core comparison logic
│   │   └── resources/
│   │       ├── fxml/
│   │       │   └── main-view.fxml                 # UI layout
│   │       └── css/
│   │           └── styles.css                     # Application styling
│   └── test/
│       └── java/
│           └── com/samesame/service/
│               └── PasswordComparatorTest.java    # Comprehensive tests
├── pom.xml                                        # Maven configuration
└── README.md                                      # This file
```

## Prerequisites

- **Java 24.0.2 or higher** - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Maven 3.6+** - Download from [Apache Maven](https://maven.apache.org/download.cgi)

## Installation & Running

### Option 1: Using Maven (Recommended if available)

1. **Clone or download** the project to your local machine

2. **Navigate to the project directory**:
   ```bash
   cd SameSame
   ```

3. **Compile and run**:
   ```bash
   mvn clean javafx:run
   ```

### Option 2: Console Version (No JavaFX Required) - RECOMMENDED

1. **Navigate to the project directory**:
   ```cmd
   cd SameSame
   ```

2. **Run the demo** (Windows):
   ```cmd
   run-demo.bat
   ```

3. **Or compile and run manually**:
   ```cmd
   mkdir build\classes
   javac -d build\classes -cp "src\main\java" src\main\java\com\samesame\service\PasswordComparator.java src\main\java\com\samesame\DemoRunner.java src\main\java\com\samesame\ConsolePasswordComparator.java
   ```

4. **Run the demo**:
   ```cmd
   java -cp build\classes com.samesame.DemoRunner
   ```

5. **Run interactive console version**:
   ```cmd
   java -cp build\classes com.samesame.ConsolePasswordComparator
   ```

### Option 3: GUI Version (Requires JavaFX)

1. **Download JavaFX** from [OpenJFX.io](https://openjfx.io/)

2. **Compile GUI version**:
   ```cmd
   mkdir build\classes\fxml build\classes\css
   javac --module-path "path\to\javafx\lib" --add-modules javafx.controls,javafx.fxml -d build\classes -cp "src\main\java" src\main\java\com\samesame\*.java src\main\java\com\samesame\controller\*.java src\main\java\com\samesame\service\*.java
   copy "src\main\resources\fxml\*.fxml" "build\classes\fxml\"
   copy "src\main\resources\css\*.css" "build\classes\css\"
   ```

3. **Run GUI version**:
   ```cmd
   java --module-path "path\to\javafx\lib" --add-modules javafx.controls,javafx.fxml -cp build\classes com.samesame.PasswordComparatorApp
   ```

### Option 3: IDE Integration

1. **Import** the project into your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)
2. **Ensure** Java 24+ and JavaFX are configured
3. **Run** the `PasswordComparatorApp.java` main class

## Usage

1. **Launch** the application
2. **Enter** your password in the first field
3. **Confirm** by entering the same password in the second field
4. **View** real-time comparison results and password strength
5. **Toggle** password visibility if needed
6. **Use** the Compare button for manual comparison or Clear to reset

## Testing

### With Maven:
```bash
mvn test
```

### Manual Testing:
```cmd
javac -d build\test-classes -cp "build\classes;junit-platform-console-standalone.jar" src\test\java\com\samesame\service\*.java
java -cp "build\classes;build\test-classes;junit-platform-console-standalone.jar" org.junit.platform.console.ConsoleLauncher --class-path build\test-classes --scan-class-path
```

*Note: You'll need to download JUnit 5 standalone JAR for manual testing*

The tests cover:
- Basic password comparison functionality
- Security edge cases (null inputs, timing attacks)
- Password strength evaluation
- Performance benchmarks
- Unicode and special character handling

## Security Considerations

### What Makes This Secure?

1. **Constant-time comparison** - Prevents attackers from determining password similarity through timing analysis
2. **Memory clearing** - Sensitive data is overwritten in memory after use
3. **No persistence** - Passwords are never written to disk or logs
4. **Input validation** - Proper handling of edge cases and malicious inputs

### Best Practices Implemented

- Character arrays used instead of strings for sensitive data
- Immediate clearing of sensitive data after use
- Secure random number generation for any cryptographic operations
- Protection against common attack vectors

## Future Enhancements

### Mobile App Adaptation
- **React Native** or **Flutter** implementation
- Touch-friendly UI adaptations
- Biometric integration

### Web App Version
- **React** or **Vue.js** frontend
- **Node.js** backend with similar security measures
- Progressive Web App (PWA) capabilities

### Additional Features
- Password generation with customizable criteria
- Batch password comparison
- Integration with password managers
- Accessibility improvements (screen reader support)
- Dark mode theme
- Internationalization (i18n) support

## Contributing

Contributions are welcome! Please ensure:

1. **Security** - Any changes maintain or improve security posture
2. **Testing** - New features include comprehensive tests
3. **Documentation** - Update README and code comments as needed
4. **Code Style** - Follow existing Java conventions

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Feel free to use, modify, and distribute according to the terms of the MIT License.

## Support

For issues, questions, or suggestions:
1. Check existing documentation
2. Review test cases for usage examples
3. Create detailed issue reports with steps to reproduce

---

**Built with ❤️ for secure password management**