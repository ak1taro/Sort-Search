import java.util.*;

public class HistoricalTradeVolumeAnalysis {

    static class Trade {
        String id;
        int volume;

        Trade(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return id + ":" + volume;
        }
    }

    // Merge Sort ASC (stable, O(n log n))
    static void mergeSort(Trade[] arr, int left, int right) {
        if (left >= right) return;
        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    static void merge(Trade[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1, n2 = right - mid;
        Trade[] L = new Trade[n1], R = new Trade[n2];
        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            // <= ensures stability (equal elements maintain original order)
            if (L[i].volume <= R[j].volume) arr[k++] = L[i++];
            else arr[k++] = R[j++];
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // Quick Sort DESC using Lomuto partition
    static void quickSort(Trade[] arr, int low, int high) {
        if (low < high) {
            int pi = lomutoPartition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    // Lomuto partition (DESC order) - pivot = last element
    static int lomutoPartition(Trade[] arr, int low, int high) {
        // Median-of-3 pivot selection
        int mid = (low + high) / 2;
        if (arr[low].volume < arr[mid].volume) swap(arr, low, mid);
        if (arr[low].volume < arr[high].volume) swap(arr, low, high);
        if (arr[mid].volume < arr[high].volume) swap(arr, mid, high);
        swap(arr, mid, high); // put median at end as pivot

        Trade pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].volume >= pivot.volume) { // >= for DESC
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    static void swap(Trade[] arr, int i, int j) {
        Trade temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }

    // Merge two already-sorted (ASC) trade lists
    static Trade[] mergeSortedLists(Trade[] a, Trade[] b) {
        Trade[] result = new Trade[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i].volume <= b[j].volume) result[k++] = a[i++];
            else result[k++] = b[j++];
        }
        while (i < a.length) result[k++] = a[i++];
        while (j < b.length) result[k++] = b[j++];
        return result;
    }

    static int totalVolume(Trade[] arr) {
        int sum = 0;
        for (Trade t : arr) sum += t.volume;
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 3: Historical Trade Volume Analysis ===");

        Trade[] trades = {
                new Trade("trade3", 500),
                new Trade("trade1", 100),
                new Trade("trade2", 300)
        };

        System.out.println("Input: " + Arrays.toString(trades));

        // Merge Sort ASC
        Trade[] mergeArr = Arrays.copyOf(trades, trades.length);
        mergeSort(mergeArr, 0, mergeArr.length - 1);
        System.out.println("MergeSort (asc): " + Arrays.toString(mergeArr) + " // Stable");

        // Quick Sort DESC
        Trade[] quickArr = Arrays.copyOf(trades, trades.length);
        quickSort(quickArr, 0, quickArr.length - 1);
        System.out.println("QuickSort (desc): " + Arrays.toString(quickArr) + " // Pivot: median-of-3");

        // Merge two sorted lists (morning + afternoon sessions)
        Trade[] morning = { new Trade("m1", 100), new Trade("m2", 300), new Trade("m3", 500) };
        Trade[] afternoon = { new Trade("a1", 200), new Trade("a2", 400) };
        Trade[] merged = mergeSortedLists(morning, afternoon);
        System.out.println("\nMorning: " + Arrays.toString(morning));
        System.out.println("Afternoon: " + Arrays.toString(afternoon));
        System.out.println("Merged: " + Arrays.toString(merged));
        System.out.println("Total volume: " + totalVolume(merged));
    }
}