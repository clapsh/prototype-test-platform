package gp.gameproto.service;

import gp.gameproto.db.entity.Dibs;
import gp.gameproto.db.entity.Test;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.DibsRepository;
import gp.gameproto.db.repository.TestRepository;
import gp.gameproto.db.repository.UserRepository;
import gp.gameproto.dto.DibsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DibsService {
    private final DibsRepository dibsRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;

    @Transactional
    public void save(DibsRequest dibsRequestDTO)throws Exception{
        //사용자가 존재하는지 확인
        User user = userRepository.findByEmail(dibsRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        //test가 존재하는지 확인
        Test test = testRepository.findById(dibsRequestDTO.getTestId())
                .orElseThrow(()-> new IllegalArgumentException("not found "+dibsRequestDTO.getTestId()));

        // 이미 좋아요가 있는 경우
        if (dibsRepository.findByUserAndTest(dibsRequestDTO.getEmail(), dibsRequestDTO.getTestId()).isPresent()){
            // 409 에러
            throw new Exception();
        }

        Dibs dibs = Dibs.builder()
                .user(user)
                .test(test)
                .createdAt(LocalDateTime.now())
                .build();

        test.addDibs(dibs);

        dibsRepository.save(dibs);
    }

    @Transactional
    public Boolean delete(DibsRequest dibsRequestDTO){
        //사용자가 존재하는지 확인
        User user = userRepository.findByEmail(dibsRequestDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        //test가 존재하는지 확인
        Test test = testRepository.findById(dibsRequestDTO.getTestId())
                .orElseThrow(()-> new IllegalArgumentException("not found "+dibsRequestDTO.getTestId()));

        // 좋아요가 없는 경우
        Dibs dibs = dibsRepository.findByUserAndTest(dibsRequestDTO.getEmail(), dibsRequestDTO.getTestId())
                .orElseThrow(()-> new IllegalArgumentException("not found dibs"));

        if (test.deleteDibs(dibs)){
            dibsRepository.delete(dibs);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    // 유저가 찜한 게임 리스트
    @Transactional(readOnly = true)
    public List<Dibs> findUserDibs(String email){
        //사용자가 존재하는지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 좋아요가 존재하는지 확인
        List<Dibs> dibsList = dibsRepository.findByUserEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("not found dibs"));

        return dibsList;
    }

}
