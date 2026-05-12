package id.procurement.procurement_app.service;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;
import id.procurement.procurement_app.entity.EVendor;
import id.procurement.procurement_app.entity.Vendor;
import id.procurement.procurement_app.mapper.VendorMapper;
import id.procurement.procurement_app.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService{

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;

//    menyimpan data vendor baru ke database
    @Override
    public VendorResponse create(VendorRequest vendorRequest) {
        Vendor vendor = vendorMapper.toEntity(vendorRequest);
        vendor.setStatus(EVendor.DRAFT);
        return vendorMapper.toResponse(vendorRepository.save(vendor));
    }

//    mengambil data detail vendor berdasarkan id
    @Override
    public VendorResponse getById(String id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow();
        return vendorMapper.toResponse(vendor);
    }

//    mengambil data seluruh vendor default: ACTIVE, atau sesuai filter (INACTIVE / IN_REVIEW)
    @Override
    public Page<VendorResponse> getAll(EVendor status, Pageable pageable) {
        EVendor vendorStatus = (status == null) ? EVendor.ACTIVE : status;
        Page<Vendor> vendors = vendorRepository.findAllByStatus(vendorStatus, pageable);
        return vendors.map(vendorMapper::toResponse);
    }

//    memperbarui data vendor berdasarkan id dan set ke DRAFT
    @Override
    public VendorResponse update(String id, VendorRequest vendorRequest) {
        Vendor existingVendor = vendorRepository.findById(id).orElseThrow();
        vendorMapper.updateEntity(vendorRequest, existingVendor);
        existingVendor.setStatus(EVendor.DRAFT);
        Vendor savedVendor = vendorRepository.save(existingVendor);
        return vendorMapper.toResponse(savedVendor);
    }

//    memperbaharui status vendor di db
    @Transactional
    @Override
    public void updateStatus(String id, EVendor newStatus, String reason) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow();

        switch (newStatus) {
            case DRAFT -> handleDraft(vendor);
            case IN_REVIEW -> handleSubmit(vendor);
            case ACTIVE -> handleApprove(vendor);
            case RETURNED -> handleReturn(vendor, reason);
            case INACTIVE -> vendor.setRejectionDescription(reason);
        }

        vendor.setStatus(newStatus);
        vendorRepository.save(vendor);
    }

    private void handleDraft(Vendor vendor) {
        if (vendor.getStatus() != EVendor.INACTIVE && vendor.getStatus() != EVendor.RETURNED && vendor.getStatus() != EVendor.DRAFT)
            throw new IllegalStateException("data harus berstatus INACTIVE atau RETURNED");
    }

    private void handleSubmit(Vendor vendor) {
        if (vendor.getStatus() != EVendor.DRAFT) throw new IllegalStateException("data harus berstatus DRAFT!");
        vendor.setRejectionDescription(null);
    }

    private void handleApprove(Vendor vendor) {
        if (vendor.getStatus() != EVendor.IN_REVIEW) throw new IllegalStateException("data harus berstatus IN_REVIEW!");
        verifyDirector();
        vendor.setRejectionDescription(null);
    }

    private void handleReturn(Vendor vendor, String reason) {
        if (vendor.getStatus() != EVendor.IN_REVIEW) throw new IllegalStateException("data harus berstatus IN_REVIEW!");
        verifyDirector();
        vendor.setRejectionDescription(reason);
    }

    private void verifyDirector() {
        boolean isDirector = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream().anyMatch(
                        a -> a.getAuthority().equals("ROLE_DIRECTOR"));
        if (!isDirector) throw new AccessDeniedException("tidak terverfikasi sebagai direktur");
    }
}
