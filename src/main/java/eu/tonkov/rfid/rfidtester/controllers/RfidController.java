package eu.tonkov.rfid.rfidtester.controllers;

import eu.tonkov.rfid.rfidtester.entities.RfidEntry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class RfidController {
   private final List<String> responses;
   private RfidEntry entry;

   public RfidController(){
       this.responses = new LinkedList<>();
   }

    @RequestMapping("/list")
    public String getScannedRfidList(){
        StringBuilder builder = new StringBuilder();
        for (String r : responses){
            builder.append(r).append("<br>");
        }
        return builder.toString();
    }

    @RequestMapping("/info")
    public String getScannedRfidInfo(){
        return entry.rfid_ex() + " " + entry.operator();
    }
/*
    @PostMapping("/epc")
    public ResponseEntity<String> handleRfidPost(@RequestBody String body){
        if (responses.size() > 10){
            responses.remove(0);
        }
        responses.add(body);
        return new ResponseEntity<>("200", HttpStatus.OK);
    }
*/

    @PostMapping("/epc")
    public ResponseEntity<String> handlePostRequest(@RequestBody RfidEntry entry){
        System.out.printf("epc: %s, operator: %s\n", entry.rfid_ex(), entry.operator());
        this.entry = entry;
        if (entry.rfid_ex().equals("E20000206712004017300C50")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
