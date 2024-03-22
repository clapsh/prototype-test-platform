package gp.gameproto.service;

import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override // 사용자 이메일로 사용자의 정보를 가져오는 메서드 (id가 아닌 이메일을 이용하기로 결정했으므로) -> 인증용
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));// IllegalArgumentException(email)); // 적합하지 못한 인자가 넘어 옴
    }

}
