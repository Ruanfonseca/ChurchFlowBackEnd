package com.system.churchflow.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String address;

    @Column(unique = true)
    private String cnpj;

    private byte picture;

    @OneToMany(mappedBy = "institution")
    private List<Member> members;

    @OneToMany(mappedBy = "institution")
    private List<Cells> cells;

}
