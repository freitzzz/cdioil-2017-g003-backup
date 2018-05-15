package cdioil.domain.authz;

import cdioil.framework.dto.SystemUserDTO;
import java.io.Serializable;

/**
 * Blank Interface that serves as a way to mark users
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 * @since Version 4.0 of FeedbackMonkey
 */
public interface User extends Serializable{
    /**
     * Method that returns a DTO of the current SystemUser
     * @return SystemUserDTO with the DTO of the current SystemUser
     */
    public SystemUserDTO toDTO();
}