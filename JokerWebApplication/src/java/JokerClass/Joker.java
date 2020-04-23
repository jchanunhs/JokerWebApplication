package JokerClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Joker {
    
    public Joker(){
        
    }
    public ArrayList<String> getAllJokes(String column_name) {
        ArrayList<String> list = new ArrayList<String>();
        try {

            DBConnection ToDB = new DBConnection();
            Connection connection = ToDB.openConn(); //Open the connection
            Statement Stmt = connection.createStatement();
            String query = "SELECT * FROM JokerTable ORDER BY Date"; //Get jokes and order by date. 
            ResultSet Rslt = Stmt.executeQuery(query);
            
            while (Rslt.next()) {
                if (column_name.equals("Date")) {
                    list.add(Rslt.getString("Date"));
                } else if (column_name.equals("Joke")) {
                    list.add(Rslt.getString("Joke"));
                }
            }

            Stmt.close();
            ToDB.closeConn();

        } catch (java.sql.SQLException e) {

            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (java.lang.Exception e) {

            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
        return list; //returns the list
    }
}


