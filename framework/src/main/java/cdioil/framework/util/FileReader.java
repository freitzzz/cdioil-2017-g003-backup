package cdioil.framework.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Classe FileReader lê conteúdos de ficheiros
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class FileReader {
    /**
     * Método que lê o conteúdo de um ficheiro e retorna uma lista com o seu conteudo
     * @param file String com o path do ficheiro
     * @return List com o conteudo do ficheiro ou null caso o ficheiro não exista
     */
    public static List<String> readFile(File file){
        try{
            return Files.readAllLines(file.toPath());
        }catch(IOException e){
            return null;
        }
    }
    /**
     * Esconde o construtor privado
     */
    private FileReader(){}
}
