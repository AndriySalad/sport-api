package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.SocialMediaLinkDto;
import com.v1.sport.utils.GenericMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/social-media-links")
public class SocialMediaLinksController {

    @PostMapping("/{userId}")
    public GenericMessage createSocialMediaLink(@PathVariable("userId") Long userId, @RequestBody SocialMediaLinkDto dto) {

        return new GenericMessage("Social media link created successfully");
    }
}
