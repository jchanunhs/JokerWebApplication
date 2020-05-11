package JokerClass;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Joker {
    private String JokerID,Joke,Date,Name;
    
    //get all jokes
    public Joker(){
        
    }
    
    //get jokes
    public Joker(String ID) {
        JokerID = ID;
    }
    
    //add jokes
    public Joker(String JK, String NM){
        Joke = JK;
        Name = NM;
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
    
    public String getJokeInformation(String column_name) {
        String holder = "";
        try {

            DBConnection ToDB = new DBConnection();
            Connection connection = ToDB.openConn(); //Open the connection
            Statement Stmt = connection.createStatement();
            String query = "SELECT * FROM JokerTable WHERE JokerID = '" + JokerID + "' ";
            ResultSet Rslt = Stmt.executeQuery(query);
            if (Rslt.next()) {
               holder = Rslt.getString(column_name);
            }
            else{
                holder = "JokerID invalid";
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
        return holder;
    }

    public boolean addJoke() {
        boolean done = false;
        try {
            if (!done) {
                DBConnection ToDB = new DBConnection(); //Have a connection to the DB
                Connection DBConn = ToDB.openConn();
                Statement Stmt = DBConn.createStatement();
                //Set up date 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = new Date(System.currentTimeMillis()); //get current time when joke was created
                Date = String.valueOf(formatter.format(date));
                //Set up jokerid
                JokerID = String.valueOf((int) (Math.random() * 1000000) + 1000000); //Create a 7 digit joker id

                String SQL_Command = "SELECT * FROM JokerTable WHERE JokerID ='" + JokerID + "'"; //SQL query command
                ResultSet Rslt = Stmt.executeQuery(SQL_Command); 
                done = !Rslt.next(); //if jokerid is not taken we use it to add joke to DB
                if (done) {
                    SQL_Command = "INSERT INTO JokerTable (JokerID, Joke, Date, Name)"
                            + " VALUES ('" + JokerID + "', '" + Joke + "', '" + Date + "', '" + Name + "')";
                    Stmt.executeUpdate(SQL_Command);
                }
                Stmt.close();
                ToDB.closeConn();
            }
        } catch (java.sql.SQLException e) {
            done = false;
            System.out.println("SQLException: " + e);
            while (e != null) {
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("Message: " + e.getMessage());
                System.out.println("Vendor: " + e.getErrorCode());
                e = e.getNextException();
                System.out.println("");
            }
        } catch (java.lang.Exception e) {
            done = false;
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }
        return done;
    }
    
    public String getJokerID(){
        return JokerID;
    }

}

