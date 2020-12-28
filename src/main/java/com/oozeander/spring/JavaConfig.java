/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oozeander.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 *
 * @author b.ketrouci
 */
@Configuration
@ComponentScan(basePackages = {"com.oozeander.service", "com.oozeander.repository",
    "com.oozeander.resource"})
@Import({RestConfig.class, DataJpaConfig.class})
public class JavaConfig {
}
