/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wso2.developerstudio.datamapper.diagram.custom.dialogs;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.wso2.developerstudio.datamapper.Concat;
import org.wso2.developerstudio.datamapper.DataMapperFactory;
import org.wso2.developerstudio.datamapper.DataMapperPackage;
import org.wso2.developerstudio.datamapper.Multiply;
import org.wso2.developerstudio.datamapper.OperatorLeftConnector;
import org.wso2.developerstudio.datamapper.impl.ConcatImpl;
import org.wso2.developerstudio.datamapper.impl.MultiplyImpl;

public class ConfigureMultiplyOperatorDialog extends AbstractConfigureOperatorDialog {

	private String inputCount;
	private TransactionalEditingDomain editingDomain;
	private ArrayList<OperatorLeftConnector> caseOutputConnectors = new ArrayList<OperatorLeftConnector>();
	private MultiplyImpl multiplyImpl;

	public ConfigureMultiplyOperatorDialog(Shell parentShell, Multiply multiplyOperator,
			TransactionalEditingDomain editingDomain) {
		super(parentShell);
		this.multiplyImpl = (MultiplyImpl) multiplyOperator;
		this.editingDomain = editingDomain;
	}

	@Override
	public void create() {
		super.create();
		setTitle("Configure Multiply Operator");
		setMessage("Set multiply operator properties", IMessageProvider.INFORMATION);
	}

	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		// Set title.
		newShell.setText("Configure Multiply Operator");
	}

	protected Control createDialogArea(Composite parent) {
		Composite area = (Composite) super.createDialogArea(parent);
		Composite container = new Composite(area, SWT.NONE);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);

		Label concatInputConnectorCountLabel = new Label(container, SWT.NULL);
		concatInputConnectorCountLabel.setText("Number of Inputs : ");

		final Text inputConnectorCount = new Text(container, SWT.BORDER);
		inputConnectorCount.setLayoutData(dataPropertyConfigText);
		inputConnectorCount
				.setText(multiplyImpl.getBasicContainer().getLeftContainer().getLeftConnectors().size() + "");
		inputCount = inputConnectorCount.getText();
		inputConnectorCount.addListener(SWT.Modify, new Listener() {
			public void handleEvent(Event event) {
				try {
					inputCount = new String(inputConnectorCount.getText());
					if (!(StringUtils.isEmpty(inputCount))) {
						getButton(IDialogConstants.OK_ID).setEnabled(true);
						validate();
					} else {
						getButton(IDialogConstants.OK_ID).setEnabled(false);
					}
				} catch (Exception e) {
					getButton(IDialogConstants.OK_ID).setEnabled(false);
				}
			}
		});

		return area;
	}

	/**
	 * Create contents of the button bar.
	 * 
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		validate();
	}

	private void validate() {
		boolean isEnabled = false;
		Button okButton = getButton(IDialogConstants.OK_ID);
		if (inputCount != null) {
			if (!inputCount.equals("0") && !StringUtils.isEmpty(inputCount)) {
				isEnabled = true;
			}
		}
		if (okButton != null) {
			okButton.setEnabled(isEnabled);
		}
	}

	protected void okPressed() {

		int number = Integer.parseInt(inputCount)
				- multiplyImpl.getBasicContainer().getLeftContainer().getLeftConnectors().size();
		if (number > 0) {
			for (int i = 0; i < number; ++i) {
				OperatorLeftConnector multiplyOperatorContainers = DataMapperFactory.eINSTANCE
						.createOperatorLeftConnector();
				AddCommand addCmd = new AddCommand(editingDomain, multiplyImpl.getBasicContainer().getLeftContainer(),
						DataMapperPackage.Literals.OPERATOR_LEFT_CONTAINER__LEFT_CONNECTORS,
						multiplyOperatorContainers);
				if (addCmd.canExecute()) {
					editingDomain.getCommandStack().execute(addCmd);
				}

			}
		} else if (number < 0) {

			for (int i = 0; i < Math.abs(number); i++) {
				EList<OperatorLeftConnector> listOfLeftConnectors = multiplyImpl.getBasicContainer().getLeftContainer()
						.getLeftConnectors();
				OperatorLeftConnector multiplyOperatorConnector = listOfLeftConnectors
						.get(listOfLeftConnectors.size() - 1);
				caseOutputConnectors.add(multiplyOperatorConnector);
				DeleteCommand deleteCmd = new DeleteCommand(editingDomain, caseOutputConnectors);
				if (deleteCmd.canExecute()) {
					editingDomain.getCommandStack().execute(deleteCmd);
				}
				caseOutputConnectors.remove(multiplyOperatorConnector);
			}
		}

		super.okPressed();
	}

}
