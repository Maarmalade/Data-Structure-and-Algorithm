import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HashingSearchCompareRandom {

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

        // Input the number of random words to read
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of random words to read:");
        int numRandomWords = scanner.nextInt();

        // Randomly select words
        Random random = new Random();
        List<String> randomWords = new ArrayList<>();
        for (int i = 0; i < numRandomWords; i++) {
            int randomIndex = random.nextInt(wordMap1.size());
            randomWords.add((String) wordMap1.keySet().toArray()[randomIndex]);
        }

        // Shuffle the list of random words
        Collections.shuffle(randomWords);

        // Display shuffled words and their indices
        System.out.println("Shuffled words:");
        for (int i = 0; i < randomWords.size(); i++) {
            System.out.println("Word: " + randomWords.get(i) + ", Index: " + i);
        }

        // Perform searches and measure time
        long totalNanoTime1 = 0;
        long totalNanoTime2 = 0;
        int iterations = 20;
        boolean found1 = false;
        boolean found2 = false;

        for (int i = 0; i < iterations; i++) {
            // Search in HashMap with initial capacity 50000
            long start1 = System.nanoTime();
            for (String searchWord : randomWords) {
                Integer foundIndex1 = wordMap1.get(searchWord);
                if (foundIndex1 != null && !found1) {
                    found1 = true;
                }
            }
            long end1 = System.nanoTime();
            long nanos1 = end1 - start1;
            totalNanoTime1 += nanos1;

            // Search in HashMap with initial capacity 500000
            long start2 = System.nanoTime();
            for (String searchWord : randomWords) {
                Integer foundIndex2 = wordMap2.get(searchWord);
                if (foundIndex2 != null && !found2) {
                    found2 = true;
                }
            }
            long end2 = System.nanoTime();
            long nanos2 = end2 - start2;
            totalNanoTime2 += nanos2;
        }

        // Print search results and comparison
        if (!found1) {
            System.out.println("Words not found in HashMap with initial capacity 50000");
        }
        if (!found2) {
            System.out.println("Words not found in HashMap with initial capacity 500000");
        }

        double averageNanoTime1 = (double) totalNanoTime1 / iterations;
        double averageNanoTime2 = (double) totalNanoTime2 / iterations;
        System.out.println("Average time taken over " + iterations + " iterations for HashMap with initial capacity 50000: " + averageNanoTime1 + " nanoseconds");
        System.out.println("Average time taken over " + iterations + " iterations for HashMap with initial capacity 500000: " + averageNanoTime2 + " nanoseconds");

        // Compare average times
        if (averageNanoTime1 < averageNanoTime2) {
            System.out.println("HashMap with initial capacity 50000 is faster.");
        } else if (averageNanoTime1 > averageNanoTime2) {
            System.out.println("HashMap with initial capacity 500000 is faster.");
        } else {
            System.out.println("Both HashMaps have similar search times.");
        }
    }
}
