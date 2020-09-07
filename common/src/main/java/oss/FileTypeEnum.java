package oss;

/**
 * @author cbc
 */

public enum FileTypeEnum {
    // Name is used for oss bucket, and it should follow AWS standard
    // https://docs.aws.amazon.com/AmazonS3/latest/dev/BucketRestrictions.html
    /**
     * 头像
     */
    avatar,
    /**
     * 用户上传的代码
     */
    code,
    /**
     * 数据文件
     */
    dataset
}
