package cdioil.backoffice.console.presentation;

import cdioil.application.ImportarUtilizadoresFicheirosController;
import cdioil.backoffice.console.utils.Console;
import cdioil.domain.authz.SystemUser;
import java.util.List;

/**
 * User Interface relativa ao caso de uso Importar Listas de Utilizadores através 
 * de ficheiros (US-111)
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public class ImportarUtilizadoresFicheirosUI {
    /**
     * Constante que representa o codigo de saida da User Interface
     */
    private static final String EXIT_CODE="Sair";
    /**
     * Constante que representa o codigo que permite saber se deve-se persistir 
     * os utilizadores importados na base de dados
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
     * Constante que repsenta a mensagem a quando nenhum utilizador tenha sido importado
     */
    private static final String MENSAGEM_NENHUM_UTILIZADOR_IMPORTADO="Nenhum utilizador válido foi importado!";
    /**
     * Constante que representa as mensagens a quando os utilizadores tenham sido importados com succeso
     */
    private static final String[] MENSAGEM_UTILIZADOES_IMPORTADOS={"#####Utilizadores Importados#####"
            ,"#####                       #####"};
    
    /**
     * Constante que representa a mensagem a quando a persistencia dos utilizadores na base de dados
     */
    private static final String MENSAGEM_PERSISTIR_BASE_DADOS="Pretende guardar os utilizadores importados na base de dados?"
                + "\nDigite \""+PERSISTIR_BASE_DADOS_CODE+"\" caso pretenda guardar ou qualquer outra mensagem para não guardar";
    /**
     * Constante que representa a mensagem a quando a persistencia dos utilizadores na base de dados 
     * tenha ocurrido com succeso
     */
    private static final String MENSAGEM_PERSISTENCIA_SUCESSO="Os utilizadores foram persistidos com sucesso!";
    /**
     * Constante que representa a mensagem a quando a persistencia dos utilizadores na base de dados 
     * não tenha ocurrido com succeso
     */
    private static final String MENSAGEM_PERSISTENCIA_FALHA="Ocorreu um erro a quando a persistencia dos utilizadores";
    /**
     * Constante que representa a mensagem de saida
     */
    private static final String MENSAGEM_SAIDA="A qualquer momento digite \""+EXIT_CODE+"\" para sair";
    /**
     * Controlador que controla a importação dos utilizadores atravé de ficheiros
     */
    private final ImportarUtilizadoresFicheirosController iufCtrl;
    /**
     * Constroi uma nova user interface que permite a interação entre o administrador 
     * e a importação de utilizadores através de ficheiros
     */
    public ImportarUtilizadoresFicheirosUI(){
        iufCtrl=new ImportarUtilizadoresFicheirosController();
        importarUtilizadores();
    }
    /**
     * Método que trata da interação entre o administrador e a importação dos utilizadores
     */
    private void importarUtilizadores(){
        System.out.println(MENSAGEM_SAIDA);
        boolean catched=false;
        while(!catched){
            String pathFicheiro=Console.readLine(MENSAGEM_CAMINHO_FICHEIRO);
            if(pathFicheiro.equalsIgnoreCase(EXIT_CODE))return;
            List<SystemUser> usersImportados=iufCtrl.readUtilizadores(pathFicheiro);
            if(usersImportados==null){
                String decisao=Console.readLine(MENSAGEM_CAMINHO_FICHEIRO_NAO_ENCONTRADO);
                if(decisao.equalsIgnoreCase(EXIT_CODE))return;
            }else{
                if(usersImportados.isEmpty()){
                    System.out.println(MENSAGEM_NENHUM_UTILIZADOR_IMPORTADO);
                }else{
                    System.out.println(MENSAGEM_UTILIZADOES_IMPORTADOS[0]);
                    usersImportados.forEach((t)->{
                        System.out.println(t);
                    });
                    System.out.println(MENSAGEM_UTILIZADOES_IMPORTADOS[1]);
                    catched=true;
                }
            }
        }
        persistirUtilizadores();
    }
    /**
     * Método que trata da persistencia de utilizadores na base de dados
     */
    private void persistirUtilizadores(){
        String persistir=Console.readLine(MENSAGEM_PERSISTIR_BASE_DADOS);
        if(persistir.equalsIgnoreCase(PERSISTIR_BASE_DADOS_CODE)){
            if(iufCtrl.gravarUtilizadores()){
                System.out.println(MENSAGEM_PERSISTENCIA_SUCESSO);
            }else{
                System.out.println(MENSAGEM_PERSISTENCIA_FALHA);
            }
        }
    }
}
