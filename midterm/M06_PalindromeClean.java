import java.util.Scanner;

public class M06_PalindromeClean {
    
    /**
     * 清洗字串，移除非英文字母並轉為小寫
     */
    private static String cleanString(String s) { //s: 原始字串
        StringBuilder cleaned = new StringBuilder();
        
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                cleaned.append(Character.toLowerCase(c));
            }
        }
        
        return cleaned.toString(); // 清洗後的字串
    }
    
    /**
     * 使用遞迴檢測回文
     */
    private static boolean isPalindromeRecursive(String s, int left, int right) {
        // 指標相遇或交錯，表示檢測完畢
        if (left >= right) {
            return true;
        }
        
        // 檢查當前位置字符是否相同
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        
        // 遞迴檢查內部子字串
        return isPalindromeRecursive(s, left + 1, right - 1);
    }
    
    /**
     * 回文檢測
     */
    public static boolean isPalindrome(String s) {
        String cleaned = cleanString(s);   // 先清洗字串
        
        // 空字串或單字符視為回文
        if (cleaned.length() <= 1) {
            return true;
        }
        
        // 使用遞迴檢測回文
        return isPalindromeRecursive(cleaned, 0, cleaned.length() - 1);
    }
    
    /**
     * 使用雙指標迭代法檢測回文
     */
    public static boolean isPalindromeIterative(String s) {
        String cleaned = cleanString(s);
        
        if (cleaned.length() <= 1) {
            return true;
        }
        
        int left = 0;
        int right = cleaned.length() - 1;
        
        while (left < right) {
            if (cleaned.charAt(left) != cleaned.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取輸入字串
        System.out.print("\n請輸入字串 : ");
        String input = scanner.nextLine();
        
        // 檢測回文
        boolean result = isPalindrome(input);
        
        // 輸出結果
        System.out.println(result ? "Yes" : "No");
        
        scanner.close();
    }
}