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
package org.eclipse.gmf.tests.setup;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.gmfgraph.Connection;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.Node;
import org.eclipse.gmf.mappings.AuditContainer;
import org.eclipse.gmf.mappings.AuditRule;
import org.eclipse.gmf.mappings.Auditable;
import org.eclipse.gmf.mappings.CanvasMapping;
import org.eclipse.gmf.mappings.Constraint;
import org.eclipse.gmf.mappings.DiagramElementTarget;
import org.eclipse.gmf.mappings.DomainElementTarget;
import org.eclipse.gmf.mappings.GMFMapFactory;
import org.eclipse.gmf.mappings.LabelMapping;
import org.eclipse.gmf.mappings.LinkConstraints;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.Severity;
import org.eclipse.gmf.mappings.TopNodeReference;
import org.eclipse.gmf.tests.Plugin;
import org.eclipse.gmf.tests.setup.DomainModelSource.LinkData;
import org.eclipse.gmf.tests.setup.DomainModelSource.NodeData;

public class MapSetup implements MapDefSource {

	private Mapping myMap;
	private NodeMapping myNodeA;
	private NodeMapping myNodeB;
	private LinkMapping myClassLink;
	private LinkMapping myRefLink;
	
	public MapSetup() {
	}

	/**
	 * @return <code>this</code> for convenience
	 */
	public MapSetup init(DiaDefSource ddSource, DomainModelSource domainSource, ToolDefSource toolDef) {
		initCanvasMappping(domainSource.getModel(), ddSource.getCanvasDef(), domainSource.getDiagramElement());
		if (toolDef.getMainMenu() != null) {
			myMap.getDiagram().getMenuContributions().add(toolDef.getMainMenu());
		}
		myMap.getDiagram().setPalette(toolDef.getPalette());
		
		myNodeA = createNodeMapping(ddSource.getNodeDef(), ddSource.getLabelDef(), domainSource.getNodeA());
		myNodeA.setContextMenu(toolDef.getNodeContextMenu());
		myNodeA.setTool(toolDef.getNodeCreationTool());
		if (domainSource.getNodeB() != null) {
			myNodeB = createNodeMapping(ddSource.getNodeDef(), ddSource.getLabelDef(), domainSource.getNodeB());
		}
		
		myClassLink = createLinkMapping(ddSource.getLinkDef(), domainSource.getLinkAsClass());
		myClassLink.setTool(toolDef.getLinkCreationTool());
		if (domainSource.getLinkAsRef() != null) {
			myRefLink = createLinkMapping(ddSource.getLinkDef(), null, domainSource.getLinkAsRef(), null);
		}
		initAudits();
		return this;
	}

	private void initCanvasMappping(EPackage domainModel, Canvas canvas, EClass diagramElement) {
		Mapping m = GMFMapFactory.eINSTANCE.createMapping();		
			
		CanvasMapping cme = GMFMapFactory.eINSTANCE.createCanvasMapping();
		cme.setDiagramCanvas(canvas);
		cme.setDomainMetaElement(diagramElement);
		cme.setDomainModel(domainModel);
		setupCanvasMapping(cme);
		m.setDiagram(cme);
		myMap = m;
	}
	
	private void initAudits() {
		DomainElementTarget classA = GMFMapFactory.eINSTANCE.createDomainElementTarget();
		classA.setElement(myNodeA.getDomainMetaElement());
		DomainElementTarget classB = GMFMapFactory.eINSTANCE.createDomainElementTarget();
		classB.setElement(myNodeB.getDomainMetaElement());
		AuditContainer root = createAuditContainer(Plugin.getPluginID() + ".category1" + System.currentTimeMillis()); //$NON-NLS-1$
		// create set of allways satisfied constraints
		root.getAudits().add(createAudit("constraint.id1", "true", classA, Severity.ERROR_LITERAL, false)); //$NON-NLS-1$ //$NON-NLS-2$
		root.getAudits().add(createAudit("constraint.id2", "10 > 0", classB, Severity.WARNING_LITERAL, false));	//$NON-NLS-1$ //$NON-NLS-2$
		
		AuditContainer subCat = createAuditContainer("category2"); //$NON-NLS-1$
		DiagramElementTarget nodeTarget = GMFMapFactory.eINSTANCE.createDiagramElementTarget();
		nodeTarget.setElement(myNodeA);
		root.getChildContainers().add(subCat);
		subCat.getAudits().add(createAudit("constraint.id3", "''<>'Foo'", nodeTarget, Severity.INFO_LITERAL, false)); //$NON-NLS-1$ //$NON-NLS-2$
		
		myMap.setAudits(root);
	}
	
	private AuditRule createAudit(String id, String ruleBody, Auditable target, Severity severity, boolean isLiveMode) {
		AuditRule audit = GMFMapFactory.eINSTANCE.createAuditRule();
		audit.setId(id);
		audit.setName("Name of" + id); //$NON-NLS-1$
		audit.setMessage("Violation of " + id); //$NON-NLS-1$
		audit.setDescription("Description of " + id); //$NON-NLS-1$
		audit.setTarget(target);
		Constraint rule = GMFMapFactory.eINSTANCE.createConstraint();
		rule.setBody(ruleBody);
		audit.setRule(rule);
		
		audit.setSeverity(severity);
		audit.setUseInLiveMode(isLiveMode);
		return audit;
	}
	private AuditContainer createAuditContainer(String id) {
		AuditContainer container = GMFMapFactory.eINSTANCE.createAuditContainer();		
		container.setId(id);
		container.setName("Name of " + id); //$NON-NLS-1$
		container.setDescription("Description of " + id); //$NON-NLS-1$
		return container;
	}

	private LinkMapping createLinkMapping(Connection link, LinkData data) {
		return createLinkMapping(link, data.getEClass(), data.getTargetFeature(), data.getContainment());
	}

	private LinkMapping createLinkMapping(Connection link, EClass domainMetaElement, EStructuralFeature linkMetafeature, EReference containmentFeature) {		
		LinkMapping lme = GMFMapFactory.eINSTANCE.createLinkMapping();
		lme.setDiagramLink(link);
		lme.setDomainMetaElement(domainMetaElement);
		lme.setLinkMetaFeature(linkMetafeature);
		lme.setContainmentFeature(containmentFeature);
		// FIXME lme.setTool(GMFMapFactory.eINSTANCE.createCreationTool());
		if (domainMetaElement == null) {
			setupReferenceLinkMapping(lme);
		} else {
			setupClassLinkMapping(lme);
		}
		myMap.getLinks().add(lme);
		return lme;
	}

	private NodeMapping createNodeMapping(Node nodeDef, DiagramLabel labelDef, NodeData nodeData) {
		return createNodeMapping(nodeDef, nodeData.getEClass(), labelDef, nodeData.getNameAttr(), nodeData.getContainment());
	}

	private NodeMapping createNodeMapping(Node nodeDef, EClass domainMetaElement, DiagramLabel labelDef, EAttribute editFeature, EReference containmentFeature) {
		NodeMapping nme = GMFMapFactory.eINSTANCE.createNodeMapping();
		nme.setDiagramNode(nodeDef);
		nme.setDomainMetaElement(domainMetaElement);
		if (editFeature != null) {
			final LabelMapping lm = GMFMapFactory.eINSTANCE.createLabelMapping();
			lm.getFeatures().add(editFeature);
			lm.setDiagramLabel(labelDef);
			nme.getLabelMappings().add(lm);
		}
		// FIXME nme.setTool(GMFMapFactory.eINSTANCE.createCreationTool());
		setupNodeMapping(nme);
		TopNodeReference tnr = GMFMapFactory.eINSTANCE.createTopNodeReference();
		tnr.setOwnedChild(nme);
		tnr.setContainmentFeature(containmentFeature);
		myMap.getNodes().add(tnr);
		return nme;	
	}
	
	protected void addCreationConstraints(LinkMapping linkMapping, String sourceConstraint, String endConstraint) {
		LinkConstraints constraints = GMFMapFactory.eINSTANCE.createLinkConstraints();
		Constraint source = GMFMapFactory.eINSTANCE.createConstraint();
		source.setBody(sourceConstraint);
		constraints.setSourceEnd(source);
		
		Constraint target = GMFMapFactory.eINSTANCE.createConstraint();
		target.setBody(endConstraint);
		constraints.setTargetEnd(target);
		
		linkMapping.setCreationConstraints(constraints);
	}		

	/**
	 * Allows for extra initialization code. Does nothing by default
	 */
	protected void setupCanvasMapping(CanvasMapping cme) {
	}

	/**
	 * Allows for extra initialization code. Does nothing by default
	 */
	protected void setupNodeMapping(NodeMapping nme) {
	}

	/**
	 * Allows for extra initialization code. Does nothing by default
	 */
	protected void setupClassLinkMapping(LinkMapping lme) {
	}

	/**
	 * Allows for extra initialization code. Does nothing by default
	 */
	protected void setupReferenceLinkMapping(LinkMapping lme) {
	}

	public final Mapping getMapping() {
		return myMap;
	}

	public final CanvasMapping getCanvas() {
		return getMapping().getDiagram();
	}

	public NodeMapping getNodeA() {
		return myNodeA;
	}

	public NodeMapping getNodeB() {
		return myNodeB;
	}

	public LinkMapping getClassLink() {
		return myClassLink;
	}

	public LinkMapping getReferenceLink() {
		return myRefLink;
	}
}
