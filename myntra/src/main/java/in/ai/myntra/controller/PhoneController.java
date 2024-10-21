package in.ai.myntra.controller;

import in.ai.myntra.model.Phone;
import in.ai.myntra.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @PostMapping
    public Phone addPhone(@RequestBody Phone phone) {
        return phoneService.addPhone(phone);
    }

    @GetMapping("/{id}")
    public Optional<Phone> getPhoneById(@PathVariable Long id) {
        return phoneService.getPhoneById(id);
    }

    @PutMapping
    public Phone updatePhone(@RequestBody Phone phone) {
        return phoneService.updatePhone(phone);
    }

    @DeleteMapping("/{id}")
    public void deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
    }

}
