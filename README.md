# Course Assigner

The Instructors Course Assigner is a JavaFX application designed to facilitate the assignment of courses to instructors. With the ability to import extensive data, this tool is invaluable for educational institutions and departments looking to streamline the course assignment process.

## Features

- **Excel Data Import**: Seamlessly import data regarding instructors, their respective courses, and other related details using Apache POI.
- **Course & Section Information**: Get a detailed breakdown of courses and their respective sections.
- **Instructors' Teaching History**: Analyze the recent courses taught by instructors along with the frequency.
- **Smart Course Assignment**: The application intelligently assigns courses to instructors, taking into consideration various factors.
- **Filter & Sort**: Courses are filtered and sorted based on specified criteria to ensure the best match.

## Prerequisites

- Java Development Kit (JDK)
- Apache Maven
- IntelliJ IDEA (recommended)

## How to Build

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Darkkid819/ExcelParser
   ```

2. **Navigate to Project Directory**:
   ```bash
   cd ExcelParser
   ```

3. **Build with Maven**:
   ```bash
   mvn clean install
   ```

4. **Open in IntelliJ IDEA**:
    - Launch IntelliJ IDEA.
    - Choose "Open" and navigate to the project directory.
    - Let IntelliJ IDEA load the project and download the necessary dependencies.

5. **Run the Application**:
    - Once the project is loaded, right-click on the `Main` class in the project explorer.
    - Choose `Run 'Main'` to start the application.

## Dependencies

- [JavaFX](https://openjfx.io/)
- [Apache POI](https://poi.apache.org/)
- [ikonli](https://kordamp.org/ikonli/)

## License

[MIT License](LICENSE.md)