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
     * Constant that represents the default password used on the users contained on the 
     * password
     * <br>Cannot contain "Password" as the name since Sonarqube doesn't like "Hardcoded" passwords
     */
    private static final String DEFAULT_COMINHOS="Password123";
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
            ("joao@email.com",DEFAULT_COMINHOS,"Joao","Padaria","919555666","Porto","1937-01-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("maria@email.com",DEFAULT_COMINHOS,"Maria","Albertina","919555667","Lisboa","1997-01-02")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("antonio@email.com",DEFAULT_COMINHOS,"Antonio","Variações","919555668","Coimbra","1997-01-03")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("gisele@email.com",DEFAULT_COMINHOS,"Gisele","Gisele","919555669","Braga","1997-01-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("quetirofoiesse@email.com",DEFAULT_COMINHOS,"Maya","das Cartas","919555696","Porto","1997-06-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("bombado70@email.com",DEFAULT_COMINHOS,"Agua","Fresca","919555676","Algarve","1997-08-06")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("lil.pump.x3@email.com",DEFAULT_COMINHOS,"Pump","Eskeiit","919420420","ESKEIT","1997-07-01")));
        userRepo.add(new RegisteredUser(createSystemUser
            ("dinamagalhaes@email.com",DEFAULT_COMINHOS,"Dina","Magalhaes","919553666","Matosinhos","1997-01-07")));
    }
    /**
     * Inicializa diversos Administradores (Admin)
     */
    private void inicializarAdministradores(){
        AdminRepositoryImpl adminRepo=new AdminRepositoryImpl();
        adminRepo.add(new Admin(createSystemUser
            ("sonae.lover@sonae.pt",DEFAULT_COMINHOS,"Joao","Miguel","919525666","Portugal","1997-12-01")));
        adminRepo.add(new Admin(createSystemUser
            ("trabalhonasonae@sonae.pt",DEFAULT_COMINHOS,"Armando","Torres","919535666","Matosinhos","1997-08-01")));
        adminRepo.add(new Admin(createSystemUser
            ("tiago.ferreira@bit.sonae.pt",DEFAULT_COMINHOS,"Tiago","Fereira","919558666","Santarem","1999-01-01")));
    }
    /**
     * Inicializa diversos Gestores (Manager)
     */
    private void inicializarGestores(){
        ManagerRepositoryImpl gestorRepo=new ManagerRepositoryImpl();
        gestorRepo.add(new Manager(createSystemUser
            ("bom.gestor@sonae.pt",DEFAULT_COMINHOS,"Mario","Lirio","919555655","Barcelona","2997-01-01")));
        gestorRepo.add(new Manager(createSystemUser
            ("gestor.do.mes@gestores.sonae.pt",DEFAULT_COMINHOS,"Luis","Penacova","919545266","Madrid","1957-01-01")));
        gestorRepo.add(new Manager(createSystemUser
            ("tiago.almeida@sonae.pt",DEFAULT_COMINHOS,"Tiago","Almeida","919555666","Figueira","1947-01-01")));
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
