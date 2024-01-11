package ar.edu.utn.frba.dds.models.domain.MediosDeComunicacion;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@DiscriminatorValue("wsp")
public class Whatsapp extends MedioDeNotificacion{



  private int codHarc = 0;


  private String telefono;

  public static final String ACCOUNT_SID = "AC8248ee633b83070844ab4267e9269cb8";

  public static final String AUTH_TOKEN = "fa45e420a9fe4186a4fe2f8e35a7c139";

  public Whatsapp(){}

  public Whatsapp(String numero){
    this.telefono = numero;
  }

  @Override
  public void enviarNotificacion() {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
            new com.twilio.type.PhoneNumber("whatsapp:549"+telefono),
            new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
            this.notificacionesToString()) //DETALLE DEL WHATSAPP
        .create();
    System.out.println(message.getSid());
    System.out.println("Se ha enviado notificacion al WhatssApp - ");

  this.notificacionesRecientes.clear();
  }
}
