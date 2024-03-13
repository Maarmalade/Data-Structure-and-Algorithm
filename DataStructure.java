import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DataStructure {
    public static void main(String[] args) {
        String fileName = "C://words.txt";
        List<String> wordList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\s+");
                for (String word : words) {
                    wordList.add(word);
                }
            }

            // Convert ArrayList to array
            String[] wordsArray = wordList.toArray(new String[0]);

            // Print array elements (optional)
            for (String word : wordsArray) {
                System.out.print(word+" ");
                
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
