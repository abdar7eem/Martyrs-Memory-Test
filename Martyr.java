package application;

public class Martyr {
	
	private String martyrName;
	private String dateOfMartyrdom;
	
	public Martyr(String martyrName, String dateOfMartyrdom) {
		this.martyrName=martyrName;
		this.dateOfMartyrdom=dateOfMartyrdom;
	}
	public String getMartyrName() {
		return martyrName;
	}
	public void setMartyrName(String martyrName) {
		this.martyrName = martyrName;
	}
	public String getDateOfMartyrdom() {
		return dateOfMartyrdom;
	}
	public void setDateOfMartyrdom(String dateOfMartyrdom) {
		this.dateOfMartyrdom = dateOfMartyrdom;
	}
	
	
}
