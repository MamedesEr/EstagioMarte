package aplicacao;

import org.apache.commons.mail.EmailException;

/**
 *
 * @author Mattheus
 */
public class Principal {
    public static void main(String[] args) throws EmailException {
        Email email = new Email();
        email.sendEmail();
    }
}
