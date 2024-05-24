package com.javayh.dependent.detective.maven;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.springframework.stereotype.Component;
import com.javayh.dependent.detective.dependency.DependencyAnalyzer;
import com.javayh.dependent.detective.dependency.bean.DependencyCoordinates;
import com.javayh.dependent.detective.dependency.bean.ProjectCoordinates;
import com.javayh.dependent.detective.exception.FileReaderException;
import com.javayh.dependent.detective.exception.MavenInvocationException;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * maven 依赖分析
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-21
 */
@Slf4j
@Component
public class MavenDependencyAnalyzer extends DependencyAnalyzer {

    Pattern pattern = Pattern.compile("([\\w.-]+:[\\w.-]+:jar:[\\w.-]+:[\\w.-]+)");

    /**
     * 获取项目名
     *
     * @param projectPath 项目所在的路径
     * @return {@link ProjectCoordinates} 项目坐标
     */
    @Override
    protected ProjectCoordinates readerProject(String projectPath) {
        // Parse the pom.xml file
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        try {
            model = reader.read(new FileReader(projectPath));
        } catch (Exception e) {
            log.error("readerProject {}", e.getMessage(), e);
            throw new FileReaderException(e.getMessage());
        }
        ProjectCoordinates projectCoordinates = new ProjectCoordinates();
        projectCoordinates.setGroupId(model.getGroupId());
        projectCoordinates.setArtifactId(model.getArtifactId());
        projectCoordinates.setVersion(model.getVersion());
        return projectCoordinates;
    }


    /**
     * 获取项目的依赖
     *
     * @param pomPath pom文件的位置
     * @return {@link DependencyCoordinates} 依赖坐标
     */
    @Override
    protected List<DependencyCoordinates> dependencyTree(String pomPath, String mvnEnv) {
        List<String> dependenciesList = new ArrayList<>();
        // Use Maven Invoker to run the "dependency:tree" goal and capture the output
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File(pomPath));
        request.setGoals(Collections.singletonList("dependency:tree"));
        try {
            Invoker INVOKER = new DefaultInvoker();
            INVOKER.setMavenHome(new File(mvnEnv));
            INVOKER.setOutputHandler(dependenciesList::add);
            INVOKER.execute(request);
        } catch (org.apache.maven.shared.invoker.MavenInvocationException e) {
            log.error("dependencyTree {}", e.getMessage(), e);
            throw new MavenInvocationException(e.getMessage());
        }

        return extractDependencies(dependenciesList);
    }

    private List<DependencyCoordinates> extractDependencies(List<String> dependencyTreeLines) {
        List<DependencyCoordinates> dependencies = new ArrayList<>();

        for (String line : dependencyTreeLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String group = matcher.group(1);
                DependencyCoordinates dependencyCoordinates = new DependencyCoordinates();
                dependencyCoordinates.setSource(group);
                dependencies.add(dependencyCoordinates);
            }
        }
        return dependencies;
    }

}
