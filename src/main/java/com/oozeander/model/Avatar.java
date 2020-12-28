/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author b.ketrouci
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avatars", schema = "default_schema")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"id", "fileData"})
@JsonPropertyOrder({"fileName", "fileExtension", "fileSize"})
public class Avatar {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("file_name")
    @Column(name = "file_name")
    private String fileName;
    @JsonProperty("file_extension")
    @Column(name = "file_extension")
    private String fileExtension;
    @JsonProperty("file_size")
    @Column(name = "file_size")
    private String fileSize;
    @Column(name = "file_data")
    @Lob
    private Blob fileData;
}
