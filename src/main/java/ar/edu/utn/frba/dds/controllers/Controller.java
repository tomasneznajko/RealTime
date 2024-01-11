package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.domain.comunidades.Miembro;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Controller implements WithSimplePersistenceUnit {
    protected Miembro miembroEnSesion(Context ctx) {
        if(ctx.sessionAttribute("usuario_id") == null)
            return null;
        return entityManager()
                .find(Miembro.class, ctx.sessionAttribute("usuario_id"));
    }

    protected String encryptId(Long id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(id.toString().getBytes());
            StringBuilder hexStringBuilder = new StringBuilder();

            for (byte hashedByte : hashedBytes) {
                hexStringBuilder.append(String.format("%02x", hashedByte));
            }

            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar el ID", e);
        }
    }

/*    protected Long decryptId(String encryptedId) {
        // Implementa la lógica de desencriptación aquí (puedes usar IdEncryptor.decrypt())
        return Long.valueOf(IdEncryptor.decrypt(encryptedId));
    }*/
}
