package com.etoile.app.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ncs_drilltype")
@Getter
@Setter
public class Drilltype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dtid;

    @Column(nullable = false, length = 32)
    private String typename;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updated;
    
    @OneToMany(mappedBy="drilltype",cascade=CascadeType.ALL,orphanRemoval=true)
    private List<Drill> arDrill;

}