import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SearchAlgorithmComparison {

    public static void main(String[] args) {
        String fileName = "C:/words.txt"; // Use forward slashes for portability (/ instead of \)
        List<String> wordArrayList = readFileToArrayList(fileName);
        List<String> wordLinkedList = readFileToLinkedList(fileName);

        if (wordArrayList == null || wordLinkedList == null) {
            System.err.println("Error reading file.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word to search:");
        String searchWord = scanner.nextLine().toLowerCase();

        // Binary Search
        long startBinary = System.nanoTime();
        int indexBinary = binarySearch(wordArrayList.toArray(new String[0]), searchWord);
        long endBinary = System.nanoTime();
        long nanosBinary = endBinary - startBinary;

        // Binary Search with LinkedList
        long startBinaryLinkedList = System.nanoTime();
        int indexBinaryLinkedList = binarySearchLinkedList(wordLinkedList, searchWord);
        long endBinaryLinkedList = System.nanoTime();
        long nanosBinaryLinkedList = endBinaryLinkedList - startBinaryLinkedList;
        
        // Modified Binary Search
        long startModifiedBinary = System.nanoTime();
        int indexModifiedBinary = modifiedBinarySearch(wordArrayList.toArray(new String[0]), searchWord);
        long endModifiedBinary = System.nanoTime();
        long nanosModifiedBinary = endModifiedBinary - startModifiedBinary;
        
        // Sequential ArrayList Search
        long startSequentialArrayList = System.nanoTime();
        int indexSequentialArrayList = linearSearch(wordArrayList, searchWord);
        long endSequentialArrayList = System.nanoTime();
        long nanosSequentialArrayList = endSequentialArrayList - startSequentialArrayList;

        // Modified Sequential LinkedList Search
        long startModifiedSequentialLinkedList = System.nanoTime();
        int indexModifiedSequentialLinkedList = ModifiedLinearSearch(wordLinkedList, searchWord, -1);
        long endModifiedSequentialLinkedList = System.nanoTime();
        long nanosModifiedSequentialLinkedList = endModifiedSequentialLinkedList - startModifiedSequentialLinkedList;

        // Hashing Search with 50,000 elements
        HashMap<String, Integer> wordMap50000 = createWordHashMap(wordArrayList, 50000);
        long startHashing50000 = System.nanoTime();
        Integer indexHashing50000 = wordMap50000.get(searchWord);
        long endHashing50000 = System.nanoTime();
        long nanosHashing50000 = endHashing50000 - startHashing50000;

        // Hashing Search with 500,000 elements
        HashMap<String, Integer> wordMap500000 = createWordHashMap(wordArrayList, 500000);
        long startHashing500000 = System.nanoTime();
        Integer indexHashing500000 = wordMap500000.get(searchWord);
        long endHashing500000 = System.nanoTime();
        long nanosHashing500000 = endHashing500000 - startHashing500000;

        // Print results
        System.out.println("Sequential ArrayList Search - Time taken: " + nanosSequentialArrayList + " nanoseconds");
        System.out.println("Modified Sequential LinkedList Search - Time taken: " + nanosModifiedSequentialLinkedList + " nanoseconds");
        System.out.println("Binary Array Search - Time taken: " + nanosBinary + " nanoseconds");
        System.out.println("Modified Binary Search - Time taken: " + nanosModifiedBinary + " nanoseconds");
        System.out.println("Binary Search with LinkedList - Time taken: " + nanosBinaryLinkedList + " nanoseconds");
        System.out.println("Hashing Search with 50,000 elements - Time taken: " + nanosHashing50000 + " nanoseconds");
        System.out.println("Hashing Search with 500,000 elements - Time taken: " + nanosHashing500000 + " nanoseconds");

        if (indexSequentialArrayList != -1) {
            System.out.println("Word found at index using Sequential ArrayList Search: " + indexSequentialArrayList);
        } else {
            System.out.println("Word not found using Sequential ArrayList Search");
        }

        if (indexModifiedSequentialLinkedList != -1) {
            System.out.println("Word found at index using Modified Sequential LinkedList Search: " + indexModifiedSequentialLinkedList);
        } else {
            System.out.println("Word not found using Modified Sequential LinkedList Search");
        }

        if (indexBinary != -1) {
            System.out.println("Word found at index using Binary Search: " + indexBinary);
        } else {
            System.out.println("Word not found using Binary Search");
        }

        if (indexModifiedBinary != -1) {
            System.out.println("Word found at index using Modified Binary Search: " + indexModifiedBinary);
        } else {
            System.out.println("Word not found using Modified Binary Search");
        }

        if (indexBinaryLinkedList != -1) {
            System.out.println("Word found at index using Binary Search with LinkedList: " + indexBinaryLinkedList);
        } else {
            System.out.println("Word not found using Binary Search with LinkedList");
        }

        if (indexHashing50000 != null) {
            System.out.println("Word found at index using Hashing Search with 50,000 hash table capacity: " + indexHashing50000);
        } else {
            System.out.println("Word not found using Hashing Search with 50,000 hash table capacity");
        }

        if (indexHashing500000 != null) {
            System.out.println("Word found at index using Hashing Search with 500,000 hash table capacity: " + indexHashing500000);
        } else {
            System.out.println("Word not found using Hashing Search with 500,000 hash table capacity");
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n\nEnter the number of words to search (max: " + wordArrayList.size() + "):");
        int numWordsToSearch;
        try {
            numWordsToSearch = Integer.parseInt(sc.nextLine());
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

        System.out.println("\nArrayList sequential search results:");
        long total = 0;
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            int index = linearSearch(wordArrayList, wordsToSearch[i].toLowerCase()); // Make search case-insensitive
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            total+=nanos;
        }
        System.out.println("Total time used to search all words: " + total);
        System.out.println("Average time used to search all words: " + total/numWordsToSearch);
        
        
        System.out.println("\nLinkedList sequential search results:");
        total = 0;
        int lastFoundIndex = -1;
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            int index = ModifiedLinearSearch(wordLinkedList, wordsToSearch[i].toLowerCase(), lastFoundIndex);
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            total+=nanos;
        }
        System.out.println("Total time used to search all words: " + total);
        System.out.println("Average time used to search all words: " + total/numWordsToSearch);
        
        
        System.out.println("\n\nArrayList binary search results:");
        total = 0;
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            indexBinary = binarySearch(wordArrayList.toArray(new String[0]), wordsToSearch[i].toLowerCase());
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            total+=nanos;
        }
        System.out.println("Total time used to search all words: " + total);
        System.out.println("Average time used to search all words: " + total/numWordsToSearch);
        
        
        System.out.println("\n\nArrayList modified binary search results:");
        total = 0;
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            indexBinary = modifiedBinarySearch(wordArrayList.toArray(new String[0]), wordsToSearch[i].toLowerCase());
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            total+=nanos;
        }
        System.out.println("Total time used to search all words: " + total);
        System.out.println("Average time used to search all words: " + total/numWordsToSearch);
        
        
        System.out.println("\n\nLinkedList binary search results:");
        total = 0;
        for (int i = 0; i < wordsToSearch.length; i++) {
            long startTime = System.nanoTime();
            indexBinary = binarySearchLinkedList(wordLinkedList, wordsToSearch[i].toLowerCase());
            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            total+=nanos;
        }
        System.out.println("Total time used to search all words: " + total);
        System.out.println("Average time used to search all words: " + total/numWordsToSearch);
        
        // Random hash search
        System.out.println("\nHash search result:");

        long totalNanoTime1 = 0;
        long totalNanoTime2 = 0;

        Set<String> selectedWords = new HashSet<>(); // Track selected words

        Random random = new Random();

        for (int i = 0; i < numWordsToSearch; i++) {

            do {
                searchWord = wordArrayList.get(random.nextInt(wordArrayList.size())); // Randomly select from wordArrayList
            } while (selectedWords.contains(searchWord)); // Check if already selected
            selectedWords.add(searchWord); // Add to selected words

            long start1 = System.nanoTime();
            Integer foundIndex1 = wordMap50000.get(searchWord);
            long end1 = System.nanoTime();
            totalNanoTime1 += (end1 - start1);

            long start2 = System.nanoTime();
            Integer foundIndex2 = wordMap500000.get(searchWord);
            long end2 = System.nanoTime();
            totalNanoTime2 += (end2 - start2);
        }

        double averageNanoTime1 = (double) totalNanoTime1 / numWordsToSearch;
        double averageNanoTime2 = (double) totalNanoTime2 / numWordsToSearch;

        System.out.println("Total time used to search all words for 50000 hash table: " + totalNanoTime1 + " nanoseconds");
        System.out.println("Average time used to search all words for 50000 hash table: " + averageNanoTime1 + " nanoseconds");

        System.out.println("\nTotal time used to search all words for 500000 hash table: " + totalNanoTime2 + " nanoseconds");
        System.out.println("Average time used to search all words for 500000 hash table: " + averageNanoTime2 + " nanoseconds");
    }

    public static List<String> readFileToList(String fileName) {
        List<String> wordList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\\s+"); // Escape the backslash for regex
                Collections.addAll(wordList, words); // Add words to the list
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null; // Return null on error
        }

        return wordList;
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

    public static int modifiedBinarySearch(String[] array, String target) {
        int left = 0;
        int right = array.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            int comparisonResult = target.compareTo(array[mid]);

            if (comparisonResult == 0) {
                return mid; // Found at mid
            } else if (comparisonResult < 0) {
                if (target.compareTo(array[left]) == 0) {
                    return left; // Found at left
                } else {
                    right = mid - 1;
                }
            } else {
                if (target.compareTo(array[right]) == 0) {
                    return right; // Found at right
                } else {
                    left = mid + 1;
                }
            }
        }

        return -1; // Not found
    }

    public static int linearSearch(List<String> list, String target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).toLowerCase().equals(target)) { // Consistent case-insensitive search
                return i;
            }
        }
        return -1; // Not found
    }

    public static int ModifiedLinearSearch(List<String> wordlist, String target, int lastFoundIndex) {
        if (lastFoundIndex >= 0 && lastFoundIndex < wordlist.size()) {
            // Start search from the index after the last found word (potentially faster for clustered data)
            ListIterator<String> iterator = wordlist.listIterator(lastFoundIndex + 1);
            while (iterator.hasNext()) {
                String word = iterator.next();
                if (word.toLowerCase().equals(target)) {
                    return iterator.previousIndex(); // Return the actual index (previous from iterator)
                }
            }
        }

        // If not found after last found index, perform regular search from beginning
        ListIterator<String> iterator = wordlist.listIterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            if (word.toLowerCase().equals(target)) {
                return iterator.previousIndex();
            }
        }

        return -1; // Not found
    }

    public static int binarySearchLinkedList(List<String> list, String target) {
        int left = 0;
        int right = list.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            String midElement = list.get(mid);

            int comparisonResult = target.compareTo(midElement);

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

    public static HashMap<String, Integer> createWordHashMap(List<String> wordList, int capacity) {
        HashMap<String, Integer> wordMap = new HashMap<>(capacity);
        int index = 0;

        for (String word : wordList) {
            wordMap.put(word.toLowerCase(), index++);
        }

        return wordMap;
    }
}
