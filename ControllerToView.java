//the controller and view components of MVC will communicate through these once they are implemented
public interface ControllerToView {

	//Pre: game is starting
	//Post: let user know the game is starting
	public void startingGame();
	
	//Pre: playerName must exist
	//Post: user asked if they want to restart the game and input returned as a String
	public String restartAfterEnd(String playerName);
	
	//Pre: computerPlayer exists
	//Post: user notified that it is the computer's turn
	public void computerTurn(String computerPlayer);
	
	//Pre: card just chosen must be displayed to user
	//Post: card chosen displayed to user using playerName
	public void displayChosenCard(String playerName, String card); //only user
	
	//Pre: card discarded from playerName's hand
	//Post: card discarded displayed to user using playerName
	public void displayDiscard(String playerName, String card); //user and computer
	
	//Pre: card exists
	//Post: user is notified which card the computer has discarded from its hand
	public void computerDiscard(String card, String computerPlayer);
	
	//Pre: hand needs to be displayed to the user
	//Post: hand is displayed to the user
	public void displayHand(String hand, String playerName);
	//print out hand to user
	
	//Pre: previous username already taken or empty username given
	//Post: asked user for another username and returned as a String
	public String getUserNameAgain(boolean empty);
	
	//Pre: new round is starting
	//Post: let user know the round is starting
	public void startingRound();
	
	//Pre: dealerName must exist as a String
	//Post: name of current dealer is displayed to the user
	public void displayDealer(String dealerName);
	
	//Pre: need to know if user wants to knock
	//Post: using playerName, user is asked if they want to knock and answer is returned as a String
	public String doesUserWantToKnock(String playerName);
	
	//Pre: need to know if the user wants to exit or restart the game
	//Post: user is asked if they want to exit or restart the game and the input is returned as a String
	public String doesUserWantToExitOrRestart(String playerName);
	//ask user if they want to exit and return the answer
	
	//Pre: user wants to restart game
	//Post: user notified that the game restarted
	public void restartGame();
	
	//Pre: the user's current score needs to be displayed
	//Post: the user's score is displayed to the user
	public void displayScore(int score, String playerName);
	//print out score to user
	
	//Pre: the game has ended
	//Post: the user is notified that the game has ended and if they have won using playerName
	public void endGame(boolean UserNotWon, boolean computerHasNotWon, String playerName);
	//let the user know the game has ended
	
	//Pre: playerName has ended this round
	//Post: the other players score, playerName's score, and the final score of the round are displayed to user
	public void displayHandScore(int sumOfOthers, int handScore, int finalHandScore, String playerName);
	
	//Pre: need to know if the user wants to pick from discard or stock pile
	//Post: user is asked if they want to pick from discard or stock pile and
		//input is returned as a String
	public String pickFromDiscardOrStock(String playerName);
	//ask user if they want to pick from discard or stock and return answer as a String
	
	//Pre: need to know what card user wants to discard
	//Post: user asked which card they want to discard and input returned as a String
	public String whichCardDiscard(String playerName);
	
	//Pre: top card of discard pile needs to be displayed
	//Post: top card of discard pile displayed to user as a String
	public void displayTopDiscard(String card);
	
	//Pre: wrong or bad representation of a card entered to discard
	//Post: message displayed to user telling that the wrong or bad representation was given 
	public void badRep(String playerName);
	
	//Pre: the stock pile has no more cards
	//Post: user is notified that stock pile is empty
	public void displayStockEmpty(String playerName);
	
	//Pre: user wants to play gin rummy and needs a name
	//Post: user's name is returned as a String
	public String getUserName();
	
	//Pre: first player is going first in the game
	//Post: user is notified that firstPlayer is going first in the game
	public void goingFirst(String firstPlayer);
	
}
