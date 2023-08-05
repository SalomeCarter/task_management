package com.example.task_management.entity;

import com.example.task_management.enums.Priority;
import com.example.task_management.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String header;

    private String description;


    private LocalDate createdAt;


    @Enumerated(EnumType.STRING)
    private Status status;


    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    private Goal goal;

    @ElementCollection
    private List<byte[]> photos;

    @ElementCollection
    private List<String> comments = new ArrayList<>();

    @ElementCollection
    private List<String> fileAttachments = new ArrayList<>();

}
