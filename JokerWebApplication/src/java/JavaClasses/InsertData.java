package JavaClasses;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/InsertData"})
public class InsertData extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet InsertData</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InsertData at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Set up variables 
        ArrayList<String> JokeList = new ArrayList<String>(); //ArrayList of Jokes
        ArrayList<String> DateList = new ArrayList<String>(); //ArrayList of Dates
        ArrayList<String> NameList = new ArrayList<String>(); //ArrayList of Names
        String JokerID = request.getParameter("JokerID"); //Joker ID which user entered

        String[] JokerIDList = JokerID.split("\\s"); //Split the joker ID based on the space
        String Joke = "";
        String Date = "";
        String Name = "";
        try {
            DBConnection ToDB = new DBConnection(); //Have a connection to the DB
            Connection DBConn = ToDB.openConn(); //Open the connection
            //For each item in the JokerID List, execute query, get the row, add it to array list
            for (int i = 0; i < JokerIDList.length; i++) {
                //SQL Query to find joker id
                PreparedStatement stmt = DBConn.prepareStatement("SELECT * From JokerTable WHERE JokerID = ?");
                stmt.setString(1, JokerIDList[i]); //Where joker ID = JOKER_ID
                //execute the query
                ResultSet Rslt = stmt.executeQuery();
                if (Rslt.next()) {
                    //Get the row values from DB
                    Name = Rslt.getString("Name");
                    Joke = Rslt.getString("Joke");
                    Date = Rslt.getString("Date");
                    JokeList.add(Joke); //Add the joke to the arraylist
                    DateList.add(Date);
                    NameList.add(Name);
                } else {
                    //If the user accidently enters invalid joke, we let them know by adding error message to array list.
                    JokeList.add("Invalid JokerID VALUE(" + JokerIDList[i] + "): Joke not found");
                    DateList.add("Invalid JokerID VALUE(" + JokerIDList[i] + "): Date not found!");
                    NameList.add("Invalid JokerID VALUE(" + JokerIDList[i] + "): Name not found!");
                }
                stmt.close();
                
            }

            //output html
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>\n"
                    + "<html lang=\"en\">\n"
                    + "    <head>\n"
                    + "        <title>Joker Services</title>\n"
                    + "        <link rel=\"stylesheet\" href = \"style.css\">\n"
                    + "        <link rel=\"shortcut icon\" href=\"images/jokeicon.ico\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "        <meta name=\"keywords\" content=\"Joke, Jokes, Joker, Joker Services, Funny Content\">\n"
                    + "        <meta name=\"author\" content=\"Jason Chan, Nathan Villajuan, Marcos Perez, Danny Torres\">\n"
                    + "        <meta name=\"description\" content=\"Welcome to Joker Services!\">\n"
                    + "\n"
                    + "    </head>\n"
                    + "\n"
                    + "    <body>\n"
                    + "        <div id=\"wrapper\">\n"
                    + "\n"
                    + "            <header>Joker Services</header>\n"
                    + "\n"
                    + "            <main> ");

            out.println("<h1 align=\"center\">Your Jokes" + "<" + "/h1>");
            out.println("<table>\n"
                    + "  <tr>\n"
                    + "    <th>Joker ID</th>\n"
                    + "    <th>Date Created</th>\n"
                    + "    <th>Creator</th>\n"
                    + "    <th>Joke</th>\n"
                    + "  </tr>");
            //get data from array list
            for (int i = 0; i < JokerIDList.length; i++) { //JokerIDList length is equal to the date,name, joke list. 
                out.println(" <tr>\n"
                        + "    <td>" + JokerIDList[i] + "</td>\n"
                        + "    <td>" + DateList.get(i) + "</td>\n"
                        + "     <td>" + NameList.get(i) + "</td>\n"
                        + "     <td>" + JokeList.get(i) + "</td>\n"
                        + "  </tr>");
            }

            out.println("</table>");
            out.println("   </main>\n"
                    + "\n"
                    + "\n"
                    + "            <footer><small><em>\n"
                    + "                        Copyright 2020 Joker Services<br>\n"
                    + "            </em></small></footer>\n"
                    + "\n"
                    + "        </div>\n"
                    + "\n"
                    + "    </body> \n"
                    + "\n"
                    + "</html>");
	    //close the connection
	    DBConn.close(); 
            ToDB.closeConn();
        } catch (java.lang.Exception e) {
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String JokerID = String.valueOf((int) (Math.random() * 1000000) + 1000000); //Create a 7 digit joker id
        //get Joke and Name from the input.
        String Joke = request.getParameter("Joke");
        String Name = request.getParameter("Name");
        //if user wants to remain anonymous, we set name as Unknown User. 
        if (Name.equals("")) {
            Name = "Unknown User";
        }
        //Set up date 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date(System.currentTimeMillis()); //get current time when joke was created
        String JokerDate = String.valueOf(formatter.format(date));
        //If the joke textfield is not empty, then attempt to Joke to DB
        if (!Joke.equals("")) {
            try {

                DBConnection ToDB = new DBConnection(); //Have a connection to the DB
                Connection DBConn = ToDB.openConn();
                //check if the joker id already exist/taken
                PreparedStatement checkExist = DBConn.prepareStatement("SELECT * FROM JokerTable WHERE JokerID = ?");
                checkExist.setString(1, JokerID);
                ResultSet Rslt = checkExist.executeQuery();
                //if it doesnt exist, we add the joke to the table 
                if (!Rslt.next()) {
                    PreparedStatement stmt = DBConn.prepareStatement("INSERT INTO JokerTable Values(?, ?, ?, ?)");
                    stmt.setString(1, JokerID);
                    stmt.setString(2, Joke);
                    stmt.setString(3, JokerDate);
                    stmt.setString(4, Name);
                    stmt.executeUpdate();
                    stmt.close();
                    PrintWriter out = response.getWriter();

                    out.println("<!DOCTYPE html>\n"
                            + "<html lang=\"en\">\n"
                            + "    <head>\n"
                            + "        <title>Joker Services</title>\n"
                            + "        <link rel=\"stylesheet\" href = \"style.css\">\n"
                            + "        <link rel=\"shortcut icon\" href=\"images/jokeicon.ico\">\n"
                            + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                            + "        <meta name=\"keywords\" content=\"Joke, Jokes, Joker, Joker Services, Funny Content\">\n"
                            + "        <meta name=\"author\" content=\"Jason Chan, Nathan Villajuan, Marcos Perez, Danny Torres\">\n"
                            + "        <meta name=\"description\" content=\"Welcome to Joker Services!\">\n"
                            + "\n"
                            + "    </head>\n"
                            + "\n"
                            + "    <body>\n"
                            + "        <div id=\"wrapper\">\n"
                            + "\n"
                            + "            <header>Joker Services</header>\n"
                            + "\n"
                            + "            <main> ");

                    out.println("<h1 align=\"center\">Your joke was successfully inserted " + Name + "!" + "<" + "/h1>" + "<p align=\"center\">");
                    out.println("Your Joker ID is: " + JokerID);
                    out.println("</p>");
                    out.println("   </main>\n"
                            + "\n"
                            + "\n"
                            + "            <footer><small><em>\n"
                            + "                        Copyright 2020 Joker Services<br>\n"
                            + "            </em></small></footer>\n"
                            + "\n"
                            + "        </div>\n"
                            + "\n"
                            + "    </body> \n"
                            + "\n"
                            + "</html>");

                } //If for some reason the joker id already exist(Chances of this occuring is 1 in a million), we show a message saying there was an error
                else {
                    PrintWriter out = response.getWriter();
                    out.println("<html><body><b>Error with adding joke to table! Please try again!" + JokerID
                            + "</b></body></html>");

                }
                //close connection
		DBConn.close();
                ToDB.closeConn();
                
            } catch (java.lang.Exception e) {
                System.out.println("Exception: " + e);
                e.printStackTrace();
            }
        } //If user did not enter joke to the joke form, output this to prevent NULLs from populating our DB and wasting JokerIDs
        else {
            PrintWriter out = response.getWriter();
            out.println("<html><body><b>You did not enter any joke!"
                    + "</b></body></html>");

        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
