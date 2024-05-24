package com.v1.sport.services;

import com.v1.sport.data.dto.SocialMediaLinkDto;

public interface SocialMediaLinkService {

    void createSocialMediaLink(Long userId, SocialMediaLinkDto dto);

    void deleteSocialMediaLink(Long userId, Long linkId);

    void updateSocialMediaLink(Long userId, SocialMediaLinkDto dto);
}
