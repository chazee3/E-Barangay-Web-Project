package com.barangay;
import jakarta.persistence.*;
@Entity
public class Resident {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String fullname, purok, contact, birthdate, address, sex, civilStatus, occupation;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getFullname(){return fullname;} public void setFullname(String s){this.fullname=s;}
  public String getPurok(){return purok;} public void setPurok(String s){this.purok=s;}
  public String getContact(){return contact;} public void setContact(String s){this.contact=s;}
  public String getBirthdate(){return birthdate;} public void setBirthdate(String s){this.birthdate=s;}
  public String getAddress(){return address;} public void setAddress(String s){this.address=s;}
  public String getSex(){return sex;} public void setSex(String s){this.sex=s;}
  public String getCivilStatus(){return civilStatus;} public void setCivilStatus(String s){this.civilStatus=s;}
  public String getOccupation(){return occupation;} public void setOccupation(String s){this.occupation=s;}
}
