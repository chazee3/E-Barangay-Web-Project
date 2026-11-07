package com.barangay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.LocalDate;
import java.util.*;

@Controller
public class MainController {
  @Autowired ResidentRepository residentRepo;
  @Autowired DocumentRepository docRepo;
  @Autowired BlotterRepository blotterRepo;

  private boolean has(String v, String s){ return v != null && v.toLowerCase().contains(s); }
  private Map<Long,String> nameMap(){
    Map<Long,String> map = new HashMap<>();
    for(Resident r : residentRepo.findAll()){ map.put(r.getId(), r.getFullname() == null ? "" : r.getFullname()); }
    return map;
  }

  @GetMapping("/") public String index(Model m){
    m.addAttribute("residentCount", residentRepo.count());
    m.addAttribute("docCount", docRepo.count());
    m.addAttribute("blotterCount", blotterRepo.count());
    return "index";
  }

  // ---------- RESIDENTS ----------
  @GetMapping("/residents") public String residents(@RequestParam(required=false) String q, @RequestParam(required=false) String category, Model m){
    List<Resident> list = residentRepo.findAll();
    if(q != null && !q.isBlank()){
      String s = q.toLowerCase().trim();
      list = list.stream().filter(r -> has(r.getFullname(), s) || has(r.getPurok(), s) || has(r.getContact(), s)).toList();
    }
    if(category != null && !category.isBlank()){
      list = list.stream().filter(r -> category.equals(r.getCategory())).toList();
    }
    m.addAttribute("residents", list);
    m.addAttribute("q", q);
    m.addAttribute("category", category);
    return "residents";
  }

  @PostMapping("/residents/add") public String addResident(@RequestParam String fullname, @RequestParam(required=false) String purok, @RequestParam(required=false) String contact, @RequestParam(required=false) String birthdate,
      @RequestParam(required=false) String address, @RequestParam(required=false) String sex, @RequestParam(required=false) String civilStatus, @RequestParam(required=false) String occupation,
      @RequestParam(required=false) String category, @RequestParam(required=false) String disabilityType, @RequestParam(required=false) String pwdIdNumber){
    Resident r = new Resident();
    r.setFullname(fullname); r.setPurok(purok); r.setContact(contact); r.setBirthdate(birthdate);
    r.setAddress(address); r.setSex(sex); r.setCivilStatus(civilStatus); r.setOccupation(occupation);
    r.setCategory(category); r.setStatus("Active");
    r.setDisabilityType(disabilityType); r.setPwdIdNumber(pwdIdNumber);
    residentRepo.save(r); return "redirect:/residents";
  }

  @GetMapping("/residents/edit/{id}") public String editResident(@PathVariable Long id, Model m){
    Resident r = residentRepo.findById(id).orElse(null);
    if(r == null) return "redirect:/residents";
    m.addAttribute("r", r); return "resident-edit";
  }

  @PostMapping("/residents/update/{id}") public String updateResident(@PathVariable Long id, @RequestParam String fullname, @RequestParam(required=false) String purok, @RequestParam(required=false) String contact, @RequestParam(required=false) String birthdate,
      @RequestParam(required=false) String address, @RequestParam(required=false) String sex, @RequestParam(required=false) String civilStatus, @RequestParam(required=false) String occupation,
      @RequestParam(required=false) String status, @RequestParam(required=false) String category,
      @RequestParam(required=false) String disabilityType, @RequestParam(required=false) String pwdIdNumber){
    Resident r = residentRepo.findById(id).orElse(null);
    if(r != null){
      r.setFullname(fullname); r.setPurok(purok); r.setContact(contact); r.setBirthdate(birthdate);
      r.setAddress(address); r.setSex(sex); r.setCivilStatus(civilStatus); r.setOccupation(occupation);
      r.setStatus(status); r.setCategory(category);
      r.setDisabilityType(disabilityType); r.setPwdIdNumber(pwdIdNumber);
      residentRepo.save(r);
    }
    return "redirect:/residents";
  }

  @GetMapping("/residents/view/{id}") public String viewResident(@PathVariable Long id, Model m){
    Resident r = residentRepo.findById(id).orElse(null);
    if(r == null) return "redirect:/residents";
    m.addAttribute("r", r);
    m.addAttribute("docs", docRepo.findAll().stream().filter(d -> id.equals(d.getResidentId())).toList());
    m.addAttribute("blotters", blotterRepo.findAll().stream().filter(b -> id.equals(b.getComplainantId())).toList());
    return "resident-view";
  }

  @PostMapping("/residents/delete/{id}") public String deleteResident(@PathVariable Long id){
    boolean hasDocs = docRepo.findAll().stream().anyMatch(d -> id.equals(d.getResidentId()));
    boolean hasBlotters = blotterRepo.findAll().stream().anyMatch(b -> id.equals(b.getComplainantId()));
    if(hasDocs || hasBlotters) return "redirect:/residents?error=1";
    residentRepo.deleteById(id); return "redirect:/residents";
  }

  // Public Resident verification page (opened when scanning a Resident QR code)
  @GetMapping("/verify/resident/{id}") public String verifyResident(@PathVariable Long id, Model m){
    Resident r = residentRepo.findById(id).orElse(null);
    m.addAttribute("r", r);
    return "resident-verify";
  }

  // ---------- DOCUMENTS ----------
  @GetMapping("/documents") public String documents(@RequestParam(required=false) String q, @RequestParam(required=false) String type, @RequestParam(required=false) String status, Model m){
    Map<Long,String> names = nameMap();
    List<Document> list = docRepo.findAll();
    if(q != null && !q.isBlank()){
      String s = q.toLowerCase().trim();
      list = list.stream().filter(d -> has(names.get(d.getResidentId()), s) || has(d.getPurpose(), s) || has(d.getOrNumber(), s) || has(d.getControlNo(), s)).toList();
    }
    if(type != null && !type.isBlank()){
      list = list.stream().filter(d -> type.equals(d.getDocType())).toList();
    }
    if(status != null && !status.isBlank()){
      list = list.stream().filter(d -> status.equals(d.getDisplayStatus())).toList();
    }
    list = list.stream().sorted(Comparator.comparing(Document::getId).reversed()).toList();
    m.addAttribute("docs", list);
    m.addAttribute("residents", residentRepo.findAll());
    m.addAttribute("names", names);
    m.addAttribute("q", q); m.addAttribute("type", type); m.addAttribute("status", status);
    return "documents";
  }

  @PostMapping("/documents/add") public String addDoc(@RequestParam Long residentId, @RequestParam String docType, @RequestParam(required=false) String purpose,
      @RequestParam(required=false) String validUntil, @RequestParam(required=false) String orNumber, @RequestParam(required=false) String amountPaid){
    Document d = new Document(); d.setResidentId(residentId); d.setDocType(docType); d.setPurpose(purpose);
    d.setValidUntil(validUntil); d.setOrNumber(orNumber); d.setAmountPaid(amountPaid);
    d.setDateIssued(LocalDate.now().toString()); d.setStatus("Issued"); docRepo.save(d); return "redirect:/documents";
  }

  @GetMapping("/documents/view/{id}") public String viewDoc(@PathVariable Long id, Model m){
    Document d = docRepo.findById(id).orElse(null);
    if(d == null) return "redirect:/documents";
    m.addAttribute("d", d);
    m.addAttribute("resident", residentRepo.findById(d.getResidentId()).orElse(null));
    return "document-view";
  }

  @GetMapping("/documents/edit/{id}") public String editDoc(@PathVariable Long id, Model m){
    Document d = docRepo.findById(id).orElse(null);
    if(d == null) return "redirect:/documents";
    m.addAttribute("d", d);
    m.addAttribute("residentName", residentRepo.findById(d.getResidentId()).map(Resident::getFullname).orElse("Unknown"));
    return "document-edit";
  }

  @PostMapping("/documents/update/{id}") public String updateDoc(@PathVariable Long id, @RequestParam String docType, @RequestParam(required=false) String purpose, @RequestParam(required=false) String status,
      @RequestParam(required=false) String validUntil, @RequestParam(required=false) String orNumber, @RequestParam(required=false) String amountPaid){
    Document d = docRepo.findById(id).orElse(null);
    if(d != null){
      d.setDocType(docType); d.setPurpose(purpose); d.setStatus(status);
      d.setValidUntil(validUntil); d.setOrNumber(orNumber); d.setAmountPaid(amountPaid);
      docRepo.save(d);
    }
    return "redirect:/documents";
  }

  @PostMapping("/documents/delete/{id}") public String deleteDoc(@PathVariable Long id){
    docRepo.deleteById(id); return "redirect:/documents";
  }

  // Public Document verification page (opened by scanning the QR on a certificate)
  @GetMapping("/verify/{id}") public String verifyDoc(@PathVariable Long id, Model m){
    Document d = docRepo.findById(id).orElse(null);
    m.addAttribute("d", d);
    if(d != null){
      m.addAttribute("holder", residentRepo.findById(d.getResidentId()).map(Resident::getFullname).orElse("Unknown"));
      m.addAttribute("valid", !"Expired".equals(d.getDisplayStatus()));
    }
    return "document-verify";
  }

  // ---------- BLOTTER ----------
  @GetMapping("/blotter") public String blotter(@RequestParam(required=false) String q, @RequestParam(required=false) String type, @RequestParam(required=false) String status, Model m){
    Map<Long,String> names = nameMap();
    List<Blotter> list = blotterRepo.findAll();
    if(q != null && !q.isBlank()){
      String s = q.toLowerCase().trim();
      list = list.stream().filter(b -> has(names.get(b.getComplainantId()), s) || has(b.getRespondentName(), s) || has(b.getIncidentPlace(), s) || has(b.getCaseNo(), s)).toList();
    }
    if(type != null && !type.isBlank()){
      list = list.stream().filter(b -> type.equals(b.getIncidentType())).toList();
    }
    if(status != null && !status.isBlank()){
      list = list.stream().filter(b -> status.equals(b.getStatus())).toList();
    }
    list = list.stream().sorted(Comparator.comparing(Blotter::getId).reversed()).toList();
    m.addAttribute("blotters", list);
    m.addAttribute("residents", residentRepo.findAll());
    m.addAttribute("names", names);
    m.addAttribute("q", q); m.addAttribute("type", type); m.addAttribute("status", status);
    return "blotter";
  }

  @PostMapping("/blotter/add") public String addBlotter(@RequestParam Long complainantId, @RequestParam String respondentName, @RequestParam String incidentType, @RequestParam(required=false) String details,
      @RequestParam(required=false) String incidentDate, @RequestParam(required=false) String incidentPlace, @RequestParam(required=false) String witnesses){
    Blotter b = new Blotter(); b.setComplainantId(complainantId); b.setRespondentName(respondentName); b.setIncidentType(incidentType); b.setDetails(details);
    b.setIncidentDate(incidentDate); b.setIncidentPlace(incidentPlace); b.setWitnesses(witnesses);
    b.setDateFiled(LocalDate.now().toString()); b.setStatus("Pending"); blotterRepo.save(b); return "redirect:/blotter";
  }

  @GetMapping("/blotter/view/{id}") public String viewBlotter(@PathVariable Long id, Model m){
    Blotter b = blotterRepo.findById(id).orElse(null);
    if(b == null) return "redirect:/blotter";
    m.addAttribute("b", b);
    m.addAttribute("complainant", residentRepo.findById(b.getComplainantId()).map(Resident::getFullname).orElse("Unknown"));
    return "blotter-view";
  }

  @GetMapping("/blotter/edit/{id}") public String editBlotter(@PathVariable Long id, Model m){
    Blotter b = blotterRepo.findById(id).orElse(null);
    if(b == null) return "redirect:/blotter";
    m.addAttribute("b", b);
    m.addAttribute("complainant", residentRepo.findById(b.getComplainantId()).map(Resident::getFullname).orElse("Unknown"));
    return "blotter-edit";
  }

  @PostMapping("/blotter/update/{id}") public String updateBlotter(@PathVariable Long id, @RequestParam String respondentName, @RequestParam String incidentType, @RequestParam(required=false) String details,
      @RequestParam(required=false) String incidentDate, @RequestParam(required=false) String incidentPlace, @RequestParam(required=false) String witnesses,
      @RequestParam(required=false) String resolution, @RequestParam(required=false) String status, @RequestParam(required=false) String hearingDate){
    Blotter b = blotterRepo.findById(id).orElse(null);
    if(b != null){
      b.setRespondentName(respondentName); b.setIncidentType(incidentType); b.setDetails(details);
      b.setIncidentDate(incidentDate); b.setIncidentPlace(incidentPlace); b.setWitnesses(witnesses);
      b.setResolution(resolution); b.setStatus(status); b.setHearingDate(hearingDate);
      blotterRepo.save(b);
    }
    return "redirect:/blotter";
  }

  @PostMapping("/blotter/delete/{id}") public String deleteBlotter(@PathVariable Long id){
    blotterRepo.deleteById(id); return "redirect:/blotter";
  }

  // ---------- QR GENERATORS ----------

  // Smart endpoint: if text is "BRGY-2", it auto-resolves to https://your-domain/verify/resident/2
  @GetMapping(value="/qr/{text}", produces="image/png") @ResponseBody
  public byte[] qr(@PathVariable String text) throws Exception {
    if (text != null && text.startsWith("BRGY-")) {
      try {
        String cleanId = text.substring(5).trim();
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/verify/resident/" + cleanId).toUriString();
        return QRUtil.generate(url);
      } catch (Exception ignored) {}
    }
    return QRUtil.generate(text);
  }

  // Dedicated resident QR generator
  @GetMapping(value="/qr/resident/{id}", produces="image/png") @ResponseBody
  public byte[] residentQr(@PathVariable Long id) throws Exception {
    String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/verify/resident/" + id).toUriString();
    return QRUtil.generate(url);
  }

  // Document QR generator
  @GetMapping(value="/qr/doc/{id}", produces="image/png") @ResponseBody
  public byte[] docQr(@PathVariable Long id) throws Exception {
    String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/verify/" + id).toUriString();
    return QRUtil.generate(url);
  }
}
