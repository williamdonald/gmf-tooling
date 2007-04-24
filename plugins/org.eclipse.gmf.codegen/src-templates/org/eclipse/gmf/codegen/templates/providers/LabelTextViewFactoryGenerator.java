package org.eclipse.gmf.codegen.templates.providers;

import org.eclipse.gmf.codegen.gmfgen.*;
import org.eclipse.gmf.common.codegen.*;
import org.eclipse.gmf.codegen.gmfgen.util.*;

public class LabelTextViewFactoryGenerator
{
  protected static String nl;
  public static synchronized LabelTextViewFactoryGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    LabelTextViewFactoryGenerator result = new LabelTextViewFactoryGenerator();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + "/*" + NL + " * ";
  protected final String TEXT_3 = NL + " */";
  protected final String TEXT_4 = NL + NL + "import org.eclipse.core.runtime.IAdaptable;" + NL + "import org.eclipse.gmf.runtime.notation.View;";
  protected final String TEXT_5 = NL + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_6 = " extends ";
  protected final String TEXT_7 = " {" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected void decorateView(View containerView, View view, IAdaptable semanticAdapter, String semanticHint, int index, boolean persisted) {" + NL + "\t\tsuper.decorateView(containerView, view, semanticAdapter, semanticHint, index, persisted);" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected List createStyles(View view) {" + NL + "\t\t";
  protected final String TEXT_8 = " styles = new ";
  protected final String TEXT_9 = "();";
  protected final String TEXT_10 = NL + "\t\tstyles.add(";
  protected final String TEXT_11 = ".eINSTANCE.createDescriptionStyle());";
  protected final String TEXT_12 = NL + "\t\tstyles.add(";
  protected final String TEXT_13 = ".eINSTANCE.createFillStyle());" + NL + "\t\tstyles.add(";
  protected final String TEXT_14 = ".eINSTANCE.createLineStyle());";
  protected final String TEXT_15 = "\t\t" + NL + "\t\tstyles.add(";
  protected final String TEXT_16 = ".eINSTANCE.createFontStyle());";
  protected final String TEXT_17 = NL + "\t\treturn styles;" + NL + "\t}" + NL + "}";
  protected final String TEXT_18 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
final GenNodeLabel genLabel = (GenNodeLabel) ((Object[]) argument)[0];
final ImportAssistant importManager = (ImportAssistant) ((Object[]) argument)[1];
GenDiagram genDiagram = genLabel.getDiagram();
ViewmapLayoutTypeHelper layoutHelper = ViewmapLayoutTypeHelper.getSharedInstance();

final boolean hasShapeStyles = layoutHelper.isStoringChildPositions(genLabel.getNode());
final boolean isDesignLabel = genLabel.getModelFacet() instanceof DesignLabelModelFacet;

Viewmap viewmap = genLabel.getViewmap();
StyleAttributes styleAttributes = (viewmap == null) ? null : (StyleAttributes)viewmap.find(StyleAttributes.class);
final boolean isFixedFont = styleAttributes != null && styleAttributes.isFixedFont();

    stringBuffer.append(TEXT_1);
    
String copyrightText = genDiagram.getEditorGen().getCopyrightText();
if (copyrightText != null && copyrightText.trim().length() > 0) {

    stringBuffer.append(TEXT_2);
    stringBuffer.append(copyrightText.replaceAll("\n", "\n * "));
    stringBuffer.append(TEXT_3);
    }
    importManager.emitPackageStatement(stringBuffer);
    stringBuffer.append(TEXT_4);
    importManager.markImportLocation(stringBuffer);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(genLabel.getNotationViewFactoryClassName());
    stringBuffer.append(TEXT_6);
    stringBuffer.append(layoutHelper.isStoringChildPositions(genLabel.getNode()) ?
	importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.view.factories.AbstractShapeViewFactory") :
	importManager.getImportedName("org.eclipse.gmf.runtime.diagram.ui.view.factories.BasicNodeViewFactory"));
    stringBuffer.append(TEXT_7);
    stringBuffer.append(importManager.getImportedName("java.util.List"));
    stringBuffer.append(TEXT_8);
    stringBuffer.append(importManager.getImportedName("java.util.ArrayList"));
    stringBuffer.append(TEXT_9);
    
if (hasShapeStyles || isDesignLabel) {

    stringBuffer.append(TEXT_10);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.NotationFactory"));
    stringBuffer.append(TEXT_11);
    
}
if (hasShapeStyles) {

    stringBuffer.append(TEXT_12);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.NotationFactory"));
    stringBuffer.append(TEXT_13);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.NotationFactory"));
    stringBuffer.append(TEXT_14);
    
	if (!isFixedFont) {

    stringBuffer.append(TEXT_15);
    stringBuffer.append(importManager.getImportedName("org.eclipse.gmf.runtime.notation.NotationFactory"));
    stringBuffer.append(TEXT_16);
    
	}
}

    stringBuffer.append(TEXT_17);
    importManager.emitSortedImports();
    stringBuffer.append(TEXT_18);
    return stringBuffer.toString();
  }
}
