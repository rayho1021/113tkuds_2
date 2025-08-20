import java.util.Scanner;

public class M05_GCD_LCM_Recursive {
    
    /*
     * Time Complexity: O(log min(a, b))
     * 說明：歐幾里得演算法每次遞迴都會使較大數變為較小數，較小數變為餘數
     *       餘數最多為原較小數的一半，因此遞迴深度最多為 log min(a, b)
     */
    
    // 使用 a 和 b 計算最大公因數(基底情況：當 b 為 0 時，GCD 為 a)
    public static int gcd(int a, int b) {
        if (b == 0) {
            return a; 
        }

        return gcd(b, a % b); // 遞迴情況：gcd(a, b) = gcd(b, a % b)
    }
    
    /*
     * Time Complexity: O(log min(a, b))
     * 說明：LCM 計算主要依賴 GCD 的計算，因此時間複雜度與 GCD 相同
     *       使用 a / gcd * b 的順序避免乘法溢位，先除法後乘法確保中間結果不會超出範圍
     */
    
    // 計算 a 和 b 的最小公倍數
    public static long lcm(int a, int b) {
        int gcdValue = gcd(a, b);
        return (long) a / gcdValue * b;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取兩個正整數
        System.out.print("\n請輸入兩個正整數 a 和 b (a b): ");
        String[] input = scanner.nextLine().trim().split("\\s+");
        int a = Integer.parseInt(input[0]);
        int b = Integer.parseInt(input[1]);
        
        // 計算 GCD 和 LCM
        int gcdResult = gcd(a, b);
        long lcmResult = lcm(a, b);
        
        // 輸出
        System.out.println("GCD: " + gcdResult);
        System.out.println("LCM: " + lcmResult + "\n");
        
        scanner.close();
    }
}