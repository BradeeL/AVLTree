
/*
 * Starter code for AVL Tree
 */

// replace package name with your netid
package bal210004;

import java.util.ArrayDeque;
import java.util.Comparator;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> extends BinarySearchTree.Entry<T> {
        int height;

        Entry(T x, Entry<T> left, Entry<T> right) {
            super(x, left, right);
            height = 0;
        }
    }

    AVLTree() {
        super();
    }

    // TO DO
    @Override
    public boolean add(T x) {
        boolean retValue = super.add(new Entry<>(x, null, null));
        find((Entry<T>) root, x);
        Entry<T> current;
        Entry<T> left;
        Entry<T> right;

        while (stack.peek() != null) {
            current = (Entry<T>) stack.pop();
            //Adjust Height
            left = (Entry<T>) current.left;
            right = (Entry<T>) current.right;
            current.height = Math.max(left == null ? -1 : left.height, right == null ? -1 : right.height) + 1;

            //Check for imbalance, perform rotations if needed.

            //leftHeight >=0 prevent null dereference.
            if (left != null) {
                //LL Rotation
                if (left.height - (right == null ? -1 : right.height) > 1 && x.compareTo(current.left.element) < 0) {
                    //Check if root must be updated.
                    if (stack.peek() == null) {
                        root = left;
                    } else {
                        //if not, update the parents child
                        //TODO: fix this, parents left child not necessarily the one being rotated. Same for all rotations
                        if (stack.peek().element.compareTo(current.element) > 0) {
                            stack.peek().left = left;
                        } else {
                            stack.peek().right = left;
                        }
                    }
                    Entry<T> temp = (Entry<T>) current.left.right;
                    current.left.right = current;
                    current.left = temp;

                    //Height adjustment
                    //In LL Rotation, only current's height changes
                    current.height = Math.max(temp == null ? -1 : temp.height, right == null ? -1 : right.height) + 1;
                }

                //LR Rotation
                else if (left.height - (right == null ? -1 : right.height) > 1 && x.compareTo(current.left.element) > 0) {
                    //Check if root must be updated.
                    if (stack.peek() == null) {
                        root = current.left.right;
                    } else {
                        //if not, update the parents child
                        if (stack.peek().element.compareTo(current.element) > 0) {
                            stack.peek().left = left.right;
                        } else {
                            stack.peek().right = left.right;
                        }
                    }
                    Entry<T> tempLeft = (Entry<T>) current.left.right.left;
                    Entry<T> tempRight = (Entry<T>) current.left.right.right;
                    current.left.right.left = current.left;
                    current.left.right.right = current;
                    current.left.right = tempLeft;
                    current.left = tempRight;

                    //TODO: Height adjustments


                }
            }

            if (right != null) {
                //RR Rotation
                if (right.height - (left == null ? -1 : left.height) > 1 && x.compareTo(right.element) > 0) {
                    //check if root should be updated
                    if (stack.peek() == null) {
                        root = right;
                    } else {
                        //if not update parents child
                        if (stack.peek().element.compareTo(current.element) > 0) {
                            stack.peek().left = right;
                        } else {
                            stack.peek().right = right;
                        }
                    }
                    Entry<T> temp = (Entry<T>) current.right.left;
                    current.right.left = current;
                    current.right = temp;

                    //Height Adjustment
                    current.height = Math.max(temp == null ? -1 : temp.height, left == null ? -1 : left.height) + 1;
                }

                //TODO: RL Rotations
                if (right.height - (left == null ? -1 : left.height) > 1 && x.compareTo(right.element) < 0) {
                    //check if root should be updated
                    if (stack.peek() == null) {
                        root = right;
                    } else {
                        //if not update parents child
                        if (stack.peek().element.compareTo(current.element) > 0) {
                            stack.peek().left = right.left;
                        } else {
                            stack.peek().right = right.left;
                        }
                    }
                }
            }

        }
        return retValue;
    }

    //Optional. Complete for extra credit
    @Override
    public T remove(T x) {
        return super.remove(x);
    }

    /**
     * TO DO
     * verify if the tree is a valid AVL tree, that satisfies
     * all conditions of BST, and the balancing conditions of AVL trees.
     * In addition, do not trust the height value stored at the nodes, and
     * heights of nodes have to be verified to be correct.  Make your code
     * as efficient as possible. HINT: Look at the bottom-up solution to verify BST
     */

    //Struct to return multiple values in verify function
    private class VerifyRetValues {
        boolean flag;
        int height;
        T min;
        T max;

        public VerifyRetValues(boolean flag, int height, T min, T max) {
            this.flag = flag;
            this.height = height;
            this.min = min;
            this.max = max;
        }
    }

    boolean verify() {
        if (root == null) return true;
        VerifyRetValues validity = verify((Entry<T>) root);
        return validity.flag && validity.height == ((Entry<T>) root).height;
    }

    //implementation of pseudocode done in assignment 6
    VerifyRetValues verify(Entry<T> current) {
        VerifyRetValues leftValidity = null;
        VerifyRetValues rightValidity = null;
        //verify left subtree
        if (current.left != null) {
            leftValidity = verify((Entry<T>) current.left);
            if (!leftValidity.flag || leftValidity.max.compareTo(current.element) >= 0 || leftValidity.height != ((Entry<T>) current.left).height) {
                return new VerifyRetValues(false, leftValidity.height, leftValidity.min, leftValidity.max);
            }
        }
        //verify right subtree
        if (current.right != null) {
            rightValidity = verify((Entry<T>) current.right);
            if (!rightValidity.flag || rightValidity.max.compareTo(current.element) <= 0 || rightValidity.height != ((Entry<T>) current.right).height) {
                return new VerifyRetValues(false, rightValidity.height, rightValidity.min, rightValidity.max);
            }
        }
        //verify height balance
        if (Math.abs((leftValidity == null ? -1 : leftValidity.height) - (rightValidity == null ? -1 : rightValidity.height)) > 1) {
            return new VerifyRetValues(false,
                    Math.max(leftValidity == null ? -1 : leftValidity.height,
                            rightValidity == null ? -1 : rightValidity.height) + 1,
                    current.element,
                    current.element);
        }

        return new VerifyRetValues(true,
                Math.max(leftValidity == null ? -1 : leftValidity.height,
                        rightValidity == null ? -1 : rightValidity.height) + 1,
                current.element,
                current.element);
    }
}

