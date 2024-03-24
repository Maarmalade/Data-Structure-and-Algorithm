import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.*;

public class WordSearch {

    public static void main(String[] args) {
        String fileName = "C:/words.txt"; // Use forward slashes for portability

        List<String> wordList = readFileToList(fileName);
        if (wordList == null) {
            System.err.println("Error reading file.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of words to search (max: " + wordList.size() + "):");
        int numWordsToSearch;
        try {
            numWordsToSearch = Integer.parseInt(scanner.nextLine());
            if (numWordsToSearch > wordList.size()) {
                throw new IllegalArgumentException("Number of random words cannot exceed list size.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a number.");
            return;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        // Generate random words only once
        List<String> wordsToSearch = generateRandomWords(wordList, numWordsToSearch);

        System.out.println("Searching for these words:");
        for (String word : wordsToSearch) {
            System.out.println(word);
        }

        // Perform binary search
        System.out.println("\nBinary Search results:");
        long binarySearchTime = performSearch(wordList, wordsToSearch, true);

        // Perform sequential search
        System.out.println("\nSequential Search results:");
        long sequentialSearchTime = performSearch(wordList, wordsToSearch, false);

        System.out.println("");
        System.out.println("Total Time used for Binary Search: " + binarySearchTime + " nanoseconds");
        System.out.println("Total Time used for Sequential Search: " + sequentialSearchTime + " nanoseconds");
    }

    private static List<String> readFileToList(String fileName) {
        try {
            return Files.readAllLines(Paths.get(fileName));
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }

    private static List<String> generateRandomWords(List<String> wordList, int numWords) {
        List<String> randomWords = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numWords; i++) {
            int randomIndex = random.nextInt(wordList.size());
            randomWords.add(wordList.get(randomIndex));
        }

        return randomWords;
    }

    private static long performSearch(List<String> wordList, List<String> wordsToSearch, boolean isBinarySearch) {
        long totalTime = 0;
        String[] wordArray = null; // Optimize by sorting only once if binary search is used

        for (String word : wordsToSearch) {
            long startTime = System.nanoTime();
            int index;

            if (isBinarySearch && wordArray == null) {
                // Sort only once before binary search
                wordArray = wordList.toArray(new String[0]);
                Arrays.sort(wordArray);
            }

            if (isBinarySearch) {
                index = binarySearch(wordArray, word.toLowerCase());
            } else {
                // Use lowercase for case-insensitive linear search
                index = linearSearch(wordList, word.toLowerCase());
            }

            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            if (index != -1) {
                System.out.println("Word '" + word + "' found at index: " + index + " (Time taken: " + nanos + " nanoseconds)");
            } else {
                System.out.println("Word '" + word + "' not found");
            }
            totalTime += nanos;
        }

        return totalTime;
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

    public static int linearSearch(List<String> list, String target) {
    	for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(target)) { // Consistent case-insensitive search
                return i;
            }
        }
        return -1;
    }
}
