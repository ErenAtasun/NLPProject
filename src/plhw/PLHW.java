/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package plhw;

/**
 *
 * @author atasu
 */

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PLHW {


    public static void main(String[] args) {
        try {
            // ornek1.tur dosyasının yolunu burada belirtin
            String input = new String(Files.readAllBytes(Paths.get("ornek1.tur")));
            TurkishLexer lexer = new TurkishLexer(input);
            List<TurkishLexer.Token> tokens = lexer.tokenize();
            TurkishParser parser = new TurkishParser(tokens);
            parser.parse();
            System.out.println("Parsing completed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
   

