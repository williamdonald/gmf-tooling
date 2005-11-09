/*
 * Copyright (c) 2005 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.codegen.util;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.codegen.jet.JETEmitter;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.emf.codegen.jmerge.JControlModel;
import org.eclipse.emf.codegen.jmerge.JMerger;
import org.eclipse.gmf.codegen.gmfgen.GenChildContainer;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.gmf.codegen.gmfgen.GenCompartment;
import org.eclipse.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.gmf.codegen.gmfgen.GenLink;
import org.eclipse.gmf.codegen.gmfgen.GenLinkLabel;
import org.eclipse.gmf.codegen.gmfgen.GenNode;
import org.eclipse.gmf.codegen.gmfgen.GenNodeLabel;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

/**
 * Invokes JET templates to populate diagram editor project.
 * 
 * @author artem
 */
public class Generator implements Runnable {

	private final GenDiagram myDiagram;

	// myDestRoot.getJavaProject().getElementName() == myDestProject.getName()
	private IPackageFragmentRoot myDestRoot;

	private IProject myDestProject;

	private JControlModel myJControlModel;

	private CodeFormatter myCodeFormatter;

	private IProgressMonitor myProgress;

	private IStatus myRunStatus = Status.CANCEL_STATUS;
	private List/*<IStatus>*/ myExceptions;

	public Generator(GenDiagram diagram) {
		myDiagram = diagram;
	}

	public void run(IProgressMonitor progress) throws InterruptedException {
		setProgressMonitor(progress);
		doRun();
	}

	public void run() {
		try {
			doRun();
		} catch (InterruptedException ex) {
			myRunStatus = new Status(IStatus.CANCEL, "org.eclipse.gmf.codegen", 0, Messages.interrupted, ex);
		}
	}

	private void doRun() throws InterruptedException {
		try {
			setupProgressMonitor();
			myExceptions = new LinkedList/*<IStatus>*/();
			initializeEditorProject();

			// edit parts, edit policies and providers
			generateStructuralFeatureParser();
			generateBaseItemSemanticEditPolicy();
			generateReferenceConnectionEditPolicy();
			generateDiagramItemSemanticEditPolicy();
			for (Iterator nodes = myDiagram.getNodes().iterator(); nodes.hasNext();) {
				GenNode node = (GenNode) nodes.next();
				generateNode(node);
			}
			for (Iterator it = myDiagram.getLinks().iterator(); it.hasNext();) {
				final GenLink next = (GenLink) it.next();
				generateViewFactory(next);
				generateSemanticHints(next);
				generateLinkEditPart(next);
				generateLinkItemSemanticEditPolicy(next);
				for (Iterator labels = next.getLabels().iterator(); labels.hasNext();) {
					GenLinkLabel label = (GenLinkLabel) labels.next();
					generateLinkLabelViewFactory(label);
					generateTextLinkLabelViewFactory(label);
				}
			}
			generateViewFactory(myDiagram);
			generateDiagramEditPart();
			generateEditPartFactory();
			generateElementTypes();
			generateViewProvider();
			generateEditPartProvider();
			generateMetamodelSupportProvider();
			generateModelingAssistantProvider();
			generateIconProvider();

			// editor
			generateInitDiagramFileAction();
			generatePalette();
			generateDiagramEditorUtil();
			generateDiagramFileCreator();
			generateVisualIDRegistry();
			generateCreationWizard();
			generateCreationWizardPage();
			generateEditor();
			generateDocumentProvider();
			generateActionBarContributor();
			generateMatchingStrategy();
			generatePreferencesInitializer();
			generatePluginClass();
			generatePluginXml();

			if (myExceptions.isEmpty()) {
				myRunStatus = Status.OK_STATUS;
			} else {
				IStatus[] s = (IStatus[]) myExceptions.toArray(new IStatus[myExceptions.size()]);
				myRunStatus = new MultiStatus("org.eclipse.gmf.codegen", 0, s, Messages.problems, null);
			}
		} catch (NullPointerException ex) {
			myRunStatus = new Status(IStatus.ERROR, "org.eclipse.gmf.codegen", 0, NullPointerException.class.getName(), ex);
		} catch (JETException ex) {
			myRunStatus = ex.getStatus();
		} catch (UnexpectedBehaviourException ex) {
			myRunStatus = new Status(Status.ERROR, "org.eclipse.gmf.codegen", 0, Messages.unexpected, ex);
		} finally {
			myProgress.done();
			myExceptions = null;
		}
	}

	/**
	 * Provides information about success/failures during {@link #run()}
	 * @return state of the generator run, or CANCEL if generator was not yet run.
	 */
	public IStatus getRunStatus() {
		return myRunStatus;
	}

	private void generateNode(GenNode node) throws JETException, InterruptedException {
		generateNodeEditPart(node);
		generateCommonNode(node);
	}

	private void generateChildnode(GenChildNode child) throws JETException, InterruptedException {
		generateChildNodeEditPart(child);
		generateCommonNode(child);
	}
	
	private void generateCommonNode(GenNode node) throws JETException, InterruptedException {
		for (Iterator labels = node.getLabels().iterator(); labels.hasNext();) {
			GenNodeLabel label = (GenNodeLabel) labels.next();
			generateNodeLabelEditPart(label);
			generateTextLabelViewFactory(label);
		}
		for (Iterator compartments = node.getCompartments().iterator(); compartments.hasNext();) {
			GenCompartment compartment = (GenCompartment) compartments.next();
			generateCompartment(compartment);
		}
		generateSemanticHints(node);
		generateChildContainer(node);
		generateNodeItemSemanticEditPolicy(node);
	}
	
	private void generateCompartment(GenCompartment compartment) throws JETException, InterruptedException {
		generateCompartmentEditPart(compartment);
		generateCompartmentItemSemanticEditPolicy(compartment);
		generateChildContainer(compartment);
	}
	
	private void generateChildContainer(GenChildContainer childContainer) throws JETException, InterruptedException {
		generateViewFactory(childContainer);
		for (Iterator childNodes = childContainer.getChildNodes().iterator(); childNodes.hasNext();) {
			GenChildNode childNode = (GenChildNode) childNodes.next();
			generateChildnode(childNode);
		}
	}

	// parts

	private void generateDiagramEditPart() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getDiagramEditPartEmitter(),
			myDiagram.getEditPartsPackageName(),
			myDiagram.getEditPartClassName(),
			myDiagram
		);
	}

	private void generateNodeEditPart(GenNode genNode) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getNodeEditPartEmitter(),
			myDiagram.getEditPartsPackageName(),
			genNode.getEditPartClassName(),
			genNode
		);
	}

	private void generateNodeLabelEditPart(GenNodeLabel label) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getNodeLabelEditPartEmitter(),
			myDiagram.getEditPartsPackageName(),
			label.getEditPartClassName(),
			label
		);
	}

	private void generateChildNodeEditPart(GenChildNode genChildNode) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getChildNodeEditPartEmitter(),
			myDiagram.getEditPartsPackageName(),
			genChildNode.getEditPartClassName(),
			genChildNode
		);
	}
	
	private void generateCompartmentEditPart(GenCompartment genCompartment) throws JETException, InterruptedException {
		generate(
				EmitterFactory.getCompartmentEditPartEmitter(),
				myDiagram.getEditPartsPackageName(),
				genCompartment.getEditPartClassName(),
				genCompartment
		);
	}

	private void generateLinkEditPart(GenLink genLink) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getLinkEditPartEmitter(),
			myDiagram.getEditPartsPackageName(),
			genLink.getEditPartClassName(),
			genLink
		);
	}

	private void generateEditPartFactory() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getEditPartFactoryEmitter(),
			myDiagram.getEditPartsPackageName(),
			myDiagram.getEditPartFactoryClassName(),
			myDiagram
		);
	}

	// edit policies

	private void generateBaseItemSemanticEditPolicy() throws JETException, InterruptedException {
		String fqn = myDiagram.getBaseItemSemanticEditPolicyQualifiedClassName();
		generate(
			EmitterFactory.getBaseItemSemanticEditPolicyEmitter(),
			myDiagram.getEditPoliciesPackageName(),
			fqn.substring(fqn.lastIndexOf('.') + 1),
			myDiagram
		);
	}

	private void generateReferenceConnectionEditPolicy() throws JETException, InterruptedException {
		String fqn = myDiagram.getReferenceConnectionEditPolicyQualifiedClassName();
		generate(
			EmitterFactory.getReferenceConnectionEditPolicyEmitter(),
			myDiagram.getEditPoliciesPackageName(),
			fqn.substring(fqn.lastIndexOf('.') + 1),
			myDiagram
		);
	}

	private void generateDiagramItemSemanticEditPolicy() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getDiagramItemSemanticEditPolicyEmitter(),
			myDiagram.getEditPoliciesPackageName(),
			myDiagram.getItemSemanticEditPolicyClassName(),
			myDiagram
		);
	}

	private void generateCompartmentItemSemanticEditPolicy(GenCompartment genCompartment) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getCompartmentItemSemanticEditPolicyEmitter(),
			myDiagram.getEditPoliciesPackageName(),
			genCompartment.getItemSemanticEditPolicyClassName(),
			genCompartment
		);
	}

	private void generateNodeItemSemanticEditPolicy(GenNode genNode) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getNodeItemSemanticEditPolicyEmitter(),
			myDiagram.getEditPoliciesPackageName(),
			genNode.getItemSemanticEditPolicyClassName(),
			genNode
		);
	}

	private void generateLinkItemSemanticEditPolicy(GenLink genLink) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getLinkItemSemanticEditPolicyEmitter(),
			myDiagram.getEditPoliciesPackageName(),
			genLink.getItemSemanticEditPolicyClassName(),
			genLink
		);
	}

	// providers

	private void generateStructuralFeatureParser() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getStructuralFeatureParserEmitter(),
			myDiagram.getProvidersPackageName(),
			"StructuralFeatureParser", //$NON-NLS-1$
			myDiagram
		);
	}

	private void generateSemanticHints(GenCommonBase genElement) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getSemanticHintsEmitter(),
			myDiagram.getProvidersPackageName(),
			AccessUtil.getSemanticHintsClassName(genElement),
			genElement
		);
	}

	private void generateViewFactory(GenCommonBase genElement) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getViewFactoryEmitter(),
			myDiagram.getProvidersPackageName(),
			genElement.getNotationViewFactoryClassName(),
			genElement
		);
	}

	private void generateLinkLabelViewFactory(GenLinkLabel label) throws JETException, InterruptedException {
		generate(
			EmitterFactory.getLinkLabelViewFactoryEmitter(),
			myDiagram.getProvidersPackageName(),
			label.getNotationViewFactoryClassName(),
			label
		);
	}
	
	private void generateTextLinkLabelViewFactory(GenLinkLabel label) throws JETException, InterruptedException {
		generate(
				EmitterFactory.getTextLabelViewFactoryEmitter(),
				myDiagram.getProvidersPackageName(),
				label.getTextNotationViewFactoryClassName(),
				label
		);
	}
	
	private void generateTextLabelViewFactory(GenNodeLabel label) throws JETException, InterruptedException {
		generate(
				EmitterFactory.getTextLabelViewFactoryEmitter(),
				myDiagram.getProvidersPackageName(),
				label.getNotationViewFactoryClassName(),
				label
		);
	}

	private void generateElementTypes() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getElementTypesEmitter(),
			myDiagram.getProvidersPackageName(),
			"ElementTypes", //$NON-NLS-1$
			myDiagram
		);
	}

	private void generateViewProvider() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getViewProviderEmitter(),
			myDiagram.getProvidersPackageName(),
			myDiagram.getViewProviderClassName(),
			myDiagram
		);
	}

	private void generateEditPartProvider() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getEditPartProviderEmitter(),
			myDiagram.getProvidersPackageName(),
			myDiagram.getEditPartProviderClassName(),
			myDiagram
		);
	}
	
	private void generateMetamodelSupportProvider() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getMetamodelSupportProviderEmitter(),
			myDiagram.getProvidersPackageName(),
			myDiagram.getMetamodelSupportProviderClassName(),
			myDiagram);
	}

	private void generateModelingAssistantProvider() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getModelingAssistantProviderEmitter(),
			myDiagram.getProvidersPackageName(),
			myDiagram.getModelingAssistantProviderClassName(),
			myDiagram);
	}

	private void generateIconProvider() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getIconProviderEmitter(),
			myDiagram.getProvidersPackageName(),
			myDiagram.getIconProviderClassName(),
			myDiagram);
	}

	// editor

	private void generateInitDiagramFileAction() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getInitDiagramFileActionEmitter(),
			myDiagram.getEditorPackageName(),
			myDiagram.getInitDiagramFileActionClassName(),
			myDiagram);
	}

	private void generatePalette() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getPaletteEmitter(),
			myDiagram.getPalette().getPackageName(),
			myDiagram.getPalette().getFactoryClassName(),
			myDiagram
		);
	}

	private void generateDiagramEditorUtil() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getDiagramEditorUtilEmitter(),
			myDiagram.getEditorPackageName(),
			"DiagramEditorUtil", //$NON-NLS-1$
			myDiagram
		);
	}

	private void generateDiagramFileCreator() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getDiagramFileCreatorEmitter(),
			myDiagram.getEditorPackageName(),
			"DiagramFileCreator", //$NON-NLS-1$
			myDiagram
		);
	}
	
	private void generateVisualIDRegistry() throws JETException, InterruptedException {
		generate(
				EmitterFactory.getVisualIDRegistryEmitter(),
				myDiagram.getEditorPackageName(),
				"VisualIDRegistry", //$NON-NLS-1$
				myDiagram
			);
	}

	private void generateCreationWizard() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getCreationWizardEmitter(),
			myDiagram.getEditorPackageName(),
			"CreationWizard", //$NON-NLS-1$
			myDiagram
		);
	}

	private void generateCreationWizardPage() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getCreationWizardPageEmitter(),
			myDiagram.getEditorPackageName(),
			"CreationWizardPage", //$NON-NLS-1$
			myDiagram
		);
	}

	private void generateEditor() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getEditorEmitter(),
			myDiagram.getEditorPackageName(),
			myDiagram.getEditorClassName(),
			myDiagram
		);
	}
	
	private void generateDocumentProvider() throws JETException, InterruptedException {
		generate(
				EmitterFactory.getDocumentProviderEmitter(),
				myDiagram.getEditorPackageName(),
				myDiagram.getDocumentProviderClassName(),
				myDiagram
			);
	}

	private void generateActionBarContributor() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getActionBarContributorEmitter(),
			myDiagram.getEditorPackageName(),
			myDiagram.getActionBarContributorClassName(),
			myDiagram
		);
	}

	private void generateMatchingStrategy() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getMatchingStrategyEmitter(),
			myDiagram.getEditorPackageName(),
			myDiagram.getMatchingStrategyClassName(),
			myDiagram
		);
	}

	private void generatePreferencesInitializer() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getPreferencesInitializerEmitter(),
			myDiagram.getEditorPackageName(),
			"PreferencesInitializer", //$NON-NLS-1$
			myDiagram
		);
	}

	private void generatePluginClass() throws JETException, InterruptedException {
		generate(
			EmitterFactory.getPluginClassEmitter(),
			myDiagram.getEditorPackageName(), 
			myDiagram.getPluginClassName(),
			myDiagram
		);
	}

	private void generatePluginXml() throws JETException, InterruptedException {
		IProgressMonitor pm = getNextStepMonitor();
		try {
			pm.beginTask(Messages.pluginxml, 3);
			JETEmitter emitter = EmitterFactory.getPluginXmlEmitter();
			String genText = emitter.generate(new SubProgressMonitor(pm, 1), new Object[] { myDiagram });
			IFile f = myDestProject.getFile("plugin.xml"); //$NON-NLS-1$
			// FIXME merge!
			if (f.exists()) {
				f.setContents(new ByteArrayInputStream(genText.getBytes()), true, true, new SubProgressMonitor(pm, 1));
			} else {
				f.create(new ByteArrayInputStream(genText.getBytes()), true, new SubProgressMonitor(pm, 1));
			}
			f.getParent().refreshLocal(IResource.DEPTH_ONE, new SubProgressMonitor(pm, 1));
		} catch (CoreException ex) {
			myExceptions.add(ex.getStatus());
		} finally {
			pm.done();
		}
	}

	/**
	 * Optionally, specify progressMonitor to use. Should be called prior to {@link #run()}
	 * @param progress
	 */
	public void setProgressMonitor(IProgressMonitor progress) {
		myProgress = progress;
	}

	private void setupProgressMonitor() {
		if (myProgress == null) {
			myProgress = new NullProgressMonitor();
			return;
			// no need to set it up
		}
		Counter c = new Counter(myDiagram);
		c.setAdditionalOperations(8); // init, palette, editor, plugin.xml, etc
		c.setOperationsPerNode(2);
		c.setOperationsPerChildNode(1);
		c.setOperationsPerLink(2);
		myProgress.beginTask(Messages.start, c.getTotal());
	}

	private IProgressMonitor getNextStepMonitor() throws InterruptedException {
		if (myProgress.isCanceled()) {
			throw new InterruptedException();
		}
		return new SubProgressMonitor(myProgress, 1);
	}

	private void initializeEditorProject() throws UnexpectedBehaviourException, InterruptedException {
		myDestProject = ResourcesPlugin.getWorkspace().getRoot().getProject(myDiagram.getPluginID());
		final Path srcPath = new Path('/' + myDestProject.getName() + "/src"); //$NON-NLS-1$
		final Path projectLocation = null; // use default
		final List referencedProjects = createReferencedProjectsList();
		final int style = org.eclipse.emf.codegen.ecore.Generator.EMF_PLUGIN_PROJECT_STYLE;
		final List pluginVariables = createPluginVariablesList();
		final IProgressMonitor pm = getNextStepMonitor();

		org.eclipse.emf.codegen.ecore.Generator.createEMFProject(srcPath, projectLocation, referencedProjects, pm, style, pluginVariables);

		try {
			myDestRoot = JavaCore.create(myDestProject).findPackageFragmentRoot(srcPath);
		} catch (JavaModelException ex) {
			throw new UnexpectedBehaviourException(ex.getMessage());
		}
		if (myDestRoot == null) {
			throw new UnexpectedBehaviourException("no source root can be found");
		}
	}

	private List createPluginVariablesList() {
		return Collections.EMPTY_LIST;
	}

	private List createReferencedProjectsList() {
		return Collections.EMPTY_LIST;
	}

	/**
	 * NOTE: potential problem - packageName and className should match those specified in 
	 * the template. Besides, getQualifiedXXX helpers in diagram GenModel should also correctly
	 * return qualified class names.  
	 */
	private void generate(JETEmitter emitter, String packageName, String className, Object input) throws InterruptedException {
		IProgressMonitor pm = getNextStepMonitor();
		try {
			pm.beginTask(className, 4);
			String genText = emitter.generate(new SubProgressMonitor(pm, 1), new Object[] { input });
			IPackageFragment pf = myDestRoot.createPackageFragment(packageName, true, new SubProgressMonitor(pm, 1));
			ICompilationUnit cu = pf.getCompilationUnit(className + ".java"); //$NON-NLS-1$
			if (cu.exists()) {
				genText = merge(genText, cu.getSource(), new SubProgressMonitor(pm, 1));
			} else {
				pm.worked(1);
			}
			pf.createCompilationUnit(cu.getElementName(), formatCode(genText), true, new SubProgressMonitor(pm, 1));
		} catch (NullPointerException ex) {
			myExceptions.add(new Status(IStatus.ERROR, "org.eclipse.gmf.codegen", 0, ex.getMessage(), ex));
		} catch (CoreException ex) {
			myExceptions.add(ex.getStatus());
		} finally {
			pm.done();
		}
	}

	private String merge(String generatedText, String oldContents, IProgressMonitor pm) {
		pm.beginTask(Messages.merge, 1);
		JMerger jMerge = new JMerger();
		jMerge.setControlModel(getJControlModel());
		jMerge.setSourceCompilationUnit(jMerge.createCompilationUnitForContents(generatedText));
		jMerge.setTargetCompilationUnit(jMerge.createCompilationUnitForContents(oldContents));
		jMerge.merge();
		pm.done();
		return jMerge.getTargetCompilationUnitContents();
	}

	private String formatCode(String text) {
		IDocument doc = new Document(text);
		TextEdit edit = getCodeFormatter().format(CodeFormatter.K_COMPILATION_UNIT, doc.get(), 0, doc.get().length(), 0, null);

		try {
			// check if text formatted successfully 
			if(edit != null) {
				edit.apply(doc);
				text = doc.get();				
			}		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return text;
	}

	private CodeFormatter getCodeFormatter() {
		if (myCodeFormatter == null) {
			myCodeFormatter = ToolFactory.createCodeFormatter(null);
		}
		return myCodeFormatter;
	}

	private JControlModel getJControlModel() {
		if (myJControlModel == null) {
			myJControlModel = new JControlModel(EmitterFactory.getJMergeControlFile().toString());
		}
		return myJControlModel;
	}

	private static final class Counter {
		private final GenDiagram myDiagram;
		private int myOpsPerNode = 1;
		private int myOpsPerLink = 1;
		private int myOpsPerChildNode = 1;
		private int myAdditionalOps = 0;

		Counter(GenDiagram diagram) {
			myDiagram = diagram;
		}

		public void setOperationsPerNode(int opsPerNode) {
			 myOpsPerNode = opsPerNode;
		}
		public void setOperationsPerLink(int opsPerLink) {
			myOpsPerLink = opsPerLink;
		}
		public void setOperationsPerChildNode(int opsPerChild) {
			myOpsPerChildNode = opsPerChild;
		}
		public void setAdditionalOperations(int additionalOps) {
			myAdditionalOps = additionalOps;
		}
		public int getTotal() {
			int rv = myAdditionalOps;
			rv += myDiagram.getNodes().size() * myOpsPerNode;
			rv += myDiagram.getLinks().size() * myOpsPerLink;
			rv += getChildNodeCount() * myOpsPerChildNode;
			return rv;  
		}

		private int getChildNodeCount() {
			int rv = 0;
			for (Iterator it = myDiagram.getNodes().iterator(); it.hasNext();) {
				rv += AccessUtil.getAllChildNodes((GenNode) it.next()).size();
			}
			return rv;
		}
	}
}
