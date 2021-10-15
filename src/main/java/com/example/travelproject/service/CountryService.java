package com.example.travelproject.service;

import com.example.travelproject.entity.Country;
import com.example.travelproject.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class CountryService {
    private CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getAll(){
        List<Country> all = countryRepository.findAll();
        return all;
    }

    public Country getCountryById(Long countryId){
        return countryRepository.getById(countryId);
    }
}
