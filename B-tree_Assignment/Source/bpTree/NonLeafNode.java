package bpTree;

import java.util.ArrayList;
import java.util.List;

public class NonLeafNode extends Node {
    private List<Node> childNode;

    public NonLeafNode(int degree) {
        this.degree = degree;
        keys = new ArrayList<>();
        childNode = new ArrayList<>();
    }

    public NonLeafNode(int degree, List<Integer> keys, List<Node> children) {
        this(degree);
        this.keys.addAll(keys);
        this.childNode.addAll(children);
    }

    public List<Node> getChildNode() {
        return childNode;
    }

    public Node getChildLeftSibling(int location) {
        if(location > 0 ) {
            return childNode.get(location - 1);
        }
        return null;
    }

    public Node getChildRightSibling(int location) {
        if(location < getSize()) {
            return childNode.get(location + 1);
        }
        return null;
    }

    @Override
    public int getValue(int key) {
        System.out.print(keys.get(0));
        for(int i = 1; i < getSize(); i++) {
            System.out.print("," + keys.get(i));
        }
        System.out.println();

        int location = findLocation(key);
        Node temp = childNode.get(location);
        if(temp != null) {
            return temp.getValue(key);
        }
        return -1;
    }

    @Override
    void rangeSearch(int startKey, int endKey) {
        int location = findLocation(startKey);
        Node temp = childNode.get(location);
        if(temp != null) {
            temp.rangeSearch(startKey, endKey);
        }
    }

    @Override
    public Node insert(Node root, int key, int value) {
        int location = findLocation(key);
        Node child = childNode.get(location);
        child.insert(root, key, value);
        if(child.isFull()) {
            int temp = child.keys.get(child.getSize() / 2);
            Node splitNode = child.split();
            int loc = findLocation(splitNode.keys.get(0));
            if(child instanceof LeafNode) {
                keys.add(loc, splitNode.keys.get(0));
            }
            else if(child instanceof NonLeafNode) {
                keys.add(loc, temp);
            }
            childNode.add(loc + 1, splitNode);
        }
        if(root.isFull()) {
            int temp = root.keys.get(root.getSize() / 2);
            Node parentSplit = split();
            NonLeafNode parent = new NonLeafNode(degree);
            parent.keys.add(temp);
            parent.getChildNode().add(this);
            parent.getChildNode().add(parentSplit);
            return parent;
        }
        return null;
    }

    @Override
    public Node delete(Node root, int key) {
        int location = findLocation(key);
        Node child = childNode.get(location);
        child.delete(root, key);
        if(child instanceof LeafNode) {
            if(child.getSize() != 0) {
                int loc = keys.indexOf(key);
                if (loc != -1) {
                    keys.set(loc, child.keys.get(0));
                }
            }
        }
        if(child.isUnderflow()) {
            Node leftSibling = getChildLeftSibling(location);
            Node rightSibling = getChildRightSibling(location);
            Node left = leftSibling != null ? leftSibling : child;
            Node right = leftSibling != null ? child : rightSibling;
            if(leftSibling != null) {
                location -= 1;
            }
            if (right.canBorrow()) {
                left.borrowFromRight(this, right, location);
            }
            else if(left.canBorrow()){
                right.borrowFromLeft(this, left, location);
            }
            else {
                left.merge(this, right, location);
            }
        }
        if(root instanceof NonLeafNode && root.getSize() == 0) {
            return ((NonLeafNode)root).getChildNode().get(0);
        }
        return null;
    }

    @Override
    public Node split() {
        int start = degree / 2;
        int end = degree;

        NonLeafNode splitNode = new NonLeafNode(degree, keys.subList(start, end), childNode.subList(start+1, end + 1));

        keys.removeAll(splitNode.keys);
        splitNode.keys.remove(0);
        childNode.removeAll(splitNode.childNode);
        return splitNode;
    }

    @Override
    void borrowFromLeft(Node parentNode, Node siblingNode, int loc) {
        NonLeafNode parent = (NonLeafNode)parentNode;
        NonLeafNode sibling = (NonLeafNode)siblingNode;
        keys.add(0, parent.keys.get(loc));
        childNode.add(0, sibling.childNode.get(sibling.getSize()));
        parent.keys.set(loc, sibling.keys.get(sibling.getSize() - 1));
        sibling.keys.remove(sibling.getSize() - 1);
        sibling.childNode.remove(sibling.childNode.size() - 1);
    }

    @Override
    void borrowFromRight(Node parentNode, Node siblingNode, int loc) {
        NonLeafNode parent = (NonLeafNode)parentNode;
        NonLeafNode sibling = (NonLeafNode)siblingNode;
        keys.add(parent.keys.get(loc));
        childNode.add(sibling.childNode.get(0));
        parent.keys.set(loc, sibling.keys.get(0));
        sibling.keys.remove(0);
        sibling.childNode.remove(0);
    }

    @Override
    public void merge(Node parentNode, Node siblingNode, int loc) {
        NonLeafNode parent = (NonLeafNode)parentNode;
        NonLeafNode sibling = (NonLeafNode)siblingNode;
        keys.add(parent.keys.get(loc));
        keys.addAll(sibling.keys);
        childNode.addAll(sibling.getChildNode());
        parent.keys.remove(loc);
        parent.getChildNode().remove(loc + 1);
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("NonLeafNode: ");
        info.append(keys.get(0).toString());
        for(int i = 1; i < getSize(); i++) {
            info.append(",").append(keys.get(i));
        }
        return info.toString();
    }
}