package id.procurement.procurement_app.dto.auth;

import java.util.List;

public record JwtResponse(
        String token,
        String id,
        String username,
        String email,
        List<String> roles
) {
}
