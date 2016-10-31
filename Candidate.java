public class Candidate extends Person {

	private String partyAffiliation;
	private int candidateID;
	
	/**
	 * Create a candidate object
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param party
	 */
	public Candidate(int id, String firstName, String lastName, String party){
		this.candidateID = id;
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPartyAffiliation(party);
	}

	/**
	 * 
	 * @return partAffiliation
	 */
	public String getPartyAffiliation() {
		return this.partyAffiliation;
	}

	/**
	 * 
	 * @param partyAffiliation
	 */
	public void setPartyAffiliation(String partyAffiliation) {
		this.partyAffiliation = partyAffiliation;
	}

	/**
	 * 
	 * @return candidateID
	 */
	public int getCandidateID() {
		return this.candidateID;
	}

	/**
	 * 
	 * @param candidateID
	 */
	public void setCandidateID(int candidateID) {
		this.candidateID = candidateID;
	}

}