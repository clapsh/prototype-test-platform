package gp.gameproto.controller;

import gp.gameproto.db.entity.Category;
import gp.gameproto.db.entity.Test;
import gp.gameproto.dto.*;
import gp.gameproto.service.TestService;
import gp.gameproto.service.api.RecommendGameApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/proto")
public class TestApiController {
    private final TestService testService;
    private final RecommendGameApiClient recentPlayedGameApiClient;

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
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetTestResponse(test));
    }

    // 프로젝트 수정
    @PutMapping("/{testId}")
    public ResponseEntity<Long> updateTest (@PathVariable("testId") Long id, @RequestParam("email") String email, @RequestBody UpdateTestRequest request){
        Test test = testService.update(request, email, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(test.getId());
    }


    // 프로젝트 다운로드 링크 제공
    @GetMapping("/download/{testId}")
    public ResponseEntity<String> findTestLinkById (@PathVariable("testId") Long id){
        Test test = testService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(test.getDownloadLink());
    }


    // 프로젝트 제작시 기존 프로젝트 불러오기
    @GetMapping("/build/{testId}")
    public ResponseEntity<GetExistingTestResponse> findExistingTestById (@PathVariable("testId") Long id){
        Test test = testService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetExistingTestResponse(test));
    }

    // 유저별 최근 제작 게임 리스트로 불러오기 (프로젝트 제작시)
    @GetMapping("build/list")
    public ResponseEntity<GetRecentUserTestsResponse> findRecentProjects (@RequestParam("email") String email){
        List<Test> tests = testService.findTestOfRecentGames(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetRecentUserTestsResponse(tests));
    }

    // (공통) 최근 제작된 게임(테스트) 5개 가져오기
    @GetMapping("main/recent")
    public ResponseEntity<GetRecentTestsResponse> findRecentTests (){
        List<Test> tests = testService.findRecentGames();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetRecentTestsResponse(tests));
    }

    // (공통) 카테고리 별 게임(테스트) 불러오기
    @GetMapping("main/games/{category}")
    public ResponseEntity<GetCategorizedTestsResponse> findCategorizedTests (@PathVariable("category") Category category){
        List<Test> tests = testService.findCategorizedTests(category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetCategorizedTestsResponse(tests));
    }

    // 유저별 제작한 게임(테스트) 리스트 불러오기
    @GetMapping("/mypage/games")
    public ResponseEntity<GetUserTestsResponse> findUserTests (@RequestParam("email") String email){
        List<Test> tests = testService.findUserTestsOfGames(email);
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

    // 찜 Top10 개 프로젝트 가져오기
    @GetMapping("top10")
    public ResponseEntity<GetTop10Response> getTop10Games (){
        List<Test> tests = testService.findTop10Games();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetTop10Response(tests));
    }

    // (게시자) 게임의 모든 리뷰 회차 알려주기
    @GetMapping("/review/round/{gameId}")
    public ResponseEntity<GetAllRoundsResponse> getRoundsOfGame (@PathVariable("gameId")Long gameId){
        List<Integer> roundList = testService.findTestRoundsOfGame(gameId);
        roundList = roundList.stream().distinct().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetAllRoundsResponse(roundList));
    }

    // (게시자) testId로 게임의 모든 리뷰 회차 알려주기
    @GetMapping("/round/{testId}")
    public ResponseEntity<GetAllRoundsResponse> getAllRoundsOfGameByTestId (@PathVariable("testId")Long testId){
        Long gameId = testService.findById(testId).getGame().getId();
        List<Integer> roundList = testService.findTestRoundsOfGame(gameId);
        roundList = roundList.stream().distinct().collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetAllRoundsResponse(roundList));
    }

    // 테스트 참여하기
    @GetMapping("/engage/{testId}")
    public ResponseEntity<String> updateUsersCntNPlayedTestList(@PathVariable("testId")Long testId,
                                                              @RequestParam("email")String email){
        String message = testService.updateEngageState(testId, email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(message);
    }

    //테스트 참여 중인지 확인
    @GetMapping("/isEngaging/{testId}")
    public ResponseEntity<Boolean> findIsEngaging(@PathVariable("testId")Long testId,
                                                                @RequestParam("email")String email){
        Boolean isEngaging = testService.findEngageState(testId, email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(isEngaging);
    }


    //AI 추천 게임
    @GetMapping("/ai")
    public ResponseEntity<GetAIRecommendResponse> getAIRecommend(@RequestParam("email")String email){
        List<Test> aiRecommendTests = recentPlayedGameApiClient.findAIRecommendTests(email);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetAIRecommendResponse(aiRecommendTests));
    }

    // 게임 검색
    @GetMapping("search")
    public ResponseEntity<GetTestSearchResponse> getTestKeywordSearch(@RequestParam("keyword") String keyword){
        List<Test> keywordTests = testService.findTestByKeyword(keyword);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new GetTestSearchResponse(keywordTests));
    }

    //프로젝트 모집인원
    @GetMapping("/getRecruitedCount/{testId}")
    public ResponseEntity<Integer> getRecruitedUserCount(@PathVariable("testId")Long testId){
        Integer count = testService.findById(testId).getUsersCnt();

        return ResponseEntity.status(HttpStatus.OK)
                .body(count);
    }

    // 참여한 프로젝트 리스트
    @GetMapping("/engagedList")
    public ResponseEntity<List<Test>> getEnagedTestList(@RequestParam("email")String email){
        List<Long> testIds = testService.findEngagedTestIds(email);
        List<Test> engagedTests = new ArrayList<>();
        for(Long testId: testIds){
            Test dto = testService.findById(testId);
            engagedTests.add(dto);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(engagedTests);
    }
}
