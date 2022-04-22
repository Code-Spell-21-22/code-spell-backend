package pt.ua.deti.codespell.codespellbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import pt.ua.deti.codespell.codespellbackend.exception.implementations.AchievementNotFoundException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.ExistentUserException;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.repository.AchievementRepository;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @NonNull
    public Achievement findByTitle(String title) {
        if (!achievementRepository.existsByTitle(title))
            throw new AchievementNotFoundException(String.format("The achievement %s could not be found.", title));
        return achievementRepository.findByTitle(title);
    }

    // Is there a need for this method? Achievements will be hardcoded in the database right?
    public void registerAchievement(Achievement achievement) {
        if (achievementRepository.existsByTitle(achievement.getTitle()))
            throw new ExistentUserException("An achievement with the provided title already exists.");
        achievementRepository.save(achievement);
    }

}
