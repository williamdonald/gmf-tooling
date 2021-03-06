/*
 * Copyright (c) 2006, 2010 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Alexander Shatalin (Borland) - initial API and implementation
 */
package org.eclipse.gmf.tests.gef;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ContentHandler;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.Tool;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase;
import org.eclipse.gmf.codegen.gmfgen.GenCompartment;
import org.eclipse.gmf.codegen.gmfgen.GenNode;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.emf.core.GMFEditingDomainFactory;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tests.JobTracker;
import org.eclipse.gmf.tests.Utils;
import org.eclipse.gmf.tests.setup.GeneratedDiagramPlugin;
import org.eclipse.gmf.tests.setup.RuntimeBasedGeneratorConfiguration;
import org.eclipse.gmf.tests.setup.ViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;

public class DiagramEditorTest extends AbstractDiagramEditorTest {

	public DiagramEditorTest(String name) {
		super(name, new RuntimeBasedGeneratorConfiguration());
	}

	public void testSaveDiagramChanges() {
		IEditorPart editorPart = getEditor();
		assertFalse("Created Editor is dirty", editorPart.isDirty());
		Diagram diagram = getDiagram();
		Command setNameCommand = getViewerConfiguration().getSetNotationalElementStructuralFeature(diagram, NotationPackage.eINSTANCE.getDiagram_Name(), getUniqueString());
		checkEditorDirtyState(setNameCommand, editorPart, getViewerConfiguration().getViewer());
	}

	private void checkEditorDirtyState(Command setNameCommand, IEditorPart editorPart, EditPartViewer viewer) {
		viewer.getEditDomain().getCommandStack().execute(setNameCommand);
		assertTrue("Editor was not marked as dirty", editorPart.isDirty());
		editorPart.doSave(new NullProgressMonitor());
		assertFalse("Editor was not saved", editorPart.isDirty());
	}

	public void testSaveNotationElementChanges() {
		IEditorPart editorPart = getEditor();
		ViewerConfiguration viewerConfiguration = getViewerConfiguration();
		Diagram diagram = getDiagram();
		Node nodeA = createNodeA(diagram, editorPart);
		assertTrue("Created node invisible", nodeA.isVisible());
		Command setNameCommand = viewerConfiguration.getSetNotationalElementStructuralFeature(nodeA, NotationPackage.eINSTANCE.getView_Visible(), Boolean.FALSE);
		checkEditorDirtyState(setNameCommand, editorPart, getViewerConfiguration().getViewer());
	}

	/**
	 * Creating Node, saving editor
	 */
	private Node createNodeA(Diagram diagram, IEditorPart editorPart) {
		Node result = createNode(getSetup().getGenModel().getNodeA(), diagram);
		editorPart.doSave(new NullProgressMonitor());
		assertFalse("Editor was not saved", editorPart.isDirty());
		return result;
	}

	public void testSaveDomainElementChangesSeparateFiles() {
		useSameFileForDiagramAndModel(false);
		checkSaveDomainElementChanges();
	}

	public void testSaveDomainElementChangesSameFile() {
		useSameFileForDiagramAndModel(true);
		checkSaveDomainElementChanges();
	}

	private void checkSaveDomainElementChanges() {
		IEditorPart editorPart = getEditor();
		ViewerConfiguration viewerConfiguration = getViewerConfiguration();
		EditPartViewer viewer = viewerConfiguration.getViewer();
		Diagram diagram = getDiagram();

		Node nodeA = createNodeA(diagram, editorPart);
		Command setLabelCommand = viewerConfiguration.getSetBusinessElementStructuralFeatureCommand(nodeA, "label", getUniqueString());
		checkEditorDirtyState(setLabelCommand, editorPart, viewer);
	}

	/**
	 * Testing fix of request:
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=153893
	 */
	public void testSaveWithUnloadedResource() throws IOException {
		IEditorPart editorPart = getEditor();
		ViewerConfiguration viewerConfiguration = getViewerConfiguration();
		EditPartViewer viewer = viewerConfiguration.getViewer();
		Diagram diagram = getDiagram();

		// Creating arbitrary model resource + unloading it
		URI anotherResourceURI = diagram.eResource().getURI().trimFileExtension().appendFileExtension("additional." + getSetup().getGenModel().getGenDiagram().getEditorGen().getDomainFileExtension());
		createAdditionalModelResource(anotherResourceURI);
		Resource anotherResource = diagram.eResource().getResourceSet().getResource(anotherResourceURI, true);
		anotherResource.unload();

		// Changing + saving editor
		Command setNameCommand = viewerConfiguration.getSetNotationalElementStructuralFeature(diagram, NotationPackage.eINSTANCE.getDiagram_Name(), getUniqueString());
		checkEditorDirtyState(setNameCommand, editorPart, viewer);

		// Checking contents of unloaded resource.
		checkAdditionalModelResource(anotherResourceURI);
	}

	public void testUnspecifiedTypeRequest() {
		EditPartViewer viewer = getViewerConfiguration().getViewer();
		Diagram diagram = getDiagram();
		CreationTool creationTool = getNodeCreationTool(viewer);

		GenNode genNodeA = getSetup().getGenModel().getNodeA();
		Node aNode = checkCreateNode(viewer, diagram, creationTool, genNodeA.getVisualID());

		assertTrue("Incorrect setup passed", genNodeA.getCompartments().size() > 0);
		GenCompartment genCompartment = genNodeA.getCompartments().get(0);
		assertTrue("Incorrect setup passed", genCompartment.getChildNodes().size() > 0);
		GenNode childNode = genCompartment.getChildNodes().get(0);
		assertNotNull("Incorrect setup passed", childNode);

		Node compartment = findChildnode(aNode, genCompartment);
		checkCreateNode(viewer, compartment, creationTool, childNode.getVisualID());
	}

	private Node findChildnode(Node parentNode, GenCommonBase genElement) {
		String visualID = String.valueOf(genElement.getVisualID());
		for (Iterator<?> it = parentNode.getChildren().iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if (nextView.getType().equals(visualID)) {
				assertTrue(nextView instanceof Node);
				return (Node) nextView;
			}
		}
		fail("Node not found" + visualID);
		return null;
	}

	private Node checkCreateNode(EditPartViewer viewer, View parentView, CreationTool creationTool, int newNodeVisualID) {
		Request request = creationTool.createCreateRequest();
		EditPart parentEP = (EditPart) viewer.getEditPartRegistry().get(parentView);
		assertNotNull(parentEP);
		Command createANodeCommand = parentEP.getCommand(request);

		viewer.getEditDomain().getCommandStack().execute(createANodeCommand);
		assertTrue(parentView.getChildren().size() == 1);
		Node aNode = (Node) parentView.getChildren().get(0);
		assertEquals("Node with incorrect visual ID was created.", aNode.getType(), String.valueOf(newNodeVisualID));
		return aNode;
	}

	private CreationTool getNodeCreationTool(EditPartViewer viewer) {
		PaletteRoot paletteRoot = viewer.getEditDomain().getPaletteViewer().getPaletteRoot();
		assertNotNull("Palette root absent", paletteRoot);
		PaletteContainer container = findPaletteContainer(paletteRoot, "Default");
		assertTrue("Incorrect palette was created", container.getChildren().size() > 0);
		assertTrue("Incorrect palette was created", container.getChildren().get(0) instanceof ToolEntry);
		ToolEntry toolEntry = (ToolEntry) container.getChildren().get(0);
		Tool tool = toolEntry.createTool();
		assertTrue("Incorrect palette was created", tool instanceof CreationTool);
		CreationTool creationTool = (CreationTool) tool;
		creationTool.setViewer(viewer);
		creationTool.setEditDomain(viewer.getEditDomain());
		return creationTool;
	}

	private PaletteContainer findPaletteContainer(PaletteRoot paletteRoot, String groupName) {
		for (Iterator<?> it = paletteRoot.getChildren().iterator(); it.hasNext();) {
			PaletteContainer nextContainer = (PaletteContainer) it.next();
			if (groupName.equals(nextContainer.getLabel())) {
				return nextContainer;
			}
		}
		fail("No palette container " + groupName + " fourn in the palette");
		return null;
	}

	private void checkAdditionalModelResource(URI anotherResourceURI) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.getResource(anotherResourceURI, true);
		assertTrue("Unloaded resource was changed while saving editor", resource.getContents().size() == 1);
	}

	private void createAdditionalModelResource(URI anotherResourceURI) throws IOException {
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = resourceSet.createResource(anotherResourceURI, ContentHandler.UNSPECIFIED_CONTENT_TYPE);
		EObject domainDiagramElement = createDiagramDomainObject(getSetup());
		resource.getContents().add(domainDiagramElement);
		resource.save(Collections.EMPTY_MAP);
	}

	public void testDiagramAndModelExternalModificationSameResource() {
		useSameFileForDiagramAndModel(true);
		checkDiagramAndModelExternalModification();
	}

	public void testDiagramAndModelExternalModificationSeparateResources() {
		useSameFileForDiagramAndModel(false);
		checkDiagramAndModelExternalModification();
	}

	/*
	 * Here's console output for regular (successful) run of testDiagramAndModelExternalModificationSeparateResources.
	 * The difference in test...SameResource() is in single file notification
	 *  
	 * Important thing to note:
	 * - handleElementChanged is invoked asynchronously from a worker thread, albeit idea of saveResources() 
	 * with subsequent redispatchEvents was to have handleElementChanged (among others, of course) to be already invoked
	 * by the moment we do getDiagram(). Nevertheless, on my Windows machine handleElementChanged() still runs prior to 
	 * getDiagram() call (see below). It is not true for Linux test machine, where we had failures for the long time.
	 * 
		[300887] Done saveResources, all resource notifications should be already dispatched
		[300887] added async handleElementChanged Worker-3
		[300887] added async handleElementChanged Worker-3
		[300887] L/AbstractDiagramEditorTest_1270580558187/AbstractDiagramEditorTest_1270580617468.samplemodel
		[300887] about to be replaced
		[300887] doc content set
		[300887] DiagramEditor.handleElementContentReplaced.1
		[300887] DiagramEditor.handleElementContentReplaced.2
		[300887] content replaced fired
		[300887] L/AbstractDiagramEditorTest_1270580558187/AbstractDiagramEditorTest_1270580617468.samplemodel_diagram
		[300887] about to be replaced
		[300887] doc content set
		[300887] DiagramEditor.handleElementContentReplaced.1
		[300887] DiagramEditor.handleElementContentReplaced.2
		[300887] content replaced fired
		[300887] Test is about to re-get top EP's model:testDiagramAndModelExternalModificationSeparateResources
	 */
	private void checkDiagramAndModelExternalModification() {
		IEditorPart editorPart = getEditor();
		Diagram diagram = getDiagram();
		assertTrue("Not empty diagram created", diagram.getChildren().size() == 0);
		assertTrue("Not empty domain model element created", diagram.getElement().eContents().size() == 0);

		Diagram diagramCopy = reloadInSeparateResourceSet(diagram);
		assertTrue("Passed diagram is not empty", diagramCopy.getChildren().size() == 0);
		try {
			ViewerConfiguration viewerConfiguration = getViewerConfigurationFactory().createViewerConfiguration(new Shell(SWT.NONE), diagramCopy, getSetup());
			Command command = viewerConfiguration.getCreateNodeCommand(diagramCopy, getSetup().getGenModel().getNodeA());
			viewerConfiguration.getViewer().getEditDomain().getCommandStack().execute(command);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertFalse("Diagram node was not created", diagramCopy.getChildren().size() == 0);

		new WorkspaceUtils(getProject()).atomicSave(diagramCopy.eResource().getResourceSet().getResources());

		diagram = getDiagram();
		assertFalse("Editor is dirty", editorPart.isDirty());
		assertTrue("Diagram content was not refreshed", diagram.getChildren().size() > 0);
		assertTrue("Domain model content was not refreshed", diagram.getElement().eContents().size() > 0);
	}

	public void testDiagramResourceExternalModification() {
		Diagram diagram = getDiagram();
		String newDiagramName = getUniqueString();

		Diagram diagramCopy = reloadInSeparateResourceSet(diagram);
		try {
			ViewerConfiguration viewerConfiguration = getViewerConfigurationFactory().createViewerConfiguration(new Shell(SWT.NONE), diagramCopy, getSetup());
			Command command = viewerConfiguration.getSetNotationalElementStructuralFeature(diagramCopy, NotationPackage.eINSTANCE.getDiagram_Name(), newDiagramName);
			viewerConfiguration.getViewer().getEditDomain().getCommandStack().execute(command);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		assertEquals("Diagram name was not set", newDiagramName, diagramCopy.getName());

		new WorkspaceUtils(getProject()).atomicSave(diagramCopy.eResource());

		diagram = getDiagram();
		assertFalse("Editor is dirty", getEditor().isDirty());
		assertEquals("Diagram name was not updated", newDiagramName, diagram.getName());
	}

	public void testModelResorceExternalModification() {
		Diagram diagram = getDiagram();

		Diagram diagramCopy = reloadInSeparateResourceSet(diagram);
		String newName = getUniqueString();
		try {
			ViewerConfiguration viewerConfiguration = getViewerConfigurationFactory().createViewerConfiguration(new Shell(SWT.NONE), diagramCopy, getSetup());
			Command command = viewerConfiguration.getSetBusinessElementStructuralFeatureCommand(diagramCopy, "diagramAttribute", newName);
			viewerConfiguration.getViewer().getEditDomain().getCommandStack().execute(command);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		new WorkspaceUtils(getProject()).atomicSave(diagramCopy.getElement().eResource());

		diagram = getDiagram();
		assertFalse("Editor is dirty", getEditor().isDirty());
		EObject diagramModel = diagram.getElement();
		EStructuralFeature stFeature = diagramModel.eClass().getEStructuralFeature("diagramAttribute");
		assertNotNull("Name feature not found", stFeature);
		String nodeAName = (String) diagramModel.eGet(stFeature);
		assertEquals("Name was not refreshed", newName, nodeAName);
	}

	// utilities for workspace (IResource) operations
	private static class WorkspaceUtils {
		private final IProject myProject;

		WorkspaceUtils(IProject p) {
			assert p != null;
			myProject = p;
		}

		void atomicSave(Resource resource) {
			atomicSave(Collections.singletonList(resource));
		}

		void atomicSave(final List<Resource> resources) {
			// Batching all the notifications from Eclipse resource subsystem.
			// Otherwise notifications will be dispatched on by one and just created
			// diagram node will be removed by CanonicalEditPolicy because
			// corresponding notification from the domain model file changes is
			// waiting to be dispatched later
			IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
	
				public void run(IProgressMonitor monitor) throws CoreException {
					for (Resource nextResource : resources) {
						try {
							nextResource.save(Collections.EMPTY_MAP);
						} catch (IOException e) {
							fail(e.getMessage());
						}
					}
				}
			};
			JobTracker jt = new JobTracker();
			try {
				jt.start();
				ResourcesPlugin.getWorkspace().run(runnable, myProject, IWorkspace.AVOID_UPDATE, new NullProgressMonitor());
				jt.freeze();
				// WorkspaceSynchronizer re-dispatches resource events using Jobs, let these jobs complete
				Utils.assertDispatchDisplayMessages(jt.getNonEmptyCondition(), 3);
				// hence DocumentProvider.ResourceSetInfo's delegate (from Job) invoked handleResourceChanged(), which in turn 
				// posted asyncExec to invoke DocumentProvider.handleElementChanged(), thus need to give it a chance
				Utils.assertDispatchDisplayMessages(3);
			} catch (CoreException e) {
				Assert.fail(e.getMessage());
			} finally {
				jt.stop();
			}
		}
	}

	private Diagram reloadInSeparateResourceSet(Diagram diagram) {
		TransactionalEditingDomain editingDoman = GMFEditingDomainFactory.INSTANCE.createEditingDomain();
		editingDoman.setID(getSetup().getGenModel().getGenDiagram().getEditingDomainID());
		AdapterFactoryEditingDomain oldEditingDomain = (AdapterFactoryEditingDomain) TransactionUtil.getEditingDomain(diagram);
		((AdapterFactoryEditingDomain) editingDoman).setResourceToReadOnlyMap(oldEditingDomain.getResourceToReadOnlyMap());
		ResourceSet resourceSet = editingDoman.getResourceSet();
		Resource newDiagramResource = resourceSet.getResource(diagram.eResource().getURI(), true);
		EObject newDiagram = newDiagramResource.getEObject(diagram.eResource().getURIFragment(diagram));
		assertTrue("Unable to reload the diagram into another ResourceSet", newDiagram instanceof Diagram);
		return (Diagram) newDiagram;
	}

	@Override
	protected Diagram createDiagramView(EObject domainElement, GeneratedDiagramPlugin genPlugin) {
		return RuntimeBasedGeneratorConfiguration.createDiagram(domainElement, genPlugin);
	}
}
