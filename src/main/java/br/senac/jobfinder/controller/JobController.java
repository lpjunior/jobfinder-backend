package br.senac.jobfinder.controller;

import br.senac.jobfinder.model.Job;
import br.senac.jobfinder.repository.JobRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "*") // Libera CORS para testes
public class JobController {

    private final JobRepository jobRepository;

    public JobController(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @GetMapping
    public List<Job> list() {
        return jobRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> find(@PathVariable Long id) {
        return jobRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Job create(@RequestBody Job job) {
        job.setPostedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> update(@PathVariable Long id, @RequestBody Job job) {
        return jobRepository.findById(id).map(existing -> {
            existing.setTitle(job.getTitle());
            existing.setDescription(job.getDescription());
            existing.setCompany(job.getCompany());
            existing.setLocation(job.getLocation());
            existing.setType(job.getType());
            return ResponseEntity.ok(jobRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
