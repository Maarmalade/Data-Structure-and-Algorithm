import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HashingSearchCompare {

    public static void main(String[] args) {
        String fileName = "C:/words.txt";

        // Initialize HashMaps with different initial capacities
        HashMap<String, Integer> wordMap1 = new HashMap<>(50000);
        HashMap<String, Integer> wordMap2 = new HashMap<>(500000);

        // Populate both HashMaps with words and indices
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            int index = 0;
            for (String line : lines) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    wordMap1.put(word.toLowerCase(), index);
                    wordMap2.put(word.toLowerCase(), index);
                    index++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        // Input word to search
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word to search:");
        String searchWord = scanner.nextLine().toLowerCase();

        // Perform searches and measure time
        long totalNanoTime1 = 0;
        long totalNanoTime2 = 0;
        int iterations = 20;
        boolean found1 = false;
        boolean found2 = false;

        for (int i = 0; i < iterations; i++) {
            // Search in HashMap with initial capacity 50000
            long start1 = System.nanoTime();
            Integer foundIndex1 = wordMap1.get(searchWord);
            long end1 = System.nanoTime();
            long nanos1 = end1 - start1;
            totalNanoTime1 += nanos1;

            // Search in HashMap with initial capacity 500000
            long start2 = System.nanoTime();
            Integer foundIndex2 = wordMap2.get(searchWord);
            long end2 = System.nanoTime();
            long nanos2 = end2 - start2;
            totalNanoTime2 += nanos2;

            // Print result if word is found
            if (foundIndex1 != null && !found1) {
                System.out.println("Word found at index in HashMap with initial capacity 50000: " + foundIndex1);
                found1 = true;
            }
            if (foundIndex2 != null && !found2) {
                System.out.println("Word found at index in HashMap with initial capacity 500000: " + foundIndex2);
                found2 = true;
            }
        }

        // Print search results and comparison
        if (!found1) {
            System.out.println("Word not found in HashMap with initial capacity 50000");
        }
        if (!found2) {
            System.out.println("Word not found in HashMap with initial capacity 500000");
        }

        double averageNanoTime1 = (double) totalNanoTime1 / iterations;
        double averageNanoTime2 = (double) totalNanoTime2 / iterations;
        System.out.println("Average time taken for 50000 hash table : " + averageNanoTime1 + " nanoseconds");
        System.out.println("Average time taken for 500000 hash table : " + averageNanoTime2 + " nanoseconds");

        }
    }

