package de.jonashackt.springbootvuejs.controller;

import de.jonashackt.springbootvuejs.domain.Medicine;
import de.jonashackt.springbootvuejs.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
@RestController()
@RequestMapping("/api")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping(value = "/medicine/create",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createMedicine(@RequestBody Medicine medicine) throws Exception {
        String s = medicineService.createMedicine(medicine);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @PostMapping(path = "/medicine/delete/{id}")
    public ResponseEntity<String>  deleteMedicine(@PathVariable long id) {
        String s = medicineService.deleteMedicine(id);
        return new ResponseEntity<String>(s, HttpStatus.OK);
    }

    @GetMapping(value = "/medicine/getall",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Medicine>> getAllMedicines() {
        Collection<Medicine> medicines = medicineService.getAllMedicines();
        return new ResponseEntity<Collection<Medicine>>(medicines, HttpStatus.OK);
    }

    @GetMapping(path = "/medicine/{id}")
    public ResponseEntity<Medicine> getMedicineById(@PathVariable("id") long id) {
        Medicine m = medicineService.getMedicine(id);
        return new ResponseEntity<Medicine>(m, HttpStatus.OK);
    }
}
