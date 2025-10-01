package br.com.fiap.ondetamoto.service;


import br.com.fiap.ondetamoto.dto.RegisterDTO;
import br.com.fiap.ondetamoto.exception.EmailAlreadyExistsException;
import br.com.fiap.ondetamoto.model.UserRole;
import br.com.fiap.ondetamoto.model.Usuario;
import br.com.fiap.ondetamoto.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario registerUser(RegisterDTO data) {
        if (usuarioRepository.findByEmail(data.email()) != null) {
            throw new EmailAlreadyExistsException("Este e-mail já está cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(data.senha());

        UserRole userRoleEnum = UserRole.valueOf(data.role().toUpperCase());

        Usuario newUser = new Usuario(data.email(), encryptedPassword, userRoleEnum);

        return usuarioRepository.save(newUser);
    }

    @Transactional
    @CachePut(value = "usuarios", key = "#result.id")
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Cacheable(value = "usuarios", key = "#id")
    public Usuario readUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Cacheable(value = "usuarios", key = "'todos'")
    public List<Usuario> readUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    @CachePut(value = "usuarios", key = "#result.id")
    public Usuario updateUsuario(Long id, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isEmpty()) {
            return null;
        }

        Usuario usuarioExistente = usuarioOptional.get();

        usuarioAtualizado.setId(id);

        if (!passwordEncoder.matches(usuarioAtualizado.getSenha(), usuarioExistente.getSenha())) {
            usuarioAtualizado.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        } else {
            usuarioAtualizado.setSenha(usuarioExistente.getSenha());
        }

        return usuarioRepository.save(usuarioAtualizado);
    }

    @Transactional
    @CacheEvict(value = "usuarios", key = "#id")
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
        limparCacheTodosUsuarios();
    }

    @CacheEvict(value = "usuarios", key = "'todos'")
    public void limparCacheTodosUsuarios() {}

    @CacheEvict(value = "usuarios", allEntries = true)
    public void limparTodoCacheUsuarios() {}

}