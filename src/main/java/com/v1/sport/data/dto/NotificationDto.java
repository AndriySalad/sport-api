package com.v1.sport.data.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long id;
    private String title;
    private String description;
    private boolean isViewed;
    private UserListItemDto sender;
    private UserListItemDto receiver;
    private Timestamp date;
    private String type;
}
