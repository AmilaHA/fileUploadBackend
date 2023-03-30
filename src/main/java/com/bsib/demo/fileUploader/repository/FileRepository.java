package com.bsib.demo.fileUploader.repository;

import com.bsib.demo.fileUploader.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, Long> {
}
