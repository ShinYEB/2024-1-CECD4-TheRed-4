package com.thered.stocksignal.service.company;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thered.stocksignal.app.dto.MyBalanceDto;
import com.thered.stocksignal.domain.entity.Company;
import com.thered.stocksignal.kisApi.KisApiRequest;
import com.thered.stocksignal.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.thered.stocksignal.app.dto.CompanyDto.*;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final KisApiRequest apiRequest;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    @Override
    public String findCodeByName(String companyName) {
        Company company = companyRepository.findByCompanyName(companyName);
        return company != null ? company.getCompanyCode() : null;
    }

    @Override
    public String findLogoByName(String companyName) {
        Company company = companyRepository.findByCompanyName(companyName);
        return company != null ? company.getLogoImage() : null;
    }

    @Override
    public CompanyInfoResponseDto findCompanyInfoByCode(String companyCode, String accessToken, String appKey, String appSecret) {

        // API url
        String endpoint = "/uapi/domestic-stock/v1/quotations/inquire-price";

        // API 쿼리 파라미터
        String url = apiRequest.buildUrl(endpoint,
                "FID_COND_MRKT_DIV_CODE=J",
                "FID_INPUT_ISCD="+companyCode
        );

        // 요청 헤더 생성
        Request request = new Request.Builder()
                .url(url)
                .addHeader("authorization", "Bearer " + accessToken)
                .addHeader("appkey", appKey)
                .addHeader("appsecret", appSecret)
                .addHeader("tr_id", "FHKST01010100")
                .build();

        try (Response response = client.newCall(request).execute()) {

            // 응답 본문을 JsonNode로 변환
            String jsonResponse = Objects.requireNonNull(response.body()).string();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);
            JsonNode output = jsonNode.path("output");

            CompanyInfoResponseDto companyInfo = CompanyInfoResponseDto.builder().build();

            companyInfo.setOpenPrice(output.path("stck_oprc").asLong());   // 시가
            companyInfo.setClosePrice(output.path("stck_prpr").asLong());   // 종가(현재가)
            companyInfo.setLowPrice(output.path("stck_lwpr").asLong());   // 저가
            companyInfo.setHighPrice(output.path("stck_hgpr").asLong());   // 고가
            companyInfo.setTradingVolume(output.path("acml_vol").asLong());   // 거래량
            companyInfo.setTradingValue(output.path("acml_tr_pbmn").asLong());   // 거래대금
            companyInfo.setOneYearHighPrice(output.path("stck_dryy_hgpr").asLong());   // 연중최고가
            companyInfo.setOneYearLowPrice(output.path("stck_dryy_lwpr").asLong());   // 연중최저가

            return companyInfo;

        } catch (IOException e) {
            return null;
            // TODO : 실패 시 Status 반환
            // ApiResponse.onFailure(Status.);
        }
    }

}
