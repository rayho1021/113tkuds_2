/*
題目: 高鐵站點三元組 3Sum
1.
- 票務調整量 (正=增加座位配額，負=調出)
- 找出所有三站調整量總和為 0 的組合，表示內部平衡不影響總供給。
- 給一整數陣列，輸出所有不重複且升序排列的三元組，其和為 0。
2.解題核心：排序 + 固定一點 i + 雙指針查找 -nums[i] + 去重
3.邊界：
- 少於 3 個元素 → 無輸出（此處 n>=3 已避免）
- 全為0 → 只輸出 "0 0 0"
- 無符合組合 → 無輸出
- 多重重複 → 需正確去重
4.複雜度：時間 O(n²)，空間 O(1) 不計輸出
5.
輸入：
第一行: 站點數量 n
第二行: n 個調整量 (3 ≤ n ≤ 3000，-10^5 ≤ nums[i] ≤ 10^5)
輸出：多行三元組（遞增）
*/

import java.util.*;

public class LC15_3Sum_THSRStops {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        Arrays.sort(nums);  // 排序陣列，便於雙指針查找和去重
        
        // 固定第一個元素 i，用雙指針找剩餘兩個
        for (int i = 0; i < nums.length - 2; i++) {

            // 若當前值 > 0，後續皆為正數，無法湊成 0
            if (nums[i] > 0) {
                break;
            }
            // 跳過重複的第一個元素
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1; // 左指針從 i+1 開始
            int right = nums.length - 1; // 右指針從末端開始
            int target = -nums[i]; // 目標：找兩數和等於 -nums[i]
            
            // left 夾逼查找
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    // 找到符合的三元組
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    while (left < right && nums[left] == nums[left + 1]) {
                        left++; // 跳過 left 的重複元素
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--; // 跳過 right 的重複元素 
                    }
                    // 移動到下一個不同的元素
                    left++;
                    right--;
                } 
                else if (sum < target) {
                    left++;  // 和太小，需要較大的數，移動左指針
                } 
                else {
                    right--; // 和太大，需要較小的數，移動右指針
                }
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC15_3Sum_THSRStops solution = new LC15_3Sum_THSRStops();
        int n = scanner.nextInt();   // 站點數量
        int[] nums = new int[n]; // 調整量陣列

        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        // 找出所有三元組
        List<List<Integer>> result = solution.threeSum(nums);
        
        for (List<Integer> triplet : result) {
            System.out.println(triplet.get(0) + " " + triplet.get(1) + " " + triplet.get(2));
        }
        scanner.close();
    }
}

/*
解題思路 : 
1.對陣列排序，使後續雙指針查找和去重操作成為可能。
2.外層迴圈固定 nums[i] 作為三元組的第一個數，轉化為在剩餘元素中找兩數和等於 -nums[i]。
3.雙指針夾逼：在 [i+1, n-1] 範圍內使用雙指針：
- sum < target：左指針右移增大和值
- sum > target：右指針左移減小和值
- sum == target：找到答案，記錄並跳過重複
4.多層去重：
- 第一層：跳過重複的 nums[i]
- 第二層：找到答案後跳過重複的 nums[left] 和 nums[right]
5.提前終止：當 nums[i] > 0 時，後續元素皆為正數，無法組成和為0的三元組。
*/