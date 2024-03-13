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

public class ImpUIGameShopButtons extends ImpUIGameButtons {
    
    public static enum ImpUIGameShopButtonEnum {
        DEFAULT, BUY1, BUY2, BUY3, BUY4, BUY5, FEAT1, FEAT2, FEAT3, EVENT1, EVENT2, EVENT3, ENDSHOP, RESTART, QUIT;
    }
    
    private ImpUIGameShopButtonEnum button = ImpUIGameShopButtonEnum.DEFAULT;
    
    public ImpUIGameShopButtonEnum getButton() {
        return button;
    }
    public void setButton(ImpUIGameShopButtonEnum b) {
        button = b;
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}