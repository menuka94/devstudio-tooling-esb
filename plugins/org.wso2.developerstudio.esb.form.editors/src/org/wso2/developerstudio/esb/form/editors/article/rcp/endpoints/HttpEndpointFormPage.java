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


package org.wso2.developerstudio.esb.form.editors.article.rcp.endpoints;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;
import org.wso2.developerstudio.eclipse.gmf.esb.EndPointProperty;
import org.wso2.developerstudio.esb.form.editors.article.providers.ConfigureEndPointPropertiesDialog;
import org.wso2.developerstudio.esb.form.editors.article.rcp.Messages;
/**
 * 
 * To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Generation - Code and Comments
 */
public class HttpEndpointFormPage extends EndpointFormPage {

	public Combo endpointTrace;
	public Combo endpointStatistics;
	public Text httpEP_UriTemplate;
	public Text httpEP_Method;

	public Button httpEP_Properties;
	public Text httpEP_Description;
	public List<EndPointProperty> endpointPropertyList;
	
    Section basicSection;
    Section miscSection;
	
    public HttpEndpointFormPage(FormEditor editor) {
		super(editor);
	}

	protected void createFormContent(IManagedForm managedForm) {

		super.createFormContent(managedForm);
		
		form.setText(Messages.getString("HttpEndpointPage.sectionMainTitle"));
	}

	public void createFormMiscSection() {

		 /* Misc Section */ 
	    miscSection = endpointCommons.createSection(form, toolkit, Messages.getString("EndpointPage.section.misc"));
		
		GridData miscSectionGridData = new GridData();
		miscSectionGridData.horizontalSpan = 3;
		miscSectionGridData.horizontalAlignment = GridData.FILL;
		miscSectionGridData.grabExcessHorizontalSpace = true;
		miscSection.setLayoutData(miscSectionGridData);
		// miscSection.setLayoutData(new TableWrapData(TableWrapData.FILL));

		Composite miscSectionClient = toolkit.createComposite(miscSection);
		miscSectionClient.setLayout(new GridLayout());
		miscSection.setClient(miscSectionClient);	
		
		toolkit.createLabel(miscSectionClient, "URI Template");
		httpEP_UriTemplate = toolkit.createText(miscSectionClient, "");
		httpEP_UriTemplate.setBackground(new Color(null, 229,236,253));
		//httpEP_UriTemplate.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		GridData httpEPUriGridData = new GridData();
		httpEPUriGridData.horizontalSpan = 3;
		httpEPUriGridData.horizontalAlignment = GridData.FILL;
		httpEPUriGridData.grabExcessHorizontalSpace = true;
		httpEP_UriTemplate.setLayoutData(httpEPUriGridData);
		
		toolkit.createLabel(miscSectionClient, "HTTP Method");
		httpEP_Method = toolkit.createText(miscSectionClient, "");
		httpEP_Method.setBackground(new Color(null, 229,236,253));
		//httpEP_Method.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		GridData httpEPMethodGridData = new GridData();
		httpEPMethodGridData.horizontalSpan = 3;
		httpEPMethodGridData.horizontalAlignment = GridData.FILL;
		httpEPMethodGridData.grabExcessHorizontalSpace = true;
		httpEP_Method.setLayoutData(httpEPMethodGridData);
	
		toolkit.createLabel(miscSectionClient, "Description");
		httpEP_Description = toolkit.createText(miscSectionClient, "");
		httpEP_Description.setBackground(new Color(null, 229,236,253));
		//httpEP_Description.setLayoutData(new TableWrapData(TableWrapData.FILL_GRAB));
		GridData httpEPDescriptionGridData = new GridData();
		httpEPDescriptionGridData.horizontalSpan = 3;
		httpEPDescriptionGridData.horizontalAlignment = GridData.FILL;
		httpEPDescriptionGridData.grabExcessHorizontalSpace = true;
		httpEP_Description.setLayoutData(httpEPDescriptionGridData);
		
		httpEP_Properties = toolkit.createButton(miscSectionClient, "Add Properties", SWT.PUSH);
		httpEP_Properties.setBackground(new Color(null, 229,236,253));
		httpEP_Properties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = Display.getDefault().getActiveShell();
				ConfigureEndPointPropertiesDialog paramDialog = new ConfigureEndPointPropertiesDialog(shell,endpointPropertyList);
				paramDialog.setBlockOnOpen(true);
				paramDialog.open();
				endpointPropertyList = paramDialog.getEndpointPropertyList();		
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	public void createFormQosSection() {
		endpointCommons.createFormQosSection(form, toolkit);
	}
	
	public void createFormErrorHandlingSection() {
		endpointCommons.createFormErrorHandlingSection(form, toolkit);
	}

	public EndpointCommons getEndpointCommons() {
		return endpointCommons;
	}
	
}
