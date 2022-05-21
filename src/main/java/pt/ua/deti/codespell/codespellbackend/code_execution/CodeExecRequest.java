package pt.ua.deti.codespell.codespellbackend.code_execution;

import lombok.Data;

import java.util.UUID;

@Data
public class CodeExecRequest {

    private final UUID codeUniqueId;
    private final int level;
    private final int chapter;
    private final String code;

}
