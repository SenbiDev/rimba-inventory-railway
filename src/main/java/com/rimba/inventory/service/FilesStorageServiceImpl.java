package com.rimba.inventory.service;

import com.rimba.inventory.enums.FileStorageEnum;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {

  private final String initialFolderPath = "src/main/resources/static/images/dummy";
  private final String customerFolder = "customer";
  private final String itemFolder = "item";

  private final Path root = Paths.get(initialFolderPath);

  Path customerFolderPath;
  Path itemFolderPath;

  @Override
  public void init() {
    try {
      this.customerFolderPath = root.resolveSibling(this.customerFolder);
      Files.createDirectories(this.customerFolderPath);

      this.itemFolderPath = root.resolveSibling(this.itemFolder);
      Files.createDirectories(this.itemFolderPath);

    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  @Override
  public void save(FileStorageEnum fileStorageEnum, MultipartFile file) {
    try {
//      this.file1.createTempFile('/')
//      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

      if (fileStorageEnum.equals(FileStorageEnum.CUSTOMER)) {
        Files.copy(file.getInputStream(), this.customerFolderPath.resolve(file.getOriginalFilename()));
      } else {
        Files.copy(file.getInputStream(), this.itemFolderPath.resolve(file.getOriginalFilename()));
      }

    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public boolean update(FileStorageEnum fileStorageEnum, String oldFilename, MultipartFile newFile) throws IOException {
    Boolean isFileExist;
//    boolean isFileExist = Files.exists(Path.of(String.format("src/main/resources/static/images/%s", filename)));
    if (fileStorageEnum.equals(FileStorageEnum.CUSTOMER)) {
      isFileExist = Files.exists(Path.of(this.customerFolderPath.toString() + "/" + oldFilename));
    } else {
      isFileExist = Files.exists(Path.of(this.itemFolderPath.toString() + "/" + oldFilename));
    }

    if (isFileExist) {
//      Files.delete(Path.of(String.format("src/main/resources/static/images/%s", filename)));

      if (fileStorageEnum.equals(FileStorageEnum.CUSTOMER)) {
        Files.delete(Path.of(this.customerFolderPath.toString() + "/" + oldFilename));
        Files.copy(newFile.getInputStream(), this.customerFolderPath.resolve(newFile.getOriginalFilename()));
      } else {
        Files.delete(Path.of(this.itemFolderPath.toString() + "/" + oldFilename));
        Files.copy(newFile.getInputStream(), this.itemFolderPath.resolve(newFile.getOriginalFilename()));
      }

      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean delete(FileStorageEnum fileStorageEnum, String filename) throws IOException {
//    return Files.deleteIfExists(Path.of(String.format("src/main/resources/static/images/%s", filename)));

    if (fileStorageEnum.equals(FileStorageEnum.CUSTOMER)) {
      return Files.deleteIfExists(Path.of(this.customerFolderPath.toString() + "/" + filename));
    } else {
      return Files.deleteIfExists(Path.of(this.itemFolderPath.toString() + "/" + filename));
    }
  }

}
