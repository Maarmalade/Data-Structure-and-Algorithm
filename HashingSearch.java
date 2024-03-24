import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HashingSearch {

    public static void main(String[] args) {
        String filename = "C://words.txt"; // Replace with your actual file path
        String targetWord;

        List<String> words = new ArrayList<>();
        HashMap<String, Integer> wordMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] wordList = line.split("\\s+"); // Split based on whitespace
                for (String word : wordList) {
                    words.add(word.toLowerCase()); // Convert to lowercase for case-insensitive search
                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1); // Add word and count occurrences
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        System.out.println("Loaded " + words.size() + " words from the file.");

        System.out.print("Enter the word to search: ");
        targetWord = new java.util.Scanner(System.in).nextLine().toLowerCase(); // Read and convert to lowercase

        long startTime = System.nanoTime();
        int foundIndex = wordMap.containsKey(targetWord) ? wordMap.get(targetWord) - 1 : -1; // Get word index (occurrence-1) if found
        long searchTime = System.nanoTime() - startTime;

        if (foundIndex != -1) {
            System.out.printf("Word '%s' found at line %d (appeared %d times).\n", targetWord, foundIndex + 1, wordMap.get(targetWord));
        } else {
            System.out.println("Word '" + targetWord + "' not found.");
        }

        System.out.printf("Search time: %.6f nanoseconds\n", (double) searchTime);
    }
}
