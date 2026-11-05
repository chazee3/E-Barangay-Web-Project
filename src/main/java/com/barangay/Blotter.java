package com.barangay;
import jakarta.persistence.*;
@Entity
public class Blotter {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private Long complainantId; private String respondentName, incidentType, details, dateFiled, status;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Long getComplainantId(){return complainantId;} public void setComplainantId(Long v){this.complainantId=v;}
  public String getRespondentName(){return respondentName;} public void setRespondentName(String v){this.respondentName=v;}
  public String getIncidentType(){return incidentType;} public void setIncidentType(String v){this.incidentType=v;}
  public String getDetails(){return details;} public void setDetails(String v){this.details=v;}
  public String getDateFiled(){return dateFiled;} public void setDateFiled(String v){this.dateFiled=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
}