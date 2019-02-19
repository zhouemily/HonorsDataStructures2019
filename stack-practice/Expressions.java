import java.util.Stack;
import java.util.Scanner;

/**
 * A class that practic using Stack.
 * 
 * @author Emily Zhou
 * @version 11/07/2018
 */

public class Expressions
{
    /** 
     * parenthesis matching : An expression is said to be balanced if
     * every opener has a corresponding closer, in the right order
     * {, [ or ( are the only types of brackets allowed
     *     
     * @param   expression containing operands operators 
     *         and any of the 3 supportedbrackets
     * @return  true is the parenthesis are balanced         
     *         false otherwise
     */
    public static boolean matchParenthesis(String expression)
    {
        Stack<String> stack = new Stack();
        for (int i = 0; i < expression.length(); i++)
        {
            String c = expression.substring(i, i+1);
            if (isOpenBracket(c))
            {
                stack.push(c);
            }
            else if (isCloseBracket(c))
            {
                if (stack.isEmpty())
                {
                    return false;
                }
                if (!isPairBracket(stack.pop(),c))
                {
                    return false;
                }
            }
        }
        if (!stack.isEmpty())
        {
            return false;
        }
        return true;
    }

    /**
     * Helper method to see if a given string is an open bracket.
     * 
     * @param c the string to check
     * @return true if it is an open bracket otherwise false
     */
    private static boolean isOpenBracket(String c)
    {
        return (c.equals("{") ||
            c.equals("[") ||
            c.equals("("));     
    }

    /**
     * Helper method to see if a given string is a close bracket.
     * 
     * @param c the string to check
     * @return true if it is a close bracket otherwise false
     */
    private static boolean isCloseBracket(String c)
    {
        return (c.equals("}") ||
            c.equals("]") ||
            c.equals(")"));     
    }

    /**
     * Helper method to see if the first string is an open bracket
     * and the second string is the corresponding close bracket.
     * 
     * @param c1 the first string
     * @param c2 the second string
     * @return true if the given 2 strings are a pair of brackets
     */
    private static boolean isPairBracket(String c1, String c2)
    {
        return ((c1.equals("{") && c2.equals("}")) ||
            (c1.equals("[") && c2.equals("]")) ||
            (c1.equals("(") && c2.equals(")")));

    }

    /** 
     * returns a string in postfix form 
     * if given an expression in infix form as a parameter
     * do this conversion using a Stack
     * 
     * @param expr valid expression in infix form
     * @return equivalent expression in postfix form
     */
    public static String infixToPostfix(String expr)
    {
        System.out.println("Infix: " + expr);

        Stack<String> stack = new Stack<String>();
        String strPostfix = "";
        Scanner scan = new Scanner(expr);
        scan.useDelimiter(" ");
        while (scan.hasNext())
        {
            if (scan.hasNextInt())
            {
                strPostfix += scan.nextInt() + " ";
                continue;
            }
            char currentOp = scan.next().charAt(0);
            if (stack.isEmpty())
            {
                stack.push(String.valueOf(currentOp));
                continue;
            }
            while (true)
            {
                String op = stack.peek();
                if (getPrecedenceLevel(op.charAt(0)) > getPrecedenceLevel(currentOp))
                {
                    strPostfix += stack.pop() + " ";
                    if (!stack.isEmpty())
                    {
                        continue;
                    }
                }
                stack.push(String.valueOf(currentOp));
                break;
            }
        }
        while (!stack.isEmpty())
        {
            strPostfix += stack.pop() + " ";
        }
        System.out.println("Postfix: " +strPostfix);
        return strPostfix;
    }

    private static boolean isOperator(char c)
    {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
            return true;

        }
        return false;
    }

    /**
     * Get precedence of an operator
     */
    private static int getPrecedenceLevel(char op) 
    {
        switch (op) {
            case '+':
            case '-':
            return 0;

            case '*':
            case '/':
            case '%':
            return 1;

            default:
            throw new RuntimeException("Unknown operator: " + op);
        }
    }

    /**
     * returns the value of an expression in postfix form
     * do this computation using a Stack
     * 
     * @param expr valid expression in postfix form
     * @return value of the expression
     * @precondition postfix expression  
     *              contains numbers and operators + - * / and %
     *               and that operands and operators are separated by spaces
     */
    public static double evalPostfix(String expr)
    {
        System.out.println("Evaluate: "+expr);
        Stack<Integer> operands = new Stack<Integer>();
        Scanner scan = new Scanner(expr);
        scan.useDelimiter(" ");
        while (scan.hasNext())
        {
            if (scan.hasNextInt())
            {
                operands.push(scan.nextInt());
                continue;
            }
            char op = scan.next().charAt(0);
            int result = compute(op, Integer.valueOf(operands.pop()),
                    Integer.valueOf(operands.pop()));
            operands.push(result);
        }
        System.out.println("Result: "+ operands.peek());
        return Double.valueOf(operands.pop());
    }

    private static int compute(char op, int operand1, int operand2)
    {
        switch (op) {
            case '+':
            return operand2 + operand1;

            case '-':
            return operand2 - operand1;

            case '*':
            return operand2 * operand1;

            case '/':
            return operand2 / operand1;

            case '%':
            return operand2 % operand1;

            default: throw new RuntimeException("Unknown operator" + op);
        }
    }

    // Tester to check if infix to postfix and evaluate postfix work well
    public static void main(String[] args)
    {
        String exp = "2 + 3 * 4";
        test(exp, 14);

        exp = "8 * 12 / 2";
        test(exp, 48);

        exp = "5 % 2 + 3 * 2 - 4 / 2";
        test(exp, 5);   

        // test balanced expressions
        testBalanced("{ 2 + 3 } * ( 4 + 3 )", true);
        testBalanced("} 4 + 4 { * ( 4 + 3 )", false);
        testBalanced("[ [ [ ] ]", false);
        testBalanced("{ ( } )", false);
        testBalanced("( ( ( ) ) )", true);
    }

    public static void test(String expr, double expect)
    {
        String post = infixToPostfix(expr);        
        double val = evalPostfix(post);

        System.out.println("Infix: " + expr);
        System.out.println("Postfix: " + post);
        System.out.println("Value: " + val);
        if (val == expect)
        {
            System.out.print("** Success! Great Job **");
        }
        else
        {
            System.out.print("** Oops! Something went wrong. ");
            System.out.println("Check your postfix and eval methods **");
        }
    }

    public static void testBalanced(String ex, boolean expected)
    {
        boolean act = matchParenthesis(ex);
        if (act == expected)
            System.out.println("** Success!: matchParenthesis(" + ex + ") returned " + act);
        else
        {
            System.out.print("** Oops! Something went wrong check : matchParen(" + ex + ")");
            System.out.println(" returned " + act + " but should have returned " + expected);
        }
    }
}
