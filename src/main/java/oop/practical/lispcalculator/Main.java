package oop.practical.lispcalculator;

import oop.practical.lispcalculator.calculator.CalculateException;
import oop.practical.lispcalculator.calculator.Calculator;
import oop.practical.lispcalculator.lisp.Lisp;
import oop.practical.lispcalculator.lisp.ParseException;

import java.util.Scanner;

public final class Main {

    public static void main(String[] args) {
        var calculator = new Calculator();
        var scanner = new Scanner(System.in);
        while (true) {
            var input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            try {
                var ast = Lisp.parse(input);
                var result = calculator.visit(ast);
                System.out.println(result);
            } catch (ParseException e) {
                System.out.println("Error parsing input: " + e.getMessage());
            } catch (CalculateException e) {
                System.out.println("Error calculating expression: " + e.getMessage());
            }
        }
    }

}
