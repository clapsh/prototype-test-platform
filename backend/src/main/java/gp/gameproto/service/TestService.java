package gp.gameproto.service;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Game;
import gp.gameproto.db.entity.Test;
import gp.gameproto.db.entity.User;
import gp.gameproto.db.repository.GameRepository;
import gp.gameproto.db.repository.TestRepository;
import gp.gameproto.db.repository.UserRepository;
import gp.gameproto.dto.AddTestRequest;
import gp.gameproto.dto.UpdateTestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TestService {
    private final TestRepository testRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    // 테스트 저장하기
    @Transactional
    public String save (AddTestRequest request, Long gameId, String email){
        // 사용자가 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 게임이 존재하는지 확인 (게임은 이전에 저장했기때문에 이게 반드시 통과해야함)
        Game game = gameRepository.findById(gameId)
                .orElseThrow(()-> new UsernameNotFoundException("게임이 존재하지 않습니다."));

        // 테스트가 존재하는 경우 (게임 이름과 회차가 같은 경우)
        if(testRepository.findByNameAndRound(game.getName(), request.getRound()).isPresent()){
            return "테스트가 존재합니다.";
        }

        Test test = request.toEntity();
        // 연관관계 매핑
        test.mappingGame(game);
        test.mappingUser(user);
        user.addTest(test);

        // 테스트 저장
        Test savedTest = testRepository.saveTest(test);
        return ""+savedTest.getId();
    }

    // 테스트 id로 가져오기
    @Transactional(readOnly = true)
    public Test findById (Long id){
        Test test = testRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ id));
        return test;
    }

    // 테스트 업데이트
    @Transactional
    public Test update(UpdateTestRequest request, String email, Long testId) {
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 테스트 찾기
        Test test = testRepository.findById(testId)
            .orElseThrow(()-> new IllegalArgumentException("not found: "+ testId));

        // 테스트 수정 권한이 없는 경우
        if (test.getUser().getEmail() != email){ // 예외처리??
            new UsernameNotFoundException("수정 권한이 없습니다.");
        }

        return test.update(request);
    }

    // 유저별 최근 제작된 게임의 test 가져오기
    @Transactional(readOnly = true)
    public List<Test> findTestOfRecentGames(String email){
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 유저별 최근 제작 테스트 가져오기
        List<Test> tests = testRepository.findUserRecentGameList(email)
                .orElseThrow(()-> new IllegalArgumentException("not found"+ email));

        // response dto에 맞는 list로 변환 및 반환
        return tests;
    }

    // (공통) 최근 제작된 게임(테스트) 5개 가져오기
    @Transactional(readOnly = true)
    public List<Test> findRecentGames(){
        // 최근 제작 테스트 (게임 중복 X) 가져오기
        List<Test> tests = testRepository.findRecentGameList()
                .orElseThrow(()-> new IllegalArgumentException("not found"));
        return tests;
    }

    // (공통) 카테고리 별 게임(테스트) 불러오기
    @Transactional(readOnly = true)
    public List<Test> findCategorizedTests(Category category){
        List<Test> tests = testRepository.findCategorizedGameList(category)
                .orElseThrow(()->new IllegalArgumentException("not found"+ category));
        return tests;
    }

    // 유저별 제작한 게임(테스트) 리스트 불러오기
    @Transactional(readOnly = true)
    public List<Test> findUserTestsOfGames(String email){
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 유저별 최근 제작 테스트 가져오기
        List<Test> tests = testRepository.findUserGameList(email)
                .orElseThrow(()-> new IllegalArgumentException("not found"+ email));

        // response dto에 맞는 list로 변환 및 반환
        return tests;
    }

    // 테스트 삭제하기
    @Transactional
    public String delete(String email, Long testId){
        // 사용자 존재하는지 확인
        Optional<User> byEmail = userRepository.findByEmail(email);
        User user = byEmail.orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 테스트 찾기
        Test test = testRepository.findById(testId)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ testId));

        // 테스트 삭제 권한이 없는 경우
        if (test.getUser().getEmail() != email){ // 예외처리??
            new UsernameNotFoundException("삭제 권한이 없습니다.");
        }

        // 테스트 삭제
        test.delete();
        return "삭제가 완료되었습니다.";
    }
}
