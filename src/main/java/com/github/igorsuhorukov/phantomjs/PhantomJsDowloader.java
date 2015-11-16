package com.github.igorsuhorukov.phantomjs;

import org.apache.maven.cli.MavenCli;
import org.codehaus.plexus.util.IOUtil;

import java.io.*;

/**
 */
public class PhantomJsDowloader {

    public static void main(String[] args) throws Exception{
        System.out.println(getPhantomJsPath());
    }

    public static String getPhantomJsPath() throws Exception{
        return getPhantomJsPath("1.9.7", PhantomJsDowloader.class.getClassLoader(), getTempDir());
    }

    public static String getPhantomJsPath(String version, ClassLoader classLoader, File projectDirectory) throws Exception{
        copyProjectModel(classLoader, projectDirectory);
        MavenCli mavenCli = new MavenCli();

        String baseDir = projectDirectory.getAbsolutePath();
        System.setProperty(MavenCli.MULTIMODULE_PROJECT_DIRECTORY, baseDir);
        String[] mavenParameters = {"install", "-Dphantomjs.version=" + version};
        checkRetCode(mavenCli.doMain(mavenParameters, baseDir, System.out, System.err));
        return System.getProperty("phantomjs.binary");
    }

    private static void copyProjectModel(ClassLoader classLoader, File tempDir) throws IOException {
        try (InputStream pomResource = classLoader.getResourceAsStream("pom.xml");
             OutputStream outputStream = new FileOutputStream(new File(tempDir, "pom.xml"))){

            if(pomResource==null) throw new IllegalArgumentException("Resource 'pom.xml' not found in classpath");
            IOUtil.copy(pomResource, outputStream);
        }
    }

    private static File getTempDir() {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "phantomjs");
        if(!tempDir.exists()){
            tempDir.mkdir();
        }
        return tempDir;
    }

    private static void checkRetCode(int retCode) {
        if(retCode!=0){
            throw new IllegalArgumentException("Maven exit code  "+ retCode);
        }
    }
}
