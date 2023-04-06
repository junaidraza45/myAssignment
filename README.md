## MY SBAB Assignment Project with following requirement
---
- The task is to implement an API for potential clients. 
- The API will find out which bus lines that have the most bus stops on their route,
-  and to present the top 10 bus lines in a nice and formatted way in a web
   browser. 
- The other endpoint should return a list of every bus stop of the bus line provided. 
- here are no requirements how the bus stops are sorted.
- To accomplish the task please use the Trafiklab’s open API (https://www.trafiklab.se). You
can find more information about the specific API at the documentation page:
http://www.trafiklab.se/api/sl-hallplatser-och-linjer-2.
You can register your own account at Trafiklab and obtain an API key.
- The data should be automatically gathered from API for each run of the application.
---
### Tools & Framework
- Java 11
- Spring boot Project
- Maven 3+ for building project and running integration test
- Spring Data JPA for persistence layer
- H2 in memory database 
- Swagger 3 for Api Documentation 
- RestController for Apis
- Health Check endpoints  

---
### How to run, build & verify working of project
- Build project & run integration test  $ `mvn clean install`
- Run the app with  $ `mvn spring-boot:run` then browse to http://localhost:8080
- The brwoswer (http://localhost:8080) will have a page with required list of top 10 Bus lines which have maximum no of stops.
- To access Swagger for testing Apis then browse to http://localhost:8080/swagger
- When the app is running, the in-memory DB can be inspected at http://localhost:8080/h2-console 
  This is, of course, a proof of concept and should not be deployed publicly.
---
### Design 
- All application configurations are defined under application.yaml
- On application startup sl api endpoint are called and retrieves data.
- Data is stored in memory database
- fallback methods for io timeout scenarios
  There is chance due to n.w. slowness or bad connectivity application might not be able to make call to sl api.
- There seems to be not all the data availabe for names so might be the no of stops showing in didn't have all the names avialabe that is because of given data limmitations.
- Choice of H2 database is for simplicity of test, however just change property application can be configured to any other database.
- Sl api documentation says, they refresh data in every 24h, therefore data can be store in database for 24 hours and a scheduler can fatah fresh data in given intervals
- Layered approach `Controllers -> Service -> DatabaseRepository --> Database queries`  
- SpringBeans are used for bossiness & persistence layer  

---
