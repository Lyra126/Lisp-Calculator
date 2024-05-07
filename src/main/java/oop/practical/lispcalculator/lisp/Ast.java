package oop.practical.lispcalculator.lisp;

import java.math.BigDecimal;
import java.util.List;

public sealed interface Ast {

    record Number(
        BigDecimal value
    ) implements Ast {}

    record Variable(
        String name
    ) implements Ast {}

    record Function(
        String name,
        List<Ast> arguments
    ) implements Ast {}

}
