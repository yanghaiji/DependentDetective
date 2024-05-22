package com.javayh.dependent.detective.maven;

import java.util.List;

import com.javayh.dependent.detective.dependency.DependencyAnalyzer;
import com.javayh.dependent.detective.dependency.bean.DependencyCoordinates;
import com.javayh.dependent.detective.dependency.bean.ProjectCoordinates;

/**
 * <p>
 * maven 依赖分析
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-21
 */
public class MavenDependencyAnalyzer extends DependencyAnalyzer {

    /**
     * 获取项目名
     *
     * @param projectPath 项目所在的路径
     * @return {@link ProjectCoordinates} 项目坐标
     */
    @Override
    protected ProjectCoordinates readerProject(String projectPath) {
        return null;
    }

    /**
     * 获取项目的依赖
     *
     * @param pomPath pom文件的位置
     * @return {@link DependencyCoordinates} 依赖坐标
     */
    @Override
    protected List<DependencyCoordinates> dependencyTree(String pomPath) {
        return null;
    }
}
