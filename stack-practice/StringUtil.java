import java.util.Stack;

/**
 * A utility class to reverse a String or check if
 * a String is a palindrome.
 * 
 * @author Emily Zhou
 * @version 11/07/2018
 */
public class StringUtil
{
    /**
     * Reverse a string using a Stack and substring not charAt.
     * 
     * @param str the String to reverse
     * @return the reversed String
     */
    public static String reverseString(String str)
    {
        System.out.println("String to reverse: " + str);
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < str.length(); i++)
        {
            stack.push(str.substring(i, i+1));
        }
        String newstr = "";
        while (!stack.isEmpty())
        {
            newstr += stack.pop();
        }
        System.out.println("Reversed: " + newstr);
        return newstr;
    }
    
    /**
     * Check to see if a String is palindrome by using the reverseString method.
     * 
     * @param s the String to check
     * @return true if it is palindrome otherwise false
     */
    public static boolean isPalindrome(String s)
    {
        String newstr = reverseString(s);
        if (newstr.equals(s)) 
        {
            return true;
        }
        return false;
    }

    // The tester for checking that reverse and isPalindrome work well.
    public static void main(String[] args)
    {
        String test =  "racecar";
        String test2 = "notapalindrome";

        if ( !("".equalsIgnoreCase(reverseString(""))) )
            System.out.println("** Oops Something went wrong. Check your reverse method **");

        if ( !("a".equalsIgnoreCase(reverseString("a"))) )
            System.out.println("** Oops Something went wrong. Check your reverse method **");

        if (!test.equalsIgnoreCase(reverseString(test)))
            System.out.println("** Oops Something went wrong. Check your reverse method **");
        else
            System.out.println("Success " + test + " matched " + reverseString(test));
      
        if (test2.equalsIgnoreCase(reverseString(test2)))
            System.out.println("** Oops Something went wrong. Check your reverse method **");

    }
}
