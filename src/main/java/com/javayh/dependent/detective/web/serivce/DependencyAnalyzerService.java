package com.javayh.dependent.detective.web.serivce;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.javayh.dependent.detective.common.PomXmlFinder;
import com.javayh.dependent.detective.dependency.DependencyAnalyzer;
import com.javayh.dependent.detective.dependency.bean.ProjectCoordinates;

/**
 * <p>
 *
 * </p>
 *
 * @author hai ji
 * @version 1.0.0
 * @since 2024-05-22
 */
@Service
public class DependencyAnalyzerService {

    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(5, 10,
        0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    @Autowired
    private DependencyAnalyzer dependencyAnalyzer;

    @Value("${mvnEnv}")
    private String mvnEnv;

    public CopyOnWriteArrayList<ProjectCoordinates> dependencyAnalyzer(String projectPath)
        throws IOException, InterruptedException {
        // fix 线程安全问题
        CopyOnWriteArrayList<ProjectCoordinates> projectCoordinates = new CopyOnWriteArrayList<>();
        List<String> finder = PomXmlFinder.finder(projectPath);
        CountDownLatch latch = new CountDownLatch(finder.size());
        finder.forEach(file -> EXECUTOR.execute(() -> {
            try {
                ProjectCoordinates coords = dependencyAnalyzer.dependencyAnalyzer(file, mvnEnv);
                projectCoordinates.add(coords);
            } finally {
                latch.countDown();
            }
        }));
        latch.await();
        return projectCoordinates;
    }
}
