package cdioil.backoffice.application.authz;

import cdioil.domain.authz.SystemUser;
import cdioil.framework.dto.SystemUserDTO;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class UserManagementController implements Serializable {

    private static final long serialVersionUID = 10L;
    
    private UserRepositoryImpl userRepository;

    public UserManagementController() {
        userRepository = new UserRepositoryImpl();
    }

    public List<SystemUserDTO> findAllSystemUsersDTO() {
        List<SystemUserDTO> systemUserDTOList = new LinkedList<>();

        // Fetch all System Users from DB
        Iterator<SystemUser> allSystemUsers = userRepository.findAll().iterator();

        while (allSystemUsers.hasNext()) {
            SystemUser systemUser = allSystemUsers.next();

            systemUserDTOList.add(systemUser.toDTO());
        }

        return systemUserDTOList;
    }

    public List<SystemUserDTO> findFilteredSystemUsersDTO() {
        return null;
    }
}
