import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Connection with database manager
public class DBConnector {

    private String address = "jdbc:mysql://";
    private String dbname = "";
    private String user = "";
    private String password = "";
    private final String DRIVER = "com.mysql.jdbc.Driver";

    private SQLParser parser = new SQLParser();

    private Connection connection;

    public DBConnector(String address, String db_name, String user, String password) {
        this.address += address;
        this.dbname = db_name;
        this.user = user;
        this.password = password;
    }

    public boolean connect() {
        try {

            connection = DriverManager.getConnection(address + "/" + dbname, user, password);
            System.out.println(connection.getAutoCommit());

        } catch (SQLException e) {
            sqlErrorCodeInterpreter(e);
            return false;
        }
        return true;
    }

    public boolean disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            sqlErrorCodeInterpreter(e);
            return false;
        }
        return true;
    }

    public boolean tryReconnect() {
        try {
            connection.close();
            connection = DriverManager.getConnection(address + "/" + dbname, user, password);
            if (!connection.isClosed()) {
                System.out.println("Reconnect:succeed");
            } else {
                System.out.println("Reconnect:failed");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean reconnect() {
        try {
            connection.close();
            connection = DriverManager.getConnection(address + "/" + dbname, user, password);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean tryExecute() {
        try {

            boolean con = true;
            do {
                Statement statement = connection.createStatement();
                con = parser.readParsedInstruction(statement);
            }
            while (con);

        }
        catch (SQLException e)
        {
            sqlErrorCodeInterpreter(e);
            return false;
        }
        return true;
    }


    public void sqlErrorCodeInterpreter(SQLException exception) {

        if (exception.getErrorCode() == 0) {
            System.out.println("Timeout, trying to reconnect");
            tryReconnect();
        }
        else
            {
                System.out.println("Error: exception message \"" + exception.getMessage() + "\"");
            }

    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
