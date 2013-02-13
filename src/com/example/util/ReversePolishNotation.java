package com.example.util;

import com.example.util.exception.ExpressionParseException;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: ZIM
 * Date: 11.02.13
 * Time: 23:44
 */
public class ReversePolishNotation {
    private static final Character PLUS = '+';
    private static final Character MINUS = '-';
    private static final Character MULTIPLY = '*';
    private static final Character DIVIDE = '/';
    private static final Character OPEN_BRACKET = '(';
    private static final Character CLOSE_BRACKET = ')';
    private Stack<Character> operations;
    private Stack<Double> numbers;

    public ReversePolishNotation() {
        operations = new Stack<Character>();
        numbers = new Stack<Double>();
    }

    public Double polishCalculate(final String formula) throws ExpressionParseException {
        try {
            String[] elements = formula.split(" ");
            for (int i = 0; i < elements.length; i++) {
                String currentElement = elements[i];
                if (currentElement.charAt(0) == OPEN_BRACKET) {
                    operations.push(OPEN_BRACKET);
                } else if (currentElement.charAt(0) == CLOSE_BRACKET) {
                    Character operator;
                    while ((operator = operations.pop()) != OPEN_BRACKET) {
                        makeStackOperation(operator, true);
                    }
                } else if (currentElement.charAt(0) == PLUS) {
                    makeStackOperation(PLUS, false);
                } else if (currentElement.charAt(0) == MINUS) {
                    makeStackOperation(MINUS, false);
                } else if (currentElement.charAt(0) == MULTIPLY) {
                    makeStackOperation(MULTIPLY, false);
                } else if (currentElement.charAt(0) == DIVIDE) {
                    makeStackOperation(DIVIDE, false);
                } else {
                    try {
                        Double number = Double.parseDouble(currentElement);
                        numbers.push(number);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return Double.NaN;
                    }
                }
            }
            while (!operations.empty()) {
                makeStackOperation(operations.pop(), true);
            }
        } catch (Exception e) {
            throw new ExpressionParseException(e.getMessage());
        }

        return numbers.pop();
    }

    private void makeStackOperation(Character currentOperation, boolean stackOut) {
        int peekPriority = operations.size() > 0 ? getPriority(operations.peek()) : 0;
        int currentPriority = getPriority(currentOperation);
        if ((currentPriority <= peekPriority && peekPriority > 1) || stackOut) {
            Double operand2 = numbers.pop();
            Double operand1 = numbers.pop();
            Character operation = stackOut ? currentOperation : operations.pop();
            Double result = makeOperation(operand1, operand2, operation);
            numbers.push(result);
        }
        if (!stackOut)
            operations.push(currentOperation);
    }

    private Double makeOperation(Double operand1, Double operand2, Character operation) {
        if (operation == '+')
            return operand1 + operand2;
        else if (operation == '-')
            return operand1 - operand2;
        else if (operation == '*')
            return operand1 * operand2;
        else
            return operand1 / operand2;
    }

    /**
     * @param operation Current operation.
     * @return Priority of current operation.
     */
    private int getPriority(char operation) {
        if (operation == '*' || operation == '/')
            return 3;
        if (operation == '-' || operation == '+')
            return 2;
        if (operation == '(' || operation == ')')
            return 1;
        return 0;
    }
}