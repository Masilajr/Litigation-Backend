package com.LDLS.Litigation.Project.diary.Controller;

import com.LDLS.Litigation.Project.diary.service.DocumentService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping({"/api/documents/"})
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    public DocumentController() {
    }

    @PostMapping({"/upload"})
    public String uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("caseNumber") String caseNumber) throws IOException {
        this.documentService.uploadDocument(file, caseNumber);
        return "redirect:/";
    }

    @GetMapping({"/download/{id}"})
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        return this.documentService.downloadDocument(id);
    }
}
