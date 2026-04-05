package com.rashid.backend.model;

import jakarta.persistence.*;
import lombok.Data;

// import java.util.ArrayList;
// import java.util.List;

@Entity
@Table(name = "teams")
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
}
