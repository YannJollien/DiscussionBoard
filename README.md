DiscussionBoard

Android Studio Project; Welcome to my Android Application Project created by Yann Jollien ad the HES-SO Valais/Wallis.

The goal of the application is to have an application which provides a discussion board with different topics and posts from users.

The application is based on the room persistence library architecture and is currently working with RoomDatabase.

#Database 
Tables
-Thread --> Storing threads
-Post --> Storing posts
-User --> User management for Login and further activities
-Feed --> Live information about posted threads and posts by users
-TemporaryThread --> Pending thread requests

Information about databases; The current version of the application is working without any use of Foreign Keys. Why this decision ? While I was programming the application I figured out that 
it perfectly works without foreign keys. Furthermore foreign keys were not allowed on primary keys based on auto generated integers, which is part of most of my tables.
The decision not to use Foreign Keys is not due to lack of competences, I perfectly understand the use of foreign keys but I decided not to use them.

Therefore it only makes sense to use a foreign key with the tables threads and posts because this is the only case where a delete affects both tables. My solution works perfectly fine so far,
certainly I won't argue about the utility of using foreign keys.

I will work with foreign keys for the next delivery.

#Functionalities

The user can create an acount to log in to the application.
(For testing purposes you can use; test@bluewin.ch pwd 123456 (admin account) , user@bluewin.ch pwd 123456)

Add Threads

Add posts 
Delete posts
Update posts

Visualize feed of events 

Administrate users
Administrate threads

Change language of the application -- EN / DE

See profile informations
Cahnge your user password

See informations about application

#Special mentions

-Use of SharedPreferences for AlertDialog at start of some activities
-A thread has to be validated by an admin 
-The whole archtiecture is slightly different from the example app on cyberlearn, the inspiration comes from https://www.youtube.com/channel/UC_Fh8kvtkVPkeihBs42jGcA

#Testing device during developpement
Google Pixel XL / Samsung Galaxy S10



