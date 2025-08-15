/*
練習 1.3：數字陣列處理器
實作以下功能：
1. 移除陣列中的重複元素
2. 合併兩個已排序的陣列
3. 找出陣列中出現頻率最高的元素
4. 將陣列分割成兩個相等（或近似相等）的子陣列
*/

import java.util.*;

public class NumberArrayProcessor {
    
    /**
     * Remove duplicate elements from array
     */
    public static int[] removeDuplicates(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        
        if (array.length <= 1) {
            return array.clone();
        }
        
        // Use LinkedHashSet to maintain order and remove duplicates
        Set<Integer> seen = new LinkedHashSet<>();
        for (int num : array) {
            seen.add(num);
        }
        
        // Convert back to array
        int[] result = new int[seen.size()];
        int index = 0;
        for (int num : seen) {
            result[index++] = num;
        }
        
        return result;
    }
    
    /**
     * Merge two sorted arrays
     */
    public static int[] mergeSortedArrays(int[] array1, int[] array2) {
        if (array1 == null || array2 == null) {
            throw new IllegalArgumentException("Arrays cannot be null");
        }
        
        int[] result = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        
        // Merge while both arrays have elements
        while (i < array1.length && j < array2.length) {
            if (array1[i] <= array2[j]) {
                result[k++] = array1[i++];
            } else {
                result[k++] = array2[j++];
            }
        }
        
        // Copy remaining elements from array1
        while (i < array1.length) {
            result[k++] = array1[i++];
        }
        
        // Copy remaining elements from array2
        while (j < array2.length) {
            result[k++] = array2[j++];
        }
        
        return result;
    }
    
    /**
     * Find the most frequent element in array
     */
    public static int findMostFrequent(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        
        if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }
        
        // Count frequency of each element
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : array) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Find element with maximum frequency
        int mostFrequent = array[0];
        int maxFrequency = 0;
        
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                maxFrequency = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }
        
        return mostFrequent;
    }
    
    /**
     * Split array into two equal or nearly equal sub-arrays
     */
    public static int[][] splitArray(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        
        if (array.length == 0) {
            return new int[][] { {}, {} };
        }
        
        int midPoint = (array.length + 1) / 2; // For odd length, first array gets extra element
        
        // Create first sub-array
        int[] firstHalf = new int[midPoint];
        System.arraycopy(array, 0, firstHalf, 0, midPoint);
        
        // Create second sub-array
        int[] secondHalf = new int[array.length - midPoint];
        System.arraycopy(array, midPoint, secondHalf, 0, array.length - midPoint);
        
        return new int[][] { firstHalf, secondHalf };
    }
    
    /**
     * Print array in formatted way
     */
    public static void printArray(int[] array, String title) {
        System.out.print(title + ": ");
        if (array == null) {
            System.out.println("null");
        } else if (array.length == 0) {
            System.out.println("[]");
        } else {
            System.out.println(Arrays.toString(array));
        }
    }
    
    /**
     * Print 2D array (for split results)
     */
    public static void print2DArray(int[][] arrays, String title) {
        System.out.println(title + ":");
        if (arrays == null) {
            System.out.println("null");
            return;
        }
        
        for (int i = 0; i < arrays.length; i++) {
            System.out.printf("  Sub-array %d: %s%n", i + 1, Arrays.toString(arrays[i]));
        }
    }
    
    /**
     * Get frequency count of the most frequent element
     */
    public static int getMostFrequentCount(int[] array) {
        if (array == null || array.length == 0) {
            return 0;
        }
        
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int num : array) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        int maxFrequency = 0;
        for (int frequency : frequencyMap.values()) {
            maxFrequency = Math.max(maxFrequency, frequency);
        }
        
        return maxFrequency;
    }
    
    public static void main(String[] args) {
        System.out.println("Number Array Processor Demo");
        System.out.println("===========================");
        
        try {
            // Test arrays
            int[] arrayWithDuplicates = {1, 2, 3, 2, 4, 1, 5, 3, 6};
            int[] sortedArray1 = {1, 3, 5, 7, 9};
            int[] sortedArray2 = {2, 4, 6, 8, 10, 12};
            int[] frequencyArray = {1, 2, 3, 2, 2, 4, 5, 2, 6};
            int[] splitTestArray = {10, 20, 30, 40, 50, 60, 70};
            
            // Display test arrays
            printArray(arrayWithDuplicates, "Original array");
            printArray(sortedArray1, "Sorted array 1");
            printArray(sortedArray2, "Sorted array 2");
            printArray(frequencyArray, "Array for frequency test");
            printArray(splitTestArray, "Array for split test");
            
            // 1. Remove Duplicates
            System.out.println("\n" + "=".repeat(50));
            System.out.println("1. Remove Duplicates:");
            int[] noDuplicates = removeDuplicates(arrayWithDuplicates);
            printArray(noDuplicates, "Result");
            
            // 2. Merge Sorted Arrays
            System.out.println("\n" + "=".repeat(50));
            System.out.println("2. Merge Sorted Arrays:");
            int[] merged = mergeSortedArrays(sortedArray1, sortedArray2);
            printArray(merged, "Result");
            
            // 3. Find Most Frequent Element
            System.out.println("\n" + "=".repeat(50));
            System.out.println("3. Find Most Frequent Element:");
            int mostFrequent = findMostFrequent(frequencyArray);
            int frequency = getMostFrequentCount(frequencyArray);
            System.out.printf("Most frequent element: %d (appears %d times)%n", 
                            mostFrequent, frequency);
            
            // 4. Split Array
            System.out.println("\n" + "=".repeat(50));
            System.out.println("4. Split Array:");
            int[][] splitResult = splitArray(splitTestArray);
            print2DArray(splitResult, "Result");
            
            // Additional test with odd length
            System.out.println("\nTesting with odd length array:");
            int[] oddArray = {1, 2, 3, 4, 5};
            printArray(oddArray, "Original");
            int[][] oddSplit = splitArray(oddArray);
            print2DArray(oddSplit, "Split result");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("System error: " + e.getMessage());
        }
    }
}