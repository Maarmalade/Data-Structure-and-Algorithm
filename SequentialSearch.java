import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class SequentialSearch {
	
	
	
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

            // Print array elements (optional)
            for (String word : wordsArray) {
                System.out.print(word + " ");
            }
            System.out.println(); // Add a new line after printing all words

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the word to search:");
            String searchWord = sc.nextLine();
            int index = linearSearch(wordsArray, searchWord);
            if (index != -1) {
                System.out.println("Word found at index: " + index);
            } else {
                System.out.println("Word not found");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
	
	public static int linearSearch(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) { // Use equals() to compare strings
                return i; // Return the index of the target element if found
            }
        }
        return -1;
	}
}
