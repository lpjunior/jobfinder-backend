package br.senac.jobfinder.service;

import br.senac.jobfinder.model.*;
import br.senac.jobfinder.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registerNewUser(UserRegister registration) {
        // 1. Validação adicional (ex: confirmar senhas iguais)
        if (!registration.password().equals(registration.matchingPassword())) {
            throw new IllegalArgumentException("As senhas não correspondem");
        }

        // 2. Verifica se e-mail já existe
        if (userRepository.existsByEmail(registration.email())) {
            throw new IllegalStateException("Este email já está em uso");
        }

        // 3. Cria e popula User (entidade)
        User user = new User();
        user.setFirstName(registration.firstName());
        user.setLastName(registration.lastName());
        user.setEmail(registration.email());
        user.setPassword(passwordEncoder.encode(registration.password()));

        // 4. Salva no banco
        userRepository.save(user);
    }

    @Transactional
    public void editUser(UserEdit edit) {
        var user = userRepository.findByEmail(edit.email())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        user.setFirstName(edit.firstName());
        user.setLastName(edit.lastName());
        user.setEmail(edit.email());

        userRepository.save(user);
    }

    @Transactional
    public UserDto findByEmail(String email) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new UserDto(user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @Transactional
    public void updatePassword(@Valid String email, @Valid ChangePasswordForm form) {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if(!passwordEncoder.matches(form.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("A senha atual não corresponde");
        }

        if(form.getCurrentPassword().equals(form.getNewPassword())) {
            throw new IllegalStateException("A senha não pode ser a mesma que já está em uso");
        }

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
    }
}
