package id.procurement.procurement_app.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "username harus diisi") @Size(min = 6, message = "username minimal 6 karakter")
        String username,
        @NotBlank(message = "password harus diisi") @Size(min = 8, message = "password minimal 8 karakter")
        String password
) {
}
