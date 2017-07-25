import java.util.ArrayList;
import java.util.Iterator;

//allows the controller and view to communicate
public class ModelCommunication implements ControllerToModel{

	private final int WINNINGSCORE = 100; //score needed to win the game
	private final int NUMOFPLAYERS = 2; //number of human players in the game

	private ArrayList<PlayerHand> players; //players' hands
	private Deck deck1; //the deck being used for game
	private boolean exit; //true if the player wants to exit the game
	private Iterator<PlayerHand> iter; //iterator over the players
	private PlayerHand curPlayer; //the current player
	private DiscardPile discardPile; //the discard pile for the game
	private PlayerHand dealer; //the current dealer in the game
	private boolean userKnocked; //true if the user has knocked
	private boolean restart; //true if user wants to restart game
	private boolean emptyStock; //true if stock is empty
	private MaintainDOM dom; //helps permanently store info
	
	//Pre: an object needs to be created so that the controller can talk to 
		//the model that holds all the data
	//Post: an object is created so the controller and model can communicate,
		//the model object instantiates exit, players, deck1 and sets iter and curPlayer to null
	public ModelCommunication(){
		exit = false;
		players = new ArrayList<PlayerHand>();		
		userKnocked = false;
		restart = false;
		//deck1, iter, curPlayer, discardPile, dealer, emptyStock instantiated later

	}
	
	//Pre: stock pile exists
	//Post: the variable keeping track of if the stock is empty is reset to false
	public void resetEmptyStock(){
		emptyStock = false;
	}
	
	//Pre: the stock pile exists
	//Post: true is returned if the stock pile is empty, false otherwise
	public boolean isStockEmpty(){
		return emptyStock;
	}
	
	//Pre: next dealer is needed
	//Post: dealer is rotated to the next player and true is returned or
		//players are not created yet and false is returned
	public boolean rotateDealer(){
		if(NUMOFPLAYERS>0 && curPlayer!=null){
			while(dealer!=curPlayer){ //stop when curPlayer is the dealer
				nextPlayer();
			}
			nextPlayer();			
			dealer = curPlayer;
			nextPlayer();
			return true;
		}else
			return false;
	}
	
	//Pre: need to know if user wants to restart
	//Post: true is returned if user wants to restart. false otherwise
	public boolean doesUserRestart(){
		return restart;
	}
	
	//Pre: need to reset restart
	//Post: restart is false
	public void resetRestart(){
		restart = false;
	}
	
	//Pre: user wants to restart the game
	//Post: the game is restarted with total score of zero for both players
	public void restartGame(){
		createDeck();
		int numPlayers = NUMOFPLAYERS;
		while(numPlayers>0){
			dealCards();
			nextPlayer();
			numPlayers--;
		}
		rotateDealer();
		resetScores();
		restart = true;
		if(curPlayer.getName().equals(getComputerName()))
			nextPlayer();
		dom = new MaintainDOM(getComputerName(), curPlayer.getName());
	}
	
	//Pre: totalScores for each player have to be reset
	//Post: totalScores for each player in players is reset to 0
	private void resetScores(){
		for(PlayerHand player: players)
			player.resetScore();
	}
	
	//Pre: need to reset variable keeping track of user knocking
	//Post: user not recorded as knocking
	@Override
	public void resetKnocking(){
		userKnocked = false;
	}
	
	//Pre: players must be created and dealers name wanted
	//Post: dealer's name returned as a String or exception is thrown if dealer not initialized
	@Override
	public String getDealerName(){
		if(dealer == null)
			throw new NullPointerException("No dealer is specified yet.");
		else
			return dealer.getName();
	}
	
	//Pre: card wanted from discard pile
	//Post: card taken from discard pile and put into curplayer's hand OR exception thrown
		//String representation of chosen card returned
	@Override
	public boolean pickCardFromDiscard(){
		try{
			Card_276 pickCard = discardPile.pickUpTopCard();
			curPlayer.add(pickCard);
			return true;
		}catch(IndexOutOfBoundsException e){
			return false;
		}
	}
	
	//Pre: card wanted from stock pile
	//Post: card taken from deck and put in curPlayer's hand and returned OR exception thrown and null returned
	@Override
	public String pickCardFromStock(){
		try{
			Card_276 pickCard = deck1.pickUpTopCard();
			curPlayer.add(pickCard);
			if(deck1.getSize()==0)
				emptyStock = true;
			return pickCard.toString();
		}catch(IndexOutOfBoundsException e){
		//too many cards in hand
			return null;
		}catch(NullPointerException npe){
			emptyStock = true;
			return null;
		}
	}
	
	//Pre: top card of the discard pile is needed
	//Post: top card is returned as a String
	public String getTopDiscard(){
		return discardPile.whatIsTopCard().toString();
	}
	
	//Pre: need to discard Card_276 with cardRep as representation from current player's hand
	//Post: true is returned if card is discarded, false is returned if card not in hand
	public String discardFromHand(String cardRep){
		try{

			int thisCardRep = Integer.parseInt(cardRep);
			Iterator<Card_276> cardIter = curPlayer.iterator();
			while(cardIter.hasNext()){
				Card_276 curCard = cardIter.next();
				if(curCard.getRepresentation() == thisCardRep){
					discardPile.discardCard(curCard);
					curPlayer.remove(curCard);
					return curCard.toString();
				}
			}
			return null;
		}catch(NumberFormatException nfe){
			throw nfe;
		}
	}
	
	//Pre: need to know if the user knocked
	//Post: returns true if user has knocked, false otherwise
	@Override
	public boolean didUserKnock(){
		return userKnocked;
	}
	
	//Pre: user has knocked
	//Post: userKnocked set to true
	@Override
	public void userKnocked(){
		userKnocked = true;
	}
	
	//Pre: amt should be added to player's totalScore
	//Post: amt is added to this player's total Score
	@Override
	public void totalScore(int amt) {
		curPlayer.addToTotalScore(amt);
	}

	//Pre: the player's current score is needed
	//Post: player's current score is returned as an int
	@Override
	public int getCurPlayerScore() {
		return curPlayer.getScore();
	}

	//Pre: the score needed to win the game is needed
	//Post: the score needed to win the game is returned as an int
	@Override
	public int getWinningScore() {
		return WINNINGSCORE;
	}

	//Pre: number of players is needed
	//Post: the number of players in this game is returned as an int
	@Override
	public int getNumPlayers() {
		return NUMOFPLAYERS;
	}

	//Pre: need to know if user wants to exit
	//Post: true is returned if user wants to exit; false otherwise
	@Override
	public boolean doesUserExit() {
		return exit;
	}

	//Pre: deck needs to be created
	//Post: deck is created and set equal to deck1 and corresponding discardPile is created with the top card from deck1
		@Override
		public void createDeck() {
			deck1 = new Deck();
			discardPile = new DiscardPile(deck1.pickUpTopCard());
			resetEmptyStock();			
		}

		//Pre: human players need to be created
		//Post NUMOFPLAYERS-1 human players created and added to players; iter is instantiated and
			//the dealer is set to this player, the person to go first is the next Player
		@Override
		public void createNewHumanPlayers(String username) {
			int count = 0;
			//create human players
			while(count<NUMOFPLAYERS-1){
				//create a single human player
				HumanPlayer thisPlayer = new HumanPlayer(username);
				players.add(thisPlayer);
				count++;
			}
			
			//set up iterator, assign dealer
			iter = players.iterator();
			nextPlayer();
			dealer = curPlayer;
			nextPlayer();
			dom = new MaintainDOM(getComputerName(), username);

		}
		
		//Pre: a computer player needs to be created for this game
		//Post: a computer player is created
		public void createComputerPlayer(){
			//create a single computer player
			ComputerPlayer newPlayer = new ComputerPlayer();
			players.add(newPlayer);
		}

		//Pre: user wants to exit the game
		//Post: exit is set to true
		@Override
		public void userWantsToExit() {
			exit = true;
		}

		//Pre: the next player is needed
		//Post: curPlayer is set to the next player unless there are no players
		@Override
		public void nextPlayer() {
			if(NUMOFPLAYERS > 0){
				if(!iter.hasNext())
					iter = players.iterator();  //start back at the beginning
				curPlayer = iter.next();
			}else 
				throw new NullPointerException("No players in the game yet");
		}

		//Pre: a representation of this existing hand is needed
		//Post: a representation of this hand is returned as a String
		@Override
		public String getCurHandRepresentation() {
			PlayerHand thisPlayer = curPlayer.clone();
			//System.out.println(thisPlayer);
			ArrayList<Card_276> removed = pickMeldOrder(thisPlayer);
			//System.out.println(removed);
			thisPlayer.sort(); //sort deadwood by representation
			for(Card_276 curCard: removed)
				thisPlayer.add(curCard);
			return thisPlayer.toString();
		}

		//Pre: the size of curPlayer's hand is needed
		//Post: the size of curPlayer's hand is returned as an int
		@Override
		public int getHandSize() {
			return curPlayer.getHandSize();
		}

		//Pre: cards need to be dealt to curPlayer
		//Post: the max number of cards is dealt from the deck and dealt to curPlayer
			//OR an exception is thrown
		@Override
		public void dealCards(){
			try{
				//gets cards from deck
				ArrayList<Card_276> dealtCards = deck1.deal(curPlayer.getMaxCards());
				boolean dealt = curPlayer.deal(dealtCards);
				if(!dealt)
					deck1.returnCardsToDeck(dealtCards);
			}catch(IllegalArgumentException iae){
				//wanted to deal <0 cards from deck
			}catch(NullPointerException npe){
				emptyStock = true;	
			}
		}
		
		//Pre: need player's name
		//Post: returns player's name as a String
		public String getName(){
			return curPlayer.getName();
		}

		//Pre: sum of cards in player's hand is needed
		//Post: sum of cards in player's hand returned as an int
		private int getHandSum(PlayerHand player) {
			int valueSum = 0;
			Iterator<Card_276> iter = player.iterator();
			while(iter.hasNext()){
				Card_276 curCard = iter.next();
				valueSum = valueSum + curCard.getValue();	
			}
			return valueSum;
		}
		
		//Pre: sum of cards in curPlayer's hand is needed
		//Post: sum of cards in curPlayer's hand is returned as an int
		@Override
		public int getHandSum(){
			return getHandSum(curPlayer);
		}
		
		
		//Pre: deadwood count from curPlayer's hand needed when Meld A then Meld B done
		//Post: deadwood count returned as an int
		public int getDeadwoodAthenB(){
			PlayerHand AthenB = (PlayerHand)curPlayer.clone();
			if(AthenB==null)
				return 0;
			takeMeldAOut(AthenB);
			takeMeldBOut(AthenB);
			return getHandSum(AthenB);
		}
		
		//Pre: deadwood count from curPlayer's hand needed when Meld B then Meld A done
		//Post: deadwood count returned as an int
		public int getDeadwoodBthenA(){
			PlayerHand BthenA = (PlayerHand)curPlayer.clone();
			if(BthenA == null)
				return 0;
			takeMeldBOut(BthenA);
			takeMeldAOut(BthenA);
			return getHandSum(BthenA);
		}
		
		//Pre: The cards that satisfy Meld B (a run in the same suit of at least three cards) need to be permanently removed from curPlayer's hand
		//Post: Meld B cards are removed from the hand and discarded
		public void takeOutMeldBCards(){
			ArrayList<Card_276> removedCards = takeMeldBOut(curPlayer);			
			for(Card_276 curCard: removedCards)
				discardPile.discardCard(curCard);
		}

		
		//Pre: The cards that satisfy Meld A (3 or 4 of the same ranks) need to be permanently removed from curPlayer's hand
		//Post: Meld A cards are removed from the hand and discarded
		public void takeOutMeldACards() {
			ArrayList<Card_276> removedCards = takeMeldAOut(curPlayer);			
			for(Card_276 curCard: removedCards)
				discardPile.discardCard(curCard);
		}
		
		
		//Pre: cards satisfying meld B need to be removed from player's hand
		//Post: meld B cards removed from hand and put into ArrayList, which is then returned
		private ArrayList<Card_276> takeMeldBOut(PlayerHand player){
			ArrayList<Card_276> removed = new ArrayList<Card_276>();

			if(player.getHandSize()>0){
			
			ArrayList<ArrayList<Card_276>> separateSuits = player.mapCardsMeldB();
			int numOfSuits = separateSuits.size();
			//System.out.println(separateSuits);
			ArrayList<Card_276> cardsInMeld = new ArrayList<Card_276>();
			
			for(int j = 0; j<numOfSuits; j++){
				ArrayList<Card_276> curArray = separateSuits.get(j);
				int curSize = curArray.size();
				cardsInMeld.clear();
				if(curSize>2){
					int meldCards = 1; //count current card as in the meld
					for(int i = 1; i < curSize; i++) {
						Card_276 card1 = curArray.get(i-1);
						Card_276 card2 = curArray.get(i);
						cardsInMeld.add(card1);
						boolean consec = (card2.getRepresentation()- card1.getRepresentation()) == 1;
						if(consec){
							meldCards++;
						}else if(meldCards<3){ //!consec
							meldCards = 1;
							cardsInMeld.clear();
						}else{ //!consec and meldCards>=3
							//copy over cards from meld to removed
							for(Card_276 card: cardsInMeld)
								removed.add(card);
							meldCards = 1;
							cardsInMeld.clear();
						}
						if(i==curSize-1 && consec && meldCards>2){ //last card is in meld
							cardsInMeld.add(card2);
							for(Card_276 card: cardsInMeld)
								removed.add(card);
							cardsInMeld.clear();
						}
						//System.out.println(removed);
						//System.out.println(cardsInMeld);

						
					}
				}
			}
		   // if(!cardsInMeld.isEmpty() &&cardsInMeld.size()>2)
		    //	for(Card_276 card: cardsInMeld)
		    	//	removed.add(card);
		    for(Card_276 card: removed)
		    	player.remove(card);
			}//close if
		    return removed;
		    
		    
		}
		
		
		//Pre: cards satisfying meld A need to be removed from hand
		//Post: meld A cards removed from hand and put into ArrayList, which is then returned
		private ArrayList<Card_276> takeMeldAOut(PlayerHand player){
			ArrayList<ArrayList<Card_276>> separateRanks = player.mapCardsMeldA();
			int size = separateRanks.size();
			//System.out.println(separateRanks);
			ArrayList<Card_276> removed = new ArrayList<Card_276>();
			for(int i = 0; i<size;i++){
				ArrayList<Card_276> curRank = separateRanks.get(i);
				int thisSize = curRank.size();
				if(thisSize>2){ //meld
					for(int j= 0; j<thisSize;j++){
						removed.add(curRank.get(j));
					}
				}
			}
			for(Card_276 curCard: removed){
				player.remove(curCard);
			}
			//System.out.println(removed);
			return removed;
		}
		
		//Pre: computer needs to discard a card
		//Post: computer discards a card from hand or exception thrown
		@Override
		public String computerMapToDiscard(){
			PlayerHand computer = curPlayer.clone();
			pickMeldOrder(computer);
			//meld cards removed from computer and put into removed
			String discardCard;
			if(computer.getHandSize()>0){
				Card_276 discard = computer.getCard(0);
				curPlayer.remove(discard);
				discardPile.discardCard(discard);
				discardCard = discard.toString();
			}
			else{ //all cards in melds
				throw new IllegalArgumentException("All cards in melds!");
			}
			return discardCard;
		}
		
		//Pre: most helpful way to do melds needed
		//Post: meld cards removed from player in most helpful way and returned
		private ArrayList<Card_276> pickMeldOrder(PlayerHand player){
			//A or B meld first
			int AthenB = getDeadwoodAthenB();
			int BthenA = getDeadwoodBthenA();
			ArrayList<Card_276> removed = new ArrayList<Card_276>();
			if(AthenB <= BthenA){ //want AthenB
				removed = takeMeldAOut(player);
				ArrayList<Card_276> MeldB = takeMeldBOut(player);
				if(!MeldB.isEmpty())
					removed.addAll(MeldB);
			}else{ //want BthenA
				
				removed = takeMeldBOut(player);
				ArrayList<Card_276> MeldA = takeMeldAOut(player);
				if(!MeldA.isEmpty())
					removed.addAll(MeldA);
			}
			
			return removed;
		}
		
		//Pre: player must exist
		//Post: if all cards in melds, return true. otherwise false
		public boolean allCardsRemoved(String player){
			while(!curPlayer.getName().equals(player)){
				nextPlayer();
			}
			boolean answer = false;
			int handSize = curPlayer.getHandSize();
			ArrayList<Card_276> removedCards = pickMeldOrder(curPlayer);
			if(removedCards.size()==handSize){
				answer = true;
			}
			for(Card_276 card: removedCards){
				curPlayer.add(card);
			}
			return answer;
		}
		
		//Pre: name of computer player is needed
		//Post: name of computer player is returned as a String
		public String getComputerName(){
			for(PlayerHand player: players){
				if(player.getType().equals("ComputerPlayer"))
					return player.getName();
			}
			throw new NullPointerException("No computer player exists");
		}
		
		//Pre: data needs to be stored for this hand for both players
		//Post: totalscore, deadwood count stored for each player for this hand
		public void storeData(String winner, int winnerDeadWood, int otherDeadWood){
			if(winner.equals(curPlayer.getName())){
				int winnerTotalScore = getCurPlayerScore();
				String winID = curPlayer.getID();
				nextPlayer();
				String loseID = curPlayer.getID();
				dom.updateDOM(winID, winnerDeadWood, winnerTotalScore, loseID, otherDeadWood, getCurPlayerScore());						
				nextPlayer();
			}else{
				int otherTotalScore = getCurPlayerScore();
				String loseID = curPlayer.getID();
				nextPlayer();
				String winID = curPlayer.getID();
				dom.updateDOM(winID, winnerDeadWood, getCurPlayerScore(), loseID, otherDeadWood, otherTotalScore);
				nextPlayer();
			}
		}

		
}
