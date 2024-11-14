package entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    private String id; 
    private String nome; 
    private String login; 
    private String email;
    private String cpf;
    private String telefone;
    private String password;

    public Usuario(String login, String email) {
        this("", "", login, email, "", "", "");
    }
}
