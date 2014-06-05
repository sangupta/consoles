package com.sangupta.consoles;

import java.awt.Color;

public class ConsoleUtils {

    public static Color invert(Color color) {
        return new Color(255 - color.getRed(),
                         255 - color.getGreen(), 
                         255 - color.getBlue(),
                         color.getAlpha());
    }

}
