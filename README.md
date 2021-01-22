Two minimum viable products that will import a ~6 million record .csv file into PostgresSQL. One method uses batch processing, the other uses sessions to loop through the data and insert it into the appropriate row/column.

Average runtime for the batch processor with a ThreadPoolTaskExecutor is 10 minutes. Average runtime for the stateless sessions parser/processor is 40 minutes.

<h3>Instructions to run:</h3>
<ol>1. Download the financial data from <a href="https://www.kaggle.com/ntnu-testimon/paysim1">Kaggle</a>. Add this data to "resource/data" and be sure to include the .csv file in your .gitignore!</ol>
<ol>2. Within main/java/com there are two distinct packages, "batch" and "session", which are the batch processor and sessions processor respectively.</ol>
<ol>3. Each package has it's own main file that can be ran</ol>
<ol>4. Once the application is launched without issues, head over to Postman and test on your configured port and the route "/load"</ol>

<h3>Technologies Used</h3>
<li>Java</li>
<li>Spring Boot for REST API</li>
<li>Spring Batch Processing</li>
<li>Maven</li>
<li>Factory Design Pattern within Batch Processor</li>
<li>Hibernate</li>
<li>Java Persistence API (JPA)</li>
<li>PostgreSQL</li>
