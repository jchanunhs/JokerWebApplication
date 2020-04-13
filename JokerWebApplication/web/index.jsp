<%-- 
    Document   : index
    Created on : Mar 31, 2020, 2:00:11 PM
    Author     : Jason
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page import="JokerClass.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Joker Services</title>
        <link rel="stylesheet" href = "style.css">
        <link rel="shortcut icon" href="images/jokeicon.ico">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="keywords" content="Joke, Jokes, Joker, Joker Services, Funny Content">
        <meta name="author" content="Jason Chan, Nathan Villajuan, Marcos Perez, Danny Torres">
        <meta name="description" content="Welcome to Joker Services!">

    </head>

    <body>
        <div id="wrapper">

            <header>Joker Services</header>

            <main> 

                <form name = "JokerForm"
                      action="./InsertData"
                      method="get" 
                      enctype="text/plain">

                    <label for="JokerID">Joke IDs: </label>
                    <input type="text" name ="JokerID"  id="JokerID">

                    <div class ="formspace"></div>
                    <input type="submit" value="Submit">
                    <div class ="formspace"></div>
                    <div style="text-align: center">Please separate Joker IDs using spaces </div>

                </form>
                <h1 align="center">All Jokes in Database</h1>
                <table>
                    <tr>

                        <th>Date Created</th>

                        <th>Joke</th>
                    </tr>

                    <% //connect to db 
                        DBConnection ToDB = new DBConnection();
                        Connection connection = ToDB.openConn(); //Open the connection
                        Statement stmt = connection.createStatement();
                        String query = "SELECT * FROM JokerTable ORDER BY Date"; //Get jokes and order by date. 
                        ResultSet rslt = stmt.executeQuery(query);
                        while (rslt.next()) {
                    %>
                    <tr>                   
                        <td><%=rslt.getString("Date")%></td>                   
                        <td><%=rslt.getString("Joke")%></td>
                    </tr>
                    <% } %>
                </table>
                <% //close connections
                    connection.close();
                    ToDB.closeConn();
                %>

                <div style="text-align: center">            
                    <a id ="addlink" href = "addjoke.html">Add Joke</a>
                </div>




            </main>


            <footer><small><em>
                        Copyright 2020 Joker Services<br>

                    </em></small></footer>

        </div>

    </body> 

</html>