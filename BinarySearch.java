import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.time.Duration;
import java.time.LocalTime;

public class BinarySearch {

    public static void main(String[] args) {
        String fileName = "C://words.txt";
        List<String> wordList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\\s+"); // Escape the backslash for regex
                for (String word : words) {
                    wordList.add(word);
                }
            }

            // Convert ArrayList to array
            String[] wordsArray = wordList.toArray(new String[0]);

            // Sort the array
            Arrays.sort(wordsArray);

            // Print array elements (optional)
            for (String word : wordsArray) {
                System.out.print(word + " ");
            }
            System.out.println(); // Add a new line after printing all words

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the word to search:");
            String searchWord = sc.nextLine();

            long start = System.nanoTime();
            int index = binarySearch(wordsArray, searchWord);
            long end = System.nanoTime();
            long nanos = end - start;
            System.out.println("Time taken: " + nanos + " nanoseconds");

            if (index != -1) {
                System.out.println("Word found at index: " + index);
            } else {
                System.out.println("Word not found");
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static int binarySearch(String[] array, String target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparisonResult = target.compareTo(array[mid]);

            if (comparisonResult == 0) {
                return mid;
            } else if (comparisonResult < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1; // Not found
    }
}
