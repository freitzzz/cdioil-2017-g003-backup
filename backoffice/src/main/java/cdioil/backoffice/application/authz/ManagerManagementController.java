package cdioil.backoffice.application.authz;

import cdioil.domain.authz.Manager;
import cdioil.framework.dto.SystemUserDTO;
import cdioil.persistence.impl.ManagerRepositoryImpl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ManagerManagementController {

    public List<SystemUserDTO> findAllSystemUserDTO() {
        List<SystemUserDTO> allManagersDTO = new LinkedList<>();

        Iterator<Manager> allManagers = new ManagerRepositoryImpl().findAll().iterator();

        while (allManagers.hasNext()) {
            Manager manager = allManagers.next();

            allManagersDTO.add(manager.toDTO());
        }

        return allManagersDTO;
    }

    public List<SystemUserDTO> findFilteredSystemUserDTO(String searchTerm) {
        List<SystemUserDTO> filteredSystemUserDTOList = new LinkedList<>();

        Iterator<Manager> allManagers = new ManagerRepositoryImpl().findAll().iterator();

        while (allManagers.hasNext()) {
            Manager manager = allManagers.next();

            if (manager.toString().toLowerCase().contains(searchTerm.trim().toLowerCase())) {
                filteredSystemUserDTOList.add(manager.toDTO());
            }
        }

        return filteredSystemUserDTOList;
    }
}
