package dev.weverton.ecommerce.controller;

import dev.weverton.ecommerce.security.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    private final JWT jwt;

    @Autowired
    public TokenController(JWT jwt) {
        this.jwt = jwt;
    }

    @GetMapping("/generate")
    public ResponseEntity<Object> generateToken() {
        String username = "exampleUser";

        String token = jwt.generateToken(username);

        return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(token));
    }

    static class TokenResponse {
        private final String jwt_token;

        public TokenResponse(String token) {
            this.jwt_token = token;
        }

        public String getJwt_token() {
            return jwt_token;
        }
    }
}
