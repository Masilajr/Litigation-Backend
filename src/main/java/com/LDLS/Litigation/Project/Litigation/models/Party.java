package com.LDLS.Litigation.Project.Litigation.models;
import lombok.Data;
import javax.persistence.*;
@Data
@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "court_case_id")
    private CourtCase courtCase;

}
