package com.javayh.dependent.detective.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javayh.dependent.detective.dependency.bean.ProjectCoordinates;
import com.javayh.dependent.detective.web.serivce.DependencyAnalyzerService;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-22
 */
@RestController
@RequestMapping
public class DepController {

    @Autowired
    private DependencyAnalyzerService dependencyAnalyzerService;

    @GetMapping(value = "/dep/list")
    public List<ProjectCoordinates> depList(String projectPath) throws IOException, InterruptedException {
        return dependencyAnalyzerService.dependencyAnalyzer(projectPath);
    }

}
