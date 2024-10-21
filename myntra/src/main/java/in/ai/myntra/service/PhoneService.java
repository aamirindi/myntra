package in.ai.myntra.service;

import in.ai.myntra.model.Phone;
import in.ai.myntra.repo.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepository;


    public Phone addPhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    public Optional<Phone> getPhoneById(Long id) {
        return phoneRepository.findById(id);
    }

    public Phone updatePhone(Phone phone) {
        return phoneRepository.save(phone);
    }

    public void deletePhone(Long id) {
        phoneRepository.deleteById(id);
    }
}
