package com.etoile.app.Entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ncs_member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mid;

    @Column(nullable = false, length = 32)
    private String mobile;

    @Column(nullable = false, length = 32)
    private String name;

    @Column(nullable = false, length = 12)
    private String passcode;

    @Column(length = 1)
    private String gender;

    @Column(length = 10)
    private String birthday;

    @Column(length = 32)
    private String school;

    private Integer level;

    @Column(length = 19)
    private String loginTm;

    @Column(length = 19)
    private String logoutTm;

    @Column(length = 32)
    private String email;

    @Column(length = 1)
    private String absence;

    @Column(length = 16)
    private String ipaddr;

    @Lob
    private String address;

    @Column(nullable = false, length = 1)
    private String active;
    
    @OneToMany(mappedBy="student",cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Student> arStudent;
    
    @OneToMany(mappedBy="member",cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Member> arMember;
    

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime created;

    @Column(nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updated;
}
