package com.barangay;

import jakarta.persistence.*;

@Entity
@Table(name = "documents")
public class Document {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long residentId;
  private String requester;
  private String contact; // Added contact
  private String docType;
  private String purpose;
  private String validUntil;
  private String orNumber;
  private String amountPaid;
  private String dateIssued;
  private String status;
  private String controlNo;

  public Document() {}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Long getResidentId() { return residentId; }
  public void setResidentId(Long residentId) { this.residentId = residentId; }

  public String getRequester() { return requester; }
  public void setRequester(String requester) { this.requester = requester; }

  public String getContact() { return contact; }
  public void setContact(String contact) { this.contact = contact; }

  public String getDocType() { return docType; }
  public void setDocType(String docType) { this.docType = docType; }

  public String getPurpose() { return purpose; }
  public void setPurpose(String purpose) { this.purpose = purpose; }

  public String getValidUntil() { return validUntil; }
  public void setValidUntil(String validUntil) { this.validUntil = validUntil; }

  public String getOrNumber() { return orNumber; }
  public void setOrNumber(String orNumber) { this.orNumber = orNumber; }

  public String getAmountPaid() { return amountPaid; }
  public void setAmountPaid(String amountPaid) { this.amountPaid = amountPaid; }

  public String getDateIssued() { return dateIssued; }
  public void setDateIssued(String dateIssued) { this.dateIssued = dateIssued; }

  public String getStatus() { return status; }
  public void setStatus(String status) { this.status = status; }

  public String getControlNo() { return controlNo; }
  public void setControlNo(String controlNo) { this.controlNo = controlNo; }

  public String getDisplayStatus() { return status != null ? status : "Issued"; }
}
