public class Ballot {

	private Voter owner;
	private int ballotID;
	private dateTime startTime;
	private dateTime endTime;
	private String pollOption;
	private ArrayList<Candidate> listOfCandidates;

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

	public dateTime getStartTime() {
		return this.startTime;
	}

	/**
	 * 
	 * @param startTime
	 */
	public void setStartTime(dateTime startTime) {
		this.startTime = startTime;
	}

	public dateTime getEndTime() {
		return this.endTime;
	}

	/**
	 * 
	 * @param endTime
	 */
	public void setEndTime(dateTime endTime) {
		this.endTime = endTime;
	}

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

}