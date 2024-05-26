package com.v1.sport.services;

import com.v1.sport.data.dto.SocialMediaLinkDto;

public interface SocialMediaLinkService {

    SocialMediaLinkDto createSocialMediaLink(SocialMediaLinkDto dto);

    void deleteSocialMediaLink(Long linkId);

    SocialMediaLinkDto updateSocialMediaLink(SocialMediaLinkDto dto, Long socialMediaLinkId);
}
