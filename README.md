# Exam in PG5100 - Enterprise programming

## Topic/theme of the exam:

The topic or theme given is to make a web application regarding movie rating. The application need to show a list of movies, where a logged-in users can rate the movie from 1-5 by giving a star and write a short comment or review.

## How to run the application:

The application can be run from "LocalApplicationRunner". 

### The application will run on:
[localhost:8080](http://localhost:8080) as requested in the exam.

## How to run the tests:

First you have to download the dependencies needed. Run ` mvn clean install` in the terminal, and it will do it for you.
When the dependencies have been downloaded, run `mvn verify` to the tests.

## Admin And User:

- Administrator
  - Email: admin@admin.com
  - Password: a
- User
  - Email: foo@bar.com
  - Password: a
                                 
## What I did in the exam based on the info and assigment given:

The exam says that ratings are "stars" and user can use them to rate the movies. 
When average is displayed the value I use is  Double and that with no limit on the decimals front. 
This is not requested in the exam. In the exam its written that the user can only give one review per movie, that constrained is there in my solution,
but I have given the user access to update the older review to a new one. In real life it would been boring to not delete review or have the chance to update it. 

## Requirements done:

 - R1, R2, R3 almost done (didnt get the sorting by rating or time to work on the frontend), I began with R4. I hope 
 I can reach grade C it is stated that "not Sufficient to get this X grade", so I hope I reach the C grade.

## Extras

 - Tests run with travis (picture in the folder).
 - Admin can add/create movies.
 - User can update review, but still keep the one review per movie constraint.

## Tests coverage
 - Backend 91% coverage
 - Frontend 31% Coverage
 - Total 68% Coverage