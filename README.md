Job Board is a web application which allows to periodically save data from the external API (https://www.arbeitnow.com/api/job-board-api) providing services of discovering jobs market in Germany. The application provide services of searching the vacancies using pagination and sorting functionality, receiving some statistics like top 10 latest most viewed vacancies, location statistics, etc.

Implemented functionality is as follows: 
1) Download pages from https://www.arbeitnow.com/api/job-board-api (API is freely available without authorization), save ones in database (H2), taking into account that new data is added periodically (regularly you need to download).

2) Write a REST API
- getting ll vacancies with pagination and sorting
- getting top 10 latest most viewed vacancies
- getting statistics on locations (city and number of vacancies)
3) Cover code with tests


Notes
After starting the application, the H2 database is accessible through the following url:
http://localhost:8080/h2-console