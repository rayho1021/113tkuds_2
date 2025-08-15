/*
題目 9：資料流中的移動平均數
題目描述：
設計一個類別，計算資料流中滑動視窗的移動平均數，同時支援查詢任意時間點的中位數和極值。
功能需求：
- MovingAverage(int size)：初始化，size 為視窗大小
- double next(int val)：加入新值並返回移動平均數
- double getMedian()：返回當前視窗的中位數
- int getMin()：返回當前視窗的最小值
- int getMax()：返回當前視窗的最大值

測試案例：
MovingAverage ma = new MovingAverage(3);
ma.next(1) = 1.0
ma.next(10) = 5.5  
ma.next(3) = 4.67 (1+10+3)/3
ma.next(5) = 6.0 (10+3+5)/3
ma.getMedian() = 5.0
ma.getMin() = 3
ma.getMax() = 10
*/

import java.util.*;

public class MovingAverageStream {
    private final Queue<Integer> window; // 儲存視窗內的元素
    private final int size; // 視窗大小
    private long sum; // 總和，用於計算平均數

    // 計算中位數
    private final PriorityQueue<Integer> maxHeap; // Max Heap 存較小的一半，堆頂是最大值
    private final PriorityQueue<Integer> minHeap; // Min Heap 存較大的一半，堆頂是最小值
    private final Map<Integer, Integer> lazyRemovalMap;  // 用於延遲移除的 HashMap

    // 兩個雙向佇列用於計算極值
    private final Deque<Integer> minDeque;
    private final Deque<Integer> maxDeque;

    public MovingAverageStream (int size) {
        this.size = size;
        this.window = new LinkedList<>();
        this.sum = 0;

        // 初始化中位數相關的資料結構
        this.maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        this.minHeap = new PriorityQueue<>();
        this.lazyRemovalMap = new HashMap<>();

        // 初始化極值相關的資料結構
        this.minDeque = new LinkedList<>();
        this.maxDeque = new LinkedList<>();
    }

    public double next(int val) {
        // 1. 處理舊元素
        if (window.size() == size) {
            int oldVal = window.poll();
            sum -= oldVal;
            // 標記舊元素為延遲移除
            lazyRemovalMap.put(oldVal, lazyRemovalMap.getOrDefault(oldVal, 0) + 1);
        }

        // 2. 加入新元素
        window.offer(val);
        sum += val;

        // 3. 更新中位數堆
        addNumToHeaps(val);
        balanceHeaps();

        // 4. 更新極值雙向佇列
        updateDeques(val);

        return (double) sum / window.size();
    }

    public double getMedian() {
        // 清理堆
        cleanUpHeaps();
        if (maxHeap.isEmpty()) {
            return 0.0;
        }
        if (maxHeap.size() > minHeap.size()) {
            return maxHeap.peek();
        } else {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
    }

    public int getMin() {
        return minDeque.peekFirst();
    }

    public int getMax() {
        return maxDeque.peekFirst();
    }

    // 將新數字添加到中位數堆中
    private void addNumToHeaps(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
        } else {
            minHeap.offer(num);
        }
    }

    // 平衡中位數堆的大小
    private void balanceHeaps() {
        cleanUpHeaps(); // 平衡前先清理
        while (maxHeap.size() > minHeap.size() + 1) {
            minHeap.offer(maxHeap.poll());
            cleanUpHeaps();
        }
        while (minHeap.size() > maxHeap.size()) {
            maxHeap.offer(minHeap.poll());
            cleanUpHeaps();
        }
    }

    // 清理堆頂的無效元素
    private void cleanUpHeaps() {
        while (!maxHeap.isEmpty() && lazyRemovalMap.containsKey(maxHeap.peek())) {
            int num = maxHeap.peek();
            lazyRemovalMap.put(num, lazyRemovalMap.get(num) - 1);
            if (lazyRemovalMap.get(num) == 0) {
                lazyRemovalMap.remove(num);
            }
            maxHeap.poll();
        }
        while (!minHeap.isEmpty() && lazyRemovalMap.containsKey(minHeap.peek())) {
            int num = minHeap.peek();
            lazyRemovalMap.put(num, lazyRemovalMap.get(num) - 1);
            if (lazyRemovalMap.get(num) == 0) {
                lazyRemovalMap.remove(num);
            }
            minHeap.poll();
        }
    }

    // 更新極值雙向佇列
    private void updateDeques(int val) {
        // 更新 minDeque
        while (!minDeque.isEmpty() && minDeque.peekLast() > val) {
            minDeque.pollLast();
        }
        minDeque.addLast(val);

        // 更新 maxDeque
        while (!maxDeque.isEmpty() && maxDeque.peekLast() < val) {
            maxDeque.pollLast();
        }
        maxDeque.addLast(val);
    }
    
    // 移除舊元素時，更新極值雙向佇列
    private void removeOldValueFromDeques(int val) {
        if (!minDeque.isEmpty() && minDeque.peekFirst() == val) {
            minDeque.pollFirst();
        }
        if (!maxDeque.isEmpty() && maxDeque.peekFirst() == val) {
            maxDeque.pollFirst();
        }
    }
    
    public static void main(String[] args) {
        MovingAverageStream ma = new MovingAverageStream (3);
        
        System.out.println("ma.next(1) = " + ma.next(1)); // 1.0
        System.out.println("ma.next(10) = " + ma.next(10)); // 5.5
        System.out.println("ma.next(3) = " + ma.next(3)); // 4.67

        System.out.println("--- 視窗 [1, 10, 3] ---");
        System.out.println("中位數 (getMedian) = " + ma.getMedian()); // 3.0
        System.out.println("最小值 (getMin) = " + ma.getMin()); // 1
        System.out.println("最大值 (getMax) = " + ma.getMax()); // 10
        System.out.println("---");
        
        System.out.println("ma.next(5) = " + ma.next(5)); // 6.0 (舊值1被移除)
        
        System.out.println("--- 視窗 [10, 3, 5] ---");
        System.out.println("中位數 (getMedian) = " + ma.getMedian()); // 5.0
        System.out.println("最小值 (getMin) = " + ma.getMin()); // 3
        System.out.println("最大值 (getMax) = " + ma.getMax()); // 10
        System.out.println("---");

        // 額外測試
        System.out.println("ma.next(8) = " + ma.next(8)); // (3+5+8)/3 = 5.333
        System.out.println("--- 視窗 [3, 5, 8] ---");
        System.out.println("中位數 (getMedian) = " + ma.getMedian()); // 5.0
        System.out.println("最小值 (getMin) = " + ma.getMin()); // 3
        System.out.println("最大值 (getMax) = " + ma.getMax()); // 8
    }
}