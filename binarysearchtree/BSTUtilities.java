/**
 * A collection of static methods for operating on binary search trees.
 * 
 * @author Emily Zhou
 * @version 12-03-2018
 */
public abstract class BSTUtilities
{
    /** 
     * Check if a given binary search tree contains a value.
     * 
     * performance: O(h) where h is the height of the tree
     * 
     * @precondition: t is a binary search tree in ascending order
     * @postcondition: returns true if t contains the value x;
     *            otherwise, returns false
     */
    public static boolean contains(
    TreeNode t, Comparable x, TreeDisplay display)
    {
        if (t == null) 
        {
            return false;
        }
        display.visit(t);
        int comp = x.compareTo(t.getValue());
        if (comp == 0) 
        {
            return true;
        }
        if (comp < 0) 
        {
            return contains(t.getLeft(), x, display);
        }
        return contains(t.getRight(), x, display);
    }

    /**
     * Insert a value to a binary search tree and ignore if duplicate value.
     * 
     * performance: O(h) where h is the height of the tree
     * 
     * @precondition:  t is a binary search tree in ascending order
     * @postcondition: if t is empty, returns a new tree containing x;
     *              otherwise, returns t, with x having been inserted
     *              at the appropriate position to maintain the binary
     *              search tree property; x is ignored if it is a
     *              duplicate of an element already in t; only one new
     *              TreeNode is created in the course of the traversal
     */
    public static TreeNode insert(TreeNode t, Comparable x, TreeDisplay display)
    {
        TreeNode node = null;
        if (t == null)
        {
            node = new TreeNode(x);
            display.visit(node);
            return node;
        }
        display.visit(t);
        int comp = x.compareTo(t.getValue());
        if (comp == 0)
        {
            return t;
        }
        if (comp < 0)
        {
            if (t.getLeft() == null) 
            {
                t.setLeft(new TreeNode(x));
                return t;
            }
            insert(t.getLeft(), x, display);
            return t;
        }
        if (t.getRight() == null)
        {
            t.setRight(new TreeNode(x));
            return t;
        }
        insert(t.getRight(), x, display);
        return t;
    }

    /**
     * Delete a value from a binary search tree.
     * 
     * @precondition:  t is a binary search tree in ascending order
     * @postcondition: returns a pointer to a binary search tree,
     *               in which the value at node t has been deleted
     *               (and no new TreeNodes have been created)
     */
    private static TreeNode deleteNode(TreeNode t, TreeDisplay display)
    {
        display.visit(t);
        if (t.getRight() == null && t.getLeft() == null)
        {
            return null;
        }
        if (t.getRight() == null) 
        {
            t.setValue(t.getLeft().getValue());
            t.setLeft(t.getLeft().getLeft());
            t.setRight(t.getLeft().getRight());
            return t;
        }
        if (t.getLeft() == null)
        {
            t.setValue(t.getRight().getValue());
            t.setLeft(t.getRight().getLeft());
            t.setRight(t.getRight().getRight());
            return t;
        }
        TreeNode node = t.getLeft();
        TreeNode leftChildParent = t;
        TreeNode rightChildParent = null;
        while (node.getRight() != null)
        {
            rightChildParent = node;
            leftChildParent = null;
            node = node.getRight();
        }
        if (leftChildParent != null)
        {
            leftChildParent.setLeft(null);
            t.setValue(node.getValue());
        }
        else if (rightChildParent != null)
        {
            rightChildParent.setRight(null);
            t.setValue(node.getValue());
        }
        return t;
    }

    /**
     * Delete a value from a binary search tree.
     * 
     * @precondition: t is a binary search tree in ascending order
     * @postcondition: returns a pointer to a binary search tree,
     *               in which the value x has been deleted (if present)
     *               (and no new TreeNodes have been created)
     */
    public static TreeNode delete(TreeNode t, Comparable x, TreeDisplay display)
    {
        if (!contains(t, x, display)) 
        {
            return t;
        }
        TreeNode node = t;
        TreeNode leftChildParent = null;
        TreeNode rightChildParent = null;
        int comp = x.compareTo(node.getValue());
        while (comp != 0)
        {
            display.visit(node);
            if (comp < 0)
            {
                leftChildParent = node;
                rightChildParent = null;
                node = node.getLeft();
            }
            else
            {
                rightChildParent = node;
                leftChildParent = null;
                node = node.getRight();
            }
            comp = x.compareTo(node.getValue());
        }
        TreeNode subtreeRoot = deleteNode(node, display);
        if (leftChildParent != null)
        {
            leftChildParent.setLeft(subtreeRoot);
        }
        else if (rightChildParent != null)
        {
            rightChildParent.setRight(subtreeRoot);
        }
        else if (subtreeRoot != null)
        {
            return subtreeRoot;
        }
        else 
        {
            return null;
        }
        return t;
    }
}