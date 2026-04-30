package id.procurement.procurement_app.dto.vendor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VendorRequest(
        @NotBlank(message = "nama vendor harus diisi")
        String name,
        String address,
        @NotBlank(message = "nomor telepon vendor harus diisi")
        String phone,
        @Email(message = "perhatikan format email")
        String email
) {
}
