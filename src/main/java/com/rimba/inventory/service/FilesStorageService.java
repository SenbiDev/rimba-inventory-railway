package com.rimba.inventory.service;

import com.rimba.inventory.enums.FileStorageEnum;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface FilesStorageService {
  public void init();

  public void save(FileStorageEnum fileStorageEnum, MultipartFile file);

  public boolean update(FileStorageEnum fileStorageEnum, String oldFileName, MultipartFile newFile) throws IOException;

  public boolean delete(FileStorageEnum fileStorageEnum, String filename) throws IOException;
}
