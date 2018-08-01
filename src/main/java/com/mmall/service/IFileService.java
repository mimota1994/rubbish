package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by huangzhigang on 18-5-31.
 */
public interface IFileService {
    String upload(MultipartFile file, String path);
}
