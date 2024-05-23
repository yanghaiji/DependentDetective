package com.javayh.dependent.detective.dependency.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 项目坐标
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-21
 */
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCoordinates extends Dependency {

    private List<DependencyCoordinates> dependencyCoordinates;

}
