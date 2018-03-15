package cdioil.application.authz;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Gestor;
import cdioil.domain.authz.SystemUser;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.GestorRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;

import java.util.ArrayList;

public class AtribuirPerfilGestorController {

    private UserRepositoryImpl systemUserRepository;
    private GestorRepositoryImpl gestorRepository;
    private AdminRepositoryImpl adminRepository;

    public AtribuirPerfilGestorController() {
        this.systemUserRepository = new UserRepositoryImpl();
        this.gestorRepository = new GestorRepositoryImpl();
        this.adminRepository = new AdminRepositoryImpl();
    }

    public ArrayList<String> getListaUsersRegistados() {

        // Lista de System user
        ArrayList<SystemUser> systemUsers =
                (ArrayList<SystemUser>) systemUserRepository.findAll();

        // Lista de gestores
        ArrayList<Gestor> gestores =
                (ArrayList<Gestor>) gestorRepository.findAll();

        // Lista de admins
        ArrayList<Admin> admins =
                (ArrayList<Admin>) adminRepository.findAll();

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

    public void atribuirGestor(String email) {
        // Buscar SystemUser com email dado

        // Criar Gestor

        // Adicionar Gestor
    }
}
