/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.game.buttons;

import implementations.ui.title.buttons.*;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;

/**
 *
 * @author PlaneShaper
 */

public class ImpUIGameTurnButtons extends ImpUIGameButtons {
    
    public static enum ImpUIGameTurnButtonEnum {
        DEFAULT, DRAWORBIT, ZONEGOLDILOCKS, ZONEASTEROIDS, ZONEGAS, ZONEROCKY, COMET, FLARE, WIND, HELIOPULSE, COSMICRAY, GAMMARAYBURST, 
        ARIES, TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, OPHIUCHUS, SAGITTARIUS, CAPRICORN, AQUARIUS, PISCES, CETUS, 
        URANOMETRIA, ENDTURN, RESTART, QUIT;
    }
    
    private ImpUIGameTurnButtonEnum button = ImpUIGameTurnButtonEnum.DEFAULT;
    
    public ImpUIGameTurnButtonEnum getButton() {
        return button;
    }
    public void setButton(ImpUIGameTurnButtonEnum b) {
        button = b;
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}