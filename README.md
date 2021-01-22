Two minimum viable products that will import a ~6 million record .csv file into PostgresSQL. One method uses batch processing, the other uses sessions to loop through the data and insert it into the appropriate row/column.

Average runtime for the batch processor with a ThreadPoolTaskExecutor is 10 minutes. Average runtime for the stateless sessions parser/processor is 40 minutes.

<h3>Instructions to run:</h3>
<ol>Download the financial data from <a href="https://www.kaggle.com/ntnu-testimon/paysim1">Kaggle</a>. Add this data to "resource/data" and be sure to include the .csv file in your .gitignore!</ol>
<ol>Within main/java/com there are two distinct packages, "batch" and "session", which are the batch processor and sessions processor respectively.</ol>
<ol>Each package has it's own main file that can be ran</ol>
<ol>Once the application is launched without issues, head over to Postman and test on your configured port and the route "/load"</ol>

Within main/java/com there are two packages, one with the materials to run the batch processor, another with the materials to run the program that uploads the csv to Postgres via a parser and Sessions.
