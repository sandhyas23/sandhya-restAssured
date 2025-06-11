package requestObjects;

public class Address {
	  String plotNumber,street, state,country;
	    int zipCode;

	    public Address(String plotNumber, String street, String state, String country, int zipCode){
	        this.plotNumber = plotNumber;
	        this.street = street;
	        this.state = state;
	        this.country = country;
	        this.zipCode = zipCode;
	    }

	

	    public Address() {
			// TODO Auto-generated constructor stub
		}
	    
	    @Override
	    public String toString() {
	        return "Address{" +
	                "plotNumber='" + plotNumber + '\'' +
	                ", street='" + street + '\'' +
	                ", state='" + state + '\'' +
	                ", country='" + country + '\'' +
	                ", zipCode='" + zipCode + '\'' +
	                '}';
	    }




		public String getStreet() {
	        return street;
	    }

	    public void setStreet(String street) {
	        this.street = street;
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }

	    public String getCountry() {
	        return country;
	    }

	    public void setCountry(String country) {
	        this.country = country;
	    }

	    public int getZipCode() {
	        return zipCode;
	    }

	    public void setZipCode(int zipCode) {
	        this.zipCode = zipCode;
	    }



		public String getPlotNumber() {
			return plotNumber;
		}



		public void setPlotNumber(String plotNumber) {
			this.plotNumber = plotNumber;
		}
	

}
