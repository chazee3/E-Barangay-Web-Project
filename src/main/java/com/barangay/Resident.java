package com.barangay;

import jakarta.persistence.*;

@Entity
@Table(name = "residents")
public class Resident {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String middleName;
  private String lastName;
  private String extension;
  private String fullname; // Auto-combined

  private String purok;
  private String contact;
  private String birthdate;
  private String address;
  private String sex;
  private String civilStatus;
  private String occupation;
  private String category;
  private String status;
  private String disabilityType;
  private String pwdIdNumber;

  public Resident() {}

  // Getters & Setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getMiddleName() { return middleName; }
  public void setMiddleName(String middleName) { this.middleName = middleName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getExtension() { return extension; }
  public void setExtension(String extension) { this.extension = extension; }

  public String getFullname() { 
    if (fullname != null && !fullname.isBlank()) return fullname;
    StringBuilder sb = new StringBuilder();
    if (firstName != null) sb.append(firstName).append(" ");
    if (middleName != null && !middleName.isBlank()) sb.append(middleName).append(" ");
    if (lastName != null) sb.append(lastName);
    if (extension != null && !extension.isBlank()) sb.append(" ").append(extension);
    return sb.toString().trim();
  }
  public void setFullname(String fullname) { this.fullname = fullname; }

  public String getPurok() { return purok; }
  public void setPurok(String purok) { this.purok = purok; }

  public String getContact() { return contact; }
  public void setContact(String contact) { this.contact = contact; }

  public String getBirthdate() { return birthdate; }
  public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

  public String getAddress() { return address; }
  public void setAddress(String address) { this.address = address; }

  public String getSex() { return sex; }
  public void setSex(String sex) { this.sex = sex; }

  public String getCivilStatus() { return civilStatus; }
  public void setCivilStatus(String civilStatus) { this.civilStatus = civilStatus; }

  public String getOccupation() { return occupation; }
  public void setOccupation(String occupation) { this.occupation = occupation; }

  public String getCategory() { return category; }
  public void setCategory(String category) { this.category = category; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public String getDisabilityType() { return disabilityType; }
  public void setDisabilityType(String disabilityType) { this.disabilityType = disabilityType; }

  public String getPwdIdNumber() { return pwdIdNumber; }
  public void setPwdIdNumber(String pwdIdNumber) { this.pwdIdNumber = pwdIdNumber; }
}
