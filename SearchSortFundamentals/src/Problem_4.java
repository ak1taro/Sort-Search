import java.util.*;

public class PortfolioReturnSorting {

    static class Asset {
        String name;
        double returnRate;
        double volatility;

        Asset(String name, double returnRate, double volatility) {
            this.name = name;
            this.returnRate = returnRate;
            this.volatility = volatility;
        }

        @Override
        public String toString() {
            return name + ":" + returnRate + "%";
        }
    }

    // Merge Sort by returnRate ASC (stable - preserves original order for ties)
    static void mergeSort(Asset[] arr, int left, int right) {
        if (left >= right) return;
        // Hybrid: use Insertion Sort for small partitions (size <= 10)
        if (right - left + 1 <= 10) {
            insertionSort(arr, left, right);
            return;
        }
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(Asset[] arr, int left, int mid, int right) {
        Asset[] temp = new Asset[right - left + 1];
        int i = left, j = mid + 1, k = 0;
        while (i <= mid && j <= right) {
            // <= for stability (equal returnRate: preserve original order)
            if (arr[i].returnRate <= arr[j].returnRate) temp[k++] = arr[i++];
            else temp[k++] = arr[j++];
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];
        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // Insertion Sort helper for small partitions
    static void insertionSort(Asset[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Asset key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j].returnRate > key.returnRate) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Quick Sort by returnRate DESC + volatility ASC (tie-break)
    static void quickSort(Asset[] arr, int low, int high) {
        if (low < high) {
            // Hybrid: use insertion for small partitions
            if (high - low + 1 <= 10) {
                insertionSortDesc(arr, low, high);
                return;
            }
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Lomuto partition DESC (primary: returnRate DESC, secondary: volatility ASC)
    static int partition(Asset[] arr, int low, int high) {
        // Median-of-3 pivot selection
        int mid = (low + high) / 2;
        medianOfThree(arr, low, mid, high);
        swap(arr, mid, high); // put median at end as pivot

        Asset pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            // DESC returnRate; if equal, ASC volatility
            if (arr[j].returnRate > pivot.returnRate ||
                    (arr[j].returnRate == pivot.returnRate && arr[j].volatility < pivot.volatility)) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    static void medianOfThree(Asset[] arr, int a, int b, int c) {
        if (arr[a].returnRate < arr[b].returnRate) swap(arr, a, b);
        if (arr[a].returnRate < arr[c].returnRate) swap(arr, a, c);
        if (arr[b].returnRate < arr[c].returnRate) swap(arr, b, c);
    }

    static void swap(Asset[] arr, int i, int j) {
        Asset temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }

    // Insertion Sort DESC for small partitions
    static void insertionSortDesc(Asset[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Asset key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j].returnRate < key.returnRate) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 4: Portfolio Return Sorting ===");

        Asset[] assets = {
                new Asset("AAPL", 12.0, 0.25),
                new Asset("TSLA", 8.0,  0.60),
                new Asset("GOOG", 15.0, 0.30)
        };

        System.out.println("Input: " + Arrays.toString(assets));

        // Merge Sort ASC (stable)
        Asset[] mergeArr = Arrays.copyOf(assets, assets.length);
        mergeSort(mergeArr, 0, mergeArr.length - 1);
        System.out.println("MergeSort (returnRate ASC): " + Arrays.toString(mergeArr));

        // Quick Sort DESC + volatility ASC tie-break
        Asset[] quickArr = Arrays.copyOf(assets, assets.length);
        quickSort(quickArr, 0, quickArr.length - 1);
        System.out.println("QuickSort (returnRate DESC + volatility ASC): " + Arrays.toString(quickArr));

        // Stability demo: equal returnRates
        System.out.println("\n--- Stability demo (tie returns) ---");
        Asset[] ties = {
                new Asset("FUND_A", 10.0, 0.3),
                new Asset("FUND_B", 10.0, 0.5),
                new Asset("FUND_C", 10.0, 0.2)
        };
        System.out.println("Input (same returns): " + Arrays.toString(ties));
        Asset[] tiesMerge = Arrays.copyOf(ties, ties.length);
        mergeSort(tiesMerge, 0, tiesMerge.length - 1);
        System.out.println("MergeSort (stable, original order preserved): " + Arrays.toString(tiesMerge));
    }
}