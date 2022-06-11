package pt.ua.deti.codespell.codespellbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.exception.EntityNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.InjectableCode;
import pt.ua.deti.codespell.codespellbackend.repository.InjectableCodeRepository;

@Service
public class InjectableCodeService {

    private final InjectableCodeRepository injectableCodeRepository;

    @Autowired
    public InjectableCodeService(InjectableCodeRepository injectableCodeRepository) {
        this.injectableCodeRepository = injectableCodeRepository;
    }

    @NonNull
    public InjectableCode getInjectableCodeForLevel(ObjectId levelId) {

        if (!injectableCodeRepository.existsByLevelId(levelId))
            throw new EntityNotFoundException(String.format("Unable to find injectable code with id %s.", levelId.toString()));
        return injectableCodeRepository.findByLevelId(levelId);

    }

}
