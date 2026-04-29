package id.procurement.procurement_app.security.services;

import id.procurement.procurement_app.entity.User;
import id.procurement.procurement_app.exception.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override @Transactional
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User (" + identifier + ") is not found"));

        return UserDetailsImpl.build(user);
    }

}
