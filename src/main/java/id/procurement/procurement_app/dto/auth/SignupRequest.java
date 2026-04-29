package id.procurement.procurement_app.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SignupRequest(
        @NotBlank(message = "username harus diisi") @Size(min = 6, message = "username minimal 6 karakter")
        String username,
        @NotBlank(message = "password harus diisi") @Size(min = 8, message = "password minimal 8 karakter")
        String password,
        @Email(message = "perhatikan format email")
        String email,
        @NotEmpty(message = "role harus dipilih")
        Set<String> role
) {
}
