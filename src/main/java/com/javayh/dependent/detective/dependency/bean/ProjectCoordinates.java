package com.javayh.dependent.detective.dependency.bean;

import java.util.List;

import lombok.Data;

/**
 * <p>
 * 项目坐标
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-21
 */
@Data
public class ProjectCoordinates {

    private String groupId;

    private String artifactId;

    private String version;

    private List<DependencyCoordinates> dependencyCoordinates;
}
