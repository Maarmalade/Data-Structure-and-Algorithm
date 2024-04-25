import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SearchAlgorithmComparison {

    public static void main(String[] args) {
        String fileName = "C:/words.txt";
        List<String> wordList = readFileToList(fileName);

        if (wordList == null) {
            System.err.println("Error reading file.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word to search:");
        String searchWord = scanner.nextLine().toLowerCase();

        // Binary Search
        long startBinary = System.nanoTime();
        int indexBinary = binarySearch(wordList.toArray(new String[0]), searchWord);
        long endBinary = System.nanoTime();
        long nanosBinary = endBinary - startBinary;

        // Modified Binary Search
        long startModifiedBinary = System.nanoTime();
        int indexModifiedBinary = modifiedBinarySearch(wordList.toArray(new String[0]), searchWord);
        long endModifiedBinary = System.nanoTime();
        long nanosModifiedBinary = endModifiedBinary - startModifiedBinary;

        // Sequential ArrayList Search
        long startSequentialArrayList = System.nanoTime();
        int indexSequentialArrayList = linearSearch(wordList, searchWord);
        long endSequentialArrayList = System.nanoTime();
        long nanosSequentialArrayList = endSequentialArrayList - startSequentialArrayList;

        // Modified Sequential LinkedList Search
        LinkedList<String> wordLinkedList = new LinkedList<>(wordList);
        long startModifiedSequentialLinkedList = System.nanoTime();
        int indexModifiedSequentialLinkedList = ModifiedLinearSearch(wordLinkedList, searchWord, -1);
        long endModifiedSequentialLinkedList = System.nanoTime();
        long nanosModifiedSequentialLinkedList = endModifiedSequentialLinkedList - startModifiedSequentialLinkedList;

        // Binary Search with LinkedList
        long startBinaryLinkedList = System.nanoTime();
        int indexBinaryLinkedList = binarySearchLinkedList(wordLinkedList, searchWord);
        long endBinaryLinkedList = System.nanoTime();
        long nanosBinaryLinkedList = endBinaryLinkedList - startBinaryLinkedList;

        // Hashing Search with 50,000 elements
        HashMap<String, Integer> wordMap50000 = createWordHashMap(wordList, 50000);
        long startHashing50000 = System.nanoTime();
        Integer indexHashing50000 = wordMap50000.get(searchWord);
        long endHashing50000 = System.nanoTime();
        long nanosHashing50000 = endHashing50000 - startHashing50000;

        // Hashing Search with 500,000 elements
        HashMap<String, Integer> wordMap500000 = createWordHashMap(wordList, 500000);
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

    public static int binarySearchLinkedList(LinkedList<String> list, String target) {
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
