package id.procurement.procurement_app.dto.vendor;

import jakarta.validation.constraints.NotBlank;

public record VendorRevisionRequest(
        @NotBlank(message = "deskripsi pengajuan revisi harus diisi")
        String description
) {
}
