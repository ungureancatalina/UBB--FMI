package ro.mpp2024.rest;

import ro.mpp2024.model.Cursa;
import ro.mpp2024.persistence.repository.CursaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curse")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CursaController {
    private final CursaRepository cursaRepo;

    public CursaController(CursaRepository cursaRepo) {
        this.cursaRepo = cursaRepo;
    }

    @PostMapping
    public ResponseEntity<Cursa> createCursa(@RequestBody Cursa cursa) {
        cursaRepo.adauga(cursa);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursa);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cursa> getCursaById(@PathVariable int id) {
        Cursa result = cursaRepo.findById(id);
        if (result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public List<Cursa> getAll() {
        return (List<Cursa>) cursaRepo.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cursa> updateCursa(@PathVariable int id, @RequestBody Cursa cursa) {
        Cursa result = cursaRepo.findById(id);
        if (result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        cursa.setId(id);
        cursaRepo.modifica(cursa);
        return ResponseEntity.ok(cursa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCursa(@PathVariable int id) {
        Cursa existing = cursaRepo.findById(id);
        if (existing == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        cursaRepo.sterge(id);
        return ResponseEntity.noContent().build();
    }
}
