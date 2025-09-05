/*
題目: 多院區即時叫號合併
1.同時接收 k 個科別/院區的已排序候診名單，需要快速整合成一條升序序列（依叫號優先權或時間），效率需優於兩兩合併的 O(kN)。
2.解法核心：使用最小堆 PriorityQueue ，每次彈出最小值。
3.邊界 :
* k=0 → 輸出空
* 所有串列為空
* 僅一條串列（退化）
* 值高度交錯（測堆穩定性）
4.限制：
* 0 <= k <= 1e4
* 總節點數 N <= 2e5
* 每行以 -1 結束，可能出現空串列（直接 -1）
5.複雜度：時間 O(N log k)，空間 O(k)。
6.
輸入:
- 第一行: k
- 第二行: k 行升序序列（-1 結尾）
輸出: 合併後序列
*/

import java.util.*;

public class LC23_MergeKLists_Hospitals {
    static class Item {
        int val;
        int listIdx;
        int pos; // position inside that list
        Item(int val, int listIdx, int pos) {
            this.val = val;
            this.listIdx = listIdx;
            this.pos = pos;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {  // 無輸入
            sc.close(); 
            return;
        }
        int k = sc.nextInt();
        List<List<Integer>> lists = new ArrayList<>(Math.max(0, k));

        // 逐行讀取 k 個已排序序列，直到看到 -1 為止（每行以 -1 結尾）
        for (int i = 0; i < k; i++) {
            List<Integer> cur = new ArrayList<>();
            while (true) {
                // 假設輸入格式正確，每行最終會有 -1
                if (!sc.hasNextInt()) break;
                int v = sc.nextInt();
                if (v == -1) break;
                cur.add(v);
            }
            lists.add(cur);
        }
        sc.close();

        // 最小堆：依照 val 遞增
        PriorityQueue<Item> pq = new PriorityQueue<>(new Comparator<Item>() {
            public int compare(Item a, Item b) {
                return Integer.compare(a.val, b.val);
            }
        });

        // 將每個非空串列的第一個數放入堆中
        for (int i = 0; i < lists.size(); i++) {
            List<Integer> lst = lists.get(i);
            if (!lst.isEmpty()) {
                pq.offer(new Item(lst.get(0), i, 0));
            }
        }

        // 逐個彈出最小並推進對應串列
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        while (!pq.isEmpty()) {
            Item it = pq.poll();
            if (!first) sb.append(' ');
            sb.append(it.val);
            first = false;

            int nextPos = it.pos + 1;
            List<Integer> source = lists.get(it.listIdx);
            if (nextPos < source.size()) {
                pq.offer(new Item(source.get(nextPos), it.listIdx, nextPos));
            }
        }

        // 若沒有任何元素，輸出空行
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        } 
        else {
            System.out.println();
        }
    }
}


/*
解題思路 : 
1.把每個非空串列的頭元素放入一個最小堆（PriorityQueue）。
2.合併：每次從堆中取出"最小值"，將該值加入輸出；然後把取出元素所在串列的下一個元素（若存在）推入堆。
3.終止條件：堆空代表所有節點已被輸出。
4.堆大小最多為 k，每次取出並插入的成本為 O(log k)，總操作次數為 N，因此總時複 O(N log k)。空間僅需保存 k 個堆元素與原始序列（或若採 streaming 可降低記憶體）。
*/