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

public class ImpUIGameExtraButtons extends ImpUIGameButtons {
    
    public static enum ImpUIGameExtraButtonEnum {
        DEFAULT, RESTART, QUIT;
    }
    
    private ImpUIGameExtraButtonEnum button = ImpUIGameExtraButtonEnum.DEFAULT;
    
    public ImpUIGameExtraButtonEnum getButton() {
        return button;
    }
    public void setButton(ImpUIGameExtraButtonEnum b) {
        button = b;
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}