import java.util.Scanner;

//Allows the view to communicate with the user by displaying and getting input
public class UserViewCommunication {

	//Scanner used to communicate with the user
	private Scanner input;
	
	//Pre: View needs to communicate with the user by displaying and getting data from the user
	//Post: An object is created that allows the view to communicate with the user via a Scanner object
	public UserViewCommunication(){
		input = new Scanner(System.in);
	}
	
	//Pre: msg needs to be displayed to the user
	//Post: msg is displayed to the user
	public void display(String msg){
		System.out.println(msg);
	}
	
	//Pre: view needs to get input from the user
	//Post: input as a string from the user is returned
	public String getInput(){
		String answer = input.nextLine();
		return answer;
	}
}
