import evaluator.ExpressionEvaluator;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) {
        ExpressionEvaluator exp = new ExpressionEvaluator(new HashMap<>(4){{
            // fifth root of unity
            put("x", 3.0);
        }});
        String[] equations = {
                "-1.3+e^(2.3-1.2)-pi", "3^-1+2^-2",
                "1.2+(4.5-2.2/0.5+(3.45-2.22))+3.5-2.1", "3*x^2-2*x+1"
        };
        try{
            for(String equation: equations){
                if(equation.contains("x"))
                    System.out.println("Equation: " + equation + " where x = " + exp.getVariable("x"));
                else System.out.println("Equation: " + equation);
                System.out.println("Result: " + exp.evaluateEquation(equation, 3, true) + "\n");
            }
        } catch(ArithmeticException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //e.printStackTrace();
        }
    }
}

