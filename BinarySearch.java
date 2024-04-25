import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BinarySearch {

    public static void main(String[] args) {
        String fileName = "C://words.txt";
        List<String> wordList = new ArrayList<>();
        LinkedList<String> wordLinkedList = new LinkedList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            for (String line : lines) {
                // Assuming words are separated by spaces
                String[] words = line.split("\\s+"); // Escape the backslash for regex
                for (String word : words) {
                    wordList.add(word);
                    wordLinkedList.add(word);
                }
            }

            // Convert ArrayList to array
            String[] wordsArray = wordList.toArray(new String[0]);

            // Sort the array
            Arrays.sort(wordsArray);

            // Sort the linked list
            wordLinkedList.sort(String::compareTo);

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the word to search (Binary Search): ");
            String searchWord = sc.nextLine().toLowerCase();

            long startArray = System.nanoTime();
            int indexArray = binarySearch(wordsArray, searchWord);
            long endArray = System.nanoTime();
            long nanosArray = endArray - startArray;

            long startModifiedArray = System.nanoTime();
            int indexModifiedArray = modifiedBinarySearch(wordsArray, searchWord);
            long endModifiedArray = System.nanoTime();
            long nanosModifiedArray = endModifiedArray - startModifiedArray;

            long startLinkedList = System.nanoTime();
            int indexLinkedList = binarySearchLinkedList(wordLinkedList, searchWord);
            long endLinkedList = System.nanoTime();
            long nanosLinkedList = endLinkedList - startLinkedList;

            System.out.println("Array - Time taken: " + nanosArray + " nanoseconds");
            System.out.println("Modified Array - Time taken: " + nanosModifiedArray + " nanoseconds");
            System.out.println("Linked List - Time taken: " + nanosLinkedList + " nanoseconds");

            if (indexArray != -1) {
                System.out.println("Word found at index in Array: " + indexArray);
            } else {
                System.out.println("Word not found in Array");
            }

            if (indexModifiedArray != -1) {
                System.out.println("Word found at index in Modified Array: " + indexModifiedArray);
            } else {
                System.out.println("Word not found in Modified Array");
            }

            if (indexLinkedList != -1) {
                System.out.println("Word found at index in Linked List: " + indexLinkedList);
            } else {
                System.out.println("Word not found in Linked List");
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
}
