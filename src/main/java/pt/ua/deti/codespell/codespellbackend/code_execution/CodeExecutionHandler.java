package pt.ua.deti.codespell.codespellbackend.code_execution;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pt.ua.deti.codespell.codespellbackend.model.Chapter;
import pt.ua.deti.codespell.codespellbackend.model.Level;
import pt.ua.deti.codespell.codespellbackend.service.ChapterService;
import pt.ua.deti.codespell.codespellbackend.service.rabbitmq.RabbitMQSender;

import java.util.UUID;
import java.util.regex.Matcher;

@Component
@Log4j2
public class CodeExecutionHandler {

    private final ChapterService chapterService;
    private final RabbitMQSender rabbitMQSender;

    @Autowired
    public CodeExecutionHandler(ChapterService chapterService, RabbitMQSender rabbitMQSender) {
        this.chapterService = chapterService;
        this.rabbitMQSender = rabbitMQSender;
    }

    public void scheduleCodeExecution(Level level, UUID solutionUniqueId, String code) {

        Chapter chapter = chapterService.getChapterById(level.getChapterId());

        int levelNumber = level.getNumber();
        int chapterNumber = chapter.getNumber();

        String codeTreated = code.replaceAll("\"", Matcher.quoteReplacement("\\\""));

        CodeExecRequest newCodeExecRequest = new CodeExecRequest(solutionUniqueId, levelNumber, chapterNumber, codeTreated);
        String newCodeExecRequestJson;

        try {
            newCodeExecRequestJson = new ObjectMapper().writeValueAsString(newCodeExecRequest);
        } catch (JsonProcessingException e) {
            log.warn("Unable to parse code execution request to JSON.");
            return;
        }

        rabbitMQSender.sendMessage("receiver", newCodeExecRequestJson);
        log.info("Sent code execution request to code launcher.");

    }

}
