package pl.javadev.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.javadev.user.User;
import pl.javadev.user.UserRepository;
import pl.javadev.userRole.UserRole;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundOne;
        boolean isOk = false;
        org.springframework.security.core.userdetails.User userDetails;
        if (!username.contains("@")) {
            foundOne = userRepository.findByIndexNumber(username);
        } else {
            foundOne = userRepository.findByEmail(username);
            isOk = true;
        }

        if (foundOne.isEmpty())
            throw new UsernameNotFoundException("User not found");

        User user = foundOne.get();
        if (isOk) {
            userDetails =
                    new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getPassword(),
                            convertAuthorities(user.getRoles())
                    );
        } else
            userDetails =
                    new org.springframework.security.core.userdetails.User(
                            user.getIndexNumber(),
                            user.getPassword(),
                            convertAuthorities(user.getRoles())
                    );
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,
                convertAuthorities(user.getRoles()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(authentication);
        System.err.println("HAHAHAHAH");
        return userDetails;
    }

    private Set<GrantedAuthority> convertAuthorities(Set<UserRole> userRoles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserRole ur : userRoles) {
            authorities.add(new SimpleGrantedAuthority(ur.getName()));
        }
        return authorities;
    }
}
