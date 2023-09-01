package com.xwh.task.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author xwh
 * @version 1.0
 * 2023/8/25
 */
public class MinIOUtil {
    @Resource
    private MinioClient minioClient;

    private void createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        BucketExistsArgs build = BucketExistsArgs.builder().bucket(bucketName).build();
        if(!minioClient.bucketExists(build)) {
            MakeBucketArgs makeArgs = MakeBucketArgs.builder().bucket(bucketName).build();
            minioClient.makeBucket(makeArgs);
        }
    }

    public ObjectWriteResponse putObject(String bucketName, String objectName, InputStream inputStream, long objectSize, String contentType)throws Exception{
        if (StringUtils.isEmpty(bucketName)){
            throw new RuntimeException("这里上传文件到了一个空的桶中");
        }
        createBucket(bucketName);
        long partSize=-1;//-1自动设置
        PutObjectArgs build = PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(inputStream, objectSize, partSize).build();
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(build);
        return objectWriteResponse;
    }

    public InputStream getObject(String bucketName,String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        GetObjectArgs build = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
        return minioClient.getObject(build);
    }

    //url临时获取对象
    public String getObjectUrl(String bucketName,String objectName) throws Exception{
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(objectName)
                .expiry(5, TimeUnit.MINUTES)
                .method(Method.GET)
                .build();
        return minioClient.getPresignedObjectUrl(args);
    }

    public void removeObject(String bucketName,String objectName)throws Exception{
        RemoveObjectArgs build = RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build();
        minioClient.removeObject(build);
    }

    public ObjectWriteResponse uploadFile(String bucketName, String filePath, MultipartFile file) throws Exception{
        return putObject(bucketName,filePath,file.getInputStream(),file.getSize(),file.getContentType());
    }

    public void downloadFile(String bucketName, String filePath, HttpServletResponse response) throws Exception{
        try(InputStream is = getObject(bucketName,filePath);
            BufferedInputStream bis = new BufferedInputStream(is);
            OutputStream os = response.getOutputStream()){
            response.setContentType("application/force-download;charset=utf-8");
            response.setHeader("Content-Disposition","attachment;fileName="+ URLEncoder.encode(filePath,"utf-8"));
            byte[] buffer = new byte[1024*1024*1024];
            int offset = bis.read(buffer);
            while (offset!=-1){
                os.write(buffer,0,offset);
                offset = bis.read(buffer);
            }
            os.flush();
        }catch (Exception e){

        }
    }
}
