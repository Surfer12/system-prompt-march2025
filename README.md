# System Prompt March 2025 Project

## Overview
A metaphorical exploration of technological interoperability and creative cognitive processes.

## Project Structure
- `src/main/java/com/uplift/system/`: Java source code
  - `gate/`: Modules for technology connection and event management
  - `library/`: Narrative and storytelling components
- `src/main/python/uplift/system/`: Python modules
  - `gate/`: Python equivalents of gate functionality
  - `library/`: Python storytelling components
- `src/main/cpp/uplift/system/`: C++ components
- `src/main/swift/uplift/system/`: Swift components
- `src/main/mojo/uplift/system/`: Mojo components
- `src/test/`: Test code for all languages
- `config/`: Configuration files
- `docs/`: Documentation
  - `specs/`: Technical specifications
  - `guides/`: User guides
  - `api/`: API documentation
  - `examples/`: Example code and usage
- `scripts/`: Utility scripts

## Prerequisites
- Java 17+
- Gradle 8.0+ or Maven 3.8+
- Python 3.9+
- Magic package management
- Max engine (for Mojo integration)

## Build and Run

### Using Gradle
```bash
# Build the project
./gradlew build

# Run system information task
./gradlew systemInfo

# Run interactive narrative
./gradlew interactiveNarrative

# Generate StoryTeller report
./gradlew generateStoryTellerReport

# Create distribution package
./gradlew createSystemDistribution
```

### Using Maven
```bash
# Build the project
mvn clean install

# Run the StoryTeller
mvn exec:java -Dexec.mainClass="com.uplift.system.library.StoryTeller"
```

### Using Python
```bash
# Install the package
cd src/main/python
pip install -e .

# Run the Python StoryTeller
python -m uplift.system.library.story_teller
```

## Key Components
1. **Events**: Flexible event management system
2. **Connectors**: Technology interoperability framework
3. **Librarian**: Central system orchestration
4. **StoryTeller**: Interactive narrative interface

## Creative Principles
- Dynamic emergence
- Recursive reflection
- Adaptive reasoning
- Systematic integration

## Licensing
MIT License

## Contributing
Please see our contribution guidelines in docs/guides/contributing.md. 