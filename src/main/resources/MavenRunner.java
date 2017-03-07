//dependency:mvn:/com.github.igor-suhorukov:phantomjs-runner:${project.version}
//dependency:mvn:/org.slf4j:slf4j-simple:1.7.5
import com.github.igorsuhorukov.maven.MavenRunner;

class MavenRunner {
    public static void main(String[] args) throws Exception{
        System.exit(MavenUtils.executeMavenTask(System.getProperty("user.dir"), args, System.out, System.err));
    }
}
