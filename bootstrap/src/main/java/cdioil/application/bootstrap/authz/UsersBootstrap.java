package cdioil.application.bootstrap.authz;

import cdioil.domain.authz.Admin;
import cdioil.domain.authz.BirthDate;
import cdioil.domain.authz.Email;
import cdioil.domain.authz.Location;
import cdioil.domain.authz.Manager;
import cdioil.domain.authz.Name;
import cdioil.domain.authz.Password;
import cdioil.domain.authz.PhoneNumber;
import cdioil.domain.authz.SystemUser;
import cdioil.domain.authz.RegisteredUser;
import cdioil.persistence.impl.AdminRepositoryImpl;
import cdioil.persistence.impl.ManagerRepositoryImpl;
import cdioil.persistence.impl.RegisteredUserRepositoryImpl;
import cdioil.persistence.impl.UserRepositoryImpl;
import java.time.LocalDate;

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
     * Inicializa diversos utilizadores registados (RegisteredUser)
     */
    private void inicializarUtilizadoresRegitados(){
        RegisteredUserRepositoryImpl userRepo=new RegisteredUserRepositoryImpl();
        userRepo.add(new RegisteredUser(createSystemUser
            ("joao@email.com",Password.DEFAULT_PASSWORD,"Joao","Padaria","919555666","Porto","1997-01-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("maria@email.com",Password.DEFAULT_PASSWORD,"Maria","Albertina","919555667","Lisboa","1997-01-02")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("antonio@email.com",Password.DEFAULT_PASSWORD,"Antonio","Variações","919555668","Coimbra","1997-01-03")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("gisele@email.com",Password.DEFAULT_PASSWORD,"Gisele","Gisele","919555669","Porto","1997-01-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("quetirofoiesse@email.com",Password.DEFAULT_PASSWORD,"Maya","das Cartas","919555696","Porto","1997-01-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("bombado70@email.com",Password.DEFAULT_PASSWORD,"Agua","Fresca","919555676","Porto","1997-01-06")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("lil.pump.x3@email.com",Password.DEFAULT_PASSWORD,"Pump","Eskeiit","919420420","ESKEIT","1997-01-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("dinamagalhaes@email.com",Password.DEFAULT_PASSWORD,"Dina","Magalhaes","919553666","Porto","1997-01-07")));
    }
    /**
     * Inicializa diversos Administradores (Admin)
     */
    private void inicializarAdministradores(){
        AdminRepositoryImpl adminRepo=new AdminRepositoryImpl();
        adminRepo.add(new Admin(createSystemUser
            ("sonae.lover@sonae.pt",Password.DEFAULT_PASSWORD,"Joao","Antonio","919525666","Porto","1997-01-01")));
        adminRepo.add(new Admin(createSystemUser
            ("trabalhonasonae@sonae.pt",Password.DEFAULT_PASSWORD,"Armando","Torres","919535666","Porto","1997-01-01")));
        adminRepo.add(new Admin(createSystemUser
            ("tiago.ferreira@bit.sonae.pt",Password.DEFAULT_PASSWORD,"Tiago","Fereira","919558666","Porto","1997-01-01")));
    }
    /**
     * Inicializa diversos Gestores (Manager)
     */
    private void inicializarGestores(){
        ManagerRepositoryImpl gestorRepo=new ManagerRepositoryImpl();
        gestorRepo.add(new Manager(createSystemUser
            ("bom.gestor@sonae.pt",Password.DEFAULT_PASSWORD,"Mario","Lirio","919555655","Porto","1997-01-01")));
        gestorRepo.add(new Manager(createSystemUser
            ("gestor.do.mes@gestores.sonae.pt",Password.DEFAULT_PASSWORD,"Antonio","Penacova","919545266","Porto","1997-01-01")));
        gestorRepo.add(new Manager(createSystemUser
            ("tiago.almeida@sonae.pt",Password.DEFAULT_PASSWORD,"Tiago","Almeida","919555666","Porto","1997-01-01")));
    }
    /**
     * Método que cria um SystemUser a ser atribuido a um RegisteredUser-Admin-Manager
     * @param email String com o email do utilizador
     * @param password String com a password do utilizador
     * @param nome String com o nome do utilizador
     * @param apelido String com o apelido do utilizador
     * @return SystemUser com o utilizador do sistema
     */
    private SystemUser createSystemUser(String email,String password,String nome,String apelido
            ,String phoneNumber,String location,String birthDate){
        Email emailAddress = new Email(email);
        Password pwd = new Password(password);
        Name name = new Name(nome,apelido);
        PhoneNumber phone = new PhoneNumber(phoneNumber);
        Location local = new Location(location);
        BirthDate date = new BirthDate(LocalDate.parse(birthDate));
        SystemUser user=new SystemUser(emailAddress,name,pwd,phone,local,date);
        user.activateAccount(user.getActivationCode());
        return new UserRepositoryImpl().add(user);
    }
}
