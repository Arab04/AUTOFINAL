package com.auto.service.service;

import com.auto.service.entity.Attachment;
import com.auto.service.entity.AttachmentContent;
import com.auto.service.entity.User;
import com.auto.service.payloads.ApiResponseModel;
import com.auto.service.payloads.ResUploadFile;
import com.auto.service.repository.AttachmentContentRepository;
import com.auto.service.repository.AttachmentRepository;
import com.auto.service.repository.AttachmentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import uz.pdp.appbootcampg6server.exception.ResourceNotFoundException;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;
    @Autowired
    AttachmentTypeRepository attachmentTypeRepository;

    @Transactional
    public ApiResponseModel uploadFile(MultipartHttpServletRequest request, User user) throws IOException {
        Iterator<String> itr = request.getFileNames();
        MultipartFile file;
        List<ResUploadFile> resUploadFiles = new ArrayList<>();
        while (itr.hasNext()) {
            file = request.getFile(itr.next());
            Attachment attachment;
            try{
                Attachment attachment1=new Attachment(file.getOriginalFilename(), file.getContentType(), file.getSize());
                 attachment= attachmentRepository.save(attachment1);
            }
       catch (Exception e){
           attachment = attachmentRepository.save(new Attachment(file.getOriginalFilename(), file.getContentType(), file.getSize()));

       }
            try {
                attachmentContentRepository.save(new AttachmentContent(attachment, file.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            resUploadFiles.add(new ResUploadFile(attachment.getId(),
                    attachment.getName(),
                    ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/").path(attachment.getId().toString()).toUriString(),
                    attachment.getContentType(),
                    attachment.getSize()));

        }
        return new ApiResponseModel(true, "", resUploadFiles);
    }

    public HttpEntity<?> getAttachmentContent(UUID attachmentId, HttpServletResponse response) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(() -> new ResourceNotFoundException("Attachment", "id", attachmentId));
        AttachmentContent attachmentContent = attachmentContentRepository.findByAttachment(attachment).orElseThrow(() -> new ResourceNotFoundException("Attachment content", "attachment id", attachmentId));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .body(attachmentContent.getContent());
    }

}
