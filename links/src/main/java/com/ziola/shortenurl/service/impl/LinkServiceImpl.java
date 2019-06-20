package com.ziola.shortenurl.service.impl;

import com.ziola.shortenurl.domain.LinkRequest;
import com.ziola.shortenurl.repository.LinkRepository;
import com.ziola.shortenurl.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkServiceImpl implements LinkService {
    
    private final LinkRepository shortenLinkRepository;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    public LinkRequest createShortenLink(String link) {
        LinkRequest shortenLink = new LinkRequest();
        shortenLink.setFullLengthLink(link);
        shortenLink.setVisits(0L);
        shortenLink.setShortenLink(generateShortenLink());
        shortenLink.setPassword(randomAlphaNumeric(3));
        return shortenLinkRepository.save(shortenLink);
    }

    @Override
    public LinkRequest findAndUpdate(String link) {
        LinkRequest request = shortenLinkRepository.findByShortenLink(link);
        request.updateVisits();
        return shortenLinkRepository.save(request);
    }

    @Override
    public String generatePassword() {
        return randomAlphaNumeric(3);
    }

    private String generateShortenLink() {
        String result = randomAlphaNumeric(6);
        LinkRequest temp = shortenLinkRepository.findByShortenLink(result);
        if (temp != null) {
            result = randomAlphaNumeric(6);
        }
        return result;
    }

    private static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
