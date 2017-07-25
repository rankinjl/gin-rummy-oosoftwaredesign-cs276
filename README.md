# gin-rummy-oosoftwaredesign-cs276

For CS276, Object Oriented Software Design, each student implemented a comprehensive program for a chosen game. I implemented Gin Rummy as a Java program following the Model-View-Controller architectural pattern. The Class Diagram and State Machine Diagram are given in Dia files, the rules I was required to implement are given in a PDF file, and the schedule I made myself to follow throughout the semester is given as a docx file.

The single user plays against one computer player, "Jessica". The game begins with the dealer shuffling the deck and dealing 10 cards to each player. The next card in the remaining deck is flipped over to create the discard pile. The non-dealer player begins play by either drawing the top card from the discard pile or drawing the top card from the remaining deck. Then the same player discards a card from their hand and places it on the discard pile. 

The goal is to get one's hand into melds (sets of 3-4 cards sharing the same rank/numbers or runs of 3+ cards in sequence in the same suit - NO INTERSECTING MELDS) and eliminate deadwood (cards not in melds). Players alternate turns until one of the following occurs: a player ends the hand with all 10 cards in melds, one player knocks, there are no cards in the remaining deck left. If there are no cards remaining in the deck, no points are awarded and another round takes place. If a player knocks or has all cards in melds, the player with the smallest deadwood count wins and the other player's deadwood count is added to the winning player's game total. If this winning player happens to have deadwood, it is subtracted from the other player's deadwood count before adding to the winning player's game total. 

The goal of the game is to be the first player to reach 100 points. A single round in a game continues as long as there are cards left in the remaining deck, no player knocks, and no player has all cards in melds. Rounds continue until someone wins the game with 100 points. Starting consecutive games may continue as long as the user wants.

Points are awarded as follows:
- Ace: 1 point
- Face cards (J, Q, K): 10 points
- All other cards according to numerical value (ie, the 5 of Spades is worth 5 points)

Card_276.java
- represents a single card object with a suit, rank, and value
- these objects are comparable based on the card representation (number 0-51)

ClassDiagram8.dia
- the Class Diagram for this project showing different interactions and relationships among the classes

ComputerPlayer.java
- **Subclass** of PlayerHand using ```extends``` ("is-a" relationship)
- represents the hand of cards for a computer player (In this case "Jessica").

ControllerToModel.java
- **Interface** for the Controller to communicate with the Model and for the Model to communicate with the Controller

ControllerToView.java
- **Interface** for the Controller to communicate with the View and for the View to communicate with the Controller

Deck.java
- uses an ArrayList of Card_276 to represent the unused cards of the game to make sure there are no duplicate cards
- used to deal cards to players, shuffle cards

DiscardPile.java
- uses an ArrayList of Card_276 to represent the used cards of the game that are now discarded (not in unused deck or playerhands)
- used to peek at top card, pick up top card, discard

HumanPlayer.java
- **Subclass** of PlayerHand using ```extends``` ("is-a" relationship)
- represents the hand of cards for a human player (In this case the user).

MaintainDOM.java
- outputs the game data into "CSC276XMLOutput.xml" as the game is played
- acts as the Model for the game in MVC

ModelCommunication.java
- the main *Model* object
- **Subclass** of ControllerToModel using ```implements```
- allows the Controller components to store and access data
- contains and mediates access to the different PlayerHands, Deck, the current PlayerHand, DiscardPile, dealer PlayerHand, MaintainDOM

PlayGinRummy.java
- starts a Gin Rummy Game by making and running a RunGame object in the main method

PlayerHand.java
- represents a hand of cards for a specific player
- holds and controls the cards in the hand as an ArrayList

Rules_GinRummy.pdg
- describes the keywords used in Gin Rummy and the rules that needed to be implemented

RunGame.java
- the main *Controller* object
- communicates with the Model (ModelCommunication) and View (ViewCommunication) objects
- contains the majority of the "Business Logic"/actual implementation of the game

SpiralPlan.docx
- describes the schedule I followed throughout the semester to manage my time and implement all the rules for Gin Rummy
- follows increment 4-9 and lists which project requirements were implemented, why they were implemented in that order, and the estimated and actual hours spent on that increment implementation

StateMachineDiagram8.dia
- the State Machine Diagram that shows the behavior of the Gin Rummy system

UserViewCommunication.java
- part of the *View* 
- used by the View to communicate directly with the user by getting input with a System.in Scanner and displaying messages with System.out 

ViewCommunication.java
- the main *View* object
- **Subclass** of ControllerToView using ```implements```
- allows the Controller components to display data to the user and get input from the user
- uses a UserViewCommunication object 
