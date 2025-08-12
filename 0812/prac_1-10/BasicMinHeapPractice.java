/*
題目 1：實作基本 Min Heap 操作
實作一個基本的 Min Heap，支援以下操作：
insert(int val)：插入一個元素
extractMin()：取出並返回最小元素
getMin()：查看最小元素但不移除
size()：返回 heap 的大小
isEmpty()：檢查 heap 是否為空

測試案例：
插入順序：15, 10, 20, 8, 25, 5
期望的 extractMin 順序：5, 8, 10, 15, 20, 25
*/

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BasicMinHeapPractice {
    private ArrayList<Integer> heap;
    
    /**
     * 初始化空的 heap
     */
    public BasicMinHeapPractice() {
        this.heap = new ArrayList<>();
    }
    
    /**
     * 插入一個元素到 heap 中
     * 時間複雜度：O(log n)
     */
    public void insert(int val) {
        // 1. 將新元素加到陣列末尾
        heap.add(val);
        
        // 2. 向上調整維持 heap property
        heapifyUp(heap.size() - 1);
        
        System.out.println("插入 " + val + "，當前 heap: " + heap);
    }
    
    /**
     * 取出並返回最小元素（根節點）
     * 時間複雜度：O(log n)
     */
    public int extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        
        // 1. 保存最小值（根節點）
        int min = heap.get(0);
        
        // 2. 將最後一個元素移到根部
        int lastElement = heap.get(heap.size() - 1);
        heap.set(0, lastElement);
        
        // 3. 移除最後一個元素
        heap.remove(heap.size() - 1);
        
        // 4. 如果還有元素，向下調整
        if (!isEmpty()) {
            heapifyDown(0);
        }
        
        System.out.println("取出最小值 " + min + "，剩餘 heap: " + heap);
        return min;
    }
    
    /**
     * 查看最小元素但不移除
     * 時間複雜度：O(1)
     */
    public int getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }
    
    /**
     * 返回 heap 的大小
     * 時間複雜度：O(1)
     */
    public int size() {
        return heap.size();
    }
    
    /**
     * 檢查 heap 是否為空
     * 時間複雜度：O(1)
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /**
     * 向上調整（用於 insert 後維持 heap property）
     * 將指定索引的元素向上調整到正確位置
     */
    private void heapifyUp(int index) {
        // 如果已經是根節點，不需要調整
        if (index == 0) {
            return;
        }
        
        // 計算父節點索引
        int parentIndex = (index - 1) / 2;
        
        // 如果當前節點小於父節點，需要交換
        if (heap.get(index) < heap.get(parentIndex)) {
            swap(index, parentIndex);
            // 遞迴向上調整
            heapifyUp(parentIndex);
        }
    }
    
    /**
     * 向下調整（用於 extractMin 後維持 heap property）
     * 將指定索引的元素向下調整到正確位置
     */
    private void heapifyDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;
        
        // 找出父節點和兩個子節點中最小的
        if (leftChild < heap.size() && heap.get(leftChild) < heap.get(smallest)) {
            smallest = leftChild;
        }
        
        if (rightChild < heap.size() && heap.get(rightChild) < heap.get(smallest)) {
            smallest = rightChild;
        }
        
        // 如果最小值不是父節點，需要交換並繼續向下調整
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }
    
    /**
     * 交換 heap 中兩個位置的元素
     */
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    
    /**
     * 返回 heap 的字串表示（用於除錯）
     */
    @Override
    public String toString() {
        return heap.toString();
    }

    public static void main(String[] args) {
        System.out.println("=== Min Heap 測試 ===\n");
        
        BasicMinHeapPractice minHeap = new BasicMinHeapPractice();
        
        // 測試插入操作
        System.out.println("1. 測試插入操作：");
        int[] insertValues = {15, 10, 20, 8, 25, 5};
        
        for (int val : insertValues) {
            minHeap.insert(val);
        }
        
        System.out.println("\n插入完成，最終 heap: " + minHeap);
        System.out.println("當前大小: " + minHeap.size());
        System.out.println("最小值: " + minHeap.getMin());
        
        // 測試取出最小值
        System.out.println("\n2. 測試 extractMin 操作：");
        System.out.println("期望順序: 5, 8, 10, 15, 20, 25");
        System.out.print("實際順序: ");
        
        while (!minHeap.isEmpty()) {
            int min = minHeap.extractMin();
            System.out.print(min);
            if (!minHeap.isEmpty()) {
                System.out.print(", ");
            }
        }
        System.out.println();
        
        // 測試邊界情況
        System.out.println("\n3. 測試邊界情況：");
        System.out.println("heap 是否為空: " + minHeap.isEmpty());
        System.out.println("heap 大小: " + minHeap.size());
        
        try {
            minHeap.getMin();
        } catch (NoSuchElementException e) {
            System.out.println("正確處理空 heap 的 getMin(): " + e.getMessage());
        }
        
        try {
            minHeap.extractMin();
        } catch (NoSuchElementException e) {
            System.out.println("正確處理空 heap 的 extractMin(): " + e.getMessage());
        }
        
        // 額外測試：重複值
        System.out.println("\n4. 測試重複值：");
        minHeap.insert(10);
        minHeap.insert(5);
        minHeap.insert(10);
        minHeap.insert(3);
        
        System.out.println("插入 10, 5, 10, 3 後:");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.extractMin() + " ");
        }
        
        System.out.println("\n\n=== 測試完成 ===");
    }
}