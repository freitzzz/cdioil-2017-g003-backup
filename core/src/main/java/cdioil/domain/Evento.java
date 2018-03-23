package cdioil.domain;

import cdioil.domain.authz.UsersGroup;

/**
 * Interface criada para representar carateristicas que todos os eventos
 * têm em comum (e.g. público alvo).
 * 
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public interface Evento {
    
    /**
     * Devolve informacao de um Evento.
     * @return descricao do evento numa String
     */
    public abstract String info();
    
    /**
     * Devolve a lista de utilizadores a que o evento
     * se destina.
     * @return utilizadores aos quais o evento se destina
     */
    public abstract UsersGroup publicoAlvo();
    
    
}
