package ro.mpp2024.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.mpp2024.model.Utilizator;
import ro.mpp2024.persistence.repository.UtilizatorRepository;

@RestController
public class AuthController {

    @Autowired
    private UtilizatorRepository utilizatorRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody Utilizator credentials) {
        Utilizator user = utilizatorRepo.findByNume(credentials.getNume());
        if (user == null || !user.verificaParola(credentials.getParola())) {
            throw new RuntimeException("Autentificare esuata");
        }

        return jwtUtil.generateToken(user.getNume());
    }

}
