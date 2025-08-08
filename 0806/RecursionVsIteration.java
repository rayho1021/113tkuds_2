/*
練習 2.4：遞迴與迭代比較
對以下問題分別實作遞迴和迭代版本，並比較效能：
1. 計算二項式係數
2. 尋找陣列中所有元素的乘積
3. 計算字串中元音字母的數量
4. 檢查括號是否配對正確
 */

import java.util.*;

public class RecursionVsIteration {
    
    // 測試結果記錄類
    static class TestResult {
        String algorithm;
        String method;
        String input;
        Object result;
        long timeNanos;
        long memoryUsed;
        int stackDepth;
        
        TestResult(String algorithm, String method, String input, Object result, 
                  long timeNanos, long memoryUsed, int stackDepth) {
            this.algorithm = algorithm;
            this.method = method;
            this.input = input;
            this.result = result;
            this.timeNanos = timeNanos;
            this.memoryUsed = memoryUsed;
            this.stackDepth = stackDepth;
        }
        
        double getTimeMillis() {
            return timeNanos / 1_000_000.0;
        }
        
        double getMemoryKB() {
            return memoryUsed / 1024.0;
        }
    }
    
    // 用於追蹤遞迴深度的變數
    private static int maxRecursionDepth = 0;
    private static int currentRecursionDepth = 0;
    
    // 1. 二項式係數計算 C(n, k)
    /**
     * 遞迴版本 - 二項式係數
     * 使用公式：C(n, k) = C(n-1, k-1) + C(n-1, k)
     */
    public static long binomialCoefficientRecursive(int n, int k) {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
        
        // 基底條件
        if (k == 0 || k == n) {
            currentRecursionDepth--;
            return 1;
        }
        
        if (k < 0 || k > n) {
            currentRecursionDepth--;
            return 0;
        }
        
        // 遞迴關係
        long result = binomialCoefficientRecursive(n - 1, k - 1) + 
                     binomialCoefficientRecursive(n - 1, k);
        
        currentRecursionDepth--;
        return result;
    }
    
    /**
     * 迭代版本 - 二項式係數
     * 使用動態規劃的迭代實作
     */
    public static long binomialCoefficientIterative(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k == 0 || k == n) return 1;
        
        // 優化：使用對稱性 C(n, k) = C(n, n-k)
        k = Math.min(k, n - k);
        
        long result = 1;
        for (int i = 0; i < k; i++) {
            result = result * (n - i) / (i + 1);
        }
        
        return result;
    }
    
    // 2. 陣列元素乘積計算
    /**
     * 遞迴版本 - 陣列乘積
     */
    public static long arrayProductRecursive(int[] arr, int index) {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
        
        // 基底條件
        if (index >= arr.length) {
            currentRecursionDepth--;
            return 1;
        }
        
        // 遞迴計算
        long result = arr[index] * arrayProductRecursive(arr, index + 1);
        
        currentRecursionDepth--;
        return result;
    }
    
    /**
     * 遞迴版本 - 陣列乘積（外部介面）
     */
    public static long arrayProductRecursive(int[] arr) {
        if (arr.length == 0) return 1;
        return arrayProductRecursive(arr, 0);
    }
    
    /**
     * 迭代版本 - 陣列乘積
     */
    public static long arrayProductIterative(int[] arr) {
        long product = 1;
        for (int num : arr) {
            product *= num;
        }
        return product;
    }
    
    // 3. 元音字母計數
    /**
     * 遞迴版本 - 元音計數
     */
    public static int countVowelsRecursive(String str, int index) {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
        
        // 基底條件
        if (index >= str.length()) {
            currentRecursionDepth--;
            return 0;
        }
        
        // 檢查當前字符是否為元音
        char ch = Character.toLowerCase(str.charAt(index));
        int currentCount = (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') ? 1 : 0;
        
        // 遞迴計算剩餘字符
        int result = currentCount + countVowelsRecursive(str, index + 1);
        
        currentRecursionDepth--;
        return result;
    }
    
    /**
     * 遞迴版本 - 元音計數（外部介面）
     */
    public static int countVowelsRecursive(String str) {
        if (str == null || str.isEmpty()) return 0;
        return countVowelsRecursive(str, 0);
    }
    
    /**
     * 迭代版本 - 元音計數
     */
    public static int countVowelsIterative(String str) {
        if (str == null || str.isEmpty()) return 0;
        
        int count = 0;
        for (char ch : str.toCharArray()) {
            ch = Character.toLowerCase(ch);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                count++;
            }
        }
        return count;
    }
    
    // 4. 括號配對檢查
    /**
     * 遞迴版本 - 括號配對檢查
     */
    public static boolean isValidParenthesesRecursive(String str, int index, int balance) {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
        
        // 如果 balance 變成負數，表示右括號過多
        if (balance < 0) {
            currentRecursionDepth--;
            return false;
        }
        
        // 基底條件：處理完所有字符
        if (index >= str.length()) {
            currentRecursionDepth--;
            return balance == 0;
        }
        
        char ch = str.charAt(index);
        boolean result;
        
        if (ch == '(') {
            result = isValidParenthesesRecursive(str, index + 1, balance + 1);
        } else if (ch == ')') {
            result = isValidParenthesesRecursive(str, index + 1, balance - 1);
        } else {
            // 忽略非括號字符
            result = isValidParenthesesRecursive(str, index + 1, balance);
        }
        
        currentRecursionDepth--;
        return result;
    }
    
    /**
     * 遞迴版本 - 括號配對檢查（外部介面）
     */
    public static boolean isValidParenthesesRecursive(String str) {
        if (str == null || str.isEmpty()) return true;
        return isValidParenthesesRecursive(str, 0, 0);
    }
    
    /**
     * 迭代版本 - 括號配對檢查
     */
    public static boolean isValidParenthesesIterative(String str) {
        if (str == null || str.isEmpty()) return true;
        
        int balance = 0;
        for (char ch : str.toCharArray()) {
            if (ch == '(') {
                balance++;
            } else if (ch == ')') {
                balance--;
                if (balance < 0) return false;
            }
            // 忽略其他字符
        }
        
        return balance == 0;
    }
    
    // 測試和效能分析方法
    /**
     * 執行單個測試並記錄結果（加入異常處理）
     */
    private static TestResult runTest(String algorithm, String method, String input, Runnable test) {
        // 重置遞迴深度追蹤
        maxRecursionDepth = 0;
        currentRecursionDepth = 0;
        
        // 記錄記憶體使用
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // 建議垃圾回收
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        
        // 執行測試
        long startTime = System.nanoTime();
        Object result = null;
        String status = "執行完成";
        
        try {
            test.run();
            result = status;
        } catch (StackOverflowError e) {
            result = "堆疊溢位錯誤";
            status = "堆疊溢位";
            System.out.printf(" %s版本發生堆疊溢位！\n", method);
        } catch (Exception e) {
            result = "錯誤: " + e.getMessage();
            status = "執行錯誤";
            System.out.printf("%s版本執行錯誤: %s\n", method, e.getMessage());
        }
        
        long endTime = System.nanoTime();
        
        // 計算記憶體使用
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = Math.max(0, memoryAfter - memoryBefore);
        
        return new TestResult(algorithm, method, input, result, 
                            endTime - startTime, memoryUsed, maxRecursionDepth);
    }
    
    /**
     * 測試二項式係數
     */
    public static void testBinomialCoefficient() {
        System.out.println("=" .repeat(60));
        System.out.println("         二項式係數 C(n, k) 比較測試");
        System.out.println("=" .repeat(60));
        
        // 測試數據
        int[][] testCases = {
            {5, 2},   // 小規模：C(5, 2) = 10
            {10, 5},  // 中規模：C(10, 5) = 252
            {15, 7}   // 大規模：C(15, 7) = 6435（限制在安全範圍內）
        };
        
        List<TestResult> results = new ArrayList<>();
        
        for (int[] testCase : testCases) {
            int n = testCase[0];
            int k = testCase[1];
            String input = String.format("C(%d, %d)", n, k);
            
            System.out.printf("\n 測試 %s:\n", input);
            
            // 測試遞迴版本
            TestResult recursiveResult = runTest("二項式係數", "遞迴", input, () -> {
                long result = binomialCoefficientRecursive(n, k);
                System.out.printf("遞迴結果: %d\n", result);
            });
            results.add(recursiveResult);
            
            // 測試迭代版本
            TestResult iterativeResult = runTest("二項式係數", "迭代", input, () -> {
                long result = binomialCoefficientIterative(n, k);
                System.out.printf(" 迭代結果: %d\n", result);
            });
            results.add(iterativeResult);
            
            // 比較結果（處理堆疊溢位情況）
            System.out.printf("執行時間 - 遞迴: %.3f ms, 迭代: %.3f ms\n", 
                recursiveResult.getTimeMillis(), iterativeResult.getTimeMillis());
            System.out.printf("遞迴深度: %d\n", recursiveResult.stackDepth);
            
            // 只有在兩者都成功執行時才計算加速比
            if (recursiveResult.result.toString().equals("執行完成") && 
                iterativeResult.result.toString().equals("執行完成") &&
                iterativeResult.timeNanos > 0) {
                double speedup = (double) recursiveResult.timeNanos / iterativeResult.timeNanos;
                System.out.printf("迭代比遞迴快 %.1fx\n", speedup);
            } else if (recursiveResult.result.toString().contains("堆疊溢位")) {
                System.out.println("遞迴版本無法處理此規模的數據");
            }
        }
        
        System.out.println("\n 二項式係數總結:");
        System.out.println("遞迴版本：程式碼簡潔，直接對應數學定義，但時間複雜度為 O(2^n)");
        System.out.println("迭代版本：使用動態規劃思想，時間複雜度為 O(n*k)，效率更高");
        System.out.println("建議：對於大數值使用迭代版本，小數值可用遞迴（可讀性更好）");
        System.out.println("注意：遞迴版本在 n > 20 時會有明顯的效能問題和堆疊溢位風險");
    }
    
    /**
     * 測試陣列乘積
     */
    public static void testArrayProduct() {
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("           陣列元素乘積比較測試");
        System.out.println("=" .repeat(60));
        
        // 測試數據（調整規模避免堆疊溢位）
        int[][] testArrays = {
            {1, 2, 3, 4, 5},                    // 小規模：5個元素
            generateArray(50, 1, 3),            // 中規模：50個元素
            generateArray(500, 1, 2)            // 大規模：500個元素
        };
        
        String[] descriptions = {"小規模(5元素)", "中規模(50元素)", "大規模(500元素)"};
        
        for (int i = 0; i < testArrays.length; i++) {
            int[] arr = testArrays[i];
            String desc = descriptions[i];
            
            System.out.printf("\n 測試 %s:\n", desc);
            System.out.printf("陣列預覽: %s\n", 
                arr.length <= 10 ? Arrays.toString(arr) : 
                String.format("[%d, %d, %d, ..., %d] (%d個元素)", 
                    arr[0], arr[1], arr[2], arr[arr.length-1], arr.length));
            
            // 測試遞迴版本
            TestResult recursiveResult = runTest("陣列乘積", "遞迴", desc, () -> {
                long result = arrayProductRecursive(arr);
                System.out.printf("遞迴結果: %d\n", result);
            });
            
            // 測試迭代版本
            TestResult iterativeResult = runTest("陣列乘積", "迭代", desc, () -> {
                long result = arrayProductIterative(arr);
                System.out.printf("迭代結果: %d\n", result);
            });
            
            // 比較結果（處理異常情況）
            System.out.printf("執行時間 - 遞迴: %.3f ms, 迭代: %.3f ms\n", 
                recursiveResult.getTimeMillis(), iterativeResult.getTimeMillis());
            System.out.printf("遞迴深度: %d\n", recursiveResult.stackDepth);
            
            if (recursiveResult.result.toString().equals("執行完成") && 
                iterativeResult.result.toString().equals("執行完成") &&
                iterativeResult.timeNanos > 0) {
                double speedup = (double) recursiveResult.timeNanos / iterativeResult.timeNanos;
                System.out.printf("迭代比遞迴快 %.1fx\n", speedup);
            } else if (recursiveResult.result.toString().contains("堆疊溢位")) {
                System.out.println("遞迴版本無法處理此規模的數據");
            }
        }
        
        System.out.println("\n 陣列乘積總結:");
        System.out.println("遞迴版本：概念清晰，但有堆疊溢位風險（大陣列）");
        System.out.println("迭代版本：記憶體效率高，適合處理大型陣列");
        System.out.println("建議：一般情況下使用迭代版本，除非有特殊的遞迴處理需求");
    }
    
    /**
     * 測試元音計數
     */
    public static void testVowelCount() {
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("           元音字母計數比較測試");
        System.out.println("=" .repeat(60));
        
        // 測試數據（調整長度避免堆疊溢位）
        String[] testStrings = {
            "Hello World",                       // 小規模
            "The quick brown fox jumps over the lazy dog. " +
            "This sentence contains every letter of the alphabet.",  // 中規模
            generateLongString(1000)             // 大規模：1000字符
        };
        
        String[] descriptions = {"短字串", "中等段落", "長文章(1000字符)"};
        
        for (int i = 0; i < testStrings.length; i++) {
            String str = testStrings[i];
            String desc = descriptions[i];
            
            System.out.printf("\n 測試 %s:\n", desc);
            System.out.printf("   字串預覽: \"%s\"\n", 
                str.length() <= 50 ? str : str.substring(0, 47) + "...");
            System.out.printf("   字串長度: %d\n", str.length());
            
            // 測試遞迴版本
            TestResult recursiveResult = runTest("元音計數", "遞迴", desc, () -> {
                int result = countVowelsRecursive(str);
                System.out.printf("   遞迴結果: %d個元音\n", result);
            });
            
            // 測試迭代版本
            TestResult iterativeResult = runTest("元音計數", "迭代", desc, () -> {
                int result = countVowelsIterative(str);
                System.out.printf("   迭代結果: %d個元音\n", result);
            });
            
            // 比較結果（處理異常情況）
            System.out.printf("執行時間 - 遞迴: %.3f ms, 迭代: %.3f ms\n", 
                recursiveResult.getTimeMillis(), iterativeResult.getTimeMillis());
            System.out.printf("遞迴深度: %d\n", recursiveResult.stackDepth);
            
            if (recursiveResult.result.toString().equals("執行完成") && 
                iterativeResult.result.toString().equals("執行完成") &&
                iterativeResult.timeNanos > 0) {
                double speedup = (double) recursiveResult.timeNanos / iterativeResult.timeNanos;
                System.out.printf("迭代比遞迴快 %.1fx\n", speedup);
            } else if (recursiveResult.result.toString().contains("堆疊溢位")) {
                System.out.println("遞迴版本無法處理此規模的數據");
            }
        }
        
        System.out.println("\n 元音計數總結:");
        System.out.println("遞迴版本：結構清晰，但長字串可能導致堆疊溢位");
        System.out.println("迭代版本：記憶體使用恆定，適合處理任意長度字串");
        System.out.println("建議：字串處理通常選擇迭代，除非需要複雜的遞迴邏輯");
    }
    
    /**
     * 測試括號配對
     */
    public static void testParenthesesValidation() {
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("           括號配對檢查比較測試");
        System.out.println("=" .repeat(60));
        
        // 測試數據（調整深度避免堆疊溢位）
        String[] testStrings = {
            "(())",                              // 小規模：簡單配對
            "((())())()(())",                    // 中規模：複雜嵌套
            generateParentheses(100)             // 大規模：100層深度
        };
        
        String[] descriptions = {"簡單括號", "複雜嵌套", "深度嵌套(100層)"};
        
        for (int i = 0; i < testStrings.length; i++) {
            String str = testStrings[i];
            String desc = descriptions[i];
            
            System.out.printf("\n 測試 %s:\n", desc);
            System.out.printf("   括號序列: \"%s\"\n", 
                str.length() <= 50 ? str : str.substring(0, 47) + "...");
            System.out.printf("   序列長度: %d\n", str.length());
            
            // 測試遞迴版本
            TestResult recursiveResult = runTest("括號配對", "遞迴", desc, () -> {
                boolean result = isValidParenthesesRecursive(str);
                System.out.printf("   遞迴結果: %s\n", result ? "配對正確" : "配對錯誤");
            });
            
            // 測試迭代版本
            TestResult iterativeResult = runTest("括號配對", "迭代", desc, () -> {
                boolean result = isValidParenthesesIterative(str);
                System.out.printf("   迭代結果: %s\n", result ? "配對正確" : "配對錯誤");
            });
            
            // 比較結果（處理異常情況）
            System.out.printf("執行時間 - 遞迴: %.3f ms, 迭代: %.3f ms\n", 
                recursiveResult.getTimeMillis(), iterativeResult.getTimeMillis());
            System.out.printf("遞迴深度: %d\n", recursiveResult.stackDepth);
            
            if (recursiveResult.result.toString().equals("執行完成") && 
                iterativeResult.result.toString().equals("執行完成") &&
                iterativeResult.timeNanos > 0) {
                double speedup = (double) recursiveResult.timeNanos / iterativeResult.timeNanos;
                System.out.printf("迭代比遞迴快 %.1fx\n", speedup);
            } else if (recursiveResult.result.toString().contains("堆疊溢位")) {
                System.out.println(" 遞迴版本無法處理此規模的數據");
            }
        }
        
        System.out.println("\n 括號配對總結:");
        System.out.println("遞迴版本：自然地模擬堆疊行為，邏輯清晰");
        System.out.println("迭代版本：直接使用計數器，記憶體效率更高");
        System.out.println("建議：深度嵌套時使用迭代避免堆疊溢位，簡單情況可用遞迴");
    }
    

    // 工具方法
    /**
     * 生成測試用陣列
     */
    private static int[] generateArray(int size, int min, int max) {
        Random random = new Random(42); // 固定種子確保可重現
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt(max - min + 1) + min;
        }
        return arr;
    }
    
    /**
     * 生成測試用長字串
     */
    private static String generateLongString(int length) {
        StringBuilder sb = new StringBuilder();
        String sample = "abcdefghijklmnopqrstuvwxyz ";
        Random random = new Random(42);
        
        for (int i = 0; i < length; i++) {
            sb.append(sample.charAt(random.nextInt(sample.length())));
        }
        
        return sb.toString();
    }
    
    /**
     * 生成測試用括號序列
     */
    private static String generateParentheses(int pairs) {
        StringBuilder sb = new StringBuilder();
        
        // 生成平衡的括號序列
        for (int i = 0; i < pairs; i++) {
            sb.append('(');
        }
        for (int i = 0; i < pairs; i++) {
            sb.append(')');
        }
        
        return sb.toString();
    }
    
    /**
     * 顯示總體比較分析
     */
    public static void showOverallAnalysis() {
        System.out.println("\n" + "=" .repeat(60));
        System.out.println("              總體分析與建議");
        System.out.println("=" .repeat(60));
        
        System.out.println("\n 遞迴 vs 迭代的選擇原則：");
        System.out.println();
        
        System.out.println(" 選擇遞迴的情況：");
        System.out.println("問題本身具有遞迴結構（樹、圖遍歷）");
        System.out.println("程式碼可讀性和數學定義的對應性重要");
        System.out.println("資料規模較小，不會造成堆疊溢位");
        System.out.println("分治法等天然適合遞迴的演算法");
        
        System.out.println("\n 選擇迭代的情況：");
        System.out.println("處理大量資料，記憶體效率重要");
        System.out.println("執行效能是主要考量");
        System.out.println("避免堆疊溢位的風險");
        System.out.println("線性處理問題（陣列遍歷、字串處理）");
        
        System.out.println("\n  效能比較總結：");
        System.out.println("時間複雜度：通常相同，但遞迴有函式呼叫開銷");
        System.out.println("空間複雜度：遞迴 O(n) 堆疊空間，迭代通常 O(1)");
        System.out.println("可讀性：遞迴通常更直觀，迭代更接近機器執行");
        System.out.println("除錯難度：遞迴較難追蹤，迭代較容易");
        
        System.out.println("\n 實務建議：");
        System.out.println("1. 優先考慮問題的本質和資料特性");
        System.out.println("2. 小規模問題可選擇更清晰的實作方式");
        System.out.println("3. 大規模問題優先考慮效能和穩定性");
        System.out.println("4. 必要時可提供兩種實作供選擇");
    }
    
    /**
     * 主程式
     */
    public static void main(String[] args) {
        System.out.println("遞迴與迭代比較測試程式");
        System.out.println("本程式將對四個經典演算法問題進行遞迴與迭代的效能比較");
        System.out.println("每個演算法都使用三組不同規模的測試數據進行測試\n");
        
        try {
            // 依序執行四個測試
            testBinomialCoefficient();
            testArrayProduct();
            testVowelCount();
            testParenthesesValidation();
            
            // 顯示總體分析
            showOverallAnalysis();
            
        } catch (Exception e) {
            System.err.println("測試過程中發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n 所有測試完成！");
        System.out.println("透過以上測試，我們可以清楚看到遞迴與迭代在不同情況下的優缺點。");
        System.out.println("選擇合適的實作方式對於寫出高效且可維護的程式碼非常重要。");
    }
}