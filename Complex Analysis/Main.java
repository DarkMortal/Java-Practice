import complex.Complex;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter the real part of the first complex number:");
        double real1 = scanner.nextDouble();
        System.out.println("Enter the imaginary part of the first complex number:");
        double imaginary1 = scanner.nextDouble();
        Complex a = new Complex(real1, imaginary1);

        System.out.println("Enter the real part of the second complex number:");
        double real2 = scanner.nextDouble();
        System.out.println("Enter the imaginary part of the second complex number:");
        double imaginary2 = scanner.nextDouble();
        Complex b = new Complex(real2, imaginary2);

        System.out.println("Addition : " + a.add(b));
        System.out.println("Subtraction : " + a.subtract(b));
        System.out.println("Multiplication : " + a.multiply(b));
    }
}