import java.util.*;

public class M03_TopKConvenience {

    static class Product { //商品類別
        String name;
        int quantity;
        int inputOrder;  // 輸入順序
        
        public Product(String name, int quantity, int inputOrder) {
            this.name = name;
            this.quantity = quantity;
            this.inputOrder = inputOrder;
        }
        
        @Override
        public String toString() {
            return name + " " + quantity;
        }
    }
    
    static class MinHeap {
        private Product[] heap;
        private int size;
        private int capacity;
        
        public MinHeap(int capacity) {
            this.capacity = capacity;
            this.heap = new Product[capacity];
            this.size = 0;
        }
        
        /*
         * Time Complexity: O(log K)
         * 說明：新元素插入到堆底，然後向上調整至正確位置，最多調整 log K 層
         */
        public void insert(Product product) {
            if (size < capacity) {
                heap[size] = product;
                heapifyUp(size);
                size++;
            }
        }
        
        /*
         * Time Complexity: O(log K)
         * 說明：移除堆頂元素，將最後元素移到堆頂，然後向下調整至正確位置，最多調整 log K 層
         */
        public Product extractMin() {
            if (size == 0) return null;
            
            Product min = heap[0];
            heap[0] = heap[size - 1];
            size--;
            if (size > 0) {
                heapifyDown(0);
            }
            return min;
        }
        
        public Product peek() {
            return size > 0 ? heap[0] : null;
        }
        public boolean isFull() {
            return size == capacity;
        }
        public int getSize() {
            return size;
        }
        
        /*
         * Time Complexity: O(log K)
         * 說明：替換堆頂元素後，向下調整至正確位置，最多調整 log K 層
         */
        public void replaceTop(Product product) {
            if (size > 0) {
                heap[0] = product;
                heapifyDown(0);
            }
        }
        
        /**
         * 向上調整堆
         */
        private void heapifyUp(int index) {
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                if (compare(heap[index], heap[parentIndex]) >= 0) {
                    break;
                }
                swap(index, parentIndex);
                index = parentIndex;
            }
        }
        
        /**
         * 向下調整堆
         */
        private void heapifyDown(int index) {
            while (true) {
                int smallest = index;
                int leftChild = 2 * index + 1;
                int rightChild = 2 * index + 2;
                
                if (leftChild < size && compare(heap[leftChild], heap[smallest]) < 0) {
                    smallest = leftChild;
                }
                
                if (rightChild < size && compare(heap[rightChild], heap[smallest]) < 0) {
                    smallest = rightChild;
                }
                
                if (smallest == index) {
                    break;
                }
                
                swap(index, smallest);
                index = smallest;
            }
        }
        
        /**
         * 比較兩個商品(先比銷量，銷量相同時按字典序)
         */
        private int compare(Product p1, Product p2) {
            if (p1.quantity != p2.quantity) {
                return Integer.compare(p1.quantity, p2.quantity);
            }

            return p1.name.compareTo(p2.name);
        }
        
        private void swap(int i, int j) {
            Product temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
        
        /**
         * 獲取堆中所有元素
         */
        public List<Product> getAllProducts() {
            List<Product> result = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                result.add(heap[i]);
            }
            return result;
        }
    }
    
    /*
     * Time Complexity: O(n log K + K log K)
     * 說明：遍歷 n 個商品，每個商品可能需要 O(log K) 的堆操作，最後排序 K 個元素需要 O(K log K)
     *       總時間複雜度為 O(n log K + K log K)
     */
    public static List<Product> findTopK(List<Product> products, int k) {
        MinHeap minHeap = new MinHeap(k);
        
        // 處理每個商品
        for (Product product : products) {
            if (!minHeap.isFull()) {
                // 堆未滿，直接插入
                minHeap.insert(product);
            } else {
                // 堆已滿，比較當前商品與堆頂
                Product minProduct = minHeap.peek();
                if (shouldReplace(product, minProduct)) {
                    minHeap.replaceTop(product);
                }
            }
        }
        
        // 將堆中元素取出並排序（高到低）
        List<Product> result = minHeap.getAllProducts();
        result.sort((p1, p2) -> {
            if (p1.quantity != p2.quantity) {
                return Integer.compare(p2.quantity, p1.quantity);  // 銷量降序
            }
            return p1.name.compareTo(p2.name);  // 銷量相同時按字典序升序
        });
        
        return result;
    }
    
    /**
     * 判斷是否替換堆頂元素(當新商品銷量 > 堆頂，或銷量相同但字典序更大時，要替換)
     */
    private static boolean shouldReplace(Product newProduct, Product minProduct) {
        if (newProduct.quantity > minProduct.quantity) {
            return true;
        }
        if (newProduct.quantity == minProduct.quantity) {
            return newProduct.name.compareTo(minProduct.name) > 0;
        }
        return false;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 輸入 n 和 K
        System.out.print("輸入商品數量 n 和 Top-K 熱銷的 K (n k): ");
        String[] firstLine = scanner.nextLine().trim().split("\\s+");
        int n = Integer.parseInt(firstLine[0]);
        int k = Integer.parseInt(firstLine[1]);
        
        // 商品資料
        List<Product> products = new ArrayList<>();
        System.out.println("請輸入 " + n + " 個商品 (商品名 銷量):");
        for (int i = 0; i < n; i++) {
            String[] parts = scanner.nextLine().trim().split("\\s+");
            String name = parts[0];
            int quantity = Integer.parseInt(parts[1]);
            products.add(new Product(name, quantity, i));
        }
        
        // 找出 Top-K
        List<Product> topK = findTopK(products, k);
        
        // 輸出
        System.out.println("\nTop-" + k + " 熱銷商品: ");
        for (Product product : topK) {
            System.out.println(product);
        }
        
        scanner.close();
    }
}