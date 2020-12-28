/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.resource;

import com.oozeander.model.Avatar;
import com.oozeander.service.AvatarService;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author b.ketrouci
 */
@RestController
@RequestMapping("/api/avatars")
@CrossOrigin
public class AvatarResource {

    @Autowired
    private AvatarService avatarService;

    @Transactional
    @GetMapping(value = "/{id}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> get(@PathVariable("id") Long id, HttpServletRequest request) throws SQLException {
        Avatar avatar = avatarService.get(id);
        if (avatar != null) {
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(avatar.getFileExtension()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + avatar.getFileName() + "\"")
                    .body(new ByteArrayResource(avatar.getFileData().getBytes(1L, (int) avatar.getFileData().length())));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Avatar> saveAvatar(@RequestParam("file") MultipartFile file) {
        Pair<Boolean, Avatar> response = avatarService.save(file);
        if (response.getValue0()) {
            return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getValue1().getId()).toUri()).body(response.getValue1());
        }
        return ResponseEntity.badRequest().build();
    }
}
