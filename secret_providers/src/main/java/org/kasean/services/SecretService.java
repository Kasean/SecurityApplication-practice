package org.kasean.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecretService {

    private static final Logger LOGGER = LoggerFactory.getLogger("SecretService");


    private final RedisTemplate<String, String> redisTemplate;
    private final EncryptionService encoder;

    public SecretService(RedisTemplate<String, String> redisTemplate, EncryptionService encoder) {
        this.redisTemplate = redisTemplate;
        this.encoder = encoder;
    }

    public String createSecret(String rawSecret){
        var rawKey = UUID.randomUUID();
        var savedSecret = encoder.encrypt(rawSecret);

        saveData(rawKey.toString(), savedSecret);
        return "localhost:8080/showSecret/" + rawKey;
    }

    private void saveData(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getData(String key) {

        var secret = redisTemplate.opsForValue().get(key);
        if (secret != null) {
            var result = encoder.decrypt(secret);
            redisTemplate.delete(key);
            return result;

        }

        return "Sorry, this secret unavailable (already viewed).";
    }
}
