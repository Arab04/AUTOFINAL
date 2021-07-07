package com.auto.service.projection;


import com.auto.service.entity.AttachmentType;
import org.springframework.data.rest.core.config.Projection;

import java.util.UUID;

@Projection(name = "customAttachmentType", types = AttachmentType.class)
public interface CustomAttachmentType {
    UUID getId();
    String getContentTypes();
    Integer getWidth();
    Integer getHeight();
    String getType();
}
