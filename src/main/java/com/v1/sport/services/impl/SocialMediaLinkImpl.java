package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.SocialMediaLinkDto;
import com.v1.sport.data.models.SocialMediaLink;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.SocialMediaLinkRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.SocialMediaLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialMediaLinkImpl implements SocialMediaLinkService {

    private final UserRepository userRepository;
    private final SocialMediaLinkRepository socialMediaLinkRepository;

    @Override
    @Transactional
    public SocialMediaLinkDto createSocialMediaLink(SocialMediaLinkDto dto) {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        SocialMediaLink link = SocialMediaLink.builder()
                .link(dto.getLink())
                .title(dto.getTitle())
                .user(user)
                .build();
        SocialMediaLink newLink = socialMediaLinkRepository.save(link);
        return SocialMediaLinkDto.builder()
                .id(newLink.getId())
                .link(newLink.getLink())
                .title(newLink.getTitle())
                .build();
    }

    @Override
    public void deleteSocialMediaLink(Long linkId) {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        SocialMediaLink link = socialMediaLinkRepository.findByIdAndUser(linkId, user).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        socialMediaLinkRepository.delete(link);
    }

    @Override
    public SocialMediaLinkDto updateSocialMediaLink(SocialMediaLinkDto dto, Long socialMediaLinkId) {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        SocialMediaLink link = socialMediaLinkRepository.findByIdAndUser(socialMediaLinkId, user).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        link.setLink(dto.getLink());
        link.setTitle(dto.getTitle());
        SocialMediaLink newLink = socialMediaLinkRepository.save(link);
        return SocialMediaLinkDto.builder()
                .id(newLink.getId())
                .link(newLink.getLink())
                .title(newLink.getTitle())
                .build();
    }
}
