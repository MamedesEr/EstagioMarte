package javafxmvc.model.domain;

import java.sql.Time;
import java.time.LocalDate;

public class Emprestimo {
    
    private int idEmprestimo;
    private LocalDate dtEmprestimo;
    private Time hrEmprestimo;
    private String descricao;
    private LocalDate dtDevolucao;
    private Time hrDevolucao;
    private LocalDate dtPrevisaoEntrega;
    private Time hrPrevisaoEntrega;
    private Usuario usuario;
    private Chave chave;
    private Pessoa pessoa;

    public Emprestimo() {
    }

    public Emprestimo(int idEmprestimo, LocalDate dtEmprestimo, Time hrEmprestimo, String descricao, LocalDate dtDevolucao, Time hrDevolucao, LocalDate dtPrevisaoEntrega, Time hrPrevisaoEntrega, Usuario usuario, Chave chave, Pessoa pessoa) {
        this.idEmprestimo = idEmprestimo;
        this.dtEmprestimo = dtEmprestimo;
        this.hrEmprestimo = hrEmprestimo;
        this.descricao = descricao;
        this.dtDevolucao = dtDevolucao;
        this.hrDevolucao = hrDevolucao;
        this.dtPrevisaoEntrega = dtPrevisaoEntrega;
        this.hrPrevisaoEntrega = hrPrevisaoEntrega;
        this.usuario = usuario;
        this.chave = chave;
        this.pessoa = pessoa;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public LocalDate getDtEmprestimo() {
        return dtEmprestimo;
    }

    public void setDtEmprestimo(LocalDate dtEmprestimo) {
        this.dtEmprestimo = dtEmprestimo;
    }

    public Time getHrEmprestimo() {
        return hrEmprestimo;
    }

    public void setHrEmprestimo(Time hrEmprestimo) {
        this.hrEmprestimo = hrEmprestimo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDtDevolucao() {
        return dtDevolucao;
    }

    public void setDtDevolucao(LocalDate dtDevolucao) {
        this.dtDevolucao = dtDevolucao;
    }

    public Time getHrDevolucao() {
        return hrDevolucao;
    }

    public void setHrDevolucao(Time hrDevolucao) {
        this.hrDevolucao = hrDevolucao;
    }

    public LocalDate getDtPrevisaoEntrega() {
        return dtPrevisaoEntrega;
    }

    public void setDtPrevisaoEntrega(LocalDate dtPrevisaoEntrega) {
        this.dtPrevisaoEntrega = dtPrevisaoEntrega;
    }

    public Time getHrPrevisaoEntrega() {
        return hrPrevisaoEntrega;
    }

    public void setHrPrevisaoEntrega(Time hrPrevisaoEntrega) {
        this.hrPrevisaoEntrega = hrPrevisaoEntrega;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Chave getChave() {
        return chave;
    }

    public void setChave(Chave chave) {
        this.chave = chave;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    
}
