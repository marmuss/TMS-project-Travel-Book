package com.example.travelproject.service;

import com.example.travelproject.entity.Country;
import com.example.travelproject.repository.CountryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.Callable;

@Component
@AllArgsConstructor
public class CountryService {
    private CountryRepository countryRepository;

    @Transactional
    public void addCountry(Country country){
        countryRepository.save(country);
    }

    public List<Country> getAll(){
        List<Country> all = countryRepository.findAll();
        return all;
    }

    public List<Country> getAllSortedByContinent(){
        List<Country> all = countryRepository.findAll(Sort.by(Sort.Direction.ASC, "continent"));
        return all;
    }


    public Country getCountryById(Long countryId){
        return countryRepository.getById(countryId);
    }

    @Transactional
    public void deleteById(Long id){
        countryRepository.deleteById(id);
    }

    public void addTourist(Country country){
        int tourists = country.getTourists() + 1;
        countryRepository.addTourist(country.getId(), tourists);
    }

    public Country getTopByTourists(){
        List<Country> top3ByOrderByTourists = countryRepository.findTop3ByOrderByTourists();
        if (!top3ByOrderByTourists.isEmpty()) {
            Country country = top3ByOrderByTourists.get(1);
            return country;
        }
        return null;
    }
}
