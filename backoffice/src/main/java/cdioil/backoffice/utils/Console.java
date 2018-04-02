/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simula uma Consola
 * @author Ana Guerra (1161191)
 */
public class Console {
    /**
     * Lê o input fornecido
     * @param prompt String de entrada
     * @return String do buffer
     */
     public static String readLine(String prompt) {
        try {
            System.out.println(prompt);
            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);
            return in.readLine();
        } catch (Exception ex1) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                    null, ex1);
            return null;
        }

    }
    /**
     * Lê o input fornecido em String e coverte para um inteiro.
     * @param prompt String de entrada
     * @return o valor convertido
     */
    public static int readInteger(String prompt) {
        do {
            try {
                String strInt = readLine(prompt);
                int valor = Integer.parseInt(strInt);

                return valor;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Console.class.getName()).log(Level.SEVERE,
                        null, ex);
            }

        } while (true);
    }
    
}
