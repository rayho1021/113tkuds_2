/*
題目：Merge k Sorted Lists
合併 k 個已排序的鏈結串列成一個排序串列。
範例：lists = [[1,4,5],[1,3,4],[2,6]] → [1,1,2,3,4,4,5,6]
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
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null; // 空輸入
        
        // 使用分治法，不斷合併相鄰的串列對，直到只剩一個串列
        while (lists.length > 1) {
            List<ListNode> mergedLists = new ArrayList<>(); // 儲存每輪合併結果
            
            // 兩兩配對進行合併
            for (int i = 0; i < lists.length; i += 2) {
                ListNode l1 = lists[i]; // 第一個串列
                ListNode l2 = (i + 1 < lists.length) ? lists[i + 1] : null; // 第二個串列（可能為空）
                mergedLists.add(mergeTwoLists(l1, l2)); // 合併兩個串列並加入結果
            }
            
            lists = mergedLists.toArray(new ListNode[0]); // 更新串列陣列為合併結果
        }
        return lists[0]; // 回傳最終合併串列
    }
    
    // 合併兩個已排序串列的輔助方法
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0); // 虛擬頭節點、結果串列
        ListNode current = dummy; 
        
        // 比較兩個串列的節點值，選擇較小的加入結果串列
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1; // 選擇 l1 的節點，移動 l1 指標
                l1 = l1.next; 
            } else {
                current.next = l2; // 選擇 l2 的節點，移動 l2 指標
                l2 = l2.next; 
            }
            current = current.next; // 移動結果串列指標
        }
        
        current.next = (l1 != null) ? l1 : l2; // 連接剩餘的節點
        return dummy.next; // 回傳真正的頭節點
    }
}

/*
解題思路：
1. 使用分治法 (Divide and Conquer) 將問題分解為更小的子問題。
2. 重複將串列兩兩配對合併，直到只剩下一個串列。
3. 合併過程：
  - 第一輪：k 個串列 → k/2 個串列
  - 第二輪：k/2 個串列 → k/4 個串列
  - 依此類推，直到剩餘 1 個串列
4. 兩兩合併，重用 "Merge Two Sorted Lists" 的邏輯來合併每對串列。
5. 當串列數量為奇數時，最後一個串列直接加入下一輪。
6. 比逐一合併更高效，避免重複比較已排序的長串列。
*/