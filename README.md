<h1>Solid Bank Application</h1>
<h2>Instructions</h2>
<li>Download the project and unpack it</li>
<li>Go to the Terminal</li>
<li>Use "cd" command to get the location of the project</li>
<li>Write "javac -project_name.java"</li>
<li>Write "java -project_name"</li>
<li>In the IDE you need to connect to H2 database</li>
<li>Then you need to create tables "Account" and "Transaction"</li>
<li>To create the tables use the .sql file in "resources/db/migration"</li>
<li>Go to H2 console at the address: "//localhost:8080/h2-console"</li>
<li>When you perform operations in IDE, you can see immediate results in H2 console</li>
<li>In order to perform HTTP requests, you need to go to "//localhost:8080/swagger-ui/index.html#/"</li>
<li>There you can execute requests or use Postman to do the same</li>

<h3>HTTP Requests</h3>

```
GET request -> show all accounts

POST request -> create an account

PUT request -> deposit into an account

PUT request -> withdraw from an account

GET request -> getting information about one particular account

GET request -> list of transactions of an account

DELETE request -> delete an account
``` 
