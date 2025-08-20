import java.util.Scanner;

public class M04_TieredTaxSimple {
    /**
     * 稅率級距類別
     */
    static class TaxBracket {
        int upperLimit;    // 級距上限
        double rate;       // 稅率
        
        public TaxBracket(int upperLimit, double rate) {
            this.upperLimit = upperLimit;
            this.rate = rate;
        }
    }
    
    /*定義稅率級距
    0-120,000: 5%
    120,001-500,000: 12%
    500,001-1,000,000: 20%
    1,000,001以上: 30%
    */
    private static final TaxBracket[] TAX_BRACKETS = {
        new TaxBracket(120000, 5.0),    
        new TaxBracket(500000, 12.0),     
        new TaxBracket(1000000, 20.0),   
        new TaxBracket(Integer.MAX_VALUE, 30.0)  
    };
    
    /*
     * Time Complexity: O(1)
     * 說明：稅率級距數量固定為 4，每個收入最多遍歷 4 個級距進行計算，雖然有迴圈，但迴圈次數為常數，所以時間複雜度為 O(1)。
     */

    //計算累進稅額
    public static int calculateTax(int income) { // income: 年收入
        if (income <= 0) {
            return 0; // 應繳稅額
        }
        
        double totalTax = 0.0;
        int remainingIncome = income;
        int previousLimit = 0;
        
        // 逐段計算稅額
        for (TaxBracket bracket : TAX_BRACKETS) {
            if (remainingIncome <= 0) {
                break;
            }
            
            // 計算此級距的應稅收入
            int bracketLimit = bracket.upperLimit - previousLimit;
            int taxableInThisBracket = Math.min(remainingIncome, bracketLimit);
            
            // 計算此級距的稅額
            double taxInThisBracket = taxableInThisBracket * (bracket.rate / 100.0);
            totalTax += taxInThisBracket;
            
            // 更新剩餘收入和前一級距上限
            remainingIncome -= taxableInThisBracket;
            previousLimit = bracket.upperLimit;
            
            // 如果已經處理完所有收入，提早結束
            if (remainingIncome <= 0) {
                break;
            }
        }
        
        return (int) Math.round(totalTax);
    }
    
    /*
     * Time Complexity: O(n)
     * 說明：需要處理 n 個收入，每個收入的稅額計算為 O(1)，總時間複雜度為 O(n)
     *       空間複雜度為 O(1)，只需要常數額外空間儲存累計值
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 讀取收入筆數
        System.out.print("收入筆數 n : ");
        int n = Integer.parseInt(scanner.nextLine().trim());
        
        int[] incomes = new int[n];
        int[] taxes = new int[n];
        long totalTax = 0;
        
        // 讀取所有收入
        System.out.println("請輸入 " + n + " 個年收入:");
        for (int i = 0; i < n; i++) {
            incomes[i] = Integer.parseInt(scanner.nextLine().trim());
        }
        
        // 計算所有稅額
        for (int i = 0; i < n; i++) {
            taxes[i] = calculateTax(incomes[i]);
            totalTax += taxes[i];
        }
        
        // 計算平均稅額
        int averageTax = (int) Math.round((double) totalTax / n);
        
        // 一次性輸出所有結果
        System.out.println("\n稅額與平均稅額 :");
        for (int i = 0; i < n; i++) {
            System.out.println("Tax: " + taxes[i]);
        }
        System.out.println("Average: " + averageTax +"\n");
        
        scanner.close();
    }
}