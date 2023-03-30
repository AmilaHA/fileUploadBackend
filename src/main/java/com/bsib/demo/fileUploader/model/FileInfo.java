package com.bsib.demo.fileUploader.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "File")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileInfo extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String fileName;
    @NotNull
    private String filePath;
    @NotNull
    private String module;
    @NotNull
    private String majorType;
    @NotNull
    private String mainType;
    @NotNull
    private String email;
    private String text1;
    private String text2;
    private String text3;
    private String text4;

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", module='" + module + '\'' +
                ", majorType='" + majorType + '\'' +
                ", mainType='" + mainType + '\'' +
                ", text1='" + text1 + '\'' +
                ", text2='" + text2 + '\'' +
                ", text3='" + text3 + '\'' +
                ", text4='" + text4 + '\'' +
                '}';
    }
}
