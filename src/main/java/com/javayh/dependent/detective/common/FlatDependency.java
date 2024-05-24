package com.javayh.dependent.detective.common;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadFontStyle;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;

import lombok.Data;

/**
 * @author HaiJiYang
 */
@Data
@ContentRowHeight(15)
@HeadRowHeight(30)
@HeadFontStyle(fontHeightInPoints = 12)
public class FlatDependency {
    @ColumnWidth(30)
    @ExcelProperty("Project Group ID")
    private String groupId;

    @ColumnWidth(30)
    @ExcelProperty("Project Artifact ID")
    private String artifactId;

    @ColumnWidth(15)
    @ExcelProperty("Project Version")
    private String version;

    @ColumnWidth(30)
    @ExcelProperty("Dependency Group ID")
    private String dependencyGroupId;

    @ColumnWidth(30)
    @ExcelProperty("Dependency Artifact ID")
    private String dependencyArtifactId;

    @ColumnWidth(15)
    @ExcelProperty("Dependency Version")
    private String dependencyVersion;

    @ColumnWidth(30)
    @ExcelProperty("Dependency Scope")
    private String dependencyScope;

    @ColumnWidth(120)
    @ExcelProperty("Dependency Source")
    private String dependencySource;

}
