package cdioil.domain;

import cdioil.domain.authz.GrupoUtilizadores;

/**
 * Abstract class that represents Events.
 *
 * @author <a href="1160936@isep.ipp.pt">Gil Durão</a>
 */
public abstract class Event {

    /**
     * Serialization number.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Survey's target audience.
     */
    private GrupoUtilizadores targetAudience;

    public Event(GrupoUtilizadores targetAudience) {
        if (targetAudience == null) {
            throw new IllegalArgumentException("O público alvo não"
                    + " pode ser null");
        }
        this.targetAudience = targetAudience;
    }

    protected Event() {
        //For ORM
    }

    /**
     * Returns the info of an event
     *
     * @return event's description
     */
    public abstract String info();

    /**
     * Returns the event's target audience
     *
     * @return event's target audience
     */
    public GrupoUtilizadores targetAudience(){
        return targetAudience;
    }

}
