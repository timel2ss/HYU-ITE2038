package bpTree;

import java.io.PrintWriter;
import java.util.*;

public class BpTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public BpTree(int degree) {
        root = new LeafNode(degree);
    }

    public int keySearch(int key) {
        return root.getValue(key);
    }

    public void rangeSearch(int start, int end) {
        root.rangeSearch(start, end);
    }

    public void insert(int key, int value) {
        Node temp = root.insert(root, key, value);
        if(temp != null) {
            root = temp;
        }
    }

    public void delete(int key) {
        Node temp = root.delete(root, key);
        if(temp != null) {
            root = temp;
        }
    }

    public void save(PrintWriter output, Node node) {
        if(node == null) {
            return;
        }
        if(node instanceof NonLeafNode) {
            NonLeafNode nonLeafNode = (NonLeafNode)node;
            for (int i = 0; i < nonLeafNode.getChildNode().size(); i++) {
                save(output, nonLeafNode.getChildNode().get(i));
            }
            output.print(nonLeafNode.toString() + "\n");
        }
        else if(node instanceof LeafNode) {
            output.print(node.toString() + "\n");
        }
    }

    public static BpTree load(Scanner input, int order) {
        BpTree bptree = new BpTree(order);
        Node root = null;

        List<List<Node>> count = new ArrayList<>();

        List<Integer> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();

        LeafNode prev = null;

        while(input.hasNextLine()) {
            String info = input.nextLine();

            String[] nodeInfo = info.split(" ");
            // NonLeafNode: key1,key2,key3...
            // LeafNode: key1,key2,key3... value1,value2,value3...
            if(nodeInfo[0].equals("NonLeafNode:")) {
                String[] keysInfo = nodeInfo[1].split(",");
                for(String key: keysInfo) {
                    keys.add(Integer.parseInt(key));
                }

                for(int i = 0; i < count.size(); i++) {
                    if(keysInfo.length + 1 == count.get(i).size()) {
                        NonLeafNode nonLeafNode = new NonLeafNode(order, keys, count.get(i));
                        count.get(i).clear();
                        if(count.size() == i + 1) {
                            count.add(new ArrayList<>());
                        }
                        count.get(i + 1).add(nonLeafNode);
                        root = nonLeafNode;
                        break;
                    }
                }
                keys.clear();
            }
            else if(nodeInfo[0].equals("LeafNode:")) {
                for(String key : nodeInfo[1].split(",")) {
                    keys.add(Integer.parseInt(key));
                }
                for (String value : nodeInfo[2].split(",")) {
                    values.add(Integer.parseInt(value));
                }
                LeafNode leafNode = new LeafNode(order, prev, keys, values);
                keys.clear();
                values.clear();
                if(count.size() == 0) {
                    count.add(new ArrayList<>());
                }
                count.get(0).add(leafNode);
                if(prev != null) {
                    prev.setRight(leafNode);
                }
                prev = leafNode;
            }
        }
        if(root == null) {
            if(count.size() != 0) {
                if (count.get(0).size() != 0) {
                    bptree.setRoot(count.get(0).get(0));
                }
            }
            else {
                bptree.setRoot(new LeafNode(order));
            }
        }
        else {
            bptree.setRoot(root);
        }
        return bptree;
    }
}