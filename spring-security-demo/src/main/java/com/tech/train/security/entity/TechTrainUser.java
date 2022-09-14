package com.tech.train.security.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import java.util.Map;

@Data
@NoArgsConstructor
@ToString(of = {"id", "userName"})
@Entity
@Table(name="tech_train_user", indexes = {
        @Index(name = "tech_train_user_in", unique = true, columnList = "userName")
})
public class TechTrainUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String userName;

    private String password;

    private String firstName;

    private  String lastName;

}
