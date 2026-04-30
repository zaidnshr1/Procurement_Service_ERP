package id.procurement.procurement_app.controller;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;
import id.procurement.procurement_app.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<VendorResponse> createVendor(@RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(vendorService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER', 'DIRECTOR', 'STAFF')")
    public ResponseEntity<List<VendorResponse>> getAllVendor() {
        return ResponseEntity.ok(vendorService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER', 'DIRECTOR', 'STAFF')")
    public ResponseEntity<VendorResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(vendorService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable String id, @RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(vendorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<Void> deleteVendor(@PathVariable String id) {
        vendorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
