package hermosura;

import javax.swing.*;
import java.io.*;

public class JOptionPane_Based_Student_Feedback_System {

	public static void main(String[] args) {
		
		int response = 0;
		String data = "";
		
		
		do {
		String nameStudent = JOptionPane.showInputDialog("Enter your name");
		String nameCourse = JOptionPane.showInputDialog("Enter your Course/Subject");
		String messageFeedback = JOptionPane.showInputDialog("Enter your message");

		String [] ratings = {"1 (Very Poor)","2 (Poor)","3 (Average)","4 (Good)","5 (Excellent)"};
		
		String rate = (String) JOptionPane.showInputDialog(
                null, "Rate:",
                "Course Rating",
                JOptionPane.QUESTION_MESSAGE,
                null, ratings, ratings[0]);
		
		
		data += String.format("Student Name: %s\nCourse: %s\nFeedback: %s\nRating: %s\n\n", nameStudent, nameCourse, messageFeedback, rate);
		
		response = JOptionPane.showConfirmDialog(null, "Do you want to continue?", null, JOptionPane.YES_NO_OPTION);
		} while (response != JOptionPane.NO_OPTION);

		
		try (FileWriter fw = new FileWriter("studentfeedbackdata.txt", true)){
			fw.write("--- Student Feedback Records ---\n\n");
			fw.write(data);
			
		} catch (Exception e) {
			System.out.println("An unexpected error occured: " + e.getMessage());
		}
		
		
		
		try (BufferedReader br = new BufferedReader(new FileReader("studentfeedbackdata.txt"))) {
			
			String line;
            int totalRating = 0;
            int count = 0;
            
            int excellent = 0;
            int good = 0;
            int average = 0;
            int poor = 0;
            int veryPoor = 0;
            StringBuilder allRecords = new StringBuilder();

            while ((line = br.readLine()) != null) {
                allRecords.append(line).append("\n");

                if (line.startsWith("Rating:")) {
                    String ratingPart  = line.replace("Rating:", "").trim();
                    int numericRating  = Integer.parseInt(ratingPart.substring(0, 1));
                    totalRating += numericRating;
                    count++;

                    switch (numericRating) {
                        case 5: excellent++; break;
                        case 4: good++;      break;
                        case 3: average++;   break;
                        case 2: poor++;      break;
                        case 1: veryPoor++;  break;
                    }
                }
            }
            
            
            
            String summary = allRecords.toString();
            String evaluate = "";
            
            if (count > 0) {
                double computedAverage = (double) totalRating / count; 
                
                if (computedAverage <= 2.5) {
                	evaluate += "Needs Improvement";
                }else if (computedAverage <= 3.4) {
                	evaluate += "Average Feedback";
                } else if (computedAverage <= 4.4) {
                	evaluate += "Good Feedback";
                } else if (computedAverage <= 5) {
                	evaluate += "Outstanding Feedback!";
                }

                summary += String.format(
                    "\n=== Summary ===\n" +
                    "Total Feedbacks : %d\n" +
                    "Average Rating  : %.2f\n" +
                    "Excellent: %d\n" +
                    "Good: %d\n" +
                    "Average: %d\n" +
                    "Poor: %d\n" +
                    "Very Poor: %d\n\n"
                    + "Overall Assessment: %s\n",
                    count, computedAverage, excellent, good, average, poor, veryPoor, evaluate);
            }
            JOptionPane.showMessageDialog(null, summary, "Feedback Records", JOptionPane.INFORMATION_MESSAGE);
            
            
            
		} catch (Exception e) {
			System.out.println("An nexpectederror occured: " + e.getMessage());
		}
	}

}
