/*
題目：Remove Nth Node From End of List
給定鏈結串列的頭節點，移除倒數第 n 個節點並回傳頭節點。
範例：head = [1,2,3,4,5], n = 2 → [1,2,3,5] (移除倒數第 2 個節點 4)
範例：head = [1], n = 1 → [] (移除唯一節點)
*/

/**
* Definition for singly-linked list.
* public class ListNode {
*     int val;
*     ListNode next;
*     ListNode() {}
*     ListNode(int val) { this.val = val; }
*     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
* }
*/

class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0); // 建立虛擬頭節點
        dummy.next = head;
        ListNode slow = dummy; // 快慢指標，初始指向虛擬頭節點
        ListNode fast = dummy; 
        
        // 讓快指標先前進 n+1 步，與慢指標保持 n+1 的距離
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // 同時移動快慢指標，直到快指標到達末尾
        while (fast != null) {
            slow = slow.next; // 慢指標向前移動一步
            fast = fast.next; // 快指標向前移動一步
        }
        
        // 慢指標正指向待刪除節點的前一個節點，移除倒數第 n 個節點
        slow.next = slow.next.next; 
        return dummy.next; // 回傳真正的頭節點（跳過虛擬頭節點）
    }
}

/*
解題思路：
1. 使用雙指標技巧（快慢指標）實現一次遍歷解決問題。
2. 讓兩個指標保持固定距離，當前指標到達末尾時，後指標正好在目標位置。
3. 虛擬頭節點簡化邊界處理，特別是當需要刪除頭節點時的情況。
4. 快指標先前進 n+1 步，確保慢指標最終指向待刪除節點的前一個節點。
5. 快慢指標同時向前移動，保持固定距離直到快指標到達 null。
6. 透過 slow.next = slow.next.next 跳過待刪除的節點。
*/