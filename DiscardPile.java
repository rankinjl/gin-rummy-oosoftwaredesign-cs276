import java.util.ArrayList;


public class DiscardPile {

	private ArrayList<Card_276> discardPile; //list of cards representing the pile of discarded cards
	private int numOfCards;	//number of cards in the discardPile
	
	//Pre: topCard must exist as a Card_276
	//Post: discardPile is created and topCard is added to it, numOfCards set to 0
	public DiscardPile(Card_276 topCard){
		discardPile = new ArrayList<Card_276>();
		numOfCards = 0;
		discardCard(topCard);
	}
	
	//Pre: thisCard must exist
	//Post: thisCard is added to the top of the discardPile and numOfCards is updated
	public void discardCard(Card_276 thisCard){
		numOfCards = numOfCards + 1;
		discardPile.add(thisCard);
	}
	
	//Pre: want to know what the top card in the discardPile is
	//Post: the top card in the discardPile is returned as a Card_276
	public Card_276 whatIsTopCard(){
		return discardPile.get(numOfCards-1);
	}
	
	//Pre: want to pick up the top card in the discardPile
	//Post: the top card in discardPile is removed and returned and numOfCards is updated
		//OR an exception is thrown if there are no cards
	public Card_276 pickUpTopCard(){
		if(numOfCards>0){
			numOfCards = numOfCards - 1;
			return discardPile.remove(numOfCards);
		}else
			throw new IndexOutOfBoundsException("There are no cards in the discard pile to pick up!");
	}
}
