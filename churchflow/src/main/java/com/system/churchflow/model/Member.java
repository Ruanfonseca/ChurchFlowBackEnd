package com.system.churchflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String email;

    @Column(unique = true)
    private String cpf;

    private String dateOfBirth;

    private String Ecclesiasticalposition;

    private byte picture;

    @ManyToOne
    private Institution institution;

    @ManyToOne
    private Cells cells;

}
