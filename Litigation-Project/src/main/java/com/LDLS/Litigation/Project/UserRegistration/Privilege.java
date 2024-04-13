package com.LDLS.Litigation.Project.UserRegistration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@Table(name = "privilege")
public class Privilege {
    private String name;
    private boolean view;
    private boolean editAdd;
    private boolean upload;
    private boolean generate;
    private boolean approve;
}

