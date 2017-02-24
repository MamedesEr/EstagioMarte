package javafxmvc.model.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.logging.Logger;

/**
 *
 * @author Mattheus
 */
public class Emprestimo {
    
    private int idEmprestimo;
    private Date dtEmprestimo;
    private Time hrEmprestimo;
    private String descricao;
    private Date dtDevolucao;
    private Time hrDevolucao;
    private Date dtPrevisaoEntrega;
    private Time hrPrevisaoEntrega;

    public Emprestimo() {
    }

    public Emprestimo(int idEmprestimo, Date dtEmprestimo, Time hrEmprestimo, String descricao, Date dtDevolucao, Time hrDevolucao, Date dtPrevisaoEntrega, Time hrPrevisaoEntrega) {
        this.idEmprestimo = idEmprestimo;
        this.dtEmprestimo = dtEmprestimo;
        this.hrEmprestimo = hrEmprestimo;
        this.descricao = descricao;
        this.dtDevolucao = dtDevolucao;
        this.hrDevolucao = hrDevolucao;
        this.dtPrevisaoEntrega = dtPrevisaoEntrega;
        this.hrPrevisaoEntrega = hrPrevisaoEntrega;
    }

    public Time getHrPrevisaoEntrega() {
        return hrPrevisaoEntrega;
    }

    public void setHrPrevisaoEntrega(Time hrPrevisaoEntrega) {
        this.hrPrevisaoEntrega = hrPrevisaoEntrega;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Date getDtEmprestimo() {
        return dtEmprestimo;
    }

    public void setDtEmprestimo(Date dtEmprestimo) {
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

    public Date getDtDevolucao() {
        return dtDevolucao;
    }

    public void setDtDevolucao(Date dtDevolucao) {
        this.dtDevolucao = dtDevolucao;
    }

    public Time getHrDevolucao() {
        return hrDevolucao;
    }

    public void setHrDevolucao(Time hrDevolucao) {
        this.hrDevolucao = hrDevolucao;
    }

    public Date getDtPrevisaoEntrega() {
        return dtPrevisaoEntrega;
    }

    public void setDtPrevisaoEntrega(Date dtPrevisaoEntrega) {
        this.dtPrevisaoEntrega = dtPrevisaoEntrega;
    }
    
}
