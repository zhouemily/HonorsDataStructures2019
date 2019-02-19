
/**
 * The TreeNode class.
 * 
 * @author Anu Datar 
 * @version 1.0
 */
public class TreeNode
{
    private Object value;
    private TreeNode left;
    private TreeNode right;

    /**
     * Constructor for TreeNode object.
     * 
     * @param initValue the value of the node
     */
    public TreeNode(Object initValue)
    { 
        this(initValue, null, null);
    }

    /**
     * Constructor for TreeNode object.
     * 
     * @param initValue the value of the node
     * @param initLeft the left node
     * @param initRight the right node
     */
    public TreeNode(Object initValue, TreeNode initLeft, TreeNode initRight)
    { 
        value = initValue; 
        left = initLeft; 
        right = initRight; 
    }

    /**
     * Get value of the node.
     * 
     * @return the value of the node
     */
    public Object getValue() { return value; }

    /**
     * Get left node.
     * 
     * @return the left node
     */
    public TreeNode getLeft() { return left; }

    /**
     * Get right node.
     * 
     * @return the right node
     */
    public TreeNode getRight() { return right; }

    /**
     * Set value of the node.
     * 
     * @param theNewValue the new value of the node
     */
    public void setValue(Object theNewValue) { value = theNewValue; }

    /**
     * Set left node.
     * 
     * @param theNewLeft the new left node
     */
    public void setLeft(TreeNode theNewLeft) { left = theNewLeft; }

    /**
     * Set right node.
     * 
     * @param theNewRight the new right node
     */
    public void setRight(TreeNode theNewRight) { right = theNewRight; }
}