package com.ilim.app.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "phonecall")
public class PhoneCall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "caller_id", referencedColumnName = "id", nullable = false)
    private User caller;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id", nullable = false)
    private User receiver;

    @Column(name = "call_time", nullable = false)
    private String callTime;

    @Column(name = "duration")
    private int duration;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCaller() {
        return caller;
    }

    public void setCaller(User caller) {
        this.caller = caller;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    // toString
    @Override
    public String toString() {
        return "PhoneCall{" +
                "id=" + id +
                ", caller=" + caller +
                ", receiver=" + receiver +
                ", callTime='" + callTime + '\'' +
                ", duration=" + duration +
                '}';
    }

    // equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneCall phoneCall = (PhoneCall) o;
        return Objects.equals(id, phoneCall.id);
    }

    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
