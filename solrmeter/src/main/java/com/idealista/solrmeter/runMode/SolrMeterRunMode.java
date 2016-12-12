package com.idealista.solrmeter.runMode;

import com.google.inject.Injector;
import com.idealista.solrmeter.view.ConsoleFrame;

public interface SolrMeterRunMode {

    void main(Injector injector);
    
    void restartApplication();
    
    ConsoleFrame getMainFrame();
    
}
