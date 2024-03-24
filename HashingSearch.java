import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.*;

public class HashingSearch {

    public static void main(String[] args) {
        String fileName = "C:/words.txt";
        HashMap<String, Integer> wordMap = new HashMap<>(); // Use HashMap for potential performance benefits

        int index = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordMap.put(word.toLowerCase(), index++); // Store word and assign index
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word to search:");
        String searchWord = scanner.nextLine().toLowerCase();

        long start = System.nanoTime();
        Integer foundIndex = wordMap.get(searchWord);
        long end = System.nanoTime();
        long nanos = end - start;
        System.out.println("Time taken: " + nanos + " nanoseconds");

        if (foundIndex != null) {
            System.out.println("The target found at index: " + foundIndex);
        } else {
            System.out.println("Word not found");
        }
    }
}
