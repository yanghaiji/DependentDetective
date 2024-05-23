package com.javayh.dependent.detective.dependency.bean;

import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 依赖坐标
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DependencyCoordinates extends Dependency {

    /**
     * 全路径
     */
    private String source;

    /**
     * 作用域
     */
    private String scope;

    public void setSource(String source) {
        this.source = source;
        if (Objects.nonNull(source)) {
            String[] split = source.split(":");
            this.setGroupId(split[0]);
            this.setArtifactId(split[1]);
            this.setVersion(split[3]);
            this.setScope(split[4]);

        }
    }
}
