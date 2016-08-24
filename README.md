<img src = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/New_Walmart_Logo.svg/1280px-New_Walmart_Logo.svg.png" align = "right">

# WalmartCodingChallenge
Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue.


For example, see the seating arrangement and pricing details for a simple venue below.

<img src = "https://s12.postimg.org/fr3ydyeyl/Screen_Shot_2016_08_24_at_2_55_06_AM.png" align = "center">

Supported Functions
=======


* Find the number of seats available within the venue, optionally by seating level
  (available seats are seats that are neither held nor reserved)
* Find and hold the best available seats on behalf of a customer, which can be limited to specific levels
  (Each ticket hold will expire within 60 seconds.
* Reserve and commit a specific group of held seats for a customer

Requirements
=======

* Java Spring
* Maven
* Java JDK 1.8
* Junit
* MySQL

Assumptions & Design
=======

* For any given theater configuration, each seat in the theater can be uniquely identified with three fields. *Level No, Row No and Seat No*. These are the parameters that a customer needs to identify his held/reserved seats.

* Each Row in a given level is numbered from 1 to N ( N is the number of rows in a given level)

* Within each row, seats are numbered from 1 to n (n is the number of seats in each row)

* The service automatically creates a unique seat ID for each seat in the theater. This seat ID is hidden to the user and is for the theater admin reference only.

### Best Seat Selection Criteria: ###

The application automatically generates a unique score for each seat in the theater on the following basis. S1 and S2 are any two seats in the theater. If Score of S1 is lesser than Score of S2, then S1 is a better seat than S2. In Short, a seat with lesser score is better. 

* Within each row for any given level, the middle seats are given a lower score when compared to the seats from the right and left corners. 
* Within each level, rows which are closer to the screen have lower score. 
* So the relative position of a seat in the theater determines the score for that seat.
* In the given example, Seats in Level 1 will always have a lower score than seats in level 2.(which also translates to the price since seats in Level 1 are $100 whereas seats in Level 2 are $75). 

*Example*: Let's say there are 5 rows in a given Level and there are 10 Seats in each row. So we have a total of 50 seats. Let R represent the unique row numbered from 1 to 5 with Row#1 being closest to the screen. Let S be the seats in each row which are numbered from 1 to 10.

So a Seat at **R1-S1**(which is the left most seat on the first row) will have a higher score than the seat at **R1-S5**. This is because the seat R1,S5 is at the middle which will give the customer a better experience.

Similarly, seat **R3-S5** will have a higher score than R1-S5 because **R3-S5** is farther from the screen than **R1-S5** and hence has will provide a lesser experience to the customer





Installation Instructions 
=======





