import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.*;

public class HashingSearch {

    public static void main(String[] args) {
        String fileName = "C:/words.txt";
        HashMap<String, Integer> wordMap = new HashMap<>(); // Use HashMap for potential performance benefits

        // 1. User enters the number of words to generate randomly
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of words to generate randomly:");
        int numWordsToGenerate = scanner.nextInt();

        // Read words from the text file
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordMap.put(word.toLowerCase(), wordMap.size()); // Store word with unique index based on insertion order
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Generate random words
        List<String> generatedWords = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < numWordsToGenerate; i++) {
            int randomIndex = random.nextInt(wordMap.size());
            String randomWord = (String) wordMap.keySet().toArray()[randomIndex];
            generatedWords.add(randomWord);
        }

        // Loop for each generated word and search
        for (String generatedWord : generatedWords) {
            System.out.println("Searching for word: " + generatedWord);

            long start = System.nanoTime();
            Integer foundIndex = wordMap.get(generatedWord);
            long end = System.nanoTime();
            long nanos = end - start;

            System.out.println("Time taken: " + nanos + " nanoseconds");

            if (foundIndex != null) {
                System.out.println("The target found at index: " + foundIndex);
            } else {
                System.out.println("Word not found");
            }

            System.out.println();
        }
    }
}
