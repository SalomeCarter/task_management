package com.example.task_management.service;

import com.example.task_management.entity.Attachment;
import com.example.task_management.repo.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttachmentService {
    @Autowired
    private AttachmentRepository attachmentRepository;

    public void save(Attachment attachment) {
        attachmentRepository.save(attachment);
    }
}
