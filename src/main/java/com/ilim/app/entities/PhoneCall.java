package com.ilim.app.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@Entity
@Table(name = "phonecalls")
@NoArgsConstructor
public class PhoneCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO : aradaki ilişkiye bakmam gerek !!
    @ManyToOne
    @JoinColumn(name = "caller_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private UserEntity caller;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private UserEntity receiver;

    @Column(name = "call_time", nullable = false)
    private LocalDateTime callTime;

    @Column(name = "duration")
    private int duration;}

