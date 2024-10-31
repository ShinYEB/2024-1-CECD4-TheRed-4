package com.thered.stocksignal.service.scenario;


import com.thered.stocksignal.app.dto.ScenarioDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ConditionResponseDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ScenarioRequestDto;
import com.thered.stocksignal.app.dto.ScenarioDto.ScenarioResponseDto;
import com.thered.stocksignal.app.dto.StockDto;
import com.thered.stocksignal.app.dto.TradeDto;
import com.thered.stocksignal.app.dto.kis.KisSocketDto;
import com.thered.stocksignal.config.WebSocketHandler;
import com.thered.stocksignal.domain.entity.Company;
import com.thered.stocksignal.domain.entity.Scenario;
import com.thered.stocksignal.domain.entity.ScenarioCondition;
import com.thered.stocksignal.domain.entity.User;
import com.thered.stocksignal.domain.enums.BuysellType;
import com.thered.stocksignal.domain.enums.MethodType;
import com.thered.stocksignal.domain.enums.OrderType;
import com.thered.stocksignal.repository.CompanyRepository;
import com.thered.stocksignal.repository.ScenarioConditionRepository;
import com.thered.stocksignal.repository.ScenarioRepository;
import com.thered.stocksignal.repository.UserRepository;
import com.thered.stocksignal.service.company.CompanyService;
import com.thered.stocksignal.app.dto.StockDto.CurrentPriceResponseDto;
import com.thered.stocksignal.service.trade.TradeService;
import com.thered.stocksignal.service.user.UserAccountService;
import com.thered.stocksignal.service.user.UserAccountServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScenarioServiceImpl implements ScenarioService {

    private final ScenarioRepository scenarioRepository;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ScenarioConditionRepository scenarioConditionRepository;
    private final WebSocketHandler webSocketHandler;
    private final UserAccountService userAccountService;
    private final TradeService tradeService;

    public List<ScenarioResponseDto> getScenario(Long userId) {
        List<Scenario> scenarios = scenarioRepository.findByUserId(userId);

        List<ScenarioResponseDto> scenarioList = new ArrayList<>();

        for (Scenario scenario : scenarios) {
            Long currentPrice = companyService.findCurrentPriceByCode(scenario.getCompany().getCompanyCode(), userId)
                    .map(CurrentPriceResponseDto::getCurrentPrice)
                    .orElse(null); // current price 정보를 찾을 수 없음

            ScenarioResponseDto responseDto = ScenarioResponseDto.builder()
                    .scenarioId(scenario.getId())
                    .scenarioName(scenario.getScenarioName())
                    .companyName(scenario.getCompany().getCompanyName())
                    .initialPrice(scenario.getInitialPrice())
                    .currentPrice(currentPrice)
                    .build();

            scenarioList.add(responseDto);
        }

        return scenarioList;
    }

    public boolean createScenario(Long userId, ScenarioRequestDto newScenario) {

        Optional<Company> company = companyRepository.findByCompanyName(newScenario.getCompanyName());
        Optional<User> user = userRepository.findById(userId);

        if(company.isEmpty()) return false;
        if(user.isEmpty()) return false;

        userAccountService.refreshKisSocketKey(userId);

        // 웹소켓 연결
        KisSocketDto.Header header = KisSocketDto.Header.builder()
                .approval_key(userRepository.findById(userId).get().getSocketKey())
                .custtype("P")
                .tr_type("1")
                .content_type("utf-8")
                .build();

        KisSocketDto.Input input = KisSocketDto.Input.builder()
                .tr_id("H0STASP0")
                .tr_key(company.get().getCompanyCode())
                .build();

        KisSocketDto.Body body = KisSocketDto.Body.builder()
                .input(input)
                .build();

        KisSocketDto.KisSocketRequestDto request = KisSocketDto.KisSocketRequestDto.builder()
                .header(header)
                .body(body)
                .build();

        try{
            webSocketHandler.subscribeStockInfo(userId, request);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 시나리오 객체 생성
        Scenario scenario = Scenario.builder()
                .scenarioName(newScenario.getScenarioName())
                .company(company.get())
                .initialPrice(newScenario.getInitialPrice())
                .user(user.get())
                .build();

        // 시나리오 저장
        scenario = scenarioRepository.save(scenario);

        // 조건 객체 생성
        ScenarioCondition condition = ScenarioCondition.builder()
                .scenario(scenario)
                .buysellType(newScenario.getBuysellType())
                .methodType(newScenario.getMethodType())
                .targetPrice1(newScenario.getTargetPrice1())
                .targetPrice2(newScenario.getTargetPrice2())
                .targetPrice3(newScenario.getTargetPrice3())
                .targetPrice4(newScenario.getTargetPrice4())
                .quantity(newScenario.getQuantity())
                .build();

        // 조건 저장
        scenarioConditionRepository.save(condition);

        return true;
    }

    public boolean deleteScenario(Long userId, Long scenarioId) {

        Optional<Scenario> scenario = scenarioRepository.findByIdAndUserId(scenarioId, userId);

        // 시나리오가 존재하지 않으면 삭제 실패
        if (scenario.isEmpty()) {
            return false;
        }

        String companyCode = scenario.get().getCompany().getCompanyCode();

        // 시나리오와 관련된 조건 삭제
        scenarioConditionRepository.deleteByScenario(scenario.get());

        // 시나리오 삭제
        scenarioRepository.delete(scenario.get());

        userAccountService.refreshKisSocketKey(userId);

        // 웹소켓에서 해당 종목 연결 해제
        KisSocketDto.Header header = KisSocketDto.Header.builder()
                .approval_key(userRepository.findById(userId).get().getSocketKey())
                .custtype("P")
                .tr_type("2")
                .content_type("utf-8")
                .build();

        KisSocketDto.Input input = KisSocketDto.Input.builder()
                .tr_id("H0STASP0")
                .tr_key(companyCode)
                .build();

        KisSocketDto.Body body = KisSocketDto.Body.builder()
                .input(input)
                .build();

        KisSocketDto.KisSocketRequestDto request = KisSocketDto.KisSocketRequestDto.builder()
                .header(header)
                .body(body)
                .build();

        try{
            webSocketHandler.subscribeStockInfo(userId, request);
        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    public List<ConditionResponseDto> getConditions(Long userId, Long scenarioId) {
        List<ScenarioCondition> conditions = scenarioConditionRepository.findByScenarioId(scenarioId);

        List<ConditionResponseDto> conditionList = new ArrayList<>();

        for (ScenarioCondition condition : conditions) {
            Long currentPrice = companyService.findCurrentPriceByCode(condition.getScenario().getCompany().getCompanyCode(), userId)
                    .map(CurrentPriceResponseDto::getCurrentPrice)
                    .orElse(null); // current price 정보를 찾을 수 없음

            ConditionResponseDto responseDto = ConditionResponseDto.builder()
                    .conditionId(condition.getId())
                    .buysellType(condition.getBuysellType())
                    .methodType(condition.getMethodType())
                    .initialPrice(condition.getScenario().getInitialPrice())
                    .currentPrice(currentPrice)
                    .targetPrice1(condition.getTargetPrice1())
                    .targetPrice2(condition.getTargetPrice2())
                    .targetPrice3(condition.getTargetPrice3())
                    .targetPrice4(condition.getTargetPrice4())
                    .quantity(condition.getQuantity())
                    .build();

            conditionList.add(responseDto);
        }

        return conditionList;
    }

    public boolean addCondition(Long userId, ScenarioDto.ConditionRequestDto newCondition){
        Optional<Scenario> scenario = scenarioRepository.findById(newCondition.getScenarioId());

        if(scenario.isEmpty()) return false;

        // 조건 객체 생성
        ScenarioCondition condition = ScenarioCondition.builder()
                .scenario(scenario.get())
                .buysellType(newCondition.getBuysellType())
                .methodType(newCondition.getMethodType())
                .targetPrice1(newCondition.getTargetPrice1())
                .targetPrice2(newCondition.getTargetPrice2())
                .targetPrice3(newCondition.getTargetPrice3())
                .targetPrice4(newCondition.getTargetPrice4())
                .quantity(newCondition.getQuantity())
                .build();

        // 조건 저장
        scenarioConditionRepository.save(condition);

        return true;
    }

    public boolean deleteCondition(Long userId, Long conditionId) {

        Optional<ScenarioCondition> condition = scenarioConditionRepository.findById(conditionId);

        // 조건이 존재하지 않으면 삭제 실패
        if (condition.isEmpty()) {
            return false;
        }

        // 해당 조건이 사용자의 것이 아니면 삭제 실패
        if(!Objects.equals(condition.get().getScenario().getUser().getId(), userId)){
            return false;
        }

        // 조건 삭제
        scenarioConditionRepository.delete(condition.get());

        return true;
    }

    public boolean checkAutoTrade(Long userId, List<StockDto.RealTimeStockInfoDto> stockInfoDtoList) {
        List<Scenario> scenarios = scenarioRepository.findByUserId(userId);
        for(Scenario scenario : scenarios){
            Company company = scenario.getCompany();
            String companyName = company.getCompanyName();
            String companyCode = company.getCompanyCode();
            List<ScenarioCondition> conditions = scenarioConditionRepository.findByScenarioId(scenario.getId());

            Long buyPrice = null;
            Long sellPrice = null;

            for (StockDto.RealTimeStockInfoDto stockInfo : stockInfoDtoList) {
                if (stockInfo.getCompanyName().equals(companyName)) {
                    buyPrice = stockInfo.getBuyPrice1();  // 매수호가1
                    sellPrice = stockInfo.getSellPrice1(); // 매도호가1
                    break;
                }
            }

            if (buyPrice != null && sellPrice != null) {
                executeAutoTrade(userId, companyCode, conditions, sellPrice, buyPrice);
            }
        }

        return false;
    }

    private void executeAutoTrade(Long userId, String companyCode, List<ScenarioCondition> conditions, Long sellPrice, Long buyPrice) {
        int status;
        if(conditions.size() == 1){
            if(!conditions.getFirst().isFinished()){
                status = excuteByRule(userId, companyCode, conditions.getFirst(), sellPrice, buyPrice);
                if(status == 1) {
                    conditions.getFirst().setFinished(true);
                    scenarioConditionRepository.save(conditions.getFirst());
                }
            }
        }
        else if(conditions.size() == 2){
            if(!conditions.getFirst().isFinished()){
                status = excuteByRule(userId, companyCode, conditions.getFirst(), sellPrice, buyPrice);
                if(status == 1) {
                    conditions.getFirst().setFinished(true);
                    conditions.getLast().setFinished(false);
                    scenarioConditionRepository.save(conditions.getFirst());
                    scenarioConditionRepository.save(conditions.getLast());
                }
            }
            if(!conditions.getLast().isFinished()){
                status = excuteByRule(userId, companyCode, conditions.getLast(), buyPrice, sellPrice);
                if(status == 1) {
                    conditions.getFirst().setFinished(false);
                    conditions.getLast().setFinished(true);
                    scenarioConditionRepository.save(conditions.getFirst());
                    scenarioConditionRepository.save(conditions.getLast());
                }
            }
        }
    }

    private int excuteByRule(Long userId, String companyCode, ScenarioCondition sc, Long sellPrice, Long buyPrice){

        TradeDto dto = TradeDto.builder().scode(companyCode).orderType(OrderType.SIJANG).price(0L).week(sc.getQuantity()).build();

        if (sc.getMethodType() == MethodType.RATE || sc.getMethodType() == MethodType.PRICE) {

            if (sc.getBuysellType() == BuysellType.BUY) {

                if (sc.getTargetPrice1() != null && buyPrice >= sc.getTargetPrice1()) {
                    tradeService.buy(userId, dto);
                    return 1;
                }
                if (sc.getTargetPrice2() != null && buyPrice <= sc.getTargetPrice2()) {
                    tradeService.buy(userId, dto);
                    return 1;
                }

            } else if (sc.getBuysellType() == BuysellType.SELL) {

                if (sc.getTargetPrice1() != null && sellPrice >= sc.getTargetPrice1()) {
                    tradeService.sell(userId, dto);
                    return 1;
                }
                if (sc.getTargetPrice2() != null && sellPrice <= sc.getTargetPrice2()) {
                    tradeService.sell(userId, dto);
                    return 1;
                }
            }
        }

        if (sc.getMethodType() == MethodType.TRADING) {

            if (sc.getBuysellType() == BuysellType.BUY) {

                // 상승 -> 하락
                if (sc.getTargetPrice1() != null && buyPrice >= sc.getTargetPrice1()) {
                    sc.setPrice1Reached(true);
                    scenarioConditionRepository.save(sc);
                    return 0;
                }
                else if (sc.getTargetPrice2() != null && buyPrice <= sc.getTargetPrice2() && sc.isPrice1Reached()) {
                    tradeService.buy(userId, dto);
                    return 1;
                }

                // 하락 -> 상승
                if (sc.getTargetPrice3() != null && buyPrice <= sc.getTargetPrice3()) {
                    sc.setPrice3Reached(true);
                    scenarioConditionRepository.save(sc);
                    return 0;
                }
                else if (sc.getTargetPrice4() != null && buyPrice >= sc.getTargetPrice4() && sc.isPrice3Reached()) {
                    tradeService.buy(userId, dto);
                    return 1;
                }

            } else if (sc.getBuysellType() == BuysellType.SELL) {

                // 상승 -> 하락
                if (sc.getTargetPrice1() != null && sellPrice >= sc.getTargetPrice1()) {
                    sc.setPrice1Reached(true);
                    scenarioConditionRepository.save(sc);
                    return 0;
                }
                if (sc.getTargetPrice2() != null && sellPrice <= sc.getTargetPrice2() && sc.isPrice1Reached()) {
                    tradeService.sell(userId, dto);
                    return 1;
                }

                // 하락 -> 상승
                if (sc.getTargetPrice3() != null && sellPrice <= sc.getTargetPrice3()) {
                    sc.setPrice3Reached(true);
                    scenarioConditionRepository.save(sc);
                    return 0;
                }
                else if (sc.getTargetPrice4() != null && sellPrice >= sc.getTargetPrice4() && sc.isPrice3Reached()) {
                    tradeService.sell(userId, dto);
                    return 1;
                }
            }
        }
        return 0;
    }
}
