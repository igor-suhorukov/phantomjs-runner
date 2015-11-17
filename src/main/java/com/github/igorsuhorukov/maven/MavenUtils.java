package com.github.igorsuhorukov.maven;

import org.apache.maven.cli.MavenCli;

import java.io.PrintStream;

public class MavenUtils {

    public static int executeMavenTask(String baseDir, String[] mavenParameters, PrintStream out, PrintStream err) {
        ClassLoader prevClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(MavenCli.class.getClassLoader());
            System.setProperty(MavenCli.MULTIMODULE_PROJECT_DIRECTORY, baseDir);
            MavenCli mavenCli = new MavenCli();
            return mavenCli.doMain(mavenParameters, baseDir, out, err);
        } finally {
            Thread.currentThread().setContextClassLoader(prevClassLoader);
        }
    }
}
