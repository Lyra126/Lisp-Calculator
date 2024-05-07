package oop.practical.lispcalculator.calculator;

import oop.practical.lispcalculator.lisp.Lisp;
import oop.practical.lispcalculator.lisp.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

public class CalculatorTests {

    @ParameterizedTest
    @MethodSource
    void testNumber(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testNumber() {
        return Stream.of(
            Arguments.of("Integer", "1", new BigDecimal("1")),
            Arguments.of("Decimal", "1.0", new BigDecimal("1.0"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testIdentifier(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testIdentifier() {
        return Stream.of(
            Arguments.of("E", "e", BigDecimal.valueOf(Math.E)),
            Arguments.of("PI", "pi", BigDecimal.valueOf(Math.PI)),
            // An expected value of null means a CalculateException is thrown
            Arguments.of("Undefined", "undefined", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testAdd(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testAdd() {
        return Stream.of(
            Arguments.of("Empty", "(add)", new BigDecimal("0")),
            Arguments.of("Single", "(add 1)", new BigDecimal("1")),
            Arguments.of("Multiple", "(add 1 2 3)", new BigDecimal("6")),
            Arguments.of("Symbol", "(+)", new BigDecimal("0"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSub(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testSub() {
        return Stream.of(
                Arguments.of("Empty", "(sub)", null),
                Arguments.of("Single", "(sub 1)", new BigDecimal("-1")),
                Arguments.of("Multiple", "(sub 1 2 3)", new BigDecimal("-4")),
                Arguments.of("Symbol", "(- 1)", new BigDecimal("-1"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMul(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testMul() {
        return Stream.of(
                Arguments.of("Empty", "(mul)", new BigDecimal("1")),
                Arguments.of("Single", "(mul 2)", new BigDecimal("2")),
                Arguments.of("Zero", "(mul 0 0)", new BigDecimal("0")),
                Arguments.of("Single", "(mul 2.5 2.5)", new BigDecimal("6.25")),
                Arguments.of("Multiple", "(mul 2 3 4)", new BigDecimal("24")),
                Arguments.of("Symbol", "(*)", new BigDecimal("1"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testDiv(String test, String input, BigDecimal expected) {
        test(input, expected);
    }

    public static Stream<Arguments> testDiv() {
        return Stream.of(
                Arguments.of("Empty", "(div)", null),
                Arguments.of("Single(r)", "(div 2)", new BigDecimal("0")),
                Arguments.of("Single", "(div 2.0)", new BigDecimal("0.5")),
                Arguments.of("Multiple", "(div 1.0 2.0 3.0)", new BigDecimal("0.2")),
                Arguments.of("Symbol", "(/ 2.0)", new BigDecimal("0.5")),
                Arguments.of("DBZ (1)", "(div 0 0)", null),
                Arguments.of("DBZ (2)", "(div 1 0)", null),
                Arguments.of("DBZ (3)", "(div 0 1)", new BigDecimal("0")),
                Arguments.of("Pair Rounding(1)", "(div 6 4)", new BigDecimal("2")),
                Arguments.of("Pair Rounding(2)", "(div 10 4)", new BigDecimal("2")),
                Arguments.of("Pair Rounding(3)", "(div 7 9)", new BigDecimal("1")),
                Arguments.of("Pair Rounding(4)", "(div 9 8)", new BigDecimal("1")),
                Arguments.of("Pair Rounding(5)", "(div 10.0 5.0)", new BigDecimal("2.0"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testPow(String test, String input, BigDecimal expected) {
        test(input, expected);
    }
    public static Stream<Arguments> testPow() {
        return Stream.of(
                Arguments.of("Empty", "(pow)", null),
                Arguments.of("Single", "(pow 2)", null),
                Arguments.of("Regular", "(pow 2 3)", new BigDecimal("8")),
                Arguments.of("> 2", "(pow 1 2 3)", null),
                Arguments.of("Non-Int", "(pow 2 2.5)", null),
                Arguments.of("Negative", "(pow 2 -1)", null),
                Arguments.of("Big Num", "(pow 2 4294967298)", null) //above max

        );
    }

    @ParameterizedTest
    @MethodSource
    void testSqrt(String test, String input, BigDecimal expected) {
        test(input, expected);
    }
    public static Stream<Arguments> testSqrt() {
        return Stream.of(
                Arguments.of("Empty", "(sqrt)", null),
                Arguments.of("Regular", "(sqrt 4)", new BigDecimal("2")),
                Arguments.of("Regular with Dec", "(sqrt 16.0)", new BigDecimal("4")),
                Arguments.of("Regular with Dec", "(sqrt 4.0)", new BigDecimal("2")),
                Arguments.of("Neg", "(sqrt -1)", null),
                //Specification
                Arguments.of(" Integer (1)", "(sqrt 4)", new BigDecimal("2")),
                Arguments.of(" Perfect Square Precision (1)", "(sqrt 4.0)", new BigDecimal("2")),
                Arguments.of("Decimal (1)", "(sqrt 2.000)", new BigDecimal("1.414")),
                Arguments.of("Zero (1)", "(sqrt 0)", new BigDecimal("0")),
                Arguments.of("Fraction (1)", "(sqrt 0.25)", new BigDecimal("0.5")),
                Arguments.of("Negative (1)", "(sqrt -1)", null),
                Arguments.of("Missing Arguments (1)", "(sqrt)", null),
                Arguments.of("Excess Arguments (1)", "(sqrt 1 2)", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testRem(String test, String input, BigDecimal expected) {
        test(input, expected);
    }
    public static Stream<Arguments> testRem() {
        return Stream.of(
                Arguments.of("Pos Pos", "(rem 7 2)", new BigDecimal("1")),
                Arguments.of("Neg Pos", "(rem -7 2)", new BigDecimal("-1")),
                Arguments.of("Pos Neg", "(rem 7 -2)", new BigDecimal("1")),
                Arguments.of("Neg Neg", "(rem -7 -2)", new BigDecimal("-1")),
                Arguments.of("DBZ (1)", "(rem 7 0)", null),
                Arguments.of("DBZ (2)", "(rem 0 0)", null),
                Arguments.of("DBZ (3)", "(rem 0 8)", new BigDecimal("0"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMod(String test, String input, BigDecimal expected) {
        test(input, expected);
    }
    public static Stream<Arguments> testMod() {
        return Stream.of(
                Arguments.of("Pos Pos", "(mod 7 2)", new BigDecimal("1")),
                Arguments.of("Neg Pos (1)", "(mod -7 2)", new BigDecimal("1")),
                Arguments.of("Neg Pos (2)", "(mod -7 3)", new BigDecimal("2")),
                Arguments.of("Neg Neg", "(mod -7 -2)", null),
                Arguments.of("Pos Neg", "(mod 7 -2)", null),
                Arguments.of("DBZ (1)", "(mod 7 0)", null),
                Arguments.of("DBZ (2)", "(mod 0 0)", null),
                Arguments.of("DBZ (3)", "(mod 0 8)", new BigDecimal("0")),
                Arguments.of("DBZ (3)", "(mod 8 4)", new BigDecimal("0"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void testSin(String test, String input, BigDecimal expected) {
        test(input, expected);
    }
    public static Stream<Arguments> testSin() {
        return Stream.of(
                Arguments.of("Zero", "(sin 0)", new BigDecimal("0.0")),
                Arguments.of("One", "(sin 1.0)", new BigDecimal("0.8414709848078965")),
                Arguments.of("PI/2", "(sin 1.5707963267948966)", new BigDecimal("1.0")),
                Arguments.of("Above 2PI", "(sin 31.41592653589793)", new BigDecimal("-1.2246467991473533E-15")),
                Arguments.of("Big Value", "(sin 999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999)", null),
                Arguments.of("Undefined", "(sin ???)", null),
                Arguments.of("Missing Arguments", "(sin)", null),
                Arguments.of("Excessive Arguments", "(sin 1 2)", null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCos(String test, String input, BigDecimal expected) {
        test(input, expected);
    }
    public static Stream<Arguments> testCos() {
        return Stream.of(
                Arguments.of("Zero", "(cos 0)", new BigDecimal("1.0")),
                Arguments.of("Undefined", "(cos ???)", null),
                Arguments.of("Big Value", "(cos 999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999)", null),
                Arguments.of("Excessive Arguments", "(cos 1 2)", null),
                Arguments.of("Undefined", "(cos ???)", null),
                Arguments.of("Missing Arguments", "(cos)", null)
        );
    }

    private static void test(String input, BigDecimal expected) {
        if (expected != null) {
            var result = Assertions.assertDoesNotThrow(() -> new Calculator().visit(Lisp.parse(input)));
            Assertions.assertEquals(expected, result);
        } else {
            Assertions.assertThrows(CalculateException.class, () -> new Calculator().visit(Lisp.parse(input)));
        }
    }

}
