Warlords

COMPSYS302 - Group 19 - Project A

What is it ?
-----------------

In the distant future, humanity has found a new method of diplomacy in the form of a sport set in intergalactic territory, known as Warlords.
In this sport, countries send off their most dangerous criminals worthy of execution out to space in specially designed pods. A rectangular arena is set up, and players are matched to fight to the death for their country in a knockout tournament where losing means death.
Four players participate in a single round of Warlords. Rounds are played until there is one winner, who is sent back home as a national hero, with all crimes forgiven and a chance to start their life anew. The losers, however, will have paid for their crimes with death.
You are a wrongly accused criminal sent from your home country to participate in the next round of Warlords. Determined to win and clear your name in front of the billions watching, you have been transported to the arena to start the match.

How to Play
-----------------

From the main menu, select "SINGLE PLAYER". Navigation is done using the arrow keys to select an option and ENTER to select.

In the game, use the arrow keys to move the paddle around.
Use TAB to go back to the main menu without completing the game.

Location of MVC Diagram
------------------

The MVC Diagram can be found in the project root folder, labelled "mvcPrototype_group19.pdf".

Compilation Instructions
------------------
NOTE: THIS PROJECT USES JAVA 8 (JRE1.8.0_112). PLEASE USE JAVA 8 WHEN RUNNING THE PROGRAM FROM ECLIPSE.

Steps:
- Open Eclipse Mars Java
- Navigate to File -> Import -> Git -> Projects from Git
- Select "Clone URI"
- Enter authentication details and URI from Bitbucket -> Repositories -> UoA-CS302-2017-Group19 -> Clone.
- Once complete, in Eclipse go to Project -> Clean, then Project -> Build All.
- Run the application by going Run -> Run or clicking the green Play button.


Troubleshooting
------------------
If you run into issues with the JavaFX API being forbidden, try:
- Right click on project and select properties
- Open Java Compiler >> Error/Warnings
- Under 'Deprecated and Restricted' change 'Forbidden references' from Error to Ignore

If you are on JAVA 1.7 and lower and need to switch to JAVA1.8, try:

- Open the project on Eclipse.
- Right click project -> Properties -> Java Build Path.
- Click Add Library, and select JRE System Library.
- Click Installed JREs, and then click Add. Select Standard VM and grab the Java 1.8 .jar from directory /usr/lib/jvm/jdk1.8.0_91
- Click OK and click Finish.
- Right click the project and head to properties again and set the compliance level to 1.8.
- In our project, the directory src/group19/testcases contains two external libraries to be added (for the test cases), JUnit 4 and Hamcrest 1.3. To add these libraries, right click the project in Eclipse and click Properties. Click Java Build Path and Add External JARs. Navigate to the libraries, select them and click OK.
