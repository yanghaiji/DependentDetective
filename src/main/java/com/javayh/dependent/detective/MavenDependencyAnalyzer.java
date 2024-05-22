package com.javayh.dependent.detective;

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

public class MavenDependencyAnalyzer {
    public static void main(String[] args) {
        String pomPath = "C:\\source_code\\dependent\\ContentHub\\pom.xml";
        String mvnPath = "C:\\java_tools\\IntelliJ IDEA 2020.1\\plugins\\maven\\lib\\maven3";
        File pomFile = new File(pomPath);
        if (!pomFile.exists()) {
            System.out.println("File not found: " + pomPath);
            System.exit(1);
        }

        try {
            // Parse the pom.xml file
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileReader(pomFile));

            List<String> dependenciesList = new ArrayList<>();
            dependenciesList.add("Project: " + model.getGroupId() + ":" + model.getArtifactId() + ":" + model.getVersion());


            // Use Maven Invoker to run the "dependency:tree" goal and capture the output
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(pomFile);
            request.setGoals(Collections.singletonList("dependency:tree"));

            Invoker invoker = new DefaultInvoker();
            invoker.setMavenHome(new File(mvnPath));

            invoker.setOutputHandler(dependenciesList::add);
            invoker.execute(request);

            List<String> list = extractDependencies(dependenciesList);
            // Output dependencies list
            for (String line : list) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> extractDependencies(List<String> dependencyTreeLines) {
        List<String> dependencies = new ArrayList<>();
        Pattern pattern = Pattern.compile("([\\w\\.-]+:[\\w\\.-]+:jar:[\\w\\.-]+:[\\w\\.-]+)");
        for (String line : dependencyTreeLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                dependencies.add(matcher.group(1));
            }
        }
        return dependencies;
    }


}
