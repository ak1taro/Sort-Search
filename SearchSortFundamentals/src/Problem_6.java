import java.util.*;

public class RiskThresholdBinaryLookup {

    // Linear Search: find exact threshold in unsorted array
    static int linearSearch(int[] arr, int target) {
        int comparisons = 0;
        for (int i = 0; i < arr.length; i++) {
            comparisons++;
            if (arr[i] == target) {
                System.out.println("Linear: threshold=" + target + " → found at index " + i +
                        " (" + comparisons + " comps)");
                return i;
            }
        }
        System.out.println("Linear: threshold=" + target + " → not found (" + comparisons + " comps)");
        return -1;
    }

    // Binary Search: exact match in sorted array
    static int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1, comparisons = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] == target) {
                System.out.println("Binary exact " + target + ": index " + mid +
                        " (" + comparisons + " comps)");
                return mid;
            } else if (arr[mid] < target) low = mid + 1;
            else high = mid - 1;
        }
        System.out.println("Binary exact " + target + ": not found (" + comparisons + " comps)");
        return -1;
    }

    // Floor: largest value <= target  (lower_bound variant)
    static int floor(int[] arr, int target) {
        int low = 0, high = arr.length - 1, result = Integer.MIN_VALUE, comparisons = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] <= target) {
                result = arr[mid];
                low = mid + 1;  // try to go higher (still <= target)
            } else {
                high = mid - 1;
            }
        }
        System.out.println("Binary floor(" + target + "): " +
                (result == Integer.MIN_VALUE ? "none" : result) +
                " (" + comparisons + " comps)");
        return result;
    }

    // Ceiling: smallest value >= target  (upper_bound variant)
    static int ceiling(int[] arr, int target) {
        int low = 0, high = arr.length - 1, result = Integer.MAX_VALUE, comparisons = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            comparisons++;
            if (arr[mid] >= target) {
                result = arr[mid];
                high = mid - 1;  // try to go lower (still >= target)
            } else {
                low = mid + 1;
            }
        }
        System.out.println("Binary ceiling(" + target + "): " +
                (result == Integer.MAX_VALUE ? "none" : result) +
                " (" + comparisons + " comps)");
        return result;
    }

    // Find insertion point for new client in sorted risk bands
    static int insertionPoint(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = (low + high) / 2;
            if (arr[mid] < target) low = mid + 1;
            else high = mid;
        }
        System.out.println("Insertion point for " + target + ": index " + low);
        return low;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 6: Risk Threshold Binary Lookup ===");

        int[] sortedRisks = { 10, 25, 50, 100 };
        System.out.println("Sorted risks: " + Arrays.toString(sortedRisks));

        System.out.println("\n-- Linear Search (unsorted use-case) --");
        int[] unsorted = { 50, 10, 100, 25 };
        System.out.println("Unsorted bands: " + Arrays.toString(unsorted));
        linearSearch(unsorted, 30);  // not found
        linearSearch(unsorted, 50);  // found

        System.out.println("\n-- Binary Search (sorted) --");
        binarySearch(sortedRisks, 25);   // found
        binarySearch(sortedRisks, 30);   // not found

        System.out.println("\n-- Floor & Ceiling --");
        floor(sortedRisks, 30);
        ceiling(sortedRisks, 30);
        floor(sortedRisks, 50);       // exact match => floor = 50
        ceiling(sortedRisks, 50);     // exact match => ceiling = 50
        floor(sortedRisks, 5);        // below minimum => none
        ceiling(sortedRisks, 150);    // above maximum => none

        System.out.println("\n-- Insertion Point --");
        insertionPoint(sortedRisks, 30);   // between 25 and 50
        insertionPoint(sortedRisks, 5);    // before 10
        insertionPoint(sortedRisks, 120);  // after 100

        System.out.println("\n-- Complexity Report --");
        System.out.println("Linear Search: O(n) — no prerequisite");
        System.out.println("Binary Search (exact/floor/ceiling/insertion): O(log n) — sorted array required");
    }
}