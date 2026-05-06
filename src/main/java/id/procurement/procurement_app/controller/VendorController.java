package id.procurement.procurement_app.controller;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;
import id.procurement.procurement_app.entity.EVendor;
import id.procurement.procurement_app.service.VendorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

//    buat data vendor
//    menampilkan data vendor. default status = ACTIVE (service layer) atau jika pakai parameter bisa untuk
//    filter supaya mendapatkan list data lebih spesifik berdasarkan status
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER', 'DIRECTOR', 'STAFF')")
    public ResponseEntity<Page<VendorResponse>> getAllVendor(@RequestParam(required = false) EVendor status,
                                                             @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(vendorService.getAll(status, pageable));
    }

//    membuat data vendor baru, setelah itu status masih IN_REVIEW dan butuh approval direksi untuk pengaktia
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<VendorResponse> createVendor(@RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(vendorService.create(request));
    }

//    lihat detail data vendor dengan parameter id vendor
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER', 'DIRECTOR', 'STAFF')")
    public ResponseEntity<VendorResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(vendorService.getById(id));
    }

//    update data vendor
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<VendorResponse> updateVendor(@PathVariable String id, @RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(vendorService.update(id, request));
    }

//    update status vendor
    @PostMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<Void> updateStatus(@PathVariable String id,
                                             @RequestParam EVendor targetStatus) {
        vendorService.updateStatus(id, targetStatus);
        return ResponseEntity.noContent().build();
    }


}
