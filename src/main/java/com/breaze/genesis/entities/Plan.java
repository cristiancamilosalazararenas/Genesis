package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "plans")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "subscriptions")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "tokens_granted", nullable = false)
    private Integer tokensGranted;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "creation_date", insertable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(name = "update_date", insertable = false, updatable = false)
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "plan")
    private List<Subscription> subscriptions;
}
