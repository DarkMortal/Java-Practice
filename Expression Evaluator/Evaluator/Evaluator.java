package Evaluator;

import java.util.ArrayList;
import java.util.List;

public interface Evaluator {
    List<Character> terminalSymbols = new ArrayList<>(){{
        add('^'); add('*'); add('/'); add('-'); add('+');
    }};

    double evaluateEquation(String equation, int precision, boolean verbose) throws Exception;
}
