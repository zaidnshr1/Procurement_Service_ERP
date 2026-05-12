package id.procurement.procurement_app.exception;

public class VendorNotFoundException extends RuntimeException {
    public VendorNotFoundException(String id) {
        super("Vendor dengan ID " + id + " tidak ditemukan");
    }
}
