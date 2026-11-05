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
  @GetMapping("/residents") public String residents(Model m){ m.addAttribute("residents", residentRepo.findAll()); return "residents"; }
  @PostMapping("/residents/add") public String addResident(@RequestParam String fullname, @RequestParam String purok, @RequestParam String contact, @RequestParam String birthdate){
    Resident r = new Resident(); r.setFullname(fullname); r.setPurok(purok); r.setContact(contact); r.setBirthdate(birthdate); residentRepo.save(r); return "redirect:/residents";
  }
  @GetMapping("/documents") public String documents(Model m){ m.addAttribute("docs", docRepo.findAll()); m.addAttribute("residents", residentRepo.findAll()); return "documents"; }
  @PostMapping("/documents/add") public String addDoc(@RequestParam Long residentId, @RequestParam String docType, @RequestParam String purpose){
    Document d = new Document(); d.setResidentId(residentId); d.setDocType(docType); d.setPurpose(purpose); d.setDateIssued(LocalDate.now().toString()); d.setStatus("Issued"); docRepo.save(d); return "redirect:/documents";
  }
  @GetMapping("/blotter") public String blotter(Model m){ m.addAttribute("blotters", blotterRepo.findAll()); m.addAttribute("residents", residentRepo.findAll()); return "blotter"; }
  @PostMapping("/blotter/add") public String addBlotter(@RequestParam Long complainantId, @RequestParam String respondentName, @RequestParam String incidentType, @RequestParam String details){
    Blotter b = new Blotter(); b.setComplainantId(complainantId); b.setRespondentName(respondentName); b.setIncidentType(incidentType); b.setDetails(details); b.setDateFiled(LocalDate.now().toString()); b.setStatus("Pending"); blotterRepo.save(b); return "redirect:/blotter";
  }
  @GetMapping(value="/qr/{text}", produces="image/png") @ResponseBody
  public byte[] qr(@PathVariable String text) throws Exception { return QRUtil.generate(text); }
}