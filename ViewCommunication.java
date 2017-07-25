
//allows the controller and view to communicate
public class ViewCommunication implements ControllerToView{

	UserViewCommunication toUser; //to communicate with the user
	
	//Pre: an object needs to be created so that the controller can talk to the view for the user
	//Post: an object is created so the controller and view can communicate using 
		//a UserViewCommunication object to talk to the user
	public ViewCommunication(){
	 toUser = new UserViewCommunication();
	}
	
	//Pre: a game is starting
	//Post: let user know the game is starting
	@Override
	public void startingGame(){
		toUser.display("Starting new game...");
	}
	
	//Pre: computerPlayer exists
	//Post: user notified that it is the computer's turn
	@Override
	public void computerTurn(String computerPlayer){
		toUser.display("******************************************************************************** \n\nComputer player " +computerPlayer+ "'s turn...");
	}
	
	//Pre: card exists
	//Post: user is notified which card the computer has discarded from its hand
	public void computerDiscard(String card, String computerPlayer){
		toUser.display("Computer player " +computerPlayer+ " has discarded " + card +"\n\n********************************************************************************");
	}
	
	//Pre: playerName must exist
	//Post: user asked if they want to restart the game and input returned as a String
	public String restartAfterEnd(String playerName){
		toUser.display(playerName +", would you like to restart the game? ('r' for restart, any other key for no)");
		return toUser.getInput();
	}
	
	//Pre: new round is starting
	//Post: let user know the round is starting
	@Override
	public void startingRound(){
		toUser.display("Starting new round...\n");
	}
	
	//Pre: user wants to play gin rummy and needs a name
	//Post: user enters a name and it is returned as a String
	@Override
	public String getUserName(){
		toUser.display("Enter a user name you would like to be called: ");
		return toUser.getInput();
	}
	
	//Pre: previous username already taken or username given empty
	//Post: asked user for another username and returned as a String
	@Override
	public String getUserNameAgain(boolean empty){
		if(empty)
			toUser.display("User name must not be empty.");
		else
			toUser.display("User name is already taken.");
		return getUserName();
	}
	
	//Pre: need to know if user wants to knock
	//Post: using playerName, user is asked if they want to knock and answer is returned as a String
	@Override
	public String doesUserWantToKnock(String playerName){
		toUser.display(playerName + ", do you want to knock? ('y' for yes, any other key for no)");
		return toUser.getInput();
	}
	
	//Pre: hand needs to be displayed to the user
	//Post: hand is displayed to the user using playerName
	@Override
	public void displayHand(String hand, String playerName) {
		toUser.display(playerName + "'s current hand:\n" + hand);
		
	}
	
	//Pre: dealerName must exist as a String
	//Post: name of current dealer dealerName is displayed to the user
	@Override
	public void displayDealer(String dealerName){
		toUser.display(dealerName + " is the dealer.");
	}

	//Pre: need to know if the user wants to exit or restart the game
	//Post: user is asked if they want to exit or restart the game using playerName and the input is returned as a String 
	@Override
	public String doesUserWantToExitOrRestart(String playerName) {
		toUser.display(playerName + ", do you want to end or restart the game? ('e' for exit, 'r' for restart, any other key to continue)");
		String answer = toUser.getInput();
		return answer;
	}
	
	//Pre: user wants to restart game
	//Post: user notified that the game restarted
	public void restartGame(){
		toUser.display("\nRestarting Game...\n");
	}

	//Pre: card just chosen must be displayed to user
	//Post: card chosen displayed to user using playerName
	public void displayChosenCard(String playerName, String card){
		toUser.display(card + " added to " + playerName + "'s hand");
	}
	
	//Pre: card discarded from playerName's hand
	//Post: card discarded displayed to user using playerName
	public void displayDiscard(String playerName, String card){
		toUser.display(card + " discarded from " + playerName +"'s hand");
	}
	
	//Pre: the user's score needs to be displayed
	//Post: the user's score is displayed to the user using playerName
	@Override
	public void displayScore(int score, String playerName) {
		toUser.display(playerName + "'s current total score: " + score);
	}

	//Pre: playerName has ended this round
	//Post: the other players score, playerName's score, and the final score of the round are displayed to user
	@Override
	public void displayHandScore(int sumOfOthers, int handScore, int finalHandScore, String playerName){
		toUser.display("\nWINNER: " +playerName+"\n");
		toUser.display("Other player's deadwood count: "+ sumOfOthers);
		toUser.display(playerName + "'s independent score this hand: " +handScore);
		toUser.display("Total being added to "+playerName+"'s total score: "+ finalHandScore);
	}
	
	//Pre: the game has ended
	//Post: the user is notified that the game has ended and if they or the computer have won using playerName
	@Override
	public void endGame(boolean UserNotWon, boolean computerHasNotWon, String playerName) {
		toUser.display("Game Over");
		if(!UserNotWon){
			toUser.display(playerName + " wins!");
		}else if(!computerHasNotWon)
			toUser.display("Computer wins...");
		
	}
	
	//Pre: top card of discard pile needs to be displayed
	//Post: top card of discard pile displayed to user as a String
	@Override
	public void displayTopDiscard(String card){
		toUser.display("Current top card of discard pile: " +card);
	}
	
	//Pre: firstPlayer must exist as a String
	//Post: user is notified that firstPlayer is going first in the game
	public void goingFirst(String firstPlayer){
		toUser.display(firstPlayer + " is going first.\n");
	}
		
	//Pre: need to know what card user wants to discard
	//Post: user asked which card they want to discard using playerName and input returned as a String
	@Override
	public String whichCardDiscard(String playerName){
		toUser.display(playerName+", which card would you like to discard? (answer as the number to the left of the card names)");
		return toUser.getInput();
	}

	//Pre: need to know if the user wants to pick from discard or stock pile
	//Post: user is asked if they want to pick from discard or stock pile using playerName and
		//input is returned as a String
	@Override
	public String pickFromDiscardOrStock(String playerName){
		toUser.display(playerName +", do you want to pick from the discard pile or stock pile ('d' for discard, any other key for stock)");
		return toUser.getInput();
		
	}
	
	//Pre: wrong or bad representation of a card entered to discard
	//Post: message displayed to user telling that the wrong or bad representation was given using playerName
	@Override
	public void badRep(String playerName){
		toUser.display(playerName + ", card representation given was not acceptable.");
	}
	
	
	//Pre: the stock pile has no more cards
	//Post: user is notified that stock pile is empty using playerName
	@Override
	public void displayStockEmpty(String playerName){
		toUser.display("\n\n" + playerName + ", stock pile is empty. Round over. No points awarded.\n\n");
	}

	
	
}
