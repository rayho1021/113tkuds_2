/*
題目：Reverse Nodes in k-Group
給定鏈結串列的頭節點，每 k 個節點為一組進行反轉，回傳修改後的串列。
如果節點數量不是 k 的倍數，剩餘節點保持原順序。只能改變節點連接，不能修改節點值。
範例：head = [1,2,3,4,5], k = 3 → [3,2,1,4,5]
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
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head; // 邊界情況
        
        ListNode dummy = new ListNode(0); // 虛擬頭節點
        dummy.next = head;
        ListNode prev = dummy; // prev --> 每組的前一個節點
        
        while (true) {
            ListNode kthNode = getKthNode(prev, k); // 是否還有 k 個節點可以反轉
            if (kthNode == null) break; // 節點不足 k 個，結束
            
            ListNode nextGroupHead = kthNode.next; // 保存下一組的起始節點
            // 反轉當前組的 k 個節點
            ListNode groupPrev = nextGroupHead; // 反轉後的組內連接起點
            ListNode current = prev.next; // 當前組的第一個節點
            
            // 組內反轉
            while (current != nextGroupHead) {
                ListNode next = current.next; // 暫存下一個節點
                current.next = groupPrev; // 反轉當前節點的指標
                groupPrev = current; // 移動 groupPrev、current
                current = next;
            }
            
            // 重新連接
            ListNode groupTail = prev.next; // 反轉後組的尾節點（原來的第一個節點）
            prev.next = kthNode; // 前一組連接到反轉後組的頭節點
            prev = groupTail; // 更新 prev 為當前組的尾節點
        }

        return dummy.next; // 回傳真正的頭節點
    }
    
    // 輔助方法：從 start 節點開始找第 k 個節點
    private ListNode getKthNode(ListNode start, int k) {
        while (start != null && k > 0) {
            start = start.next;
            k--;
        }
        return start; // 如果 k > 0 則回傳 null，表節點不足
    }
}

/*
解題思路：
1. 分組反轉，每次處理 k 個節點為一組。
2. 邏輯:
  - 檢查剩餘節點是否足夠 k 個
  - 反轉當前組內的 k 個節點
  - 重新連接反轉後的組與前後部分
3. 節點反轉，在每組內部使用串列反轉。
4. 連接：
  - prev.next 指向反轉後組的頭節點（原來的第 k 個節點）
  - 反轉後組的尾節點（原來的第 1 個節點）連接到下一組
5. 剩餘節點不足 k 個時，保持原有順序不變。
6. 虛擬頭節點：方便處理當第一組需要反轉時。
*/