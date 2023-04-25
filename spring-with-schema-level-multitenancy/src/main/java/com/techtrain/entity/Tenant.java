package com.techtrain.entity;

import com.techtrain.constants.TenantType;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "type"})
@Entity
@Table(name = "TENANT", schema="public", indexes = {
        @Index(name = "tenant_unq", unique = true, columnList = "id")
})
public class Tenant {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private TenantType type;

    @Column(name = "ENABLED")
    private Boolean enabled;
    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_ON")
    private Long createdOn;

    @Column(name = "LAST_UPDATED_BY")
    private Long lastUpdatedBy;

    @Column(name = "LAST_UPDATED_ON")
    private Long lastUpdatedOn;

}
