package cdioil.application.bootstrap.authz;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Gestor;
import cdioil.domain.authz.Nome;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.UserRegistado;
import cdioil.persistence.UserRegistadoRepository;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.GestorRepositoryImpl;
import cdioil.persistence.impl.RepositorioUtilizadoresImpl;

/**
 * Bootstrap que persiste utilizadores na base de dados
 * @author <a href="1160907@isep.ipp.pt">João Freitas</a>
 */
public final class UsersBootstrap {
    /**
     * Inicializa o bootstrap dos utilizadores
     */
    public UsersBootstrap(){
        inicializarUtilizadoresRegitados();
        inicializarAdministradores();
        inicializarGestores();
    }
    /**
     * Inicializa diversos utilizadores registados (UserRegistado)
     */
    private void inicializarUtilizadoresRegitados(){
        UserRegistadoRepository userRepo=new RepositorioUtilizadoresImpl();
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("joao@email.com",Password.DEFAULT_PASSWORD,"Joao","Padaria")));
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("maria@email.com",Password.DEFAULT_PASSWORD,"Maria","Albertina")));
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("antonio@email.com",Password.DEFAULT_PASSWORD,"Antonio","Variações")));
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("gisele@email.com",Password.DEFAULT_PASSWORD,"Gisele","Gisele")));
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("quetirofoiesse@email.com",Password.DEFAULT_PASSWORD,"Maya","das Cartas")));
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("bombado70@email.com",Password.DEFAULT_PASSWORD,"Agua","Fresca")));
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("lil.pump.x3@email.com",Password.DEFAULT_PASSWORD,"Pump","Eskeiit")));
        userRepo.addUserRegistado(new UserRegistado(createSystemUser
            ("dinamagalhaes@email.com",Password.DEFAULT_PASSWORD,"Dina","Magalhaes")));
    }
    /**
     * Inicializa diversos Administradores (Admin)
     */
    private void inicializarAdministradores(){
        AdminRepositoryImpl adminRepo=new AdminRepositoryImpl();
        adminRepo.add(new Admin(createSystemUser
            ("sonae.lover@sonae.pt",Password.DEFAULT_PASSWORD,"Joao","Antonio")));
        adminRepo.add(new Admin(createSystemUser
            ("trabalhonasonae@sonae.pt",Password.DEFAULT_PASSWORD,"Armando","Torres")));
        adminRepo.add(new Admin(createSystemUser
            ("tiago.ferreira@bit.sonae.pt",Password.DEFAULT_PASSWORD,"Tiago","Fereira")));
    }
    /**
     * Inicializa diversos Gestores (Gestor)
     */
    private void inicializarGestores(){
        GestorRepositoryImpl gestorRepo=new GestorRepositoryImpl();
        gestorRepo.add(new Gestor(createSystemUser
            ("bom.gestor@sonae.pt",Password.DEFAULT_PASSWORD,"Mario","Lirio")));
        gestorRepo.add(new Gestor(createSystemUser
            ("gestor.do.mes@gestores.sonae.pt",Password.DEFAULT_PASSWORD,"Antonio","Penacova")));
        gestorRepo.add(new Gestor(createSystemUser
            ("tiago.almeida@sonae.pt",Password.DEFAULT_PASSWORD,"Tiago","Almeida")));
    }
    /**
     * Método que cria um SystemUser a ser atribuido a um UserRegistado/Admin/Gestor
     * @param email String com o email do utilizador
     * @param password String com a password do utilizador
     * @param nome String com o nome do utilizador
     * @param apelido String com o apelido do utilizador
     * @return SystemUser com o utilizador do sistema
     */
    private SystemUser createSystemUser(String email,String password,String nome,String apelido){
        return new SystemUser(new Email(email),new Nome(nome,apelido),new Password(password));
    }
}
