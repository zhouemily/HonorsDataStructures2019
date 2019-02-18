import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * A binary tree tester.
 */
public class BinaryTreeTester
{
    public void test() throws Exception
    {
        testBasic();
        Thread.sleep(5000); //sleep 5sec before display next test
        testTraverse();
        testSaveLoad();
        Thread.sleep(5000);
        testCopy();
        Thread.sleep(5000);
        testMorseCode();
    }
    /**
     * Test basic TreeUtil methods.
     */
    private void testBasic()
    {
        TreeDisplay display = new TreeDisplay();
        // to get the display to send back the values when it visits a node:
        display.setTester(this);
        
        System.out.println("Test tree basic methods");
        TreeNode t = createRandomTree(6);
        display.displayTree(t);
        System.out.println("leftmost: " + TreeUtil.leftmost(t));
        System.out.println("rightmost: " + TreeUtil.rightmost(t));
        System.out.println("maxDepth: " +TreeUtil.maxDepth(t));
        System.out.println("countNodes: " +TreeUtil.countNodes(t));
        System.out.println("countLeaves: " +TreeUtil.countLeaves(t));
    }
    
    /**
     * Test TreeUtil tree traverse methods.
     */
    private void testTraverse() throws Exception
    {
        TreeDisplay display = new TreeDisplay();
        display.setTester(this);
        
        TreeNode t = createRandomTree(6);
        display.displayTree(t);
        System.out.println("Test preorder traverse a tree");
        TreeUtil.preOrder(t, display);
        System.out.println("Test inorder traverse a tree");
        TreeUtil.inOrder(t, display);
        System.out.println("Test postorder traverse a tree");
        TreeUtil.postOrder(t, display);
    }
    
    /**
     * Test TreeUtil save and load a tree to/from a file.
     */
    private void testSaveLoad() throws Exception
    {
        TreeDisplay display = new TreeDisplay();
        display.setTester(this);
        
        System.out.println("Test save tree to a file");
        TreeNode t = createRandomTree(6);
        display.displayTree(t);
        TreeUtil.saveTree("./tree.out", t);
        Thread.sleep(5000);
        
        System.out.println("Test load a tree from a file");
        TreeNode t1 = TreeUtil.loadTree("./tree.out");
        TreeDisplay display1 = new TreeDisplay();
        display1.setTester(this);
        display1.displayTree(t1);
    }
    
    /**
     * Test TreeUtil copy tree and tree shape comparison methods.
     */
    private void testCopy() throws Exception
    {
        TreeDisplay display = new TreeDisplay();
        display.setTester(this);
        
        System.out.println("Test copy tree");
        TreeNode t = createRandomTree(6);
        display.displayTree(t);
        Thread.sleep(5000);
        TreeNode copy = TreeUtil.copy(t);
        
        System.out.println("Test tree shape comparison");
        System.out.println("Is same shape: " + TreeUtil.sameShape(copy, t));
        TreeDisplay display1 = new TreeDisplay();
        display1.displayTree(copy);
    }
    
    /**
     * Test TreeUtil morse code methods.
     */
    private void testMorseCode() throws Exception
    {
        TreeDisplay display = new TreeDisplay();
        display.setTester(this);
        System.out.println("Test build morse decoding tree");
        TreeNode morseDecodeTree = TreeUtil.createDecodingTree(display);
        Thread.sleep(5000);
        System.out.println("Test decode morse");
        String cipher = ".... . .-.. .-.. ---";  //hello
        String text = TreeUtil.decodeMorse(morseDecodeTree, cipher, display);
        System.out.println("morse code [" + cipher + "]");
        System.out.println("morse decoded text [" + text + "]");
        
    }
    
    /**
     * A helper method to create a non-null random tree.
     * 
     * @param depth the depth of the tree
     */
    private TreeNode createRandomTree(int depth)
    {
       TreeNode t = TreeUtil.createRandom(6);
       while (t == null) 
       {
           t = TreeUtil.createRandom(6);
       }
       return t;
    }
    
    /**
    * called by the display object to send back the node value
    * when a node is visited
    */
    public void sendValue(Object value)
    {
        System.out.println(value);
    }
    
    public static void main(String[] args) throws Exception
    {
        BinaryTreeTester tester = new BinaryTreeTester();
        tester.test();
    }
}
