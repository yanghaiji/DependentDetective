package com.javayh.dependent.detective.common;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.javayh.dependent.detective.dependency.bean.DependencyCoordinates;
import com.javayh.dependent.detective.dependency.bean.ProjectCoordinates;
import com.javayh.dependent.detective.handler.FreezeAndFilterHandler;

/**
 * @author HaiJiYang
 */
public class ExcelExportUtil {

    public static void writeExcel(ServletOutputStream outputStream, List<ProjectCoordinates> projects) {
        // Prepare data for flat export
        List<FlatDependency> flatData = new LinkedList<>();
        Set<ScoringFlatDependency> scoringFlatDependencyList = new HashSet<>();

        for (ProjectCoordinates project : projects) {
            List<DependencyCoordinates> dependencyCoordinates = project.getDependencyCoordinates();
            for (int i = 0; i < dependencyCoordinates.size(); i++) {
                FlatDependency flatDependency = new FlatDependency();
                if (i == 0) {
                    flatDependency.setGroupId(project.getGroupId());
                    flatDependency.setArtifactId(project.getArtifactId());
                    flatDependency.setVersion(project.getVersion());

                }
                flatDependency.setDependencyGroupId(dependencyCoordinates.get(i).getGroupId());
                flatDependency.setDependencyArtifactId(dependencyCoordinates.get(i).getArtifactId());
                flatDependency.setDependencyVersion(dependencyCoordinates.get(i).getVersion());
                flatDependency.setDependencySource(dependencyCoordinates.get(i).getSource());
                flatDependency.setDependencyScope(dependencyCoordinates.get(i).getScope());
                ScoringFlatDependency scoringFlatDependency = new ScoringFlatDependency();
                BeanUtils.copyProperties(flatDependency, scoringFlatDependency);
                flatData.add(flatDependency);
                scoringFlatDependencyList.add(scoringFlatDependency);
            }
            // 空行
            FlatDependency flatDependency = new FlatDependency();
            flatData.add(flatDependency);
        }

        // Create an ExcelWriter
        ExcelWriter excelWriter = EasyExcel.write(outputStream).build();

        try {
            // Write data to the first sheet
            WriteSheet sheet1 = EasyExcel.writerSheet(0, "Projects Dependency")
                .head(FlatDependency.class)
                .registerWriteHandler(new FreezeAndFilterHandler())
                .build();
            excelWriter.write(flatData, sheet1);

            // Write data to the second sheet
            WriteSheet sheet2 = EasyExcel.writerSheet(1, "Projects Dependency Scoring")
                .head(ScoringFlatDependency.class)
                .registerWriteHandler(new FreezeAndFilterHandler())
                .build();
            excelWriter.write(scoringFlatDependencyList, sheet2);
        } finally {
            // Finish and close the writer
            excelWriter.finish();
        }
    }

}

