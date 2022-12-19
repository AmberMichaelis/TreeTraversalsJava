package org.example;

import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The new data should become a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Should be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to add to the tree.
     * @throws java.lang.IllegalArgumentException If data is null.
     */
    public void add(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            addHelper(root, data);
        }
    }

    private void addHelper(BSTNode<T> node, T data) {
            if (data.compareTo(node.getData()) > 0) {
                if (node.getRight() == null) {
                    node.setRight(new BSTNode<>(data));
                    size++;
                } else {
                    addHelper(node.getRight(), data);
                }
            } else if (data.compareTo(node.getData()) < 0) {
                if (node.getLeft() == null) {
                    node.setLeft(new BSTNode<>(data));
                    size++;
                } else {
                    addHelper(node.getLeft(), data);
                }
            }
        }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the SUCCESSOR to
     * replace the data. You should use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do NOT return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data The data to remove.
     * @return The data that was removed.
     * @throws java.lang.IllegalArgumentException If data is null.
     * @throws java.util.NoSuchElementException   If the data is not in the tree.
     */
    public T remove(T data) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null");
        }
        BSTNode<T> removed = new BSTNode<>(null);
        this.root = removeHelper(this.root, data, removed);
        return removed.getData();
    }

    private BSTNode<T> removeHelper(BSTNode<T> node, T data, BSTNode<T> removed) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found");
        } else {
            int value = data.compareTo(node.getData());
            if (value > 0) {
                node.setRight(removeHelper(node.getRight(), data, removed));
            } else if (value < 0) {
                node.setLeft(removeHelper(node.getLeft(), data, removed));
            } else {
                removed.setData(node.getData());
                size--;
                if (node.getRight() == null) {
                    return node.getLeft();
                } else if (node.getLeft() == null) {
                    return node.getRight();
                } else {
                    BSTNode<T> child = new BSTNode<>(null);
                    node.setLeft(successorHelper(node.getLeft(), child));
                    node.setData(child.getData());
                }
            }
        }
        return node;
    }

    private BSTNode<T> successorHelper(BSTNode<T> node, BSTNode<T> child) {
        if (node.getLeft() ==  null) {
            child.setData(node.getData());
            return node.getRight();
        }
        node.setLeft(successorHelper(node.getLeft(), child));
        return node;
    }

    public T get(T data) {
        if (data == null) {
            throw new NoSuchElementException("Data cannot be found");
        }
        return getHelper(this.root, data);
    }

    private T getHelper(BSTNode<T> current, T data) {
        if (current == null) {
            throw new NoSuchElementException("Data cannot be found");
        }
        if (data.equals(current.getData())) {
                return current.getData();
        } else if (data.compareTo(current.getData()) < 0) {
                return getHelper(current.getLeft(), data);
        } else {
                return getHelper(current.getRight(), data);
        }
    }

    private boolean contains(T data) {
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private T findSmallestValue(BSTNode<T> node) {
        return node.getLeft() == null ? node.getData() : findSmallestValue(node.getLeft());
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
