import java.util.*;
import java.io.*;
import java.time.LocalTime;

public class CRUD {
	
	public static void main(String args[]){	
		String command;		
		do{
			Scanner in = new Scanner(System.in);
			System.out.println("ELECTRONIC VOTING SYSTEM MENU: Press A)dmin, V)oter, X to exit.");
			
			command = in.nextLine();
			if (command.equalsIgnoreCase("A")){
				adminMenu();
			}
			else if(command.equalsIgnoreCase("V")){
				voterMenu();
			}

		}while (!command.equalsIgnoreCase("X"));
		
	}
	
	public static void adminMenu(){
		String command;
		Scanner in = new Scanner(System.in);
		ArrayList<Voter> listVoters = readVoters();
		ArrayList<Ballot> listBallots = readBallots();
		ArrayList<Candidate> listCandidates = readCandidates();
		do {
			System.out.println("ADMIN MENU: R)egister voter, C)heck registration, G)et tallys, P)rint results, N)ew ballot, A)dd Candidate, X to exit.");
			command = in.nextLine();
			if (command.equalsIgnoreCase("R")) {
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
				listVoters.add(tempVoter);
				listBallots.add(tempBallot);
			} else if (command.equalsIgnoreCase("C")) {
				System.out.println("Which voter would you like to check?");
				Voter voter = getVoter(listVoters, Integer.parseInt(in.nextLine()));
				System.out.println(voter.getFirstName() + " " + voter.getLastName() + " can register: ");
				System.out.println(voter.getRegistrationStatus());
			} else if (command.equalsIgnoreCase("G")) {
				for (int i = 0; i < listCandidates.size(); i++) {
					System.out.println(listCandidates.get(i).getFirstName() + " " + listCandidates.get(i).getLastName()
							+ ": " + listCandidates.get(i).getVoteCount());
				}
			} else if (command.equalsIgnoreCase("P")) {
				for (int i = 0; i < listCandidates.size(); i++) {
					System.out.println(listCandidates.get(i).getFirstName() + " " + listCandidates.get(i).getLastName()
							+ ": " + listCandidates.get(i).getVoteCount());
				}
			} else if (command.equalsIgnoreCase("N")) {
				Ballot tempBallot = new Ballot();
				System.out.println("Who is the owner of this ballot?: ");
				for (int i = 0; i < listVoters.size(); i++) {
					System.out.println(
							i + ": " + listVoters.get(i).getFirstName() + " " + listVoters.get(i).getLastName());
				}
				Voter voter = getVoter(listVoters, Integer.parseInt(in.nextLine()));
				tempBallot.setOwner(voter);
				System.out.println("New ballot has been made.");
				tempBallot.setStartTime(LocalTime.now());
				System.out.println("Start time has been set.");
				tempBallot.setBallotID(getNextBallotId(listBallots));
				listBallots.add(tempBallot);
			} else if (command.equalsIgnoreCase("A")){
				Candidate tempCandidate = new Candidate();
				System.out.println("Enter first name of candidate: ");
				tempCandidate.setFirstName(in.nextLine());
				System.out.println("Enter last name of candidate: ");
				tempCandidate.setLastName(in.nextLine());
				System.out.println("Enter party affiliation: ");
				tempCandidate.setPartyAffiliation(in.next());
				tempCandidate.setCandidateID(getNextCandidateId(listCandidates));
				listCandidates.add(tempCandidate);
			}
			
		} while (!command.equalsIgnoreCase("X"));
		saveVoters(listVoters);
		saveBallots(listBallots);	
		saveCandidates(listCandidates);
	}
	
	private static void voterMenu() {
		String command;
		Scanner in = new Scanner(System.in);
		ArrayList<Voter> listVoters = readVoters();
		ArrayList<Ballot> listBallots = readBallots();
		ArrayList<Candidate> listCandidates = readCandidates();
		do {
			System.out.println("VOTER MENU: V)ote, X to exit.");
			command = in.nextLine();
			if (command.equalsIgnoreCase("V")) {
				for (int i = 0; i < listCandidates.size(); i++) {
					System.out.print(i + ": " + listCandidates.get(i).getFirstName() + " "
							+ listCandidates.get(i).getLastName());
				}
				getCandidate(listCandidates, Integer.parseInt(in.nextLine())).addVote();
			} 
		} while (!command.equalsIgnoreCase("X"));
		saveVoters(listVoters);
		saveBallots(listBallots);	
		saveCandidates(listCandidates);
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
    
    public static int getNextCandidateId(ArrayList<Candidate> list) {
    int nextId = 0;
    for(Candidate tempCandidate : list) {
        if(tempCandidate.getCandidateID() > nextId){
            nextId = tempCandidate.getCandidateID();
        }
    }   
    return nextId + 1;
    }
    
    public static Candidate getCandidate(ArrayList<Candidate> list, int id){
	    for (Candidate candidate : list){
	        if(candidate.getCandidateID() == id){
	            return candidate;
	        }
	    }
	    System.out.println("No candidate with this ID.");
	    return new Candidate();
	}

	public static Voter getVoter(ArrayList<Voter> list, int id){
	    for (Voter voter : list){
	        if(voter.getIdNumber() == id){
	            return voter;
	        }
	    }
	    System.out.println("No voter with this ID.");
	    return new Voter();
	}

	public static ArrayList<Voter> readVoters(){
        try{
            ArrayList<Voter> listVoters = new ArrayList<Voter>();
            FileReader myFile = new FileReader("listVoters.csv");
            Scanner myScanner = new Scanner(myFile);
            String myLine;
            while(myScanner.hasNextLine()){
                myLine = myScanner.nextLine();
                String myParts[] = myLine.split(",");
                listVoters.add(new Voter(Integer.parseInt(myParts[0]), myParts[1], myParts[2], myParts[3], myParts[4])); 
            }
            myFile.close();
            return listVoters;
        }
        catch(Exception e){
        	System.out.println("Error on reading Voter."); 
        	return new ArrayList<Voter>();
        }
    }
    
    public static ArrayList<Ballot> readBallots(){
        try{
            ArrayList<Ballot> listBallots = new ArrayList<Ballot>();
            FileReader myFile = new FileReader("listBallots.csv");
            Scanner myScanner = new Scanner(myFile);
            String myLine;
            while(myScanner.hasNextLine()){
                myLine = myScanner.nextLine();               
                String myParts[] = myLine.split(","); 
                listBallots.add(new Ballot(Integer.parseInt(myParts[0].toString()), myParts[1], myParts[2]));
            }
            myFile.close();
            return listBallots;
        }
        catch(Exception e){
        	System.out.println("Error on reading ballot."); 
        	return new ArrayList<Ballot>();
        }
    }
	
    public static ArrayList<Candidate> readCandidates(){
        try{
            ArrayList<Candidate> listBallots = new ArrayList<Candidate>();
            FileReader myFile = new FileReader("listCandidates.csv");
            Scanner myScanner = new Scanner(myFile);
            String myLine;
            while(myScanner.hasNextLine()){
                myLine = myScanner.nextLine();
                String myParts[] = myLine.split(",");
                listBallots.add(new Candidate(Integer.parseInt(myParts[0]), myParts[1], myParts[2], myParts[3], Integer.parseInt(myParts[4]))); 
            }
            myFile.close();
            return listBallots;
        }
        catch(Exception e){
        	System.out.println("readCandidatesError");
            return new ArrayList<Candidate>();
        }
    }
	
    public static void saveVoters(ArrayList<Voter> list){
	    try {            
	        File file = new File("listVoters.csv");
	        file.createNewFile();
	        FileWriter writer = new FileWriter(file);
	        for(Voter tVoter: list){
	            writer.write(tVoter.getIdNumber() + "," + tVoter.getFirstName()  + "," + tVoter.getLastName() + "," + tVoter.getRegistrationStatus() + "," + tVoter.getPassword() + "\n");
	        }
	        writer.flush();
	        writer.close();
	    }
	    catch(IOException e) {
	        System.out.println("saveVotersError!");
	    }
	}

	public static void saveBallots(ArrayList<Ballot> listBallots){
	    try {            
	        File file = new File("listBallots.csv");
	        file.createNewFile();
	        FileWriter writer = new FileWriter(file);
	        for(Ballot tBallot: listBallots){
	            writer.write(tBallot.getBallotID() + "," + tBallot.getOwner().getFirstName() + "," + tBallot.getOwner().getLastName() +  "\n");
	        }
	        writer.flush();
	        writer.close();
	    }
	    catch(IOException e) {
	        System.out.println("saveBallotsError");
	    }
	}

	public static void saveCandidates(ArrayList<Candidate> listCandidates){
        try {            
            File file = new File("listCandidates.csv");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            for(Candidate tCandidate: listCandidates){
                writer.write(tCandidate.getCandidateID() + "," + tCandidate.getFirstName()  + "," + tCandidate.getLastName() + "," + tCandidate.getPartyAffiliation() + "," + tCandidate.getVoteCount() + "\n");
            }
            writer.flush();
            writer.close();
        }
        catch(IOException e) {
            System.out.println("saveCandidatesError");
        }
    }
}


