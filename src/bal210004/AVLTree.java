
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
        boolean retValue = super.add(new Entry<>(x,null,null));
		find((Entry<T>)root,x);
		Entry<T> current;
		Entry<T> left;
		Entry<T> right;
		int leftHeight;
		int rightHeight;
		while(stack.peek()!=null){
			current=(Entry<T>) stack.pop();
			//Adjust Height
			if(current.left!=null){
				left=(Entry<T>) current.left;
				leftHeight=left.height;
			} else {
				leftHeight=-1;
			}
			if(current.right!=null){
				right=(Entry<T>) current.right;
				rightHeight=right.height;
			} else {
				rightHeight=-1;
			}
			current.height=Math.max(leftHeight,rightHeight)+1;

			//Check for imbalance, perform rotations if needed.

			//TODO: Add height recalculation after rotation

			//leftHeight >=0 prevent null dereference.
			if(leftHeight>=0) {
				//LL Rotation
				if (leftHeight - rightHeight > 1 && x.compareTo(current.left.element) < 0){
					//Check if root must be updated.
					if(stack.peek()==null){
						root=current.left;
					}
					Entry<T> temp=(Entry<T>) current.left.right;
					current.left.right=current;
					current.left=temp;
				}
				//TODO: LR Rotations
			}
			//TODO: RR and RL Rotations
		}
		return retValue;
    }

    //Optional. Complete for extra credit
    @Override
    public T remove(T x) {
        return super.remove(x);
    }

    /** TO DO
     *	verify if the tree is a valid AVL tree, that satisfies
     *	all conditions of BST, and the balancing conditions of AVL trees.
     *	In addition, do not trust the height value stored at the nodes, and
     *	heights of nodes have to be verified to be correct.  Make your code
     *  as efficient as possible. HINT: Look at the bottom-up solution to verify BST
     */
    boolean verify() {
        return false;
    }
}

