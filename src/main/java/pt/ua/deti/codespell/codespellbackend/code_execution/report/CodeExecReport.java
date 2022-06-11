package pt.ua.deti.codespell.codespellbackend.code_execution.report;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@RedisHash("CodeExecReport")
public class CodeExecReport implements Serializable {

    private String id;
    private AnalysisStatus analysisStatus;
    private ExecutionStatus executionStatus;
    private List<Step> steps;
    private int score;
    private List<String> output;
    private List<String> tips;
    private List<String> errors;
    private List<String> runtimeLogs;

}
