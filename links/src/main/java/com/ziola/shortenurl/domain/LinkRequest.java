package com.ziola.shortenurl.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Table(name = "links")
public class LinkRequest {

    private static final int EXPIRATION = 24*60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_id")
    private Long id;
    @Column(name = "full_length_link")
    private String fullLengthLink;
    @Column(name = "shorten_link")
    private String shortenLink;
    @Column(name = "visits")
    private Long visits;
    @Column(name = "creation_date")
    private Date expiryDate;
    @Column(name = "password")
    private String password;

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, LinkRequest.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public LinkRequest() {
        expiryDate = calculateExpiryDate();
    }

    public void updateVisits() {
        this.visits = visits + 1;
    }
}
