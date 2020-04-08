# JokerWebApplication
A Client/Server based web application.

Database name is JokerDB and contains a JokerTable with the following columns
- JokerID
- Joke
- Name
- Date

Index.jsp
- The homepage of the web application
- Displays all jokes from JokerDB to a table
- User can insert a JokerID and submit form to fetch the joke.

Addjoke.thml
- Contains a form that allows user to enter name and a joke.
- When user submits their joke, data is inserted into database.

DBConnection.java
- Responsible for connecting our web application to the database.

InsertData.java
- Web servlet of our application
- Handles GET and POST requests from user

Scenarios

For homepage
- If user enters wrong JokerID, message will be displayed in the table saying that it's an invalid JokerID.
- If user enters correct JokerID, we display the joke in a table. Difference between the homepage and the InsertData display is that InsertData will display creator name.

For AddJoke
- If user enters Joke, but not their name, their name will be set to "Unknown User" and show success message on webpage saying that the joke was inserted successfully. Show user their JokerID
- If user enters nothing, display and error page saying the user didn't enter any joke.
- If user enters Name and Joke, show success message on webpage and give user their JokerID.

JokerID is a random generated number from 1000000-1999999 created by the InsertData. JokerID must be unique because it's a primary key in the database. If the generated JokerID is already taken, we display an error message saying there was a problem with inserting joke to database. This problem may occur if there are too many jokes in the database.
