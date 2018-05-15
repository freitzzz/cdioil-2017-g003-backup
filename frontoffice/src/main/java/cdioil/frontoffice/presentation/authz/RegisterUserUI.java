package cdioil.frontoffice.presentation.authz;


import cdioil.frontoffice.application.authz.RegisterUserController;
import cdioil.console.Console;
import java.time.format.DateTimeParseException;

/**
 * User Interface for the Register User use case (US-180)
 */
public class RegisterUserUI {
    /**
     * Constant that represents the index of the email field
     */
    private static final int EMAIL_FIELD_INDEX=0;
    /**
     * Constant that represents the index of the password field
     */
    private static final int PASSWORD_FIELD_INDEX=1;
    /**
     * Constant that represents the index of the name field
     */
    private static final int NAME_FIELD_INDEX=2;
    /**
     * Constant that represents the index of the phone number field
     */
    private static final int PHONE_NUMBER_FIELD_INDEX=3;
    /**
     * Constant that represents the index of the location field
     */
    private static final int LOCATION_FIELD_INDEX=0;
    /**
     * Constant that represents the index of the birth date field
     */
    private static final int BIRTH_DATE_FIELD_INDEX=1;
    /**
     * Constant that represents the header of the use case
     */
    private static final String USE_CASE_HEADER="##### Registar Utilizadores #####\n";
    /**
     * Constant that represents a 'Yes'
     */
    private static final String YES="Sim";
    /**
     * Constant that represents a 'No'
     */
    private static final String NO="Não";
    /**
     * Constant that represents the optional fields message
     */
    private static final String OPTIONAL_FIELDS_MESSAGE="Pretende preencher os campos opcionais? Digite \""
            +YES+"\" para preencher ou \""+NO+"\" para acabar o processo de registo";
    /**
     * Constant that represents the message that ocures if the user was registered with success
     */
    private static final String USER_REGISTERED_WITH_SUCCESS="O utilizador foi registado com sucesso!";
    /**
     * Constant that represents the exit code 
     */
    private static final String EXIT_CODE="Sair";
    /**
     * Constant that represents the exit message
     */
    private static final String EXIT_MESSAGE="Pretende continuar? Caso não queira digite \""+EXIT_CODE+"\" para sair";
    /**
     * Array Constant with all the obligatory fields used in the registration process
     */
    private static final String[] REGISTRATION_FIELDS={"Email: "
            ,"Password: ","Name: ","Phone Number:"};
    /**
     * Array Constant with all the optional fields used in the registration process
     */
    private static final String[] OPTIONAL_REGISTRATION_FIELDS={"Location: ","Birth Date: "};
    /**
     * Current controller
     */
    private final RegisterUserController registerUserController=new RegisterUserController();
    /**
     * Method that registers a certain user via console user interface
     */
    public void registerUser() {
        System.out.println(USE_CASE_HEADER);
        boolean catched=false;
        while(!catched){
            for(int i=0;i<REGISTRATION_FIELDS.length;i++){
                try{
                    switch (i){
                        case EMAIL_FIELD_INDEX:
                            registerUserController.addEmail(Console.readLine(REGISTRATION_FIELDS[i]));
                            break;
                        case PASSWORD_FIELD_INDEX:
                            registerUserController.addPassword(Console.readLine(REGISTRATION_FIELDS[i]));
                            break;
                        case NAME_FIELD_INDEX:
                            registerUserController.addName(Console.readLine(REGISTRATION_FIELDS[i]));
                            break;
                        case PHONE_NUMBER_FIELD_INDEX:
                            registerUserController.addPhoneNumber(Console.readLine(REGISTRATION_FIELDS[i]));
                            break;
                    }
                }catch(IllegalArgumentException|IndexOutOfBoundsException e){
                    Console.logError(e.getMessage());
                    i--;
                    if(verifyExitUI()){
                        return;
                    }
                }
            }
            if(verifiyOptionalFields()){
                for(int i=0;i<OPTIONAL_REGISTRATION_FIELDS.length;i++){
                    try{
                        if(i==LOCATION_FIELD_INDEX){
                            registerUserController.addLocation(Console.readLine(OPTIONAL_REGISTRATION_FIELDS[i]));
                        }else if(i==BIRTH_DATE_FIELD_INDEX){
                            registerUserController.addBirthDate(Console.readLine(OPTIONAL_REGISTRATION_FIELDS[i]));    
                        }
                    }catch(IllegalArgumentException|DateTimeParseException e){
                        Console.logError(e.getMessage());
                        i--;
                        if(verifyExitUI()){
                            return;
                        }
                    }
                }
            }
            catched=true;
        }
        try{
            registerUserController.registerUser();
            Console.log(USER_REGISTERED_WITH_SUCCESS,Console.ConsoleColors.YELLOW);
        }catch(IllegalArgumentException|IllegalStateException e){
            Console.logError(e.getMessage());
        }
    }
    /**
     * Method that verifies if the user wants to add info to optional fields
     * @return boolean true if the user wants to add info to optional fields, 
     * false if not
     */
    private boolean verifiyOptionalFields(){
        boolean catched=false;
        while(!catched){
            String decision=Console.readLine(OPTIONAL_FIELDS_MESSAGE);
            if(decision.equalsIgnoreCase(YES)){
                return true;
            }
            if(decision.equalsIgnoreCase(NO)){
                return false;
            }
        }
        return catched;
    }
    /**
     * Method that asks and verifies if the user wants to exit of the current UI
     * @return boolean true if the user wants to exit of the current UI, false if not
     */
    private boolean verifyExitUI(){
        return Console.readLine(EXIT_MESSAGE).equalsIgnoreCase(EXIT_CODE);
    }
}
