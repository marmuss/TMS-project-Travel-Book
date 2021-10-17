package com.example.travelproject.service;

import com.example.travelproject.entity.Country;
import com.example.travelproject.entity.User;
import com.example.travelproject.entity.UserProfile;
import com.example.travelproject.repository.UserProfileRepository;
import com.example.travelproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class UserProfileService {

    private UserRepository userRepository;
    private UserProfileRepository userProfileRepository;
    private ImageService imageService;
    private CountryService countryService;

    public UserProfile save(User user){
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setPhoto("default.png");
        userProfile.setCreationDate(LocalDateTime.now());
        return userProfileRepository.save(userProfile);
    }

    public void saveImage(MultipartFile image, User user) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        String uploadDir = "user-photos/" + user.getId();
        imageService.uploadImage(uploadDir, fileName, image);
        UserProfile userProfile = findUserProfileByUser(user).get();
        userProfileRepository.setPhoto(userProfile.getId(), fileName);

    }

    public Optional<UserProfile> findUserProfileByUser(User user){
    return userProfileRepository.findByUser(user);
    }

    public Optional<UserProfile> findUserProfileByUserId(Long id){
        return userProfileRepository.findByUser(userRepository.getById(id));
    }

    @Transactional
    public void saveResidenceCountry(User user, Country country){
        UserProfile userProfile = findUserProfileByUser(user).get();
        userProfile.setResidenceCountry(country);
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void saveFavoriteCountry(User user, Country country){
        UserProfile userProfile = findUserProfileByUser(user).get();
        userProfile.setFavoriteCountry(country);
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void addVisitedCountry(User user, Country country){
        UserProfile userProfile = findUserProfileByUser(user).get();
        Set<Country> visitedCountries = userProfile.getVisitedCountries();
        visitedCountries.add(country);
        userProfile.setVisitedCountries(visitedCountries);
        userProfileRepository.save(userProfile);
        countryService.addTourist(country);
    }

    @Transactional
    public void deleteVisitedCountry(User user, Country country){
        UserProfile userProfile = findUserProfileByUser(user).get();
        Set<Country> visitedCountries = userProfile.getVisitedCountries();
        visitedCountries.remove(country);
        userProfile.setVisitedCountries(visitedCountries);
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void addCompanion(long id, UserProfile profile){
        Set<UserProfile> companions = profile.getCompanions();
        companions.add(userProfileRepository.getById(id));
        profile.setCompanions(companions);
        userProfileRepository.save(profile);
    }

    public List<UserProfile> getLastUsers(){
        return userProfileRepository.findTop5ByOrderByIdDesc();
    }
}
