import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class InterfaceGenerator {

    public static void main(String[] args) throws IOException, InterruptedException {
        String jdkPath = " C:/Program Files/Java/jdk1.8.0_201/bin";
        String classPath = "D:\\workspace\\opt\\git\\ky\\jniDemo\\target\\classes";
        String cmd = jdkPath + "/javah.exe -jni -classpath " + classPath + " -d D:\\workspace\\opt\\git\\ky\\jniDemo\\native\\jniDll\\include com.ky.jni.AacEncoder";

        Process p = Runtime.getRuntime().exec(cmd);
        InputStream is = p.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("GBK")));

        p.waitFor();
        String s;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }

}
