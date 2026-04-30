package id.procurement.procurement_app.service;

import id.procurement.procurement_app.dto.auth.JwtResponse;
import id.procurement.procurement_app.dto.auth.LoginRequest;
import id.procurement.procurement_app.dto.auth.MessageResponse;
import id.procurement.procurement_app.dto.auth.SignupRequest;
import id.procurement.procurement_app.entity.ERole;
import id.procurement.procurement_app.entity.Role;
import id.procurement.procurement_app.entity.User;
import id.procurement.procurement_app.repository.RoleRepository;
import id.procurement.procurement_app.repository.UserRepository;
import id.procurement.procurement_app.security.jwt.JwtUtils;
import id.procurement.procurement_app.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(role -> role.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(
                jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles
        );
    }

    public MessageResponse register(SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.username())) {
            throw new RuntimeException("Error: username is already taken");
        }

        if (userRepository.existsByEmail(signupRequest.email())) {
            throw new RuntimeException("Error: email is already used");
        }

        User user = User.builder()
                        .username(signupRequest.username())
                        .email(signupRequest.email())
                        .password(passwordEncoder.encode(signupRequest.password()))
                        .build();

        Set<String> strRole = signupRequest.role();
        Set<Role> roles = new HashSet<>();

        if (strRole == null || strRole.isEmpty()) {
            roles.add(getRole(ERole.ROLE_STAFF));
        } else {
            strRole.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin" -> roles.add(getRole(ERole.ROLE_ADMIN));
                    case "finance_manager" -> roles.add(getRole(ERole.ROLE_FINANCE_MANAGER));
                    case "director" -> roles.add(getRole(ERole.ROLE_DIRECTOR));
                    default -> roles.add(getRole(ERole.ROLE_STAFF));
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("Registrasi berhasil");
    }

    private Role getRole(ERole role) {
        return roleRepository.findByRoleName(role)
                .orElseThrow(() -> new RuntimeException("Error: role " + role + " is not found"));
    }
}
