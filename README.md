# CSE-308-Server

The backend for a web app that attempts to find a fair districting of New York State.
Built for CSE 308: Software Engineering by Kristian Charbonneau, Miki Pokryvailo, 
Yormax Castro and Teddy Choudhury. 

The web app allows you to run two redistricting algorithms (simulated annealing and 
region growing) for New York state and shows the results in real time. Both algorithms 
try to maximize an objective function that takes into account the population equality, 
"wasted" votes, partisan fairness and geographic compactness. 

The backend was built with Java, Spring Boot and MySQL. 
