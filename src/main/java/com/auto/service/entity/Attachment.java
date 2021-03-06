package com.auto.service.entity;

import com.auto.service.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Attachment extends AbsEntity {

    private String name;
    private String contentType;
    private long size;
    @ManyToOne(fetch = FetchType.LAZY)
    private AttachmentType attachmentType;

    public Attachment(String name, String contentType, long size) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
    }
}
