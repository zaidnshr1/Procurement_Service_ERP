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
        Page<Vendor> vendors = vendorRepository.findAllByStatus(status, pageable);
        return vendors.map(vendorMapper::toResponse);
    }

//    memperbarui data vendor berdasarkan id
    @Override
    public VendorResponse update(String id, VendorRequest vendorRequest) {
        Vendor existingVendor = vendorRepository.findById(id).orElseThrow();
        vendorMapper.updateEntity(vendorRequest, existingVendor);
        Vendor savedVendor = vendorRepository.save(existingVendor);
        return vendorMapper.toResponse(savedVendor);
    }

//    memperbarui data status vendor isActive = false
    @Override
    public void updateStatus(String id, EVendor newStatus) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow();

        if (newStatus ==  EVendor.ACTIVE && vendor.getStatus() == EVendor.IN_REVIEW) {
            vendor.setStatus(EVendor.ACTIVE);
            vendorRepository.save(vendor);
        } else if (newStatus == EVendor.INACTIVE) {
            vendor.setStatus(EVendor.INACTIVE);
            vendorRepository.save(vendor);
        } else if (newStatus == EVendor.IN_REVIEW) {
            vendor.setStatus(EVendor.IN_REVIEW);
            vendorRepository.save(vendor);
        }
    }
}
