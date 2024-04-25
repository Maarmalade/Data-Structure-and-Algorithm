import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BinarySearchRandom {

    public static void main(String[] args) {
        String fileName = "C:/words.txt";

        List<String> wordList = readFileToList(fileName);
        if (wordList == null) {
            System.err.println("Error reading file.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of random words to perform binary search (max: " + wordList.size() + "):");
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

        List<String> wordsToSearch = generateRandomWords(wordList, numWordsToSearch);

        long binaryArrayTime = performBinaryArraySearch(wordList, wordsToSearch);
        long modifiedBinaryArrayTime = performModifiedBinaryArraySearch(wordList, wordsToSearch);
        long linkedListTime = performLinkedListSearch(wordList, wordsToSearch);

        System.out.println("\nTime taken for Binary ArrayList Search: " + binaryArrayTime + " nanoseconds");
        System.out.println("Time taken for Modified Binary ArrayList Search: " + modifiedBinaryArrayTime + " nanoseconds");
        System.out.println("Time taken for Linked List Search: " + linkedListTime + " nanoseconds");
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

    private static long performBinaryArraySearch(List<String> wordList, List<String> wordsToSearch) {
        long totalTime = 0;
        String[] wordArray = null;

        for (String word : wordsToSearch) {
            long startTime = System.nanoTime();

            if (wordArray == null) {
                wordArray = wordList.toArray(new String[0]);
                Arrays.sort(wordArray);
            }

            int index = binarySearch(wordArray, word.toLowerCase());

            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            if (index != -1) {
             //   System.out.println("Word '" + word + "' found in array at index: " + index + " (Time taken: " + nanos + " nanoseconds)");
            } else {
            //    System.out.println("Word '" + word + "' not found in array (binary)");
            }
            totalTime += nanos;
        }

        return totalTime;
    }

    private static long performModifiedBinaryArraySearch(List<String> wordList, List<String> wordsToSearch) {
        long totalTime = 0;
        String[] wordArray = null;

        for (String word : wordsToSearch) {
            long startTime = System.nanoTime();

            if (wordArray == null) {
                wordArray = wordList.toArray(new String[0]);
                Arrays.sort(wordArray);
            }

            int index = modifiedBinarySearch(wordArray, word.toLowerCase());

            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            if (index != -1) {
             //   System.out.println("Word '" + word + "' found in modified array at index: " + index + " (Time taken: " + nanos + " nanoseconds)");
            } else {
             //   System.out.println("Word '" + word + "' not found in modified array (modified binary)");
            }
            totalTime += nanos;
        }

        return totalTime;
    }

    private static long performLinkedListSearch(List<String> wordList, List<String> wordsToSearch) {
        long totalTime = 0;
        LinkedList<String> linkedList = new LinkedList<>(wordList);

        for (String word : wordsToSearch) {
            long startTime = System.nanoTime();

            int index = binarySearchLinkedList(linkedList, word.toLowerCase());

            long endTime = System.nanoTime();
            long nanos = endTime - startTime;

            if (index != -1) {
             //   System.out.println("Word '" + word + "' found in linked list at index: " + index + " (Time taken: " + nanos + " nanoseconds)");
            } else {
             //   System.out.println("Word '" + word + "' not found in linked list");
            }
            totalTime += nanos;
        }

        return totalTime;
    }

    private static int binarySearch(String[] array, String target) {
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

    private static int binarySearchLinkedList(LinkedList<String> list, String target) {
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
