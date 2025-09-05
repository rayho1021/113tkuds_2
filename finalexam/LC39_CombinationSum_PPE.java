/*
題目: 防災物資組合總和 - I 版
1. 
- 找出所有符合 target 的物資價格組合。
- I 版 (Combination Sum I)：每個元素可無限次使用。
- II 版 (Combination Sum II)：每個元素僅能使用一次，且需去除重複組合。
2.解題核心: 回溯 + 剩餘值剪枝；II 版排序 + 同層跳重複。
3.限制：
* 1 <= n <= 30
* 1 <= 候選值 <= 200
* 1 <= target <= 500
4.邊界：
* 無解（所有值 > target）
* 單一值等於 target
* I 版需多次使用同一數字
* II 版有重複數需去重
5.
輸入：
- n target
- n 個整數（候選價格，可能重複）
輸出：每行一組升序組合
*/

import java.util.*;

public class LC39_CombinationSum_PPE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), target = sc.nextInt();
        int[] candidates = new int[n];
        for (int i = 0; i < n; i++) candidates[i] = sc.nextInt();
        sc.close();

        Arrays.sort(candidates); // 排序便於升序與剪枝
        List<List<Integer>> res = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), res);

        // 輸出所有組合
        for (List<Integer> comb : res) {
            for (int i = 0; i < comb.size(); i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(comb.get(i));
            }
            System.out.println();
        }
    }

    // 回溯 (I 版: 可重複使用元素)
    private static void backtrack(int[] candidates, int remain, int start,
                                  List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > remain) break; // 剪枝
            path.add(candidates[i]);
            backtrack(candidates, remain - candidates[i], i, path, res); // 可重複使用 → 傳 i
            path.remove(path.size() - 1);
        }
    }
}


/*
解題思路 : 
1.排序：讓組合能保持升序，也方便剪枝與去重。
2.回溯：
- 若 remain == 0 → 收錄當前組合。
- 若 remain < 0 → 結束當前分支。
3.I 版：允許元素重複 → 遞迴時仍傳入 i。
4.II 版：每元素只用一次 → 遞迴傳 i+1，並在同層檢查 if (i > start && nums[i] == nums[i-1]) continue; 去重。
*/
