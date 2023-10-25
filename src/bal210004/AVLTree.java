
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
        find(root, x);
        Entry<T> current;
        Entry<T> left;
        Entry<T> right;

        while (stack.peek() != null) {
            current = (Entry<T>) stack.pop();

            //Adjust Height
            int lHeight= current.left==null?-1: ((Entry<T>) current.left).height;
            int rHeight= current.right==null?-1:((Entry<T>)current.right).height;
            current.height = Math.max(lHeight,rHeight) + 1;

            //Check for imbalances
            int childLHeight;
            int childRHeight;

            //L imbalance
            if (lHeight - rHeight > 1) {
                childLHeight=current.left.left==null?-1:((Entry<T>)current.left.left).height;
                childRHeight=current.left.right==null?-1:((Entry<T>)current.left.right).height;
                //LL rotation
                if(childLHeight>childRHeight){
                    LLRotate(current);
                }
                //LR Rotation
                else {
                    LRRotate(current);
                }
            }

            //R Imbalance
            else if(rHeight-lHeight>1){
                childLHeight=current.right.left==null?-1:((Entry<T>)current.right.left).height;
                childRHeight=current.right.right==null?-1:((Entry<T>)current.right.right).height;
                //RR Rotation
                if(childRHeight>childLHeight){
                    RRRotate(current);
                }
                //RL Rotation
                else{
                    RLRotate(current);
                }
            }
        }
        return retValue;
    }

    //Optional. Complete for extra credit
    //TODO: I was unable to get this working :(
    @Override
    public T remove(T x) {
        T retValue = super.remove(x);
        Entry<T> current;

        while (stack.peek() != null) {
            current = (Entry<T>) stack.pop();

            //Adjust Height
            int lHeight= current.left==null?-1: ((Entry<T>) current.left).height;
            int rHeight= current.right==null?-1:((Entry<T>) current.right).height;
            current.height = Math.max(lHeight,rHeight) + 1;

            //Check for imbalances
            int childLHeight;
            int childRHeight;

            //L imbalance
            if (lHeight - rHeight > 1) {
                childLHeight=current.left.left==null?-1:((Entry<T>)current.left.left).height;
                childRHeight=current.left.right==null?-1:((Entry<T>)current.left.right).height;
                //LL Rotation
                if(childLHeight>=childRHeight){
                    LLRotate(current);
                }
                //LR Rotation
                else {
                    LRRotate(current);
                }
            }
            //R Imbalance
            else if(rHeight-lHeight>1){
                childLHeight=current.right.left==null?-1:((Entry<T>)current.right.left).height;
                childRHeight=current.right.right==null?-1:((Entry<T>)current.right.right).height;
                //RR Rotation
                if(childRHeight>=childLHeight){
                    RRRotate(current);
                }
                //RL Rotation
                else{
                    RLRotate(current);
                }

            }



        }
        return retValue;
    }

    private void LLRotate(Entry<T> a){
        if (stack.peek() == null) {
            root = a.left;
        } else {
            //if not, update the parents child
            if (stack.peek().element.compareTo(a.element) > 0) {
                stack.peek().left = a.left;
            } else {
                stack.peek().right = a.left;
            }
        }
        //Height Reevaluation
        a.height = Math.max(a.left.right == null ? -1 : ((Entry<T>) a.left.right).height, a.right == null ? -1 : ((Entry<T>)a.right).height) + 1;
        ((Entry<T>)a.left).height=Math.max(a.height,a.left.left==null?-1:((Entry<T>)a.left.left).height) + 1;

        Entry<T> temp = (Entry<T>) a.left.right;
        a.left.right = a;
        a.left = temp;
    }

    private void LRRotate(Entry<T> a){
        //Check if root must be updated.
        if (stack.peek() == null) {
            root = a.left.right;
        } else {
            //if not, update the parents child
            if (stack.peek().element.compareTo(a.element) > 0) {
                stack.peek().left = a.left.right;
            } else {
                stack.peek().right = a.left.right;
            }
        }

        //Height reevaluation
        a.height = Math.max(a.right == null ? -1 : ((Entry<T>)a.right).height,
                a.left.right.right == null ? -1 : ((Entry<T>) a.left.right.right).height) + 1;
        ((Entry<T>) a.left).height = Math.max(a.left.left == null ? -1 : ((Entry<T>) a.left.left).height,
                a.left.right.left == null ? -1 : ((Entry<T>) a.left.right.left).height) + 1;
        ((Entry<T>) a.left.right).height = Math.max(a.height, ((Entry<T>) a.left).height) + 1;


        //Perform the rotation
        Entry<T> tempLeft = (Entry<T>) a.left.right.left;
        Entry<T> tempRight = (Entry<T>) a.left.right.right;
        a.left.right.left = a.left;
        a.left.right.right = a;
        a.left.right = tempLeft;
        a.left = tempRight;
    }

    private void RRRotate(Entry<T> a){
        //check if root should be updated
        if (stack.peek() == null) {
            root = a.right;
        } else {
            //if not update parents child
            if (stack.peek().element.compareTo(a.element) > 0) {
                stack.peek().left = a.right;
            } else {
                stack.peek().right = a.right;
            }
        }

        //Height Reevaluation
        a.height = Math.max(a.right.left == null ? -1 : ((Entry<T>) a.right.left).height,
                a.left == null ? -1 : ((Entry<T>)a.left).height) + 1;
        ((Entry<T>)a.right).height=Math.max(a.height,a.right.right==null?-1:
                ((Entry<T>)a.right.right).height)+1;

        Entry<T> temp = (Entry<T>) a.right.left;
        a.right.left = a;
        a.right = temp;
    }

    private void RLRotate(Entry<T> a){
        //check if root should be updated
        if (stack.peek() == null) {
            root = a.right.left;
        } else {
            //if not update parents child
            if (stack.peek().element.compareTo(a.element) > 0) {
                stack.peek().left = a.right.left;
            } else {
                stack.peek().right = a.right.left;
            }
        }
        //Height Reevaluation
        a.height = Math.max(a.left == null ? -1 : ((Entry<T>)a.left).height,
                a.right.left.left == null ? -1 : ((Entry<T>) a.right.left.left).height) + 1;
        ((Entry<T>) a.right).height = Math.max(a.right.right == null ? -1 : ((Entry<T>) a.right.right).height,
                a.right.left.right == null ? -1 : ((Entry<T>) a.right.left.right).height) + 1;
        ((Entry<T>) a.right.left).height = Math.max(a.height, ((Entry<T>) a.right).height) + 1;

        Entry<T> tempLeft = (Entry<T>) a.right.left.left;
        Entry<T> tempRight = (Entry<T>) a.right.left.right;
        a.right.left.right = a.right;
        a.right.left.left = a;
        a.right.left = tempRight;
        a.right = tempLeft;
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

