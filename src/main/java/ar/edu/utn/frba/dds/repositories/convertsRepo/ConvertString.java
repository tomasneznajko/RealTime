package ar.edu.utn.frba.dds.repositories.convertsRepo;

import java.util.List;

public class ConvertString {
    private StringBuilder stringBuilder;

    public ConvertString(){
        this.stringBuilder = new StringBuilder();
    }

    public String converterListToString(List<Long> lista) {
        this.stringBuilder.setLength(0);
        this.stringBuilder.append("(");

        for (int i = 0; i < lista.size(); i++) {
            this.stringBuilder.append(lista.get(i));

            // Agregar una coma si no es el último elemento
            if (i < lista.size() - 1) {
                this.stringBuilder.append(", ");
            }
        }
        this.stringBuilder.append(")");
        return this.stringBuilder.toString();
    }

}
