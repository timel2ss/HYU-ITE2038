package bpTree;

import java.util.*;

public class LeafNode extends Node {
    private List<Integer> values;
    private LeafNode right;

    public LeafNode(int degree) {
        this.degree = degree;
        keys = new ArrayList<>();
        values = new ArrayList<>();
        right = null;
    }

    public LeafNode(int degree, LeafNode prev, List<Integer> keys, List<Integer> values) {
        this(degree);
        this.keys.addAll(keys);
        this.values.addAll(values);
        if(prev != null) {
            this.right = prev.right;
        }
    }

    public void setRight(LeafNode right) {
        this.right = right;
    }

    @Override
    public int getValue(int key) {
        int idx = keys.indexOf(key);
        if(idx != -1) {
            return values.get(idx);
        }
        return idx;
    }

    @Override
    void rangeSearch(int startKey, int endKey) {
        if(keys.get(0) > endKey) {
            return;
        }
        for(int key : keys) {
            if(startKey <= key && key <= endKey) {
                System.out.println(key + "," + values.get(keys.indexOf(key)));
            }
        }
        if(right != null) {
            right.rangeSearch(startKey, endKey);
        }
    }

    @Override
    public Node insert(Node root, int key, int value) {
        int loc = keys.indexOf(key);
        if(loc != -1) {
            System.out.println(key + " already exists");
        }
        else {
            int location = findLocation(key);
            keys.add(location, key);
            values.add(location, value);
            if (root.isFull()) {
                LeafNode splitNode = (LeafNode) split();
                NonLeafNode parent = new NonLeafNode(degree);
                parent.keys.add(splitNode.keys.get(0));
                parent.getChildNode().add(this);
                parent.getChildNode().add(splitNode);
                return parent;
            }
        }
        return null;
    }

    @Override
    public Node delete(Node root, int key) {
        int location = keys.indexOf(key);
        if(location != -1) {
            keys.remove(location);
            values.remove(location);
        }
        else {
            System.out.println(key + " does not exist");
        }
        return this;
    }

    @Override
    public Node split() {
        int start = (degree + 1) / 2;
        int end = degree;

        LeafNode splitNode = new LeafNode(degree, this, keys.subList(start, end), values.subList(start, end));
        right = splitNode;
        keys.removeAll(splitNode.keys);
        values.removeAll(splitNode.values);
        return splitNode;
    }

    @Override
    void borrowFromLeft(Node parentNode, Node siblingNode, int loc) {
        NonLeafNode parent = (NonLeafNode)parentNode;
        LeafNode sibling = (LeafNode)siblingNode;
        keys.add(0, sibling.keys.get(sibling.getSize() - 1));
        values.add(0, sibling.values.get(sibling.values.size() - 1));
        sibling.keys.remove(sibling.getSize() - 1);
        sibling.values.remove(sibling.values.size() - 1);
        parent.keys.set(loc, keys.get(0));
    }

    @Override
    void borrowFromRight(Node parentNode, Node siblingNode, int loc) {
        NonLeafNode parent = (NonLeafNode)parentNode;
        LeafNode sibling = (LeafNode)siblingNode;
        keys.add(sibling.keys.get(0));
        values.add(sibling.values.get(0));
        sibling.keys.remove(0);
        sibling.values.remove(0);
        parent.keys.set(loc, sibling.keys.get(0));
    }

    @Override
    public void merge(Node parentNode, Node siblingNode, int loc) {
        NonLeafNode parent = (NonLeafNode)parentNode;
        LeafNode sibling = (LeafNode)siblingNode;
        keys.addAll(sibling.keys);
        values.addAll(sibling.values);
        right = sibling.right;
        parent.keys.remove(loc);
        parent.getChildNode().remove(loc + 1);
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("LeafNode: ");
        info.append(keys.get(0));
        for(int i = 1; i < getSize(); i++) {
            info.append(",").append(keys.get(i));
        }
        info.append(" ");
        info.append(values.get(0));
        for (int j = 1; j < values.size(); j++) {
            info.append(",").append(values.get(j));
        }
        return info.toString();
    }
}