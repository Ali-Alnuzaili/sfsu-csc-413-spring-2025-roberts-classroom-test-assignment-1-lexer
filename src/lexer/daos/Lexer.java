import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Lexer {
    public static void main(String[] args) {
        // Step 1: Check if a file path is provided
        if (args.length == 0) {
            System.out.println("usage: java lexer.Lexer filename.x");
            return;
        }

        String filePath = args[0];

        // Step 2: Open and read the file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                System.out.printf("%3d: %s%n", lineNumber, line); // Print line number and content
                lineNumber++;
                // (Later we will modify this to process tokens)
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }
}
