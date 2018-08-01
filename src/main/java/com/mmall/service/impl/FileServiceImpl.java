package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by huangzhigang on 18-5-31.
 */
@Service("iFileService")

public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        String filename = file.getOriginalFilename();
        //extensive name
        //abc.jpg
        //from back to front
        String fileExtensionName = filename.substring(filename.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("begin upload file,the name of file:{},upload path:{},the new file name:{}",filename,path,uploadFileName);

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //upload file successfully

            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //file has been uploaded to ftp server

            targetFile.delete();
        } catch (IOException e) {
            logger.info("upload file error",e);
            return null;
        }

        return targetFile.getName();
    }
}
