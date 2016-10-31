import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class GUI {
	
	/**
	 * 
	 * @param listCandidate
	 * @return candidate with the specified ID.
	 */
	public static int getNextCandidateID(ArrayList<Candidate> listCandidate){
		int nextId = 0;
		for (Candidate candidate : listCandidate){
			if(candidate.getCandidateID() > nextId){
				nextId = candidate.getCandidateID();
			}
		}
	}
	
	/**
	 * Saves the data to a .csv file named "listCandidate".
	 * @param listCandidate
	 */
	public static void saveCandidateData(ArrayList<Candidate> listCandidate){
		try{
			File file = new File("listCandidate.csv");
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			for (Candidate candidate: listCandidate){
				writer.write(candidate.getCandidateID() + "," + candidate.getFirstName() + "," + candidate.getLastName() + "," + candidate.getParyAffiliation());
			}
			writer.flush();
			writer.close();
		}
		catch(IOException e){
			System.out.println("Something went wrong");
		}
	}
	
	/**
	 * Reads a list of candidate from a .csv file and returns it as an arraylist.
	 * @return ArrayList<Candidate> listCandidate
	 */
	public static ArrayList<Candidate> readCandidate(){
		try{
			ArrayList<Candidate> listCandidate = new ArrayList();
			FileReader myFile = new FileReader("listCandidate.csv");
			String myLine;
			while(myScanner.hasNextLine()){
				myLine = myScanner.nextLine();
				String myParts[] = myLine.split(",");
				listCandidate.add(new Candidate(Integer.parseInt(myParts[0]), myParts[1], myParts[2], myParts[3]));
			}
			myFile.close();
			return listCandidate;
		}
		catch(Exception e){
			return new ArrayList();
		}
	}
	
	/**
	 * Main method to get the GUI working with Swings.
	 * @param args
	 */
	public static void main(String[] args){
		JFrame myFrame = new JFrame("Electronic Voting System");
		Container myPanel = myFrame.getContentPane();
		myFrame.setSize(1280, 720);
		ArrayList<Candidate> listCandidate = readCandidate();
		myPanel.add(new Jlabel("Welcome to the electronic voting system"));
		
		JMenuBar myMenuBar = new JMenuBar();
	}
	

}
