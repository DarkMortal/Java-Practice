import java.util.Scanner;
import java.util.InputMismatchException;

public class Recursion{
	private static Scanner obj = new Scanner(System.in);
	
	public static int factorial(int x) {
		if(x <= 0) return 1;
		return x*factorial(x-1);
	}
	
	public static int subFactorial(int x) {
		if(x <= 3) return x-1;
		return (x-1)*(subFactorial(x-1)+subFactorial(x-2));
	}
	
	public static void main(String args[]) {
		System.out.print("Enter a positive integer between 1 and 10\nEnter -1 to exit\n");
		while(true){
			try{
				int n = obj.nextInt();
				if(n >= 1 && n <= 10){
					System.out.println("Factorial of "+n+" = "+factorial(n));
					System.out.println("Sub-factorial of "+n+" = "+subFactorial(n));
				}
				else if(n <= 0) break;
				else System.out.println("Invalid input!!!");
			}catch(InputMismatchException exc){
				System.out.println("Input Mismatch :(");
				// exc.printStackTrace(System.out);
				break;
			}
		} System.out.println("Thank you for using me :)");
	}
}