import java.util.Scanner;

public class M01_BuildHeap {
    /*
     * Time Complexity: O(n)
     * 說明：總時間為 Σ(每層節點數 × 該層最大下沉深度) = O(n)，優於逐一插入的 O(n log n)
     *      從最後一個非葉節點開始，向前進行 heapifyDown，最後一個非葉節點的索引為 (n/2) - 1
     */
    public static void buildHeap(int[] arr, String type) { //arr: 待建堆的陣列，type: 堆的類型："max" 或 "min"
        int n = arr.length;
        boolean isMaxHeap = type.equals("max");

        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(arr, i, n, isMaxHeap);
        }
    }
    
    /*
     * Time Complexity: O(log n)
     * 說明：最壞情況下需要從當前節點一直下沉到葉節點，下沉深度最多為樹的高度 log n，每次下沉只需要與左右子節點比較並可能交換
     */
    private static void heapifyDown(int[] arr, int index, int size, boolean isMaxHeap) { //isMaxHeap 是否為最大堆
        int targetIndex = index;
        int leftChild = getLeftChild(index);
        int rightChild = getRightChild(index);
        
        // 應該放在父節點位置的元素
        if (leftChild < size) {
            if (shouldSwap(arr[leftChild], arr[targetIndex], isMaxHeap)) {
                targetIndex = leftChild;
            }
        }
        
        if (rightChild < size) {
            if (shouldSwap(arr[rightChild], arr[targetIndex], isMaxHeap)) {
                targetIndex = rightChild;
            }
        }
        
        // 如果需要交換，則進行交換並繼續向下堆化
        if (targetIndex != index) {
            swap(arr, index, targetIndex);
            heapifyDown(arr, targetIndex, size, isMaxHeap);
        }
    }
    
    /**
     * 判斷是否需要交換
     */
    private static boolean shouldSwap(int child, int parent, boolean isMaxHeap) {
        if (isMaxHeap) {
            return child > parent;  // 最大堆：子節點 > 父節點時交換
        } else {
            return child < parent;  // 最小堆：子節點 < 父節點時交換
        }
    }
    
    /**
     * 獲取左子節點索引
     */
    private static int getLeftChild(int index) {
        return 2 * index + 1;
    }
    
    /**
     * 獲取右子節點索引
     */
    private static int getRightChild(int index) {
        return 2 * index + 2;
    }
    
    /**
     * 交換陣列中兩個元素
     */
    private static void swap(int[] arr, int i, int j) { // i: 第一個元素索引；j: 第二個元素索引
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * 列印陣列
     */
    private static void printArray(int[] arr) { //arr: 待列印的陣列
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(arr[i]);
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取堆類型
        System.out.print("type (max/min) : ");
        String type = scanner.nextLine().trim();
        
        // 讀取陣列大小
        System.out.print("陣列大小 n = ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        // 讀取陣列元素
        System.out.print("陣列元素 : ");
        String[] elements = scanner.nextLine().trim().split("\\s+");
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(elements[i]);
        }
        
        // 建構堆
        buildHeap(arr, type);
        
        // 輸出結果
        System.out.print("輸出 : \n");
        printArray(arr);
        
        scanner.close();
    }
}