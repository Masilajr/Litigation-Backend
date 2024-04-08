package com.LDLS.Litigation.Project.BillingModule.Entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {


    @Id
    @GeneratedValue
    private Long id;
}
