package com.etoile.app.Entity;
import java.time.LocalDate;
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

@Getter
@Setter
@Entity
@Table(name = "ncs_course")  // 실제 테이블 이름을 여기에 작성하세요.
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private int cid;

    @Column(name = "title", length = 64)
    private String title;

    @Column(name = "orgname", length = 64)
    private String orgname;

    @Column(name = "period1")
    private LocalDate period1;

    @Column(name = "days", precision = 3, scale = 0)
    private int days;

    @Column(name = "period2")
    private LocalDate period2;

    @Column(name = "endtime", length = 256)
    private String endtime;

    @Column(name = "seat_cnt")
    private int seatCnt;

    @Column(name = "alive", length = 3)
    private String alive;

    @Column(name = "col_cnt")
    private int colCnt;

    @Column(name = "created", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @Column(name = "updated", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updated;
    
    @OneToMany(mappedBy="course",cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Student> students;
    
    @OneToMany(mappedBy="course",cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Drill> drills;

}
