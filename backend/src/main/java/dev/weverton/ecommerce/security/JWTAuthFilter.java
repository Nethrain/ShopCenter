package dev.weverton.ecommerce.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.weverton.ecommerce.dto.CreadenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWT jwt;

    public JWTAuthFilter(AuthenticationManager authenticationManager, JWT jwt){
        this.authenticationManager = authenticationManager;
        this.jwt = jwt;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try{
            //Pega as credenciais da request
            CreadenciaisDTO credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), CreadenciaisDTO.class);

            //Transforma num token do security
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credentials.getEmail(), credentials.getSenha(), new ArrayList<>());

            //tenta authenticar
            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //Método chamado quando a authenticacao e bem sucedida
        String username = ((User) authResult.getPrincipal()).getUsername();
        String token = jwt.generateToken(username);
        response.addHeader("Authorization", "Bearer " + token);
    }
}
