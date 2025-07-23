Repository: krypt03/student-activity-management-system---java--
Files analyzed: 4

Estimated tokens: 4.1k

Directory structure:
└── krypt03-student-activity-management-system---java--/
    └── Student Activity Management System/
        ├── thisula.iml
        ├── out/
        │   └── production/
        │       └── thisula/
        └── src/
            ├── Main.java
            ├── Module.java
            └── Student.java


================================================
FILE: Student Activity Management System/thisula.iml
================================================
<?xml version="1.0" encoding="UTF-8"?>
<module type="JAVA_MODULE" version="4">
  <component name="NewModuleRootManager" inherit-compiler-output="true">
    <exclude-output />
    <content url="file://$MODULE_DIR$">
      <sourceFolder url="file://$MODULE_DIR$/src" isTestSource="false" />
    </content>
    <orderEntry type="inheritedJdk" />
    <orderEntry type="sourceFolder" forTests="false" />
  </component>
</module>



================================================
FILE: Student Activity Management System/src/Main.java
================================================
import java.util.Scanner;

public class Main {
    public static final Scanner userInput = new Scanner(System.in);// Since the scanner object is final. It can't be modified by others.
    public static Module module = new Module(); // Creates an instance called module from the Module Class

    // Defining menu method
    public static void menu() {
        int choice;
        System.out.println("----Menu----");
        System.out.println("1. Check available seats");
        System.out.println("2. Register student (with ID)");
        System.out.println("3. Delete student");
        System.out.println("4. Find student (with student ID)");
        System.out.println("5. Store student details into a file");
        System.out.println("6. Load student details from the file to the system");
        System.out.println("7. View the list of students based on their names");
        System.out.println("8. Additional controls");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
        choice = userInput.nextInt();
        userInput.nextLine(); // Consume newline inorder to prevent input buffers

        switch (choice) {
            case 1:
                System.out.println("Available seats: " + module.checkAvailableSeats()); // using a method from the module class to find the remaining seats
                menu(); //recursion method
                break;
            case 2:
                System.out.print("Enter student ID: ");
                String studentID = userInput.nextLine();
                module.registerStudent(studentID); // calls the registerStudent method from module class to register the student
                menu();
                break;
            case 3:
                System.out.print("Enter student ID to delete: ");
                String deleteID = userInput.nextLine();
                module.deleteStudent(deleteID); // calls the deleteStudent method from the module class to delete the student
                menu();
                break;
            case 4:
                System.out.print("Enter student ID to find: ");
                String findID = userInput.nextLine();
                Student student = module.findStudent(findID); // calls the findStudent method from the module class to find the student by using the ID
                if (student != null) { // checking if the given input is empty or not
                    System.out.println("Student found: " + student.getStudentName());// calling the getter from the student method to retrieve name
                } else {
                    System.out.println("Student not found.");
                }
                menu();
                break;
            case 5:
                System.out.print("Enter filename to store data: ");
                String storeFilename = userInput.nextLine();
                module.storeStudentDetails(storeFilename); // calls the storeStudentDetails from the Module class to store data
                menu();
                break;
            case 6:
                System.out.print("Enter filename to load data: ");
                String loadFilename = userInput.nextLine();
                module.loadStudentDetails(loadFilename); // calls the loadStudentDetails from the Module class to load data
                menu();
                break;
            case 7:
                System.out.println("Students sorted based on their names:");
                module.sortByName(); //accessed using the object
                menu();
                break;
            case 8:
                //This is the sub menu for the additional methods
                System.out.println("Additional controls:");
                System.out.println("a. Add student name");
                System.out.println("b. Add module marks");
                System.out.println("c. Generate summary of the system");
                System.out.println("d. Generate complete report");
                System.out.print("Enter your choice: ");
                char subChoice = userInput.next().charAt(0); // captures the user's input
                userInput.nextLine(); // Consume newline

                switch (subChoice) {
                    case 'a':
                        System.out.print("Enter student ID: ");
                        String updateID = userInput.nextLine();
                        System.out.print("Enter student name: ");
                        String updateName = userInput.nextLine();
                        module.addStudentName(updateID, updateName); // calls the addStudentName method from the Module to update the student name
                        break;
                    case 'b':
                        System.out.print("Enter student ID: ");
                        String marksID = userInput.nextLine();
                        for (int i = 0; i < 3; i++) { // iterates three times to get module marks from the user
                            System.out.print("Enter marks for module " + (i + 1) + ": ");
                            double mark = userInput.nextInt();
                            module.addModuleMark(marksID, i + 1, mark); //Update the student's details using the addModuleMark from the Module class
                        }
                        break;
                    case 'c':
                        module.generateSummary(); // calls the generateSummer method from the Module class to display the summary
                        break;
                    case 'd':
                        module.generateCompleteReport(); //  create a detailed summary of the system
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again."); // if wrong value is inputted by the user this message will show
                        break;
                }
                menu();
                break;
            case 9:
                System.out.println("Exiting the system. Goodbye!"); // Exits the program
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                menu();
                break;
        }
    }
    public static void main(String[] args) {
        menu();// Calls the menu method
        userInput.close();// closing the scanner object in order to stop the memory leakage
    }
}



================================================
FILE: Student Activity Management System/src/Module.java
================================================
    import java.io.*;
    import java.util.Arrays; //Importing essential classes

    public class Module {
        private static final int maximumStudentCount = 100; // used the final data type inorder to stop users from accessing it (constant)
        Student[] students; // creating one dimensional array to store student data (object array)
        private int size;

        public Module() { // Module constructor
            students = new Student[maximumStudentCount];
            size = 0; // helps to keep the count of registered students
        }

        public int checkAvailableSeats() { // This method will calculate the available seats
            return maximumStudentCount - size;
        }
        // Creating the registerStudent method as a non-static method
        public void registerStudent(String studentID) {
            if (size >= maximumStudentCount) { // check if there's enough space for a new student
                System.out.println("No available seats. Registration failed.");
                return;
            }
            if (!isValidStudentID(studentID)) { //Check if the given input is valid ( checks the format using regular expressions)
                System.out.println("Invalid student ID format. Registration failed.");
                return;
            }
            if (findStudent(studentID) != null) { // check if the given student ID already exists in the file
                System.out.println("Student with ID " + studentID + " already exists. Registration failed.");
                return;
            }
            students[size++] = new Student(studentID); // Registers the new student by creating a new student object. And increments the size
            System.out.println("Student registered successfully.");
        }

        private boolean isValidStudentID(String studentID) { // This method will validate the input using regex
            return studentID.matches("w\\d{7}");// checks if the first character is lowercase 'w'  and rest 7 occurrences should be digit
        }

        public void deleteStudent(String studentID) { // this method will delete the given studentID and corresponding details from the system
            Student student = findStudent(studentID); // check if the given studentID exists using findStudent method and retrieve it
            if (student == null) { // if student object is to failed retrieve it will output that the given ID doesn't exists
                System.out.println("Student with ID " + studentID + " not found. Deletion failed.");
                return;
            }
            // Find index of student
            int index = -1; // Uses -1 to refer that the value has not been found. This will keep track of finding the studentID.
            for (int i = 0; i < size; i++) { // looping through the array
                if (students[i].getStudentID().equals(studentID)) { // checking i
                    index = i;
                    break;
                }
            }
            // Shift array elements
            if (index != -1) { // checking if index is on the right index
                for (int i = index; i < size - 1; i++) { // this loop starts from the index where student was found
                    students[i] = students[i + 1]; // shifting each element one position left
                    //Making sure that no null cells between cells which have data. removing null spaces
                }
                students[size - 1] = null; // setting the last element as null
                size--; // decrementing the size in order to keep track of the registered student count
                System.out.println("Student deleted successfully.");
            }
        }

        public void addStudentName(String studentID, String name) {
            Student student = findStudent(studentID); // locate the student object with specified studentID
            if (student == null) { // student with the given ID doesn't exist
                System.out.println("Student with ID " + studentID + " not found. Update failed.");
                return;
            }
            student.setStudentName(name); // using setter method to set the name
            System.out.println("Student name updated successfully.");
        }

        public Student findStudent(String studentID) {
            for (int i = 0; i < size; i++) { // Looping through the array
                if (students[i].getStudentID().equals(studentID)) { // using getter method to retrieve name and using equals to compare them
                    return students[i];
                }
            }
            return null; //returns null if there's no student with the given ID
        }

        public void addModuleMark(String studentID, int moduleNumber, double mark) { // setting the parameter for the method
            Student student = findStudent(studentID); // locate student object with specified studentID
            if (student == null) { // student with the given ID doesn't exist
                System.out.println("Student with ID " + studentID + " not found. Update failed.");
                return;
            }
            student.setModuleMark(moduleNumber, mark); //using setter method to pass the values
            System.out.println("Module mark updated successfully.");
        }

        public void generateSummary() {
            int totalRegistrations = size; // shows the number of students that are registered to the system
            // Had to use long data type since .count() uses long data type
            long module1Count = Arrays.stream(students, 0, size) // creates a stream from a portion of an array. starting from the index 0 to size
                    .filter(student -> student.getModuleMarks()[0] > 40) // Using lambda expression. filter out students who have marks greater than 40 and counts them
                    .count(); //
            long module2Count = Arrays.stream(students, 0, size)// same goes for this one. only keeps the student object which contain marks are greater than 40
                    .filter(student -> student.getModuleMarks()[1] > 40)
                    .count();
            long module3Count = Arrays.stream(students, 0, size)
                    .filter(student -> student.getModuleMarks()[2] > 40)
                    .count();

            System.out.println("Total student registrations: " + totalRegistrations); // print the total registrations
            System.out.println("Students with more than 40 marks in Module 1: " + module1Count);
            System.out.println("Students with more than 40 marks in Module 2: " + module2Count);
            System.out.println("Students with more than 40 marks in Module 3: " + module3Count);
        }

        //Use to create the complete summary of the system
        public void generateCompleteReport() {
            // Sort students array based on average marks (bubble sort)
            for (int i = 0; i < size - 1; i++) { //Outer loop
                for (int j = 0; j < size - i - 1; j++) { // inner loop
                    if (students[j].getAverageMarks() < students[j + 1].getAverageMarks()) { //comparing average marks of each student
                        // Swap students[j] and students[j+1]
                        Student temp = students[j];
                        students[j] = students[j + 1];
                        students[j + 1] = temp;
                    }
                }
            }

            System.out.println("Complete Report:");
            for (int i = 0; i < size; i++) {
                System.out.println("Student ID: " + students[i].getStudentID());
                System.out.println("Student Name: " + students[i].getStudentName());
                System.out.println("Module 1 Marks: " + students[i].getModuleMarks()[0]);
                System.out.println("Module 2 Marks: " + students[i].getModuleMarks()[1]);
                System.out.println("Module 3 Marks: " + students[i].getModuleMarks()[2]);
                System.out.println("Total Marks: " + students[i].getTotalMarks());
                System.out.println("Average Marks: " + students[i].getAverageMarks()); // calls getAverageMarks from the Student class
                System.out.println("Grade: " + students[i].getGrade()); // calls getGrade method from the Student class
                System.out.println();
            }
        }
        // In order to store data to a text file we use java object serialization
        public void storeStudentDetails(String filename) {
            try (ObjectOutputStream objectSerialization = new ObjectOutputStream(new FileOutputStream(filename))) { // using ObjectOutputStream to
                objectSerialization.writeObject(Arrays.copyOf(students, size));
                System.out.println("Student details stored successfully.");
            } catch (IOException e) {
                System.out.println("Error storing student details: " + e.getMessage());
            }
        }

        public void loadStudentDetails(String filename) {
            try (ObjectInputStream objectDeserialization = new ObjectInputStream(new FileInputStream(filename))) {
                students = (Student[]) objectDeserialization.readObject();
                size = students.length; // Update size to reflect loaded students
                System.out.println("Student details loaded successfully.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading student details: " + e.getMessage());
            }
        }

        public void sortByName() {
            if (size == 0) { // checking if the array is empty or not
                System.out.println("No entries to sort.");
                return;
            }
            //Using bubble sort
            for (int i = 0; i < size - 1; i++) { // Outer loop iterating through objects
                for (int j = 0; j < size - i - 1; j++) {
                    if (students[j].getStudentName().compareTo(students[j + 1].getStudentName()) > 0) { // Swaps students[j] and students[j+1] if there are not in the right order
                        Student temp = students[j]; // temp stores value of students[j]
                        students[j] = students[j + 1];// assign the value of student[j+1] to students[j]
                        students[j + 1] = temp;// assign tem to student[j+1]
                    }
                }
            }
            System.out.println("Students have been sorted successfully based on student names");
            for (int i = 0; i < size; i++) { // iterating through the array and printing data
                System.out.println("Name: " + students[i].getStudentName() + ", ID: " + students[i].getStudentID());// Used getters and setters inorder to prevent unique id's getting printed
            }
        }
    }



================================================
FILE: Student Activity Management System/src/Student.java
================================================
import java.io.Serial; //Imports serial annotation
import java.io.Serializable; // imports this interface for serialization
import java.util.Arrays; // Imports array class for array manipulation

public class Student implements Serializable {
    @Serial //UID works as an ID to identify the serialized object
    private static final long serialVersionUID = 1L; // when serializing the object it will receive a unique ID which helps to serialize and deserialize the object.
    private final String studentID;
    private String studentName;
    private final double [] moduleMarks;

    public Student(String studentID) { // Creating the student constructor
        this.studentID = studentID;
        this.studentName = "";
        this.moduleMarks = new double[3]; // Initializing the array length
    }

    public String getStudentID() { //getter method for studentID
        return studentID;
    }

    public String getStudentName() { //getter method for studentName
        return studentName;
    }

    public void setStudentName(String studentName) { // setter method for studentName

        this.studentName = studentName;
    }

    public double[] getModuleMarks() { //getter method for module marks

        return moduleMarks;
    }

    public void setModuleMark(int moduleNumber, double mark) { //setter method for module marks
        if (mark >= 0 && mark <= 100) { // marks within this range are considered valid marks
            this.moduleMarks[moduleNumber - 1] = mark; // converts module number to corresponding index of it
        } else {
            System.out.println("Invalid module mark. Marks should be between 0 and 100.");
        }
    }

    public double getTotalMarks() { // getter method for total marks

        return Arrays.stream(moduleMarks).sum(); // converts module marks into a stream and will return the sum of the stream
    }

    public double getAverageMarks() { // getter method for average marks
        // using Arrays will help to manipulate the array
        return Arrays.stream(moduleMarks).average().orElse(0);// returns the average of the data stream. if the array is empty using '.orElse(0)' returns 0
    }

    public String getGrade() { // getter method for grading
        double average = getAverageMarks();
        if (average >= 80) { // conditional statement for grading
            return "Distinction";
        } else if (average >= 70) {
            return "Merit";
        } else if (average >= 40) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}

