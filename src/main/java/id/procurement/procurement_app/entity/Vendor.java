package id.procurement.procurement_app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vendors")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vendor {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    private String address;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String accountNumber;

    private String npwp;

//    Builder.Default supaya nilai isActive tidak null
//    vendors --> EVendor untuk mencetak status
    @Enumerated(EnumType.STRING)
    private EVendor status;

    private String rejectionDescription;
}
