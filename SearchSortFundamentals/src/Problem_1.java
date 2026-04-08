import java.util.*;

public class TransactionFeeSorting {

    static class Transaction {
        String id;
        double fee;
        String timestamp;

        Transaction(String id, double fee, String timestamp) {
            this.id = id;
            this.fee = fee;
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return id + ":" + fee + "@" + timestamp;
        }
    }

    // Bubble Sort by fee ascending (stable, for small batches <= 100)
    static int bubbleSort(List<Transaction> list) {
        int n = list.size();
        int swaps = 0, passes = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Transaction temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swaps++;
                    swapped = true;
                }
            }
            passes++;
            if (!swapped) break; // early termination
        }
        System.out.println("BubbleSort passes: " + passes + ", swaps: " + swaps);
        return swaps;
    }

    // Insertion Sort by fee + timestamp (stable, for medium batches 100-1000)
    static void insertionSort(List<Transaction> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Transaction key = list.get(i);
            int j = i - 1;
            // Sort by fee DESC; break ties by timestamp ASC
            while (j >= 0 && (list.get(j).fee < key.fee ||
                    (list.get(j).fee == key.fee && list.get(j).timestamp.compareTo(key.timestamp) > 0))) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    // Flag high-fee outliers > $50
    static void flagOutliers(List<Transaction> list) {
        System.out.print("High-fee outliers (> $50): ");
        boolean found = false;
        for (Transaction t : list) {
            if (t.fee > 50) {
                System.out.print(t + " ");
                found = true;
            }
        }
        if (!found) System.out.print("none");
        System.out.println();
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>(Arrays.asList(
                new Transaction("id1", 10.5, "10:00"),
                new Transaction("id2", 25.0, "09:30"),
                new Transaction("id3", 5.0,  "10:15")
        ));

        System.out.println("=== Problem 1: Transaction Fee Sorting ===");
        System.out.println("Input: " + transactions);

        // Bubble Sort (fee ASC)
        List<Transaction> bubbleList = new ArrayList<>(transactions);
        bubbleSort(bubbleList);
        System.out.println("BubbleSort (fees ASC): " + bubbleList);

        // Insertion Sort (fee DESC + timestamp)
        List<Transaction> insertList = new ArrayList<>(transactions);
        insertionSort(insertList);
        System.out.println("InsertionSort (fee DESC + ts): " + insertList);

        // Flag outliers
        flagOutliers(bubbleList);

        // Test with a high-fee transaction
        System.out.println("\n--- With outlier ---");
        List<Transaction> withOutlier = new ArrayList<>(Arrays.asList(
                new Transaction("id4", 75.0, "11:00"),
                new Transaction("id5", 10.0, "11:05"),
                new Transaction("id6", 55.0, "11:10")
        ));
        bubbleSort(withOutlier);
        System.out.println("Sorted: " + withOutlier);
        flagOutliers(withOutlier);
    }
}