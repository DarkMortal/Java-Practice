package evaluator;

import java.util.*;
import complex.Complex;

public class ExpressionEvaluator implements Evaluator {

    // default constructor
    public ExpressionEvaluator(){}

    private Map<String, Complex> variables;
    public ExpressionEvaluator(Map<String, Complex> variables_){
        this.variables = variables_;
    }

    public void setVariables(Map<String, Complex> variables_){
        this.variables = variables_;
    }

    public Complex getVariable(String variable){
        return this.variables.get(variable);
    }

    private Complex compute(Complex operand1, Complex operand2, char operator, boolean verbose) throws Exception{
        if(verbose)
            System.out.println("Evaluating : " + operand1 + " " + operator + " " + operand2);
        if(operator == '/' && operand2.equals(new Complex(0.0, 0.0)))
            throw new ArithmeticException("Division by zero");
        switch (operator) {
            case '+': return operand1.add(operand2);
            case '-': return operand1.subtract(operand2);
            case '*': return operand1.multiply(operand2);
            case '/': return operand1.divide(operand2);
            case '^': return operand1.pow(operand2);
            default: throw new ArithmeticException("Invalid operator: " + operator);
        }
    }

    private Complex getOperand(String variable) throws Exception {
        if(variable.isEmpty()) return  new Complex(0.0, 0.0);
        if(variable.equals("pi")) return new Complex(Math.PI, 0.0);
        if(variable.equals("e")) return new Complex(Math.E, 0.0);
        if(variable.charAt(variable.length() - 1) == 'i'){
            if(variable.length() == 1) return new Complex(0.0, 1.0);
            String t = variable.substring(0, variable.length() - 1);
            if(t.equals("pi")) return new Complex(0.0, Math.PI);
            if(t.equals("e")) return new Complex(0.0, Math.E);
            return new Complex(0.0, Double.parseDouble(t));
        }
        try{
            return new Complex(Double.parseDouble(variable), 0.0);
        }catch (Exception e){
            if(variables != null && variables.containsKey(variable))
                return variables.get(variable);
            else throw new Exception("Symbol not found: " + variable);
        }
    }

    public Complex complexEvaluator(String equation, boolean verbose) throws Exception{
        List<Complex> operands = new ArrayList<>();
        List<Character> operators = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        boolean isCurrentOperandNegative = false;

        for(int i = 0; i < equation.length(); i++){
            char c = equation.charAt(i);

            if(terminalSymbols.contains(c)){
                String variable = sb.toString().trim();

                if(!variable.isEmpty()){
                    operands.add(isCurrentOperandNegative ?
                            getOperand(variable).multiply(-1.0) :
                            getOperand(variable));
                    sb = new StringBuilder();
                    isCurrentOperandNegative = false;
                }else{
                    if(c == '-') isCurrentOperandNegative = true;
                    else throw new Exception("Invalid equation");
                    continue;
                }
                operators.add(c);
            } else sb.append(c);
            if(i == equation.length() - 1) {
                String variable = sb.toString().trim();
                if(variable.isEmpty()) continue;
                operands.add(isCurrentOperandNegative ?
                        getOperand(variable).multiply(-1.0) :
                        getOperand(variable));
                isCurrentOperandNegative = false;
            }

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
                if(verbose) System.out.println("SubEquation: " + subEquation);
                operands.add(
                   complexEvaluator(subEquation.toString(), verbose).multiply(
                         isCurrentOperandNegative ? -1.0 : 1.0
                   )
                ); i++;
                if(i < equation.length())
                    operators.add(equation.charAt(i));
                sb = new StringBuilder();
                isCurrentOperandNegative = false;
            }
        }
        // TODO: implement operator precedence
        for(char operator: terminalSymbols){
            while(!operators.isEmpty() && operators.contains(operator)){
                int index = operators.indexOf(operator);
                Complex operand1 = operands.get(index);
                Complex operand2 = operands.get(index + 1);
                operands.set(index, compute(operand1, operand2, operator, verbose));

                operands.remove(index + 1);
                operators.remove(index);
            }
        }

        return operands.getFirst();
    }

    public String evaluateEquation(String equation,int precision, boolean verbose) throws Exception {
        return this.complexEvaluator(equation, verbose).toString(precision);
    }
}
