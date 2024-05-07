package oop.practical.lispcalculator.lisp;

record Token(
    Token.Type type,
    String value
) {

    enum Type {
        NUMBER,
        IDENTIFIER,
        OPERATOR,
    }

}
