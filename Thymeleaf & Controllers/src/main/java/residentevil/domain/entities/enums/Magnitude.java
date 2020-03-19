package residentevil.domain.entities.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Magnitude {
    Low,
    Medium,
    High;

    public static List<String> getMagnitudes() {
        return Arrays.stream(Magnitude.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
