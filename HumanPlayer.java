// Jessica Rankins CSC 276 Gin Rummy Project


public class HumanPlayer extends PlayerHand{

	//Pre: a human Player is needed with name userName
	//Post: a human player is created as a PlayerHand with name userName
	public HumanPlayer(String userName){
		super();
		name = userName;
		THISID = "2";
	}

	//Pre: the type of player is needed for this player
	//Post: the type of player (humanPlayer) is returned as a String
	public String getType(){
		return "HumanPlayer";
	}
	

}
