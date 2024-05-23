package com.javayh.dependent.detective.dependency.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dependency {

    private String groupId;

    private String artifactId;

    private String version;

}
