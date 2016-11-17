import java.util.*;
import java.io.*;
import java.time.LocalTime;

public class CRUD {
	
	public static void main(String args[]){	
		String command;		
		do{
			Scanner in = new Scanner(System.in);
			System.out.println("ELECTRONIC VOTING SYSTEM MENU: Press A)dmin, V)oter ");
			
			command = in.nextLine();
			if (command.equalsIgnoreCase("A")){
				adminMenu();
			}
			else if(command.equalsIgnoreCase("V")){
				voterMenu();
			}
			else{
				System.out.println("Please enter correct input.");
			}
		}while (!command.equalsIgnoreCase("X"));
		
	}
	
	public static void adminMenu(){
		String command;
		Scanner in = new Scanner(System.in);
		ArrayList<Voter> listVoters = readVoters();
		ArrayList<Ballot> listBallots = readBallots();
		System.out.println("ADMIN MENU: R)egister voter, C)heck registration, G)et tallys, P)rint results, N)ew ballot");
		command = in.nextLine();
		if (command.equalsIgnoreCase("R")){
			Voter tempVoter = new Voter();			
			System.out.println("First name: ");
			tempVoter.setFirstName(in.nextLine());
			System.out.println("Last name: ");
			tempVoter.setLastName(in.nextLine());
			System.out.println("Password(Social Security, no hyphens): ");
			tempVoter.setPassword(in.nextLine());
			Ballot tempBallot = new Ballot();
			System.out.println("New ballot has been made.");
			tempBallot.setOwner(tempVoter);
			tempBallot.setStartTime(LocalTime.now());
			System.out.println("Start time has been set.");			
			tempVoter.setIdNumber(getNextVoterId(listVoters));
			tempBallot.setBallotID(getNextBallotId(listBallots));
		}				
	}
	
	private static void voterMenu() {
		String command;
		Scanner in = new Scanner(System.in);
		ArrayList<Candidate> listCandidates = readCandidates();
		System.out.println("VOTER MENU: V)ote");
		command = in.nextLine();
		if (command.equalsIgnoreCase("V")){
				for(int i = 0; i < listCandidates.size(); i++){		
				System.out.print(i + ": " + listCandidates.get(i).getFirstName());
				System.out.println(" " + listCandidates.get(i).getLastName());
				}
				getCandidate(listCandidates, Integer.parseInt(in.nextLine())).addVote();
		}
		
		
		
		
	}
	
    public static int getNextVoterId(ArrayList<Voter> list) {
    int nextId = 0;
    for(Voter tempVoter : list) {
        if(tempVoter.getIdNumber() > nextId){
            nextId = tempVoter.getIdNumber();
        }
    }   
    return nextId + 1;
    }
    
    public static int getNextBallotId(ArrayList<Ballot> list) {
    int nextId = 0;
    for(Ballot tempBallot : list) {
        if(tempBallot.getBallotID() > nextId){
            nextId = tempBallot.getBallotID();
        }
    }   
    return nextId + 1;
    }
    
    public static ArrayList<Voter> readVoters(){
        try{
            ArrayList<Voter> listPlayers = new ArrayList();
            FileReader myFile = new FileReader("listVoters.csv");
            Scanner myScanner = new Scanner(myFile);
            String myLine;
            while(myScanner.hasNextLine()){
                myLine = myScanner.nextLine();
                String myParts[] = myLine.split(",");
                listPlayers.add(new Voter()); 
            }
            myFile.close();
            return listPlayers;
        }
        catch(Exception e){
            return new ArrayList();
        }
    }
    
    public static ArrayList<Ballot> readBallots(){
        try{
            ArrayList<Ballot> listPlayers = new ArrayList();
            FileReader myFile = new FileReader("listBallots.csv");
            Scanner myScanner = new Scanner(myFile);
            String myLine;
            while(myScanner.hasNextLine()){
                myLine = myScanner.nextLine();
                String myParts[] = myLine.split(",");
                listPlayers.add(new Ballot()); 
            }
            myFile.close();
            return listPlayers;
        }
        catch(Exception e){
            return new ArrayList();
        }
    }
	
    public static ArrayList<Candidate> readCandidates(){
        try{
            ArrayList<Candidate> listPlayers = new ArrayList();
            FileReader myFile = new FileReader("listCandidates.csv");
            Scanner myScanner = new Scanner(myFile);
            String myLine;
            while(myScanner.hasNextLine()){
                myLine = myScanner.nextLine();
                String myParts[] = myLine.split(",");
                listPlayers.add(new Candidate(Integer.parseInt(myParts[0]), myParts[1], myParts[2], myParts[6])); 
            }
            myFile.close();
            return listPlayers;
        }
        catch(Exception e){
            return new ArrayList();
        }
    }
	
    public static Candidate getCandidate(ArrayList<Candidate> list, int id){
        for (Candidate candidate : list){
            if(candidate.getCandidateID() == id){
                return candidate;
            }
        }
        System.out.println("No tennis player with this ID.");
        return new Candidate();
    }
		
}


