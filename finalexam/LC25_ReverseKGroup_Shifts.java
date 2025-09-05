/*
題目: 班表 k 組反轉
1.問題定義：班表調整中，將排班以 k 人一組進行區段反轉（例如輪替順序），不足 k 的尾端保持原樣。
2.解題核心：分組檢查 + (迭代收集 k) 局部反轉 + 鏈結串列
3.邊界：
- k=1 → 保持不變
- 長度 < k → 不進行反轉
- 長度 = k → 整段反轉
- 長度 = m×k + r (r<k) → 前m組反轉，尾段保持
4.複雜度：時間 O(n)，空間 O(1) 不計輸出
5.
輸入：
- 第一行: k (分組大小)
- 第二行: 序列 (0 ≤ 長度 ≤ 10^5，1 ≤ k ≤ 10^5)
輸出：反轉後序列
*/

import java.util.*;

public class LC25_ReverseKGroup_Shifts {
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
    
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) {
            return head;
        }
        
        ListNode dummy = new ListNode(0); // 使用 dummy node 簡化邊界處理
        dummy.next = head;
        ListNode prevGroupEnd = dummy; // 指向前一組的尾端
        
        // 檢查當前位置開始是否有足夠的 k 節點，不足就結束反轉
        while (true) {
            ListNode kthNode = getKthNode(prevGroupEnd, k);
            if (kthNode == null) {
                break; 
            }
            
            // 記錄下一組的開始位置
            ListNode nextGroupStart = kthNode.next;
            
            // 反轉當前 k 個節點的區段
            // prevGroupEnd.next 當前組的第一個節點；kthNode 當前組的最後一個節點
            ListNode[] reversed = reverseGroup(prevGroupEnd.next, kthNode);
            ListNode newGroupStart = reversed[0]; // 反轉後的新開頭
            ListNode newGroupEnd = reversed[1];   // 反轉後的新結尾
            
            // 連接前一組和反轉後的當前組
            prevGroupEnd.next = newGroupStart;
            newGroupEnd.next = nextGroupStart;
            
            prevGroupEnd = newGroupEnd; // 移動到下一組的前一個位置
        }
        return dummy.next;
    }
    
    // 從給定節點開始找第 k 個節點
    private ListNode getKthNode(ListNode start, int k) {
        ListNode current = start;
        for (int i = 0; i < k && current != null; i++) {
            current = current.next;
        }
        return current; // 若不足 k 個節點則返回 null
    }
    
    // 反轉從 start 到 end 的區段（包含 end）
    private ListNode[] reverseGroup(ListNode start, ListNode end) {
        ListNode prev = end.next; // 反轉後 start 應該指向的節點
        ListNode current = start;
        
        // 反轉
        while (current != end.next) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        // 返回反轉後的新開頭和新結尾
        return new ListNode[]{end, start};
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
    
    // 轉換為陣列輸出
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
        LC25_ReverseKGroup_Shifts solution = new LC25_ReverseKGroup_Shifts();
        int k = scanner.nextInt(); // 讀取 k 分組大小
        scanner.nextLine(); 
        String line = scanner.nextLine().trim(); // 讀取序列
        
        if (line.isEmpty()) {  // 空序列情況
            System.out.println();
            scanner.close();
            return;
        }
        
        String[] tokens = line.split("\\s+");
        int[] arr = new int[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            arr[i] = Integer.parseInt(tokens[i]);
        }
        
        // 建立鏈結串列
        ListNode head = solution.createLinkedList(arr);
        
        // 執行 k 組反轉
        ListNode reversedHead = solution.reverseKGroup(head, k);
        
        List<Integer> result = solution.linkedListToList(reversedHead);
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
1.使用 getKthNode() 方法檢查當前位置是否有足夠的 k 個節點可供反轉。
2.局部反轉：reverseGroup() 方法負責反轉指定區段的鏈結串列：
- 三指針反轉：prev, current, next
- 設定正確的邊界條件
3.連接：
- revGroupEnd：指向前一組的尾端節點
- 反轉完成後正確連接前一組、當前組、下一組
4.迭代：
- 每次處理一個 k 大小的組
- 更新 prevGroupEnd 指向當前組反轉後的尾端
- 繼續處理下一組直到不足 k 個節點
5.邊界處理：
- 使用 dummy node
- 不足 k 個節點的尾段保持原樣
*/