package cdioil.backoffice.application.authz;

import cdioil.domain.authz.Manager;
import cdioil.framework.dto.ManagerDTO;
import cdioil.persistence.impl.ManagerRepositoryImpl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ManagerManagementController {

    public List<ManagerDTO> findAllManagerDTO() {
        List<ManagerDTO> allManagersDTO = new LinkedList<>();

        Iterator<Manager> allManagers = new ManagerRepositoryImpl().findAll().iterator();

        while (allManagers.hasNext()) {
            Manager manager = allManagers.next();

            allManagersDTO.add(manager.toDTO());
        }

        return allManagersDTO;
    }

    public List<ManagerDTO> findFilteredManagerDTO(String searchTerm) {
        List<ManagerDTO> filteredManagerDTOList = new LinkedList<>();

        Iterator<Manager> allManagers = new ManagerRepositoryImpl().findAll().iterator();

        while (allManagers.hasNext()) {
            Manager manager = allManagers.next();

            if (manager.toString().toLowerCase().contains(searchTerm.trim().toLowerCase())) {
                filteredManagerDTOList.add(manager.toDTO());
            }
        }

        return filteredManagerDTOList;
    }
}
