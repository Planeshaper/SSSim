/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package implementations.ui.title.buttons;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import java.io.IOException;

/**
 *
 * @author PlaneShaper
 */

public class ImpUITitleSplashButtons implements Savable {
    
    public static enum ImpUITitleSplashButtonEnum {
        DEFAULT, PLAY, SETTINGS, QUIT;
    }
    
    private ImpUITitleSplashButtonEnum button = ImpUITitleSplashButtonEnum.DEFAULT;
    
    public ImpUITitleSplashButtonEnum getButton() {
        return button;
    }
    public void setButton(ImpUITitleSplashButtonEnum b) {
        button = b;
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
}