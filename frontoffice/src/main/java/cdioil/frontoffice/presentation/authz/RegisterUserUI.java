package cdioil.frontoffice.presentation.authz;


import cdioil.frontoffice.application.authz.RegisterUserController;
import cdioil.frontoffice.utils.Console;
import java.time.format.DateTimeParseException;

/**
 * User Interface for the Register User use case (US-180)
 */
public class RegisterUserUI {
    /**
     * Method that registers a certain user via console user interface
     */
    public void registerUser() {
        System.out.println("**** Registar Utilizadores ****\n");
        String primeiroNome = Console.readLine("\nNome: ");
        String apelido = Console.readLine("\nApelido: ");
        String email = Console.readLine("\nEmail: ");
        String password= Console.readLine("\nPassword: ");
        String phoneNumber=Console.readLine("\nPhone Number: ");
        String location=Console.readLine("\nLocation: ");
        String birthDate=Console.readLine("\nBirth Date: ");
        try {
            new RegisterUserController().registerUser(primeiroNome, apelido, email,password
                    ,phoneNumber,location,birthDate);
            System.out.println("O utilizador foi registado com succeso!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }catch(DateTimeParseException f){
            System.out.println("A data que inseriu é invalida!\nVerifique se está no formato "
                    + "YYYY-MM-DD");
        }
    }
}
