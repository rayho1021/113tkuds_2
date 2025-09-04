/*
題目：Merge Two Sorted Lists
合併兩個已排序的鏈結串列成一個排序串列。應該通過拼接兩個串列的節點來完成合併。
範例：list1 = [1,2,4], list2 = [1,3,4] → [1,1,2,3,4,4]
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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 建立虛擬頭節點、結果串列
        ListNode dummy = new ListNode(0); 
        ListNode current = dummy; 
        
        // 當兩個串列都不為空時，比較節點值並選擇較小的
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1; // 選擇 list1 的節點，移動 list1 指標
                list1 = list1.next; 
            } else {
                current.next = list2; // 選擇 list2 的節點，移動 list2 指標
                list2 = list2.next; 
            }
            current = current.next; // 移動結果串列的指標
        }
        
        // 將剩餘的節點直接連接到結果串列（原串列已排序）
        if (list1 != null) {
            current.next = list1; // 連接 list1 、 list2 的剩餘部分
        } else {
            current.next = list2; 
        }
        return dummy.next; // 回傳真正的頭節點
    }
}

/*
解題思路：
1. 使用雙指標技巧，分別追蹤兩個輸入串列的當前位置。
2. 虛擬頭節點，方便結果串列的構建和返回。
3. 每次比較兩個串列當前節點的值，選擇較小的節點加入結果串列。
4. 選中的串列指標前進，結果串列指標也前進到新加入的節點。
5. 當一個串列遍歷完成後，另一個串列的剩餘部分可以直接連接。
6. 由於輸入串列已排序，剩餘部分的所有節點都比已處理的節點大。
7. 直接使用原串列的節點，不需要創建新節點，節省空間。
*/