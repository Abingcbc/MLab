package org.mlab.data.domain.oss;

import oss.FileTypeEnum;

/**
 * @author cbc
 */
public interface OSSService {
    boolean isExist(String path, FileTypeEnum fileType);

    String save(byte[] object, String path, FileTypeEnum fileType);

    byte[] get(String path, FileTypeEnum fileType);

    boolean remove(String path, FileTypeEnum fileType);
}
