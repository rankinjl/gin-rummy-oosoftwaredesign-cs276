
//testing for the computer player is UNCOMMENTED to see the hand before and after picking/discarding (in computerPlayerTurn)
//the main controller in MVc
//runs the logic of the game
public class RunGame {
	
	
	private ModelCommunication model;
	private ViewCommunication view;
	
	//Pre: an object needs to be created to control and run the game of gin rummy
	//Post: an object is created that will control and run the game of gin rummy by
		// communicating with the model and view components
	public RunGame(){
		model = new ModelCommunication();
		view = new ViewCommunication();
	}
	
	//Pre: Game of gin rummy needs to be run
	//Post: a game of gin rummy is played; players are created and new rounds are played
		//until a player wants to exit
	public void runGinRummy(){
		createPlayers();
		boolean userWantsToRestart = true;
		while(userWantsToRestart){
			model.resetEmptyStock();
			model.resetRestart();
			view.startingGame();
			//play game until player wins or would like to exit
			while(humanHasNotWon() && computerHasNotWon() && !(model.doesUserExit()) &&!model.doesUserRestart() &&!model.isStockEmpty()){
				view.startingRound();
				playNewRound();
				model.rotateDealer();
			}
			if(model.isStockEmpty()){
				while(model.getName().equals(model.getComputerName())){
					model.nextPlayer();
				}
				view.displayStockEmpty(model.getName());
			}
			userWantsToRestart = areWeRestarting();
		}
		
		endGame();
		
	}
	
	//Pre: need to know if the user is restarting
	//Post: true is returned if user restarting game, false otherwise
	private boolean areWeRestarting(){
		//get user name
		String playerName = model.getName();
		while(playerName.equals(model.getComputerName())){
			model.nextPlayer();
			playerName = model.getName();
		}
		
		boolean userWantsToRestart;
		
		//should we ask if they want to exit or restart?
		if(model.isStockEmpty()){
			
			userWantsToRestart = restartAfterGameEnd(playerName);
		}
		else if(model.doesUserExit())
			userWantsToRestart = false;
		else if(!humanHasNotWon() || !computerHasNotWon()){
			endGame();
			userWantsToRestart = restartAfterGameEnd(playerName);
		}
		else
			userWantsToRestart = model.doesUserRestart();
		
		if(userWantsToRestart)
			model.restartGame();
		
		return userWantsToRestart;
	}
	
	//Pre: playerName exists
	//Post: true returned if user wants to restart game, false otherwise
	private boolean restartAfterGameEnd(String playerName){
		
		String answer = view.restartAfterEnd(playerName);
		try{
			answer = answer.trim().toLowerCase();
			if(answer.charAt(0)=='r')
				return true; //want to restart
		}catch(StringIndexOutOfBoundsException e){
			//do nothing, entered
		}
		return false; 
	}
	
	//Pre: players for a game of gin rummy need to be created
	//Post: players are created
	private void createPlayers(){
		model.createComputerPlayer();
		//get acceptable user name
		String usersName = view.getUserName().trim();
		while(usersName.toLowerCase().equals(model.getComputerName().toLowerCase()) || usersName.equals("")){
			usersName = view.getUserNameAgain(usersName.equals("")).trim();
		}
		model.createNewHumanPlayers(usersName);
	}
	
	//Pre: A new round of gin rummy would like to be played
	//Post: A round of gin rummy is played
	private void playNewRound(){
		
		//set up the equipment of the new round
		setUpRound();
		view.displayDealer(model.getDealerName());
		String firstPlayer = model.getName();
		view.goingFirst(firstPlayer);
		//while no knock, no winning, and no exiting, either human or computer player turn
		while(!model.didUserKnock() && !model.doesUserExit() && !model.doesUserRestart() && !model.isStockEmpty() && !didAnyoneWinRound(firstPlayer)){
			//if firstPlayer is not the computer
			if(!firstPlayer.equals(model.getComputerName()))
				humanPlayerTurn(firstPlayer);
			else
				computerPlayerTurn(firstPlayer);
			model.nextPlayer();
			firstPlayer = model.getName();
		}
		//round over
		
	}
	
	//Pre: curPlayer exists
	//Post: round scores and winner displayed if someone won and true returned, otherwise false returned
	private boolean didAnyoneWinRound(String curPlayer){
		boolean won = false;
		int numOfPlayers = model.getNumPlayers();
		while(numOfPlayers>=0 &&!won){
			boolean winner = model.allCardsRemoved(model.getName());
			if(winner){
				won = true;
			}
			numOfPlayers--;
			model.nextPlayer();
		}
		
		while(!model.getName().equals(curPlayer))
			model.nextPlayer();
		return won;
	}
	
	
	//Pre: firstPlayer exists as a String and it is the human player's turn
	//Post: human player picks a card, discards a card, and is asked if they want to knock or exit
		//OR the stock pile is empty
	private void humanPlayerTurn(String curPlayer){
			//let user pick a card
			view.displayHand(model.getCurHandRepresentation(), curPlayer);
			view.displayTopDiscard(model.getTopDiscard());
			pickACard(curPlayer);
			discardACard(curPlayer);
			//ask if user wants to knock if not win
			if(!didAnyoneWinRound(curPlayer)){
				knockOrExit(curPlayer);
			}
			else{
				displayRoundWin(model.getName());
			}
					
	}
	
	//Pre: need to know if the user wants to knock or exit
	//Post: using curPlayer, user asked if they want to knock, and if they do not,
		//they are asked if they want to exit the game
	private void knockOrExit(String curPlayer){
		String input = view.doesUserWantToKnock(curPlayer);
		char firstLetter;
		try{
			input = input.toLowerCase().trim();
			firstLetter = input.charAt(0);
		}catch(StringIndexOutOfBoundsException e){
			//enter hit, user not want to knock
			firstLetter = 'n';
		}
		if(firstLetter == 'y')
			playerKnocked(curPlayer);
		else
			if( shouldWeExitOrRestart(curPlayer))
				model.restartGame();
	}
	
	//Pre: computerPlayer exists as a String and it is the computer player's turn
	//Post: computer player picks a card from the stock pile and discards a card
	private void computerPlayerTurn(String computerPlayer){
		view.computerTurn(computerPlayer);
		//TESTING:
		view.displayHand(model.getCurHandRepresentation(), computerPlayer);
		
		//must pick from stock, cannot pick from discard
		String pickedCard = model.pickCardFromStock();
		
		//cannot end round yet. keep playing until user wants to knock
		//choose a card to discard
		if(pickedCard!=null){
			//TESTING:
			view.displayChosenCard(computerPlayer, pickedCard);

			String card = model.computerMapToDiscard();
			view.computerDiscard(card, computerPlayer);	
		}
		
		//TESTING:
		//view.displayHand(model.getCurHandRepresentation(), computerPlayer);
	}
	
	
	//Pre: user needs to discard a card
	//Post: a card from the user's hand is discarded 
	private void discardACard(String playerName){
		String pattern = "[0-9]+";
		String discarded;

		do{
			String cardToDiscard = view.whichCardDiscard(playerName);
			boolean okay = cardToDiscard.matches(pattern);
			while(!okay){
				view.badRep(playerName);
				cardToDiscard = view.whichCardDiscard(playerName);
				okay = cardToDiscard.matches(pattern);
			}
			try{
				discarded = model.discardFromHand(cardToDiscard);
				if(discarded == null)
					view.badRep(playerName);
			}catch(NumberFormatException e){
				discarded = null;
				view.badRep(playerName);
			}
		}while(discarded==null);
		
		view.displayDiscard(playerName, discarded);
	}
	
	//Pre: user needs to pick a card
	//Post: user picks a card from stock or discard pile and true is returned
		//OR stock is empty and false is returned
	private void pickACard(String playerName){
		String whereToPick = view.pickFromDiscardOrStock(playerName);
		char picking;
		try{
			whereToPick = whereToPick.trim().toLowerCase();
			picking = whereToPick.charAt(0);
			if(picking=='d')
				model.pickCardFromDiscard();
			
		}catch(StringIndexOutOfBoundsException e){
			//enter was hit
			picking = 'n';
		}
		if(picking !='d'){
			String stockPick = model.pickCardFromStock();
			//not discard
			if(stockPick !=null)
				view.displayChosenCard(playerName, stockPick);
		}



	}
	
	//Pre: playerName must exist
	//Post: user is asked if they want to restart or exit; true is returned if want to restart game
	private boolean shouldWeExitOrRestart(String playerName){
		
			String answer = view.doesUserWantToExitOrRestart(playerName);
			try{
				answer = answer.trim().toLowerCase();
				if(answer.charAt(0)=='e')
					model.userWantsToExit();
				else if(answer.charAt(0)=='r'){
					view.restartGame();
					return true;
				}
			}catch(StringIndexOutOfBoundsException e){
				//do nothing, entered
			}
			return false; 
		
	}
	
	//Pre: a human player has knocked
	//Post: the knockPlayer's cards are summed and added to their totalScore,
		// the knocker's hand is displayed, and the knocker's current totalScore is displayed
	private void playerKnocked(String playerName) {
		model.userKnocked();
			
		//display hand from model
		view.displayHand(model.getCurHandRepresentation(), playerName);
		
		int thisPlayerPts = sumDeadWood();
		model.nextPlayer();
		int otherPlayerPts = sumDeadWood();
		if(thisPlayerPts<=otherPlayerPts){
			model.nextPlayer();
			displayRoundWin(model.getName());	
		}
		else //other player has less deadwood
			displayRoundWin(model.getName());	
			
	}
	
	//Pre: winner has won the round
	//Post: scores are calculated and displayed to the user
	private void displayRoundWin(String winner){
		while(!model.getName().equals(winner)){
			model.nextPlayer();
		}
		
		//sum deadwood in winner's hand
		int thisHandSum = sumDeadWood();
		//sum deadwood in other players' hands
		int sumOfAll = 0;
		model.nextPlayer();
		while(!model.getName().equals(winner)){
			sumOfAll = sumOfAll + sumDeadWood();
			model.nextPlayer();
		}
				
		int finalHandScore = sumOfAll-thisHandSum;
		//subtract this hand total from other players' deadwood
		model.totalScore(finalHandScore);
		//display this hand's score
		view.displayHandScore(sumOfAll, thisHandSum, finalHandScore, winner);
		
		//display current total score
		view.displayScore(model.getCurPlayerScore(), winner);
		
		if(winner.equals(model.getComputerName()))
			model.nextPlayer();
		//ask if they want to play again or restart
		if(humanHasNotWon()&&computerHasNotWon()&&!model.isStockEmpty())
			if(shouldWeExitOrRestart(model.getName()))
				model.restartGame();
		
		//persistently store data
		model.storeData(winner, thisHandSum, sumOfAll);
	}

	//Pre: need to find the point sum of the deadwood cards in the hand
	//Post: sum of deadwood points in hand of cards found and returned as an int 
		//(we look for the hand with least deadwood)
	private int sumDeadWood(){
		int AthenB = model.getDeadwoodAthenB();
		int BthenA = model.getDeadwoodBthenA();

		if(AthenB <= BthenA){ //want AthenB
			model.takeOutMeldACards();
			model.takeOutMeldBCards();
		}else{ //want BthenA
			model.takeOutMeldBCards();
			model.takeOutMeldACards();
		}
		
		int valueSum = model.getHandSum();
		return valueSum;
	}
		

	//Pre: The current game of gin rummy is over
	//Post: The user is notified that the current game of gin rummy is closed and if they won
	private void endGame(){
		if(model.getName().equals(model.getComputerName()))
			model.nextPlayer();
		String playerName = model.getName();
		view.endGame(humanHasNotWon(), computerHasNotWon(), playerName);
	}
	
	//Pre: A new round is going to be played and needs the equipment set up
	//Post: The equipment for a new round is set up including the deck and a hand of cards for each player
	private void setUpRound(){
		//create the deck that will be used
		model.createDeck();
		//reset variable for user knock
		model.resetKnocking();
		//allow player(s) to deal 10 cards to selves
		dealCards();

	}

	//Pre: cards need to be dealt to the players
	//Post: cards are dealt to each player
	private void dealCards() {
		int count = 0;
		int numPlayers = model.getNumPlayers();
		while(count<numPlayers){
			//deal to a single player
			model.dealCards();
			count++;
			model.nextPlayer();
		}
	}

	//Pre: computer player exists
	//Post: false is returned if computer player has won, true otherwise
	private boolean computerHasNotWon(){
		int numPlayers = model.getNumPlayers();
		int winningScore = model.getWinningScore();
		String compName = model.getComputerName();
		while(numPlayers>0){
			if(model.getName().equals(compName)){
				int curPlayerScore = model.getCurPlayerScore();
				if(curPlayerScore>=winningScore)	//computer player has won
					return false;
			}
			numPlayers--;
			model.nextPlayer();
		}
		return true;
		
	}
	
	
	//Pre: human player exists
	//Post: false is returned if human player has won, true otherwise
	private boolean humanHasNotWon(){
		int numPlayers = model.getNumPlayers();
		int winningScore = model.getWinningScore();
		String compName = model.getComputerName();
		while(numPlayers>0){
			if(!model.getName().equals(compName)){
				int curPlayerScore = model.getCurPlayerScore();
				if(curPlayerScore>=winningScore)	//human player has won
					return false;
			}
			numPlayers--;
			model.nextPlayer();
		}
		return true;
	}
	
	
	
	
}
