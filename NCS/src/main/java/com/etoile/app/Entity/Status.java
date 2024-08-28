package com.etoile.app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "ncs_status")
@Getter
@Setter
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dsid;

    @Column(nullable = false, length = 12)
    private String status;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "drill_id")
    private Drill drill;

    @Column(name = "eid", columnDefinition = "int unsigned")
    private Integer eid;
    
    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updated;

}
