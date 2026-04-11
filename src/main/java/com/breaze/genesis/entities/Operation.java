package com.breaze.genesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "operations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "transactions")
public class Operation {

    @Id
    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cost_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal costBase;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "operation")
    private List<Transaction> transactions;
}
