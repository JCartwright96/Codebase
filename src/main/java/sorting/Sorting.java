/*
 * Name     :   Sorting.java
 * Author   :   joeca
 * Date     :   20 May 2021
 */
package sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Small sorting class implementing simple version of common sorting algorithms
 * Some utilise code found elsewhere not written by myself.
 */
public class Sorting {
    /**
     * Implementation of a bubble sort using ArrayLists
     * @param array the unordered array list
     * @return the sorted array list
     */
    public static ArrayList<Integer> bubbleSort(ArrayList<Integer> array) {
        int length = array.size();

        for (int j = length; j > 0; j--) {
            for (int i = 0; i < j - 1; i++) {
                if (array.get(i) > array.get(i + 1)) {
                    int temp = array.get(i);
                    array.set(i, array.get(i + 1));
                    array.set(i + 1, temp);
                }
            }
        }
        return array;
    }

    /**
     * Implementation of a bubble sort using standard arrays
     * @param array the unordered array
     * @return the sorted array
     */
    public static int[] bubbleSort(int[] array) {
        int length = array.length;

        for (int j = length; j > 0; j--) {
            for (int i = 0; i < j - 1; i++) {
                if (array[i] > array[i+1]) {
                    int temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;
                }
            }
        }
        return array;
    }

    /**
     * Example of an insertion sort using basic arrays
     * @param array the array to sort
     * @return the sorted array
     */
    public static int[] insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i - 1;
            while(j >= 0 && current < array[j]) {
                array[j+1] = array[j];
                j--;
            }
            // at this point we've exited, so j is either -1
            // or it's at the first element where current >= a[j]
            array[j+1] = current;
        }
        return array;
    }

    private static ArrayList<Integer> generateList(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            list.add(rand.nextInt(1000));
        }
        return list;
    }

    public static void main(String[] args) {
        int[] unordered = new int[]{15, 11, 23, 654, 1};
        System.out.println("--- Bubble Sort (Array List) ---");
        System.out.println(Sorting.bubbleSort(new ArrayList<>(Arrays.asList(10, 14, 155, 57, 32, 16))));
        System.out.println("--- Bubble Sort ---");
        for (int i: Sorting.bubbleSort(unordered)) {
            System.out.println(i);
        }
        System.out.println("--- Insertion Sort ---");
        for (int i: Sorting.insertionSort(unordered)) {
            System.out.println(i);
        }
    }

}
