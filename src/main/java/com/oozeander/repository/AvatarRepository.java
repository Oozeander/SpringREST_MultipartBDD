/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.repository;

import com.oozeander.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author b.ketrouci
 */
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
