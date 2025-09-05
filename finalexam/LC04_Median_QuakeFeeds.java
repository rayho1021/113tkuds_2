/*
題目: 地震速報雙資料源中位數
1. 已知兩個「已排序的浮點數數列（長度可不同）」，在「不真正合併」兩個序列的前提下，計算它們聯合集的中位數。
2. 解法核心：
- 在較短的陣列上做二分搜尋。
- 嘗試劃分索引 i 與 j，使左半元素數量等於 (n+m+1)/2。
- 驗證左右最大/最小關係
3. 條件：
* 0 <= n, m <= 2e5 且 n+m >= 1
* 數列各自已非遞減排序
* 值範圍：|value| <= 1e9
4.邊界:
- 其中一列為空，如 n=0 或 m=0
- 兩列都長度 1
- 值大量重複（全部相同）
- 長度落差極大（1 與 1e5） 
5. 複雜度：時間 O(log(min(n,m)))；空間 O(1)
6.
- 輸入：
第一行：n m（兩個數列長度）
第二行：n 個浮點數
第三行：m 個浮點數
- 輸出：
兩數列合併後的中位數（保留 1 位小數）。
*/

import java.util.*;

public class LC04_Median_QuakeFeeds {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        double[] A = new double[n];
        double[] B = new double[m];

        for (int i = 0; i < n; i++) A[i] = sc.nextDouble();
        for (int j = 0; j < m; j++) B[j] = sc.nextDouble();

        // 確保 A 是較短的數列
        if (n > m) {
            double[] tmp = A; A = B; B = tmp;
            int tmpLen = n; n = m; m = tmpLen;
        }

        int total = n + m;
        int half = (total + 1) / 2;  // 左半部元素數量

        int left = 0, right = n;
        while (left <= right) {
            int i = (left + right) / 2;  // A 分割
            int j = half - i;           // B 分割

            // 左右邊界處理，若超出用 ±∞
            double Aleft  = (i == 0) ? Double.NEGATIVE_INFINITY : A[i - 1];
            double Aright = (i == n) ? Double.POSITIVE_INFINITY : A[i];
            double Bleft  = (j == 0) ? Double.NEGATIVE_INFINITY : B[j - 1];
            double Bright = (j == m) ? Double.POSITIVE_INFINITY : B[j];

            // 驗證是否正確劃分
            if (Aleft <= Bright && Bleft <= Aright) {
                // 找到正確分割
                double median;
                if (total % 2 == 1) {
                    median = Math.max(Aleft, Bleft);
                } else {
                    median = (Math.max(Aleft, Bleft) + Math.min(Aright, Bright)) / 2.0;
                }
                System.out.printf("合併後中位數:" +"%.1f\n", median);
                return;
            } 
            else if (Aleft > Bright) {
                right = i - 1; // i 太大，往左移
            } 
            else {
                left = i + 1; // i 太小，往右移
            }
        }
        sc.close();
    }
}


/*
解題思路 :
1. 確保在"較短"數列上二分，避免時間複雜度偏高。
2. 計算左半總長度 = (n+m+1)/2，確保奇偶數情況都能正確取中位數。
3. 分割判斷：
* 若 A[i-1] <= B[j] && B[j-1] <= A[i] → 成功找到分割。
* 否則調整 i。
4. 計算中位數：
* 奇數長度 → 取左半最大值。
* 偶數長度 → 取 (左半最大值 + 右半最小值) / 2。
5. 邊界處理：
* i=0 或 i=n → 用 ±∞ 保證條件可成立。
* m=0 或 n=0 → 直接等於另一數列中位數。
*/