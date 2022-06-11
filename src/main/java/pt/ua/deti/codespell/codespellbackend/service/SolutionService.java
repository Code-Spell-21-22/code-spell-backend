package pt.ua.deti.codespell.codespellbackend.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.code_execution.report.AnalysisStatus;
import pt.ua.deti.codespell.codespellbackend.code_execution.report.CodeExecReport;
import pt.ua.deti.codespell.codespellbackend.code_execution.report.ExecutionStatus;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.IllegalSolutionException;
import pt.ua.deti.codespell.codespellbackend.exception.implementations.LevelNotFoundException;
import pt.ua.deti.codespell.codespellbackend.model.Level;
import pt.ua.deti.codespell.codespellbackend.model.Solution;
import pt.ua.deti.codespell.codespellbackend.redis.service.CodeExecReportService;
import pt.ua.deti.codespell.codespellbackend.repository.SolutionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {

    private final SolutionRepository solutionRepository;
    private final LevelService levelService;
    private final CodeExecReportService codeExecReportService;

    @Autowired
    public SolutionService(SolutionRepository solutionRepository, LevelService levelService, CodeExecReportService codeExecReportService) {
        this.solutionRepository = solutionRepository;
        this.levelService = levelService;
        this.codeExecReportService = codeExecReportService;
    }

    public void storeSolution(Solution solution) {

        Optional<CodeExecReport> codeExecReportOpt = codeExecReportService.getById(solution.getCodeReportId());

        if (codeExecReportOpt.isEmpty()) {
            throw new IllegalSolutionException("Unable to store solution. The code report could not be found or was invalid.");
        }

        CodeExecReport codeExecReport = codeExecReportOpt.get();

        if (codeExecReport.getAnalysisStatus() != AnalysisStatus.SUCCESS || codeExecReport.getExecutionStatus() != ExecutionStatus.SUCCESS) {
            throw new IllegalSolutionException("Unable to store solution. The code report could not be found or was invalid.");
        }

        if (!levelService.existsByLevelId(solution.getLevelId())) {
            throw new LevelNotFoundException(String.format("Unable to find the level %s.", solution.getLevelId().toString()));
        }

        solutionRepository.deleteAll(solutionRepository.findAllByAuthorUsernameAndLevelId(solution.getAuthorUsername(), solution.getLevelId()));
        solutionRepository.save(solution);

    }

    public List<Solution> getStoredSolutionsForUser(String username) {
        return solutionRepository.findAllByAuthorUsername(username);
    }

    public List<Solution> getStoredSolutionsForLevel(ObjectId levelId) {

        if (!levelService.existsByLevelId(levelId)) {
            throw new LevelNotFoundException("The level could not be found.");
        }

        return solutionRepository.findAllByLevelId(levelId);

    }

}
