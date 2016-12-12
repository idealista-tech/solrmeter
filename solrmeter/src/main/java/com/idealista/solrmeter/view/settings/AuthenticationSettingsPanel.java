package com.idealista.solrmeter.view.settings;

import javax.swing.Box;
import javax.swing.BoxLayout;

import com.idealista.solrmeter.controller.SettingsController;
import com.idealista.solrmeter.view.I18n;
import com.idealista.solrmeter.view.SettingsPanel;
import com.idealista.solrmeter.view.component.PropertyPanel;
import com.idealista.solrmeter.view.component.TextPropertyPanel;
import com.idealista.solrmeter.view.exception.InvalidPropertyException;
import com.idealista.solrmeter.view.listener.PropertyChangeListener;

public class AuthenticationSettingsPanel extends SettingsPanel implements PropertyChangeListener {

	private static final long serialVersionUID = 3487000162609678664L;
	private boolean editable;

	public AuthenticationSettingsPanel(SettingsController controller, boolean editable) {
		super(controller);
		this.editable = editable;
		this.initGUI();
		
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		final PropertyPanel authenticationUser = new TextPropertyPanel(I18n.get("settings.query.httpAuthUser"), "solr.server.configuration.httpAuthUser", editable, this);
		final PropertyPanel authenticationPassword = new TextPropertyPanel(I18n.get("settings.query.httpAuthPass"), "solr.server.configuration.httpAuthPass", editable, this);
		this.add(authenticationUser);
		this.add(authenticationPassword);
		this.add(Box.createVerticalGlue());
	}

	@Override
	public String getSettingsName() {
		return I18n.get("settings.authentication.title");
	}

	@Override
	public void onPropertyChanged(String property, String text)
			throws InvalidPropertyException {
		controller.setProperty(property, text);
	}
	
}
