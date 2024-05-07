package oop.practical.lispcalculator.calculator;

import java.math.BigDecimal;
import java.util.List;
import java.math.RoundingMode;
import java.math.MathContext;

final class Functions {

    static BigDecimal add(List<BigDecimal> arguments) {
        var result = BigDecimal.ZERO;
        for (var number : arguments) {
            result = result.add(number);
        }
        return result;
    }

    // subtract all the elements in the list from the first element, start the loop from index 1
    // subtracting each element from the result, start with the first element, not zero.
    static BigDecimal sub(List<BigDecimal> arguments) throws CalculateException  {
        //if list is empty
        if(arguments.isEmpty())
            throw new CalculateException("No arguments");

        //if list only has one argument, subtract against 0
        if(arguments.size() == 1)
            return BigDecimal.ZERO.subtract(arguments.getFirst());

        //if list has multiple arguments
        var result = arguments.getFirst();
        for (int i = 1; i < arguments.size(); i++)
            if (arguments.get(i) != null)
                result = result.subtract(arguments.get(i));

        return result;
    }

    static BigDecimal mul(List<BigDecimal> arguments) {
        //if list is empty, return 1
        if(arguments.isEmpty())
            return BigDecimal.ONE;

        //if list has 1 item, return it
        if(arguments.size() == 1)
            return BigDecimal.ONE.multiply(arguments.getFirst());

        //if item has multiple items, multiply all
        var result = BigDecimal.ONE;
        for (var number : arguments) {
            result = result.multiply(number);
        }
        return result;
    }

    static BigDecimal div(List<BigDecimal> arguments)throws CalculateException{
        // if list is empty throw exception
        if (arguments.isEmpty())
            throw new CalculateException("Unable to divide nothing");

        // if 1 argument, return it
        if (arguments.size() == 1) {
            if (arguments.getFirst().equals(BigDecimal.ZERO))
                throw new CalculateException("Division by zero");
            return BigDecimal.ONE.divide(arguments.getFirst(), arguments.getFirst().scale(), RoundingMode.HALF_EVEN);
        }

        var result = arguments.getFirst();  // Start with the first argument

        for (int i = 1; i < arguments.size(); i++) {
            if (arguments.get(i).equals(BigDecimal.ZERO))
                throw new CalculateException("Division by Zero");
            result = result.divide(arguments.get(i), result.scale(), RoundingMode.HALF_EVEN);
        }

        return result;
    }

    static BigDecimal pow(List<BigDecimal> arguments)throws CalculateException {
        if(arguments.size() != 2)
            throw new CalculateException("Error! Needs 2 arguments");
        try {
            return arguments.getFirst().pow(arguments.get(1).intValueExact());
        }catch(ArithmeticException e){
            throw new CalculateException("Invalid Arguments");
        }
    }

    static BigDecimal sqrt(List<BigDecimal> arguments)throws CalculateException {
        if(arguments.size() != 1)
            throw new CalculateException("Error! Needs 1 argument");
        if(arguments.getFirst().compareTo(BigDecimal.ZERO) < 0)
            throw new CalculateException("Error! Unable to sqrt negative numbers");
        return arguments.getFirst().sqrt(new MathContext(arguments.getFirst().precision(), RoundingMode.HALF_EVEN));
    }

    static BigDecimal rem(List<BigDecimal> arguments) throws CalculateException{
        if(arguments.size() != 2)
            throw new CalculateException("Error! Needs 2 arguments");

        //arguments.getFirst() = dividend, arguments.get(1) = divisor
        //prevents zero divisor
        if (arguments.get(1).compareTo(BigDecimal.ZERO) == 0)
            throw new CalculateException("Error! Division by zero");

        return arguments.getFirst().remainder(arguments.get(1));
    }

    static BigDecimal mod(List<BigDecimal> arguments) throws CalculateException{
        if(arguments.size() != 2)
            throw new CalculateException("Error! Needs 2 arguments");
        //arguments.getFirst() = dividend, arguments.get(1) = divisor
        if (arguments.get(1).compareTo(BigDecimal.ZERO) == 0) //prevents zero divisor
            throw new CalculateException("Error! Division by zero");
        else if (arguments.get(1).compareTo(BigDecimal.ZERO) < 0) //divisor can't be neg
            throw new CalculateException("Error! Ambiguous Operation");
        return arguments.getFirst().remainder(arguments.get(1)).add(arguments.get(1)).remainder(arguments.get(1));
    }

    static BigDecimal sin(List<BigDecimal> arguments) throws CalculateException{
        if(arguments.size() != 1)
            throw new CalculateException("Error! Needs 1 argument");
        if(arguments.getFirst().compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) > 0 || arguments.getFirst().compareTo(BigDecimal.valueOf(-Double.MAX_VALUE)) < 0)
            throw new CalculateException("Error! Argument is too large");
        return BigDecimal.valueOf(Math.sin(arguments.getFirst().doubleValue()));
    }

    static BigDecimal cos(List<BigDecimal> arguments) throws CalculateException{
        if(arguments.size() != 1)
            throw new CalculateException("Error! Needs 1 argument");
        if(arguments.getFirst().compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) > 0 || arguments.getFirst().compareTo(BigDecimal.valueOf(-Double.MAX_VALUE)) < 0)
            throw new CalculateException("Error! Argument is too large");
        if (arguments.getFirst().doubleValue() > 2 * Math.PI)
            throw new CalculateException("Error! Argument is above 2π");
        return BigDecimal.valueOf(Math.cos(arguments.getFirst().doubleValue()));
    }

}
