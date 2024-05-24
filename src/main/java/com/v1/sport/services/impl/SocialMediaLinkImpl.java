package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.SocialMediaLinkDto;
import com.v1.sport.data.models.SocialMediaLink;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.SocialMediaLinkRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.SocialMediaLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialMediaLinkImpl implements SocialMediaLinkService {

    private final UserRepository userRepository;
    private final SocialMediaLinkRepository socialMediaLinkRepository;

    @Override
    @Transactional
    public void createSocialMediaLink(Long userId, SocialMediaLinkDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        SocialMediaLink link = SocialMediaLink.builder()
                .link(dto.getLink())
                .title(dto.getTitle())
                .user(user)
                .build();
        socialMediaLinkRepository.save(link);

    }

    @Override
    public void deleteSocialMediaLink(Long userId, Long linkId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        SocialMediaLink link = socialMediaLinkRepository.findByIdAndUser(linkId, user).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        socialMediaLinkRepository.delete(link);

    }

    @Override
    public void updateSocialMediaLink(Long userId, SocialMediaLinkDto dto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        SocialMediaLink link = socialMediaLinkRepository.findByIdAndUser(dto.getId(), user).orElseThrow(() -> new EntityNotFoundException("Link not found"));
        link.setLink(dto.getLink());
        link.setTitle(dto.getTitle());
        socialMediaLinkRepository.save(link);
    }
}
