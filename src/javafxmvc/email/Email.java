package javafxmvc.email;

import javafxmvc.model.domain.Chave;
import javafxmvc.model.domain.Pessoa;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class Email {
    
    Pessoa pessoa = new Pessoa();
    Chave chave = new Chave();
    
    public void sendEmail() throws EmailException {

        SimpleEmail email = new SimpleEmail();
        //Utilize o hostname do seu provedor de email
        System.out.println("alterando hostname...");
        email.setHostName("smtp.gmail.com");
        //Quando a porta utilizada não é a padrão (gmail = 465)
        email.setSmtpPort(465);
        //Adicione os destinatários
        email.addTo(pessoa.getEmail(), pessoa.getNome());
        //Configure o seu email do qual enviará
        email.setFrom("ifmt.daee@gmail.com", "Departamento DAEE");
        //Adicione um assunto
        email.setSubject("Devolução Chave DAEE");
        //Adicione a mensagem do email
        email.setMsg("Este e-mail é para informar para devolver a chave do Laboratorio: "+chave.getIdentificador());
        //Para autenticar no servidor é necessário chamar os dois métodos abaixo
        System.out.println("autenticando...");
        email.setSSL(true);
        email.setAuthentication("ifmt.daee@gmail.com", "daeeifmt2017");
        System.out.println("enviando...");
        email.send();
        System.out.println("Email enviado!");
    }
}
