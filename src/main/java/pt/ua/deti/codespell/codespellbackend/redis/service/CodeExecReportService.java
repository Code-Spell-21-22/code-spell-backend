package pt.ua.deti.codespell.codespellbackend.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.codespell.codespellbackend.code_execution.report.CodeExecReport;
import pt.ua.deti.codespell.codespellbackend.redis.repository.CodeExecReportRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class CodeExecReportService {

    private final CodeExecReportRepository codeExecReportRepository;

    @Autowired
    public CodeExecReportService(CodeExecReportRepository codeExecReportRepository) {
        this.codeExecReportRepository = codeExecReportRepository;
    }

    public Optional<CodeExecReport> getById(UUID codeUniqueId) {
        return codeExecReportRepository.findById(codeUniqueId.toString());
    }

}
