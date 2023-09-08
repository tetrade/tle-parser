package org.example;

import lombok.Getter;
import lombok.Setter;
import org.orekit.propagation.analytical.tle.TLE;

@Getter
@Setter
public class TLEData extends TLE {

    private String name;

    public TLEData(String line1, String line2) {
        super(line1, line2);
    }

    public TLEData(String name, String line1, String line2) {
        super(line1, line2);
        this.name = name;
    }

    @Override
    public String toString() {
        return name + "\n" + super.toString();
    }
}
