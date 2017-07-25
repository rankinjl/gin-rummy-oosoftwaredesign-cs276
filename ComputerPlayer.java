// Jessica Rankins CSC 276 Gin Rummy Project


public class ComputerPlayer extends PlayerHand {

	
	//Pre: a computerPlayer needs to be created
	//Post: a computerPlayer is created as a PlayerHand with name "Jessica"
	public ComputerPlayer(){
		super();
		name = "Jessica";
		THISID = "1";
	}

	//Pre: the Type is needed for this player
	//Post: the type of player (computerPlayer) is returned as a String
	public String getType(){
		return "ComputerPlayer";
	}
	

}
