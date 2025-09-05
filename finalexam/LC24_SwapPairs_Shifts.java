/*
題目: 班表兩兩交換
1.班表資料鏈結串列中，將相鄰兩兩節點(時段)"成對交換"位置（1↔2, 3↔4 …），模擬輪班互換情況。
2.解題核心: Dummy + while (a!=null && a.next!=null)
3.邊界：
- 空串列或單一節點 → 保持不變
- 偶數長度 → 全部成對交換
- 奇數長度 → 最後一個節點保留原位
- 兩個節點 → 直接交換
4.複雜度：時間 O(n)，空間 O(1)
5.
輸入：一行整數 (0 ≤ 節點數 ≤ 10^5，值範圍不限)
輸出：交換後整數序列
*/

import java.util.*;

public class LC24_SwapPairs_Shifts {
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
    
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(0); // 使用 dummy node 簡化邊界處理
        dummy.next = head;
        ListNode prev = dummy; // prev 指向當前待交換的"前"一個節點
        
        // 確保存在完整的一對節點可供交換
        while (prev.next != null && prev.next.next != null) {
            ListNode first = prev.next;      // 第一個節點
            ListNode second = prev.next.next; // 第二個節點
            
            // 執行交換
            prev.next = second;          // prev --> second
            first.next = second.next;    // first --> second 的下一個
            second.next = first;         // second --> first
            prev = first;                // 移動 prev 到下一對的前一個節點
        }
        return dummy.next; // 返回新的頭節點（跳過 dummy）
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
        LC24_SwapPairs_Shifts solution = new LC24_SwapPairs_Shifts();
        String line = scanner.nextLine().trim(); // 讀取輸入並解析為陣列
        
        if (line.isEmpty()) {  // 空輸入情況
            System.out.println();
            scanner.close();
            return;
        }
        
        String[] tokens = line.split("\\s+");
        int[] arr = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }
        
        ListNode head = solution.createLinkedList(arr); // 建立鏈結串列
        // 執行兩兩交換
        ListNode swappedHead = solution.swapPairs(head);
        
        List<Integer> result = solution.linkedListToList(swappedHead);
        for (int i = 0; i < result.size(); i++) {
            if (i > 0) System.out.print(" ");
            System.out.print(result.get(i));
        }
        if (!result.isEmpty()) {
            System.out.println();
        }
        scanner.close();
    }
}

/*
解題思路 :
1.建立虛擬頭節點 Dummy Node 避免真實頭節點的交換。
2.三指針交換：
- prev：指向待交換對的"前"一個節點
- first：待交換對的"第一個"節點
- second：待交換對的"第二個"節點
3.交換操作:
- 交換前：prev -> first -> second -> next
- 交換後：prev -> second -> first -> next
4.迴圈條件：確保存在完整的一對節點 prev.next != null && prev.next.next != null
5.指針推進：每次交換後，將 prev 移動到當前交換後的 first 位置，準備下一輪交換。
*/