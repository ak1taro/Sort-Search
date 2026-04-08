import java.util.*;

public class ClientRiskScoreRanking {

    static class Client {
        String name;
        int riskScore;
        double accountBalance;

        Client(String name, int riskScore, double accountBalance) {
            this.name = name;
            this.riskScore = riskScore;
            this.accountBalance = accountBalance;
        }

        @Override
        public String toString() {
            return name + ":" + riskScore;
        }
    }

    // Bubble Sort by riskScore ASC (in-place, O(1) space)
    static int bubbleSort(Client[] arr) {
        int n = arr.length;
        int swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                    swapped = true;
                    System.out.println("  Swap: " + arr[j + 1].name + " <-> " + arr[j].name);
                }
            }
            if (!swapped) break;
        }
        return swaps;
    }

    // Insertion Sort by riskScore DESC + accountBalance (tie-break)
    static void insertionSort(Client[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;
            while (j >= 0) {
                // Primary: riskScore DESC
                // Tie-break: accountBalance DESC
                boolean shouldShift = arr[j].riskScore < key.riskScore ||
                        (arr[j].riskScore == key.riskScore && arr[j].accountBalance < key.accountBalance);
                if (shouldShift) {
                    arr[j + 1] = arr[j];
                    j--;
                } else break;
            }
            arr[j + 1] = key;
        }
    }

    // Top-k highest risk after sorting DESC
    static void printTopK(Client[] arr, int k) {
        System.out.print("Top " + k + " risks: ");
        for (int i = 0; i < Math.min(k, arr.length); i++) {
            System.out.print(arr[i].name + "(" + arr[i].riskScore + ")");
            if (i < Math.min(k, arr.length) - 1) System.out.print(", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 2: Client Risk Score Ranking ===");

        Client[] clients = {
                new Client("clientC", 80, 50000),
                new Client("clientA", 20, 30000),
                new Client("clientB", 50, 45000)
        };

        System.out.println("Input: " + Arrays.toString(clients));

        // Bubble Sort ASC (visualize swaps)
        Client[] bubbleArr = Arrays.copyOf(clients, clients.length);
        System.out.println("Bubble Sort swaps:");
        int swaps = bubbleSort(bubbleArr);
        System.out.println("Bubble (asc): " + Arrays.toString(bubbleArr) + " // Swaps: " + swaps);

        // Insertion Sort DESC
        Client[] insertArr = Arrays.copyOf(clients, clients.length);
        insertionSort(insertArr);
        System.out.println("Insertion (desc): " + Arrays.toString(insertArr));

        // Top 3 risks
        printTopK(insertArr, 3);

        // Adaptive behavior demo: nearly-sorted input
        System.out.println("\n--- Adaptive behavior on nearly-sorted input ---");
        Client[] nearlySorted = {
                new Client("X", 10, 1000),
                new Client("Y", 20, 2000),
                new Client("Z", 35, 3000),
                new Client("W", 30, 2500)  // slightly out of order
        };
        System.out.println("Input: " + Arrays.toString(nearlySorted));
        int s2 = bubbleSort(nearlySorted);
        System.out.println("Sorted: " + Arrays.toString(nearlySorted) + " // Swaps: " + s2);
    }
}