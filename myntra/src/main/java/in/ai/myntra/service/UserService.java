package in.ai.myntra.service;

import in.ai.myntra.model.Phone;
import in.ai.myntra.model.Address;
import in.ai.myntra.model.User;
import in.ai.myntra.repo.PhoneRepository;
import in.ai.myntra.repo.AddressRepository;
import in.ai.myntra.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private AddressRepository addressRepository;

    public User createUser(User user) {
        // Set the user reference in each phone and address
        user.getPhoneNumbers().forEach(phone -> phone.setUser(user));
        user.getAddresses().forEach(address -> address.setUser(user));
        // Save the user (this will also save the associated phones and addresses due to CascadeType.ALL)
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        // Set the user reference in each phone and address
        user.getPhoneNumbers().forEach(phone -> phone.setUser(user));
        user.getAddresses().forEach(address -> address.setUser(user));
        // Update the user (this will also update the associated phones and addresses due to CascadeType.ALL)
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User registerUserWithPhone(String phoneNumber) {
        User user = new User();
        Phone phone = new Phone(phoneNumber);
        user.addPhoneNumber(phone);
        return userRepository.save(user);
    }
}
