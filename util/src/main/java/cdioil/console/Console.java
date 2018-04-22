/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.frontoffice.utils;

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
    /**
     * Logs in console a certain message with a certain color
     * @param outputToLog String with the message to be logged
     * @param color ConsoleColor enum with the color being used
     */
    public static void log(String outputToLog,ConsoleColors color){
        System.out.println(color+outputToLog+ConsoleColors.RESET);
    }
    /**
     * Logs in console a certain message with the red color (Like err)
     * @param outputToLog String with the message to be logged
     */
    public static void logError(String outputToLog){
        System.out.println(ConsoleColors.RED+outputToLog+ConsoleColors.RESET);
    }
    /**
     * Enum for ANSI representations of colors that can be used in console
     */
    public enum ConsoleColors{
        RED{@Override public String toString(){return "\u001B[31m";}},
        BLUE{@Override public String toString(){return "\u001B[34m";}},
        GREEN{@Override public String toString(){return "\u001B[32m";}},
        YELLOW{@Override public String toString(){return "\u001B[33m";}},
        PURPLE{@Override public String toString(){return "\u001B[35m";}},
        WHITE{@Override public String toString(){return "\u001B[37m";}},
        CYAN{@Override public String toString(){return "\u001B[36m";}},
        RESET{@Override public String toString(){return "\u001B[0m";}}
    }
    
}
