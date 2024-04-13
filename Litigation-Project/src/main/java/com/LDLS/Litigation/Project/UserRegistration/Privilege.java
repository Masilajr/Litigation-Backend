package com.LDLS.Litigation.Project.UserRegistration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@Data
@Table(name = "privileges")
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private boolean view;
    private boolean editAdd;
    private boolean upload;
    private boolean generate;
    private boolean approve;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRegistration userRegistration;
}
