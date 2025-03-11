import Evaluator.ExpressionEvaluator;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        ExpressionEvaluator exp = new ExpressionEvaluator(new HashMap<>(4){{
            put("x", 3.0);
        }});
        String equation1 = "1.3+e^(2.3-1.2)-pi";
        String equation2 = "1.2+(4.5-2.2/0.5+(3.45-2.22))+3.5-2.1";
        String equation3 = "3*x^2-2*x+1";
        try{
            System.out.println("Equation-1: " + equation1);
            System.out.println("Result: " + exp.evaluateEquation(equation1, 3, true));
            System.out.println("\nEquation-2: " + equation2);
            System.out.println("Result: " + exp.evaluateEquation(equation2, 3, true));
            System.out.println("\nEquation-3: " + equation3 + " where x = 3");
            System.out.println("Result: " + exp.evaluateEquation(equation3, 3, true));
        } catch(ArithmeticException e){
            System.out.println(e.getMessage());
        } catch (Exception e) {
            //System.out.println("Invalid expression: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
