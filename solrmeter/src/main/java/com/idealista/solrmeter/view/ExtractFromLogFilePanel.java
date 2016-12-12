package com.idealista.solrmeter.view;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.idealista.solrmeter.controller.ExtractFromLogFileController;
import com.idealista.solrmeter.view.component.CheckBoxPropertyPanel;
import com.idealista.solrmeter.view.component.FilePropertyPanel;
import com.idealista.solrmeter.view.component.PropertyPanel;
import com.idealista.solrmeter.view.component.SaveFilePropertyPanel;
import com.idealista.solrmeter.view.component.TextPropertyPanel;
import com.idealista.solrmeter.view.exception.InvalidPropertyException;
import com.idealista.solrmeter.view.listener.PropertyChangeListener;

public class ExtractFromLogFilePanel extends JPanel implements PropertyChangeListener{
  private ExtractFromLogFileController controller;

    private static final long serialVersionUID = 189L;
  
  public ExtractFromLogFilePanel(ExtractFromLogFileController controller) {
    this.controller = controller;
    this.initGUI();
  }
  
  private void initGUI() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    final PropertyPanel fileChooserPropertyPanel = new FilePropertyPanel(I18n.get("tools.extract.logFile"), "tools.extract.solrLogFile", true, this);
    final PropertyPanel destinationFilename = new SaveFilePropertyPanel(I18n.get("tools.extract.destinationFilename"), "tools.extract.destinationFilename", true, this);
    final PropertyPanel regexPanel = new TextPropertyPanel(I18n.get("tools.extract.regex"), "tools.extract.regex", true, this);
    final PropertyPanel removeDuplicatesPanel = new CheckBoxPropertyPanel(I18n.get("tools.extract.removeDuplicates"), "tools.extract.removeDuplicates", true, this);

    

    this.add(fileChooserPropertyPanel);
    this.add(destinationFilename);
    this.add(regexPanel);
    this.add(removeDuplicatesPanel);
    this.add(Box.createVerticalGlue());
    
    
  }
  
  public Component add(PropertyPanel component){
    this.controller.addPropertyObserver(component.getPropertyName(), component);
    return super.add(component);
  }
  
  @Override
  public void onPropertyChanged(String property, String text) throws InvalidPropertyException {
    controller.setProperty(property, text);
    
  }
}
