import java.util.Scanner;

public class M11_HeapSortWithTie {
    
    /**
     * 學生類別，封裝分數和原始索引
     */
    static class Student {
        int score;
        int originalIndex;
        
        public Student(int score, int originalIndex) {
            this.score = score;
            this.originalIndex = originalIndex;
        }
        
        /**
         * 比較方法，用於最大堆
         */
        public int compareTo(Student other) {
            // 優先比較分數
            if (this.score != other.score) {
                return Integer.compare(this.score, other.score);
            }
            // 分數相同時，原始索引小的視為較大（在最大堆中優先取出）
            return Integer.compare(other.originalIndex, this.originalIndex);
        }
    }
    
    /*
     * Time Complexity: O(n)
     * 說明：從最後一個非葉節點開始向前進行 heapifyDown，雖然有 n/2 個節點需要處理
     *       但每層的節點數量和該層可能的下沉深度成反比，總時間為 O(n)
     */
    /**
     * 建立最大堆
     */
    public static void buildMaxHeap(Student[] students) {
        int n = students.length;
        
        // 從最後一個非葉節點開始，向前進行 heapifyDown
        for (int i = (n / 2) - 1; i >= 0; i--) {
            heapifyDown(students, i, n);
        }
    }
    
    /*
     * Time Complexity: O(log n)
     * 說明：最壞情況下需要從當前節點一直下沉到葉節點，下沉深度最多為樹的高度 log n
     *       每次下沉只需要與左右子節點比較並可能交換，為常數時間操作
     */
    private static void heapifyDown(Student[] students, int index, int heapSize) {
        int largest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        
        // 找出父節點和子節點中最大的
        if (leftChild < heapSize && students[leftChild].compareTo(students[largest]) > 0) {
            largest = leftChild;
        }
        
        if (rightChild < heapSize && students[rightChild].compareTo(students[largest]) > 0) {
            largest = rightChild;
        }
        
        // 如果需要交換，則進行交換並繼續向下堆
        if (largest != index) {
            swap(students, index, largest);
            heapifyDown(students, largest, heapSize);
        }
    }
    
    /*
     * Time Complexity: O(n log n)
     * 說明：建堆需要 O(n) 時間，之後進行 n-1 次取出操作，每次取出需要 O(log n) 的 heapifyDown
     *       總時間複雜度為 O(n) + O(n log n) = O(n log n)
     */
    
    // 堆排序
    public static void heapSort(Student[] students) {
        int n = students.length;
        
        // 建立最大堆
        buildMaxHeap(students);
        
        // 依次取出堆頂元素，放到陣列末尾
        for (int i = n - 1; i > 0; i--) {
            // 將堆頂（最大值）與末尾元素交換
            swap(students, 0, i);
            
            // 堆大小減一，重新調整堆
            heapifyDown(students, 0, i);
        }
    }
    
    /**
     * 交換陣列中兩個元素
     */
    private static void swap(Student[] students, int i, int j) {
        Student temp = students[i];
        students[i] = students[j];
        students[j] = temp;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取學生數量
        System.out.print("\n學生數量 n : ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        // 讀取成績
        System.out.print("成績 (用空格分隔) : ");
        String[] scoreInput = scanner.nextLine().trim().split("\\s+");
        
        // 建立學生陣列
        Student[] students = new Student[n];
        for (int i = 0; i < n; i++) {
            int score = Integer.parseInt(scoreInput[i]);
            students[i] = new Student(score, i);
        }
        
        // 進行堆排序
        heapSort(students);
        
        // 輸出排序結果
        System.out.print("排序結果 : ");
        for (int i = 0; i < n; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(students[i].score);
        }
        System.out.println();
        
        scanner.close();
    }
}