package com.isra.security.dependency_analyzer.Controllers;

import com.isra.security.dependency_analyzer.Services.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;
}
