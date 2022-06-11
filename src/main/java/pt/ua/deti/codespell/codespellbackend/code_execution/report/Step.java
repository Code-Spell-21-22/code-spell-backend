package pt.ua.deti.codespell.codespellbackend.code_execution.report;

import lombok.Data;

import java.util.List;

@Data
public class Step {

    private final int id;
    private final boolean successful;
    private final List<Object> args;

}
