package JokerClass;

import java.io.IOException;
import java.io.PrintWriter;
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
        String JokerID = request.getParameter("JokerID"); //Joker ID which user entered
        String[] JokerIDList = JokerID.split("\\s"); //Split the joker ID based on the space
        ArrayList<String> JokeList = new ArrayList<String>(); //ArrayList of Jokes
        ArrayList<String> DateList = new ArrayList<String>(); //ArrayList of Dates
        ArrayList<String> NameList = new ArrayList<String>(); //ArrayList of Names
        for (int i = 0; i < JokerIDList.length; i++) {
            Joker joke = new Joker(JokerIDList[i]);
            JokeList.add(joke.getJokeInformation("Joke"));
            DateList.add(joke.getJokeInformation("Date"));
            NameList.add(joke.getJokeInformation("Name"));
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
        out.println("<div align = \"center\">            \n" +
"                    <a id =\"addlink\" href = \"index.jsp\">Return to Home Page</a>\n" +
"                </div>");
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

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //get Joke and Name from the input.
        String Joke = request.getParameter("Joke");
        String Name = request.getParameter("Name");
        //if user wants to remain anonymous, we set name as Unknown User. 
        if (Name.equals("")) {
            Name = "Unknown User";
        }
        Joker joke = new Joker(Joke, Name);
        //If the joke textfield is not empty, then attempt to Joke to DB
        if (!Joke.equals("")) {
            if (joke.addJoke()) {
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
                out.println("Your Joker ID is: " + joke.getJokerID());
                out.println("</p>");
                out.println("<div align = \"center\">            \n" +
"                    <a id =\"addlink\" href = \"index.jsp\">Return to Home Page</a>\n" +
"                </div>");
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
                out.println("<html><body><b>Error with adding joke to table! Please try again!"
                        + "</b></body></html>");

            }

        } //If user did not enter joke to the joke form, output this to prevent NULLs from populating our DB and wasting JokerIDs
        else if (Joke.equals("")) {
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
