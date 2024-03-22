package gp.gameproto.controller;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import gp.gameproto.dto.*;
import gp.gameproto.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/proto")
public class TestApiController {
    private final TestService testService;

    // 프로젝트 등록
    @PostMapping("/{gameId}")
    public ResponseEntity<String> addTest (@RequestParam("email") String email,@PathVariable("gameId") Long gameId,
                                           @RequestBody AddTestRequest request){
        String savedTestId = testService.save(request,gameId ,email);

        return ResponseEntity.status(HttpStatus.OK)
                .body(savedTestId);
    }

    // 프로젝트 조회
    @GetMapping("/{testId}")
    public ResponseEntity<GetTestResponse> findTestById (@PathVariable("testId") Long id){
        Test test = testService.findById(id);
        // test가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetTestResponse(test));
    }

    // 프로젝트 수정
    @PutMapping("/{testId}")
    public ResponseEntity<Test> updateTest (@PathVariable("testId") Long id, @RequestParam("email") String email, @RequestBody UpdateTestRequest request){
        Test test = testService.update(request, email, id);

        // test가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(test);
    }


    // 프로젝트 다운로드 링크 제공
    @GetMapping("/download/{testId}")
    public ResponseEntity<String> findTestLinkById (@PathVariable("testId") Long id){
        Test test = testService.findById(id);
        // test가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(test.getDownloadLink());
    }


    // 프로젝트 제작시 기존 프로젝트 불러오기
    @GetMapping("/build/{testId}")
    public ResponseEntity<GetExistingTestRequest> findExistingTestById (@PathVariable("testId") Long id){
        Test test = testService.findById(id);
        // test가 삭제되었을 경우
        //if (test.getDeleted() == 'Y')

        // test가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetExistingTestRequest(test));
    }

    // 유저별 최근 제작 게임 리스트로 불러오기 (프로젝트 제작시)
    @GetMapping("build/list")
    public ResponseEntity<GetRecentUserTestsResponse> findRecentProjects (@RequestParam("email") String email){
        List<Test> tests = testService.findTestOfRecentGames(email);
        // tests가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetRecentUserTestsResponse(tests));
    }

    // (공통) 최근 제작된 게임(테스트) 5개 가져오기
    @GetMapping("main/recent")
    public ResponseEntity<GetRecentTestsResponse> findRecentTests (){
        List<Test> tests = testService.findRecentGames();
        // tests가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetRecentTestsResponse(tests));
    }

    // (공통) 카테고리 별 게임(테스트) 불러오기
    @GetMapping("main/games/{category}")
    public ResponseEntity<GetCategorizedTestsResponse> findCategorizedTests (@PathVariable("category")Category category){
        List<Test> tests = testService.findCategorizedTests(category);
        // tests가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetCategorizedTestsResponse(tests));
    }

    // 유저별 제작한 게임(테스트) 리스트 불러오기
    @GetMapping("/mypage/games")
    public ResponseEntity<GetUserTestsResponse> findUserTests (@RequestParam("email") String email){
        List<Test> tests = testService.findUserTestsOfGames(email);
        // tests가 empty일 경우 예외처리
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetUserTestsResponse(tests));
    }

    // 테스트 삭제
    @DeleteMapping("/{testId}")
    public ResponseEntity<String> deleteTest (@PathVariable("testId") Long id, @RequestParam("email") String email){
        String message = testService.delete(email,id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }
}
