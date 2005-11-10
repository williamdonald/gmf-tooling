package org.eclipse.gmf.codegen.templates.parts;

import java.util.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.gmf.codegen.gmfgen.*;
import org.eclipse.gmf.codegen.util.*;

public class CompartmentEditPartGenerator
{
  protected static String nl;
  public static synchronized CompartmentEditPartGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    CompartmentEditPartGenerator result = new CompartmentEditPartGenerator();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";" + NL;
  protected final String TEXT_3 = NL + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_4 = " extends ListCompartmentEditPart {" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_5 = "(View view) {" + NL + "\t\tsuper(view);" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected boolean hasModelChildrenChanged(Notification evt) {" + NL + "\t\treturn false;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected String getTitleName() {" + NL + "\t\treturn \"";
  protected final String TEXT_6 = "\";" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected void createDefaultEditPolicies() {" + NL + "\t\tsuper.createDefaultEditPolicies();" + NL + "\t\tinstallEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new ";
  protected final String TEXT_7 = "());" + NL + "\t\tinstallEditPolicy(EditPolicyRoles.CREATION_ROLE, new CreationEditPolicy());" + NL + "\t\tinstallEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new DragDropEditPolicy());" + NL + "\t\tinstallEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new ";
  protected final String TEXT_8 = "CanonicalEditPolicy());" + NL + "\t\t//installEditPolicy(EditPolicy.NODE_ROLE, null);" + NL + "\t}" + NL + "\t";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate class ";
  protected final String TEXT_11 = "CanonicalEditPolicy extends ";
  protected final String TEXT_12 = " {" + NL + "" + NL + "\t\t/**" + NL + "\t\t * @generated" + NL + "\t\t */" + NL + "\t\tprotected ";
  protected final String TEXT_13 = " getSemanticChildrenList() {\t";
  protected final String TEXT_14 = "\t\t" + NL + "\t\t\t";
  protected final String TEXT_15 = " modelElement = (";
  protected final String TEXT_16 = ") ((View) getHost().getModel()).getElement();" + NL + "\t\t\tList result = new ";
  protected final String TEXT_17 = "();" + NL;
  protected final String TEXT_18 = NL + "\t\t\tresult.";
  protected final String TEXT_19 = "(((";
  protected final String TEXT_20 = ") modelElement).";
  protected final String TEXT_21 = "());";
  protected final String TEXT_22 = NL + "\t\t\t";
  protected final String TEXT_23 = " featureValues = ((";
  protected final String TEXT_24 = ") modelElement).";
  protected final String TEXT_25 = "();" + NL + "\t\t\tfor (";
  protected final String TEXT_26 = " it = featureValues.iterator(); it.hasNext();) {" + NL + "\t\t\t\t";
  protected final String TEXT_27 = " nextValue = (";
  protected final String TEXT_28 = ") it.next();";
  protected final String TEXT_29 = NL + "\t\t\t";
  protected final String TEXT_30 = " nextValue = (";
  protected final String TEXT_31 = ") ((";
  protected final String TEXT_32 = ") modelElement).";
  protected final String TEXT_33 = "();";
  protected final String TEXT_34 = NL + "\t\t\t";
  protected final String TEXT_35 = " nextEClass = nextValue.eClass();";
  protected final String TEXT_36 = NL + "\t\t\tif (";
  protected final String TEXT_37 = ".eINSTANCE.get";
  protected final String TEXT_38 = "().equals(nextEClass)) {" + NL + "\t\t\t\tresult.add(nextValue);" + NL + "\t\t\t} ";
  protected final String TEXT_39 = NL + "\t\t\t}";
  protected final String TEXT_40 = NL + "\t\t\treturn result;" + NL + "\t\t}" + NL + "\t}";
  protected final String TEXT_41 = NL + "}";
  protected final String TEXT_42 = NL;

  public String generate(Object argument)
  {
    StringBuffer stringBuffer = new StringBuffer();
    
GenCompartment genCompartment = (GenCompartment) argument;
GenDiagram genDiagram = genCompartment.getDiagram();

    stringBuffer.append(TEXT_1);
    stringBuffer.append(genDiagram.getEditPartsPackageName());
    stringBuffer.append(TEXT_2);
    
ImportUtil importManager = new ImportUtil(genDiagram.getEditPartsPackageName());
importManager.markImportLocation(stringBuffer);
importManager.addImport("org.eclipse.emf.common.notify.Notification");
importManager.addImport("org.eclipse.gmf.runtime.diagram.ui.editparts.ListCompartmentEditPart");
importManager.addImport("org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy");
importManager.addImport("org.eclipse.gmf.runtime.diagram.ui.editpolicies.DragDropEditPolicy");
importManager.addImport("org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles");
importManager.addImport("org.eclipse.gmf.runtime.notation.View");

    stringBuffer.append(TEXT_3);
    stringBuffer.append(genCompartment.getEditPartClassName());
    stringBuffer.append(TEXT_4);
    stringBuffer.append(genCompartment.getEditPartClassName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(genCompartment.getTitle());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(importManager.getImportedName(genCompartment.getItemSemanticEditPolicyQualifiedClassName()));
    stringBuffer.append(TEXT_7);
    stringBuffer.append(genCompartment.getEditPartClassName());
    stringBuffer.append(TEXT_8);
    
{
	GenChildContainer childContainer = genCompartment;
	GenNode containerNode = genCompartment.getNode();

    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(childContainer.getEditPartClassName());
    stringBuffer.append(TEXT_11);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy"));
    stringBuffer.append(TEXT_12);
    stringBuffer.append(importManager.getImportedName("java.util.List"));
    stringBuffer.append(TEXT_13);
    
String modelElementInterfaceName = importManager.getImportedName(containerNode.getModelFacet().getMetaClass().getQualifiedInterfaceName());

    stringBuffer.append(TEXT_14);
    stringBuffer.append(modelElementInterfaceName);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(modelElementInterfaceName);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(importManager.getImportedName("java.util.LinkedList"));
    stringBuffer.append(TEXT_17);
    
Map childFeature2NodesMap = new HashMap();
for (Iterator it = childContainer.getChildNodes().iterator(); it.hasNext();) {
	GenChildNode nextChildNode = (GenChildNode) it.next();
	GenFeature genFeature = nextChildNode.getModelFacet().getChildMetaFeature();
	List genChildNodes; 
	if (!childFeature2NodesMap.containsKey(genFeature)) {
		genChildNodes = new ArrayList();
		childFeature2NodesMap.put(genFeature, genChildNodes);
	} else {
		genChildNodes = (List) childFeature2NodesMap.get(genFeature);
	}

	if (nextChildNode.getDomainMetaClass() == null) {
		genChildNodes.clear();
		genChildNodes.add(nextChildNode);
		continue;
	}
	if (genChildNodes.size() == 1 && ((GenChildNode) genChildNodes.get(0)).getDomainMetaClass() == null) {
		continue;
	}
	genChildNodes.add(nextChildNode);
}
		
for (Iterator it = childFeature2NodesMap.entrySet().iterator(); it.hasNext();) {
	Map.Entry nextEntry = (Map.Entry) it.next();
	GenFeature nextFeature = (GenFeature) nextEntry.getKey();
	List childNodes = (List) nextEntry.getValue();
	if (childNodes.size() == 1 && ((GenChildNode) childNodes.get(0)).getDomainMetaClass() == null) {

    stringBuffer.append(TEXT_18);
    stringBuffer.append(nextFeature.isListType() ? "addAll" : "add");
    stringBuffer.append(TEXT_19);
    stringBuffer.append(modelElementInterfaceName);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(nextFeature.getGetAccessor());
    stringBuffer.append(TEXT_21);
    
		continue;
	}
			
	if (nextFeature.isListType()) {

    stringBuffer.append(TEXT_22);
    stringBuffer.append(importManager.getImportedName("java.util.Collection"));
    stringBuffer.append(TEXT_23);
    stringBuffer.append(modelElementInterfaceName);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(nextFeature.getGetAccessor());
    stringBuffer.append(TEXT_25);
    stringBuffer.append(importManager.getImportedName("java.util.Iterator"));
    stringBuffer.append(TEXT_26);
    stringBuffer.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
    stringBuffer.append(TEXT_27);
    stringBuffer.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
    stringBuffer.append(TEXT_28);
    
	} else {

    stringBuffer.append(TEXT_29);
    stringBuffer.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
    stringBuffer.append(TEXT_30);
    stringBuffer.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
    stringBuffer.append(TEXT_31);
    stringBuffer.append(modelElementInterfaceName);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(nextFeature.getGetAccessor());
    stringBuffer.append(TEXT_33);
    
	}

    stringBuffer.append(TEXT_34);
    stringBuffer.append(importManager.getImportedName("org.eclipse.emf.ecore.EClass"));
    stringBuffer.append(TEXT_35);
    			
	for (Iterator childNodesIterator = childNodes.iterator(); childNodesIterator.hasNext();) {
		GenChildNode nextChildNode = (GenChildNode) childNodesIterator.next();
		GenClass domainMetaclass = nextChildNode.getDomainMetaClass();

    stringBuffer.append(TEXT_36);
    stringBuffer.append(importManager.getImportedName(domainMetaclass.getGenPackage().getQualifiedPackageInterfaceName()));
    stringBuffer.append(TEXT_37);
    stringBuffer.append(domainMetaclass.getClassifierAccessorName());
    stringBuffer.append(TEXT_38);
    stringBuffer.append(childNodesIterator.hasNext() ? "else" : "");
    
	}
	if (nextFeature.isListType()) {

    stringBuffer.append(TEXT_39);
    
	}
}

    stringBuffer.append(TEXT_40);
    
}

    stringBuffer.append(TEXT_41);
    importManager.emitSortedImports();
    stringBuffer.append(TEXT_42);
    return stringBuffer.toString();
  }
}
