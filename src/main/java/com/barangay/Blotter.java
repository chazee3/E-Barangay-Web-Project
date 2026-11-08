package com.barangay;

import jakarta.persistence.*;

@Entity
@Table(name = "blotters")
public class Blotter {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long complainantId;
  private String complainant;
  private String contact; // Added contact
  private String respondentName;
  private String incidentType;
  private String details;
  private String incidentDate;
  private String incidentPlace;
  private String witnesses;
  private String dateFiled;
  private String status;
  private String caseNo;
  private String hearingDate;
  private String resolution;

  public Blotter() {}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getComplainantId() { return complainantId; }
  public void setComplainantId(Long complainantId) { this.complainantId = complainantId; }

  public String getComplainant() { return complainant; }
  public void setComplainant(String complainant) { this.complainant = complainant; }

  public String getContact() { return contact; }
  public void setContact(String contact) { this.contact = contact; }

  public String getRespondentName() { return respondentName; }
  public void setRespondentName(String respondentName) { this.respondentName = respondentName; }

  public String getIncidentType() { return incidentType; }
  public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

  public String getDetails() { return details; }
  public void setDetails(String details) { this.details = details; }

  public String getIncidentDate() { return incidentDate; }
  public void setIncidentDate(String incidentDate) { this.incidentDate = incidentDate; }

  public String getIncidentPlace() { return incidentPlace; }
  public void setIncidentPlace(String incidentPlace) { this.incidentPlace = incidentPlace; }

  public String getWitnesses() { return witnesses; }
  public void setWitnesses(String witnesses) { this.witnesses = witnesses; }

  public String getDateFiled() { return dateFiled; }
  public void setDateFiled(String dateFiled) { this.dateFiled = dateFiled; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public String getCaseNo() { return caseNo; }
  public void setCaseNo(String caseNo) { this.caseNo = caseNo; }

  public String getHearingDate() { return hearingDate; }
  public void setHearingDate(String hearingDate) { this.hearingDate = hearingDate; }

  public String getResolution() { return resolution; }
  public void setResolution(String resolution) { this.resolution = resolution; }
}
