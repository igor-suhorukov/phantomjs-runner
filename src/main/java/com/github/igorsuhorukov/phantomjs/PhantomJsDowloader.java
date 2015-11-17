package com.github.igorsuhorukov.phantomjs;

import com.github.igorsuhorukov.maven.MavenUtils;
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
        String[] mavenParameters = {"install", "-Dphantomjs.version=" + version};
        checkRetCode(MavenUtils.executeMavenTask(projectDirectory.getAbsolutePath(), mavenParameters, System.out, System.err));
        return System.getProperty("phantomjs.binary");
    }

    private static void copyProjectModel(ClassLoader classLoader, File tempDir) throws IOException {
        try (InputStream pomResource = classLoader.getResourceAsStream("pom.xml");
             OutputStream outputStream = new FileOutputStream(new File(tempDir, "pom.xml"))){

            if(pomResource==null) throw new IllegalArgumentException("Resource 'pom.xml' not found in classpath");
            IOUtil.copy(pomResource, outputStream);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File getTempDir() {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "phantomjs");
        if(!tempDir.exists()) {
            tempDir.mkdir();
        }
        return tempDir;
    }

    private static void checkRetCode(int retCode) {
        if(retCode!=0){
            throw new IllegalArgumentException("Maven exit with code: "+ retCode);
        }
    }
}
