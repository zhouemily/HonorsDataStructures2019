//A FancyCalc class that implements some math methods using recursion.
//@author Emily Zhou
//@version 9.12.18
public class FancyCalc
{
        //Calculate the value of base to the power of exponent
        //@param base the base number
        //@param exponent the exponent
        //@return the value of the base to the power of exponent
	public long pow(int base, int exponent) throws Exception
	{
                Thread.sleep(1);
		if (exponent == 0)
			return 1;
		else 
			return base*(this.pow(base, exponent-1));
	}

        //Calculate square of a number
        //@param num the number to calculate
        //@return the square of the number
	public long square(long num) throws  Exception
	{
                Thread.sleep(1);
		return num*num;
	}

        //A fast way to calculate the value of base to the power of exponent
        //@param base the base number
        //@param exponent the exponent
        //@return the value of the base to the power of exponent
	public long fastPow(int base, int exponent) throws Exception
	{
                Thread.sleep(1);
		//base case
		if (exponent == 0)
			return 1;
		if (exponent % 2 == 0)
			return this.square(this.fastPow(base, exponent/2));
		return base * this.fastPow(base, exponent-1);
	}

        //Compute the greatest common divisor of two positive integers
        //@param a the first positive integer
        //@param b the second positive integer
        //@return the greatest common divisor of the two positive integers
	public int gcd(int a, int b)
	{
		if (b == 0)
			return a;
		return this.gcd(b, a % b);
	}

        //Determine if a number is prime number
        //@param num the number to test
        //@return true if the number is a prime number and false otherwise 
	public boolean isPrime(int num)
	{
		return this.helpPrime(num, 2);
	}

        //Helper method to isPrime()
	private boolean helpPrime(int num, int divisor)
	{
		if (num <= 1)
			return false;
		if (divisor == num)
			return true;
		if (num % divisor == 0)
			return false;
		return this.helpPrime(num, divisor+1);
	}

        //Compute the factorial of a positive integer
        //@param n the number to compute its factorial 
        //@return the factorial of the number
	public int fact(int n)
	{
		if (this.factHelp(n, 1) < 0)
			return -1;
		return this.factHelp(n, 1);
	}

        //Helper method of fact()
	private int factHelp(int n, int result)
	{
		if (n == 0)
			return result;
		else
			return this.factHelp(n-1, result*n);
	}

        //Get the nth element of the Fibonacci sequence (1, 1, 2, 3, ...)
        //@param n the nth element 
        //@return the nth element in the Fibonacci sequence
	public long fib(int n) throws Exception
	{
                Thread.sleep(1);
		if (n == 1)
			return 1;
		if (n == 2)
			return 1;
		return this.fib(n-1) + this.fib(n-2);
	}

        //Compute the greatest integer whose square is no larger
        //then a given number using Newton's method.
        //@param num the number to compute its square-root
        //@return the greatest integer whose square is no larger than the given number 
	public int sqrt(int num)
	{
		return this.sqrtHelp(num, 1, true);
	}

        //Helper method for sqrt
        //@param num the number
        //@param guess the guess
        //@param firstGuess if the method is called first time otherwise false
        //@return the guess
	private int sqrtHelp(int num, int guess, boolean firstGuess)
	{
		if (this.canContinue(num, guess, firstGuess))
		{
			return this.sqrtHelp(num, this.average(guess, num/guess), false);
		}
                return guess;
              
	}

        //Determine if guess is good enough
        //@param num the number
        //@param guess the current guess
        //@param firstGuess true if this is first time call 
        //@return true if continue finding a better guess otherwise false
	private boolean canContinue(int num, int guess, boolean firstGuess)
	{
		if (guess == num/guess)
			return false;
		if (guess == (num/guess)-1)
			return false;
                if (!firstGuess && guess * guess <= num)
                        return false;
		return true;
	}

        //Calculate the average of the 2 numbers
	private int average(int num1, int num2)
	{
		return (num1+num2)/2;
	}
	
        //Sum the values returned from an enumerator
        //@param en the enumerator
        //@return the sum of values returned from the enumerator
	public int sumEnum(Enumerator en) {
		if (!en.hasNext()) {
			return 0;
		}
		return en.next() + this.sumEnum(en);
	}

        public static void main(String[] args) throws Exception {
            FancyCalc f = new FancyCalc();
            long start = System.currentTimeMillis();
            long a = f.pow(1, 1000);
            long end = System.currentTimeMillis();
            System.out.println("Time taken pow 1000: " + (end - start));
            start = System.currentTimeMillis();
            f.pow(1, 2000);
            end = System.currentTimeMillis();
            System.out.println("Time taken pow 2000: " + (end - start));

            start = System.currentTimeMillis();
            f.fastPow(1, 1000);
            end = System.currentTimeMillis();
            System.out.println("Time taken fastpow 1000: " + (end - start));
            start = System.currentTimeMillis();
            f.fastPow(1, 2000);
            end = System.currentTimeMillis();
            System.out.println("Time taken fastpow 2000: " + (end - start));

            start = System.currentTimeMillis();
            f.fib(5);
            end = System.currentTimeMillis();
            System.out.println("Time taken fib 10: " + (end - start));
            start = System.currentTimeMillis();
            f.fib(6);
            end = System.currentTimeMillis();
            System.out.println("Time taken fib 11: " + (end - start));
        }
}


//A Enumberator class that returns all integers
//from a low to a high value when next() is called.
//@author Emily Zhou
//@version 8.31.18
class Enumerator
{
        private int start;
        private int end;

        //Construstor to create Enumerator object.
        //@param num1 the low value number
        //@param num2 the high value number
        public Enumerator(int num1,int num2)
        {
                start = num1-1;
                end = num2;
        }

        //Check if any more integers in the Enumerator instance.
        //@return true if has more integers otherwise false
        public boolean hasNext()
        {
                if (start+1 <= end)
                        return true;
                return false;
        }

        //Get the next integer value.
        //@return the next integer value
        public int next()
        {
                start= start+1;
                return start;
        }
}

