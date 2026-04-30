package id.procurement.procurement_app.service;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;
import id.procurement.procurement_app.entity.Vendor;
import id.procurement.procurement_app.mapper.VendorMapper;
import id.procurement.procurement_app.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VendorServiceImpl implements VendorService{

    private final VendorRepository vendorRepository;
    private final VendorMapper vendorMapper;


    @Override
    public VendorResponse create(VendorRequest vendorRequest) {
        Vendor vendor = vendorMapper.toEntity(vendorRequest);
        return vendorMapper.toResponse(vendorRepository.save(vendor));
    }

    @Override
    public VendorResponse getById(String id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow();
        return vendorMapper.toResponse(vendor);
    }

    @Override
    public List<VendorResponse> getAll() {
        List<Vendor> vendors = vendorRepository.findAllByIsActiveTrue();
        return vendors.stream().map(vendorMapper::toResponse).toList();
    }

    @Override
    public VendorResponse update(String id, VendorRequest vendorRequest) {
        Vendor existingVendor = vendorRepository.findById(id).orElseThrow();
        vendorMapper.updateEntity(vendorRequest, existingVendor);
        Vendor savedVendor = vendorRepository.save(existingVendor);
        return vendorMapper.toResponse(savedVendor);
    }

    @Override
    public void delete(String id) {
        Vendor vendor = vendorRepository.findById(id).orElseThrow();
        vendor.setIsActive(false);
        vendorRepository.save(vendor);
    }
}
