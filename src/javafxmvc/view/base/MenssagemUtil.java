package javafxmvc.view.base;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MenssagemUtil {

    private static final Alert INFORMATION_MESSAGE;
    private static final Alert CONFIRMATION_MESSAGE;
    private static final Alert ERROR_MESSAGE;
    private static final Alert WARNING_MESSAGE;
    public static final boolean CONFIRMOU = true;
    public static final boolean CANCELOU = false;
    
    static {
        INFORMATION_MESSAGE = new Alert(Alert.AlertType.INFORMATION);
        INFORMATION_MESSAGE.setTitle("Menssagem de informação");
        ERROR_MESSAGE = new Alert(Alert.AlertType.ERROR);
        ERROR_MESSAGE.setTitle("Menssagem de Erro");
        WARNING_MESSAGE = new Alert(Alert.AlertType.WARNING);
        WARNING_MESSAGE.setTitle("Menssagem de Alerta");
        CONFIRMATION_MESSAGE = new Alert(Alert.AlertType.CONFIRMATION);
        CONFIRMATION_MESSAGE.setTitle("Menssagem de Confirmação");
    }

    public static void mensagemInformacao(String detalheMensagem) {
        mensagemInformacao("Informação", detalheMensagem);
    }

    public static void mensagemInformacao(String tipoMensagem, String detalheMensagem) {
        INFORMATION_MESSAGE.setHeaderText(tipoMensagem);
        INFORMATION_MESSAGE.setContentText(detalheMensagem);
        INFORMATION_MESSAGE.showAndWait();
    }

    public static void mensagemErro(String detalheMensagem) {
        mensagemErro("Erro", detalheMensagem);
    }

    public static void mensagemErro(String tipoMensagem, String detalheMensagem) {
        ERROR_MESSAGE.setHeaderText(tipoMensagem);
        ERROR_MESSAGE.setContentText(detalheMensagem);
        ERROR_MESSAGE.showAndWait();
    }

    public static void mensagemAlerta(String detalheMensagem) {
        mesagemAlerta("Atenção situação de alerta", detalheMensagem);
    }

    public static void mesagemAlerta(String tipoMensagem, String detalheMensagem) {
        WARNING_MESSAGE.setHeaderText(tipoMensagem);
        WARNING_MESSAGE.setContentText(detalheMensagem);
        WARNING_MESSAGE.showAndWait();
    }

    public static boolean mesagemConfirmacao(String detalheMensagem) {
        return mensagemConfirmacao("Responda a questão", detalheMensagem);
    }

    public static boolean mensagemConfirmacao(String tipoMensagem, String detalheMensagem) {
        CONFIRMATION_MESSAGE.setHeaderText(tipoMensagem);
        CONFIRMATION_MESSAGE.setContentText(detalheMensagem);
        Optional<ButtonType> result = CONFIRMATION_MESSAGE.showAndWait();
        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            return MenssagemUtil.CONFIRMOU;
        } else {
            return MenssagemUtil.CANCELOU;
        }
    }

}
