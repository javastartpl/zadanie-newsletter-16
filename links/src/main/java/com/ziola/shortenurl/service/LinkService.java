package com.ziola.shortenurl.service;

import com.ziola.shortenurl.domain.LinkRequest;

public interface LinkService {

    LinkRequest createShortenLink(String link);

    LinkRequest findAndUpdate(String link);

    String generatePassword();
}
