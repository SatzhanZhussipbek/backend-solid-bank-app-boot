<h1>Solid Bank Application</h1>
<h2>Instructions</h2>
<li>Download the project and unpack it</li>
<li>Go to the Terminal</li>
<li>Use "cd" command to get the location of the project</li>
<li>Write "javac -project_name.java"</li>
<li>Write "java -project_name"</li>
<li>In the IDE you need to connect to H2 database</li>
<li>Then you need to create tables "Account", "Transaction", "User"</li>
<li>To create the tables use the .sql file in "resources/db/migration"</li>
<li>Go to H2 console at the address: "//localhost:8080/h2-console"</li>
<li>When you perform operations in Swagger, you can see immediate results in H2 console</li>
<li>In order to perform HTTP requests, you need to go to "//localhost:8080/swagger-ui/index.html#/"</li>
<li>However, first you need to register a new user, then authenticate him or her</li>
<li>Then you can perform other operations, but you always need to send a token with your requests</li>
<li>If you use Postman the procedure stays pretty much the same</li>

<h3>HTTP Requests</h3>

```
GET request -> show information about the user

GET request -> show all accounts

POST request -> create an account

POST request -> register a new user

POST request -> authenticate the new user

PUT request -> deposit into an account

PUT request -> withdraw from an account

GET request -> getting information about one particular account

GET request -> list of transactions of an account

DELETE request -> delete an account

POST request -> transfer from one account to another

``` 
