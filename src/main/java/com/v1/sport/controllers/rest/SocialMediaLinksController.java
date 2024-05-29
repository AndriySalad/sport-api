package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.SocialMediaLinkDto;
import com.v1.sport.services.SocialMediaLinkService;
import com.v1.sport.utils.GenericMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/social-media-links")
public class SocialMediaLinksController {

    private final SocialMediaLinkService socialMediaLinkService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TRAINER')")
    public SocialMediaLinkDto createSocialMediaLink(@RequestBody SocialMediaLinkDto dto) {
        return socialMediaLinkService.createSocialMediaLink(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TRAINER')")
    public GenericMessage deleteSocialMediaLink(@PathVariable("id") Long id) {
        socialMediaLinkService.deleteSocialMediaLink(id);
        return new GenericMessage("Link deleted successfully");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TRAINER')")
    public SocialMediaLinkDto updateSocialMediaLink(@RequestBody SocialMediaLinkDto dto, @PathVariable("id") Long id){
        return socialMediaLinkService.updateSocialMediaLink(dto, id);
    }
}
