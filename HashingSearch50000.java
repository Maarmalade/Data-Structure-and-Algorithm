import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.*;

public class HashingSearch50000 {

    public static void main(String[] args) {
        String fileName = "C:/words.txt";
        HashMap<String, Integer> wordMap = new HashMap<>(50000); 

        int index = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordMap.put(word.toLowerCase(), index++); 
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word to search:");
        String searchWord = scanner.nextLine().toLowerCase();

        long totalNanoTime = 0;
        int iterations = 20;
        boolean found = false;

        for (int i = 0; i < iterations; i++) {
            long start = System.nanoTime();
            Integer foundIndex = wordMap.get(searchWord);
            long end = System.nanoTime();
            long nanos = end - start;
            totalNanoTime += nanos;

            if (foundIndex != null && !found) {
                System.out.println("The target found at index: " + foundIndex);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Word not found");
        }

        double averageNanoTime = (double) totalNanoTime / iterations;
        System.out.println("Average time taken over: " + averageNanoTime + " nanoseconds");
    }
}
