/**
 * @author rbk, sa
 * Binary search tree (starter code)
 **/

// replace package name with your netid
package bal210004;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;
    // define stack
    ArrayDeque<Entry<T>> stack;

    public BinarySearchTree() {
        root = null;
        size = 0;
        stack = new ArrayDeque<>();
    }


    /**
     * TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        Entry<T> foundElement=find(root,x);
        return foundElement!=null && x.compareTo(foundElement.element) == 0;
    }


    /**
     * TO DO: Add x to tree.
     * If tree contains a node with same key, replace element by x.
     * Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        Entry<T> current = root;
        //Edge case: Tree is empty
        if (current == null) {
            root = new Entry<T>(x, null, null);
            size++;
            return true;
        }
        while (true) {
            if (x.compareTo(current.element) == 0) return false;//x==current.element, element already in tree
            if (x.compareTo(current.element) < 0) {//x<current.element, traverse left or add element
                if (current.left == null) {
                    current.left = new Entry<T>(x, null, null);
                    break;
                } else
                    current = current.left;
            } else { //x>current.element, traverse right or add element
                if (current.right == null) {
                    current.right = new Entry<T>(x, null, null);
                    break;
                } else
                    current = current.right;
            }
        }
        size++;
        return true;
    }

    /**
     * TO DO: Remove x from tree.
     * Return x if found, otherwise return null
     */
    public T remove(T x) {
        Entry<T> foundEntry = find(root,x);

        //CASE 0: Element is not in the tree
        if(foundEntry==null || foundEntry.element.compareTo(x)!=0) return null;

        //Store parent node
        Entry<T> parent = stack.peek();

        //CASE 1: Node is a leaf node
        if(foundEntry.left==null&&foundEntry.right==null){
            //CASE 1a: Node has no parent (Removing root)
            if(parent==null){
                root=null;
            }
            //CASE 1b: Node is a left child
            else if(x.compareTo(parent.element)<0){
                parent.left=null;
            }
            //CASE 1c: Node is a right child
            else if(x.compareTo(parent.element)>0){
                parent.right=null;
            }
            size--;
        }
        //CASE 2: Node has a left child
        else if(foundEntry.right==null){
            //CASE 2a: Node has no parent (Removing root)
            if(parent==null){
                root=root.left;
            }
            //CASE 2b: Node is a left child
            else if(x.compareTo(parent.element)<0){
                parent.left=foundEntry.left;
            }
            //CASE 2c: Node is a right child
            else if(x.compareTo(parent.element)>0){
                parent.right=foundEntry.left;
            }
            size--;
        }
        //CASE 3: Node has a right child
        else if(foundEntry.left==null){
            //CASE 3a: Node has no parent (Removing root)
            if(parent==null){
                root=root.right;
            }
            //CASE 3b: Node is a left child
            else if(x.compareTo(parent.element)<0){
                parent.left=foundEntry.right;
            }
            //CASE 3c: Node is a right child
            else if(x.compareTo(parent.element)>0){
                parent.right=foundEntry.right;
            }
            size--;
        }
        //CASE 4: Node has two children
        else{
            //Get the in-order successor to x
            //Attempt to find x in the right subtree of x
            //This will give us the value closest to X in the right subtree, AKA the successor
            Entry<T> successor=find(foundEntry.right,x);
            //Delete the successor
            remove(successor.element);
            //CASE 4a: Node has no parent (Removing root)
            if(parent==null){
                root.element=successor.element;
            }
            //CASE 4b: Node is a child (Does not matter left or right)
            else{
                foundEntry.element=successor.element;
            }
            //No need to decrement size since that is done in the recursive remove call.
        }

        return x;
    }

    /* attempt to find x,
    if x is in the tree, return x.
    else, return the value where the search failed
    stack contains the path to x
     */
    private Entry<T> find(Entry<T> startingNode, T x) {
        stack.clear();

        //Edge case: Tree is empty
        if (root == null) return null;


        Entry<T> current = startingNode;

        while (current != null) {
            if (x.compareTo(current.element) == 0) {
                stack.push(current);
                break;
            }
            else if (x.compareTo(current.element) < 0) {
                stack.push(current);
                current = current.left;
            }
            else if (x.compareTo(current.element) > 0) {
                stack.push(current);
                current = current.right;
            }
        }
        return stack.pop();
    }


// Start of Optional problems

    /**
     * Optional problem : Iterate elements in sorted order of keys
     * Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }

    // Optional problem
    public T min() {
        if (root == null) return null;

        Entry<T> current = root;
        while (current.left != null)
            current = current.left;

        return current.element;
    }

    public T max() {
        if (root == null) return null;

        Entry<T> current = root;
        while (current.right != null)
            current = current.right;

        return current.element;
    }

    // Optional problem.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

    // Optional: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Comparable[] arr = new Comparable[size];
        /* write code to place elements in array here */
        return arr;
    }

// End of Optional problems


    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<Long> bst = new BinarySearchTree<>();
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        // Initialize the timer
        Timer timer = new Timer();

        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if (bst.add(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (bst.remove(operand) != null) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains": {
                    operand = sc.nextInt();
                    if (bst.contains(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
            }
            bst.printTree();
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}




