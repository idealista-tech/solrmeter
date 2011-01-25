package com.linebee.solrmeter.view.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.linebee.solrmeter.SolrMeterMain;
import com.linebee.solrmeter.model.FileUtils;
import com.linebee.solrmeter.model.SolrMeterConfiguration;
import com.linebee.solrmeter.view.I18n;
import com.linebee.solrmeter.view.listener.PropertyChangeListener;

public class FilePropertyPanel extends PropertyPanel {

	private static final long serialVersionUID = 6121674014586012807L;
	
	private JTextField textField;
	
	private JButton button;

	public FilePropertyPanel(String text, String property, boolean editable,
			PropertyChangeListener listener) {
		super(text, property, editable, listener);
		this.initGUI(text);
	}

	public FilePropertyPanel(String text, String property, boolean editable) {
		super(text, property, editable);
		this.initGUI(text);
	}

	public FilePropertyPanel(String text, String property,
			PropertyChangeListener listener) {
		super(text, property, listener);
		this.initGUI(text);
	}

	@Override
	protected String getSelectedValue() {
		return textField.getText();
	}

	@Override
	protected Component getVisualComponent() {
		JPanel panelAux = new JPanel();
		panelAux.setLayout(new BoxLayout(panelAux, BoxLayout.X_AXIS));
		panelAux.add(this.createTextField());
		panelAux.add(this.createButton());
		return panelAux;
	}

	private Component createButton() {
		button = new JButton(I18n.get("settings.fileProperty.browse"));
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser  = null;
				if(SolrMeterConfiguration.getProperty(property) == null) {
					fileChooser = new JFileChooser();
				} else {
					try {
						File file = new File(FileUtils.findFileAsResource(SolrMeterConfiguration.getProperty(property)).toURI());
						if(file.exists()) {
							fileChooser = new JFileChooser(file.getParentFile());
						}
					} catch (Exception e) {
						
					} finally {
						if(fileChooser == null) {
							fileChooser = new JFileChooser();
						}
					}
				}
				fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = fileChooser.showOpenDialog(SolrMeterMain.mainFrame);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					if(!selectedFile.exists()) {
						Logger.getLogger(this.getClass()).error("Can't find file with name " + selectedFile.getName());
						//TODO show error
					}else {
						textField.setText(selectedFile.getAbsolutePath());
						notifyObservers();
					}
				}
				
			}
		});
		return button;
	}

	private Component createTextField() {
		textField = new JTextField();
		textField.setText(SolrMeterConfiguration.getProperty(property));
		textField.addFocusListener(this);
		textField.setPreferredSize(new Dimension(MAX_COMPONENT_WIDTH, 0));
		return textField;
	}

}