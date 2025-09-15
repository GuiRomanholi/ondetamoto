package br.com.fiap.ondetamoto.controller;


import br.com.fiap.ondetamoto.dto.AuthDTO;
import br.com.fiap.ondetamoto.dto.RegisterDTO;
import br.com.fiap.ondetamoto.model.Usuario;
import br.com.fiap.ondetamoto.repository.UsuarioRepository;
import br.com.fiap.ondetamoto.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO) {
        var userPwd = new UsernamePasswordAuthenticationToken(
                authDTO.email(),
                authDTO.senha()
        );
        var auth = this.authenticationManager.authenticate(userPwd);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (usuarioRepository.findByEmail(registerDTO.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String senhaCriptografada = new BCryptPasswordEncoder()
                .encode(registerDTO.senha());
        Usuario novoUsuario = new Usuario(
                registerDTO.email(),
                senhaCriptografada,
                registerDTO.role());
        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
