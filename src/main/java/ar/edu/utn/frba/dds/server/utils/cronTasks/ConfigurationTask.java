package ar.edu.utn.frba.dds.server.utils.cronTasks;

import ar.edu.utn.frba.dds.repositories.RepoDeComunidades;
import ar.edu.utn.frba.dds.server.utils.cronTasks.puntajes.ScheduledTask;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ConfigurationTask {
    public static void actualizarPuntajes() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        ScheduledFuture<?> tareaProgramada = scheduler.scheduleAtFixedRate(
                new ScheduledTask(new RepoDeComunidades()),
                0,  // Retardo inicial (en este caso, la tarea comienza de inmediato)
                7,  // Período entre ejecuciones en días
                TimeUnit.DAYS
        );

    }
}
