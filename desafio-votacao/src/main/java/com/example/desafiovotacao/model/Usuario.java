package com.example.desafiovotacao.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long usuarioId;

    @Column(name = "nome_usuario")
    private String nome;
    
    @Column(name = "cpf_usuario")
    private String cpf;
    
    @Column(name = "email_usuario")
    private String email;
    
    @Column(name = "senha_usuario")
    private String senha;

    @Column(name = "admin_usuario")
    private boolean admin;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Votacao> votacoes;

    
}
