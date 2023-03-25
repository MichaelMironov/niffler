package niffler.tests.ui;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestTrain {
    @Test
    void factorialTest() {
        System.out.println(recursion(14));
    }

    int recursion(int n) {
        if (n == 0)
            return 1;
        return n * recursion(n - 1);
    }

    @Test
    void bubbleSortTest() {
        System.out.println(Arrays.toString(bubbleSort(new int[]{4, 6, 2, 8, 3, 6, 4, 2, 1})));
    }

    private int[] bubbleSort(int[] arr) {

        int length = arr.length - 1;
        for (int i = 0; i < length; i++) {
            boolean sorted = true;
            for (int j = 0; j < length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    sorted = false;
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
            if (sorted)
                return arr;

        }
        return arr;
    }
}
