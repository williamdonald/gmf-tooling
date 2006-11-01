package org.eclipse.gmf.graphdef.codegen.templates;

import org.eclipse.gmf.gmfgraph.*;
import org.eclipse.gmf.gmfgraph.util.*;
import org.eclipse.gmf.common.codegen.*;
import org.eclipse.gmf.graphdef.codegen.*;
import org.eclipse.emf.ecore.*;
import java.util.Iterator;
import org.eclipse.emf.ecore.util.EcoreUtil;;

public class TopGenerator
{
  protected static String nl;
  public static synchronized TopGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    TopGenerator result = new TopGenerator();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "";
  protected final String TEXT_2 = NL + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_3 = " extends ";
  protected final String TEXT_4 = " {" + NL;
  protected final String TEXT_5 = NL;
  protected final String TEXT_6 = NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ScalablePolygon extends org.eclipse.draw2d.Shape {";
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate final org.eclipse.draw2d.geometry.PointList myTemplate = new org.eclipse.draw2d.geometry.PointList();" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate org.eclipse.draw2d.geometry.Rectangle myTemplateBounds;" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void addPoint(org.eclipse.draw2d.geometry.Point point){" + NL + "\t\tmyTemplate.addPoint(point);" + NL + "\t\tmyTemplateBounds = null;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected void fillShape(org.eclipse.draw2d.Graphics graphics) {" + NL + "\t\torg.eclipse.draw2d.geometry.Rectangle bounds = getBounds();" + NL + "\t\tgraphics.pushState();" + NL + "\t\tgraphics.translate(bounds.x, bounds.y);" + NL + "\t\tgraphics.fillPolygon(scalePointList());" + NL + "\t\tgraphics.popState();" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected void outlineShape(org.eclipse.draw2d.Graphics graphics) {" + NL + "\t\torg.eclipse.draw2d.geometry.Rectangle bounds = getBounds();" + NL + "\t\tgraphics.pushState();" + NL + "\t\tgraphics.translate(bounds.x, bounds.y);" + NL + "\t\tgraphics.drawPolygon(scalePointList());" + NL + "\t\tgraphics.popState();" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate org.eclipse.draw2d.geometry.Rectangle getTemplateBounds(){" + NL + "\t\tif (myTemplateBounds == null) {" + NL + "\t\t\tmyTemplateBounds = myTemplate.getBounds().getCopy().union(0, 0);" + NL + "\t\t\t//just safety -- we are going to use this as divider " + NL + "\t\t\tif (myTemplateBounds.width < 1){" + NL + "\t\t\t\tmyTemplateBounds.width = 1;" + NL + "\t\t\t}" + NL + "\t\t\tif (myTemplateBounds.height < 1){" + NL + "\t\t\t\tmyTemplateBounds.height = 1;" + NL + "\t\t\t}" + NL + "\t\t}" + NL + "\t\treturn myTemplateBounds;" + NL + "\t}" + NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate int[] scalePointList() {" + NL + "\t\torg.eclipse.draw2d.geometry.Rectangle pointsBounds = getTemplateBounds();" + NL + "\t\torg.eclipse.draw2d.geometry.Rectangle actualBounds = getBounds();" + NL + "" + NL + "\t\tfloat xScale = ((float) actualBounds.width) / pointsBounds.width;" + NL + "\t\tfloat yScale = ((float) actualBounds.height) / pointsBounds.height;" + NL + "" + NL + "\t\tif (xScale == 1 && yScale == 1) {" + NL + "\t\t\treturn myTemplate.toIntArray();" + NL + "\t\t}" + NL + "\t\tint[] scaled = (int[]) myTemplate.toIntArray().clone();" + NL + "\t\tfor (int i = 0; i < scaled.length; i += 2) {" + NL + "\t\t\tscaled[i] = (int) Math.floor(scaled[i] * xScale);" + NL + "\t\t\tscaled[i + 1] = (int) Math.floor(scaled[i + 1] * yScale);" + NL + "\t\t}" + NL + "\t\treturn scaled;" + NL + "\t}" + NL + "}";
  protected final String TEXT_9 = NL;
  protected final String TEXT_10 = NL;
  protected final String TEXT_11 = NL;
  protected final String TEXT_12 = NL + "}" + NL + "\t";
  protected final String TEXT_13 = NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final ";
  protected final String TEXT_14 = " ";
  protected final String TEXT_15 = " = ";
  protected final String TEXT_16 = ";";
  protected final String TEXT_17 = NL + "\t";
  protected final String TEXT_18 = NL + "\t" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic static final ";
  protected final String TEXT_19 = " ";
  protected final String TEXT_20 = " = ";
  protected final String TEXT_21 = ";";
  protected final String TEXT_22 = NL + "}";
  protected final String TEXT_23 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
Object[] args = (Object[]) argument;
Figure figure = (Figure) args[0];
final GraphDefDispatcher /*inner*/dispatcher = (GraphDefDispatcher) args[1];
final GraphDefDispatcher topDispatcher = (GraphDefDispatcher) args[2];
final boolean isStaticFieldsOutsideClassBody = ((Boolean) args[3]).booleanValue();

class Asserter {
	void makeAssert(boolean checkTrue){
		assert checkTrue;
		//temporarily use more forced way to ensure assertion
		//TODO: replace with single assert 
		if (!checkTrue){
			throw new IllegalStateException("Enable Assertions");
		}
	}
}

final Asserter myAsserter = new Asserter();
myAsserter.makeAssert(topDispatcher.getImportManager() == /*inner*/dispatcher.getImportManager());
myAsserter.makeAssert(topDispatcher.getFQNSwitch() == /*inner*/dispatcher.getFQNSwitch());

final ImportAssistant importManager = topDispatcher.getImportManager();
final FigureQualifiedNameSwitch fqnSwitch = topDispatcher.getFQNSwitch();

    importManager.emitPackageStatement(stringBuffer);
    stringBuffer.append(TEXT_1);
    importManager.markImportLocation(stringBuffer);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(importManager.getCompilationUnitName());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(fqnSwitch.get(figure, importManager));
    stringBuffer.append(TEXT_4);
    stringBuffer.append(TEXT_5);
    
//input: [oeg].gmfgraph.Figure figure
{ //namespace, prefix spic (stands for Scalable Polygon as Inner Class) to avoid name clashes
	boolean spicFound = false;
	for (Iterator spicAllPolygons = EcoreUtil.getAllContents(figure, false); !spicFound && spicAllPolygons.hasNext();){
		EObject spicNext = (EObject)spicAllPolygons.next();
		spicFound = GMFGraphPackage.eINSTANCE.getScalablePolygon().isSuperTypeOf(spicNext.eClass());
	}
	if (spicFound) { 

    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    
	}
} // end of namespace

    stringBuffer.append(TEXT_9);
     /*NOTE: class structure is selected by topDispatcher, but children, attributes etc -- by inner dispatcher*/ 
    stringBuffer.append(TEXT_10);
    stringBuffer.append(topDispatcher.dispatch(figure, new Object[] {
		/*inner*/dispatcher.create(figure, "this"), 
		topDispatcher.create(figure, "this")
}));
    stringBuffer.append(TEXT_11);
    
if (isStaticFieldsOutsideClassBody){ /*put fields out of inner class body*/ 
    stringBuffer.append(TEXT_12);
    
//input: [oeg].graphdef.codegen GraphDefDispatcher dispatcher
for (Iterator allFields = dispatcher.getStaticFieldsManager().allFields(); allFields.hasNext();) {
	StaticFieldsManager.StaticField next = (StaticFieldsManager.StaticField)allFields.next(); 
    stringBuffer.append(TEXT_13);
    stringBuffer.append(next.getType());
    stringBuffer.append(TEXT_14);
    stringBuffer.append(next.getName());
    stringBuffer.append(TEXT_15);
    stringBuffer.append(next.getValue());
    stringBuffer.append(TEXT_16);
    
}

     } else { 
    stringBuffer.append(TEXT_17);
    
//input: [oeg].graphdef.codegen GraphDefDispatcher dispatcher
for (Iterator allFields = dispatcher.getStaticFieldsManager().allFields(); allFields.hasNext();) {
	StaticFieldsManager.StaticField next = (StaticFieldsManager.StaticField)allFields.next(); 
    stringBuffer.append(TEXT_18);
    stringBuffer.append(next.getType());
    stringBuffer.append(TEXT_19);
    stringBuffer.append(next.getName());
    stringBuffer.append(TEXT_20);
    stringBuffer.append(next.getValue());
    stringBuffer.append(TEXT_21);
    
}

    stringBuffer.append(TEXT_22);
    }
    importManager.emitSortedImports();
    stringBuffer.append(TEXT_23);
    return stringBuffer.toString();
  }
}
