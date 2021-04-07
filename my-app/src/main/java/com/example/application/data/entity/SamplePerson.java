package com.example.application.data.entity;

import javax.persistence.Entity;

import com.example.application.data.AbstractEntity;
import java.time.LocalDate;

@Entity
public class SamplePerson extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfVaccination;
    private String occupation;
    private boolean important;
    private String age;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
  
    
    public String getOccupation() {
        return occupation;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
    public boolean isImportant() {
        return important;
    }
    public void setImportant(boolean important) {
        this.important = important;
    }
	public String getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = Integer.toString(age);
	}
	public void setAge(String age) {
		this.age = age;
	}
	public LocalDate getDateOfVaccination() {
		return dateOfVaccination;
	}
	public void setDateOfVaccination(LocalDate dateOfVaccination) {
		this.dateOfVaccination = dateOfVaccination;
	}

}
