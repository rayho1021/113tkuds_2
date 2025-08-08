/*
練習 2.1 : 遞迴數學計算器
實作以下遞迴數學函式：
1. 計算組合數 C(n, k) = C(n-1, k-1) + C(n-1, k)
2. 計算卡塔蘭數 C(n) = Σ(C(i) × C(n-1-i))，其中 i 從 0 到 n-1
3. 計算漢諾塔移動步數 hanoi(n) = 2 × hanoi(n-1) + 1
4. 判斷一個數字是否為回文數（如 12321）
 */

import java.util.Scanner;

public class RecursiveMathCalculator {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static long combination(int n, int k) {
        // 邊界條件檢查
        if (k < 0 || k > n || n < 0) {
            return 0;
        }
        
        // 基底條件
        if (k == 0 || k == n) {
            return 1;
        }
        
        // 遞迴關係：C(n, k) = C(n-1, k-1) + C(n-1, k)
        return combination(n - 1, k - 1) + combination(n - 1, k);
    }
    
    /**
     * 計算第 n 個卡塔蘭數
     * 遞迴關係：C(n) = Σ(C(i) × C(n-1-i))，其中 i 從 0 到 n-1
     * 基底條件：C(0) = 1, C(1) = 1
     */
    public static long catalanNumber(int n) {
        // 邊界條件
        if (n < 0) {
            return 0;
        }
        
        // 基底條件
        if (n <= 1) {
            return 1;
        }
        
        long result = 0;
        // 遞迴關係：C(n) = Σ(C(i) × C(n-1-i))
        for (int i = 0; i < n; i++) {
            result += catalanNumber(i) * catalanNumber(n - 1 - i);
        }
        
        return result;
    }
    
    /**
     * 計算漢諾塔移動 n 個盤子所需的步數
     * 遞迴關係：hanoi(n) = 2 × hanoi(n-1) + 1
     * 基底條件：hanoi(1) = 1
     */
    public static long hanoiSteps(int n) {
        // 邊界條件
        if (n <= 0) {
            return 0;
        }
        
        // 基底條件
        if (n == 1) {
            return 1;
        }
        
        // 遞迴關係：hanoi(n) = 2 × hanoi(n-1) + 1
        return 2 * hanoiSteps(n - 1) + 1;
    }
    
    /**
     * 判斷一個數字是否為回文數（遞迴實作）
     */
    public static boolean isPalindrome(long num) {
        // 負數不是回文數
        if (num < 0) {
            return false;
        }
        
        // 將數字轉換為字串進行遞迴處理
        String str = String.valueOf(num);
        return isPalindromeHelper(str, 0, str.length() - 1);
    }
    
    /**
     * 回文數判斷的遞迴輔助方法
     */
    private static boolean isPalindromeHelper(String str, int left, int right) {
        // 基底條件：當左邊界大於等於右邊界時，表示已檢查完成
        if (left >= right) {
            return true;
        }
        
        // 如果當前位置的字符不相等，則不是回文
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        
        // 遞迴檢查內部的字符
        return isPalindromeHelper(str, left + 1, right - 1);
    }
    
    /**
     * 顯示主選單
     */
    public static void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         遞迴數學計算器");
        System.out.println("=".repeat(50));
        System.out.println("1. 計算組合數 C(n, k)");
        System.out.println("2. 計算卡塔蘭數 C(n)");
        System.out.println("3. 計算漢諾塔移動步數");
        System.out.println("4. 判斷回文數");
        System.out.println("5. 退出程式");
        System.out.println("=".repeat(50));
        System.out.print("請選擇功能 (1-5): ");
    }
    
    /**
     * 處理組合數計算
     */
    public static void handleCombination() {
        System.out.println("\n--- 組合數計算 C(n, k) ---");
        System.out.println("計算從 n 個物品中選取 k 個的方法數");
        
        try {
            System.out.print("請輸入 n (總數量): ");
            int n = scanner.nextInt();
            System.out.print("請輸入 k (選取數量): ");
            int k = scanner.nextInt();
            
            if (n < 0 || k < 0) {
                System.out.println("錯誤：n 和 k 必須為非負整數");
                return;
            }
            
            if (k > n) {
                System.out.println("錯誤：k 不能大於 n");
                return;
            }
            
            if (n > 30) {
                System.out.println("警告：n 值較大可能導致計算時間過長，建議 n ≤ 30");
            }
            
            long startTime = System.nanoTime();
            long result = combination(n, k);
            long endTime = System.nanoTime();
            
            System.out.printf("結果：C(%d, %d) = %d\n", n, k, result);
            System.out.printf("計算時間：%.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
            
        } catch (Exception e) {
            System.out.println("輸入錯誤：請輸入有效的整數");
            scanner.nextLine(); // 清除緩衝區
        }
    }
    
    /**
     * 處理卡塔蘭數計算
     */
    public static void handleCatalan() {
        System.out.println("\n--- 卡塔蘭數計算 ---");
        System.out.println("計算第 n 個卡塔蘭數（用於括號匹配、二元樹計數等）");
        
        try {
            System.out.print("請輸入 n (索引，建議 n ≤ 15): ");
            int n = scanner.nextInt();
            
            if (n < 0) {
                System.out.println(" 錯誤：n 必須為非負整數");
                return;
            }
            
            if (n > 15) {
                System.out.println("  警告：n 值較大可能導致計算時間過長，建議 n ≤ 15");
            }
            
            long startTime = System.nanoTime();
            long result = catalanNumber(n);
            long endTime = System.nanoTime();
            
            System.out.printf(" 結果：第 %d 個卡塔蘭數 = %d\n", n, result);
            System.out.printf(" 計算時間：%.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
            
            // 顯示前幾個卡塔蘭數作為參考
            if (n <= 10) {
                System.out.print("前 " + Math.min(n + 1, 6) + " 個卡塔蘭數：");
                for (int i = 0; i <= Math.min(n, 5); i++) {
                    System.out.print(catalanNumber(i));
                    if (i < Math.min(n, 5)) System.out.print(", ");
                }
                System.out.println();
            }
            
        } catch (Exception e) {
            System.out.println("輸入錯誤：請輸入有效的整數");
            scanner.nextLine();
        }
    }
    
    /**
     * 處理漢諾塔計算
     */
    public static void handleHanoi() {
        System.out.println("\n--- 漢諾塔移動步數計算 ---");
        System.out.println("計算移動 n 個盤子所需的最少步數");
        
        try {
            System.out.print("請輸入盤子數量 n: ");
            int n = scanner.nextInt();
            
            if (n <= 0) {
                System.out.println("錯誤：盤子數量必須為正整數");
                return;
            }
            
            if (n > 30) {
                System.out.println("警告：盤子數量過大，結果可能溢位");
            }
            
            long startTime = System.nanoTime();
            long result = hanoiSteps(n);
            long endTime = System.nanoTime();
            
            System.out.printf("結果：移動 %d 個盤子需要 %d 步\n", n, result);
            System.out.printf("計算時間：%.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
            
            // 提供一些有趣的時間估算
            if (n <= 20) {
                double seconds = result / 1.0; // 假設每秒移動1個盤子
                System.out.printf("如果每秒移動1個盤子，需要 %.0f 秒\n", seconds);
                if (seconds > 60) {
                    System.out.printf("約等於 %.1f 分鐘\n", seconds / 60);
                }
                if (seconds > 3600) {
                    System.out.printf("約等於 %.1f 小時\n", seconds / 3600);
                }
            }
            
        } catch (Exception e) {
            System.out.println("輸入錯誤：請輸入有效的整數");
            scanner.nextLine();
        }
    }
    
    /**
     * 處理回文數判斷
     */
    public static void handlePalindrome() {
        System.out.println("\n--- 回文數判斷 ---");
        System.out.println("判斷一個數字是否為回文數（如：12321, 1001）");
        
        try {
            System.out.print("請輸入要判斷的數字: ");
            long num = scanner.nextLong();
            
            long startTime = System.nanoTime();
            boolean result = isPalindrome(num);
            long endTime = System.nanoTime();
            
            System.out.printf("結果：%d %s回文數\n", num, result ? "是" : "不是");
            System.out.printf("計算時間：%.3f 毫秒\n", (endTime - startTime) / 1_000_000.0);
            
            // 顯示數字的正序和反序
            String str = String.valueOf(Math.abs(num));
            String reversed = new StringBuilder(str).reverse().toString();
            System.out.printf("原數字：%s\n", str);
            System.out.printf("反轉後：%s\n", reversed);
            
        } catch (Exception e) {
            System.out.println("輸入錯誤：請輸入有效的數字");
            scanner.nextLine();
        }
    }
    
    /**
     * 主程式方法
     */
    public static void main(String[] args) {
        System.out.println("歡迎使用遞迴數學計算器！");
        System.out.println("此程式實作了四種經典的遞迴數學函式");
        
        while (true) {
            displayMenu();
            
            try {
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        handleCombination();
                        break;
                    case 2:
                        handleCatalan();
                        break;
                    case 3:
                        handleHanoi();
                        break;
                    case 4:
                        handlePalindrome();
                        break;
                    case 5:
                        System.out.println("\n感謝使用遞迴數學計算器！再見！👋");
                        scanner.close();
                        return;
                    default:
                        System.out.println("無效的選擇，請輸入 1-5");
                }
                
                // 詢問是否繼續
                System.out.print("\n按 Enter 鍵繼續...");
                scanner.nextLine(); // 消費掉前面的換行
                scanner.nextLine(); // 等待用戶按 Enter
                
            } catch (Exception e) {
                System.out.println("輸入錯誤：請輸入有效的數字");
                scanner.nextLine(); // 清除緩衝區
            }
        }
    }
}