package com.techtrain.tenant.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mtusers", indexes = {
        @Index(name = "user_unq", unique = true, columnList = "id")
})
public class MTUser {

    @Id
    @Column(name = "ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "firstname")

    private String firstName;

    @Column(name = "lastname")
    private String lastName;

}
