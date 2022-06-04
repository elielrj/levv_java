package com.example.levv.model.bo.pedido;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.levv.model.bo.meioDeTransporte.MeioDeTransporte;
import com.example.levv.model.bo.usuario.Transportador;
import com.example.levv.model.bo.usuario.Usuario;
import com.example.levv.support.AppUtil;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.GeoPoint;

import java.text.DecimalFormat;

public class Pedido {

    private String numero;
    private String data;
    private String hora;

    private String coleta;
    private String entrega;

    private GeoPoint latLngColeta;
    private GeoPoint latLngEntrega;


    private float valor;

    private boolean pago = true;
    private boolean entregue = false;
    private boolean disponivel = true;

    private MeioDeTransporte meioDeTransporte;
    private Transportador transportador;
    private Usuario usuarioDoPedido;

    private Item item;

    private DocumentReference referenciaMeioDeTransporte;
    private DocumentReference referenciaTransportador;
    private DocumentReference referenciaUsuarioDoPedido;

    public Pedido() {

    }

    public DocumentReference getReferenciaTransportador() {
        return referenciaTransportador;
    }

    public void setReferenciaTransportador(DocumentReference referenciaTransportador) {
        this.referenciaTransportador = referenciaTransportador;
    }


    public DocumentReference getReferenciaUsuarioDoPedido() {
        return referenciaUsuarioDoPedido;
    }


    public void setReferenciaUsuarioDoPedido(DocumentReference referenciaUsuarioDoPedido) {
        this.referenciaUsuarioDoPedido = referenciaUsuarioDoPedido;
    }

    public DocumentReference getReferenciaMeioDeTransporte() {
        return referenciaMeioDeTransporte;
    }

    public void setReferenciaMeioDeTransporte(DocumentReference referenciaMeioDeTransporte) {
        this.referenciaMeioDeTransporte = referenciaMeioDeTransporte;
    }

    @Exclude
    public Usuario getUsuarioDoPedido() {
        return usuarioDoPedido;
    }

    @Exclude
    public void setUsuarioDoPedido(Usuario usuarioDoPedido) {
        this.usuarioDoPedido = usuarioDoPedido;
    }


    public Pedido(PedidoBuilder pedidoBuilder) {
        this.numero = pedidoBuilder.numero;
        this.data = pedidoBuilder.data;
        this.hora = pedidoBuilder.hora;
        this.coleta = pedidoBuilder.coleta;
        this.entrega = pedidoBuilder.entrega;
        this.latLngColeta = pedidoBuilder.latLngColeta;
        this.latLngEntrega = pedidoBuilder.latLngEntrega;
        this.valor = pedidoBuilder.valor;
        this.pago = pedidoBuilder.pago;
        this.entregue = pedidoBuilder.entregue;
        this.disponivel = pedidoBuilder.disponivel;
        this.meioDeTransporte = pedidoBuilder.meioDeTransporte;
        this.transportador = pedidoBuilder.transportador;
        this.item = pedidoBuilder.item;
        this.usuarioDoPedido = pedidoBuilder.usuarioDoPedido;
    }

    public static class PedidoBuilder {
        private String numero;
        private String data;
        private String hora;

        private String coleta;
        private String entrega;

        private GeoPoint latLngColeta;
        private GeoPoint latLngEntrega;

        private float valor;

        private boolean pago = true;
        private boolean entregue = false;
        private boolean disponivel = true;

        private MeioDeTransporte meioDeTransporte;
        private Transportador transportador;
        private Usuario usuarioDoPedido;

        private DocumentReference referenciaMeioDeTransporte;
        private DocumentReference referenciaTransportador;
        private DocumentReference referenciaUsuarioDoPedido;

        private Item item;

        public PedidoBuilder setUsuarioDoPedido(Usuario usuarioDoPedido) {
            this.usuarioDoPedido = usuarioDoPedido;
            return this;
        }


        public PedidoBuilder setData(String data) {
            this.data = data;
            return this;
        }

        public PedidoBuilder setHora(String hora) {
            this.hora = hora;
            return this;
        }

        public PedidoBuilder setLatLngColeta(GeoPoint latLngColeta) {
            this.latLngColeta = latLngColeta;
            return this;
        }

        public PedidoBuilder setLatLngEntrega(GeoPoint latLngEntrega) {
            this.latLngEntrega = latLngEntrega;
            return this;
        }

        public PedidoBuilder setReferenciaMeioDeTransporte(DocumentReference referenciaMeioDeTransporte) {
            this.referenciaMeioDeTransporte = referenciaMeioDeTransporte;
            return this;
        }

        public PedidoBuilder setReferenciaTransportador(DocumentReference referenciaTransportador) {
            this.referenciaTransportador = referenciaTransportador;
            return this;
        }

        public PedidoBuilder setReferenciaUsuarioDoPedido(DocumentReference referenciaUsuarioDoPedido) {
            this.referenciaUsuarioDoPedido = referenciaUsuarioDoPedido;
            return this;
        }

        public PedidoBuilder setTransportador(Transportador transportador) {
            this.transportador = transportador;
            return this;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public PedidoBuilder() {
            this.data = AppUtil.dataAtual();
            this.hora = AppUtil.horaAtual();
            this.pago = true;
            this.entregue = false;
            this.disponivel = true;
        }


        public PedidoBuilder setValor(float valor) {
            this.valor = valor;
            return this;
        }

        public PedidoBuilder setPago(boolean pago) {
            this.pago = pago;
            return this;
        }

        public PedidoBuilder setEntregue(boolean entregue) {
            this.entregue = entregue;
            return this;
        }

        public PedidoBuilder setDisponivel(boolean disponivel) {
            this.disponivel = disponivel;
            return this;
        }

        public PedidoBuilder setColeta(String coleta) {
            this.coleta = coleta;
            return this;
        }

        public PedidoBuilder setEntrega(String entrega) {
            this.entrega = entrega;
            return this;
        }

        public PedidoBuilder setMeioDeTransporte(MeioDeTransporte meioDeTransporte) {
            this.meioDeTransporte = meioDeTransporte;
            return this;
        }

        public PedidoBuilder setItem(Item item) {
            this.item = item;
            return this;
        }

        public PedidoBuilder setNumero(String numero) {
            this.numero = numero;
            return this;
        }

        public Pedido createPedido() {
            return new Pedido(this);
        }
    }

    public String getColeta() {
        return coleta;
    }

    public void setColeta(String coleta) {
        this.coleta = coleta;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public GeoPoint getLatLngColeta() {
        return latLngColeta;
    }

    public void setLatLngColeta(GeoPoint latLngColeta) {
        this.latLngColeta = latLngColeta;
    }

    public GeoPoint getLatLngEntrega() {
        return latLngEntrega;
    }

    public void setLatLngEntrega(GeoPoint latLngEntrega) {
        this.latLngEntrega = latLngEntrega;
    }

    @Exclude
    public Transportador getTransportador() {
        return transportador;
    }

    @Exclude

    public void setTransportador(Transportador transportador) {
        this.transportador = transportador;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean isEntregue() {
        return entregue;
    }

    public void setEntregue(boolean entregue) {
        this.entregue = entregue;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }


    @Exclude

    public MeioDeTransporte getMeioDeTransporte() {
        return meioDeTransporte;
    }

    @Exclude
    public void setMeioDeTransporte(MeioDeTransporte meioDeTransporte) {
        this.meioDeTransporte = meioDeTransporte;
    }

    /*
        public List<Item> getItens() {
            return itens;
        }

        public void setItens(List<Item> itens) {
            this.itens = itens;
        }
    */
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        numero = numero.replace(" ", "");
        numero = numero.replace(".", "");
        numero = numero.replace("-", "");
        numero = numero.replace(":", "");
        numero = numero.replace("/", "");

        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Pedido Nr " + numero + " - R$ " + valor;
    }
}
