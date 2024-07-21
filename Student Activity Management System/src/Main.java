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
