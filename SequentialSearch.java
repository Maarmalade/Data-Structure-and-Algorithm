import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.*;

public class SequentialSearch {

    public static void main(String[] args) {
        String fileName = "C:/words.txt"; // Use forward slashes for portability (/ instead of \)
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

        String[] wordsToSearch = generateRandomWords(wordList, numWordsToSearch).toArray(new String[0]); // Convert list to array

        System.out.println("Searching for these words:");
        for (String word : wordsToSearch) {
            System.out.println(word);
        }

        System.out.println("\nSearch results:");
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            int index = linearSearch(wordList, wordsToSearch[i].toLowerCase()); // Make search case-insensitive
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            if (index != -1) {
                System.out.println("Word '" + wordsToSearch[i] + "' found at index: " + index + " (Time taken: " + nanos + " nanoseconds)");
            } else {
                System.out.println("Word '" + wordsToSearch[i] + "' not found");
            }
        }
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
            int randomIndex = random.nextInt(wordList.size()); // Generate random index within list bounds
            randomWords.add(wordList.get(randomIndex));
        }

        return randomWords;
    }

    public static int linearSearch(List<String> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toLowerCase().equals(target)) { // Consistent case-insensitive search
                return i;
            }
        }
        return -1;
    }
}
