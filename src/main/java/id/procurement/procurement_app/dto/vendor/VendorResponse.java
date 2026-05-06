package id.procurement.procurement_app.dto.vendor;

public record VendorResponse(
        String id,
        String name,
        String address,
        String phone,
        String email,
        String accountNumber,
        String npwp,
        Boolean status
) {
}
