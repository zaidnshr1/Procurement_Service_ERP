package id.procurement.procurement_app.controller;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;
import id.procurement.procurement_app.dto.vendor.VendorRevisionRequest;
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

//    membuat data vendor baru, setelah itu status masih DRAFT dan setelah itu submit untuk status review
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<VendorResponse> createVendor(@RequestBody @Valid VendorRequest request) {
        return ResponseEntity.ok(vendorService.create(request));
    }

//    khusus ketika sudah dalam bentuk DRAFT terus submit buat pengajuan review ke direksi
    @PostMapping("/{id}/submit")
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER')")
    public ResponseEntity<Void> submitForReview(@PathVariable String id) {
        vendorService.updateStatus(id, EVendor.IN_REVIEW, null);
        return ResponseEntity.noContent().build();
    }

//    khusus direksi untuk approve
    @PostMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    public ResponseEntity<Void> approveVendor(@PathVariable String id) {
        vendorService.updateStatus(id, EVendor.ACTIVE, null);
        return ResponseEntity.noContent().build();
    }

//    menampilkan data vendor. default status = ACTIVE (service layer) atau jika pakai parameter bisa untuk
//    filter supaya mendapatkan list data lebih spesifik berdasarkan status
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FINANCE_MANAGER', 'DIRECTOR', 'STAFF')")
    public ResponseEntity<Page<VendorResponse>> getAllVendor(@RequestParam(required = false) EVendor status,
                                                             @PageableDefault(size = 10, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(vendorService.getAll(status, pageable));
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

//    menonaktifkan vendor
    @PostMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'FINANCE_MANAGER')")
    public ResponseEntity<Void> deactivateVendor(@PathVariable String id,
                                                 @RequestBody @Valid VendorRevisionRequest reason) {
        vendorService.updateStatus(id, EVendor.INACTIVE, reason.description());
        return ResponseEntity.noContent().build();
    }

//    mengaktifkan vendor melalui DRAFT terlebih dahulu selanjutnya ke IN_REVIEW
    @PostMapping("{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF', 'FINANCE_MANAGER')")
    public ResponseEntity<Void> activateVendor(@PathVariable String id,
                                               @RequestBody @Valid VendorRevisionRequest reason) {
        vendorService.updateStatus(id, EVendor.DRAFT, reason.description());
        return ResponseEntity.noContent().build();
    }

//    direktur menolak data vendor
    @PostMapping("{id}/reject")
    @PreAuthorize("hasAnyRole('DIRECTOR')")
    public ResponseEntity<Void> rejectVendor(@PathVariable String id,
                                             @RequestBody @Valid VendorRevisionRequest reason) {
        vendorService.updateStatus(id, EVendor.RETURNED, reason.description());
        return ResponseEntity.noContent().build();
    }


}
