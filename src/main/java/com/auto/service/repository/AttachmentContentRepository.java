package com.auto.service.repository;


import com.auto.service.entity.Attachment;
import com.auto.service.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {

AttachmentContent getByAttachment(Attachment attachment);
    Optional<AttachmentContent> findByAttachment(Attachment attachment);
}
