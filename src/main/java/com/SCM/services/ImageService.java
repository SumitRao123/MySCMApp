package com.SCM.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public String getImage(MultipartFile contactImage,String filename);
    
    public String getImageUrl(String filename);

    public String uploadImage(MultipartFile contactImage,String filename );
}
