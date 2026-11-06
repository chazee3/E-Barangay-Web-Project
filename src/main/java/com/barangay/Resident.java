package com.barangay;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
@Entity
public class Resident {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private String fullname, purok, contact, birthdate, address, sex, civilStatus, occupation, status, category, disabilityType, pwdIdNumber;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getFullname(){return fullname;} public void setFullname(String s){this.fullname=s;}
  public String getPurok(){return purok;} public void setPurok(String s){this.purok=s;}
  public String getContact(){return contact;} public void setContact(String s){this.contact=s;}
  public String getBirthdate(){return birthdate;} public void setBirthdate(String s){this.birthdate=s;}
  public String getAddress(){return address;} public void setAddress(String s){this.address=s;}
  public String getSex(){return sex;} public void setSex(String s){this.sex=s;}
  public String getCivilStatus(){return civilStatus;} public void setCivilStatus(String s){this.civilStatus=s;}
  public String getOccupation(){return occupation;} public void setOccupation(String s){this.occupation=s;}
  public String getStatus(){return (status==null||status.isBlank())?"Active":status;} public void setStatus(String s){this.status=s;}
  public String getCategory(){return category;} public void setCategory(String s){this.category=s;}
  public String getDisabilityType(){return disabilityType;} public void setDisabilityType(String s){this.disabilityType=s;}
  public String getPwdIdNumber(){return pwdIdNumber;} public void setPwdIdNumber(String s){this.pwdIdNumber=s;}
  @Transient
  public Integer getAge(){
    try{ return Period.between(LocalDate.parse(birthdate), LocalDate.now()).getYears(); }
    catch(Exception e){ return null; }
  }
}
