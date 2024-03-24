import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.*;

public class SequentialSearch {

    public static void main(String[] args) {
        String fileName = "C:/words.txt"; // Use forward slashes for portability (/ instead of \)
        List<String> wordList = new ArrayList<>();
        LinkedList<String> wordLinkedList = new LinkedList<>();
        
        
//        //Array
//        try {
//            wordList = Files.readAllLines(Paths.get(fileName)); // Read lines directly into the list
//        } catch (IOException e) {
//            System.err.println("Error reading file: " + e.getMessage());
//            return; // Exit program on error
//        }
        
        //LinkedList
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\\s+"); // Escape the backslash for regex
                for (String word : words) {
                    wordLinkedList.add(word); // Add word to the linked list
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return; // Exit program on error
        }
        
        Scanner scanner = new Scanner(System.in); // Use a descriptive variable name
        System.out.println("Enter the word to search:");
        String searchWord = scanner.nextLine().toLowerCase(); // Make search case-insensitive

        long start = System.nanoTime();
        int index = linearSearch(wordLinkedList, searchWord);
        long end = System.nanoTime();
        long nanos = end - start;
        System.out.println("Time taken: " + nanos + " nanoseconds");

        if (index != -1) {
            System.out.println("Word found at index: " + index);
        } else {
            System.out.println("Word not found");
        }

//        System.out.println("Search time: " + timeTaken); // Print formatted time
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
