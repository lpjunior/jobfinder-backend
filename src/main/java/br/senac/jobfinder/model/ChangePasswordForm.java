package br.senac.jobfinder.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePasswordForm{
        @NotEmpty(message = "A senha atual é obrigatória")
        private String currentPassword;
        @NotEmpty(message = "A nova senha é obrigatória")
        private String newPassword;
        @NotEmpty(message = "É necessário confirmar a nova senha")
        private String confirmNewPassword;
}