package id.procurement.procurement_app.service;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;

import java.util.List;

public interface VendorService {
    VendorResponse create(VendorRequest vendorRequest);
    VendorResponse getById(String id);
    List<VendorResponse> getAll();
    VendorResponse update(String id, VendorRequest vendorRequest);
    void delete(String id);
}
