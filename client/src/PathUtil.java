import com.sun.deploy.util.JarUtil;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class PathUtil {

    public static String getCurrentPath() {
        Class<?> caller = getCaller();
        if (caller == null) {
            caller = PathUtil.class;
        }
        return getCurrentPath(caller);
    }

    public static Class<?> getCaller() {
        StackTraceElement[] stack = (new Throwable()).getStackTrace();
        if (stack.length < 3) {
            return PathUtil.class;
        }
        String className = stack[2].getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCurrentPath(Class<?> cls) {
        String path = cls.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.replaceFirst("file:/", "");
        path = path.replaceAll("!/", "");
        if (path.lastIndexOf(File.separator) >= 0) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }
        if ("/".equalsIgnoreCase(path.substring(0, 1))) {
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("window")) {
                path = path.substring(1);
            }
        }
        return path;
    }
    public static String getCurrentFilePath() {
        ProtectionDomain protectionDomain = JarUtil.class.getProtectionDomain();
        CodeSource codeSource = protectionDomain.getCodeSource();
        URI location = null;
        try {
            location = (codeSource == null ? null : codeSource.getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String path = (location == null ? null : location.getSchemeSpecificPart());
        if (path == null) {
            throw new IllegalStateException("Unable to determine code source archive");
        }
        File root = new File(path);
        if (!root.exists()) {
            throw new IllegalStateException(
                    "Unable to determine code source archive from " + root);
        }
        return root.getAbsolutePath();
    }
}

