package com.pokaboo.msm.controller;

import com.pokaboo.msm.service.MsmService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pokab
 */
@Api(value = "短信服务", tags = {"短信服务"})
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;
}
