public class Candidate extends Person {

	private String partyAffiliation;
	private int candidateID;

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