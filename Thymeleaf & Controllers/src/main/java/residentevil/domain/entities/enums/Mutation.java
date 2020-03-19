package residentevil.domain.entities.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Mutation {
    ZOMBIE,
    T_078_TYRANT,
    GIANT_SPIDER;

    public static List<String> getMutations() {
        return Arrays.stream(Mutation.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
