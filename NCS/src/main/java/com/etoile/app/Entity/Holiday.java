package com.etoile.app.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "ncs_holiday")
@Getter
@Setter
public class Holiday {

    @Id
    @Column(name = "holidate", nullable = false)
    private LocalDate holidate;

    @Column(length = 20)
    private String comment;

}
