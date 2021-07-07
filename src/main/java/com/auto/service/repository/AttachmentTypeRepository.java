package com.auto.service.repository;
import com.auto.service.entity.AttachmentType;
import com.auto.service.entity.enums.AttachmentTypeEnum;
import com.auto.service.projection.CustomAttachmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

/**
 * Created by Pinup on 24.07.2019.
 */
@RepositoryRestResource(path = "attachmentType", collectionResourceRel = "list", excerptProjection = CustomAttachmentType.class)
public interface AttachmentTypeRepository extends JpaRepository<AttachmentType, UUID> {
    AttachmentType findByType(AttachmentTypeEnum type);

////    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
//    @Override
//    <S extends AttachmentType> S save(S s);
//
////    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
//    @Override
//    void deleteById(UUID uuid);
}
