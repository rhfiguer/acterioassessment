package com.acterio.assessmentro.services;

import com.acterio.assessmentro.models.User;
import com.acterio.assessmentro.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailDomainServiceImpl implements EmailDomainService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, Long> countDomainTypes() {
        Map<String, Long> domainCounts = new HashMap<>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            String email = user.getEmail();
            String domain = email.substring(email.indexOf("@") + 1);

            if (!domainCounts.containsKey(domain)) {
                domainCounts.put(domain, 0L);
            }
            domainCounts.put(domain, domainCounts.get(domain) + 1L);
        }

        return domainCounts;
    }
}

