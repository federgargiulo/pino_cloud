package it.pliot.equipment.conf.security;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiKeyService {
    private static final List<String> VALID_KEYS = List.of("123456", "abcdef"); // Lista statica di API Key valide

    public boolean isValid(String apiKey) {
        return VALID_KEYS.contains(apiKey);
    }
}