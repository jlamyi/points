# Project Points
## What does it do
### Background
Our users have points in their accounts. Users only see a single balance in their accounts. But for reporting purposes we actually track their
points per payer/partner. In our system, each transaction record contains: payer name(string), points (integer), transactionDate (Date).
For earning points it is easy to assign a payer, we know which actions earned the points. And thus which partner should be paying for the points.
When a user spends points, they don't know or care which payer the points come from. But, our accounting team does care how the points are
spent. There are two rules for determining what points to "spend" first:
We want the oldest points to be spent first
We want no payer's points to go negative.
### Web service routes
Add points to user account for specific payer and date
Deduct points from the user account using above constraints and return a list of [payer, points deducted] for each call to spend points
Return point balance per user that would list all positive points per payer
All transaction information is stored in the service memory.
## How to run this web service
This web service is created in Intellij with Tomcat container. Onw way to launch it is to open this project in Intellij (File -> Open) and run it (The green triangle at the top right)
## How to test this web service
There are three routes to achieve the goals in this exercise 
1. Add points to user account for specific payer and date
  Use POST method to access "http://localhost:8080/points/ps", data format in body:
  {
    "payerName": "DANNON",
    "points": 300,
    "date": "10/31 10AM"
  }
2.Deduct points from the user account using above constraints and return a list of [payer, points deducted] for each call to spend points
  Use GET method to access "http://localhost:8080/points/ps?targetPoints=5000", parameter targetPoints is to set the desired points to be deducted
3.Return point balance per user that would list all positive points per payer
  Use GET method to access "http://localhost:8080/points/balance", no parameter is required
### Easy way to test this web service
Since there is no front-end pages to display the response from this web service. An easy way to test the web service is to use postman. The postman collection is stored under postman directory. After the server is launched, launch postman and press "import" at the top left conner to import this collection. In this collection, there are 7 sample requests to test the goal of three routes. Once it is imported, press "send" button for each request to test. 

Response result is shown at the bottom dialogue in postman application. TargetPoints parameter can be edited on the Params tag in the middle dialogue for deduction request. Transaction data can be edited on the Body tag in the middle dialogue for add request.
