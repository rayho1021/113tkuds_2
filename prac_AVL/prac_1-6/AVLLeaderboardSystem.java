/*
練習 5：AVL 樹應用 - 排行榜系統
設計一個遊戲排行榜系統，支援：
1.添加玩家分數
2.更新玩家分數
3.查詢玩家排名
4.查詢前 K 名玩家

- 擴展節點儲存額外資訊
- 維護子樹大小
- 實作 select 和 rank 操作
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AVLLeaderboardSystem {

    // 節點 (儲存玩家資訊和子樹大小)
    static class Node {
        int score;
        String playerId;
        int height;
        int size; // 子樹大小
        Node left;
        Node right;

        public Node(int score, String playerId) {
            this.score = score;
            this.playerId = playerId;
            this.height = 1;
            this.size = 1;
            this.left = null;
            this.right = null;
        }
    }

    // AVL 樹
    static class AVLTree {
        Node root;

        // 取得節點高度
        private int getHeight(Node node) {
            return (node == null) ? 0 : node.height;
        }

        // 取得子樹大小
        private int getSize(Node node) {
            return (node == null) ? 0 : node.size;
        }

        // 更新節點高度與大小
        private void updateInfo(Node node) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
            node.size = 1 + getSize(node.left) + getSize(node.right);
        }

        // 取得平衡因子
        private int getBalance(Node node) {
            return (node == null) ? 0 : getHeight(node.left) - getHeight(node.right);
        }

        // 右旋
        private Node rightRotate(Node y) {
            Node x = y.left;
            Node T2 = x.right;
            x.right = y;
            y.left = T2;
            updateInfo(y);
            updateInfo(x);
            return x;
        }

        // 左旋
        private Node leftRotate(Node x) {
            Node y = x.right;
            Node T2 = y.left;
            y.left = x;
            x.right = T2;
            updateInfo(x);
            updateInfo(y);
            return y;
        }
        
        // 1. 添加玩家分數 (或更新)
        public void insertOrUpdate(int score, String playerId) {
            this.root = insertOrUpdateNode(this.root, score, playerId);
        }
        
        private Node insertOrUpdateNode(Node node, int score, String playerId) {
            if (node == null) {
                return new Node(score, playerId);
            }
            // 如果分數相同，可以根據玩家 ID 排序或直接更新
            if (score == node.score) {
                 // 選擇更新玩家 ID 或忽略
                 return node;
            }
            if (score < node.score) {
                node.left = insertOrUpdateNode(node.left, score, playerId);
            } else {
                node.right = insertOrUpdateNode(node.right, score, playerId);
            }
            
            updateInfo(node);
            int balance = getBalance(node);
            
            if (balance > 1 && score < node.left.score) return rightRotate(node);
            if (balance < -1 && score > node.right.score) return leftRotate(node);
            if (balance > 1 && score > node.left.score) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
            if (balance < -1 && score < node.right.score) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
            return node;
        }

        // 3. 查詢玩家排名
        public int getRank(int score) {
            return getRankRecursive(this.root, score);
        }

        private int getRankRecursive(Node node, int score) {
            if (node == null) {
                return 0;
            }
            if (score == node.score) {
                // 排名為右子樹的節點數 + 1
                return 1 + getSize(node.right);
            }
            if (score < node.score) {
                // 在右子樹中找，排名為右子樹的節點數 + 1 + 在左子樹中的排名
                return 1 + getSize(node.right) + getRankRecursive(node.left, score);
            } else { // score > node.score
                // 在左子樹中找
                return getRankRecursive(node.right, score);
            }
        }
        
        // 4. 查詢前 K 名玩家
        public List<Node> getTopK(int k) {
            List<Node> topPlayers = new ArrayList<>();
            getTopKRecursive(this.root, k, topPlayers);
            // 由於樹是從低分到高分排序，需要反轉
            Collections.reverse(topPlayers);
            return topPlayers;
        }

        private void getTopKRecursive(Node node, int k, List<Node> result) {
            if (node == null || k <= 0) {
                return;
            }

            // 遞迴處理右子樹 (高分)
            getTopKRecursive(node.right, k, result);

            // 如果還有名額，將當前節點加入
            if (result.size() < k) {
                result.add(node);
                // 遞迴處理左子樹 (低分)
                getTopKRecursive(node.left, k - 1 - getSize(node.right), result);
            }
        }
    }

    public static void main(String[] args) {
        AVLTree leaderboard = new AVLTree();

        // 添加玩家分數
        leaderboard.insertOrUpdate(100, "Alice");
        leaderboard.insertOrUpdate(150, "Bob");
        leaderboard.insertOrUpdate(80, "Charlie");
        leaderboard.insertOrUpdate(200, "David");
        leaderboard.insertOrUpdate(120, "Eve");
        leaderboard.insertOrUpdate(90, "Frank");

        System.out.println("--- 排行榜系統 ---");

        // 查詢玩家排名
        System.out.println("玩家 Bob (150分) 的排名是: " + leaderboard.getRank(150)); 
        System.out.println("玩家 Alice (100分) 的排名是: " + leaderboard.getRank(100)); 
        System.out.println("玩家 David (200分) 的排名是: " + leaderboard.getRank(200)); 
        System.out.println("玩家 Charlie (80分) 的排名是: " + leaderboard.getRank(80)); 
        System.out.println("------------------------------------");

        // 查詢前 K 名玩家
        int k = 3;
        List<Node> topKPlayers = leaderboard.getTopK(k);
        System.out.println("\n--- 前 " + k + " 名玩家 ---");
        for (int i = 0; i < topKPlayers.size(); i++) {
            Node player = topKPlayers.get(i);
            System.out.println("No." + (i + 1) + ": " + player.playerId + " (分數: " + player.score + ")");
        }
    }
}