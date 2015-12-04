package wopi.model;

public class WANOrderDetails {
	public String getPlannedInstallationDate() {
		return plannedInstallationDate;
	}

	public void setPlannedInstallationDate(String plannedInstallationDate) {
		this.plannedInstallationDate = plannedInstallationDate;
	}

	public String number;
	public String status;
	public String Company;
	public String Contact;
	public String picture;// the name of display picture
	public String rowData;
	public String StringCustomerRequest;
	public String StringPriceRequestToCarrier;
	public String StringPriceGotFromCarrier;
	public String StringPriceToCustomer;
	public String StringCustomerApprovalInitiated;
	public String plannedInstallationDate;
	public String CountryCode;
	public String LocatioCode;
	

	public String getDateCustomerRequest() {
		return StringCustomerRequest;
	}

	public String getLocatioCode() {
		return LocatioCode;
	}

	public void setLocatioCode(String locatioCode) {
		LocatioCode = locatioCode;
	}

	public String getCountryCode() {
		return CountryCode;
	}

	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}

	public void setDateCustomerRequest(String StringCustomerRequest) {
		this.StringCustomerRequest = StringCustomerRequest;
	}

	public String getDatePriceRequestToCarrier() {
		return StringPriceRequestToCarrier;
	}

	public void setDatePriceRequestToCarrier(String StringPriceRequestToCarrier) {
		this.StringPriceRequestToCarrier = StringPriceRequestToCarrier;
	}

	public String getDatePriceGotFromCarrier() {
		return StringPriceGotFromCarrier;
	}

	public void setDatePriceGotFromCarrier(String StringPriceGotFromCarrier) {
		this.StringPriceGotFromCarrier = StringPriceGotFromCarrier;
	}

	public String getDatePriceToCustomer() {
		return StringPriceToCustomer;
	}

	public void setDatePriceToCustomer(String StringPriceToCustomer) {
		this.StringPriceToCustomer = StringPriceToCustomer;
	}

	public String getDateCustomerApprovalInitiated() {
		return StringCustomerApprovalInitiated;
	}

	public void setDateCustomerApprovalInitiated(String StringCustomerApprovalInitiated) {
		this.StringCustomerApprovalInitiated = StringCustomerApprovalInitiated;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getContact() {
		return this.Contact;
	}

	public void setContact(String contact) {
		this.Contact = contact;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompany() {
		return this.Company;
	}

	public void setCompany(String company) {
		this.Company = company;
	}

	public String getRowData() {
		return rowData;
	}

	public void setRowData(String rowData) {
		this.rowData = rowData;
	}
}
