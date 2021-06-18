import java.util.Scanner;

public class Menu {

    private DBConnector dbConnector;

    public void run()
    {
        menu();
    }

    //shows menu and reads options from user
    private void menu()
    {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean work = true;

        while (work)
        {
            System.out.println("Welcome in Console SQL DB Client");

            System.out.println("1. Connect to data base");
            System.out.println("2. Execute instructions");
            System.out.println("3. Change data base");
            System.out.println("4. Change data base address");
            System.out.println("5. Reconnect");
            System.out.println("6. Disconnect");
            System.out.println("7. Exit");

            System.out.print("Choose:");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> connect();
                case 2 -> executeInstructions();
                case 3 -> change_database();
                case 4 -> change_address();
                case 5 -> reconnect();
                case 6 -> dbConnector.disconnect();
                case 7 -> work = false;
                default -> System.out.println("Choosen option doesnt exists, try again!");
            }
        }
        scanner.close();


    }

    //Reads input from user, initialize dbConnector and run method to connect to sql db
    private void connect()
    {
        Scanner scanner = new Scanner(System.in);
        String login, password, address, dbname;

        System.out.print("Type address:");
        address = scanner.nextLine();

        System.out.print("Type db name:");
        dbname = scanner.nextLine();

        System.out.print("Type login:");
        login = scanner.nextLine();

        System.out.print("Type password:");
        password = scanner.nextLine();

        dbConnector = new DBConnector(address, dbname, login, password);
        dbConnector.connect();
    }

    private void executeInstructions(){
        dbConnector.tryExecute();
    }

    private void reconnect()
    {
        dbConnector.reconnect();
    }

    private void change_database()
    {
        Scanner scanner = new Scanner(System.in);
        String login, password, dbname;

        System.out.print("Type db name:");
        dbname = scanner.nextLine();
        System.out.print("Type login:");
        login = scanner.nextLine();
        System.out.print("Type password:");
        password = scanner.nextLine();


        dbConnector.setUser(login);
        dbConnector.setPassword(password);
        dbConnector.setDbname(dbname);
    }
    private void change_address()
    {

    }
}
