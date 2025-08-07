import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * 成績統計系統
 */
public class GradeStatisticsSystem {
    
    /**
     * 等第類型
     */
    public enum Grade {
        A("優秀", 90, 100),
        B("良好", 80, 89),
        C("及格", 70, 79),
        D("待加強", 60, 69),
        F("不及格", 0, 59);
        
        private final String description;
        private final int minScore;
        private final int maxScore;
        
        Grade(String description, int minScore, int maxScore) {
            this.description = description;
            this.minScore = minScore;
            this.maxScore = maxScore;
        }
        
        public String getDescription() { return description; }
        public int getMinScore() { return minScore; }
        public int getMaxScore() { return maxScore; }
        
        /**
         * 根據分數判定等第
         */
        public static Grade getGradeByScore(int score) {
            for (Grade grade : Grade.values()) {
                if (score >= grade.minScore && score <= grade.maxScore) {
                    return grade;
                }
            }
            return Grade.F; // 預設為F
        }
    }
    
    private int[] scores;
    private double average;
    private int highest;
    private int lowest;
    private int[] gradeCount;
    private int aboveAverageCount;
    
    /**
     * 學生成績陣列
     * 如果成績陣列為空或包含無效成績
     */
    public GradeStatisticsSystem(int[] scores) {
        if (scores == null || scores.length == 0) {
            throw new IllegalArgumentException("成績陣列不能為空");
        }
        
        // 驗證成績範圍
        for (int score : scores) {
            if (score < 0 || score > 100) {
                throw new IllegalArgumentException("成績必須在0-100之間，發現無效成績: " + score);
            }
        }
        
        this.scores = scores.clone();
        calculateStatistics();
    }
    
    /**
     * 計算所有統計數據
     */
    private void calculateStatistics() {
        // 1. 計算基本統計值
        double sum = 0;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        
        for (int score : scores) {
            sum += score;
            max = Math.max(max, score);
            min = Math.min(min, score);
        }
        
        this.average = sum / scores.length;
        this.highest = max;
        this.lowest = min;
        
        // 2. 各等第人數
        this.gradeCount = new int[Grade.values().length];
        for (int score : scores) {
            Grade grade = Grade.getGradeByScore(score);
            gradeCount[grade.ordinal()]++;
        }
        
        // 3. 高於平均分的學生人數
        this.aboveAverageCount = 0;
        for (int score : scores) {
            if (score > average) {
                aboveAverageCount++;
            }
        }
    }
    
    /**
     * 獲取平均成績
     */
    public double getAverage() {
        return average;
    }
    
    /**
     * 獲取最高成績
     */
    public int getHighest() {
        return highest;
    }
    
    /**
     * 獲取最低成績
     */
    public int getLowest() {
        return lowest;
    }
    
    /**
     * 獲取高於平均分的學生人數
     */
    public int getAboveAverageCount() {
        return aboveAverageCount;
    }
    
    /**
     * 列印成績報表
     */
    public void printReport() {
        DecimalFormat df = new DecimalFormat("#.##");
        
        System.out.println("=".repeat(60));
        System.out.println("                    成績統計報表");
        System.out.println("=".repeat(60));
        
        // 1. 基本統計信息
        System.out.println("\n【基本統計】");
        System.out.printf("學生人數：%d 人%n", scores.length);
        System.out.printf("平均成績：%s 分%n", df.format(average));
        System.out.printf("最高成績：%d 分%n", highest);
        System.out.printf("最低成績：%d 分%n", lowest);
        System.out.printf("高於平均：%d 人 (%.1f%%)%n", 
                         aboveAverageCount,
                         (double) aboveAverageCount / scores.length * 100);
        
        // 2. 成績分布
        System.out.println("\n【成績分布】");
        System.out.println("原始成績：" + Arrays.toString(scores));
        
        // 3. 等第統計
        System.out.println("\n【等第統計】");
        System.out.println("-".repeat(50));
        System.out.printf("%-8s %-10s %-8s %-8s %-8s%n", 
                         "等第", "說明", "分數區間", "人數", "比例");
        System.out.println("-".repeat(50));
        
        Grade[] grades = Grade.values();
        for (int i = 0; i < grades.length; i++) {
            Grade grade = grades[i];
            int count = gradeCount[i];
            double percentage = (double) count / scores.length * 100;
            
            System.out.printf("%-8s %-10s %02d-%02d分   %-8d %.1f%%%n",
                             grade.name(),
                             grade.getDescription(),
                             grade.getMinScore(),
                             grade.getMaxScore(),
                             count,
                             percentage);
        }  
    }
    
    /**
     * 主程式 
     */
    public static void main(String[] args) {
        System.out.println("成績統計系統演示");
        System.out.println("==================");
        
        try {
            // 測試資料
            int[] testScores = {85, 92, 78, 96, 87, 73, 89, 94, 82, 90};
            
            // 建立統計系統
            GradeStatisticsSystem system = new GradeStatisticsSystem(testScores);
            
            // 列印完整報表
            system.printReport();
            
        } catch (IllegalArgumentException e) {
            System.err.println("錯誤：" + e.getMessage());
        } catch (Exception e) {
            System.err.println("系統錯誤：" + e.getMessage());
        }
    }
}