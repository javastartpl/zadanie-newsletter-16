package com.ziola.shortenurl.service.impl;

import com.ziola.shortenurl.domain.LinkRequest;
import com.ziola.shortenurl.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckExpiredLinksService {

    private final LinkRepository repository;

    @Scheduled(cron = "0 0 * * * ?")
    public void updateLinks() {
        Calendar cal = Calendar.getInstance();
        List<LinkRequest> allLinks = repository.findAll();
        for (LinkRequest shortenLink : allLinks) {
            if (shortenLink.getExpiryDate().getTime() - cal.getTime().getTime() <= 0) {
                repository.delete(shortenLink);
            }
        }
    }
}
