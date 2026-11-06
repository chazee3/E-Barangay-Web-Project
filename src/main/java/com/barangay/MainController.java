package com.barangay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
public class MainController {
  @Autowired ResidentRepository residentRepo;
  @Autowired DocumentRepository docRepo;
  @Autowired BlotterRepository blotterRepo;

  @GetMapping("/") public String index(Model m){
    m.addAttribute("residentCount", residentRepo.count());
    m.addAttribute("docCount", docRepo.count());
    m.addAttribute("blotterCount", blotterRepo.count());
    return "index";
  }

  // ---------- RESIDENTS ----------
  @GetMapping("/residents") public String residents(Model m){ m.addAttribute("residents", residentRepo.findAll()); return "residents"; }

  @PostMapping("/residents/add") public String addResident(@RequestParam String fullname, @RequestParam(required=false) String purok, @RequestParam(required=false) String contact, @RequestParam(required=false) String birthdate,
      @RequestParam(required=false) String address, @RequestParam(required=false) String sex, @RequestParam(required=false) String civilStatus, @RequestParam(required=false) String occupation){
    Resident r = new Resident();
    r.setFullname(fullname); r.setPurok(purok); r.setContact(contact); r.setBirthdate(birthdate);
    r.setAddress(address); r.setSex(sex); r.setCivilStatus(civilStatus); r.setOccupation(occupation);
    residentRepo.save(r); return "redirect:/residents";
  }

  @GetMapping("/residents/edit/{id}") public String editResident(@PathVariable Long id, Model m){
    Resident r = residentRepo.findById(id).orElse(null);
    if(r == null) return "redirect:/residents";
    m.addAttribute("r", r); return "resident-edit";
  }

  @PostMapping("/residents/update/{id}") public String updateResident(@PathVariable Long id, @RequestParam String fullname, @RequestParam(required=false) String purok, @RequestParam(required=false) String contact, @RequestParam(required=false) String birthdate,
      @RequestParam(required=false) String address, @RequestParam(required=false) String sex, @RequestParam(required=false) String civilStatus, @RequestParam(required=false) String occupation){
    Resident r = residentRepo.findById(id).orElse(null);
    if(r != null){
      r.setFullname(fullname); r.setPurok(purok); r.setContact(contact); r.setBirthdate(birthdate);
      r.setAddress(address); r.setSex(sex); r.setCivilStatus(civilStatus); r.setOccupation(occupation);
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
    residentRepo.deleteById(id); return "redirect:/residents";
  }

  // ---------- DOCUMENTS ----------
  @GetMapping("/documents") public String documents(Model m){ m.addAttribute("docs", docRepo.findAll()); m.addAttribute("residents", residentRepo.findAll()); return "documents"; }
  @PostMapping("/documents/add") public String addDoc(@RequestParam Long residentId, @RequestParam String docType, @RequestParam String purpose){
    Document d = new Document(); d.setResidentId(residentId); d.setDocType(docType); d.setPurpose(purpose); d.setDateIssued(LocalDate.now().toString()); d.setStatus("Issued"); docRepo.save(d); return "redirect:/documents";
  }
  @PostMapping("/documents/delete/{id}") public String deleteDoc(@PathVariable Long id){
    docRepo.deleteById(id); return "redirect:/documents";
  }

  // ---------- BLOTTER ----------
  @GetMapping("/blotter") public String blotter(Model m){ m.addAttribute("blotters", blotterRepo.findAll()); m.addAttribute("residents", residentRepo.findAll()); return "blotter"; }
  @PostMapping("/blotter/add") public String addBlotter(@RequestParam Long complainantId, @RequestParam String respondentName, @RequestParam String incidentType, @RequestParam String details){
    Blotter b = new Blotter(); b.setComplainantId(complainantId); b.setRespondentName(respondentName); b.setIncidentType(incidentType); b.setDetails(details); b.setDateFiled(LocalDate.now().toString()); b.setStatus("Pending"); blotterRepo.save(b); return "redirect:/blotter";
  }
  @PostMapping("/blotter/delete/{id}") public String deleteBlotter(@PathVariable Long id){
    blotterRepo.deleteById(id); return "redirect:/blotter";
  }

  // ---------- QR ----------
  @GetMapping(value="/qr/{text}", produces="image/png") @ResponseBody
  public byte[] qr(@PathVariable String text) throws Exception { return QRUtil.generate(text); }
}
