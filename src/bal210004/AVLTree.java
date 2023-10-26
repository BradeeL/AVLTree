
/*
 * Starter code for AVL Tree
 */

// replace package name with your netid
package bal210004;

import java.nio.file.attribute.AclEntryType;
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
        //Add failed
        if(!retValue){
            return false;
        }
        find(root, x);
        Entry<T> current;
        Entry<T> left;
        Entry<T> right;

        while (stack.peek() != null) {
            current = (Entry<T>) stack.pop();

            //Adjust Height
            int lHeight= getHeight(current.left);
            int rHeight= getHeight(current.right);
            current.height = Math.max(lHeight,rHeight) + 1;

            //Check for imbalances
            int childLHeight;
            int childRHeight;

            //L imbalance
            if (lHeight - rHeight > 1) {
                childLHeight=current.left.left==null?-1:((Entry<T>)current.left.left).height;
                childRHeight=current.left.right==null?-1:((Entry<T>)current.left.right).height;
                //LL Imbalance, Rotate right
                if(childLHeight>childRHeight){
                    LLRotate(current);
                }
                //LR Imbalance, Rotate left child to the left, then rotate right
                else {
                    LRRotate(current);
                }
            }

            //R Imbalance
            else if(rHeight-lHeight>1){
                childLHeight=current.right.left==null?-1:((Entry<T>)current.right.left).height;
                childRHeight=current.right.right==null?-1:((Entry<T>)current.right.right).height;
                //RR Imbalance, Rotate left
                if(childRHeight>childLHeight){
                    RRRotate(current);
                }
                //RL Imbalance, Rotate right child to the right, then rotate left.
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

        //Remove failed
        if(retValue==null){
            return null;
        }
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
                //RR rotate
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

    private void LLRotate(Entry<T> a){
        Entry<T> b=(Entry<T>) a.left;
        //Should root be updated?
        if (stack.peek() == null) {
            root = b;
        } else {
            //if not, update the parents child
            if (stack.peek().element.compareTo(a.element) > 0) {
                stack.peek().left = b;
            } else {
                stack.peek().right = b;
            }
        }
        a.left=b.right;
        b.right=a;

        a.height=Math.max(getHeight(a.right),getHeight(a.left))+1;
        b.height=Math.max(getHeight(b.left),getHeight(b.right))+1;
    }

    private void RRRotate(Entry<T> a){
        Entry<T> b=(Entry<T>) a.right;
        //Should root be updated?
        if(stack.peek()==null){
            root=b;
        } else {
            //if not, update the parents child
            if(stack.peek().element.compareTo(a.element)>0){
                stack.peek().left=b;
            } else {
                stack.peek().right=b;
            }
        }

        a.right=b.left;
        b.left=a;

        //Re-evaluate heights
        a.height=Math.max(getHeight(a.right),getHeight(a.left))+1;
        b.height=Math.max(getHeight(b.left),getHeight(b.right))+1;
    }

    private void LRRotate(Entry<T> a){
        Entry<T> b=(Entry<T>) a.left;
        Entry<T> c=(Entry<T>) b.right;

        //Should root be updated?
        if(stack.peek()==null){
            root=c;
        } else {
            //if not, update the parents child
            if(stack.peek().element.compareTo(a.element)>0){
                stack.peek().left=c;
            } else {
                stack.peek().right=c;
            }
        }

        b.right=c.left;
        a.left=c.right;
        c.left=b;
        c.right=a;

        a.height=Math.max(getHeight(a.left),getHeight(a.right))+1;
        b.height=Math.max(getHeight(b.left),getHeight(b.right))+1;
        c.height=Math.max(getHeight(c.left),getHeight(c.right))+1;
    }

    private void RLRotate(Entry<T> a){
        Entry<T> b=(Entry<T>) a.right;
        Entry<T> c=(Entry<T>) b.left;

        //Should root be updated?
        if(stack.peek()==null){
            root=c;
        } else {
            //if not, update the parents child
            if(stack.peek().element.compareTo(a.element)>0){
                stack.peek().left=c;
            } else {
                stack.peek().right=c;
            }
        }

        a.right=c.left;
        b.left=c.right;
        c.left=a;
        c.right=b;

        a.height=Math.max(getHeight(a.left),getHeight(a.right))+1;
        b.height=Math.max(getHeight(b.left),getHeight(b.right))+1;
        c.height=Math.max(getHeight(c.left),getHeight(c.right))+1;
    }


    private int getHeight(BinarySearchTree.Entry<T> entry){
        if(entry==null){
            return -1;
        }
        return ((Entry<T>) entry).height;
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

        int lHeight=-1;
        int rHeight=-1;

        T lmin=current.element,rmax=current.element;
        //verify left subtree
        if (current.left != null) {
            leftValidity = verify((Entry<T>) current.left);
            lmin=leftValidity.min;
            lHeight= leftValidity.height;
            if (!leftValidity.flag || leftValidity.max.compareTo(current.element) >= 0 || leftValidity.height != ((Entry<T>) current.left).height) {
                return new VerifyRetValues(false, leftValidity.height, leftValidity.min, leftValidity.max);
            }
        }
        //verify right subtree
        if (current.right != null) {
            rightValidity = verify((Entry<T>) current.right);
            rmax=rightValidity.max;
            rHeight= rightValidity.height;
            if (!rightValidity.flag || rightValidity.max.compareTo(current.element) <= 0 || rightValidity.height != ((Entry<T>) current.right).height) {
                return new VerifyRetValues(false, rightValidity.height, rightValidity.min, rightValidity.max);
            }
        }
        //verify height balance
        if (Math.abs((leftValidity == null ? -1 : leftValidity.height) - (rightValidity == null ? -1 : rightValidity.height)) > 1) {
            return new VerifyRetValues(false,
                    Math.max(lHeight,rHeight) + 1,
                    lmin,
                    rmax);
        }

        return new VerifyRetValues(true,
                Math.max(lHeight,rHeight) + 1,
                lmin,
                rmax);
    }
}

