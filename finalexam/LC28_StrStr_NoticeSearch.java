/*
題目: 公告全文搜尋
1.在主字串 haystack（公告全文）中尋找子字串 needle 首次出現的起始索引，找不到回 -1；若 needle 長度為 0，回 0。
2.解法核心：示範暴力；必要時改 KMP（前綴函數 / failure function）。
3.複雜度 : 暴力 O(n*m)；KMP O(n+m)
4.邊界案例:
- needle 空
- needle 長於 haystack
- haystack 與 needle 相同
- 重複前綴如 "aaaaab" 尋找 "aaab"
5.限制：|haystack|, |needle| <= 5e4
6.
輸入：
- 第一行為 haystack
- 第二行為 needle（可能為空字串或包含空白）。
輸出：一個整數（索引或 -1）。
*/

import java.io.*;
public class LC28_StrStr_NoticeSearch {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 讀取兩行；若沒有到第二行則視為空字串
        String haystack = br.readLine();
        if (haystack == null) haystack = "";
        String needle = br.readLine();
        if (needle == null) needle = "";
        System.out.println(strStr(haystack, needle));
    }

    //KMP 搜尋：回傳 needle 在 haystack 首次出現的起始索引；找不到回 -1；若 needle 長度為 0 回 0。
    private static int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) return 0;       // 題目規定
        if (m > n) return -1;       // 比主字串還長，不可能存在

        // 建立 needle 的 LPS（longest prefix which is also suffix）陣列
        int[] lps = buildLPS(needle);

        int i = 0; // 指向 haystack
        int j = 0; // 指向 needle
        while (i < n) {
            if (haystack.charAt(i) == needle.charAt(j)) {  // 匹配則雙指標前進
                i++;
                j++;
                if (j == m) {  
                    return i - j;  // 完整匹配到 needle，回傳起始索引
                }
            } 
            else {
                if (j > 0) {
                    j = lps[j - 1];  // 不匹配時，利用 lps 跳轉（避免回溯 i）
                } 
                else {
                    i++; // j==0 時無法再退，直接移動 i
                }
            }
        }
        return -1; // 掃描完沒找到
    }

    /**
     * 建立 KMP 的 LPS 陣列：
     * lps[i] = 在 needle[0..i] 中，既為 proper prefix 又為 suffix 的最長長度
     */
    private static int[] buildLPS(String pat) {
        int m = pat.length();
        int[] lps = new int[m];
        lps[0] = 0;
        int len = 0; // 當前比較的 prefix 長度
        int i = 1;

        while (i < m) {
            if (pat.charAt(i) == pat.charAt(len)) {  // 擴展相等的前綴
                len++;
                lps[i] = len;
                i++;
            } 
            else {
                if (len != 0) {
                    len = lps[len - 1]; // 回退到先前的相容前綴長度，繼續嘗試
                } 
                else {
                    // 無相容前綴，該位置為 0
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}


/*
解題思路 : 
1.若 needle 長度為 0 → 回傳 0（題目規定）。
2.若 needle 長於 haystack → 回傳 -1。
3.使用 KMP：先對 needle 建 LPS 陣列（失敗函數），表示每個位置匹配失敗時應跳到的 next 比對位置，避免對 haystack 的重複比對。
4.掃描 haystack 時維持兩個指標 i（主字串）、j（模式）；匹配時雙指標++，不匹配時使用 lps 移動 j（若 j==0 則 i++）。
5.若 j == needle.length() 表示找到，起始索引為 i - j。整體時間 O(n + m)，空間 O(m)。
*/