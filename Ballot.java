import java.time.LocalTime;
import java.util.ArrayList;
public class Ballot {

	private Voter owner;
	private int ballotID;
	private LocalTime startTime;
	private LocalTime endTime;
	private String pollOption;
	private ArrayList<Candidate> listOfCandidates;

	
	public Ballot(){
		
	}
	
	public Ballot(Voter owner, int id, LocalTime start){
		this.owner = owner;
		this.ballotID = id;
		this.startTime = start;
	}
	
	public Ballot(int id, String firstName, String lastName){
		this.owner.setFirstName(firstName);
		this.owner.setLastName(lastName);
		this.ballotID = id;
	}
	/**
	 * 
	 * @return owner
	 */
	public Voter getOwner() {
		return this.owner;
	}

	/**
	 * 
	 * @param owner
	 */
	public void setOwner(Voter owner) {
		this.owner = owner;
	}
	
	/**
	 * 
	 * @return ballotID
	 */
	public int getBallotID() {
		return this.ballotID;
	}

	/**
	 * 
	 * @param ballotID
	 */
	public void setBallotID(int ballotID) {
		this.ballotID = ballotID;
	}
	
	/**
	 * 
	 * @return startTIme
	 */
	public LocalTime getStartTime() {
		return this.startTime;
	}

	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * 
	 * @return endTime
 	 */
	public LocalTime getEndTime() {
		return this.endTime;
	}

	/**
	 * 
	 * @param endTime
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * 
	 * @return pollOption
	 */
	public String getPollOption() {
		return this.pollOption;
	}

	/**
	 * 
	 * @param pollOption
	 */
	public void setPollOption(String pollOption) {
		this.pollOption = pollOption;
	}

	/**
	 * 
	 * @return listOfCandidates
	 */
	public ArrayList<Candidate> getListOfCandidates() {
		return this.listOfCandidates;
	}

	/**
	 * 
	 * @param listOfCandidates
	 */
	public void setListOfCandidates(ArrayList<Candidate> listOfCandidates) {
		this.listOfCandidates = listOfCandidates;
	}
	
	/**
	 * Add a candidate to the list.
	 * @param candidate
	 */
	public void addCandidate(Candidate candidate){
		listOfCandidates.add(candidate);
	}

}