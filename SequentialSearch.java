import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.*;

public class SequentialSearch {

    public static void main(String[] args) {
        String fileName = "C:/words.txt"; // Use forward slashes for portability (/ instead of \)
        List<String> wordArrayList = readFileToArrayList(fileName);
        List<String> wordLinkedList = readFileToLinkedList(fileName);

        if (wordArrayList == null || wordLinkedList == null) {
            System.err.println("Error reading file.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of words to search (max: " + wordArrayList.size() + "):");
        int numWordsToSearch;
        try {
            numWordsToSearch = Integer.parseInt(scanner.nextLine());
            if (numWordsToSearch > wordArrayList.size()) {
                throw new IllegalArgumentException("Number of random words cannot exceed list size.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please enter a number.");
            return;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return;
        }

        String[] wordsToSearch = generateRandomWords(wordArrayList, numWordsToSearch).toArray(new String[0]); // Convert list to array

        System.out.println("Searching for these words:");
        for (String word : wordsToSearch) {
            System.out.println(word);
        }

        System.out.println("\nArrayList search results:");
        long atotal = 0;
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            int index = linearSearchArray(wordArrayList, wordsToSearch[i].toLowerCase()); // Make search case-insensitive
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            if (index != -1) {
                //System.out.println("Word '" + wordsToSearch[i] + "' found at index: " + index + " (Time taken: " + nanos + " nanoseconds)");
            } else {
                //System.out.println("Word '" + wordsToSearch[i] + "' not found");
            }
            atotal+=nanos;
        }
        System.out.println("Total time used to search all words: " + atotal);
        System.out.println("Average time used to search all words: " + atotal/numWordsToSearch);
        
        
        System.out.println("\nLinkedList search results:");
        long btotal = 0;
        int lastFoundIndex = -1;
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            int index = linearSearch(wordLinkedList, wordsToSearch[i].toLowerCase(), lastFoundIndex);
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            if (index != -1) {
                //System.out.println("Word '" + wordsToSearch[i] + "' found at index: " + index + " (Time taken: " + nanos + " nanoseconds)");
            } else {
                //System.out.println("Word '" + wordsToSearch[i] + "' not found");
            }
            btotal+=nanos;
        }
        System.out.println("Total time used to search all words: " + btotal);
        System.out.println("Average time used to search all words: " + btotal/numWordsToSearch);
        
        System.out.println("Enter word to search: ");
        String search = scanner.nextLine();
        
        long startTime = System.nanoTime();
        int index = linearSearchArray(wordArrayList, search.toLowerCase()); // Make search case-insensitive
        long endTime = System.nanoTime();
        long nanos = endTime - startTime;
        System.out.println("Time used in ArrayList : " + nanos);
        
        lastFoundIndex = -1;
        long startTime2 = System.nanoTime();
        int index2 = linearSearch(wordLinkedList, search.toLowerCase(), lastFoundIndex); // Make search case-insensitive
        long endTime2 = System.nanoTime();
        long nanos2 = endTime2 - startTime2;
        System.out.println("Time used in LinkedList: " + nanos2);
        
        
    }

    public static ArrayList<String> readFileToArrayList(String fileName) {
        ArrayList<String> wordArrayList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\\s+"); // Escape the backslash for regex
                for (String word : words) {
                    wordArrayList.add(word); // Add word to the ArrayList
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null; // Return null on error
        }

        return wordArrayList;
    }
    
    public static LinkedList<String> readFileToLinkedList(String fileName) {
        LinkedList<String> wordLinkedList = new LinkedList<>();

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
            return null; // Return null on error
        }

        return wordLinkedList;
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
    
    public static int linearSearchArray(List<String> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toLowerCase().equals(target)) { // Consistent case-insensitive search
                return i;
            }
        }
        return -1;
    }
    
    public static int linearSearch(List<String> wordlist, String target, int lastFoundIndex) {
        if (lastFoundIndex >= 0 && lastFoundIndex < wordlist.size()) {
            // Start search from the index after the last found word (potentially faster for clustered data)
            ListIterator<String> iterator = wordlist.listIterator(lastFoundIndex + 1);
            while (iterator.hasNext()) {
                String word = iterator.next();
                if (word.equals(target)) {
                    return iterator.previousIndex(); // Return the actual index (previous from iterator)
                }
            }
        }

        // If not found after last found index, perform regular search from beginning
        ListIterator<String> iterator = wordlist.listIterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (word.equals(target)) {
                return iterator.previousIndex();
            }
        }

        return -1; // Not found
    }
    
}
