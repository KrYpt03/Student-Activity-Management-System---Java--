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
