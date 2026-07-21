import java.util.*;
public class CodeAlpha_StudentGradeTracker {

    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        ArrayList<String> studentNames= new ArrayList<>();
        ArrayList<Integer> studentMarks= new ArrayList<>();
        
        System.out.println("Enter number of students:");
        int n= sc.nextInt();
        sc.nextLine();

        for(int i= 0; i<n; i++){
            System.out.println("Enter student name:");
            String name= sc.nextLine();
            studentNames.add(name);

            System.out.println("Enter marks of" + name + ": ");
            int marks= sc.nextInt();
            sc.nextLine();
            studentMarks.add(marks);
        }

        int total= 0;
        int highest= studentMarks.get(0);
        int lowest= studentMarks.get(0);

        for(int marks: studentMarks){
            total += marks;

            if(marks> highest){
                highest= marks;
            }
            if(marks< lowest){
                lowest= marks;
            }
        }
        double average= (double) total/ n;

        System.out.println("\n ----- Student Grade Summary -----");
        for(int i= 0; i<n; i++){
            System.out.println(studentNames.get(i) + " : " + studentMarks.get(i));
        }
        System.out.println("\nAverage Marks:" + average);
        System.out.println("Highest Marks:" +highest);
        System.out.println("Lowest Marks:" + lowest);

        sc.close();
    }
}