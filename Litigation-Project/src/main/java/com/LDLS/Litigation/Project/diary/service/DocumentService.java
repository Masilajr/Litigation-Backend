package com.LDLS.Litigation.Project.diary.service;

import com.LDLS.Litigation.Project.diary.exception.ResourceNotFoundException;
import com.LDLS.Litigation.Project.diary.model.Document;
import com.LDLS.Litigation.Project.diary.repository.DocumentRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public DocumentService() {
    }

    public Document uploadDocument(MultipartFile file, String caseNumber) throws IOException {
        Document document = new Document();
        document.setName(file.getOriginalFilename());
        document.setContentType(file.getContentType());
        document.setData(file.getBytes());
        document.setNumCase(caseNumber);
        return (Document)this.documentRepository.save(document);
    }

    public ResponseEntity<Resource> downloadDocument(Long id) {
        Document document = (Document)this.documentRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundException("Document not found");
        });
        ByteArrayResource resource = new ByteArrayResource(document.getData());
        return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().contentType(MediaType.parseMediaType(document.getContentType())).header("Content-Disposition", new String[]{"attachment; filename=\"" + document.getName() + "\""})).body(resource);
    }
}
