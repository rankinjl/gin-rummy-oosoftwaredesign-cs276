// Jessica Rankins CSC 276 Gin Rummy Project


import java.util.ArrayList;
import java.util.Collections;
public class Deck {

	private final int DECKSIZE = 52; //the size of the deck of cards
	
	private ArrayList<Card_276> stockPile; //list of cards representing the card deck
	private int curSize; //the current size of the deck of cards
	
	//Pre: a deck of cards needs to be created
	//Post: a deck of Card_276s is created, filled, and shuffled 
	public Deck(){
		stockPile = new ArrayList<Card_276>();
		//assign each index in deck to a card representation
		fillDeck();
		//shuffle the deck
		shuffle();
	}
	
	//Pre: deck must be filled with non-repeating Card_276s
	//Post: deck is filled with Card_276s with cardRepresentations 0 through DECKSIZE-1 
		//and the current size of the deck is updated
	private void fillDeck(){
		for(int i = 0; i<DECKSIZE; i++){
			Card_276 curCard = new Card_276(i);
			stockPile.add(curCard);
			curSize = DECKSIZE;
		}

	}
		
	//Pre: current size of the deck needed
	//Post: size of deck returned as an int
	public int getSize(){
		return curSize;
	}
	
	
	//Pre: a certain number of cards (numCards) from the deck need to be dealt
	//Post: specified amount of cards are returned to be dealt and
		//number of cards in the deck remaining is updated OR
		//exception thrown because there are not enough cards in the deck
	public ArrayList<Card_276> deal(int numCards){
		if(curSize < numCards)
			throw new NullPointerException("Deck does not have enough cards!");
		if(numCards < 0)
			throw new IllegalArgumentException("Number of Cards to be dealt must be positive.");
		ArrayList<Card_276> dealingCards = new ArrayList<Card_276>();
		while(numCards > 0){
			dealingCards.add(stockPile.remove(curSize-1));
			numCards--;
			curSize--;
		}
		return dealingCards;
	}
	
	
	//Pre: this deck must be shuffled
	//Post: the deck is shuffled
	private void shuffle(){
		Collections.shuffle(stockPile);
		
	}

	//Pre: cards must exist
	//Post: cards are added to the deck
	public void returnCardsToDeck(ArrayList<Card_276> cards) {
		stockPile.addAll(cards);
	}
	
	//Pre: card on top of the stock pile is needed
	//Post: Card_276 from the top of the stock pile is removed and returned or an exception is trhown
	public Card_276 pickUpTopCard(){
		if(curSize>0){
			curSize--;
			return stockPile.remove(curSize);
		}
		else
			throw new NullPointerException("There are no cards in the stock pile to pick up!");
	}
	

}
