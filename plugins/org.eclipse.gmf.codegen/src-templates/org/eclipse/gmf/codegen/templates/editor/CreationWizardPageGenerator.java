package org.eclipse.gmf.codegen.templates.editor;

import org.eclipse.gmf.codegen.gmfgen.*;
import org.eclipse.gmf.common.codegen.*;

public class CreationWizardPageGenerator
{
  protected static String nl;
  public static synchronized CreationWizardPageGenerator create(String lineSeparator)
  {
    nl = lineSeparator;
    CreationWizardPageGenerator result = new CreationWizardPageGenerator();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "/*" + NL + " * ";
  protected final String TEXT_2 = NL + " */";
  protected final String TEXT_3 = NL + NL + "import org.eclipse.core.runtime.IPath;" + NL + "import org.eclipse.core.runtime.Path;" + NL + "import org.eclipse.emf.common.util.URI;" + NL + "import org.eclipse.jface.viewers.IStructuredSelection;" + NL + "import org.eclipse.osgi.util.NLS;" + NL + "import org.eclipse.swt.widgets.Composite;";
  protected final String TEXT_4 = NL + NL + "/**" + NL + " * @generated" + NL + " */" + NL + "public class ";
  protected final String TEXT_5 = " extends";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = "\t";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = "\t";
  protected final String TEXT_10 = NL + "\t{" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprivate final String fileExtension;" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic ";
  protected final String TEXT_11 = "(String pageName, IStructuredSelection selection, String fileExtension) {" + NL + "\t\tsuper(pageName, selection);" + NL + "\t\tthis.fileExtension = fileExtension;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * Override to create files with this extension." + NL + "\t * " + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected String getExtension() {" + NL + "\t\treturn fileExtension;" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic URI getURI() {";
  protected final String TEXT_12 = NL + "\t\treturn URI.createPlatformResourceURI(getFilePath().toString(), false);";
  protected final String TEXT_13 = NL + "\t\treturn URI.createFileURI(getFilePath().toString());";
  protected final String TEXT_14 = NL + "\t}";
  protected final String TEXT_15 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected IPath getFilePath() {" + NL + "\t\tIPath path = getContainerFullPath();" + NL + "\t\tif (path == null) {" + NL + "\t\t\tpath = new Path(\"\"); //$NON-NLS-1$" + NL + "\t\t}" + NL + "\t\tString fileName = getFileName();" + NL + "\t\tif (fileName != null) {" + NL + "\t\t\tpath = path.append(fileName);" + NL + "\t\t}" + NL + "\t\treturn path;" + NL + "\t}";
  protected final String TEXT_16 = NL + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tpublic void createControl(Composite parent) {" + NL + "\t\tsuper.createControl(parent);" + NL + "\t\tsetFileName(";
  protected final String TEXT_17 = ".getUniqueFileName(" + NL + "\t\t\t\tgetContainerFullPath(), getFileName(), getExtension()));" + NL + "\t\tsetPageComplete(validatePage());" + NL + "\t}" + NL + "" + NL + "\t/**" + NL + "\t * @generated" + NL + "\t */" + NL + "\tprotected boolean validatePage() {" + NL + "\t\tif (!super.validatePage()) {" + NL + "\t\t\treturn false;" + NL + "\t\t}" + NL + "\t\tString extension = getExtension();" + NL + "\t\tif (extension != null && !getFilePath().toString().endsWith(\".\" + extension)) {" + NL + "\t\t\tsetErrorMessage(NLS.bind(\"File name should have ''{0}'' extension.\", extension));" + NL + "\t\t\treturn false;" + NL + "\t\t}" + NL + "\t\treturn true;" + NL + "\t}" + NL + "}";

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
final GenDiagram genDiagram = (GenDiagram) ((Object[]) argument)[0];
final GenEditorGenerator editorGen = genDiagram.getEditorGen();
final ImportAssistant importManager = (ImportAssistant) ((Object[]) argument)[1];
final GenApplication application = editorGen.getApplication();

    
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
    stringBuffer.append(importManager.getCompilationUnitName());
    stringBuffer.append(TEXT_5);
    if (application == null) {
    stringBuffer.append(TEXT_6);
    stringBuffer.append(importManager.getImportedName("org.eclipse.ui.dialogs.WizardNewFileCreationPage"));
    stringBuffer.append(TEXT_7);
    } else {
    stringBuffer.append(TEXT_8);
    stringBuffer.append(importManager.getImportedName(application.getPackageName() + ".WizardNewFileCreationPage"));
    stringBuffer.append(TEXT_9);
    }
    stringBuffer.append(TEXT_10);
    stringBuffer.append(importManager.getCompilationUnitName());
    stringBuffer.append(TEXT_11);
    if (application == null) {
    stringBuffer.append(TEXT_12);
    } else {
    stringBuffer.append(TEXT_13);
    }
    stringBuffer.append(TEXT_14);
    if (application == null) {
    stringBuffer.append(TEXT_15);
    }
    stringBuffer.append(TEXT_16);
    stringBuffer.append(importManager.getImportedName(genDiagram.getDiagramEditorUtilQualifiedClassName()));
    stringBuffer.append(TEXT_17);
    importManager.emitSortedImports();
    return stringBuffer.toString();
  }
}
