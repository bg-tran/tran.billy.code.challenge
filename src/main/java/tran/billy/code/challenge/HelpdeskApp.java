package tran.billy.code.challenge;

import tran.billy.code.challenge.config.AppConfig;
import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;
import tran.billy.code.challenge.helper.StringHelper;
import tran.billy.code.challenge.services.HelpdeskService;
import java.io.IOException;

import java.util.Scanner;

public class HelpdeskApp {

    static final int ERROR_CODE_BAD_ARGUMENTS = 1;

    static final int ERROR_CODE_BAD_CONFIG_FILE = 2;

    static final int ERROR_CODE_CLASS_LOADING_FAILED = 3;

    private HelpdeskService helpdeskService;

    private final String[] args;

    public HelpdeskApp(String[] args){
        this.args = args;
    }

    public HelpdeskApp(String[] args,HelpdeskService helpdeskService){
        this.args = args;
        this.helpdeskService = helpdeskService;
    }

    private void printHeaders(){
        System.out.println("Welcome to Zendesk Search");
        System.out.println("Type 'quit' to exit any time, Press enter to continue\n\n");
    }

    private void printAppMenu(){
        System.out.println(StringHelper.addLeftPadding(" ", StringHelper.LEFT_PADDING_WIDTH - 5) + "Select search options:");
        System.out.println(StringHelper.addLeftPadding(" * ", StringHelper.LEFT_PADDING_WIDTH) + "Press 1 to search Zendesk");
        System.out.println(StringHelper.addLeftPadding(" * ", StringHelper.LEFT_PADDING_WIDTH) + "Press 2 to view a list of searchable fields");
        System.out.println(StringHelper.addLeftPadding(" * ", StringHelper.LEFT_PADDING_WIDTH) + "Type 'quit' to exit");
    }

    private void printHelp(){
        System.out.println(StringHelper.addRightPadding("_", StringHelper.RIGHT_PADDING_WIDTH).replace(' ','_'));
        System.out.println("Search Users with");
        UserDAO.SEARCH_FIELDS.keySet()
                .forEach(System.out::println);

        System.out.println(StringHelper.addRightPadding("_", StringHelper.RIGHT_PADDING_WIDTH).replace(' ','_'));
        System.out.println("Search Tickets with");
        TicketDAO.SEARCH_FIELDS.keySet()
                .forEach(System.out::println);

        System.out.println(StringHelper.addRightPadding("_", StringHelper.RIGHT_PADDING_WIDTH).replace(' ','_'));
        System.out.println("Search Organizations with");
        OrganizationDAO.SEARCH_FIELDS.keySet()
                .forEach(System.out::println);
    }

    private void printUsage(){
        System.out.println("Usage:");
        System.out.println("sh helpdesk <config file>");
        System.out.println("or if you are using Windows:");
        System.out.println("helpdesk.bat <config file>");
    }

    private boolean validateArgs(String[] args) {
        return args != null && args.length >= 1;
    }

    private void executeSearchZendesk(Scanner scanner) {

        String searchMenuInput;
        String searchTerm, searchValue;

        System.out.println("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back");
        searchMenuInput = scanner.nextLine();

        while(!"4".equals(searchMenuInput)) {

            switch (searchMenuInput) {
                case "1":
                    System.out.println("Enter search term");
                    searchTerm = scanner.nextLine();
                    System.out.println("Enter search value");
                    searchValue = scanner.nextLine();
                    helpdeskService.searchUsers(searchTerm, searchValue);
                    break;
                case "2":
                    System.out.println("Enter search term");
                    searchTerm = scanner.nextLine();
                    System.out.println("Enter search value");
                    searchValue = scanner.nextLine();
                    helpdeskService.searchTickets(searchTerm, searchValue);
                    break;
                case "3":
                    System.out.println("Enter search term");
                    searchTerm = scanner.nextLine();
                    System.out.println("Enter search value");
                    searchValue = scanner.nextLine();
                    helpdeskService.searchOrganizations(searchTerm, searchValue);
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
            System.out.println("Select 1) Users or 2) Tickets or 3) Organizations or 4) To go back");
            searchMenuInput = scanner.nextLine();
        }
    }

    public void run(){

        if (!validateArgs(args)){
            printUsage();
            System.exit(ERROR_CODE_BAD_ARGUMENTS);
        }

        initAppConfig(args[0]);
        printHeaders();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        printAppMenu();
        String input = scanner.nextLine();
        while (!"quit".equals(input)){
            switch (input){
                case "1":
                    executeSearchZendesk(scanner);
                    break;
                case "2":
                    printHelp();
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            printAppMenu();
            input = scanner.nextLine();
        }
        System.out.println("Exiting ...");
        scanner.close();

    }

    private void initAppConfig(String configFile) {

        try {
            AppConfig.init(configFile);
            if (helpdeskService == null) helpdeskService = new HelpdeskService();
        } catch (IOException e) {
            System.out.println("Unable to load config file");
            System.exit(ERROR_CODE_BAD_CONFIG_FILE);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to initialize app config");
            System.exit(ERROR_CODE_CLASS_LOADING_FAILED);
        }
    }

    public static void main(String[] args){

        HelpdeskApp app = new HelpdeskApp(args);
        app.run();

    }
}
