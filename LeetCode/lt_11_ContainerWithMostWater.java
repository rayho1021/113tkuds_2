/*
題目：Container With Most Water
給定一個整數陣列 height，代表 n 條垂直線的高度。
找出兩條線與 x 軸形成的容器能裝最多水的容量。
容器不能傾斜，水的容量 = min(height[i], height[j]) * (j - i)
範例：height = [1,8,6,2,5,4,8,3,7] → Output: 49
*/

class Solution {
    public int maxArea(int[] height) {
        int left = 0; // 左指標，從陣列開頭開始
        int right = height.length - 1; // 右指標，從陣列結尾開始
        int maxWater = 0; // 記錄最大水容量
        
        // 使用雙指標從兩端向中間移動，計算當前容器的水容量：寬度 × 較短的高度
        while (left < right) {
            int width = right - left; 
            int minHeight = Math.min(height[left], height[right]); // 水位高度由較短的線決定
            int currentWater = width * minHeight; // 當前容器容量
            
            maxWater = Math.max(maxWater, currentWater); // 更新最大容量
            
            if (height[left] < height[right]) {
                left++; // 左邊較矮，移動左指標尋找更高的線
            } else {
                right--; // 右邊較矮或相等，移動右指標尋找更高的線
            }
        }
        
        return maxWater; // 回傳最大水容量
    }
}

/*
解題思路：
1. 使用雙指標技巧，從陣列兩端開始向中間收縮，尋找最大容量。
2. 容量計算公式：容量 = min(height[left], height[right]) × (right - left)。
3. 關鍵洞察：總是移動較矮的指標，因為移動較高的指標不會增加容量。
4. 移動策略：
  - 如果移動較高的指標，寬度減少，高度受限於另一邊，容量必然減少
  - 如果移動較矮的指標，雖然寬度減少，但可能找到更高的線，增加容量
5. 演算法保證不會遺漏最佳解，因為任何被跳過的組合都不會比當前更優。
6. 證明正確性：假設最優解在 (i, j)，則在到達該點前，演算法不會錯過它。
7. 時間複雜度 O(n)，空間複雜度 O(1)，比暴力法的 O(n²) 時間複雜度更優秀。
8. 這是一個典型的貪心演算法，每步都做出局部最優選擇。
*/