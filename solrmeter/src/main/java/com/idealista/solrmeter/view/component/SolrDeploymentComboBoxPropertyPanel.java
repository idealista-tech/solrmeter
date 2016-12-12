package com.idealista.solrmeter.view.component;

import static com.idealista.solrmeter.SolrDeploymentParam.MASTER_SLAVE;
import static com.idealista.solrmeter.SolrDeploymentParam.SOLRCLOUD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import com.idealista.solrmeter.model.SolrMeterConfiguration;
import com.idealista.solrmeter.view.listener.PropertyChangeListener;

public class SolrDeploymentComboBoxPropertyPanel extends ComboPropertyPanel {

	private static final long serialVersionUID = 450290036767193606L;
	
	private static final String[] VALUES = { MASTER_SLAVE, SOLRCLOUD };
	
	public SolrDeploymentComboBoxPropertyPanel(String text, String property, boolean editable, PropertyChangeListener listener) {
		super(text, property, editable, listener, VALUES, false);
	}

	public void setDependantProperties(final List<PropertyPanel> masterSlavePropertyPanels, final List<PropertyPanel> solrCloudPropertyPanels) {
		if(editable) {
			comboBox.addActionListener(new IsVisibleActionListener(masterSlavePropertyPanels, solrCloudPropertyPanels));			
			comboBox.setSelectedIndex(comboBox.getSelectedIndex());
		} else {
			String selection = SolrMeterConfiguration.getProperty(property);
			evaluateChildPropertiesVisibility(selection, masterSlavePropertyPanels, solrCloudPropertyPanels);
		}
	}

	private final class IsVisibleActionListener implements ActionListener {
		
		private final List<PropertyPanel> masterSlavePropertyPanels;

		private final List<PropertyPanel> solrCloudPropertyPanels;

		private IsVisibleActionListener(List<PropertyPanel> masterSlavePropertyPanels, List<PropertyPanel> solrCloudPropertyPanels) {
			this.masterSlavePropertyPanels = masterSlavePropertyPanels;
			this.solrCloudPropertyPanels = solrCloudPropertyPanels;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			evaluateChildPropertiesVisibility(comboBox.getSelectedItem().toString(), masterSlavePropertyPanels, solrCloudPropertyPanels);
		}
	}
	
	private void evaluateChildPropertiesVisibility(String selection, final List<PropertyPanel> masterSlavePropertyPanels, final List<PropertyPanel> solrCloudPropertyPanels) {
		if(selection.equals(MASTER_SLAVE)){
			setVisible(masterSlavePropertyPanels, true);
			setVisible(solrCloudPropertyPanels, false);
		}
		
		if(selection.equals(SOLRCLOUD)){
			setVisible(masterSlavePropertyPanels, false);
			setVisible(solrCloudPropertyPanels, true);
		}
	}
	
	private void setVisible(final List<PropertyPanel> propertyPanels, boolean status) {
		for (PropertyPanel propertyPanel : propertyPanels) {
			propertyPanel.setVisible(status);
		}
	}
}