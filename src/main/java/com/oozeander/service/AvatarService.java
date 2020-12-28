/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.service;

import com.oozeander.model.Avatar;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author b.ketrouci
 */
public interface AvatarService {

    Avatar get(Long id);

    Pair<Boolean, Avatar> save(MultipartFile file);
}
