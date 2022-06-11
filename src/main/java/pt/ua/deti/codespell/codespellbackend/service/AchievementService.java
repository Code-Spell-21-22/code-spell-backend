package pt.ua.deti.codespell.codespellbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.AchievementNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.Achievement;
import pt.ua.deti.codespell.codespellbackend.repository.AchievementRepository;

import java.util.List;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    public List<Achievement> getAllAchievements() {
        return achievementRepository.findAll();
    }

    @NonNull
    public Achievement getAchievementById(ObjectId achievementId) {

        if (!achievementRepository.existsById(achievementId)) {
            throw new AchievementNotFoundException(String.format("The achievement %s could not be found.", achievementId.toString()));
        }

        return achievementRepository.findById(achievementId);

    }

}
