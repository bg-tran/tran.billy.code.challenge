package tran.billy.code.challenge;

import tran.billy.code.challenge.dao.OrganizationDAO;
import tran.billy.code.challenge.dao.TicketDAO;
import tran.billy.code.challenge.dao.UserDAO;
import tran.billy.code.challenge.helper.StringHelper;
import tran.billy.code.challenge.services.HelpdeskService;


import java.util.Scanner;



public class App {

    private void printHeaders(){
        System.out.println("Welcome to Zendesk Search");
        System.out.println("Type 'quit' to exit any time, Press enter to continue\n\n");
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

    private void run(){

        printHeaders();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String searchMenuInput;
        String searchTerm, searchValue;
        HelpdeskService helpdeskService = new HelpdeskService();
        while (!"quit".equals(input)){

            switch (input){
                case "1":
                    System.out.println("Select 1) Users or 2) Tickets or 3) Organizations");
                    searchMenuInput = scanner.nextLine();
                    switch(searchMenuInput) {

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
                    break;
                case "2":
                    printHelp();
                    break;

                default:
                    System.out.println("Invalid input");
                    break;
            }
            input = scanner.nextLine();
        }
        scanner.close();

    }

    public static void main(String[] args){

        App app = new App();
        app.run();

    }
}
