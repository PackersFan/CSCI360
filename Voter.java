public class Voter extends Person {

	private boolean registrationStatus;
	private String password;
	private int idNumber;
	private Ballot ballot;
	
	public Voter(){
		this.idNumber = 0;
	}
	
	public Voter(String fName, String lName, String rStatus, int id, String pass){
		this.idNumber = id;
		this.setFirstName(fName);
		this.setLastName(lName);
		this.setRegistrationStatus(rStatus.equals("True"));
		this.setPassword(pass);
	}

	/**
	 * 
	 * @return registrationStatus
	 */
	public boolean getRegistrationStatus() {
		return this.registrationStatus;
	}

	/**
	 * 
	 * @param registrationStatus
	 */
	public void setRegistrationStatus(boolean registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	/**
	 * This method is used to cast the vote of the voter.
	 */
	public void castVote() {
		// TODO - implement Voter.castVote
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @return idNumber
	 */
	public int getIdNumber() {
		return this.idNumber;
	}

	/**
	 * 
	 * @param idNumber
	 */
	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * 
	 * @return ballot
	 */
	public Ballot getBallot() {
		return this.ballot;
	}

	/**
	 * 
	 * @param ballot
	 */
	public void setBallot(Ballot ballot) {
		this.ballot = ballot;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}