import java.util.*;

public class AccountIDLookup {

    // Linear Search: first occurrence, returns index (-1 if not found)
    static int linearSearchFirst(String[] arr, String target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) {
                System.out.println("Linear first '" + target + "': index " + i +
                        " (" + comparisons + " comparisons)");
                return i;
            }
        }
        System.out.println("Linear first '" + target + "': not found (" + comparisons + " comparisons)");
        return -1;
    }

    // Linear Search: last occurrence
    static int linearSearchLast(String[] arr, String target) {
        int comparisons = 0;
        int lastIdx = -1;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i].equals(target)) lastIdx = i;
        }
        System.out.println("Linear last '" + target + "': index " + lastIdx +
                " (" + comparisons + " comparisons)");
        return lastIdx;
    }

    // Binary Search: exact match (returns any matching index)
    static int binarySearch(String[] arr, String target) {
        int low = 0, high = arr.length - 1, comparisons = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            int cmp = arr[mid].compareTo(target);
            if (cmp == 0) {
                System.out.println("Binary '" + target + "': index " + mid +
                        " (" + comparisons + " comparisons)");
                return mid;
            } else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        System.out.println("Binary '" + target + "': not found (" + comparisons + " comparisons)");
        return -1;
    }

    // Count occurrences using binary search (find leftmost and rightmost)
    static int countOccurrences(String[] arr, String target) {
        int left = lowerBound(arr, target);
        if (left == -1) return 0;
        int right = upperBound(arr, target);
        int count = right - left + 1;
        System.out.println("Count of '" + target + "': " + count +
                " (indices " + left + " to " + right + ")");
        return count;
    }

    // Lower bound: first index where arr[i] == target
    static int lowerBound(String[] arr, String target) {
        int low = 0, high = arr.length - 1, result = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = arr[mid].compareTo(target);
            if (cmp == 0) { result = mid; high = mid - 1; }
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return result;
    }

    // Upper bound: last index where arr[i] == target
    static int upperBound(String[] arr, String target) {
        int low = 0, high = arr.length - 1, result = -1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = arr[mid].compareTo(target);
            if (cmp == 0) { result = mid; low = mid + 1; }
            else if (cmp < 0) low = mid + 1;
            else high = mid - 1;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 5: Account ID Lookup in Transaction Logs ===");

        // Sorted array (binary search requires sorted input)
        String[] sortedLogs = { "accA", "accB", "accB", "accC" };
        System.out.println("Sorted logs: " + Arrays.toString(sortedLogs));

        System.out.println("\n-- Linear Search --");
        linearSearchFirst(sortedLogs, "accB");
        linearSearchLast(sortedLogs, "accB");
        linearSearchFirst(sortedLogs, "accZ"); // not found

        System.out.println("\n-- Binary Search --");
        binarySearch(sortedLogs, "accB");
        binarySearch(sortedLogs, "accZ"); // not found

        System.out.println("\n-- Count Occurrences (binary) --");
        countOccurrences(sortedLogs, "accB");
        countOccurrences(sortedLogs, "accA");
        countOccurrences(sortedLogs, "accZ");

        // Complexity report
        System.out.println("\n-- Time Complexity --");
        System.out.println("Linear Search: O(n) — worst case scans all n elements");
        System.out.println("Binary Search: O(log n) — requires sorted input");
        System.out.println("Count (binary lower+upper): O(log n)");
        int n = 1_000_000;
        System.out.println("\nFor n = 1,000,000:");
        System.out.println("  Linear worst case: " + n + " comparisons");
        System.out.println("  Binary worst case: ~" + (int)(Math.log(n) / Math.log(2)) + " comparisons");
    }
}