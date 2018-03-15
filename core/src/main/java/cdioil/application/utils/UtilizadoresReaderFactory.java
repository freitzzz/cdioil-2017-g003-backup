package cdioil.application.utils;

/**
 * Factory de UtilizadoresReader
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class UtilizadoresReaderFactory {
    /**
     * Constante que representa a extensão de ficheiros de texto
     */
    private static final String PLAIN_TEXT_EXTENSION=".txt";
    /**
     * Constante que representa extensão de ficheiros XML
     */
    private static final String XML_EXTENSION=".xml";
    /**
     * Constante que representa a extensão de ficheiros CSV
     */
    private static final String CSV_EXTENSION=".csv";
    /**
     * Cria um novo UtilizadoresReader conforme o formato do ficheiro a ser lido
     * @param ficheiro String com o nome do ficheiro a ser lido
     * @return UtilizadoresReader com o parser respetivo a cada tipo de ficheiro
     */
    public static UtilizadoresReader create(String ficheiro){
        if(ficheiro.endsWith(CSV_EXTENSION)){
            return new CSVUtilizadoresReader(ficheiro);
        }
        return null;
    }
    /**
     * Esconde o construtor privado
     */
    private UtilizadoresReaderFactory(){}
}
