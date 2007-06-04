package org.eclipse.gmf.codegen.templates.navigator;

import org.eclipse.gmf.codegen.gmfgen.*;
import org.eclipse.gmf.common.codegen.*;
import java.util.*;
import org.eclipse.emf.codegen.ecore.genmodel.*;
import org.eclipse.emf.ecore.*;

public class NavigatorLabelProviderGenerator {
 
  protected static String nl;
  public static synchronized NavigatorLabelProviderGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    NavigatorLabelProviderGenerator result = new NavigatorLabelProviderGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * ";
  protected final String TEXT_2 = NL + " */";
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_5 = " extends ";
  protected final String TEXT_6 = " implements ";
  protected final String TEXT_7 = ", ";
  protected final String TEXT_8 = " {" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tstatic {" + NL + "\t\t";
  protected final String TEXT_9 = ".getInstance().getImageRegistry().put(\"";
  protected final String TEXT_10 = "\", ";
  protected final String TEXT_11 = ".getMissingImageDescriptor());" + NL + "\t\t";
  protected final String TEXT_12 = ".getInstance().getImageRegistry().put(\"";
  protected final String TEXT_13 = "\", ";
  protected final String TEXT_14 = ".getMissingImageDescriptor());" + NL + "\t\t";
  protected final String TEXT_15 = ".getInstance().getImageRegistry().put(\"";
  protected final String TEXT_16 = "\", ";
  protected final String TEXT_17 = ".getMissingImageDescriptor());" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void updateLabel(";
  protected final String TEXT_18 = " label, ";
  protected final String TEXT_19 = " elementPath) {" + NL + "\t\tObject element = elementPath.getLastSegment();" + NL + "\t\tif (element instanceof ";
  protected final String TEXT_20 = " && !isOwnView(((";
  protected final String TEXT_21 = ") element).getView())) {" + NL + "\t\t\treturn;" + NL + "\t\t}" + NL + "\t\tlabel.setText(getText(element));" + NL + "\t\tlabel.setImage(getImage(element));" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_22 = " getImage(Object element) {" + NL + "\t\tif (element instanceof ";
  protected final String TEXT_23 = ") {" + NL + "\t\t\t";
  protected final String TEXT_24 = " group = (";
  protected final String TEXT_25 = ") element;" + NL + "\t\t\treturn ";
  protected final String TEXT_26 = ".getInstance().getBundledImage(group.getIcon());" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tif (element instanceof ";
  protected final String TEXT_27 = ") {" + NL + "\t\t\t";
  protected final String TEXT_28 = " navigatorItem = (";
  protected final String TEXT_29 = ") element;" + NL + "    \t\tif (!isOwnView(navigatorItem.getView())) {" + NL + "    \t\t\treturn super.getImage(element);" + NL + "    \t\t}" + NL + "    \t\treturn getImage(navigatorItem.getView());" + NL + "\t\t}" + NL;
  protected final String TEXT_30 = NL + "\t\t// Due to plugin.xml content will be called only for \"own\" views" + NL + "\t\tif (element instanceof ";
  protected final String TEXT_31 = ") {" + NL + "\t\t\t";
  protected final String TEXT_32 = " view = (";
  protected final String TEXT_33 = ") ((";
  protected final String TEXT_34 = ") element).getAdapter(";
  protected final String TEXT_35 = ".class);" + NL + "\t\t\tif (view != null && isOwnView(view)) {" + NL + "\t\t\t\treturn getImage(view);" + NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_36 = NL + "\t\treturn super.getImage(element);" + NL + "\t}" + NL + "\t\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_37 = " getImage(";
  protected final String TEXT_38 = " view) {" + NL + "\t\tswitch (";
  protected final String TEXT_39 = ".getVisualID(view)) {";
  protected final String TEXT_40 = NL + "\t\tcase ";
  protected final String TEXT_41 = ".VISUAL_ID:" + NL + "\t\t\treturn getImage(\"";
  protected final String TEXT_42 = "\", ";
  protected final String TEXT_43 = ".";
  protected final String TEXT_44 = "null";
  protected final String TEXT_45 = ");";
  protected final String TEXT_46 = NL + "\t\tdefault:" + NL + "\t\t\treturn getImage(\"";
  protected final String TEXT_47 = "\", null);" + NL + "\t\t}" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate ";
  protected final String TEXT_48 = " getImage(String key, ";
  protected final String TEXT_49 = " elementType) {" + NL + "\t\t";
  protected final String TEXT_50 = " imageRegistry = ";
  protected final String TEXT_51 = ".getInstance().getImageRegistry();" + NL + "\t\t";
  protected final String TEXT_52 = " image = imageRegistry.get(key);" + NL + "\t\tif (image == null && elementType != null && ";
  protected final String TEXT_53 = ".isKnownElementType(elementType)) {" + NL + "\t\t\timage = ";
  protected final String TEXT_54 = ".getImage(elementType);" + NL + "\t\t\timageRegistry.put(key, image);" + NL + "\t\t}" + NL + "\t\t\t" + NL + "\t\tif (image == null) {" + NL + "\t\t\timage = imageRegistry.get(\"";
  protected final String TEXT_55 = "\");" + NL + "\t\t\timageRegistry.put(key, image);" + NL + "\t\t}" + NL + "\t\treturn image;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic String getText(Object element) {" + NL + "\t\tif (element instanceof ";
  protected final String TEXT_56 = ") {" + NL + "\t\t\t";
  protected final String TEXT_57 = " group = (";
  protected final String TEXT_58 = ") element;" + NL + "\t\t\treturn group.getGroupName();" + NL + "\t\t}" + NL + "\t\t" + NL + "\t\tif (element instanceof ";
  protected final String TEXT_59 = ") {" + NL + "\t\t\t";
  protected final String TEXT_60 = " navigatorItem = (";
  protected final String TEXT_61 = ") element;" + NL + "    \t\tif (!isOwnView(navigatorItem.getView())) {" + NL + "    \t\t\treturn null;" + NL + "    \t\t}" + NL + "    \t\treturn getText(navigatorItem.getView());" + NL + "\t\t}" + NL + "\t";
  protected final String TEXT_62 = NL + "\t\t// Due to plugin.xml content will be called only for \"own\" views" + NL + "\t\tif (element instanceof ";
  protected final String TEXT_63 = ") {" + NL + "\t\t\t";
  protected final String TEXT_64 = " view = (";
  protected final String TEXT_65 = ") ((";
  protected final String TEXT_66 = ") element).getAdapter(";
  protected final String TEXT_67 = ".class);" + NL + "\t\t\tif (view != null && isOwnView(view)) {" + NL + "\t\t\t\treturn getText(view);" + NL + "\t\t\t}" + NL + "\t\t}";
  protected final String TEXT_68 = NL + "\t\treturn super.getText(element);" + NL + "\t}" + NL + "\t\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic String getText(";
  protected final String TEXT_69 = " view) {" + NL + "\t\tif (view.getElement() != null && view.getElement().eIsProxy()) {" + NL + "\t\t\treturn getUnresolvedDomainElementProxyText(view);" + NL + "\t\t}" + NL + "\t\tswitch (";
  protected final String TEXT_70 = ".getVisualID(view)) {";
  protected final String TEXT_71 = NL + "\t\tcase ";
  protected final String TEXT_72 = ".VISUAL_ID:" + NL + "\t\t\treturn get";
  protected final String TEXT_73 = "Text(view);";
  protected final String TEXT_74 = NL + "\t\tdefault:" + NL + "\t\t\treturn getUnknownElementText(view);" + NL + "\t\t}" + NL + "\t}" + NL;
  protected final String TEXT_75 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate String get";
  protected final String TEXT_76 = "Text(";
  protected final String TEXT_77 = " view) {";
  protected final String TEXT_78 = NL + "\t\t";
  protected final String TEXT_79 = " hintAdapter = new ";
  protected final String TEXT_80 = ".HintAdapter(" + NL + "\t\t\t\t";
  protected final String TEXT_81 = ".";
  protected final String TEXT_82 = "," + NL + "\t\t\t\t(view.getElement() != null ? view.getElement() : view),";
  protected final String TEXT_83 = NL + "\t\t\t\t";
  protected final String TEXT_84 = ".DESCRIPTION";
  protected final String TEXT_85 = NL + "\t\t\t\t";
  protected final String TEXT_86 = ".getType(";
  protected final String TEXT_87 = ".VISUAL_ID)";
  protected final String TEXT_88 = ");" + NL + "\t\t";
  protected final String TEXT_89 = " parser = ";
  protected final String TEXT_90 = ".getInstance().getParser(hintAdapter);" + NL + "\t\t" + NL + "\t\tif (parser != null) {" + NL + "\t\t\treturn parser.getPrintString(hintAdapter, ";
  protected final String TEXT_91 = ".NONE.intValue());" + NL + "\t\t} else {" + NL + "\t\t\t";
  protected final String TEXT_92 = ".getInstance().logError(\"Parser was not found for label \" + ";
  protected final String TEXT_93 = ");" + NL + "\t\t\treturn \"\";" + NL + "\t\t}";
  protected final String TEXT_94 = NL + "\t\treturn \"";
  protected final String TEXT_95 = "\";";
  protected final String TEXT_96 = NL + "\t\t";
  protected final String TEXT_97 = " domainModelElement = view.getElement();" + NL + "\t\tif (domainModelElement != null) {" + NL + "\t\t\treturn ";
  protected final String TEXT_98 = "String.valueOf(";
  protected final String TEXT_99 = ")";
  protected final String TEXT_100 = ";" + NL + "\t\t} else {" + NL + "\t\t\t";
  protected final String TEXT_101 = ".getInstance().logError(\"No domain element for view with visualID = \" + ";
  protected final String TEXT_102 = ");" + NL + "\t\t\treturn \"\";" + NL + "\t\t}";
  protected final String TEXT_103 = NL + "\t\treturn \"\";";
  protected final String TEXT_104 = NL + "\t}";
  protected final String TEXT_105 = NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate String getUnknownElementText(";
  protected final String TEXT_106 = " view) {" + NL + "\t\treturn \"<UnknownElement Visual_ID = \" + view.getType() + \">\";" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate String getUnresolvedDomainElementProxyText(";
  protected final String TEXT_107 = " view) {" + NL + "\t\treturn \"<Unresolved domain element Visual_ID = \" + view.getType() + \">\";" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void init(";
  protected final String TEXT_108 = " aConfig) {" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void restoreState(";
  protected final String TEXT_109 = " aMemento) {" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void saveState(";
  protected final String TEXT_110 = " aMemento) {" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic String getDescription(Object anElement) {" + NL + "\t\treturn null;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate boolean isOwnView(";
  protected final String TEXT_111 = " view) {" + NL + "\t\treturn ";
  protected final String TEXT_112 = ".MODEL_ID.equals(";
  protected final String TEXT_113 = ".getModelID(view));" + NL + "\t}" + NL + "" + NL + "}";
  protected final String TEXT_114 = NL;

	protected final String getFeatureValueGetter(String containerName, GenFeature feature, boolean isContainerEObject, ImportAssistant importManager) {
		StringBuffer result = new StringBuffer();
		if (feature.getGenClass().isExternalInterface()) {
// Using EMF reflective method to access feature value
			result.append("((");
			if (feature.isListType()) {
				result.append(importManager.getImportedName("java.util.Collection"));
			} else {
				if (feature.getTypeGenClass() != null) {
					result.append(importManager.getImportedName(feature.getTypeGenClass().getQualifiedInterfaceName()));
				} else {
					// EDataType
					result.append(importManager.getImportedName(feature.getTypeGenClassifier().getEcoreClassifier().getInstanceClassName()));
				}

			}
			result.append(")");
			if (!isContainerEObject) {
// Casting container to EObject - ExternalIntarfce could be not an instance of EObject
				result.append("((");
				result.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
				result.append(")");
			}
			result.append(containerName);
			if (!isContainerEObject) {
				result.append(")");
			}
			result.append(".eGet(");
			result.append(importManager.getImportedName(feature.getGenPackage().getQualifiedPackageInterfaceName()));
			result.append(".eINSTANCE.get");
			result.append(feature.getFeatureAccessorName());
			result.append("()))");
		} else {
			if (isContainerEObject) {
// Casting container to the typed interface
				result.append("((");
				result.append(importManager.getImportedName(feature.getGenClass().getQualifiedInterfaceName()));
				result.append(")");
			}
			result.append(containerName);
			if (isContainerEObject) {
				result.append(")");
			}
			result.append(".");
			result.append(feature.getGetAccessor());
			result.append("()");
		}
		return result.toString();
	}
	
	protected final String getFeatureValueSetterPrefix(String containerName, GenFeature feature, boolean isContainerEObject, ImportAssistant importManager) {
		StringBuffer result = new StringBuffer();
		if (feature.getGenClass().isExternalInterface()) {
// Using EMF reflective method to access feature value
			if (!isContainerEObject) {
// Casting container to EObject - ExternalIntarfce could be not an instance of EObject
				result.append("((");
				result.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
				result.append(")");
			}
			result.append(containerName);
			if (!isContainerEObject) {
				result.append(")");
			}
			result.append(".eSet(");
			result.append(importManager.getImportedName(feature.getGenPackage().getQualifiedPackageInterfaceName()));
			result.append(".eINSTANCE.get");
			result.append(feature.getFeatureAccessorName());
			result.append("(), ");
		} else {
			if (isContainerEObject) {
// Casting container to the typed interface
				result.append("((");
				result.append(importManager.getImportedName(feature.getGenClass().getQualifiedInterfaceName()));
				result.append(")");
			}
			result.append(containerName);
			if (isContainerEObject) {
				result.append(")");
			}
			if (feature.isListType()) {
				result.append(".");
				result.append(feature.getGetAccessor());
				result.append("().add(");
			} else {
				result.append(".set");
				result.append(feature.getAccessorName());
				result.append("(");
			}
		}
		return result.toString();
	}
 
	protected final String getMetaClassAccessor(GenClass genClass, ImportAssistant importManager) {
		StringBuffer buf = new StringBuffer();
		buf.append(importManager.getImportedName(genClass.getGenPackage().getQualifiedPackageInterfaceName()))
			.append(".eINSTANCE.get") //$NON-NLS-1$
			.append(genClass.getName())
			.append("()"); //$NON-NLS-1$
		return buf.toString();
	} 
 
	public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
GenNavigator genNavigator = (GenNavigator) ((Object[]) argument)[0];
GenDiagram genDiagram = genNavigator.getEditorGen().getDiagram();
Collection commonBaseElements = new ArrayList(genDiagram.getAllContainers());
commonBaseElements.addAll(genDiagram.getLinks());
final ImportAssistant importManager = (ImportAssistant) ((Object[]) argument)[1];

final String commonKeyPrefix = "Navigator?";
final String invalidElementKey = commonKeyPrefix + "InvalidElement";
final String unknownElementKey = commonKeyPrefix + "UnknownElement";
final String notFoundElementKey = commonKeyPrefix + "ImageNotFound";
/* TODO: move this logic into genmodel */
final class RegistryKey {
	
	private GenCommonBase myCommonBase;
	private String myKey;
	
	public RegistryKey(GenCommonBase commonBase) {
		myCommonBase = commonBase;
	}
	
	public String getKey() {
		if (myKey == null) {
			if (myCommonBase instanceof GenDiagram) {
				myKey = getKey((GenDiagram) myCommonBase);
			} else if (myCommonBase instanceof GenNode) {
				myKey = getKey((GenNode) myCommonBase);
			} else if (myCommonBase instanceof GenLink) {
				myKey = getKey((GenLink) myCommonBase);
			} else if (myCommonBase instanceof GenCompartment) {
				myKey = getKey((GenCompartment) myCommonBase);
			} else if (myCommonBase instanceof GenLabel) {
				myKey = getKey((GenLabel) myCommonBase);
			} else {
				myKey = getInvalidElementKey();
			}
		}
		return myKey;
	}
	
	private String getKey(GenDiagram diagram) {
		return getCommonPrefix() + "Diagram?" + getKeyFragment(diagram.getDomainDiagramElement());
	}
	
	private String getKey(GenNode node) {
		return getCommonPrefix() + (node instanceof GenTopLevelNode ? "TopLevelNode?" : "Node?") + (node.getModelFacet() != null ? getKeyFragment(node.getModelFacet().getMetaClass()) : getKeyFragment(node.getViewmap()));
	}
	
	private String getKey(GenLink link) {
		LinkModelFacet modelFacet = link.getModelFacet();
		if (modelFacet instanceof TypeLinkModelFacet) {
			return getCommonPrefix() + "Link?" + getKeyFragment(((TypeLinkModelFacet) modelFacet).getMetaClass());
		} else if (modelFacet instanceof FeatureLinkModelFacet) {
			return getCommonPrefix() + "Link?" + getKeyFragment(((FeatureLinkModelFacet) modelFacet).getMetaFeature());
		}
		return getCommonPrefix() + "Link?" + getKeyFragment(link.getViewmap());
	}
	
	private String getKey(GenCompartment compartment) {
		return getKey(compartment.getNode()) + "?Compartment?" + compartment.getTitle();
	}
	
	private String getKey(GenLabel label) {
		if (label instanceof GenNodeLabel) {
			GenNodeLabel nodeLabel = (GenNodeLabel) label;
			return getKey(nodeLabel.getNode()) + "?Label?" + getKeyFragment(nodeLabel);
		} else if (label instanceof GenLinkLabel) {
			GenLinkLabel linkLabel = (GenLinkLabel) label;
			return getKey(linkLabel.getLink()) + "?Label?" + getKeyFragment(linkLabel);
		}
		return getInvalidElementKey();
	}
	
	private String getInvalidElementKey() {
		return invalidElementKey;
	}
	
	private String getCommonPrefix() {
		return commonKeyPrefix;
	}
	
	private String getKeyFragment(GenClass genClass) {
		if (genClass == null) {
			return "";
		}
		return genClass.getGenPackage().getNSURI() + "?" + genClass.getName();
	}
	
	private String getKeyFragment(GenFeature genFeature) {
		GenClass genClass = genFeature.getGenClass();
		return getKeyFragment(genClass) + "?" + genFeature.getName();
	}
	
	private String getKeyFragment(GenLabel genLabel) {
		LabelModelFacet modelFacet = genLabel.getModelFacet();
		if (modelFacet instanceof FeatureLabelModelFacet) {
			String result = "";
			for (Iterator it = ((FeatureLabelModelFacet) genLabel).getMetaFeatures().iterator(); it.hasNext();) {
				result += getKeyFragment((GenFeature) it.next());
			}
			return result;
		} else if (modelFacet instanceof DesignLabelModelFacet) {
			return getKeyFragment(genLabel.getViewmap());
		}
		return getInvalidElementKey();
	}
	
	private String getKeyFragment(Viewmap viewmap) {
		if (viewmap instanceof FigureViewmap) {
			return ((FigureViewmap) viewmap).getFigureQualifiedClassName();
		} else if (viewmap instanceof InnerClassViewmap) {
			return ((InnerClassViewmap) viewmap).getClassName();
		} else if (viewmap instanceof ParentAssignedViewmap) {
			return ((ParentAssignedViewmap) viewmap).getFigureQualifiedClassName();
		}
		return "";
	}

}


    
String copyrightText = genDiagram.getEditorGen().getCopyrightText();
if (copyrightText != null && copyrightText.trim().length() > 0) {

    stringBuffer.append(TEXT_1);
    stringBuffer.append(copyrightText.replaceAll("\n", "\n * "));
    stringBuffer.append(TEXT_2);
    }
    importManager.emitPackageStatement(stringBuffer);
    stringBuffer.append(TEXT_3);
    importManager.markImportLocation(stringBuffer);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(genNavigator.getLabelProviderClassName());
    stringBuffer.append(TEXT_5);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.viewers.LabelProvider"));
    stringBuffer.append(TEXT_6);
    stringBuffer.append(importManager.getImportedName("org.eclipse.ui.navigator.ICommonLabelProvider"));
    stringBuffer.append(TEXT_7);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.viewers.ITreePathLabelProvider"));
    stringBuffer.append(TEXT_8);
    stringBuffer.append(importManager.getImportedName(genNavigator.getEditorGen().getPlugin().getActivatorQualifiedClassName()));
    stringBuffer.append(TEXT_9);
    stringBuffer.append(invalidElementKey);
    stringBuffer.append(TEXT_10);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.resource.ImageDescriptor"));
    stringBuffer.append(TEXT_11);
    stringBuffer.append(importManager.getImportedName(genNavigator.getEditorGen().getPlugin().getActivatorQualifiedClassName()));
    stringBuffer.append(TEXT_12);
    stringBuffer.append(unknownElementKey);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.resource.ImageDescriptor"));
    stringBuffer.append(TEXT_14);
    stringBuffer.append(importManager.getImportedName(genNavigator.getEditorGen().getPlugin().getActivatorQualifiedClassName()));
    stringBuffer.append(TEXT_15);
    stringBuffer.append(notFoundElementKey);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.resource.ImageDescriptor"));
    stringBuffer.append(TEXT_17);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.viewers.ViewerLabel"));
    stringBuffer.append(TEXT_18);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.viewers.TreePath"));
    stringBuffer.append(TEXT_19);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_20);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_21);
    stringBuffer.append(importManager.getImportedName("org.eclipse.swt.graphics.Image"));
    stringBuffer.append(TEXT_22);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorGroupQualifiedClassName()));
    stringBuffer.append(TEXT_23);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorGroupQualifiedClassName()));
    stringBuffer.append(TEXT_24);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorGroupQualifiedClassName()));
    stringBuffer.append(TEXT_25);
    stringBuffer.append(importManager.getImportedName(genNavigator.getEditorGen().getPlugin().getActivatorQualifiedClassName()));
    stringBuffer.append(TEXT_26);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_27);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_28);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_29);
    if (genDiagram.generateShortcutIcon()) {
    stringBuffer.append(TEXT_30);
    stringBuffer.append(importManager.getImportedName("org.eclipse.core.runtime.IAdaptable"));
    stringBuffer.append(TEXT_31);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_32);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_33);
    stringBuffer.append(importManager.getImportedName("org.eclipse.core.runtime.IAdaptable"));
    stringBuffer.append(TEXT_34);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_35);
    
}
    stringBuffer.append(TEXT_36);
    stringBuffer.append(importManager.getImportedName("org.eclipse.swt.graphics.Image"));
    stringBuffer.append(TEXT_37);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_38);
    stringBuffer.append(importManager.getImportedName(genDiagram.getVisualIDRegistryQualifiedClassName()));
    stringBuffer.append(TEXT_39);
    for (Iterator it = commonBaseElements.iterator(); it.hasNext();) {
	GenCommonBase nextCommonBase = (GenCommonBase) it.next();
	if (genNavigator.getChildReferencesTo(nextCommonBase).size() > 0) {
		RegistryKey registryKey = new RegistryKey(nextCommonBase);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(importManager.getImportedName(nextCommonBase.getEditPartQualifiedClassName()));
    stringBuffer.append(TEXT_41);
    stringBuffer.append(registryKey.getKey());
    stringBuffer.append(TEXT_42);
    if (nextCommonBase.getElementType() != null) {
    stringBuffer.append(importManager.getImportedName(genDiagram.getElementTypesQualifiedClassName()));
    stringBuffer.append(TEXT_43);
    stringBuffer.append(nextCommonBase.getUniqueIdentifier());
    } else {
    stringBuffer.append(TEXT_44);
    }
    stringBuffer.append(TEXT_45);
    	}
}
    stringBuffer.append(TEXT_46);
    stringBuffer.append(unknownElementKey);
    stringBuffer.append(TEXT_47);
    stringBuffer.append(importManager.getImportedName("org.eclipse.swt.graphics.Image"));
    stringBuffer.append(TEXT_48);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.emf.type.core.IElementType"));
    stringBuffer.append(TEXT_49);
    stringBuffer.append(importManager.getImportedName("org.eclipse.jface.resource.ImageRegistry"));
    stringBuffer.append(TEXT_50);
    stringBuffer.append(importManager.getImportedName(genNavigator.getEditorGen().getPlugin().getActivatorQualifiedClassName()));
    stringBuffer.append(TEXT_51);
    stringBuffer.append(importManager.getImportedName("org.eclipse.swt.graphics.Image"));
    stringBuffer.append(TEXT_52);
    stringBuffer.append(importManager.getImportedName(genDiagram.getElementTypesQualifiedClassName()));
    stringBuffer.append(TEXT_53);
    stringBuffer.append(importManager.getImportedName(genDiagram.getElementTypesQualifiedClassName()));
    stringBuffer.append(TEXT_54);
    stringBuffer.append(notFoundElementKey);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorGroupQualifiedClassName()));
    stringBuffer.append(TEXT_56);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorGroupQualifiedClassName()));
    stringBuffer.append(TEXT_57);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorGroupQualifiedClassName()));
    stringBuffer.append(TEXT_58);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_59);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_60);
    stringBuffer.append(importManager.getImportedName(genNavigator.getNavigatorItemQualifiedClassName()));
    stringBuffer.append(TEXT_61);
    if (genDiagram.generateShortcutIcon()) {
    stringBuffer.append(TEXT_62);
    stringBuffer.append(importManager.getImportedName("org.eclipse.core.runtime.IAdaptable"));
    stringBuffer.append(TEXT_63);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_64);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_65);
    stringBuffer.append(importManager.getImportedName("org.eclipse.core.runtime.IAdaptable"));
    stringBuffer.append(TEXT_66);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_67);
    
}
    stringBuffer.append(TEXT_68);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_69);
    stringBuffer.append(importManager.getImportedName(genDiagram.getVisualIDRegistryQualifiedClassName()));
    stringBuffer.append(TEXT_70);
    for (Iterator it = commonBaseElements.iterator(); it.hasNext();) {
	GenCommonBase nextCommonBase = (GenCommonBase) it.next();
	if (genNavigator.getChildReferencesTo(nextCommonBase).size() > 0) {
    stringBuffer.append(TEXT_71);
    stringBuffer.append(importManager.getImportedName(nextCommonBase.getEditPartQualifiedClassName()));
    stringBuffer.append(TEXT_72);
    stringBuffer.append(nextCommonBase.getUniqueIdentifier());
    stringBuffer.append(TEXT_73);
    	}
}
    stringBuffer.append(TEXT_74);
    for (Iterator it = commonBaseElements.iterator(); it.hasNext();) {
	GenCommonBase nextCommonBase = (GenCommonBase) it.next();
	if (genNavigator.getChildReferencesTo(nextCommonBase).size() > 0) {
    stringBuffer.append(TEXT_75);
    stringBuffer.append(nextCommonBase.getUniqueIdentifier());
    stringBuffer.append(TEXT_76);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_77);
    		LabelModelFacet labelModelFacet = null;
		GenCommonBase hintHolder = null;
		GenCommonBase elementTypeHolder = null;
		if (nextCommonBase instanceof GenChildLabelNode) {
			labelModelFacet = ((GenChildLabelNode) nextCommonBase).getLabelModelFacet();
			hintHolder = nextCommonBase;
			elementTypeHolder = nextCommonBase;
		} else if (nextCommonBase instanceof GenNode) {
			Collection labels = ((GenNode) nextCommonBase).getLabels();
			if (labels.size() > 0) {
				GenLabel nextLabel = (GenLabel) labels.iterator().next();
				labelModelFacet = nextLabel.getModelFacet();
				hintHolder = nextLabel;
				elementTypeHolder = nextCommonBase;
			}
		} else if (nextCommonBase instanceof GenLink) {
			Collection labels = ((GenLink) nextCommonBase).getLabels();
			if (labels.size() > 0) {
				GenLabel nextLabel = (GenLabel) labels.iterator().next();
				labelModelFacet = nextLabel.getModelFacet();
				hintHolder = nextLabel;
				elementTypeHolder = nextCommonBase;
			}
		} else if (nextCommonBase instanceof GenLabel) {
			labelModelFacet = ((GenLabel) nextCommonBase).getModelFacet();
			hintHolder = nextCommonBase;
			elementTypeHolder = (GenCommonBase) nextCommonBase.eContainer();
		}
		
		if (elementTypeHolder != null) {
/* 1. Label was detected. */
    stringBuffer.append(TEXT_78);
    stringBuffer.append(importManager.getImportedName("org.eclipse.core.runtime.IAdaptable"));
    stringBuffer.append(TEXT_79);
    stringBuffer.append(importManager.getImportedName(genDiagram.getParserProviderQualifiedClassName()));
    stringBuffer.append(TEXT_80);
    stringBuffer.append(importManager.getImportedName(genDiagram.getElementTypesQualifiedClassName()));
    stringBuffer.append(TEXT_81);
    stringBuffer.append(elementTypeHolder.getUniqueIdentifier());
    stringBuffer.append(TEXT_82);
    if (labelModelFacet instanceof DesignLabelModelFacet) {
    stringBuffer.append(TEXT_83);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.common.ui.services.parser.CommonParserHint"));
    stringBuffer.append(TEXT_84);
    } else {
    stringBuffer.append(TEXT_85);
    stringBuffer.append(importManager.getImportedName(genDiagram.getVisualIDRegistryQualifiedClassName()));
    stringBuffer.append(TEXT_86);
    stringBuffer.append(importManager.getImportedName(hintHolder.getEditPartQualifiedClassName()));
    stringBuffer.append(TEXT_87);
    }
    stringBuffer.append(TEXT_88);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.common.ui.services.parser.IParser"));
    stringBuffer.append(TEXT_89);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.common.ui.services.parser.ParserService"));
    stringBuffer.append(TEXT_90);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.common.ui.services.parser.ParserOptions"));
    stringBuffer.append(TEXT_91);
    stringBuffer.append(importManager.getImportedName(genDiagram.getEditorGen().getPlugin().getActivatorQualifiedClassName()));
    stringBuffer.append(TEXT_92);
    stringBuffer.append(String.valueOf(hintHolder.getVisualID()));
    stringBuffer.append(TEXT_93);
    		} else if (nextCommonBase instanceof GenCompartment) {
/* 2. Compartment was detected. */
    stringBuffer.append(TEXT_94);
    stringBuffer.append(((GenCompartment) nextCommonBase).getTitle());
    stringBuffer.append(TEXT_95);
    		} else {
			GenClass domainElement = null;
			if (nextCommonBase instanceof GenDiagram) {
				domainElement = ((GenDiagram) nextCommonBase).getDomainDiagramElement();
			} else if (nextCommonBase instanceof GenNode) {
				GenNode genNode = (GenNode) nextCommonBase;
				if (genNode.getModelFacet() != null) {
					domainElement = genNode.getModelFacet().getMetaClass();
				}
			} else if (nextCommonBase instanceof GenLink) {
				LinkModelFacet modelFacet = ((GenLink) nextCommonBase).getModelFacet();
				if (modelFacet instanceof TypeModelFacet) {
					domainElement = ((TypeModelFacet) modelFacet).getMetaClass();
				}
			}
			if (domainElement != null && domainElement.getLabelFeature() != null) {
/* 3. Underlying doman model element recognized. */
				GenFeature labelFeature = domainElement.getLabelFeature();
				boolean notString = EcorePackage.eINSTANCE.getEString() != labelFeature.getEcoreFeature().getEType();
    stringBuffer.append(TEXT_96);
    stringBuffer.append(importManager.getImportedName("org.eclipse.emf.ecore.EObject"));
    stringBuffer.append(TEXT_97);
    if (notString) {
    stringBuffer.append(TEXT_98);
    }
    stringBuffer.append(getFeatureValueGetter("domainModelElement", domainElement.getLabelFeature(), true, importManager));
    if (notString) {
    stringBuffer.append(TEXT_99);
    }
    stringBuffer.append(TEXT_100);
    stringBuffer.append(importManager.getImportedName(genDiagram.getEditorGen().getPlugin().getActivatorQualifiedClassName()));
    stringBuffer.append(TEXT_101);
    stringBuffer.append(String.valueOf(nextCommonBase.getVisualID()));
    stringBuffer.append(TEXT_102);
    			} else {
/* 4. None of above. */
    stringBuffer.append(TEXT_103);
    			}
		}
    stringBuffer.append(TEXT_104);
    	}
}
    stringBuffer.append(TEXT_105);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_106);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_107);
    stringBuffer.append(importManager.getImportedName("org.eclipse.ui.navigator.ICommonContentExtensionSite"));
    stringBuffer.append(TEXT_108);
    stringBuffer.append(importManager.getImportedName("org.eclipse.ui.IMemento"));
    stringBuffer.append(TEXT_109);
    stringBuffer.append(importManager.getImportedName("org.eclipse.ui.IMemento"));
    stringBuffer.append(TEXT_110);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.View"));
    stringBuffer.append(TEXT_111);
    stringBuffer.append(importManager.getImportedName(genDiagram.getEditPartQualifiedClassName()));
    stringBuffer.append(TEXT_112);
    stringBuffer.append(importManager.getImportedName(genDiagram.getVisualIDRegistryQualifiedClassName()));
    stringBuffer.append(TEXT_113);
    importManager.emitSortedImports();
    stringBuffer.append(TEXT_114);
    return stringBuffer.toString();
  }
}