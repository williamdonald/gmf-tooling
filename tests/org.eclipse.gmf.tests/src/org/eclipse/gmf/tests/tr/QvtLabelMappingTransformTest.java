/*
 * Copyright (c) 2006, 2008 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.tests.tr;

import java.io.IOException;
import java.util.List;

import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.codegen.gmfgen.GenChildLabelNode;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;
import org.eclipse.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.gmf.codegen.gmfgen.GenNode;
import org.eclipse.gmf.codegen.gmfgen.GenTopLevelNode;
import org.eclipse.gmf.mappings.CanvasMapping;
import org.eclipse.gmf.mappings.ChildReference;
import org.eclipse.gmf.mappings.FeatureLabelMapping;
import org.eclipse.gmf.mappings.GMFMapFactory;
import org.eclipse.gmf.mappings.LinkMapping;
import org.eclipse.gmf.mappings.Mapping;
import org.eclipse.gmf.mappings.NodeMapping;
import org.eclipse.gmf.mappings.TopNodeReference;
import org.eclipse.gmf.tests.Utils;
import org.eclipse.gmf.tests.setup.DiaDefSource;
import org.eclipse.gmf.tests.setup.MapDefSource;
import org.eclipse.gmf.tests.setup.MapDefWithReuseSetup;
import org.eclipse.m2m.qvt.oml.BasicModelExtent;
import org.eclipse.m2m.qvt.oml.ExecutionContextImpl;
import org.eclipse.m2m.qvt.oml.ExecutionDiagnostic;
import org.eclipse.m2m.qvt.oml.ModelExtent;
import org.eclipse.m2m.qvt.oml.TransformationExecutor;

/**
 * Check transformation of LabelMappings - to get either GenChildNode with GenNodeLabel or GenChildLabelNode
 * @author artem
 */
public class QvtLabelMappingTransformTest extends AbstractMappingTransformerTest {
	private GenEditorGenerator transformationResult; 

	public QvtLabelMappingTransformTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		transformationResult = executeQVT();
		assertNotNull(transformationResult);
	}
	
	protected GenEditorGenerator executeQVT() throws IOException {
		GenModel genModel = Utils.createGenModel(getCanvasMapping().getDomainModel());
		Mapping mapping = getMapping();
		
		ResourceSet resourceSet = genModel.eResource().getResourceSet();
		
		TransformationExecutor executor = new TransformationExecutor(URI.createURI("platform:/plugin/org.eclipse.gmf.bridge/transforms/Map2Gen.qvto"));
		ExecutionContextImpl context = new ExecutionContextImpl();
		context.setConfigProperty("rcp", false);
		context.setConfigProperty("useMapMode", false);
		context.setConfigProperty("useFullRunTime", false);
		context.setConfigProperty("useInTransformationCodeGen", true);
		
		EList<EObject> mapObjects = new BasicEList<EObject>();
		mapObjects.add(mapping);
		ModelExtent inMap = new BasicModelExtent(mapObjects);
		EList<EObject> genObjects = new BasicEList<EObject>();
		genObjects.add(genModel);
		ModelExtent inGen = new BasicModelExtent(genObjects);
		Resource res = resourceSet.createResource(URI.createURI("platform:/plugin/org.eclipse.gmf.runtime.notation/model/notation.genmodel"));
		res.load(null);
		ModelExtent inNotation = new BasicModelExtent(res.getContents());
		
		ModelExtent output = new BasicModelExtent();
		
		ExecutionDiagnostic result = executor.execute(context, inMap, inGen, inNotation, output);
		if(result.getSeverity() == Diagnostic.OK) {
			
			List<EObject> outObjects = output.getContents();
			assertEquals(1, outObjects.size());
			assertTrue(outObjects.get(0) instanceof GenEditorGenerator);
			
			return (GenEditorGenerator) outObjects.get(0);
		}
		
		return null;
	}

	protected MapDefSource initMapModel(final DiaDefSource graphDef) {
		return new MapDefSource() {
			private Mapping mapping;
			private NodeMapping nodeA;
			private NodeMapping nodeB;
			
			{
				final EPackage domainPack = EcoreFactory.eINSTANCE.createEPackage();
				domainPack.setName("DomainPack");
				final EClass domainA = EcoreFactory.eINSTANCE.createEClass();
				domainA.setName("DomainA");
				final EClass domainB = EcoreFactory.eINSTANCE.createEClass();
				domainB.setName("DomainB");
				domainPack.getEClassifiers().add(domainA);
				domainPack.getEClassifiers().add(domainB);

				EAttribute nameA = EcoreFactory.eINSTANCE.createEAttribute();
				nameA.setName("nameA");
				domainA.getEStructuralFeatures().add(nameA);

				EAttribute nameB = EcoreFactory.eINSTANCE.createEAttribute();
				nameB.setName("nameB");
				domainB.getEStructuralFeatures().add(nameB);

				final EReference aOwnsA = newContainment("aOwnsA", domainA);
				domainA.getEStructuralFeatures().add(aOwnsA);
				final EReference bOwnsB = newContainment("bOwnsB", domainB);
				domainB.getEStructuralFeatures().add(bOwnsB);

				mapping = GMFMapFactory.eINSTANCE.createMapping();
				CanvasMapping cme = GMFMapFactory.eINSTANCE.createCanvasMapping();
				cme.setDiagramCanvas(graphDef.getCanvasDef());
				cme.setDomainModel(domainPack);
				mapping.setDiagram(cme);

				// Node with label and subnodes (with labels as well. actually, reuses itself for subnode)
				nodeA = GMFMapFactory.eINSTANCE.createNodeMapping();
				nodeA.setDiagramNode(graphDef.getNodeDef());
				nodeA.setDomainMetaElement(domainA);
				FeatureLabelMapping lm = GMFMapFactory.eINSTANCE.createFeatureLabelMapping();
				lm.setDiagramLabel(graphDef.getLabelDef());
				lm.getFeatures().add(nameA);
				nodeA.getLabelMappings().add(lm);
				ChildReference childRef = GMFMapFactory.eINSTANCE.createChildReference();
				childRef.setContainmentFeature(aOwnsA);
				childRef.setReferencedChild(nodeA);
				nodeA.getChildren().add(childRef);

				// node with children that are pure labels
				nodeB = GMFMapFactory.eINSTANCE.createNodeMapping();
				nodeB.setDiagramNode(graphDef.getNodeDef());
				nodeB.setDomainMetaElement(domainB);
				
				lm = GMFMapFactory.eINSTANCE.createFeatureLabelMapping();
				lm.setDiagramLabel(graphDef.getLabelDef());
				lm.getFeatures().add(nameB);

				NodeMapping childNodeB = GMFMapFactory.eINSTANCE.createNodeMapping();
				childNodeB.setDiagramNode(lm.getDiagramLabel()); // SIC! we use diagram label for node to 'shortcut' label-only children
				childNodeB.setDomainMetaElement(domainB);
				childNodeB.getLabelMappings().add(lm);
				childRef = GMFMapFactory.eINSTANCE.createChildReference();
				childRef.setContainmentFeature(bOwnsB);
				childRef.setOwnedChild(childNodeB);
				nodeB.getChildren().add(childRef);

				TopNodeReference tnr = GMFMapFactory.eINSTANCE.createTopNodeReference();
				tnr.setOwnedChild(nodeA);
				tnr.setContainmentFeature(aOwnsA);
				mapping.getNodes().add(tnr);

				tnr = GMFMapFactory.eINSTANCE.createTopNodeReference();
				tnr.setOwnedChild(nodeB);
				tnr.setContainmentFeature(bOwnsB);
				mapping.getNodes().add(tnr);
			}

			public LinkMapping getClassLink() {
				return null;
			}

			public Mapping getMapping() {
				return mapping;
			}

			public NodeMapping getNodeA() {
				return nodeA;
			}

			public NodeMapping getNodeB() {
				return nodeB;
			}

			public LinkMapping getReferenceLink() {
				return null;
			}
		};
	}

	/**
	 * there should be GenChildNode with GenNodelLabel
	 */
	public void testNodeAndLabelPairOutcome() {
		GenNode nodeA = getGenNodeA();
		
		assertFalse(nodeA.getLabels().isEmpty());
		GenChildNode childA = nodeA.getChildNodes().get(0);
		assertFalse(childA instanceof GenChildLabelNode);
		assertFalse(childA.getLabels().isEmpty());

	}

	public void testSoleLabelNodeOutcome() {
		GenNode nodeB = getGenNodeB();
		assertTrue(nodeB.getLabels().isEmpty()); // sanity
		GenChildNode childB = nodeB.getChildNodes().get(0);
		assertTrue (childB instanceof GenChildLabelNode);
		assertTrue(childB.getLabels().isEmpty());
	}

	/**
	 * FIXME copy-of {@link MapDefWithReuseSetup#newContainment(String, EClass)}
	 */
	private static EReference newContainment(String name, final EClass domainClass) {
		final EReference ref = EcoreFactory.eINSTANCE.createEReference();
		ref.setName(name);
		ref.setContainment(true);
		ref.setUpperBound(-1);
		ref.setEType(domainClass);
		return ref;
	}

	////////////////
	// FIXME copy-of GenModelTransformerSimpleTest
	//

	private GenNode getGenNodeA() {
		GenNode rv = findTopNode(getNodeDomainElement(0));
		assertNotNull(rv);
		return rv;
	}

	private GenNode getGenNodeB() {
		GenNode rv = findTopNode(getNodeDomainElement(1));
		assertNotNull(rv);
		return rv;
	}

	private EClass getNodeDomainElement(int idx) {
		return (getMapping().getNodes().get(idx)).getOwnedChild().getDomainMetaElement();
	}

	private GenNode findTopNode(EClass eClass) {
		for (GenTopLevelNode next : transformationResult.getDiagram().getTopLevelNodes()) {
			if (next.getDomainMetaClass().getEcoreClass() == eClass) {
				return next;
			}
		}
		return null;
	}
}
