import java.util.ArrayList;



//the controller and model components of MVC will communicate through these once they are implemented
public interface ControllerToModel {

	//Pre: amt should be added to player's totalScore
	//Post: amt is added to this player's total Score
	public void totalScore(int amt);
	//add amt to player's totalScore
	
	//Pre: stock pile exists
	//Post: the variable keeping track of if the stock is empty is reset to false
	public void resetEmptyStock();
	
	//Pre: the stock pile exists
	//Post: true is returned if the stock pile is empty, false otherwise
	public boolean isStockEmpty();
	
	//Pre: the player's current score is needed
	//Post: player's current score is returned as an int
	public int getCurPlayerScore();
	//return player's totalScore
	
	//Pre: user wants to restart the game
	//Post: the game is restarted with total score of zero for both players
	public void restartGame();
	
	//Pre: need to know if user wants to restart
	//Post: true is returned if user wants to restart. false otherwise
	public boolean doesUserRestart();
	
	//Pre: need to reset restart
	//Post: restart is false
	public void resetRestart();
	
	//Pre: the score needed to win the game is needed
	//Post: the score needed to win the game is returned as an int
	public int getWinningScore();
	//return the score needed to win the game

	//Pre: number of players is needed
	//Post: the number of players in this game is returned as an int
	public int getNumPlayers();
	//return the number of players in the game
	
	//Pre: user wants to exit the game
	//Post: exit is set to true
	public void userWantsToExit();
	//sets exit to true because the user wants to exit
	
	//Pre: need to know if user wants to exit
	//Post: true is returned if user wants to exit; false otherwise
	public boolean doesUserExit();
	//returns true if user wants to exit and false otherwise
	
	//Pre: curPlayer must exist and cards need to be dealt to curPlayer
	//Post: the max number of cards is dealt from the deck and dealt to curPlayer
		//OR an exception is thrown
	public void dealCards();
	//deals cards to thisPlayer
	
	//Pre: a representation of this existing hand is needed
	//Post: a representation of this hand is returned as a String
	public String getCurHandRepresentation();
	//returns the curPlayer's current hand representation as a String
	
	//Pre: players need to be created
	//Post NUMOFPLAYERS created and added to players
	public void createNewHumanPlayers(String username);
	//create the players for the game
	
	//Pre: next dealer is needed
	//Post: dealer is rotated to the next player
	public boolean rotateDealer();
	
	//Pre: need to know if the user knocked
	//Post: returns true if user has knocked, false otherwise
	public boolean didUserKnock();
	
	//Pre: user has knocked
	//Post: userKnocked set to true
	public void userKnocked();

	//Pre: players must be created and dealers name wanted
	//Post: dealer's name returned as a String
	public String getDealerName();
	
	//Pre: need to reset variable keeping track of user knocking
	//Post: user not recorded as knocking this round
	public void resetKnocking();
	
	//Pre: deck needs to be created
	//Post: deck is created and set equal to deck1
	public void createDeck();
	//create the deck to be used for the game
	
	//Pre: the next player is needed
	//Post: curPlayer is set to the next player unless there are no players
	public void nextPlayer();
	//switches the current player to the next player
	
	//Pre: the size of curPlayer's hand is needed
	//Post: the size of curPlayer's hand is returned as an int
	public int getHandSize();
	//returns the size of curPlayer's hand as an int

	//Pre: sum of curPlayer's hand cards is needed
	//Post: sum of hand cards is returned as an int
	public int getHandSum();
	
	
	//Pre: a computer player needs to be created for this game
	//Post: a computer player is created
	public void createComputerPlayer();
	
	//Pre: user wants to pick a card from the stock pile
	//Post: if there is a card in the stock pile, top card of stock pile added to user's playerHand
	public String pickCardFromStock();
	
	//Pre: user wants to pick a card from the discard pile
	//Post: if there is a card in the discard pile, add to user's playerHand
	public boolean pickCardFromDiscard();
	
	//Pre: player must exist
	//Post: if all cards in melds, return true. otherwise false
	public boolean allCardsRemoved(String player);
	
	//Pre: top card of the discard pile is needed
	//Post: top card is returned as a String
	public String getTopDiscard();
	
	//Pre: need to discard Card_276 with cardRep as representation from current player's hand
	//Post: true is returned if card is discarded, false is returned if card not in hand
	public String discardFromHand(String cardRep);
	
	//Pre: need player's name
	//Post: returns player's name as a String
	public String getName();
	
	//Pre: name of computer player is needed
	//Post: name of computer player is returned as a String
	public String getComputerName();
	
	//Pre: computer needs to discard a card
	//Post: computer discards a card from hand
	public String computerMapToDiscard();
	
	//Pre: data needs to be stored for this hand for both players
	//Post: totalscore, deadwood count stored for each player for this hand
	public void storeData(String winner, int winnerDeadWood, int otherDeadWood);
	

}
