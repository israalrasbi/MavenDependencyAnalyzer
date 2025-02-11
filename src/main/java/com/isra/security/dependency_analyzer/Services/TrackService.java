package com.isra.security.dependency_analyzer.Services;

import com.isra.security.dependency_analyzer.DTOs.VulnerabilityResponse;
import com.isra.security.dependency_analyzer.Interfaces.DependencyTrackClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrackService {
    @Value("${dependency.track.api.url}")
    private String dependencyTrackApiUrl;

    @Value("${dependency.track.api.key}")
    private String apiKey;

    private RestTemplate restTemplate;
}
