In this programming project, you will create a simulation of a restaurant service process using synchronization primitives like locks and semaphores from the pthread library in C. The total number of customers and servers should be input from the command line. The simulation should include random sleep times to represent customers looking around and servers waiting to serve customers.

Define a structure called struct lunch and four functions:

void lunch_init(struct lunch *lunch) - Initializes the lunch structure.
int lunch_get_ticket(struct lunch *lunch) - Returns a unique ticket number for each customer and provides output for entering, getting the ticket, and leaving.
lunch_wait_turn(struct lunch *lunch, int ticket) - Waits for the customer's turn to be served based on the ticket number and provides output for entering, waiting, and leaving after being served.
lunch_wait_customer(struct lunch *lunch) - Waits for a customer to be served and updates the "Now Serving" screen.
Requirements:

Use only one lock in each struct lunch.
Implement the Show_serving(int number) function to display the "Now Serving" screen.
Avoid busy-waiting.
Read the number of servers and clients from the command line.
Provide outputs for the execution of the four main functions.
Use an array for each type of threads and random sleep durations for customers and servers.
Call the mytime() function for obtaining random sleep times.
