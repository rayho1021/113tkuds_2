import java.util.Scanner;

public class M02_YouBikeNextArrival {
    
    /**
     * 時間字串轉分鐘
     */
    private static int timeToMinutes(String time) { //(HH:mm)
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }
    
    /**
     * 分鐘轉時間字串
     */
    private static String minutesToTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }
    
    private static int findNextArrival(int[] times, int query) {  // times: 已排序的補給時間陣列；query: 查詢時間
        int left = 0;
        int right = times.length - 1;
        int result = -1;
        
        // 二分搜尋找第一個大於 query 的位置
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (times[mid] > query) {
                result = mid;  // 記錄可能的答案
                right = mid - 1;  // 繼續在左半部分尋找更小的符合條件
            } else {
                left = mid + 1;  // 在右半部分尋找
            }
        }
        
        return result; // 返回找到的索引，若無符合條件則返回 -1
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 補給時間數量
        System.out.print("補給時間數量 n = ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        // 補給時間轉換分鐘數
        int[] times = new int[n];
        System.out.println("輸入 " + n + " 個補給時間 (HH:mm):");
        for (int i = 0; i < n; i++) {
            String timeStr = scanner.nextLine().trim();
            times[i] = timeToMinutes(timeStr);
        }
        
        // 查詢時間
        System.out.print("查詢時間 (HH:mm) : ");
        String queryStr = scanner.nextLine().trim();
        int queryMinutes = timeToMinutes(queryStr);
        
        // 尋找下一個補給時間
        int nextIndex = findNextArrival(times, queryMinutes);
        
        // 輸出
        System.out.print("下一批到站時間 : ");
        if (nextIndex == -1) {
            System.out.println("No bike");
        } else {
            System.out.println(minutesToTime(times[nextIndex]));
        }
        
        scanner.close();
    }
}