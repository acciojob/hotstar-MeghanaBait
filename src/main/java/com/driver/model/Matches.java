package com.driver.model;

import javax.persistence.*;

@Entity
@Table
public class Matches {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String team1;
    private String team2;
    private Integer duration;

    private SubscriptionType subscriptionType;

    public Matches() {

    }

    public Matches(Integer id, String team1, String team2, Integer duration, SubscriptionType subscriptionType) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
        this.duration = duration;
        this.subscriptionType = subscriptionType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}
