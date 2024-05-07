package oop.practical.lispcalculator.calculator;

import oop.practical.lispcalculator.lisp.Ast;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class Calculator {

    public BigDecimal visit(Ast ast) throws CalculateException {
        return switch (ast) {
            case Ast.Number number -> visit(number);
            case Ast.Variable variable -> visit(variable);
            case Ast.Function function -> visit(function);
        };
    }

    private BigDecimal visit(Ast.Number ast) {
        return ast.value();
    }

    private BigDecimal visit(Ast.Variable ast) throws CalculateException{
        if (ast.name().equalsIgnoreCase("e"))
            return BigDecimal.valueOf(Math.E);
        if (ast.name().equalsIgnoreCase("pi"))
            return BigDecimal.valueOf(Math.PI);
        throw new CalculateException("Undefined variable " + ast.name() + ".");
    }

    private BigDecimal visit(Ast.Function ast) throws CalculateException {
        var arguments = new ArrayList<BigDecimal>();
        for (Ast argument : ast.arguments()) {
            arguments.add(visit(argument));
        }
        return switch (ast.name()) {
            case "+", "add" -> Functions.add(arguments);
            case "-", "sub" -> Functions.sub(arguments);
            case "*", "mul" -> Functions.mul(arguments);
            case "/", "div" -> Functions.div(arguments);
            case "pow" -> Functions.pow(arguments);
            case "sqrt" -> Functions.sqrt(arguments);
            case "rem"-> Functions.rem(arguments);
            case "mod"-> Functions.mod(arguments);
            case "sin" -> Functions.sin(arguments);
            case "cos" -> Functions.cos(arguments);
            default -> throw new CalculateException("Unknown function " + ast.name() + ".");
        };
    }

}
