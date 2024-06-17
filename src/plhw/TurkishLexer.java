/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package plhw;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.List;

public class TurkishLexer {
    private static final Map<String, TokenType> keywords = new HashMap<>();
    private static final String TOKEN_REGEX = "\\s*(\\d+|\\w+|[{}()=;<>!+-/*])\\s*";
    private static final Pattern TOKEN_PATTERN = Pattern.compile(TOKEN_REGEX);
    private Matcher matcher;
    private String input;

    static {
        keywords.put("eğer", TokenType.IF);
        keywords.put("aksi", TokenType.ELSE);
        keywords.put("takdirde", TokenType.ELSE);
        keywords.put("için", TokenType.FOR);
        keywords.put("iken", TokenType.WHILE);
        // Diğer anahtar kelimeler eklenebilir
    }

    public TurkishLexer(String input) {
        this.input = input;
        matcher = TOKEN_PATTERN.matcher(input);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (matcher.find()) {
            String tokenText = matcher.group().trim();
            TokenType type = determineTokenType(tokenText);
            tokens.add(new Token(type, tokenText));
        }
        return tokens;
    }

    private TokenType determineTokenType(String tokenText) {
        if (keywords.containsKey(tokenText)) {
            return keywords.get(tokenText);
        }
        if (tokenText.matches("\\d+")) {
            return TokenType.NUMBER;
        }
        if (tokenText.matches("\\w+")) {
            return TokenType.IDENTIFIER;
        }
        switch (tokenText) {
            case "=": return TokenType.ASSIGN;
            case "+": return TokenType.PLUS;
            case "-": return TokenType.MINUS;
            case "*": return TokenType.MULT;
            case "/": return TokenType.DIV;
            case "(": return TokenType.LPAREN;
            case ")": return TokenType.RPAREN;
            case "{": return TokenType.LBRACE;
            case "}": return TokenType.RBRACE;
            case ";": return TokenType.SEMICOLON;
            case "==": return TokenType.EQ;
            case "!=": return TokenType.NE;
            case "<": return TokenType.LT;
            case ">": return TokenType.GT;
            case "<=": return TokenType.LE;
            case ">=": return TokenType.GE;
            default: throw new IllegalArgumentException("Unknown token: " + tokenText);
        }
    }

    public static class Token {
        private final TokenType type;
        private final String text;

        public Token(TokenType type, String text) {
            this.type = type;
            this.text = text;
        }

        public TokenType getType() {
            return type;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return String.format("(%s, '%s')", type, text);
        }
    }

    public enum TokenType {
        IF, ELSE, FOR, WHILE, IDENTIFIER, NUMBER, ASSIGN, PLUS, MINUS, MULT, DIV, SEMICOLON,
        LPAREN, RPAREN, LBRACE, RBRACE, EQ, NE, LT, GT, LE, GE
    }
}

