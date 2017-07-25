//import java.util.ArrayList;

/* Jessica Rankins CSC 276 Gin Rummy Project
 * Increment 9
 * Hours:
 * 
 * Designing: 1 hours
 * Coding: 2 hours
 * Testing: 1 hours
 */


public class PlayGinRummy {
		
	//Pre:  game of gin rummy wanted
	//Post: games of gin rummy are played until player wants to exit
	public static void main(String[] args) {
		RunGame currentGame = new RunGame();
		currentGame.runGinRummy();
		//Test code:
		/*ModelCommunication model = new ModelCommunication();
		
		PlayerHand player = new PlayerHand();
		ArrayList<Card_276> cards = new ArrayList<Card_276>();
		//for(int i = 20; i<25;i++)
		//	cards.add(new Card_276(i));
		cards.add(new Card_276(2));
		cards.add(new Card_276(8));
		cards.add(new Card_276(10));
		cards.add(new Card_276(24));
		cards.add(new Card_276(27));
		cards.add(new Card_276(45));
		cards.add(new Card_276(46));
		cards.add(new Card_276(47));
		cards.add(new Card_276(30));
		cards.add(new Card_276(31));

		player.deal(cards);
		System.out.println(player);
		//PlayerHand player2 = player.clone();	
		//System.out.println(model.getDeadwoodAthenB(player));
		//System.out.println(model.getDeadwoodBthenA(player2));
		//System.out.println(model.getCurHandRepresentation(player));

		ArrayList<Card_276> result2 = model.takeMeldBOut(player);
		System.out.println(result2);
		System.out.println(player);
				
		ArrayList<Card_276> result = model.takeMeldAOut(player);
		System.out.println(result);
		System.out.println(player);
		 */
	}	
}
