package com.barangay;
import jakarta.persistence.*;
@Entity
public class Blotter {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private Long complainantId; private String respondentName, incidentType, details, dateFiled, status, incidentDate, incidentPlace, witnesses, resolution, hearingDate;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Long getComplainantId(){return complainantId;} public void setComplainantId(Long v){this.complainantId=v;}
  public String getRespondentName(){return respondentName;} public void setRespondentName(String v){this.respondentName=v;}
  public String getIncidentType(){return incidentType;} public void setIncidentType(String v){this.incidentType=v;}
  public String getDetails(){return details;} public void setDetails(String v){this.details=v;}
  public String getDateFiled(){return dateFiled;} public void setDateFiled(String v){this.dateFiled=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
  public String getIncidentDate(){return incidentDate;} public void setIncidentDate(String v){this.incidentDate=v;}
  public String getIncidentPlace(){return incidentPlace;} public void setIncidentPlace(String v){this.incidentPlace=v;}
  public String getWitnesses(){return witnesses;} public void setWitnesses(String v){this.witnesses=v;}
  public String getResolution(){return resolution;} public void setResolution(String v){this.resolution=v;}
  public String getHearingDate(){return hearingDate;} public void setHearingDate(String v){this.hearingDate=v;}
  @Transient
  public String getCaseNo(){
    String year = (dateFiled != null && dateFiled.length() >= 4) ? dateFiled.substring(0,4) : "----";
    return "BLT-" + year + "-" + String.format("%04d", id == null ? 0L : id);
  }
}
