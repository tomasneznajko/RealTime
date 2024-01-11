package ar.edu.utn.frba.dds.models.converts;

import java.util.UUID;

public class UUIDAttributeConverter {
    private static long counter = 0;

    public static Long convertUuidToLong(UUID uuid) {
        long longId = uuid.getMostSignificantBits();
        return addSuffix(longId);
    }

    private static long addSuffix(long baseId) {
        return baseId * 1000 + (counter++);
    }
}
