
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
                        root = current.left;
                    } else {
                        //if not, update the parents child
                        stack.peek().left = current.left;
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
                            stack.peek().left = current.left.right;
                        } else {
                            stack.peek().right = current.left.right;
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
                        root = current.right;
                    } else {
                        //if not update parents child
                        stack.peek().right = current.right;
                    }
                    Entry<T> temp = (Entry<T>) current.right.left;
                    current.right.left = current;
                    current.right = temp;

                    //Height Adjustment
                    current.height = Math.max(temp == null ? -1 : temp.height, left == null ? -1 : left.height) + 1;
                }

                //TODO: RL Rotations
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
    boolean verify() {
        return false;
    }
}

