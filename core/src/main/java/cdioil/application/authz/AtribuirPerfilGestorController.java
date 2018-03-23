package cdioil.application.authz;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class AtribuirPerfilGestorController {

    /**
     * Implementação do Repositorio de SystemUsers
     */
    private UserRepositoryImpl systemUserRepository;

    /**
     * Implementação do Repositorio de Gestores
     */
    private ManagerRepositoryImpl gestorRepository;

    /**
     * Implementação do Repositório de Admins
     */
    private AdminRepositoryImpl adminRepository;

    /**
     * Construtor
     */
    public AtribuirPerfilGestorController() {
        this.systemUserRepository = new UserRepositoryImpl();
        this.gestorRepository = new ManagerRepositoryImpl();
        this.adminRepository = new AdminRepositoryImpl();
    }

    /**
     * Procura todos os SystemUsers que nao sejam gestores ou admins
     * @return lista de System Users
     */
    public ArrayList<String> getListaUsersRegistados() {

        // Lista de System user
        List<SystemUser> systemUsers =
                (List<SystemUser>) systemUserRepository.findAll();

        // Lista de gestores
        List<Manager> gestores =
                (List<Manager>) gestorRepository.findAll();

        // Lista de admins
        List<Admin> admins =
                (List<Admin>) adminRepository.findAll();

        /*
        Contem todos os utilizadores que nao sao gestores nem admins
         */
        ArrayList<String> usersValidos = new ArrayList<>();

        for (SystemUser su : systemUsers) {
            if (!gestores.contains(su) && !admins.contains(su)) {
                usersValidos.add(su.toString());
            }
        }

        return usersValidos;
    }

    /**
     * Através de um dado email, cria um Manager com o SystemUser associado
     * @param email email do SystemUser pretendido
     */
    public void atribuirGestor(String email) {
        // Buscar SystemUser com email dado
        Email e = new Email(email);
        SystemUser su = systemUserRepository.findByEmail(e);

        // Adicionar Manager
        Manager g = new Manager(su);

        // Persiste
        gestorRepository.add(g);
    }
}
