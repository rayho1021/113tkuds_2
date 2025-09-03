/*
題目：Add Two Numbers
給定兩個非空的鏈結串列，代表兩個非負整數。數字以反向順序儲存，每個節點包含一個數字。將兩個數字相加並以鏈結串列形式回傳結果。
可假設兩個數字不包含前導零，除了數字 0 本身。
範例：l1 = [2,4,3], l2 = [5,6,4] → Output: [7,0,8] (342 + 465 = 807)
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
   public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
       ListNode dummy = new ListNode(0); // 虛擬頭節點
       ListNode current = dummy; // 用於構建結果鏈結串列的指標
       int carry = 0; 
       
       // 當任一鏈結串列還有節點或還有進位時繼續處理
       while (l1 != null || l2 != null || carry != 0) {
           int sum = carry; // 從進位值開始計算總和
           
           if (l1 != null) {
               sum += l1.val; // 加上 l1 當前節點的值
               l1 = l1.next;  // 移動 l1 指標到下一個節點
           }
           
           if (l2 != null) {
               sum += l2.val; // 加上 l2 當前節點的值
               l2 = l2.next;  // 移動 l2 指標到下一個節點
           }
           
           carry = sum / 10; // 計算新的進位值（十位數）
           current.next = new ListNode(sum % 10); // 建立新節點存儲個位數
           current = current.next; // 移動結果串列的指標
       }
       
       return dummy.next; // 回傳結果串列（跳過虛擬頭節點）
   }
}

/*
解題思路：
1. 使用虛擬頭節點 dummy 來簡化結果鏈結串列的建立，避免特殊處理第一個節點。
2. 同時遍歷兩個鏈結串列，將對應位置的數字相加，並處理進位。
3. 每次迭代計算當前位的總和（包含進位），然後分別計算進位值和當前位的值。
4. 建立新節點儲存當前位的結果，並移動指標繼續處理下一位。
5. 當兩個串列都遍歷完成且沒有進位時結束迴圈。
*/