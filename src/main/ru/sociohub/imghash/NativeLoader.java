package ru.sociohub.imghash;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by shutty on 1/24/14.
 */
public class NativeLoader {

    public static Logger LOG = Logger.getLogger("native");

    public NativeLoader() {
    }

    public void loadLibrary(String library) {
        try {
            System.load(saveLibrary(library));
        } catch (IOException e) {
            LOG.warning("Could not find library " + library +
                    " as resource, trying fallback lookup through System.loadLibrary");
            System.loadLibrary(library);
        }
    }


    private String getOSSpecificLibraryName(String library, boolean includePath) {
        String osArch = System.getProperty("os.arch");
        String osName = System.getProperty("os.name").toLowerCase();
        String name;
        String path;

        if (osName.startsWith("win")) {
            if (osArch.equalsIgnoreCase("x86")) {
                name = library + ".dll";
                path = "win-x86/";
            } else {
                throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
            }
        } else if (osName.startsWith("linux")) {
            if (osArch.equalsIgnoreCase("amd64")) {
                name = "lib" + library + ".so";
                path = "linux-x86_64/";
            } else if (osArch.equalsIgnoreCase("ia64")) {
                name = "lib" + library + ".so";
                path = "linux-ia64/";
            } else if (osArch.equalsIgnoreCase("i386")) {
                name = "lib" + library + ".so";
                path = "linux-x86/";
            } else {
                throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
            }
        } else {
            throw new UnsupportedOperationException("Platform " + osName + ":" + osArch + " not supported");
        }

        return includePath ? path + name : name;
    }

    private String saveLibrary(String library) throws IOException {
        InputStream in = null;
        OutputStream out = null;

        try {
            String libraryName = getOSSpecificLibraryName(library, false);
            in = this.getClass().getClassLoader().getResourceAsStream(libraryName);
            String tmpDirName = System.getProperty("java.io.tmpdir");
            File tmpDir = new File(tmpDirName);
            if (!tmpDir.exists()) {
                tmpDir.mkdir();
            }
            File file = File.createTempFile(library + "-", ".tmp", tmpDir);
            // Clean up the file when exiting
            file.deleteOnExit();
            out = new FileOutputStream(file);

            int cnt;
            byte buf[] = new byte[16 * 1024];
            // copy until done.
            while ((cnt = in.read(buf)) >= 1) {
                out.write(buf, 0, cnt);
            }
            LOG.info("Saved libfile: " + file.getAbsoluteFile());
            return file.getAbsolutePath();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignore) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
}
