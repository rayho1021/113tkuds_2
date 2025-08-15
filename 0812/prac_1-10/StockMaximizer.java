/*
題目 7： 最大化股票利潤
題目描述：
你有 K 次交易機會（一次交易包含買入和賣出），給定股票每日價格陣列，求最大利潤。使用 heap 輔助解決。
- 可以使用 Min Heap 追蹤買入的最佳時機
- 使用 Max Heap 追蹤賣出的最佳時機
- 考慮貪心演算法：將所有可獲利的交易加入 heap，然後選擇最佳的 K 個
- 注意處理 K 大於可能交易次數的情況

測試案例：
價格：[2,4,1], K=2
答案：2 (在價格2時買入，在價格4時賣出)

價格：[3,2,6,5,0,3], K=2
答案：7 (在價格2時買入，在價格6時賣出得利潤4；在價格0時買入，在價格3時賣出得利潤3)

價格：[1,2,3,4,5], K=2
答案：4 (一次交易：1買入5賣出)
*/

import java.util.Arrays;
import java.util.PriorityQueue;

public class StockMaximizer {

    public int maxProfit(int k, int[] prices) {
        if (prices == null || prices.length < 2 || k <= 0) {
            return 0;
        }

        // 當 k 大於等於總交易日數的一半時，可以進行任意次數的交易，
        if (k >= prices.length / 2) {
            int maxProfit = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }

        // 使用 Min Heap 來追蹤 K 個最大的利潤
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // 遍歷價格，找出所有正向利潤
        for (int i = 1; i < prices.length; i++) {
            int profit = prices[i] - prices[i - 1];
            if (profit > 0) {
                // 將利潤加入堆中
                minHeap.offer(profit);
                
                // 如果堆的大小超過 k，移除最小的利潤
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }
        }

        // 計算 K 個最大利潤的總和
        int totalProfit = 0;
        while (!minHeap.isEmpty()) {
            totalProfit += minHeap.poll();
        }

        return totalProfit;
    }

    public static void main(String[] args) {
        StockMaximizer solution = new StockMaximizer();

        // 測試案例 1
        int[] prices1 = {2, 4, 1};
        int k1 = 2;
        int result1 = solution.maxProfit(k1, prices1);
        System.out.println("測試案例 1：");
        System.out.println("價格: " + Arrays.toString(prices1) + ", K: " + k1);
        System.out.println("最大利潤: " + result1); 

        System.out.println("---");

        // 測試案例 2
        int[] prices2 = {3, 2, 6, 5, 0, 3};
        int k2 = 2;
        int result2 = solution.maxProfit(k2, prices2);
        System.out.println("測試案例 2：");
        System.out.println("價格: " + Arrays.toString(prices2) + ", K: " + k2);
        System.out.println("最大利潤: " + result2); // 預期輸出: 7

        System.out.println("---");

        // 測試案例 3
        int[] prices3 = {1, 2, 3, 4, 5};
        int k3 = 2;
        int result3 = solution.maxProfit(k3, prices3);
        System.out.println("測試案例 3：");
        System.out.println("價格: " + Arrays.toString(prices3) + ", K: " + k3);
        System.out.println("最大利潤: " + result3); 
        
        System.out.println("---");
        
        // 額外測試案例，K=1
        int[] prices4 = {1, 2, 3, 4, 5};
        int k4 = 1;
        int result4 = solution.maxProfit(k4, prices4);
        System.out.println("測試案例 4 (K=1)：");
        System.out.println("價格: " + Arrays.toString(prices4) + ", K: " + k4);
        System.out.println("最大利潤: " + result4); 
    }
}