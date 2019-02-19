import java.util.*;
/**
 * TreeUtil contains the following methods for manipulating binary trees.
 * 
 * leftmost - find the left most node 
 * rightmost - find the right most node
 * maxDepth  - compute max depth
 * countNodes - count total number of nodes
 * countLeaves - count total number of leaf nodes
 * preOrder - preorder traverse
 * inOrder  - inorder traverse
 * postOrder - postorder traverse
 * fillList - fill node values to a list
 * saveTree - save a tree to a file
 * buildTree - build a tree of a list iterator of node values
 * loadTree - restore a tree from a file
 * copy - copy a tree to a new tree
 * sameShape - check if 2 trees have the same shape
 * createRandom - creates a random tree of the specified depth
 * createDecodingTree - creates a morse code decoding tree
 * decodeMorse - decode morse code
 * (to be continued)
 * 
 * @author Emily Zhou
 * @since 11-29-2018
 * @version 01-13-2019
 *
 */
public class TreeUtil
{
    //used to prompt for command line input
    private static Scanner in = new Scanner(System.in);

    private static boolean debug = false;

    /**
     * Get the value of the left most node in a given tree.
     * 
     * @precondition the root node of given tree must not be null
     * @param t the root node of the given tree
     * @return the value of left most node
     */
    public static Object leftmost(TreeNode t)
    {
        // implement with a loop
        if (t == null) return null;
        TreeNode node = t;
        while (node.getLeft() != null)
        {
            node = node.getLeft();
        }
        return node.getValue();
    }

    /**
     * Get the value of the right most node in a given tree.
     * 
     * @precondition the root node of given tree must not be null
     * @param t the root node of the given tree
     * @return the value of right most node
     */
    public static Object rightmost(TreeNode t)
    {
        // implement this recursively
        if (t == null) return null;
        if (t.getRight() == null)
        {
            return t.getValue();
        }
        return rightmost(t.getRight());
    }

    /**
     * Compute the maximum depth of a given tree.
     * 
     * @param t the root node of the given tree
     * @return the maximum depth
     */
    public static int maxDepth(TreeNode t)
    {
        if (t == null) return 0;
        return Math.max(maxDepth(t.getRight()), maxDepth(t.getLeft())) + 1;
    }

    /**
     * Create a random tree of the specified depth.
     * No attempt to balance the tree is provided.
     * 
     * @param depth of the tree
     * @return TreeNode object that points to the generated tree
     */
    public static TreeNode createRandom(int depth)
    {
        //if (Math.random() * Math.pow(2, depth) < 1)
        //   return null;
        if (depth <= 0) return null;
        return new TreeNode(((int)(Math.random() * 10)),
            createRandom(depth - 1),
            createRandom(depth - 1));
    }

    /**
     * Compute the number nodes in a given tree.
     * 
     * @param t the root node of the given tree
     * @return the number nodes
     */
    public static int countNodes(TreeNode t)
    {
        if (t == null) return 0;
        return countNodes(t.getLeft()) + countNodes(t.getRight()) + 1;
    }

    /**
     * Compute the number of leaves in a given tree.
     * 
     * @param t the root node of the given tree
     * @return the number of leaves
     */
    public static int countLeaves(TreeNode t)
    {
        if (t == null) return 0;
        if (t.getLeft() == null && t.getRight() == null)
        {
            return 1;
        }
        return countLeaves(t.getLeft()) + countLeaves(t.getRight());
    }

    /**
     * Preorder (root, left, right) traverse a given tree.
     * 
     * @param t the root node of the tree
     * @param display the TreeDisplay to light up the node when visited
     */
    public static void preOrder(TreeNode t, TreeDisplay display)
    {
        if (t == null) return;
        display.visit(t);
        preOrder(t.getLeft(), display);
        preOrder(t.getRight(), display);
    }

    /**
     * Inorder (left, root, right) traverse a given tree.
     * 
     * @param t the root node of the tree
     * @param display the TreeDisplay to light up the node when visited
     */
    public static void inOrder(TreeNode t, TreeDisplay display)
    {
        if (t == null) return;
        inOrder(t.getLeft(), display);
        display.visit(t);
        inOrder(t.getRight(), display);
    }

    /**
     * Postorder (left, right, root) traverse a given tree.
     * 
     * @param t the root node of the tree
     * @param display the TreeDisplay to light up the node when visited
     */
    public static void postOrder(TreeNode t, TreeDisplay display)
    {
        if (t == null) return;
        postOrder(t.getLeft(), display);

        postOrder(t.getRight(), display);
        display.visit(t);
    }

    /**
     * Preorder traverse the given tree and fill in
     * each node value as element to the provided list
     * and treat null node as having value "$".
     * 
     * @param t the given tree
     * @param list the provided list to fill in
     */
    public static void fillList(TreeNode t, List < String > list)
    {
        if (t == null)
        {
            list.add("$");
            return;    
        }
        list.add(t.getValue().toString());
        fillList(t.getLeft(), list);
        fillList(t.getRight(), list);
    }

    /**
     * Use the FileUtil utility class to save the tree rooted at t
     * as a file with the given file name.
     * 
     * @param fileName is the name of the file to create which
     *        will hold the data values in the tree
     * @param t is the root of the tree to save
     */
    public static void saveTree(String fileName, TreeNode t)
    {
        List < String > list = new ArrayList < String > ();
        fillList(t, list);
        FileUtil.saveFile(fileName, list.iterator());
    }

    /**
     * Build the tree with the given iterator to iterate through
     * a valid description of a binary tree with String values.
     * 
     * Null nodes are indicated by "$" markers
     * 
     * @param it the iterator which will iterate over the tree description
     * @return a pointer to the root of the tree built by the iteration
     */
    public static TreeNode buildTree(Iterator < String > it)
    {
        if (!it.hasNext())
        {
            return null;
        }
        String value = it.next();
        if (value.equals("$"))
        {
            return null;
        }
        TreeNode node = new TreeNode(value, null, null);
        node.setLeft(buildTree(it));
        node.setRight(buildTree(it));
        return node;
    }

    /**
     * Read a file description of a tree and then build the tree.
     * 
     * @param fileName is a valid file name for a file
     *                 that describes a binary tree
     * @return a pointer to the root of the tree
     */
    public static TreeNode loadTree(String fileName)
    {
        Iterator < String > it = FileUtil.loadFile(fileName);
        return buildTree(it);
    }

    /**
     * Utility method that waits for a user to type text
     * into Std Input and then press enter.
     * @return the string entered by the user
     */
    private static String getUserInput()
    {
        return in.nextLine();  
    }

    /**
     * Plays a single round of 20 questions
     * postcondition:  plays a round of twenty questions,
     *                 asking the user questions as it
     *                 walks down the given knowledge tree,
     *                 lighting up the display as it goes;
     *                 modifies the tree to include information learned.
     * @param t a pointer to the root of the game tree
     * @param display which will show the progress of the game
     */
    private static void twentyQuestionsRound(TreeNode t, TreeDisplay display)
    {    
        throw new RuntimeException("Write ME!");
    }

    /** 
     * Plays a game of 20 questions
     * Begins by reading in a starting file and then plays multiple rounds
     * until the user enters "quit".  Then the final tree is saved
     */
    public static void twentyQuestions()
    {
        throw new RuntimeException("Write ME!");
    }

    /**
     * Copy a binary tree.
     * 
     * @param t the root of the tree to copy
     * @return a new tree, which is a complete copy
     *         of t with all new TreeNode objects
     *         pointing to the same values as t (in the same order, shape, etc)
     */
    public static TreeNode copy(TreeNode t)
    {
        if (t == null) return null;
        TreeNode node = new TreeNode(t.getValue(), null, null);
        node.setLeft(copy(t.getLeft()));
        node.setRight(copy(t.getRight()));
        return node;
    }

    /**
     * Tests to see if two trees have the same shape, but not necessarily the
     * same values.  Two trees have the same shape if they have TreeNode objects
     * in the same locations relative to the root
     * 
     * @param t1 pointer to the root of the first tree
     * @param t2 pointer to the root of the second tree
     * @return true if t1 and t2 describe trees having the same shape,
     *         false otherwise
     */
    public static boolean sameShape(TreeNode t1, TreeNode t2)
    {
        if (t1 == null && t2 == null)
        {
            return true;
        }
        if (t1 == null || t2 == null)
        {
            return false;
        }
        return sameShape(t1.getLeft(), t2.getLeft()) &&
        sameShape(t1.getRight(), t2.getRight());
    }

    /**
     * Generate a tree for decoding Morse code.
     * 
     * @param display the display that will show the decoding tree
     * @return the decoding tree
     */
    public static TreeNode createDecodingTree(TreeDisplay display)
    {
        TreeNode tree = new TreeNode("Morse Tree");
        display.displayTree(tree);
        insertMorse(tree, "a", ".-", display);
        insertMorse(tree, "b", "-...", display);
        insertMorse(tree, "c", "-.-.", display);
        insertMorse(tree, "d", "-..", display);
        insertMorse(tree, "e", ".", display);
        insertMorse(tree, "f", "..-.", display);
        insertMorse(tree, "g", "--.", display);
        insertMorse(tree, "h", "....", display);
        insertMorse(tree, "i", "..", display);
        insertMorse(tree, "j", ".---", display);
        insertMorse(tree, "k", "-.-", display);
        insertMorse(tree, "l", ".-..", display);
        insertMorse(tree, "m", "--", display);
        insertMorse(tree, "n", "-.", display);
        insertMorse(tree, "o", "---", display);
        insertMorse(tree, "p", ".--.", display);
        insertMorse(tree, "q", "--.-", display);
        insertMorse(tree, "r", ".-.", display);
        insertMorse(tree, "s", "...", display);
        insertMorse(tree, "t", "-", display);
        insertMorse(tree, "u", "..-", display);
        insertMorse(tree, "v", "...-", display);
        insertMorse(tree, "w", ".--", display);
        insertMorse(tree, "x", "-..-", display);
        insertMorse(tree, "y", "-.--", display);
        insertMorse(tree, "z", "--..", display);
        return tree;
    }

    /**
     * Helper method for building a Morse code decoding tree.
     * postcondition:  inserts the given letter into the decodingTree,
     *                 in the appropriate position, as determined by
     *                 the given Morse code sequence; lights up the display
     *                 as it walks down the tree
     * @param decodingTree is the partial decoding tree
     * @param letter is the letter to add
     * @param code is the Morse code for letter
     * @param display is the display that will show progress
     *                as the method walks down the tree
     */
    private static void insertMorse(TreeNode decodingTree, String letter,
    String code, TreeDisplay display)
    {
        if (code.length() == 0)
        {
            decodingTree.setValue(letter);
            display.visit(decodingTree);
            return;
        }
        String temp = code.substring(0, 1);
        if (temp.equals("-"))
        {
            if ( decodingTree.getRight() == null)
            {
                decodingTree.setRight(new TreeNode(null));
            }
            display.visit(decodingTree.getRight());
            insertMorse(decodingTree.getRight(), letter,
                code.substring(1), display);
        }
        if (temp.equals("."))
        {
            if ( decodingTree.getLeft() == null)
            {
                decodingTree.setLeft(new TreeNode(null));
            }
            display.visit(decodingTree.getLeft());
            insertMorse(decodingTree.getLeft(), letter,
                code.substring(1), display);
        }

    }

    /**
     * Decodes Morse code by walking the decoding tree
     * according to the input code.
     * 
     * @param decodingTree is the Morse code decoding tree
     * @param cipherText is Morse code consisting of dots, dashes, and spaces
     * @param display is the display object that will show the decoding progress
     * @return the string represented by cipherText
     */
    public static String decodeMorse(TreeNode decodingTree,
    String cipherText, TreeDisplay display)
    {
        if (cipherText.length() == 0)
        {
            return "";
        }
        String temp = cipherText.substring(0, 1);
        String rem = cipherText.substring(1);
        TreeNode node = decodingTree;
        while (!temp.equals(" ") && rem.length() > 0)
        {
            if (temp.equals("."))
            {
                node = node.getLeft();
            }
            if (temp.equals("-"))
            {
                node = node.getRight();
            }
            temp = rem.substring(0, 1);
            rem = rem.substring(1);
        }
        display.visit(node);
        return (String)node.getValue() + 
        decodeMorse(decodingTree, rem, display);
    }

    /**
     * optional work.
     */
    public static int eval(TreeNode expTree)
    {
        throw new RuntimeException("Write ME!");
    }

    /**
     * optional work.
     */
    public static TreeNode createExpressionTree(String exp)
    {
        throw new RuntimeException("Write ME!");
    }

    /**
     * debug printout.
     * postcondition: out is printed to System.out
     * @param out the string to send to System.out
     */

    private static void debugPrint(String out)
    {
        if (debug) System.out.println("debug: " + out);
    }
}
