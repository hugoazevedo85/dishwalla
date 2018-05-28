package org.dishwalla.dtos.inputs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerCreationRequest {
	
	 private static final SimpleDateFormat dateFormat
     = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	

    @NotNull
    @NotEmpty
	private String email;
    
    @NotNull
    @NotEmpty
	private String name;

    @NotNull
    @NotEmpty
	private String address;
    

    @NotNull
    @NotEmpty
	private String creation;
    

    @NotNull
    @NotEmpty
	private String password;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreation() {
		return creation;
	}
	public void setCreation(String creation) {
		this.creation = creation;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getDateConverted(String timezone) throws ParseException {
		 dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
		 Calendar toReeturn =  Calendar.getInstance();
		 toReeturn.setTime(dateFormat.parse(timezone));
		 return toReeturn;
	}
	

	@Override
	public String toString() {
		return "CustomerCreationRequest [email=" + email + ", name=" + name + ", address=" + address + ", creation="
				+ creation + ", password=" + password + "]";
	}
	
	
}
