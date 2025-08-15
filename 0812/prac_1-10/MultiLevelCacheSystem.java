/*
題目 10：多層級快取系統
題目描述：
設計一個多層級的 LRU 快取系統，有三個層級：L1、L2、L3，每個層級有不同的容量和存取成本。使用 heap 來最佳化資料在不同層級間的移動。
系統需求：
- 三個層級的容量：L1(快,小), L2(中,中), L3(慢,大)
- 支援 get(key) 和 put(key, value) 操作
- 當層級滿時，根據存取頻率和成本決定資料移動策略
- 使用 heap 來維護每個層級的存取優先順序

設計考量：
L1: 容量=2, 存取成本=1
L2: 容量=5, 存取成本=3  
L3: 容量=10, 存取成本=10

策略：頻繁存取的資料往上層移動，較少存取的往下層移動

測試案例：
put(1, "A"); put(2, "B"); put(3, "C");
// L1: [2,3], L2: [1], L3: []

get(1); get(1); get(2);
// 1被頻繁存取，應該移到L1
// L1: [1,2], L2: [3], L3: []

put(4, "D"); put(5, "E"); put(6, "F");
// 根據存取頻率決定資料在各層級的分布
*/

import java.util.*;

public class MultiLevelCacheSystem {

    // 快取項目，包含所有屬性
    static class CacheItem {
        String key;
        String value;
        int accessCount;
        long timestamp;
        int level;
        
        public CacheItem(String key, String value, int level) {
            this.key = key;
            this.value = value;
            this.accessCount = 0;
            this.timestamp = System.nanoTime();
            this.level = level;
        }

        // 根據 accessCount 和 level 計算評分
        public double getScore() {
            int[] costs = {1, 3, 10}; // L1, L2, L3 的存取成本
            return (double) accessCount / costs[level - 1];
        }
    }

    // 每個層級的快取
    static class CacheLevel {
        int level;
        int capacity;
        Map<String, CacheItem> items;
        PriorityQueue<CacheItem> scoreHeap;

        public CacheLevel(int level, int capacity) {
            this.level = level; // 修正：加入 level 屬性
            this.capacity = capacity;
            this.items = new HashMap<>();
            this.scoreHeap = new PriorityQueue<>(Comparator.comparingDouble(CacheItem::getScore));
        }

        public boolean isFull() {
            return items.size() >= capacity;
        }

        public void put(CacheItem item) {
            items.put(item.key, item);
            scoreHeap.offer(item);
        }

        public CacheItem remove(String key) {
            return items.remove(key);
        }
        
        public CacheItem getLowestScoreItem() {
            if (scoreHeap.isEmpty()) {
                return null;
            }
            while (!scoreHeap.isEmpty() && !items.containsKey(scoreHeap.peek().key)) {
                scoreHeap.poll();
            }
            return scoreHeap.peek();
        }
    }

    private final CacheLevel l1;
    private final CacheLevel l2;
    private final CacheLevel l3;
    private final Map<String, String> dataStore;

    public MultiLevelCacheSystem() {
        this.l1 = new CacheLevel(1, 2); 
        this.l2 = new CacheLevel(2, 5); 
        this.l3 = new CacheLevel(3, 10);
        this.dataStore = new HashMap<>();
    }

    public void put(String key, String value) {
        CacheItem item = findInCache(key);
        if (item != null) {
            item.value = value;
            item.accessCount++;
            item.timestamp = System.nanoTime();
            rebalanceCache(item);
        } else {
            item = new CacheItem(key, value, 1);
            putAndPromote(item, l1, null);
        }
        dataStore.put(key, value);
    }

    public String get(String key) {
        CacheItem item = findInCache(key);
        if (item != null) {
            item.accessCount++;
            item.timestamp = System.nanoTime();
            rebalanceCache(item);
            System.out.printf("Get(%s) -> Value: %s (from L%d), AccessCount: %d%n", key, item.value, item.level, item.accessCount);
            return item.value;
        } else {
            if (dataStore.containsKey(key)) {
                String value = dataStore.get(key);
                CacheItem newItem = new CacheItem(key, value, 1);
                putAndPromote(newItem, l1, null);
                System.out.printf("Get(%s) -> Value: %s (Cache Miss, Loaded to L1)%n", key, value);
                return value;
            }
            System.out.printf("Get(%s) -> Not Found%n", key);
            return null;
        }
    }

    private CacheItem findInCache(String key) {
        if (l1.items.containsKey(key)) {
            return l1.items.get(key);
        }
        if (l2.items.containsKey(key)) {
            return l2.items.get(key);
        }
        if (l3.items.containsKey(key)) {
            return l3.items.get(key);
        }
        return null;
    }
    
    private void rebalanceCache(CacheItem item) {
        if (item.level > 1) {
            CacheLevel nextLevelUp = getLevel(item.level - 1);
            CacheItem lowestInNextLevelUp = nextLevelUp.getLowestScoreItem();
            if (lowestInNextLevelUp == null || item.getScore() > lowestInNextLevelUp.getScore()) {
                System.out.printf("Rebalance: Promoting key '%s' from L%d to L%d%n", item.key, item.level, nextLevelUp.level);
                
                CacheLevel currentLevel = getLevel(item.level);
                currentLevel.remove(item.key);
                putAndPromote(item, nextLevelUp, currentLevel);
            }
        }
    }
    
    private void putAndPromote(CacheItem item, CacheLevel newLevel, CacheLevel oldLevel) {
        if (newLevel.isFull()) {
            CacheItem lowest = newLevel.getLowestScoreItem();
            if (lowest != null) {
                newLevel.remove(lowest.key);
                CacheLevel nextLevelDown = getLevel(newLevel.level + 1);
                if (nextLevelDown != null) {
                    System.out.printf("Promote: Demoting key '%s' from L%d to L%d%n", lowest.key, newLevel.level, nextLevelDown.level);
                    putAndPromote(lowest, nextLevelDown, newLevel);
                }
            }
        }

        if (oldLevel != null) {
            oldLevel.remove(item.key);
        }
        
        item.level = newLevel.level;
        newLevel.put(item);
    }

    private CacheLevel getLevel(int level) {
        if (level == 1) return l1;
        if (level == 2) return l2;
        if (level == 3) return l3;
        return null;
    }

    public static void main(String[] args) {
        MultiLevelCacheSystem cache = new MultiLevelCacheSystem();

        System.out.println("--- put 操作一 ---");
        cache.put("1", "A");
        cache.put("2", "B");
        cache.put("3", "C");
        System.out.println("L1 items: " + cache.l1.items.keySet());
        System.out.println("L2 items: " + cache.l2.items.keySet());
        System.out.println("L3 items: " + cache.l3.items.keySet());
        System.out.println("---");

        System.out.println("--- get 操作 ---");
        cache.get("1");
        cache.get("1");
        cache.get("2");
        System.out.println("L1 items: " + cache.l1.items.keySet());
        System.out.println("L2 items: " + cache.l2.items.keySet());
        System.out.println("L3 items: " + cache.l3.items.keySet());
        System.out.println("---");

        System.out.println("--- put 操作二 ---");
        cache.put("4", "D");
        cache.put("5", "E");
        cache.put("6", "F");
        System.out.println("L1 items: " + cache.l1.items.keySet());
        System.out.println("L2 items: " + cache.l2.items.keySet());
        System.out.println("L3 items: " + cache.l3.items.keySet());
        System.out.println("---");
    }
}