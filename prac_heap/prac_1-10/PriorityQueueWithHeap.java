/*
題目 3：使用 Heap 實作優先級佇列
題目描述：
實作一個支援優先級的任務佇列，每個任務有名稱和優先級（數字越大優先級越高）。支援：
* addTask(String name, int priority)：添加任務
* executeNext()：執行優先級最高的任務
* peek()：查看下一個要執行的任務
* changePriority(String name, int newPriority)：修改任務優先級

測試案例：
添加：("備份", 1), ("緊急修復", 5), ("更新", 3)
執行順序：緊急修復 → 更新 → 備份
*/

import java.util.NoSuchElementException;
import java.util.PriorityQueue;


public class PriorityQueueWithHeap {
    
    /**
     * 任務類別
     */
    static class Task {
        String name;
        int priority;
        long timestamp;
        
        public Task(String name, int priority) {
            this.name = name;
            this.priority = priority;
            this.timestamp = System.nanoTime(); // 用於處理相同優先級
        }
        
        @Override
        public String toString() {
            return name + "(優先級:" + priority + ")";
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Task task = (Task) obj;
            return name.equals(task.name);
        }
        
        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
    
    private PriorityQueue<Task> heap;
    
    public PriorityQueueWithHeap() {
        // 優先級高的在前，相同優先級時間戳記小的在前
        this.heap = new PriorityQueue<>((a, b) -> {
            if (a.priority != b.priority) {
                return Integer.compare(b.priority, a.priority); // 高優先級在前
            }
            return Long.compare(a.timestamp, b.timestamp); // 先添加的在前
        });
    }
    
    /**
     * 添加任務
     */
    public void addTask(String name, int priority) {
        Task task = new Task(name, priority);
        heap.offer(task);
        System.out.println("添加任務：" + task);
    }
    
    /**
     * 執行優先級最高的任務
     */
    public Task executeNext() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("沒有待執行的任務");
        }
        
        Task task = heap.poll();
        System.out.println("執行任務：" + task);
        return task;
    }
    
    /**
     * 查看下一個要執行的任務（不移除）
     */
    public Task peek() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("沒有待執行的任務");
        }
        
        return heap.peek();
    }
    
    /**
     * 修改任務的優先級
     */
    public boolean changePriority(String name, int newPriority) {
        // 創建臨時任務用於查找
        Task targetTask = new Task(name, 0);
        
        // 查找並移除舊任務
        Task oldTask = null;
        for (Task task : heap) {
            if (task.equals(targetTask)) {
                oldTask = task;
                break;
            }
        }
        
        if (oldTask == null) {
            System.out.println("找不到任務：" + name);
            return false;
        }
        
        // 移除舊任務並添加新任務
        heap.remove(oldTask);
        Task newTask = new Task(name, newPriority);
        heap.offer(newTask);
        
        System.out.println("修改任務優先級：" + name + " 從 " + oldTask.priority + " 改為 " + newPriority);
        return true;
    }
    
    /**
     * 檢查佇列是否為空
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    
    /**
     * 取得佇列中任務數量
     */
    public int size() {
        return heap.size();
    }
    
    /**
     * 顯示所有待執行任務（用於除錯）
     */
    public void showAllTasks() {
        if (heap.isEmpty()) {
            System.out.println("目前沒有待執行的任務");
            return;
        }
        
        System.out.print("待執行任務：");
        Task[] tasks = heap.toArray(new Task[0]);
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(tasks[i]);
            if (i < tasks.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== 優先級任務佇列測試 ===\n");
        
        PriorityQueueWithHeap taskQueue = new PriorityQueueWithHeap();
        
        // 測試添加任務
        System.out.println("1. 測試添加任務：");
        taskQueue.addTask("備份", 1);
        taskQueue.addTask("緊急修復", 5);
        taskQueue.addTask("更新", 3);
        
        System.out.println();
        taskQueue.showAllTasks();
        
        // 測試查看下一個任務
        System.out.println("\n2. 測試查看下一個任務：");
        System.out.println("下一個任務：" + taskQueue.peek());
        
        // 測試執行任務
        System.out.println("\n3. 測試執行任務：");
        while (!taskQueue.isEmpty()) {
            taskQueue.executeNext();
        }
        
        // 重新添加任務測試優先級修改
        System.out.println("\n4. 測試修改優先級：");
        taskQueue.addTask("任務A", 2);
        taskQueue.addTask("任務B", 4);
        taskQueue.addTask("任務C", 1);
        
        System.out.println("修改前：");
        taskQueue.showAllTasks();
        
        taskQueue.changePriority("任務C", 6);
        
        System.out.println("修改後：");
        taskQueue.showAllTasks();
        
        System.out.println("\n執行修改後的任務：");
        while (!taskQueue.isEmpty()) {
            taskQueue.executeNext();
        }
        
        // 測試相同優先級
        System.out.println("\n5. 測試相同優先級（按添加順序執行）：");
        taskQueue.addTask("第一個", 3);
        taskQueue.addTask("第二個", 3);
        taskQueue.addTask("第三個", 3);
        
        while (!taskQueue.isEmpty()) {
            taskQueue.executeNext();
        }
        
        // 測試邊界情況
        System.out.println("\n6. 測試邊界情況：");
        try {
            taskQueue.peek();
        } catch (NoSuchElementException e) {
            System.out.println("正確處理空佇列的 peek(): " + e.getMessage());
        }
        
        try {
            taskQueue.executeNext();
        } catch (NoSuchElementException e) {
            System.out.println("正確處理空佇列的 executeNext(): " + e.getMessage());
        }
        
        System.out.println("修改不存在的任務：" + taskQueue.changePriority("不存在", 5));
        
        System.out.println("\n=== 測試完成 ===");
    }
}