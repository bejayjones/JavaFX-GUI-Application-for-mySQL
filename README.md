# C195
This program acts as a GUI application to interact with a given mySQL database.
Allow users to add, update, review, and remove customers and appointments from the database,
as well as pull reports based on the appointments within the database.

AUTHER: Brandon Jones
Software 2: QAM2 11-19-2021

IDE: IntelliJ IDEA Community Edition 2021.2.1, JDK Java SE 11.0.4, JavaFX SDK 11.0.2
MySQL Connector Driver mysql-connector-java-8.0.27.jar

When you run the program you will be prompted to log in, use the creditionals
username: test
password: test

Upon log in, you will be redirected to the home screen,
here you can see all of the customers in the database in one table and all of the appointments in another table.

Under each table there are three buttons. The new button will redirect you to a window where you can input a new customer or appointment
the update button will redirect you to a window where you can update an existing customer or appointment
then finally the delete button will delete the selected customer or appointment.

*DELETING A CUSTOMER WILL DELETE ALL APPOINTMENTS IN WHICH THE SELECTED CUSTOMER IS STORED AS THE CUSTOMER ID"

The last button on the home page is a reports button which will redirect you to a view where you can see three reports:
Top-left: Shows the total number of appointments grouped by Month and Type.
Top-Right: Shows the total number of appointments by year
Bottom: Shows every appointment associated with a selected contact given by the combo box at the very bottom of the view.

