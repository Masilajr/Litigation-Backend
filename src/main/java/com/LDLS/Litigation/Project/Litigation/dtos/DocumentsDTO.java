package com.LDLS.Litigation.Project.Litigation.dtos;
import com.LDLS.Litigation.Project.Litigation.models.CourtCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsDTO {
    private String name;
    private String contentType;
    private byte[] content;
    @ManyToOne
    @JoinColumn(name = "court_case_id")
    private CourtCase courtCase;

}
