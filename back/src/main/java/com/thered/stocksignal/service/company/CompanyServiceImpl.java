package com.thered.stocksignal.service.company;

import com.thered.stocksignal.domain.entity.Company;
import com.thered.stocksignal.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

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

}
