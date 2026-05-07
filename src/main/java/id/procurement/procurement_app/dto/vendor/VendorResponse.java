package id.procurement.procurement_app.dto.vendor;

import id.procurement.procurement_app.entity.EVendor;

public record VendorResponse(
        String id,
        String name,
        String address,
        String phone,
        String email,
        String accountNumber,
        String npwp,
        EVendor status,
        String rejectionDescription
) {
}
