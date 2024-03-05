package com.system.churchflow.model;

import com.system.churchflow.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)  // Usar GenerationType.AUTO para permitir a geração automática de UUID
    private String id;

    @Column(unique = true)  // Adicionar esta anotação para garantir unicidade
    private String login;

    private String password;

    private String tokenInUse;

    private byte[] picture;
    private String name;
    private UserRole role;

    public User(String login, String encryptedPassword, String name, UserRole role) {
        this.login = login;
        this.password = encryptedPassword;
        this.name = name;
        this.role = role;
    }

    public User (String login , String password){
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return password;  // Corrigir para retornar a senha em vez do email
    }

    @Override
    public String getUsername() {
        return login;  // Corrigir para retornar o email como o nome de usuário
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Definir para true se a conta não estiver expirada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Definir para true se a conta não estiver bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Definir para true se as credenciais não estiverem expiradas
    }

    @Override
    public boolean isEnabled() {
        return true;  // Definir para true se a conta estiver habilitada
    }
}
