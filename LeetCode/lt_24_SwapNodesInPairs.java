/*
題目：Swap Nodes in Pairs
給定一個鏈結串列，交換每兩個相鄰的節點並回傳頭節點。
必須透過改變節點連接而非修改節點值來解決問題。
範例：head = [1,2,3,4] → [2,1,4,3]
範例：head = [1,2,3] → [2,1,3]
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
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0); // 虛擬頭節點
        dummy.next = head;
        ListNode prev = dummy; // prev 指向待交換對的前一個節點
        
        // 當還有至少兩個節點可以交換時繼續
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next; // 第一個節點
            ListNode second = prev.next.next; // 第二個節點
            
            // 交換
            prev.next = second; // prev 指向第二個節點
            first.next = second.next; // 第一個節點指向第二個節點的下一個
            second.next = first; // 第二個節點指向第一個
            prev = first; // 移動 prev 指標到下一對的前一個位置（即剛交換的第一個節點）
        }       
        return dummy.next; // 回傳真正的頭節點
    }
}

/*
解題思路：
1. 使用虛擬頭節點，來避免處理頭節點交換的情況。
2. 維護 prev 指標指向待交換節點對的前一個節點，便於重新連接。
3. 交換過程：
  - prev.next = second：將前一個節點指向第二個節點
  - first.next = second.next：第一個節點指向第二個節點的後繼
  - second.next = first：第二個節點指向第一個節點
4. 交換完成後，將 prev 移動到新的位置（交換後的第一個節點）。
5. 終止：當剩餘節點少於兩個時停止交換。
6. 空串列、單節點串列或奇數節點串列的最後一個節點保持不變。
*/