/*
題目: 護理紀錄刪除倒數第 N 筆
1.給定一個單向鏈結串列（按時間遞增），刪除倒數第 k 筆，一趟掃描內刪掉該節點並維持其餘順序
2.條件：
* 1 <= n <= 1e5
* 1 <= k <= n
3.邊界:
- n=1, k=1 → 刪除後空
- k=n → 刪除頭節點
- k=1 → 刪除尾節點
- 連續相同值（不影響邏輯，但測指標處理）
4.解法核心：雙指標同步移動，一次遍歷。
5.複雜度：時間 O(n)、空間 O(1)
6.
輸入：
* 一個整數 n（節點數）
* 一行 n 個整數（鏈結節點值）
* 一個整數 k（欲刪除的倒數位置）
輸出：刪除後序列
*/

import java.util.*;

public class LC19_RemoveNth_Node_Clinic {
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(); // 節點數
        ListNode dummy = new ListNode(0); // Dummy 節點，便於刪頭操作
        ListNode curr = dummy;

        // 建立鏈結串列
        for (int i = 0; i < n; i++) {
            curr.next = new ListNode(sc.nextInt());
            curr = curr.next;
        }
        int k = sc.nextInt(); // 倒數第 k 個

        // 刪除倒數第 k 個節點
        ListNode head = removeNthFromEnd(dummy.next, k);

        // 輸出刪除後序列
        curr = head;
        boolean first = true;
        while (curr != null) {
            if (!first) System.out.print(" ");
            System.out.print(curr.val);
            first = false;
            curr = curr.next;
        }
        System.out.println();
        sc.close();
    }

    // 刪除倒數第 n 個節點
    private static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode fast = dummy, slow = dummy;

        // fast 先走 n+1 步，讓 slow 指到刪除節點前一個
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        // fast 與 slow 同步前進
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next; // 刪除 slow 後方的節點
    
        return dummy.next;
    }
}

/*
解題思路 : 
1.在 head 前加一個 dummy 節點，能統一處理「刪除頭節點」與「刪除中間/尾節點」的情況。
2.快慢雙指標：
- 先讓 fast 前進 k+1 步（含 dummy）。
- 接著 fast 與 slow 同步移動。
- 當 fast 到尾時，slow 正位於刪除目標的前一個位置。
3.透過 slow.next = slow.next.next 即可完成刪除。
4.從新 head 開始遍歷並輸出所有值。
5.邊界處理：
- n=1, k=1 → 移除唯一節點，輸出空行。
- k=n → 移除頭節點。
- k=1 → 移除尾節點。
*/