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
