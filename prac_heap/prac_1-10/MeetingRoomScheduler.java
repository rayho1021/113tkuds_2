/*
題目 8：會議室排程最佳化
題目描述：
給定多個會議的開始和結束時間，求最少需要多少個會議室。同時，如果只有 N 個會議室，求如何安排會議使總會議時間最大。
- 使用 Min Heap 追蹤會議室的結束時間
- 用事件排序的方法處理會議開始和結束
- 第二部分可能需要動態規劃或貪心演算法
- 考慮將問題轉換為區間排程問題

測試案例：
會議：[[0,30],[5,10],[15,20]]
答案：需要2個會議室

會議：[[9,10],[4,9],[4,17]]  
答案：需要2個會議室

會議：[[1,5],[8,9],[8,9]]
答案：需要2個會議室

如果只有1個會議室，會議：[[1,4],[2,3],[4,6]]
最佳安排：選擇[1,4]和[4,6]，總時間 = 5
*/

import java.util.*;

public class MeetingRoomScheduler {
    
    /**
     * 會議類別
     */
    static class Meeting {
        int start;
        int end;
        
        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
        
        public int getDuration() {
            return end - start;
        }
        
        @Override
        public String toString() {
            return "[" + start + "," + end + "]";
        }
    }
    
    /**
     * 計算最少需要多少個會議室
     * 使用 Min Heap 追蹤會議室的結束時間
     */
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // 將會議按開始時間排序
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        
        // Min Heap 儲存會議室的結束時間
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        
        for (int[] interval : intervals) {
            int start = interval[0];
            int end = interval[1];
            
            // 如果有會議室已經結束，可以重複使用
            if (!heap.isEmpty() && heap.peek() <= start) {
                heap.poll();
            }
            
            // 為當前會議分配會議室
            heap.offer(end);
        }
        
        return heap.size();
    }
    
    /**
     * 在有限會議室下，安排會議使總時間最大
     * 按結束時間排序，依序選擇不衝突的會議
     */
    public static ScheduleResult maxMeetingTime(int[][] intervals, int numRooms) {
        if (intervals == null || intervals.length == 0 || numRooms <= 0) {
            return new ScheduleResult(0, new ArrayList<>());
        }
        
        // 轉換為 Meeting 物件並按結束時間排序
        List<Meeting> meetings = new ArrayList<>();
        for (int[] interval : intervals) {
            meetings.add(new Meeting(interval[0], interval[1]));
        }
        
        // 按結束時間排序（貪心策略）
        meetings.sort((a, b) -> Integer.compare(a.end, b.end));
        
        // 每個會議室的最後結束時間
        PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();
        for (int i = 0; i < numRooms; i++) {
            roomEndTimes.offer(0);
        }
        
        List<Meeting> selectedMeetings = new ArrayList<>();
        int totalTime = 0;
        
        for (Meeting meeting : meetings) {
            // 找到最早結束的會議室
            int earliestEnd = roomEndTimes.poll();
            
            // 如果會議可以安排在這個會議室
            if (earliestEnd <= meeting.start) {
                selectedMeetings.add(meeting);
                totalTime += meeting.getDuration();
                roomEndTimes.offer(meeting.end);
            } else {
                // 無法安排，會議室狀態不變
                roomEndTimes.offer(earliestEnd);
            }
        }
        
        return new ScheduleResult(totalTime, selectedMeetings);
    }
    
    /**
     * 排程結果類別
     */
    static class ScheduleResult {
        int totalTime;
        List<Meeting> selectedMeetings;
        
        public ScheduleResult(int totalTime, List<Meeting> selectedMeetings) {
            this.totalTime = totalTime;
            this.selectedMeetings = selectedMeetings;
        }
    }
    
    /**
     * 列印會議陣列
     */
    public static void printMeetings(int[][] meetings) {
        System.out.print("[");
        for (int i = 0; i < meetings.length; i++) {
            System.out.print("[" + meetings[i][0] + "," + meetings[i][1] + "]");
            if (i < meetings.length - 1) {
                System.out.print(",");
            }
        }
        System.out.print("]");
    }
    
    /**
     * 測試方法：最少會議室數量
     */
    public static void testMinRooms(int[][] meetings) {
        System.out.print("會議：");
        printMeetings(meetings);
        
        int result = minMeetingRooms(meetings);
        System.out.println(" → 需要 " + result + " 個會議室");
    }
    
    /**
     * 測試方法：有限會議室最大時間
     */
    public static void testMaxTime(int[][] meetings, int numRooms) {
        System.out.print("會議：");
        printMeetings(meetings);
        System.out.println("，會議室數量：" + numRooms);
        
        ScheduleResult result = maxMeetingTime(meetings, numRooms);
        
        System.out.println("最佳安排：" + result.selectedMeetings + "，總時間 = " + result.totalTime);
    }
    
    public static void main(String[] args) {
        System.out.println("=== 會議室排程 ===\n");
        
        // 最少會議室數量測試
        System.out.println("最少會議室數量");
        
        int[][] meetings1 = {{0, 30}, {5, 10}, {15, 20}};
        testMinRooms(meetings1);
        
        int[][] meetings2 = {{9, 10}, {4, 9}, {4, 17}};
        testMinRooms(meetings2);
        
        int[][] meetings3 = {{1, 5}, {8, 9}, {8, 9}};
        testMinRooms(meetings3);
        
        // 有限會議室最大時間測試
        System.out.println("\n有限會議室最大時間");
        
        int[][] meetings4 = {{1, 4}, {2, 3}, {4, 6}};
        testMaxTime(meetings4, 1);
        
        int[][] meetings5 = {{0, 30}, {5, 10}, {15, 20}};
        testMaxTime(meetings5, 2);
        
        int[][] meetings6 = {{9, 10}, {4, 9}, {4, 17}};
        testMaxTime(meetings6, 1);
        
        
        // 單一會議
        int[][] singleMeeting = {{1, 3}};
        System.out.println("\n單一會議：");
        testMinRooms(singleMeeting);
        testMaxTime(singleMeeting, 1);
        
        // 無重疊會議
        int[][] noOverlap = {{1, 2}, {3, 4}, {5, 6}};
        System.out.println("\n無重疊會議：");
        testMinRooms(noOverlap);
        testMaxTime(noOverlap, 1);
        
        // 完全重疊會議
        int[][] allOverlap = {{1, 5}, {1, 5}, {1, 5}};
        System.out.println("\n完全重疊會議：");
        testMinRooms(allOverlap);
        testMaxTime(allOverlap, 2);
        
        System.out.println("\n=== 測試完成 ===");
    }
}