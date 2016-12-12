/**
 * Copyright Plugtree LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idealista.solrmeter.view.settings;

import java.util.Arrays;

import javax.swing.Box;
import javax.swing.BoxLayout;

import com.idealista.solrmeter.controller.SettingsController;
import com.idealista.solrmeter.view.I18n;
import com.idealista.solrmeter.view.SettingsPanel;
import com.idealista.solrmeter.view.component.BooleanPropertyPanel;
import com.idealista.solrmeter.view.component.ComboPropertyPanel;
import com.idealista.solrmeter.view.component.FilePropertyPanel;
import com.idealista.solrmeter.view.component.IntegerPropertyPanel;
import com.idealista.solrmeter.view.component.PropertyPanel;
import com.idealista.solrmeter.view.component.SolrDeploymentComboBoxPropertyPanel;
import com.idealista.solrmeter.view.component.TextPropertyPanel;
import com.idealista.solrmeter.view.exception.InvalidPropertyException;
import com.idealista.solrmeter.view.listener.PropertyChangeListener;

public class UpdateSettingsPanel extends SettingsPanel implements PropertyChangeListener {
	
	private static final long serialVersionUID = -1176374120440517941L;
	
	private boolean editable;

	public UpdateSettingsPanel(SettingsController controller, boolean editable) {
		super(controller);
		this.editable = editable;
		this.initGUI();
	}

	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		final SolrDeploymentComboBoxPropertyPanel useSolrCloudComboBoxPropertyPanel = new SolrDeploymentComboBoxPropertyPanel(I18n.get("settings.update.useSolrCloud"), "solr.update.useSolrCloudArchitecture", editable, this);
		final PropertyPanel solrUrlPropertyPanel = new TextPropertyPanel(I18n.get("settings.update.solrUrl"), "solr.addUrl", editable, this);
		final PropertyPanel zooKeeperUrlPropertyPanel = new TextPropertyPanel(I18n.get("settings.update.zooKeeperUrl"), "solr.update.zooKeeperUrl", editable, this);
		final PropertyPanel zooKeeperChrootPropertyPanel = new TextPropertyPanel(I18n.get("settings.update.zooKeeperChroot"), "solr.update.zooKeeperChroot", editable, this);
		final PropertyPanel defaultSolrCollectionPropertyPanel = new TextPropertyPanel(I18n.get("settings.update.defaultSolrCollection"), "solr.update.defaultSolrCollection", editable, this);
		final PropertyPanel autocommitPropertyPanel = new BooleanPropertyPanel(I18n.get("settings.update.solrAutocommit"), "solr.update.solrAutocommit", editable, this);
		final PropertyPanel documentsToCommitPropertyPanel = new IntegerPropertyPanel(I18n.get("settings.update.documentsToCommit"), "solr.update.documentsToCommit",editable, this);
		final PropertyPanel timeToCommitPropertyPanel = new IntegerPropertyPanel(I18n.get("settings.update.timeToCommit"), "solr.update.timeToCommit",editable, this);
		final PropertyPanel updateFilePropertyPanel = new FilePropertyPanel(I18n.get("settings.update.updateFile"), "solr.updatesFiles", editable, this);
		final PropertyPanel updateExecutorPropertyPanel = new ComboPropertyPanel(I18n.get("settings.update.updateExecutor"), "executor.updateExecutor", editable, this, new String[]{"random", "constant"}, true);
		
		add(useSolrCloudComboBoxPropertyPanel);
		add(solrUrlPropertyPanel);
		add(zooKeeperUrlPropertyPanel);
		add(zooKeeperChrootPropertyPanel);
		add(defaultSolrCollectionPropertyPanel);
		add(autocommitPropertyPanel);
		add(documentsToCommitPropertyPanel);
		add(timeToCommitPropertyPanel);
		add(updateFilePropertyPanel);
		add(updateExecutorPropertyPanel);
		add(Box.createVerticalGlue());
		
		useSolrCloudComboBoxPropertyPanel.setDependantProperties(Arrays.asList(solrUrlPropertyPanel), Arrays.asList(zooKeeperUrlPropertyPanel, zooKeeperChrootPropertyPanel, defaultSolrCollectionPropertyPanel));
	}

	@Override
	public String getSettingsName() {
		return I18n.get("settings.update.title");
	}
	
	@Override
	public void onPropertyChanged(String property, String text)	throws InvalidPropertyException {
		controller.setProperty(property, text);
	}
}