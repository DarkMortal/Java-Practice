package Evaluator;

import java.util.*;

public class ExpressionEvaluator implements Evaluator {

    // default constructor
    public ExpressionEvaluator(){}

    private Map<String, Double> variables;
    public ExpressionEvaluator(Map<String, Double> variables_){
        this.variables = variables_;
    }

    public void setVariables(Map<String, Double> variables_){
        this.variables = variables_;
    }

    private double compute(double operand1, double operand2, char operator, boolean verbose) throws Exception{
        if(verbose)
            System.out.println("Evaluating : " + operand1 + " " + operator + " " + operand2);
        if(operator == '/' && operand2 == 0.0)
            throw new ArithmeticException("Division by zero");
        switch (operator) {
            case '+': return operand1 + operand2;
            case '-': return operand1 - operand2;
            case '*': return operand1 * operand2;
            case '/': return operand1 / operand2;
            case '^': return Math.pow(operand1, operand2);
            default: throw new ArithmeticException("Invalid operator: " + operator);
        }
    }

    private double getOperand(StringBuilder sb) throws Exception {
        String variable = sb.toString().trim();
        if(variable.equals("pi")) return Math.PI;
        if(variable.equals("e")) return Math.E;
        try{
            return Double.parseDouble(variable);
        }catch (Exception e){
            if(variables != null && variables.containsKey(variable))
                return variables.get(variable);
            else throw new Exception("Symbol not found: " + variable);
        }
    }

    public double evaluateEquation(String equation, int precision, boolean verbose) throws Exception{
        List<Double> operands = new ArrayList<>();
        List<Character> operators = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < equation.length(); i++){
            char c = equation.charAt(i);

            if(terminalSymbols.contains(c)){
                operators.add(c);
                operands.add(getOperand(sb));
                sb = new StringBuilder();
            } else sb.append(c);
            if(i == equation.length() - 1)
                operands.add(getOperand(sb));

            if(c == '('){
                i++;
                int count = 0;
                StringBuilder subEquation = new StringBuilder();
                while(equation.charAt(i) != ')' || count != 0){
                    if(equation.charAt(i) == '(') count++;
                    else if(equation.charAt(i) == ')') count--;
                    subEquation.append(equation.charAt(i));
                    i++;
                }
                if(verbose) System.out.println("SubEquation: " + subEquation.toString());
                operands.add(evaluateEquation(subEquation.toString(), precision, verbose));
                i++;
                if(i < equation.length())
                    operators.add(equation.charAt(i));
                sb = new StringBuilder();
            }
        }
      
        // TODO: implement operator precedence
        for(char operator: terminalSymbols){
            while(!operators.isEmpty() && operators.contains(operator)){
                int index = operators.indexOf(operator);
                double operand1 = operands.get(index);
                double operand2 = operands.get(index + 1);
                operands.set(index, compute(operand1, operand2, operator, verbose));

                operands.remove(index + 1);
                operators.remove(index);
            }
        }

        return Double.parseDouble(String.format("%."+precision+"f", operands.getFirst()));
    }
}
