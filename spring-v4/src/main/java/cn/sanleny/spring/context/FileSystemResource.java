package cn.sanleny.spring.context;

import java.io.File;
import java.io.InputStream;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context
 * @Version: 1.0
 */
public class FileSystemResource implements Resource {

    private File file;

    public FileSystemResource(File file) {
        this.file = file;
    }

    @Override
    public boolean exists() {
        return false;
    }

    @Override
    public boolean isReadable() {
        return false;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return null;
    }
}
