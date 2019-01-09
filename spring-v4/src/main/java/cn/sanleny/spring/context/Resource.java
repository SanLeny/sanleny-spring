package cn.sanleny.spring.context;

import java.io.File;

/**
 * @Author: sanleny
 * @Date: 2019-01-09
 * @Description: cn.sanleny.spring.context
 * @Version: 1.0
 */
public interface Resource extends InputStreamSource {

    boolean exists();

    boolean isReadable();

    boolean isOpen();

    File getFile();

}
