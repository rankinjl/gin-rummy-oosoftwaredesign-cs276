import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.*;

public class MaintainDOM {

	private Document dom;
	private final String OUTPUT_FILENAME = "CSC276XMLOutput.xml";
	private int curRound;
	
	//Pre: player1 and player2 must exist
	//Post: DOM created and saved for this game of Gin Rummy, players added
	public MaintainDOM(String player1, String player2){
		dom = createDOM();
		addMainRoots(player1, player2);
		saveDOMtoXML();
		curRound = 0;
	}
	
	//Pre: All parameters must exist
	//Post: DOM updated for this round including deadwood count and totalScore for each player
	public void updateDOM(String winnerID, int winnerDeadWood, int winnerTotal, String loserID, int loserDeadWood, int loserTotal){
		dom = getDOM(OUTPUT_FILENAME);
		if(dom != null){
			curRound++;
	
			Element gameRoot = dom.getDocumentElement();
			Element player = (Element) gameRoot.getFirstChild();
			Element child = (Element)player.getFirstChild();
			if(child.getFirstChild().getNodeValue().equals(winnerID)){
				addRound(player, winnerDeadWood, winnerTotal);
				player = (Element) player.getNextSibling();
				addRound(player, loserDeadWood, loserTotal);
			}else{
				addRound(player, loserDeadWood, loserTotal);
				player = (Element) player.getNextSibling();
				addRound(player, winnerDeadWood, winnerTotal);
			}
		}
		saveDOMtoXML();
	}
	
	//Pre: All parameters must exist
	//Post: deadWood count and totalScore for this round added to this player in DOM
	private void addRound(Element player, int deadWoodCount, int total){
		Element round = dom.createElement("Round");
		Element roundNum = dom.createElement("RoundNumber");
		roundNum.appendChild(dom.createTextNode("" + curRound));
		round.appendChild(roundNum);
		
		Element dwood = dom.createElement("DeadWood");
		dwood.appendChild(dom.createTextNode("" + deadWoodCount));
		round.appendChild(dwood);
		
		Element totalScore = dom.createElement("TotalScore");
		totalScore.appendChild(dom.createTextNode(""+ total));
		round.appendChild(totalScore);
		
		player.appendChild(round);
	}
	
	//Pre: DOM needs to be created
	//Post: new DOM object created and returned
	private Document createDOM(){
		Document tempDom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			tempDom = db.newDocument();
		}catch (ParserConfigurationException ex){
			System.out.println(ex);
			tempDom = null;
		}catch (Exception ex){
			System.out.println(ex);
			tempDom = null;
		}
		return tempDom;
	}
	
	//Pre: player1 and player2 must exist
	//Post: main root "Game" and Players with name and ID created and added to DOM
	private void addMainRoots(String player1, String player2){
		Element root = dom.createElement("Game");
		dom.appendChild(root);
		
		addPlayer(root, player1, "1");
		addPlayer(root, player2, "2");
		
	}
	
	//Pre: root, playerName, and ID must exist
	//Post: A player is added as a child of this root with name PlayerName and ID ID
	private void addPlayer(Element root, String playerName, String ID){
		Element player = dom.createElement("PlayerHand");
		Element id = dom.createElement("ID");
		id.appendChild(dom.createTextNode(ID));
		player.appendChild(id);
		
		Element name = dom.createElement("Name");
		name.appendChild(dom.createTextNode(playerName));
		player.appendChild(name);
		
		root.appendChild(player);
	}
	
	//Pre:  filename must exist
	//Post: XML DOM object created for filename and returned. Any exceptions causes the XML DOM to be null.
	private Document getDOM(String filename){
		Document tempDom;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			tempDom = db.parse(filename);
		}catch (ParserConfigurationException ex){
			System.out.println(ex);
			tempDom = null;
		}catch (IOException ex){
			System.out.println(ex);
			tempDom = null;
		}catch (SAXException ex){
			System.out.println(ex);
			tempDom = null;
		}catch (Exception ex){
			System.out.println(ex);
			tempDom = null;
		}
		return tempDom;
	}
	
	//pre:  DOM has been updated.
	//post: Save DOM to a new XML file name.
	private void saveDOMtoXML(){
		try{
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(dom);
			StreamResult result = new StreamResult(new File(OUTPUT_FILENAME));
			transformer.transform(source, result);
		}catch (TransformerFactoryConfigurationError ex){
		}catch (TransformerConfigurationException ex){
		}catch (TransformerException ex){
		}
	}
	
}
