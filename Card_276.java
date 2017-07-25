// Jessica Rankins CSC 276 Gin Rummy Project


/* cardRepresentation:
 * 
 * 0-12: Clubs - Ace to King
 * 13-25: Spades - Ace to King
 * 26-38: Diamonds - Ace to King
 * 39-51: Hearts - Ace to King
 */

public class Card_276 implements Comparable<Card_276>{

	//constant attributes 
	private final String[] SUITS = {"Clubs", "Spades", "Diamonds", "Hearts"};
	private final String[] RANKS = {"Ace", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	private final int FACECARDVALUE = 10; //point value for K,Q,J,10
	
	private int cardRepresentation; // number 0 to 51 that represents card
	
	//Pre: a card must be created with the representation given by rep
	//Post: if rep is in the correct range, a new card is created with that rep. else an exception is thrown
	public Card_276(int cardRep){
		if(cardRep < 0 || cardRep > 51)
			throw new IllegalArgumentException("Incorrect Card Value");
		else
			cardRepresentation = cardRep;
	}
	
	//Pre: the point value of this card is wanted
	//Post: the point value of the given card for gin rummy is returned 
	// Ace - 1, Two - 2, Three - 3,...,Ten Jack Queen and King - 10
	public int getValue(){
		int index = cardRepresentation % 13;
		int cardValue;
		//if card is a 10,J,Q,K
		if(index > 8 && index <13)
			cardValue = FACECARDVALUE;
		else
			cardValue = index + 1;
		return cardValue;
	}
	
	//Pre: A description of this card is needed
	//Post: A String describing the card is returned that includes the rank, face value, representation, and suit
	public String toString(){
		return cardRepresentation + ": " + getRank() + " of " + getSuit() + " (value =  " + getValue() + ")";	
	}
	
	//Pre: the representation for this card is needed
	//Post: the representation for this card is returned as an int
	public int getRepresentation(){
		return cardRepresentation;
	}
	
	//Pre: The suit of this card is needed
	//Post: Returns the suit of the card as a string from SUITS[]
	public String getSuit(){
		int index = cardRepresentation / 13;
		return SUITS[index];
	}
	
	//Pre: The rank of this card is needed
	//Post: Returns the rank of this card as a string from RANKS[]	 
	public String getRank(){
		int index = cardRepresentation % 13;
		return RANKS[index];
	}

	//Pre: other exists and needs to be compared to this card with respect to the representations
	//Post: if this card < other return -1, else if this card > other return 1, else return 0 (equal)
	public int compareTo(Card_276 other){
		if(cardRepresentation < other.getRepresentation())
			return -1;
		else if (cardRepresentation > other.getRepresentation())
			return 1;
		else 
			return 0;
		
		
	}

		
}