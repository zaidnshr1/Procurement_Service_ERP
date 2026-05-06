package id.procurement.procurement_app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    roles --> ERole
    @Enumerated(EnumType.STRING)
    @Column(length = 25, unique = true, nullable = false)
    private ERole roleName;
}
