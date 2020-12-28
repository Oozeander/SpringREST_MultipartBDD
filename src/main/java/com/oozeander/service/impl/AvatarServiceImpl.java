/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.service.impl;

import com.oozeander.model.Avatar;
import com.oozeander.repository.AvatarRepository;
import com.oozeander.service.AvatarService;
import org.hibernate.engine.jdbc.BlobProxy;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author b.ketrouci
 */
@Service("avatarService")
@Transactional
public class AvatarServiceImpl implements AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    @Override
    public Avatar get(Long id) {
        return avatarRepository.findOne(id);
    }

    @Override
    public Pair<Boolean, Avatar> save(MultipartFile file) {
        Boolean isSaved = Boolean.TRUE;
        Avatar avatar = null;
        if (!file.isEmpty()) {
            avatar = new Avatar();
            try {
                avatar.setFileName(file.getOriginalFilename());
                avatar.setFileExtension(file.getContentType());
                avatar.setFileSize(String.valueOf(file.getSize() / 1024) + " Ko");
                avatar.setFileData(BlobProxy.generateProxy(file.getBytes()));
                avatarRepository.saveAndFlush(avatar);
            } catch (Exception exception) {
                isSaved = Boolean.FALSE;
                System.out.println("Error in saving Avatar, cause : " + exception.getLocalizedMessage());
            }
        } else {
            isSaved = Boolean.FALSE;
        }
        return Pair.with(isSaved, avatar);
    }
}
