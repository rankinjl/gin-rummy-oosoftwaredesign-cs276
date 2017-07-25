//Jessica Rankins CSC 276 Gin Rummy Project

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
public class PlayerHand{


	private final int MAXCARDS = 10; //the maximum number of cards the player can have
	
	private int handSize; //the size of the player's current hand of cards
	private ArrayList<Card_276> handCards; //the actual cards in the player's current hand
	private int totalScore; //the player's total points scored in the game 
	protected String name; //name of this player
	protected String THISID; //ID for this player
	
	//Pre: a hand of cards needs to be created
	//Post: an empty hand of MAXCARDS is created and the size of the hand and total score are set to 0
	public PlayerHand(){
		handCards = new ArrayList<Card_276>(MAXCARDS);
		handSize = 0;
		totalScore = 0;
		
	}
	
	//Pre: the type of the playerHand is needed 
	//Post: the type of this PlayerHand is returned as a String
	public String getType(){
		return "PlayerHand";
	}
	
	//Pre: the name of this playerHand is needed
	//Post: the name of this PlayerHand is returned as a String
	public String getName(){
		return name;
	}
	
	//Pre: the max number of cards allowed in the hand is needed
	//Post: the max number of cards allowed in a hand is returned as an int
	public int getMaxCards(){
		return MAXCARDS;
	}
	
	//Pre: num needs to be added to this hand's total score	
	//Post: the int num is added to this hand's total score
	public void addToTotalScore(int num){
		totalScore = totalScore + num;
	}
	
	//Pre: a way to iterate the cards in this hand is needed
	//Post: an iterator for this list of cards is returned
	public Iterator<Card_276> iterator(){
		return handCards.iterator();
	}
	
	//Pre: the cards of this hand need to be separated by suits
	//Post: a list of lists is returned with each sublist a list of cards of the same suit in this hand
	public ArrayList<ArrayList<Card_276>> mapCardsMeldB(){
		sort();
		ArrayList<ArrayList<Card_276>> separated = new ArrayList<ArrayList<Card_276>>();
		String curSuit = handCards.get(0).getSuit();
		Iterator<Card_276> iter = iterator();
		Card_276 switchCard = null;
		for(int i = 0; i<4;i++){
			boolean sameSuit = true;
			ArrayList<Card_276> thisSuit = new ArrayList<Card_276>();
			if(switchCard!=null){
				thisSuit.add(switchCard);
				switchCard = null;
			}
			
			while(iter.hasNext() && sameSuit){
				Card_276 nextCard = iter.next();
				if(curSuit.equals(nextCard.getSuit()))
					thisSuit.add(nextCard);
				else{
					curSuit = nextCard.getSuit();
					sameSuit = false;
					switchCard = nextCard;
				}
			}
			separated.add(thisSuit);	
			
		}
		
		return separated;
	}
	
	//Pre: the cards of this hand need to be separated by ranks
	//Post: a list of lists is returned with each sublist a list of cards of the same rank in this hand
	public ArrayList<ArrayList<Card_276>> mapCardsMeldA(){
		ArrayList<ArrayList<Card_276>> separated = new ArrayList<ArrayList<Card_276>>();
		for(int i = 0; i<13/*ranks*/;i++)
			separated.add(new ArrayList<Card_276>());
		Iterator<Card_276> iter = iterator();
		
		while(iter.hasNext()){
			Card_276 curCard = iter.next();
			int curRank = curCard.getRepresentation()%13;
			separated.get(curRank).add(curCard);
		}
		
		return separated;
	}
	
	
	//Pre: totalScore for this player needs to be reset
	//Post: totalScore for this player is reset to 0
	public void resetScore(){
		totalScore = 0;
	}
	
	//Pre: player's total score is wanted
	//Post: player's total score returned as an int
	public int getScore(){
		return totalScore;
	}
	
	//Pre: A description of this hand is needed
	//Post: A String describing the hand is returned that includes the face value, 
		//representation, and suit of each card in the hand
	public String toString(){
		String handRep = "";
		for(Card_276 card: handCards){
			handRep = handRep + card + "\n";
		}
		return handRep;
	}
	
	//Pre: handCards need to be sorted
	//Post: handCards is sorted 
	public void sort(){
		Object[] curHand = handCards.toArray();
		Arrays.sort(curHand);
		handCards.clear();
		for(Object card: curHand)
			handCards.add((Card_276)card);
	}

	//Pre: the size of this hand is needed
	//Post: the size of this hand is returned as an int
	public int getHandSize() {
		return handSize;
	}

	//Pre: the card at index count in this hand is needed
	//Post: the card at count in this hand is returned as a Card_276
	public Card_276 getCard(int count) {
		if(count>=0 && count<handSize)
			return handCards.get(count);
		else
			return null;
	}

	//Pre: curCard needs to be removed from this hand
	//Post: curCard is removed from this hand if it is there,
		//if it is not there, an exception is thrown
	public void remove(Card_276 curCard) {
		try{
			handCards.remove(curCard);
			handSize--;
		}catch(IndexOutOfBoundsException e){
			throw new IndexOutOfBoundsException("There is no card "+ curCard + " in this hand.");
		}
		
	}

	//Pre: dealtCards exists and need to be dealt into this hand
	//Post: dealtCards are put into this empty hand, the size of the hand is
		//updated and true is returned
		//OR there are too many cards and they are not dealt and false is returned
	public boolean deal(ArrayList<Card_276> dealtCards) {
		int len = dealtCards.size();
		if(len<=MAXCARDS){		
			//make sure hand is empty before dealing new hand of cards
			handCards.clear();
			//puts cards in hand
			handCards.addAll(dealtCards);
			//updates how many cards in the hand
			handSize = len;
			sort();
			return true;
		}else
			return false;
	}

	
	//Pre: thisCard exists and needs to be added to the hand
	//Post: this Card is added to the hand and handSize is updated or an exception is thrown 
		//because there are too many cards in the hand already
	public void add(Card_276 thisCard){
		if(handSize<MAXCARDS+1){
			handCards.add(thisCard);
			handSize++;
		}else
			throw new IndexOutOfBoundsException("There are too many cards in your hand!");
	}
	
	//Pre: a clone of this playerHand is needed
	//Post: a new Hand with all the cards of this playerHand is returned
	public PlayerHand clone(){
		PlayerHand newHand=new PlayerHand();
		for(Card_276 card: handCards)
			newHand.add(card);
		return newHand;
	}
	
	//Pre: this PlayerHand's ID needed
	//Post: this PlayerHand's ID returned
	public String getID(){
		return THISID;
	}
	
}
