# Contributing to System Prompt March 2025

We welcome contributions to the System Prompt March 2025 project! This document provides guidelines for contributing to the project.

## Development Environment Setup

1. **Prerequisites**:
   - Java JDK 17 or higher
   - Gradle 8.0+ or Maven 3.8+
   - Python 3.9+
   - Magic package management system
   - Max engine for Mojo
   - C++ compiler (for C++ components)
   - Swift compiler (for Swift components)

2. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd system-prompt-march2025
   ```

3. **Build the project**:
   ```bash
   ./gradlew build
   ```

## Code Organization

- Java code should be placed in `src/main/java/com/uplift/system/`
- Python code should be placed in `src/main/python/uplift/system/`
- C++ code should be placed in `src/main/cpp/uplift/system/`
- Swift code should be placed in `src/main/swift/uplift/system/`
- Mojo code should be placed in `src/main/mojo/uplift/system/`
- Tests should be placed in the corresponding `src/test/` directories

## Pull Request Process

1. Fork the repository
2. Create a branch for your feature or bugfix
3. Add tests for your changes
4. Ensure all tests pass with `./gradlew test`
5. Submit a pull request with a clear description of the changes

## Coding Standards

### Java
- Follow Google Java Style Guide
- Include Javadoc comments for public APIs

### Python
- Follow PEP 8 style guide
- Include docstrings for all functions and classes

### C++
- Follow Google C++ Style Guide
- Include comments for complex logic

### Swift
- Follow Apple's Swift API Design Guidelines
- Include documentation comments for public APIs

### Mojo
- Follow the emerging Mojo style conventions
- Document all public functions and types

## License

By contributing to this project, you agree that your contributions will be licensed under the project's MIT license. 