package pt.ua.deti.codespell.codespellbackend.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.deti.codespell.codespellbackend.code_execution.report.CodeExecReport;

@Repository
public interface CodeExecReportRepository extends CrudRepository<CodeExecReport, String> { }
