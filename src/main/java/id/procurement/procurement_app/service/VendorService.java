package id.procurement.procurement_app.service;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;
import id.procurement.procurement_app.entity.EVendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VendorService {
    VendorResponse create(VendorRequest vendorRequest);
    VendorResponse getById(String id);
    Page<VendorResponse> getAll(EVendor status, Pageable pageable);
    VendorResponse update(String id, VendorRequest vendorRequest);
    void updateStatus(String id, EVendor newStatus);
}
