package cdioil.application.utils;

import cdioil.domain.authz.Email;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária que lê e faz parse
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class CSVUtilizadoresReader implements UtilizadoresReader{
    /**
     * Constante que representa o identificador da coluna email no ficheiro CSV
     */
    private static final String IDENTIFICADOR_COLUNA_EMAIL="EMAIL";
    /**
     * Constante que representa o identificador da coluna nome no ficheiro CSV
     */
    private static final String IDENTIFICADOR_COLUNA_NOME="NOME";
    /**
     * Constante que representa o identificador da coluna apelido no ficheiro CSV
     */
    private static final String IDENTIFICADOR_COLUNA_APELIDO="APELIDO";
    /**
     * Constante que representa o identificador do delimitador de cada coluna no 
     * ficheiro CSV
     */
    private static final String IDENTIFICADOR_DELIMITADOR=";";
    /**
     * Constante que representa o numero da linha que contém os identificadores 
     * do ficheiro CSV
     */
    private static final int NUMERO_LINHA_IDENTIFICADOR=0;
    /**
     * Constante que representa o numero de identificadores existentes no ficheiro CSV
     */
    private static final int NUMERO_IDENTIFICADORES=3;
    /**
     * File com o ficheiro a ser lido
     */
    private final File file;
    /**
     * Integer que representa o numero da coluna do campo email
     */
    private int identificadorEmail;
    /**
     * Integer que representa o numero da coluna do campo nome
     */
    private int identificadorNome;
    /**
     * Integer que representa o numero da coluna do campo apelido
     */
    private int identificadorApelido;
    /**
     * Constrói uma nova instância de CSVUtilizadoresReader com o caminho do 
     * ficheiro a ser lido
     * @param filename String com o caminho do ficheiro a ser lido
     */
    public CSVUtilizadoresReader(String filename){this.file=new File(filename);}
    /**
     * Método que através de um determinado ficheiro CSV lê uma lista de utilizadores 
     * válidos
     * @return List com todos os utilizadores válidos lidos do ficheiro
     */
    @Override
    public List<SystemUser> read(){
        List<String> fileContent=FileReader.readFile(file);
        if(fileContent==null)return null;
        List<SystemUser> usersLidos=new ArrayList<>();
        if(fileContent.size()<=1)return usersLidos;
        String[] camposFicheiro=fileContent.get(NUMERO_LINHA_IDENTIFICADOR).split(IDENTIFICADOR_DELIMITADOR);
        
        if(camposFicheiro.length!=NUMERO_IDENTIFICADORES)return null;
        identificarCampos(camposFicheiro);
        for(int i=1;i<fileContent.size();i++){
            String[] nextCampos=fileContent.get(i).split(IDENTIFICADOR_DELIMITADOR);
            if(nextCampos.length==NUMERO_IDENTIFICADORES){
                try{
                    usersLidos.add(new SystemUser(new Email(nextCampos[identificadorEmail])
                            ,new Name(nextCampos[identificadorNome],nextCampos[identificadorApelido])
                            ,new Password(Password.DEFAULT_PASSWORD)));
                }catch(IllegalArgumentException e){
                    System.out.println(e);
                }
            }
        }
        return usersLidos;
    }
    /**
     * Método que identifica os campos de cada identificador do ficheiro CSV
     * @param campos Array com todos os campos dos identificadores do fichero CSV
     */
    private void identificarCampos(String[] campos){
        for(int i=0;i<campos.length;i++){
            if(campos[i].equalsIgnoreCase(IDENTIFICADOR_COLUNA_EMAIL))this.identificadorEmail=i;
            if(campos[i].equalsIgnoreCase(IDENTIFICADOR_COLUNA_NOME))this.identificadorNome=i;
            if(campos[i].equalsIgnoreCase(IDENTIFICADOR_COLUNA_APELIDO))this.identificadorApelido=i;
        }
    }
}
