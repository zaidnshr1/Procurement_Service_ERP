package id.procurement.procurement_app.repository;

import id.procurement.procurement_app.entity.EVendor;
import id.procurement.procurement_app.entity.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, String> {
    Page<Vendor> findAllByStatus(EVendor status, Pageable pageable);
}
