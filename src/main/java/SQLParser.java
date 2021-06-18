import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class SQLParser
{
    private Scanner scanner = new Scanner(System.in);
    public boolean readParsedInstruction(Statement sqlStatement) throws SQLException
    {

        Scanner scanner = new Scanner(System.in);

        System.out.println("============================");
        System.out.println("Choose sql instruction:");
        System.out.println("1. SELECT ");
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("2. INSERT ");
        System.out.println("3. UPDATE ");
        System.out.println("4. DELETE ");
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("5. CREATE ");
        System.out.println("6. DROP ");
        System.out.println("7. ALTER ");
        System.out.println("8. TRUNCATE ");
        System.out.println("- - - - - - - - - - - - - - -");
        System.out.println("9. CUSTOM - TYPE YOUR INSTRUCTION ");

        int choice = Integer.parseInt(scanner.nextLine());

        String instruction;

        switch (choice) {
            case 1 -> select(sqlStatement);
            case 2 -> insert(sqlStatement);
            case 3 -> update(sqlStatement);
            case 4 -> delete(sqlStatement);
            case 5 -> create(sqlStatement);
            case 6 -> drop(sqlStatement);
            case 7 -> alter(sqlStatement);
            case 8 -> truncate(sqlStatement);
            case 9 -> custom(sqlStatement);
        }
        return true;
    }

    private void handleStatement(Statement sqlStatement, String parsedInstruction) throws SQLException
    {
       sqlStatement.executeUpdate(parsedInstruction);
    }

    private void handleStatementWithFeedback(Statement sqlStatement, String parsedInstruction) throws SQLException
    {
        ResultSet rs = sqlStatement.executeQuery(parsedInstruction);

        System.out.println("============================");
        System.out.println("response from server:");

        int index = 0;
        while (rs.next())
        {
            System.out.println("[" + index + "] " + rs.getString(index));
            index++;
        }
    }

    private void select(Statement sqlStatement) throws SQLException{

        System.out.println("SELECT * FROM [...]");
        String input = scanner.nextLine();
        String parsedInstruction = "SELECT * FROM " + input;
        handleStatementWithFeedback(sqlStatement, parsedInstruction);

    }

    private void insert(Statement sqlStatement) throws SQLException{


        System.out.println("INSERT INTO [table name] ([column1, column2...])");
        System.out.println("VALUES ([VALUE1, VALUE2, ...])");

        System.out.print("Table name:");
        String tableName = scanner.nextLine();
        System.out.println("Type columns(separate using ,):");
        String columns = scanner.nextLine();
        System.out.println("Type values(separate using ,):");
        String values = scanner.nextLine();

        String parsedInstruction = "INSERT INTO " + tableName +" (" + columns + ") VALUES (" + values + ")";

        handleStatement(sqlStatement, parsedInstruction);


    }
    private void update(Statement sqlStatement) throws SQLException{

        System.out.println("UPDATE [table name]");
        System.out.println("SET [columnsX] = [valueX], [columnY] = [valueY]");

        System.out.print("Table name:");
        String tableName = scanner.nextLine();

        System.out.println("Type columns(separate using ,):");
        String[] columns = scanner.nextLine().split(",");

        System.out.println("Type values(separate using ,):");
        String[] values = scanner.nextLine().split(",");


        String parsedInstruction = "UPDATE " + tableName +" SET";
        for(int i = 0; i < columns.length ; i ++)
        {
            parsedInstruction +=  columns[i] + "=" + values[i] + ", ";
        }

        handleStatement(sqlStatement, parsedInstruction);
    }
    private void delete(Statement sqlStatement) throws SQLException{
        System.out.println("DELETE FROM [table name]");

        System.out.print("Table name:");
        String tableName = scanner.nextLine();

        String parsedInstruction = "DELETE FROM " + tableName + where();

        handleStatement(sqlStatement, parsedInstruction);

    }

    private void create(Statement sqlStatement) throws SQLException{
        System.out.println("CREATE TABLE table_name (" +
                " columnX datatype," +
                " columnY datatype," +
                " ..." +
                ");");

    }
    private void drop(Statement sqlStatement) throws SQLException{

    }
    private void truncate(Statement sqlStatement) throws SQLException{

    }
    private void alter(Statement sqlStatement) throws SQLException{

    }

    private void custom(Statement sqlStatement) throws SQLException{

        System.out.println("1. Querry statement");
        System.out.println("2. Update statement");
        System.out.print("Choose:");
        int choice = Integer.parseInt(scanner.nextLine());
        System.out.println("Type complete statement, for example (SELECT * FROM Users)");
        String parsedStatement = scanner.nextLine();

        if(choice == 1)
        {
            handleStatementWithFeedback(sqlStatement, parsedStatement);
        }
        if(choice == 2)
        {
            handleStatement(sqlStatement, parsedStatement);
        }

    }

    private String where(){
        System.out.println("(leave empty if not interested) WHERE = ");
        String input = scanner.nextLine();
        return " WHERE = " + input;
    }

}
