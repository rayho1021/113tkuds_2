/*
練習 2.5：遞迴樹狀結構預習
使用遞迴解決以下樹狀結構相關問題：
1. 遞迴計算資料夾的總檔案數（模擬檔案系統）
2. 遞迴列印多層選單結構
3. 遞迴處理巢狀陣列的展平
4. 遞迴計算巢狀清單的最大深度
*/

import java.util.*;

public class RecursiveTreePreview {
    
    // 1. 檔案系統結構類別
    static class FileSystem {
        private String name;
        private int fileCount; // 當前資料夾直接包含的檔案數
        private List<FileSystem> children; // 子資料夾
        
        public FileSystem(String name, int fileCount) {
            this.name = name;
            this.fileCount = fileCount;
            this.children = new ArrayList<>();
        }
        
        public void addChild(FileSystem child) {
            if (child != null) {
                children.add(child);
            }
        }
        
        /**
         * 遞迴計算資料夾總檔案數（包含所有子資料夾）
         */
        public int getTotalFileCount() {
            int total = fileCount; // 當前資料夾的檔案數

            for (FileSystem child : children) {
                total += child.getTotalFileCount();
            }
      
            return total;
        }
        
        public String getName() {
            return name;
        }
    }
    
    // 2. 選單項目類別
    static class MenuItem {
        private String name;
        private List<MenuItem> subMenus;
        
        public MenuItem(String name) {
            this.name = name;
            this.subMenus = new ArrayList<>();
        }
        
        public void addSubMenu(MenuItem subMenu) {
            if (subMenu != null) {
                subMenus.add(subMenu);
            }
        }
        
        /**
         * 遞迴列印多層選單結構
         */
        public void printMenu() {
            printMenuRecursive(0);
        }
        
        private void printMenuRecursive(int level) {
            // 產生縮排
            String indent = "  ".repeat(level);
            String prefix = level == 0 ? "- " : "  - ";
            
            System.out.println(indent + prefix + name);
            
            // 遞迴列印子選單
            for (MenuItem subMenu : subMenus) {
                subMenu.printMenuRecursive(level + 1);
            }
        }
    }
    
    // 3. 巢狀陣列處理工具類別
    static class NestedArrayUtils {
        /**
         * 遞迴處理巢狀陣列的展平
         */
        public static List<Object> flatten(Object[] nestedArray) {
            List<Object> result = new ArrayList<>();
            
            if (nestedArray == null) {
                return result;
            }
            
            flattenRecursive(nestedArray, result);
            return result;
        }
        
        private static void flattenRecursive(Object[] array, List<Object> result) {
            for (Object element : array) {
                if (element == null) {
                    continue; // 跳過null值
                }
                
                if (element instanceof Object[]) {
                    // 遞迴處理巢狀陣列
                    flattenRecursive((Object[]) element, result);
                } else {
                    // 加入非陣列元素
                    result.add(element);
                }
            }
        }
    }
    
    // 4. 巢狀清單結構類別
    static class NestedList {
        private List<Object> items;
        
        public NestedList() {
            this.items = new ArrayList<>();
        }
        
        public void add(Object item) {
            items.add(item);
        }
        
        /**
         * 遞迴計算巢狀清單的最大深度
         */
        public int getMaxDepth() {
            if (items.isEmpty()) {
                return 0;
            }
            
            return getMaxDepthRecursive(items);
        }
        
        private int getMaxDepthRecursive(List<Object> list) {
            int maxDepth = 1; // 當前層的深度
            
            for (Object item : list) {
                if (item instanceof NestedList) {
                    NestedList nestedList = (NestedList) item;
                    int childDepth = nestedList.getMaxDepth();
                    if (childDepth > 0) { // 只有非空清單才增加深度
                        maxDepth = Math.max(maxDepth, 1 + childDepth);
                    }
                } else if (item instanceof List) {
                    // 處理一般的List型別
                    List<?> nestedList = (List<?>) item;
                    if (!nestedList.isEmpty()) {
                        int childDepth = getMaxDepthForGeneralList(nestedList);
                        maxDepth = Math.max(maxDepth, 1 + childDepth);
                    }
                }
            }
            
            return maxDepth;
        }
        
        private int getMaxDepthForGeneralList(List<?> list) {
            int maxDepth = 1;
            
            for (Object item : list) {
                if (item instanceof List) {
                    List<?> nestedList = (List<?>) item;
                    if (!nestedList.isEmpty()) {
                        int childDepth = getMaxDepthForGeneralList(nestedList);
                        maxDepth = Math.max(maxDepth, 1 + childDepth);
                    }
                }
            }
            
            return maxDepth;
        }
        
        public List<Object> getItems() {
            return items;
        }
    }
    
    /**
     * 主程式 
     */
    public static void main(String[] args) {
        System.out.println("=== 遞迴樹狀結構預習 ===\n");
        
        // 1. 測試檔案系統
        System.out.println("1. 檔案系統總檔案數計算：");
        FileSystem root = new FileSystem("根目錄", 5);
        FileSystem documents = new FileSystem("文件", 10);
        FileSystem pictures = new FileSystem("圖片", 20);
        FileSystem music = new FileSystem("音樂", 15);
        FileSystem subFolder = new FileSystem("子資料夾", 8);
        
        documents.addChild(subFolder);
        root.addChild(documents);
        root.addChild(pictures);
        root.addChild(music);
        
        System.out.println("總檔案數：" + root.getTotalFileCount());
        System.out.println();
        
        // 2. 測試選單結構
        System.out.println("2. 多層選單結構：");
        MenuItem mainMenu = new MenuItem("主選單");
        MenuItem fileMenu = new MenuItem("檔案");
        MenuItem editMenu = new MenuItem("編輯");
        MenuItem newItem = new MenuItem("新增");
        MenuItem openItem = new MenuItem("開啟");
        MenuItem saveItem = new MenuItem("儲存");
        MenuItem copyItem = new MenuItem("複製");
        MenuItem pasteItem = new MenuItem("貼上");
        
        fileMenu.addSubMenu(newItem);
        fileMenu.addSubMenu(openItem);
        fileMenu.addSubMenu(saveItem);
        editMenu.addSubMenu(copyItem);
        editMenu.addSubMenu(pasteItem);
        mainMenu.addSubMenu(fileMenu);
        mainMenu.addSubMenu(editMenu);
        
        mainMenu.printMenu();
        System.out.println();
        
        // 3. 測試巢狀陣列展平
        System.out.println("3. 巢狀陣列展平：");
        Object[] nestedArray = {
            1, 2, 
            new Object[]{3, 4, new Object[]{5, 6}}, 
            7, 
            new Object[]{8, new Object[]{9, 10}}
        };
        
        List<Object> flattened = NestedArrayUtils.flatten(nestedArray);
        System.out.println("原始巢狀陣列：[1, 2, [3, 4, [5, 6]], 7, [8, [9, 10]]]");
        System.out.println("展平結果：" + flattened);
        System.out.println();
        
        // 4. 測試巢狀清單最大深度
        System.out.println("4. 巢狀清單最大深度：");
        
        // 建立深度為3的巢狀清單
        NestedList level3 = new NestedList();
        level3.add("深度3");
        
        NestedList level2 = new NestedList();
        level2.add("深度2");
        level2.add(level3);
        
        NestedList level1 = new NestedList();
        level1.add("深度1");
        level1.add(level2);
        level1.add("同深度1");
        
        System.out.println("巢狀清單最大深度：" + level1.getMaxDepth());
        
        // 測試一般List的巢狀深度
        List<Object> generalList = new ArrayList<>();
        generalList.add("第一層");
        
        List<Object> innerList1 = new ArrayList<>();
        innerList1.add("第二層");
        
        List<Object> innerList2 = new ArrayList<>();
        innerList2.add("第三層");
        innerList1.add(innerList2);
        generalList.add(innerList1);
        
        NestedList testGeneral = new NestedList();
        testGeneral.add(generalList);
        System.out.println("包含一般List的巢狀清單深度：" + testGeneral.getMaxDepth());
        
        // 測試空清單
        NestedList empty = new NestedList();
        System.out.println("空清單深度：" + empty.getMaxDepth());
        
        System.out.println("\n=== 執行完畢 ===");
    }
}