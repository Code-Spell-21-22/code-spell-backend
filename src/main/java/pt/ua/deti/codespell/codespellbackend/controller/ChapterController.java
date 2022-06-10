package pt.ua.deti.codespell.codespellbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ua.deti.codespell.codespellbackend.model.Chapter;
import pt.ua.deti.codespell.codespellbackend.model.ProgrammingLanguage;
import pt.ua.deti.codespell.codespellbackend.model.SkillLevel;
import pt.ua.deti.codespell.codespellbackend.service.ChapterService;

@RestController
@RequestMapping("/api/chapter")
public class ChapterController {
    
    private final ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("")
    public List<Chapter> getChaptersList() {
        return chapterService.getAllChapters();
    }

}
