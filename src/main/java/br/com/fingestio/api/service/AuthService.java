package br.com.fingestio.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.fingestio.api.repository.UserRepository;
import br.com.fingestio.api.model.User;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;


    public User doLogin(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (!optionalUser.isPresent()) {
            throw new Exception("USER_NOT_FOUND");
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            throw new Exception("INVALID_PASSWORD");
        }

        return user;
    }
}