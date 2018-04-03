/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cdioil.backoffice.console.presentation;

import cdioil.application.ImportQuestionsController;
import cdioil.backoffice.utils.Console;
import cdioil.domain.Question;
import java.util.List;

/**
 * User Interface relativa ao caso de uso Importar Questoes através 
 * de ficheiros (US-210)
 * @author Ana Guerra (1161191)
 */
public class ImportQuestionsUI {
    /**
     * Constante que representa o codigo de saida da User Interface
     */
    private static final String EXIT_CODE="Sair";
    /**
     * Constante que representa o codigo que permite saber se deve persistir 
     * as questões importadas na base de dados
     */
    private static final String PERSISTIR_BASE_DADOS_CODE="Sim";
    /**
     * Constante que representa a mensagem que pede o caminho para o ficheiro a ser importado
     */
    private static final String MENSAGEM_CAMINHO_FICHEIRO="Por favor indique o caminho do "
            + "ficheiro que pretende importar";
     /**
     * Constante que representa a mensagem de erro a quando o caminho do ficheiro 
     * não é encontrado
     */
    private static final String MENSAGEM_CAMINHO_FICHEIRO_NAO_ENCONTRADO="Caminho de ficheiro "
            + "não encontrado!\nPretendo continuar? Digite \""+EXIT_CODE+"\" para sair "
            + "ou qualquer outra mensagem para continuar";
    /**
     * Constante que representa a mensagem quando nenhuma questão foi importada
     */
    private static final String MENSAGEM_NENHUMA_QUESTAO_IMPORTADO="Nenhuma questão válido foi importada!";
    /**
     * Constante que representa as mensagens quando as questões são importadas com succeso
     */
    private static final String[] MENSAGEM_QUESTOES_IMPORTADAS={"#####Questões Importadas#####"
            ,"#####                       #####"};
    
    /**
     * Constante que representa a mensagem quando a persistencia das questões na base de dados
     */
    private static final String MENSAGEM_PERSISTIR_BASE_DADOS="Pretende guardar o novo template importados na base de dados?"
                + "\nDigite \""+PERSISTIR_BASE_DADOS_CODE+"\" caso pretenda guardar ou qualquer outra mensagem para não guardar";
    /**
     * Constante que representa a mensagem quando a persistencia das questoes na base de dados 
     * tenha ocurrido com succeso
     */
    private static final String MENSAGEM_PERSISTENCIA_SUCESSO="O template foi persistidos com sucesso!";
    /**
     * Constante que representa a mensagem a quando a persistencia das questões  na base de dados 
     * não tenha ocurrido com succeso
     */
    private static final String MENSAGEM_PERSISTENCIA_FALHA="Ocorreu um erro a quando a persistencia das questões";
    /**
     * Constante que representa a mensagem de saida
     */
    private static final String MENSAGEM_SAIDA="A qualquer momento digite \""+EXIT_CODE+"\" para sair";
    /**
     * Controlador que controla a importação das questões atravé de ficheiros
     */
    private final ImportQuestionsController iQueCtrl;
    /**
     * Constroi uma nova user interface que permite a interação entre o administrador 
     * e a importação de utilizadores através de ficheiros
     */
    public ImportQuestionsUI(){
        iQueCtrl=new ImportQuestionsController();
        importarQuestoes();
    }
    /**
     * Método que trata da interação entre o gestor e a importação das questões
     */
    private void importarQuestoes(){
        System.out.println(MENSAGEM_SAIDA);
        boolean catched=false;
        while(!catched){
            String pathFicheiro=Console.readLine(MENSAGEM_CAMINHO_FICHEIRO);
            if(pathFicheiro.equalsIgnoreCase(EXIT_CODE))return;
            List<Question> listaQuestoes=iQueCtrl.lerFicheiro(pathFicheiro);
            if(listaQuestoes==null){
                String decisao=Console.readLine(MENSAGEM_CAMINHO_FICHEIRO_NAO_ENCONTRADO);
                if(decisao.equalsIgnoreCase(EXIT_CODE))return;
            }else{
                if(listaQuestoes.isEmpty()){
                    System.out.println(MENSAGEM_NENHUMA_QUESTAO_IMPORTADO);
                }else{
                    System.out.println(MENSAGEM_QUESTOES_IMPORTADAS[0]);
                    listaQuestoes.forEach((t)->{
                        System.out.println(t);
                    });
                    System.out.println(MENSAGEM_QUESTOES_IMPORTADAS[1]);
                    catched=true;
                }
            }
        }
        persistirQuestoes();
    }
    /**
     * Método que trata da persistencia do template na base de dados
     */
    private void persistirQuestoes(){
//        String persistir=Console.readLine(MENSAGEM_PERSISTIR_BASE_DADOS);
//        if(persistir.equalsIgnoreCase(PERSISTIR_BASE_DADOS_CODE)){
//            if(iQueCtrl.gravarTemplate()){
//                System.out.println(MENSAGEM_PERSISTENCIA_SUCESSO);
//            }else{
//                System.out.println(MENSAGEM_PERSISTENCIA_FALHA);
//            }
//        }
    }
    
}
