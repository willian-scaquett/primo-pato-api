package com.primopato.api.entity;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void testGettersAndSetters() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsuario("user123");
        usuario.setSenha("senhaSegura");
        usuario.setNome("Willian");

        assertEquals(1L, usuario.getId());
        assertEquals("user123", usuario.getUsuario());
        assertEquals("senhaSegura", usuario.getSenha());
        assertEquals("Willian", usuario.getNome());
    }

    @Test
    void testGetAuthoritiesRetornaListaVazia() {
        Usuario usuario = new Usuario();
        Collection<? extends GrantedAuthority> authorities = usuario.getAuthorities();

        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void testUserDetailsMethodsSempreTrue() {
        Usuario usuario = new Usuario();
        assertTrue(usuario.isAccountNonExpired());
        assertTrue(usuario.isAccountNonLocked());
        assertTrue(usuario.isCredentialsNonExpired());
        assertTrue(usuario.isEnabled());
    }

    @Test
    void testGetPasswordAndUsername() {
        Usuario usuario = new Usuario();
        usuario.setUsuario("admin");
        usuario.setSenha("123456");

        assertEquals("123456", usuario.getPassword());
        assertEquals("admin", usuario.getUsername());
    }

    @Test
    void testListaDePatos() {
        Usuario usuario = new Usuario();
        Pato pato1 = new Pato();
        Pato pato2 = new Pato();

        usuario.setPatos(List.of(pato1, pato2));

        assertEquals(2, usuario.getPatos().size());
        assertSame(pato1, usuario.getPatos().get(0));
        assertSame(pato2, usuario.getPatos().get(1));
    }
}
