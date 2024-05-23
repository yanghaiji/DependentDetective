package com.javayh.dependent.detective.dependency;

import java.util.List;

import com.javayh.dependent.detective.dependency.bean.DependencyCoordinates;
import com.javayh.dependent.detective.dependency.bean.ProjectCoordinates;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-21
 */
public abstract class DependencyAnalyzer {

    /**
     * 获取项目名
     *
     * @param projectPath 项目所在的路径
     * @return {@link ProjectCoordinates} 项目坐标
     */
    protected abstract ProjectCoordinates readerProject(String projectPath);

    /**
     * 获取项目的依赖
     *
     * @param pomPath pom文件的位置
     * @param mvnEnv  maven 的位置
     * @return {@link DependencyCoordinates} 依赖坐标
     */
    protected abstract List<DependencyCoordinates> dependencyTree(String pomPath, String mvnEnv);

    /**
     * 进行依赖的分析
     *
     * @param projectPath 项目所在的路径
     * @param mvnEnv      maven 的位置
     */
    public ProjectCoordinates dependencyAnalyzer(String projectPath, String mvnEnv) {
        ProjectCoordinates projectCoordinates = readerProject(projectPath);
        List<DependencyCoordinates> dependencyCoordinates = dependencyTree(projectPath, mvnEnv);
        projectCoordinates.setDependencyCoordinates(dependencyCoordinates);
        return projectCoordinates;
    }

}
