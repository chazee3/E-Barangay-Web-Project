package com.barangay;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity
public class Document {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
  private Long residentId; private String docType, purpose, dateIssued, status, validUntil, orNumber, amountPaid;
  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public Long getResidentId(){return residentId;} public void setResidentId(Long v){this.residentId=v;}
  public String getDocType(){return docType;} public void setDocType(String v){this.docType=v;}
  public String getPurpose(){return purpose;} public void setPurpose(String v){this.purpose=v;}
  public String getDateIssued(){return dateIssued;} public void setDateIssued(String v){this.dateIssued=v;}
  public String getStatus(){return status;} public void setStatus(String v){this.status=v;}
  public String getValidUntil(){return validUntil;} public void setValidUntil(String v){this.validUntil=v;}
  public String getOrNumber(){return orNumber;} public void setOrNumber(String v){this.orNumber=v;}
  public String getAmountPaid(){return amountPaid;} public void setAmountPaid(String v){this.amountPaid=v;}
  @Transient
  public String getControlNo(){
    String year = (dateIssued != null && dateIssued.length() >= 4) ? dateIssued.substring(0,4) : "----";
    return "DOC-" + year + "-" + String.format("%04d", id == null ? 0L : id);
  }
  @Transient
  public String getDisplayStatus(){
    try{
      if(validUntil != null && !validUntil.isBlank() && LocalDate.parse(validUntil).isBefore(LocalDate.now())) return "Expired";
    }catch(Exception e){}
    return status;
  }
}
