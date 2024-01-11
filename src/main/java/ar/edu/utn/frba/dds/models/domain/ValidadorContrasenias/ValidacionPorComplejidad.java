package ar.edu.utn.frba.dds.models.domain.ValidadorContrasenias;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;

public class ValidacionPorComplejidad implements Validable{
    public ValidacionPorComplejidad() {
    }

    @Override
    public boolean esValida(String clave) {
//Verifica si tiene al menos una minuscula, un numero y una mayuscula
        return clave.chars().anyMatch(Character::isLowerCase) &&
                clave.chars().anyMatch(Character::isDigit) &&
                clave.chars().anyMatch(Character::isUpperCase);
    }
}
