package com.example.ClientConnectivity.security.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.ClientConnectivity.model.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClientDetailsImpl implements UserDetails {

        private static final long serialVersionUID = 1L;

        private Long clientId;

        private String username;

        private String email;

        @JsonIgnore
        private String password;

        private Collection<? extends GrantedAuthority> authorities;

        public ClientDetailsImpl(Long clientId, String username, String email, String password,
                               Collection<? extends GrantedAuthority> authorities) {
            this.clientId = clientId;
            this.username = username;
            this.email = email;
            this.password = password;
            this.authorities = authorities;
        }

        public static ClientDetailsImpl build(Client user) {
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                    .collect(Collectors.toList());

            return new ClientDetailsImpl(
                    user.getClientId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    authorities);
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        public Long getClientId() {
            return clientId;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            ClientDetailsImpl user = (ClientDetailsImpl) o;
            return Objects.equals(clientId, user.clientId);
        }
}
