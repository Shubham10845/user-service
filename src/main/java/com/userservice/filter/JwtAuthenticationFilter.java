package com.userservice.filter;

import com.userservice.constants.ApplicationConstants;
import com.userservice.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Set;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Set<String> EXCLUDED_PATHS = Set.of("/user/login", "/user/signup");
    UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        SecretKey secretKey = Keys.hmacShaKeyFor(ApplicationConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        if(secretKey!=null){
            String jwt = token.substring(7).trim();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String username = String.valueOf(claims.get("email"));
            System.out.println(username);
            String authorities = String.valueOf(claims.get("role"));
            if(userRepository.findByEmail(username).isPresent()){
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                throw new RuntimeException("Password Thik Se Dalo");
            }
        }
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDED_PATHS.contains(request.getServletPath());
    }
}
