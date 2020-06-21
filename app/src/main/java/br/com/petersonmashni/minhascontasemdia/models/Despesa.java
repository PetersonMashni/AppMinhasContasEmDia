package br.com.petersonmashni.minhascontasemdia.models;

import java.util.Date;

public class Despesa {
    private String id;
    private String tipo;
    private String nome;
    private String mesAno;
    private Date vencimento;
    private Date Pagamento;
    private Number valor;
    private Number valorPago;

    public Despesa(){

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Number getValor() {
        return valor;
    }

    public void setValor(Number valor) {
        this.valor = valor;
    }

    public Number getValorPago() {
        return valorPago;
    }

    public void setValorPago(Number valorPago) {
        this.valorPago = valorPago;
    }

    public Date getPagamento() {
        return Pagamento;
    }

    public void setPagamento(Date pagamento) {
        Pagamento = pagamento;
    }
}
