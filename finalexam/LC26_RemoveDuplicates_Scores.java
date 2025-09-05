/*
題目: 去重學生成績單
1.已排序的學生成績單（依學號），需移除重複學號，就地壓縮，並回傳壓縮後長度與內容前段。
2.解法核心：使用雙指針，一個讀（i），一個寫（write）。若當前數與最後保留值不同，寫入並移動 write。
3.限制：
* 0 <= n <= 1e5
* 陣列已排序（非遞減）
4.邊界:
- n=0 → 長度 0
- 全部唯一
- 全部相同
- 交替重複：1,1,2,2,3,3
5.複雜度：時間 O(n)，空間 O(1)。
6.
輸入：
- 第一行: 整數 n（元素數量）
- 第二行: n 個已排序整數（學號）
輸出：
- 壓縮後的新長度
- 新陣列前段的唯一值（以空格分隔）
*/

import java.util.*;

public class LC26_RemoveDuplicates_Scores {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            sc.close();
            return;
        }
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        sc.close();

        // 若 n=0，直接輸出
        if (n == 0) {
            System.out.println(0);
            return;
        }

        int write = 1; // 從第二個元素開始比較 (i=1)
        for (int i = 1; i < n; i++) {
            if (arr[i] != arr[write - 1]) {
                arr[write] = arr[i];
                write++;
            }
        }

        System.out.println(write);
        for (int i = 0; i < write; i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}


/*
解題思路 : 
1指標 write=1，用來標示下一個可以覆寫的位置。
2.遍歷陣列：從第二個元素開始，若與前一個保留值不同，則寫入 arr[write]，並移動 write。
3.結束：write 即為新長度，前 write 個數字即為去重後的結果。
4.因為輸入已排序，相同值必定相鄰，檢查前一保留值即可去重。
*/