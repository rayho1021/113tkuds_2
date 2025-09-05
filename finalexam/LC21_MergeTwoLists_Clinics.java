/*
題目: 合併兩院區掛號清單
1."合併"兩院區已排序的單向鏈結串列(門診掛號清單)，為新的單一升序串列
2.解題核心：雙指針合併，Dummy + tail。
3.邊界：
- 其中一條為空 → 直接返回另一條
- 全部元素相等 → 正確處理重複值
- 長度差極大 → 效率不受影響
- 單一元素 vs 多元素
4.複雜度：時間 O(n+m)、空間 O(1)
5.
輸入：
- 第一行: 兩院區掛號數量 n + m
- n 個升序整數 (掛號序列)
- m 個升序整數 (掛號序列)
- 限制: 0 ≤ n,m ≤ 5×10^4，n+m ≥ 1，-10^9 ≤ 值 ≤ 10^9
輸出：合併後序列 (升序)
*/

import java.util.*;

public class LC21_MergeTwoLists_Clinics {
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 使用 dummy node 簡化邊界處理
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy; // tail 指向目前合併串列的尾端
        
        // 雙指針同時遍歷兩個串列
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                // list1 當前節點較小或相等，接到結果串列
                tail.next = list1;
                list1 = list1.next; // 移動 list1 
            } 
            else {
                tail.next = list2; // list2 當前節點較小，接到結果串列
                list2 = list2.next; // 移動 list2 
            }
            tail = tail.next; // 移動 tail
        }
        
        // 將剩餘未處理的串列直接接到尾端
        if (list1 != null) {
            tail.next = list1; // list1 還有剩餘節點
        } 
        else {
            tail.next = list2; // list2 還有剩餘節點（或兩者皆空）
        }
        return dummy.next; // 返回合併結果，跳過 dummy node
    }
    
    // 根據陣列建立鏈結串列
    private ListNode createLinkedList(int[] arr) {
        if (arr.length == 0) {
            return null;
        }
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }
    
    // 將鏈結串列轉換為陣列輸出
    private List<Integer> linkedListToList(ListNode head) {
        List<Integer> result = new ArrayList<>();
        ListNode current = head;
        
        while (current != null) {
            result.add(current.val);
            current = current.next;
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC21_MergeTwoLists_Clinics solution = new LC21_MergeTwoLists_Clinics();
        int n = scanner.nextInt(); // 兩院區掛號數量
        int m = scanner.nextInt();
        
        int[] arr1 = new int[n]; // 第一院區掛號序列
        for (int i = 0; i < n; i++) {
            arr1[i] = scanner.nextInt();
        }

        int[] arr2 = new int[m]; // 第二院區掛號序列
        for (int i = 0; i < m; i++) {
            arr2[i] = scanner.nextInt();
        }
        
        // 建立兩個鏈結串列
        ListNode list1 = solution.createLinkedList(arr1);
        ListNode list2 = solution.createLinkedList(arr2);
        
        // 合併兩串列
        ListNode mergedList = solution.mergeTwoLists(list1, list2);
        
        List<Integer> result = solution.linkedListToList(mergedList);
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print( result.get(i));
        }
        System.out.println();
        scanner.close();
    }
}

/*
解題思路 : 
1.使用虛擬頭節點 dummy 避免複雜的邊界判斷，簡化合併邏輯。
2.雙指針遍歷：同時儲存 list1 和 list2 的當前位置，每次選擇較小值加入結果串列。
3.就地合併，不創建新節點，而是重新連接現有節點的 next 指針，節省空間。
4.
- 若 list1.val ≤ list2.val：選擇 list1 當前節點，移動 list1 指針
- 否則選擇 list2 當前節點，移動 list2 指針
5.剩餘處理：當其中一個串列遍歷完成，直接將另一個串列的剩餘部分接到結果尾端。
6.tail：始終指向合併串列的最後一個節點，確保新節點正確接續。
*/