/*
題目：Find the Index of the First Occurrence in a String
尋找字串中第一個出現的索引
給定兩個字串 haystack 和 needle，請回傳 needle 在 haystack 中第一次出現的索引。
如果 needle 不是 haystack 的一部分，則回傳 -1。
範例: Input: haystack = "leetcode", needle = "leeto"；Output: -1
*/

class Solution {
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) {  // 空字串
            return 0;
        }

        // 遍歷 haystack 字串，從 0 開始
        for (int i = 0; i <= haystack.length() - needle.length(); i++) {
            // 使用 substring 截取 haystack 的子字串，如果與 needle 相等表示找到，回傳當前索引
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                return i; 
            }
        }
        return -1;  // 迴圈結束後仍未找到，表示 needle 不在 haystack 中
    }
}

/*
解題思路：
1. 使用一個迴圈來遍歷 haystack 的每一個可能的起始位置。
2. 從 haystack 的索引 0 開始，一直到 haystack.length() - needle.length()，若起始位置超過這個範圍，剩下的字串長度就不夠容納 needle 。
3. 在每個位置 i，截取 haystack 從 i 開始、長度為 needle.length() 的子字串。
4. 將截取的子字串與 needle 進行比較。如果相等，就表示找到了第一次出現的位置，並回傳 i；如果迴圈結束後，都沒有找到匹配的子字串，則代表 needle 不在 haystack 中，此時回傳 -1。
*/