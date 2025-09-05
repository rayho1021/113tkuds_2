/*
題目: 高鐵連假加班車 Two Sum
1. 需要快速指派「兩個班次」，以滿足臨時新增的需求。
2. 給定一個陣列，每個值是該班次目前尚可釋出的座位數，以及一個 target（臨時新增需求總座位）。
3. 找出兩個不同班次索引 i, j，其座位數和正好等於 target。若無法精準湊到，輸出 -1 -1。
4. 條件：
* 2 <= n <= 1e5
* -1e9 <= seats[i], target <= 1e9
* 多組解只需輸出任一組即可
5. 解法核心：HashMap → 查表法
6. 複雜度：時間 O(n)，空間 O(n)
7.
- 輸入：
第一行：n target
第二行：n 個整數（每班剩餘座位數）
- 輸出：
若找到：輸出 i j
若無解：輸出 -1 -1
*/

import java.util.*;  

public class LC01_TwoSum_THSRHoliday {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt(); // 讀取 n 和 target
        long target = sc.nextLong();

        long[] seats = new long[n];
        for (int i = 0; i < n; i++) {
            seats[i] = sc.nextLong();
        }

        // HashMap<需要的座位數, 索引>
        Map<Long, Integer> map = new HashMap<>();
        int index1 = -1, index2 = -1;

        for (int i = 0; i < n; i++) {
            long x = seats[i];

            // 若目前座位數 x 是某人「需要的」，直接完成
            if (map.containsKey(x)) {
                index1 = map.get(x);
                index2 = i;
                break;
            }

            // 否則紀錄還需要多少
            long need = target - x;
            map.put(need, i);
        }

        if (index1 == -1) {
            System.out.println("-1 -1");
        } else {
            System.out.println("每班剩餘座位數: " + index1 + " " + index2); 
        } 

        sc.close();
    }
}


/*
解題思路
1. 掃描陣列：每個數字 x 檢查是否有人在等它。
2. 利用 HashMap 紀錄「需要的數字 → 索引」。
3. 條件：
- 若找到匹配，輸出索引。
- 若無匹配，輸出 -1 -1。
4. 時間/空間複雜度：
- 時間 O(n) → 一次掃描
- 空間 O(n) → HashMap 存需求
*/
