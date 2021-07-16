package com.ess.meetingplanner.model;

public enum MeetingType {
	
	VC(1, 0, 1, 1, 1),
	SPEC(1, 1, 0, 0, 0),
	RS23(0, 0, 0, 0, 0),
	RS4n(1, 0, 0, 0, 0),
	RC(1, 1, 1, 1, 0);
	  
	int nbrROOM;
	int nbrBOARD;
	int nbrSCREEN;
	int nbrSPIDER_PHONE;
	int nbrWEBCAM;
	
	MeetingType(int nbrROOM, int nbrBOARD, int nbrSCREEN, int nbrSPIDER_PHONE, int nbrWEBCAM) {
		
		this.nbrROOM = nbrROOM;
		this.nbrBOARD = nbrBOARD;
		this.nbrSCREEN = nbrSCREEN;
		this.nbrSPIDER_PHONE = nbrSPIDER_PHONE;
		this.nbrWEBCAM = nbrWEBCAM;
		  	
	}

	public int getNbrROOM() {
		return nbrROOM;
	}

	public void setNbrROOM(int nbrROOM) {
		this.nbrROOM = nbrROOM;
	}

	public int getNbrBOARD() {
		return nbrBOARD;
	}

	public void setNbrBOARD(int nbrBOARD) {
		this.nbrBOARD = nbrBOARD;
	}

	public int getNbrSCREEN() {
		return nbrSCREEN;
	}

	public void setNbrSCREEN(int nbrSCREEN) {
		this.nbrSCREEN = nbrSCREEN;
	}

	public int getNbrSPIDER_PHONE() {
		return nbrSPIDER_PHONE;
	}

	public void setNbrSPIDER_PHONE(int nbrSPIDER_PHONE) {
		this.nbrSPIDER_PHONE = nbrSPIDER_PHONE;
	}

	public int getNbrWEBCAM() {
		return nbrWEBCAM;
	}

	public void setNbrWEBCAM(int nbrWEBCAM) {
		this.nbrWEBCAM = nbrWEBCAM;
	}

}
