import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.CREATE;

/**
 * @author Peyton Terry terrypn@mail.uc.edu
 */

public class FileScan {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        File selectedFile;
        String rec = "";
        int lineCount = 0;
        int wordCount = 0;
        int charCount = 0;

        try {
            File workingDirectory = new File(System.getProperty("user.dir") + File.separator + "src"); //Should check the src folder now instead of overall

            if (!workingDirectory.exists() || !workingDirectory.isDirectory()) {
                System.out.println("src directory doesn't exist.");
            }

            if (args.length > 0) {
                selectedFile = new File(workingDirectory, args[0]);
                if (!selectedFile.exists() || !selectedFile.isFile()) {
                    System.out.println("Error, the file does not exist in the 'src' directory.");
                }
            } else {
                System.out.println("No file named. Opening File Chooser..");

                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    selectedFile = chooser.getSelectedFile();
                } else {
                    System.out.println("No file selected. Exiting.");
                    return;
                }
            }

            Path file = selectedFile.toPath();

            try (InputStream in =
                         new BufferedInputStream(Files.newInputStream(file, CREATE));
                 BufferedReader reader =
                         new BufferedReader(new InputStreamReader(in))) {

                while (reader.ready()) {
                    rec = reader.readLine();
                    lineCount++;

                    String[] words = rec.split(" "); // Split by whitespace to count words
                    wordCount += words.length;

                    charCount += rec.length();
                }
            }

            System.out.println("File Name: " + selectedFile.getName());
            System.out.println("-------------------------------------");
            System.out.printf("The total number of lines are %4d\n", lineCount);
            System.out.printf("The total number of words are %4d\n", wordCount);
            System.out.printf("The total number of characters are %4d\n", charCount);
            System.out.println("\nData file read successfully.");
        }catch( FileNotFoundException e){
            System.out.println("File not found.");
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("An error occurred while processing the file.");
            e.printStackTrace();
        }
    }
}
