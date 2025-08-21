package br.senac.jobfinder.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id @GeneratedValue Long id;
    @Column(unique = true, nullable = false) private String email;
    @Column(nullable = false) private String password;
    @Column(nullable = false) private String firstName;
    @Column(nullable = false) private String lastName;
    @CreationTimestamp private LocalDateTime createdAt;
}
