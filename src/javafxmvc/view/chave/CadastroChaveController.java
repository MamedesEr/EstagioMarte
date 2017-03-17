/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmvc.view.chave;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.ChaveDAO;
import javafxmvc.view.base.MenssagemUtil;
import javafxmvc.view.base.OpCadastroEnum;

/**
 * FXML Controller class
 *
 * @author Junior
 */
public class CadastroChaveController implements Initializable {
    
//---------Classes de Negócio e Controle da Lógica----------
    private ChaveDAO subsisEndereco;
    private OpCadastroEnum opCadastro;
    private Stage palcoOrigem;
    //private List<ChaveVO> listaChave;
    
    //---------Componentes Visuais---------
    @FXML
    private Button botaoIncluir;
    @FXML
    private Button botaoAlterar;
    @FXML
    private Button botaoExcluir;
    @FXML
    private Button botaoSalvar;
    @FXML
    private Button botaoCancelar;
    @FXML
    private Button botaoSair;
    @FXML
    private Label labelRodape;
    @FXML
    private TabPane tabDados;
    @FXML
    private GridPane gridCampos;
    @FXML
    private TextField campoCodigo;
    @FXML
    private TextField campoNome;
    @FXML
    private TextField campoStatus;
    @FXML
    private TableView tabelaDados;
    @FXML
    private TextField campoPesquisaNome;
    
        public CadastroChaveController(){
        this.gridCampos.setDisable(true);
        this.campoCodigo.setDisable(true);
        this.opCadastro = OpCadastroEnum.CONSULTAR;

        this.TrataBotoes();
        this.iniciarDadosTableView();
        }
    
        public void setPalcoOrigem(Stage palcoOrigem) {
        this.palcoOrigem = palcoOrigem;
    }
    
     //=====================Trata a Lógica da Interface de Cadastro=========
    private void iniciarDadosTableView() {
        //this.listaCidades = this.subsisEndereco.cidadeBuscarPorNome("");
        //this.listaBairro = this.subsisEndereco.bairroBuscarPorNome("");

        TableColumn coluna1 = new TableColumn("Codigo");
        TableColumn coluna2 = new TableColumn("Identificador");
        TableColumn coluna3 = new TableColumn("Status");
        TableColumn coluna4 = new TableColumn("Descrição");
        coluna1.setMinWidth(100);
        coluna2.setMinWidth(350);
        coluna3.setMinWidth(50);
        coluna1.setCellValueFactory(new PropertyValueFactory("id"));
        coluna2.setCellValueFactory(new PropertyValueFactory("identificador"));
        coluna3.setCellValueFactory(new PropertyValueFactory("status"));
        coluna4.setCellValueFactory(new PropertyValueFactory("descricao"));
        this.tabelaDados.getColumns().addAll(coluna1, coluna2, coluna3, coluna4);
        //this.tabelaDados.setItems(FXCollections.observableArrayList(this.listaBairro));

        
    }

    private void atualizarDadosTableView() {
        this.tabelaDados.getItems().clear();
      //  this.tabelaDados.setItems(FXCollections.observableArrayList(this.listaBairro));
        this.tabelaDados.refresh();
    }

//    private ChaveVO obterVOTableView() {
//        BairroVO bairroVO = null;
//        if (this.tabelaDados.getSelectionModel().getSelectedItem() != null) {
//            TableView.TableViewSelectionModel selectionModel = this.tabelaDados.getSelectionModel();
//            int pos = selectionModel.getSelectedIndex();
//            bairroVO = (BairroVO) selectionModel.getSelectedItem();
//        }
//        return bairroVO;
//    }

    private void iniciarInclusao() {
        this.opCadastro = OpCadastroEnum.INCLUIR;
        this.TrataBotoes();
        this.limparCampos();
        this.tabDados.getSelectionModel().selectLast();
        this.gridCampos.setDisable(false);
        this.campoNome.requestFocus();
        this.labelRodape.setText("Inclusão em andamento...");
    }

    private void iniciarAlteracao() {
       // BairroVO bairroVO = this.obterVOTableView();
//        if (bairroVO != null) {
//            bairroVO = this.subsisEndereco.bairroBuscarPorID(bairroVO.getId());
//            if (bairroVO != null) {
//                this.opCadastro = OpCadastroEnum.ALTERAR;
//                this.TrataBotoes();
//                this.preecheCampos(bairroVO);
                this.tabDados.getSelectionModel().selectLast();
                this.gridCampos.setDisable(false);
                this.campoNome.requestFocus();
                this.labelRodape.setText("Alteracao em andamento...");
//            } else {
//                MenssagemUtil.mensagemAlerta("Cidade não localizada!!");
//            }
//        } else {
//            MenssagemUtil.mensagemAlerta("Nenhum item selecionado!!");
//        }
    }

    private void processarInclusao() {
//        RetornoNegocio retorno;
//        BairroVO bairroVO = this.criarVODados();
//        if (bairroVO != null) {
//            retorno = this.subsisEndereco.bairroInserir(bairroVO);
//            if (retorno.isValido()) {
//                this.opCadastro = OpCadastroEnum.SALVAR;
//                this.TrataBotoes();
//                this.gridCampos.setDisable(true);
//                this.labelRodape.setText(retorno.getMsgRetorno());
//                this.listaBairro = this.subsisEndereco.bairroBuscarPorNome("");
                this.atualizarDadosTableView();
                this.tabDados.getSelectionModel().selectFirst();
//            } else {
//                MenssagemUtil.mensagemErro("Erro de Inclusao", retorno.getMsgRetorno());
//                this.opCadastro = OpCadastroEnum.INCLUIR;
//                this.TrataBotoes();
                this.campoNome.requestFocus();
           // }
       // }
    }

    private void processarAlteracao() {
//        RetornoNegocio retorno;
//        BairroVO bairroVO = this.criarVODados();
//        if (bairroVO != null) {
//            retorno = this.subsisEndereco.bairroAlterar(bairroVO);
//            if (retorno.isValido()) {
//                this.opCadastro = OpCadastroEnum.SALVAR;
//                this.TrataBotoes();
//                this.gridCampos.setDisable(true);
//                this.labelRodape.setText(retorno.getMsgRetorno());
//                this.listaBairro = this.subsisEndereco.bairroBuscarPorNome("");
//                this.atualizarDadosTableView();
//                this.tabDados.getSelectionModel().selectFirst();
//            } else {
//                MenssagemUtil.mensagemErro("Erro de Alteracao", retorno.getMsgRetorno());
//                this.opCadastro = OpCadastroEnum.ALTERAR;
//                this.TrataBotoes();
//                this.campoNome.requestFocus();
//            }
//        }
    }

    private void processarExclusao() {
//        RetornoNegocio retorno;
//        BairroVO bairroVO = this.obterVOTableView();
//        if (bairroVO != null) {
//            bairroVO = this.subsisEndereco.bairroBuscarPorID(bairroVO.getId());
//            if (bairroVO != null) {
//                this.opCadastro = OpCadastroEnum.EXCLUIR;
//                this.TrataBotoes();
//                this.labelRodape.setText("Exclusao em andamento...");
//                if (MenssagemUtil.mensagemConfirmacao("Confirmação", "Confirma a exclusão de " + bairroVO)) {
//                    retorno = this.subsisEndereco.bairroExcluir(bairroVO);
//                    if (retorno.isValido()) {
//                        this.TrataBotoes();
//                        this.labelRodape.setText(retorno.getMsgRetorno());
//                        this.listaBairro = this.subsisEndereco.bairroBuscarPorNome("");
//                        this.atualizarDadosTableView();
//                    } else {
//                        MenssagemUtil.mensagemErro("Erro de Exclusao", retorno.getMsgRetorno());
//                    }
//                    this.opCadastro = OpCadastroEnum.CONSULTAR;
//                }
//            } else {
//                MenssagemUtil.mensagemAlerta("Item nao localizado!!");
//            }
//        } else {
//            MenssagemUtil.mensagemAlerta("Nenhum item selecionado!!");
//        }
    }

    private void processarCancelamento() {
        this.opCadastro = OpCadastroEnum.CANCELAR;
        this.TrataBotoes();
        this.tabDados.getSelectionModel().selectFirst();
        this.gridCampos.setDisable(true);
        this.labelRodape.setText("Operação Cancelada...");
    }

    private void processarFiltroPorCidade(KeyEvent keyEvent) {
//        if (keyEvent.getCode() == KeyCode.ENTER) {
//            this.listaBairro.clear();
//
//            if (this.campoPesquisaCidade.getText().length() == 0) {
//                this.listaBairro = this.subsisEndereco.bairroBuscarPorNome("");
//            } else {
//                List<CidadeVO> listaCidadeTemp = this.subsisEndereco.cidadeBuscarPorNome(this.campoPesquisaCidade.getText().trim());
//                if (listaCidadeTemp.size() > 0) {
//                    CidadeVO cidadeVO = listaCidadeTemp.get(0);
//                    this.listaBairro = this.subsisEndereco.bairroBuscarPorCidade(cidadeVO);
//                }
//            }
//            this.atualizarDadosTableView();
//        }
    }

    private void processarFiltroPorNome(KeyEvent keyEvent) {
//        if (keyEvent.getCode() == KeyCode.ENTER) {
//            this.listaBairro.clear();
//            this.listaBairro = this.subsisEndereco.bairroBuscarPorNome(this.campoPesquisaNome.getText().trim());
//            this.atualizarDadosTableView();
//        }
    }

    private void sair() {
        this.palcoOrigem.close();
    }

    private void TrataBotoes() {
        if (this.opCadastro == OpCadastroEnum.INCLUIR || this.opCadastro == OpCadastroEnum.ALTERAR) {
            this.botaoIncluir.setDisable(true);
            this.botaoAlterar.setDisable(true);
            this.botaoExcluir.setDisable(true);
            this.botaoSalvar.setDisable(false);
            this.botaoCancelar.setDisable(false);
        } else if (this.opCadastro == OpCadastroEnum.SALVAR
                || this.opCadastro == OpCadastroEnum.CANCELAR
                || this.opCadastro == OpCadastroEnum.CONSULTAR) {
            this.botaoIncluir.setDisable(false);
            this.botaoAlterar.setDisable(false);
            this.botaoExcluir.setDisable(false);
            this.botaoSalvar.setDisable(true);
            this.botaoCancelar.setDisable(true);
        }
    }

    private void limparCampos() {
        this.campoCodigo.clear();
        this.campoNome.clear();
        this.campoStatus.clear();
    }

    private void preecheCampos() {
        
    }  

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
