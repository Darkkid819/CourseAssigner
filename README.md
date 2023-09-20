# Instructor Data Parsing Program README

This Java program is designed to parse instructor data from a CSV file, manipulate it, and store it as Instructor objects in an ArrayList. It can handle CSV files containing instructor information, and it is designed to work regardless of the number of instructors listed in the original data. Below, it's explained how the program extracts data and the key components of the program.

## Program Structure

The program consists of three main classes:

1. **com.excelparser.Main**: The main class contains the program's entry point. It allows users to provide a CSV file path as a command-line argument or enter it manually in the console.

2. **com.excelparser.model.InstructorList**: This class manages a list of Instructor objects and provides methods to add instructors and display their data.

3. **com.excelparser.model.Parser**: The com.excelparser.model.Parser class is responsible for reading and parsing the CSV file, extracting instructor data based on specific cell indexes, and populating Instructor objects.

## Data Extraction

### Parsing CSV Data

The program reads a CSV file, where each instructor's data is separated by a line starting with "—". It starts parsing the data after the first "—" symbol. The data for each instructor includes multiple lines containing various fields.

### Data Fields Extraction

The program extracts instructor data using a specific approach that considers the structure of the CSV file. It identifies data based on its position within the CSV structure. Each field is extracted by analyzing its position relative to the structure, without relying on explicit indices.

## Usage

To use the program, follow these steps:

1. Compile the Java source files.

2. Run the program using the following command: java com.excelparser.Main [CSV_FILE_PATH]

Replace `[CSV_FILE_PATH]` with the path to your CSV file. If you don't provide a file path, the program will prompt you to enter it manually.

3. The program will parse the data, create Instructor objects, and display the instructor information in an organized manner.

## Dependencies

This program uses standard Java libraries and does not require any external dependencies.

## Note

Make sure your CSV file follows the format specified in the program to ensure accurate parsing.

---

Author: Jordan Mitacek
Date: 9/6/23
