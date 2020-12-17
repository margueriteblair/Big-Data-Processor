Two minimum viable products that will import a ~6 million record .csv file into PostgresSQL. One method uses batch processing, the other uses sessions to loop through the data and insert it into the appropriate row/column.

For the batch processing, average upload time is: 10m35s442ms
For the sessions, average upload time is: 25m09s125ms

Within main/java/com there are two packages, one with the materials to run the batch processor, another with the materials to run the program that uploads the csv to Postgres via a parser and Sessions.
