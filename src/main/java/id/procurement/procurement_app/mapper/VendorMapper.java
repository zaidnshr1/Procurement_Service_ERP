package id.procurement.procurement_app.mapper;

import id.procurement.procurement_app.dto.vendor.VendorRequest;
import id.procurement.procurement_app.dto.vendor.VendorResponse;
import id.procurement.procurement_app.entity.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VendorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    Vendor toEntity(VendorRequest vendorRequest);
    VendorResponse toResponse(Vendor vendor);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    void updateEntity(VendorRequest vendorRequest, @MappingTarget Vendor vendor);
}
