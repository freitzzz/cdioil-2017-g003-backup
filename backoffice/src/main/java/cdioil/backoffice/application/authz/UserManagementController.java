package cdioil.backoffice.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.framework.dto.SystemUserDTO;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UserManagementController implements Serializable {

    private static final long serialVersionUID = 10L;

    /**
     * Finds a list of all SystemUsers, converts each one into a DTO
     * and then adds to a list
     * @return list of SystemUserDTOs
     */
    public List<SystemUserDTO> findAllSystemUsersDTO() {
        List<SystemUserDTO> systemUserDTOList = new LinkedList<>();

        // Fetch all System Users from DB
        Iterator<SystemUser> allSystemUsers = new UserRepositoryImpl().findAll().iterator();

        while (allSystemUsers.hasNext()) {
            SystemUser systemUser = allSystemUsers.next();

            systemUserDTOList.add(systemUser.toDTO());
        }

        return systemUserDTOList;
    }

    public List<SystemUserDTO> findFilteredSystemUsersDTO(String searchTerm) {
        List<SystemUserDTO> filteredSystemUserDTOList = new ArrayList<>();

        Iterator<SystemUser> allSystemUsers = new UserRepositoryImpl().findAll().iterator();

        while (allSystemUsers.hasNext()) {
            SystemUser systemUser = allSystemUsers.next();

            if (systemUser.toString().toLowerCase().contains(searchTerm)) {
                filteredSystemUserDTOList.add(systemUser.toDTO());
            }
        }

        return filteredSystemUserDTOList;
    }
}
