import java.util.*;

public class M12_MergeKTimeTables {
    
    
    // 時刻表條目類別，封裝時間、來源列表和索引
    static class TimeEntry implements Comparable<TimeEntry> {
        int time;       
        int whichList;  // 來源時刻表編號
        int index;      
        
        public TimeEntry(int time, int whichList, int index) {
            this.time = time;
            this.whichList = whichList;
            this.index = index;
        }
        
        @Override
        public int compareTo(TimeEntry other) {
            return Integer.compare(this.time, other.time);
        }
    }
    
    /**
     * 合併 K 個已排序的時刻表
     */
    public static List<Integer> mergeKTimeTables(List<List<Integer>> timeTables) { // timeTables: K 個已排序的時刻表
        List<Integer> result = new ArrayList<>();
        PriorityQueue<TimeEntry> minHeap = new PriorityQueue<>();
        
        // 將每個時刻表的第一個元素放入 Min-Heap
        for (int i = 0; i < timeTables.size(); i++) {
            List<Integer> table = timeTables.get(i);
            if (!table.isEmpty()) {
                minHeap.offer(new TimeEntry(table.get(0), i, 0));
            }
        }
        
        // K-路合併過程
        while (!minHeap.isEmpty()) {
            // 取出當前最小的時間
            TimeEntry current = minHeap.poll();
            result.add(current.time);
            
            // 從同一個時刻表取下一個元素（如果存在）
            List<Integer> sourceTable = timeTables.get(current.whichList);
            int nextIndex = current.index + 1;
            
            if (nextIndex < sourceTable.size()) {
                int nextTime = sourceTable.get(nextIndex);
                minHeap.offer(new TimeEntry(nextTime, current.whichList, nextIndex));
            }
        }
        
        return result;
    }
    
    /**
     * HH:mm 轉換分鐘
     */
    private static int timeToMinutes(String time) {
        if (time.contains(":")) {
            String[] parts = time.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            return hours * 60 + minutes;
        } else {
            // 如果是純數字，直接解析為分鐘
            return Integer.parseInt(time);
        }
    }
    
    /**
     * 分鐘轉換 HH:mm 
     */
    private static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取時刻表數量
        System.out.print("時刻表數量 K : ");
        int K = Integer.parseInt(scanner.nextLine().trim());
        
        List<List<Integer>> timeTables = new ArrayList<>();
        boolean isTimeFormat = false;  // 檢測輸入格式
        
        // 讀取每個時刻表
        for (int i = 0; i < K; i++) {
            System.out.print("時刻表 " + (i + 1) + " 長度 : ");
            int len = Integer.parseInt(scanner.nextLine().trim());
            
            System.out.print("時刻表 " + (i + 1) + " 內容 : ");
            String[] timeInput = scanner.nextLine().trim().split("\\s+");
            
            List<Integer> timeTable = new ArrayList<>();
            for (int j = 0; j < len; j++) {
                int timeInMinutes = timeToMinutes(timeInput[j]);
                timeTable.add(timeInMinutes);
                
                // 檢測是否為 HH:mm 格式
                if (timeInput[j].contains(":")) {
                    isTimeFormat = true;
                }
            }
            
            timeTables.add(timeTable);
        }
        
        // 合併時刻表
        List<Integer> mergedTimes = mergeKTimeTables(timeTables);
        
        // 輸出結果
        System.out.print("合併結果 : ");
        for (int i = 0; i < mergedTimes.size(); i++) {
            if (i > 0) System.out.print(" ");
            
            if (isTimeFormat) {
                System.out.print(minutesToTime(mergedTimes.get(i)));
            } else {
                System.out.print(mergedTimes.get(i));
            }
        }
        System.out.println();
        
        scanner.close();
    }
}