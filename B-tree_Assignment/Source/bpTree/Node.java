package bpTree;

import java.util.List;

public abstract class Node {
    protected int degree;
    protected List<Integer> keys;

    public int getSize() {
        return keys.size();
    }

    public int findLocation(int key) {
        if(getSize() == 0) {
            return 0;
        }

        if(key < keys.get(0)) {
            return 0;
        }
        else if(key > keys.get(getSize() - 1)) {
            return getSize();
        }

        int start = 0;
        int end = getSize() - 1;

        while(start <= end) {
            int mid = (start + end) / 2;

            if(key == keys.get(mid)) {
                return mid + 1;
            }

            if(key > keys.get(mid) && key < keys.get(mid + 1)) {
                return mid + 1;
            }
            else if(key > keys.get(mid)) {
                start = mid + 1;
            }
            else if(key < keys.get(mid)) {
                end = mid - 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }

    public boolean isFull() {
        return getSize() > degree - 1;
    };

    public boolean isUnderflow() {
        return getSize() < (degree - 1) / 2;
    }

    public boolean canBorrow() {
        return getSize() - 1 >= (degree - 1) / 2;
    }

    abstract int getValue(int key);

    abstract void rangeSearch(int startKey, int endKey);

    abstract Node insert(Node root, int key, int value);

    abstract Node delete(Node root, int key);

    abstract Node split();

    abstract void borrowFromLeft(Node parentNode, Node siblingNode, int loc);

    abstract void borrowFromRight(Node parentNode, Node siblingNode, int loc);

    abstract void merge(Node parentNode, Node siblingNode, int loc);
}