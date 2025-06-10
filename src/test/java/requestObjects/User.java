package requestObjects;

public class User {

	private String userFirstName, userLastName,userEmailId;
    String userContactNumber;
    Address userAddress;

    public User(String userFirstName,String userLastName,String userContactNumber,String userEmailId,Address userAddress){
        this.userAddress = userAddress;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userContactNumber = userContactNumber;
        this.userEmailId = userEmailId;
    }

    public Address getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(Address userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getUserContactNumber() {
		return userContactNumber;
	}

	public void setUserContactNumber(String userContactNumber) {
		this.userContactNumber = userContactNumber;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserFirstName() {
        return userFirstName;
    }

   
}
