/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plhw;

/**
 *
 * @author atasu
 */
import java.util.List;
import java.util.ArrayList;

public class TurkishParser {
    private final List<TurkishLexer.Token> tokens;
    private int currentTokenIndex = 0;

    public TurkishParser(List<TurkishLexer.Token> tokens) {
        this.tokens = tokens;
    }

    public void parse() {
        while (!isAtEnd()) {
            parseStatement();
        }
    }

    private void parseStatement() {
        TurkishLexer.Token token = getCurrentToken();
        switch (token.getType()) {
            case IF:
                parseIfStatement();
                break;
            case FOR:
                parseForStatement();
                break;
            case WHILE:
                parseWhileStatement();
                break;
            case IDENTIFIER:
                parseAssignment();
                break;
            default:
                throw new IllegalArgumentException("Unexpected token: " + token);
        }
    }

    private void parseIfStatement() {
        consume(TurkishLexer.TokenType.IF);
        consume(TurkishLexer.TokenType.LPAREN);
        parseExpression();
        consume(TurkishLexer.TokenType.RPAREN);
        consume(TurkishLexer.TokenType.LBRACE);
        while (!check(TurkishLexer.TokenType.RBRACE)) {
            parseStatement();
        }
        consume(TurkishLexer.TokenType.RBRACE);
        if (match(TurkishLexer.TokenType.ELSE)) {
            consume(TurkishLexer.TokenType.LBRACE);
            while (!check(TurkishLexer.TokenType.RBRACE)) {
                parseStatement();
            }
            consume(TurkishLexer.TokenType.RBRACE);
        }
    }

    private void parseForStatement() {
        consume(TurkishLexer.TokenType.FOR);
        consume(TurkishLexer.TokenType.LPAREN);
        parseAssignment();
        consume(TurkishLexer.TokenType.SEMICOLON);
        parseExpression();
        consume(TurkishLexer.TokenType.SEMICOLON);
        parseAssignment();
        consume(TurkishLexer.TokenType.RPAREN);
        consume(TurkishLexer.TokenType.LBRACE);
        while (!check(TurkishLexer.TokenType.RBRACE)) {
            parseStatement();
        }
        consume(TurkishLexer.TokenType.RBRACE);
    }

    private void parseWhileStatement() {
        consume(TurkishLexer.TokenType.WHILE);
        consume(TurkishLexer.TokenType.LPAREN);
        parseExpression();
        consume(TurkishLexer.TokenType.RPAREN);
        consume(TurkishLexer.TokenType.LBRACE);
        while (!check(TurkishLexer.TokenType.RBRACE)) {
            parseStatement();
        }
        consume(TurkishLexer.TokenType.RBRACE);
    }

    private void parseAssignment() {
        consume(TurkishLexer.TokenType.IDENTIFIER);
        consume(TurkishLexer.TokenType.ASSIGN);
        parseExpression();
        consume(TurkishLexer.TokenType.SEMICOLON);
    }

    private void parseExpression() {
        parseTerm();
        while (match(TurkishLexer.TokenType.PLUS, TurkishLexer.TokenType.MINUS)) {
            parseTerm();
        }
    }

    private void parseTerm() {
        parseFactor();
        while (match(TurkishLexer.TokenType.MULT, TurkishLexer.TokenType.DIV)) {
            parseFactor();
        }
    }

    private void parseFactor() {
        if (match(TurkishLexer.TokenType.IDENTIFIER, TurkishLexer.TokenType.NUMBER)) {
            // Do nothing, token consumed by match()
        } else if (match(TurkishLexer.TokenType.LPAREN)) {
            parseExpression();
            consume(TurkishLexer.TokenType.RPAREN);
        } else {
            throw new IllegalArgumentException("Unexpected token: " + getCurrentToken());
        }
    }

    private boolean match(TurkishLexer.TokenType... types) {
        for (TurkishLexer.TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private void consume(TurkishLexer.TokenType type) {
        if (check(type)) {
            advance();
        } else {
            throw new IllegalArgumentException("Expected token of type " + type + " but got " + getCurrentToken());
        }
    }

    private boolean check(TurkishLexer.TokenType type) {
        if (isAtEnd()) return false;
        return getCurrentToken().getType() == type;
    }

    private TurkishLexer.Token advance() {
        if (!isAtEnd()) currentTokenIndex++;
        return getPreviousToken();
    }

    private boolean isAtEnd() {
        return currentTokenIndex >= tokens.size();
    }

    private TurkishLexer.Token getCurrentToken() {
        if (isAtEnd()) return null;
        return tokens.get(currentTokenIndex);
    }

    private TurkishLexer.Token getPreviousToken() {
        if (currentTokenIndex == 0) return null;
        return tokens.get(currentTokenIndex - 1);
    }
}

