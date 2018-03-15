package cdioil.application.authz;

import cdioil.application.persistence.RepositorioAdminJPA;
import cdioil.application.persistence.RepositorioBaseJPA;
import cdioil.application.persistence.RepositorioGestorJPA;
import cdioil.application.persistence.RepositorioSystemUserJPA;
import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Gestor;
import cdioil.domain.authz.SystemUser;

import java.util.ArrayList;
import java.util.List;

public class AtribuirPerfilGestorController {

    RepositorioBaseJPA repoSystemUser;
    RepositorioBaseJPA repoAdmin;
    RepositorioBaseJPA repoGestor;

    public AtribuirPerfilGestorController() {
        repoSystemUser = new RepositorioSystemUserJPA();
        repoAdmin = new RepositorioAdminJPA();
        repoGestor = new RepositorioGestorJPA();
    }

    public ArrayList<String> getListaUsersRegistados() {

        List<SystemUser> systemUsers =
                (ArrayList<SystemUser>) repoSystemUser.findAll();
        List<Gestor> gestores =
                (ArrayList<Gestor>) repoGestor.findAll();
        List<Admin> admins =
                (ArrayList<Admin>) repoAdmin.findAll();

        /*
        Contem todos os utilizadores que nao sao gestores nem admins
         */
        ArrayList<String> resultado = new ArrayList<>();

        for (SystemUser su : systemUsers) {
            if (!gestores.contains(su) && !admins.contains(su)) {
                resultado.add(su.toString());
            }
        }

        return resultado;
    }

    public void atribuirGestor(String email) {
        // Buscar SystemUser com email dado

        // Criar Gestor

        // Adicionar Gestor
    }
}
