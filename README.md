<img src = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/76/New_Walmart_Logo.svg/1280px-New_Walmart_Logo.svg.png" align = "right">

# WalmartCodingChallenge
Implement a simple ticket service that facilitates the discovery, temporary hold, and final reservation of seats within a high-demand performance venue.


For example, see the seating arrangement and pricing details for a simple venue below.

<img src = "https://s12.postimg.org/fr3ydyeyl/Screen_Shot_2016_08_24_at_2_55_06_AM.png" align = "center">

Supported Functions
=======


* Find the number of seats available within the venue, optionally by seating level
  (available seats are seats that are neither held nor reserved.)
* Find and hold the best available seats on behalf of a customer, which can be limited to specific levels
  (Each ticket hold will expire within 60 seconds.)
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

*Example*: 

<img src = "https://s4.postimg.org/6436j6h59/Screen_Shot_2016_08_26_at_2_01_57_PM.png" align = "center">

Let's say there are 5 rows in a given Level and there are 10 Seats in each row. So we have a total of 50 seats. Let R represent the unique row numbered from 1 to 5 with Row#1 being closest to the screen. Let S be the seats in each row which are numbered from 1 to 10.( See Diagram above for reference)

So a Seat at **R1-S1**(which is the left most seat on the first row) will have a higher score than the seat at **R1-S5**. This is because the seat R1,S5 is at the middle which will give the customer a better experience.

Similarly, seat **R3-S5** will have a higher score than R1-S5 because **R3-S5** is farther from the screen than **R1-S5** and hence has will provide a lesser experience to the customer


Installation Instructions 
=======

* Ensure that all Maven and Mysql are installed in your machine.

* Clone the repository using 

```bash
$ git clone git@github.com:vinothkumar6692/WalmartCodingChallenge.git
```

* Start the mySQL server using
```bash
$ sudo /usr/local/mysql/support-files/mysql.server start
```

NOTE: The application is configured to use MySQL's default settings
user id: "root"
password:(no password by default)
Local IP: localhost
Port:3306 
If you have configured a different user ID, password, Local IP/port - Make the corresponding change in the applicationContext.xml file

The applicationContext.xml is available in the src/main/java folder.

* Setup the database using the following command

```bash
$ cat src/main/resources/db_script.sql | mysql -u root
```
If you have configured a password for you MySQL server, use the following command

```bash
$ cat src/main/resources/db_script.sql | mysql -u root -p
```
Enter the password to your database when prompted. 

* To create the application package using maven, do

```bash
$ mvn package
```

* Reset the database using the following command

```bash
$ cat src/main/resources/db_init.sql | mysql -u root -p
```

* Run the application using 

```bash
$ java -jar target/ticketService-0.0.1-SNAPSHOT.jar service.TheaterTicketServiceController
```



Other Supported Functions
=======

In addition to the 3 key functions that are supported by the theater ticket service, the following are other functions that can also be supported by the current design.

* Find hold to reserve ratio (Number of tickets converted from hold to reserve)
* Find the level which is most/least booked
* Uniquely identify a customer hold/reserve request
* Find if a specific seat in the theater(with relative positon) is available or not


TroubleShooting
=======
* If you are having issues during build because the application is not connecting to the database, mdodify the applicationContext.xml file in target/classes to have the correct user id, password, Local IP and port settings. Clean the project using 
```bash
$ mvn build
```
and build again

Sample Screenshots
=======

Start Screen Menu
<img src = "https://s21.postimg.org/47nz5nnnr/Screen_Shot_2016_08_26_at_2_10_43_PM.png" align = "center">

View Total Available Seats in the Theater
<img src = "https://s16.postimg.io/mdm3y8hkl/Screen_Shot_2016_08_26_at_2_11_36_PM.png" align = "center">

Find and Hold Best Seats in the Theater
<img src = "https://s3.postimg.io/i6miilzcz/Screen_Shot_2016_08_26_at_2_12_14_PM.png" align = "center">

Hold and Reserve Tickets
<img src = "https://s22.postimg.io/pg20vcjsh/Screen_Shot_2016_08_26_at_2_13_14_PM.png" align = "center">

Confirmation Code on after succesful reservation
<img src = "https://s16.postimg.io/nlkedhvvp/Screen_Shot_2016_08_26_at_2_13_31_PM.png" align = "center">

Expired Hold.
<img src = "https://s13.postimg.org/ue16r6047/Screen_Shot_2016_08_26_at_2_15_53_PM.png" align = "center">





