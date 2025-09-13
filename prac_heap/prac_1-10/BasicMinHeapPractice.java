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
import java.util.List;
import java.util.NoSuchElementException;

public class BasicMinHeapPractice {

    private List<Integer> heap;

    public BasicMinHeapPractice() {
        this.heap = new ArrayList<>();
    }

    /**
     * 將元素插入堆中
     */
    public void insert(int val) {
        heap.add(val);  // 要插入的值
        int currentIndex = heap.size() - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (heap.get(currentIndex) < heap.get(parentIndex)) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    /**
     * 取出並返回最小元素
     */
    public int extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        int minVal = heap.get(0);
        int lastVal = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastVal);
            int currentIndex = 0;
            while (true) {
                int leftChildIndex = 2 * currentIndex + 1;
                int rightChildIndex = 2 * currentIndex + 2;
                int smallestIndex = currentIndex;

                if (leftChildIndex < heap.size() && heap.get(leftChildIndex) < heap.get(smallestIndex)) {
                    smallestIndex = leftChildIndex;
                }
                if (rightChildIndex < heap.size() && heap.get(rightChildIndex) < heap.get(smallestIndex)) {
                    smallestIndex = rightChildIndex;
                }

                if (smallestIndex != currentIndex) {
                    swap(currentIndex, smallestIndex);
                    currentIndex = smallestIndex;
                } else {
                    break;
                }
            }
        }
        return minVal;
    }

    /**
     * 查看最小元素但不移除
     */
    public int getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }
        return heap.get(0);
    }

    /**
     * 返回堆的大小
     * @return 堆中元素的數量
     */
    public int size() {
        return heap.size();
    }

    /**
     * 檢查堆是否為空
     * @return 如果堆為空則為 true，否則為 false
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * 交換兩個索引位置的元素
     */
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) {
        BasicMinHeapPractice minHeap = new BasicMinHeapPractice();

        System.out.println("插入元素: 15, 10, 20, 8, 25, 5");
        minHeap.insert(15);
        minHeap.insert(10);
        minHeap.insert(20);
        minHeap.insert(8);
        minHeap.insert(25);
        minHeap.insert(5);

        System.out.println("當前堆的大小: " + minHeap.size());
        System.out.println("查看最小元素 (getMin): " + minHeap.getMin());

        System.out.println("\n開始提取最小元素:");
        System.out.print("期望的 extractMin 順序: ");
        List<Integer> extractedValues = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            extractedValues.add(minHeap.extractMin());
        }
        System.out.println(extractedValues);
        // 期望輸出: [5, 8, 10, 15, 20, 25]

        System.out.println("提取後堆的大小: " + minHeap.size());
    }
}