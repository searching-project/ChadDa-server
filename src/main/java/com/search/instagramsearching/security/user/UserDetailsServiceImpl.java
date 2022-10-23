package com.search.instagramsearching.security.user;


import com.search.instagramsearching.CacheKey;
import com.search.instagramsearching.entity.Users;
import com.search.instagramsearching.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UsersRepository userRepository;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByProfileName(username).orElse(null);
        if (user == null) {
            return null;
        }else{
            UserDetailsImpl userDetails = new UserDetailsImpl();
            userDetails.setUsers(user);
            return userDetails;
        }

    }
}
