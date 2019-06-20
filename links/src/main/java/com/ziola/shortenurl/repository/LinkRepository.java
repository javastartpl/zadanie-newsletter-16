package com.ziola.shortenurl.repository;

import com.ziola.shortenurl.domain.LinkRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<LinkRequest, Long> {

    LinkRequest findByShortenLink(String result);

    LinkRequest findByPassword(String password);
}
