import java.time.LocalTime;
public class pollWorker extends Person {

	private String password;
	private int idNumber;

	/**
	 * 
	 * @param idNumber
	 */
	public void registerVoter(int idNumber) {
		// TODO - implement pollWorker.registerVoter
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param idNumber
	 */
	public boolean checkRegistration(int idNumber) {
		// TODO - implement pollWorker.checkRegistration
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ballotID
	 */
	public String getTallys(int ballotID) {
		// TODO - implement pollWorker.getTallys
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ballodID
	 */
	public void printResults(int ballodID) {
		// TODO - implement pollWorker.printResults
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ballotName
	 * @param ballotID
	 * @param start
	 * @param end
	 */
	public void createBallot(String ballotName, int ballotID, LocalTime start, LocalTime end) {
		// TODO - implement pollWorker.createBallot
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @return IDNumber
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

}