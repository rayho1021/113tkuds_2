/*
題目: 採購限額 4Sum
1.找出所有"四項物資（價格可重複出現）的搭配組合"，使總價恰為預算 target，需去重且按升序輸出。
2.解題核心：雙層枚舉 + 雙指針夾逼 + 多層去重
3.邊界：
- =4 → 只有唯一組合可能
- 全為0且target=0 → 只輸出 "0 0 0 0"
- 無符合組合 → 無輸出
- 大量重複值 → 測試去重正確性
4.複雜度：時間 O(n³)，空間 O(1) 不計輸出
5.
輸入：
第一行: 物資數量 n + 預算目標 target
第二行: n 個價格 (4 ≤ n ≤ 200，-10^9 ≤ 值,target ≤ 10^9)
輸出：多行四元組，升序排列
*/

import java.util.*;

public class LC18_4Sum_Procurement {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return result;
        }
        Arrays.sort(nums); // 排序陣列，便於雙指針查找和去重
        int n = nums.length;
        
        // 固定前兩個元素 i, j
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 跳過第一層重複元素
            }
            
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue; // 跳過第二層重複元素
                }
                
                // 計算剩餘兩數需要的目標和
                long remainingTarget = (long) target - nums[i] - nums[j];
                
                // 使用雙指針在 [j+1, n-1] 範圍內查找
                int left = j + 1;
                int right = n - 1;
                
                while (left < right) {
                    long currentSum = (long) nums[left] + nums[right];
                    
                    if (currentSum == remainingTarget) {
                        // 找到符合的四元組
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++; // 跳過左指針的重複元素
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--; // 跳過右指針的重複元素
                        }
                        
                        // 移動到下一個不同的元素
                        left++;
                        right--;
                        
                    } 
                    else if (currentSum < remainingTarget) {
                        left++; // 和太小，移動左指針
                    } 
                    else { 
                        right--;  // 和太大，移動右指針
                    }
                }
            }
        } 
        return result;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LC18_4Sum_Procurement solution = new LC18_4Sum_Procurement();
        int n = scanner.nextInt(); //物資數量和預算目標
        int target = scanner.nextInt();
        int[] nums = new int[n]; // 價格陣列
        for (int i = 0; i < n; i++) {
            nums[i] = scanner.nextInt();
        }
        
        // 找出所有四元組
        List<List<Integer>> result = solution.fourSum(nums, target);
        
        for (List<Integer> quadruplet : result) {
            System.out.println(quadruplet.get(0) + " " + quadruplet.get(1) + " " + 
                             quadruplet.get(2) + " " + quadruplet.get(3));
        }
        scanner.close();
    }
}

/*
解題思路 : 
1.排序預處理：對價格陣列排序，為後續雙指針操作和去重提供基礎。
2.雙層固定策略：外層固定 nums[i]，內層固定 nums[j]，將4Sum問題轉化為在剩餘範圍內的2Sum問題。
3.雙指針夾逼：在 [j+1, n-1] 範圍內使用雙指針查找兩數和等於 remainingTarget：
- currentSum < target：左指針右移增大和值
- currentSum > target：右指針左移減小和值
- currentSum == target：找到答案並跳過重複
4.多層去重：
- 第一層：跳過重複的 nums[i] (i > 0 且 nums[i] == nums[i-1])
- 第二層：跳過重複的 nums[j] (j > i+1 且 nums[j] == nums[j-1])
- 第三層：找到答案後同時跳過重複的 nums[left] 和 nums[right]
5.使用 long 型別進行計算，避免大數相加時的整數溢出問題。
*/