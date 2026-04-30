package id.procurement.procurement_app.dto.vendor;

public record VendorResponse(
        String id,
        String name,
        String address,
        String phone,
        String email,
        Boolean isActive
) {
}
