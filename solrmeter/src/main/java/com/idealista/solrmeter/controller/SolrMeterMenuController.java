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
package com.idealista.solrmeter.controller;

import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.idealista.solrmeter.SolrMeterMain;
import com.idealista.solrmeter.model.OptimizeExecutor;
import com.idealista.solrmeter.model.QueryExecutor;
import com.idealista.solrmeter.model.SolrMeterConfiguration;
import com.idealista.solrmeter.model.UpdateExecutor;
import com.idealista.solrmeter.view.ExtractFromLogFilePanelContainer;
import com.idealista.solrmeter.view.I18n;
import com.idealista.solrmeter.view.SettingsPanelContainer;
import com.idealista.solrmeter.view.SwingUtils;
import com.idealista.stressTestScope.StressTestScope;

@StressTestScope
public class SolrMeterMenuController {
    
    private static final Logger logger = Logger.getLogger(SolrMeterMenuController.class);

	private QueryExecutor queryExecutor;

	private UpdateExecutor updateExecutor;

	private OptimizeExecutor optimizeExecutor;

	private StatisticsRepository repository;

	@Inject
	public SolrMeterMenuController(
			StatisticsRepository repository, 
			QueryExecutor queryExecutor,
			OptimizeExecutor optimizeExecutor,
			UpdateExecutor updateExecutor) {
		super();
		this.queryExecutor = queryExecutor;
		this.updateExecutor = updateExecutor;
		this.optimizeExecutor = optimizeExecutor;
		this.repository = repository;
	}



	public void onExitMenu() {
		SolrMeterMain.mainFrame.dispose();
		System.exit(0);
	}

	public void onImportMenu() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(this.createConfigurationFileFilter());
		int returnValue = fileChooser.showOpenDialog(SolrMeterMain.mainFrame);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			if(!selectedFile.exists()) {
			    logger.error("Can't find file with name " + selectedFile.getName());
				//TODO show error
			}else {
				try {
					SolrMeterConfiguration.importConfiguration(selectedFile);
					SolrMeterMain.restartApplication();
				} catch (IOException e) {
				    logger.error("Error importing configuration", e);
					//TODO show error
				}
			}
		}
	}

	public void onExportMenu() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.setFileFilter(this.createConfigurationFileFilter());
			int returnValue = fileChooser.showSaveDialog(SolrMeterMain.mainFrame);
			if(returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = this.addExtension(fileChooser.getSelectedFile());
				if(selectedFile.exists()) {
					int optionResultPane = JOptionPane.showConfirmDialog(SolrMeterMain.mainFrame, 
							I18n.get("menu.file.export.fileExists.message"), 
							I18n.get("menu.file.export.fileExists.title"),
							JOptionPane.WARNING_MESSAGE,JOptionPane.OK_CANCEL_OPTION);
					if(optionResultPane == JOptionPane.OK_OPTION) {
						this.doExport(selectedFile);
					}
				}else {
					this.doExport(selectedFile);
				}
			}
		} catch (IOException e) {
		    logger.error("Error exporting configuration", e);
			//TODO show error
		}

	}

	private File addExtension(File selectedFile) {
		if(!selectedFile.getName().endsWith(".smc.xml")) {
			return new File(selectedFile.getParent(), selectedFile.getName() + ".smc.xml");
		}
		return selectedFile;
	}

	private void doExport(File selectedFile) throws IOException {
		SolrMeterConfiguration.exportConfiguration(selectedFile);
	}

	private FileFilter createConfigurationFileFilter() {
		FileFilter fileFilter = new FileFilter() {

			@Override
			public boolean accept(File file) {
				if(file.isDirectory()) {
					return true;
				}
				return file.getName().endsWith(".smc.xml");
			}

			@Override
			public String getDescription() {
				return "*.smc.xml";
			}

		};
		return fileFilter;
	}

	public void onSettingsMenu() {
		JDialog dialog = new JDialog(SolrMeterMain.mainFrame, I18n.get("menu.edit.settings"), ModalityType.APPLICATION_MODAL);
		dialog.setContentPane(new SettingsPanelContainer(dialog, this.isSettingsEditable(), repository));
		dialog.setSize(new Dimension(700, 400));
		SwingUtils.centerWindow(dialog);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

	private boolean isSettingsEditable() {
		if(queryExecutor.isRunning()) {
			return false;
		}
		if(updateExecutor.isRunning()) {
			return false;
		}
		if(optimizeExecutor.isOptimizing()) {
			return false;
		}
		return true;
	}


	public void onRestartMenu() {
		int optionResultPane = JOptionPane.showConfirmDialog(SolrMeterMain.mainFrame, 
				I18n.get("menu.edit.restart.confirm.message"), 
				I18n.get("menu.edit.restart.confirm.title"),
				JOptionPane.OK_CANCEL_OPTION);
		if(optionResultPane == JOptionPane.OK_OPTION) {
			queryExecutor.stop();
			updateExecutor.stop();
			SolrMeterMain.restartApplication();
		}
	}

    public void onExtractMenu() {
		JDialog dialog = new JDialog(SolrMeterMain.mainFrame, I18n.get("menu.tools.extract"), ModalityType.APPLICATION_MODAL);
		dialog.setSize(new Dimension(400, 220));
		dialog.setContentPane(new ExtractFromLogFilePanelContainer(dialog));
		SwingUtils.centerWindow(dialog);
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}

}
