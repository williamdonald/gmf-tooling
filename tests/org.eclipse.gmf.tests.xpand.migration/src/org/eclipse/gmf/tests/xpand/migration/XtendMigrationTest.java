/**
 * Copyright (c) 2008 Borland Software Corp.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Alexander Shatalin (Borland) - initial API and implementation
 */
package org.eclipse.gmf.tests.xpand.migration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.gmf.internal.xpand.migration.MigrationException;
import org.eclipse.gmf.internal.xpand.migration.MigrationExecutionContextImpl;
import org.eclipse.gmf.internal.xpand.migration.XtendMigrationFacade;
import org.eclipse.gmf.tests.xpand.TestsResourceManager;
import org.eclipse.gmf.tests.xpand.migration.testModel.MigrationTestsPackage;
import org.eclipse.m2m.internal.qvt.oml.common.MdaException;
import org.eclipse.m2m.internal.qvt.oml.compiler.CompiledModule;
import org.eclipse.m2m.internal.qvt.oml.compiler.QvtCompiler;
import org.eclipse.m2m.internal.qvt.oml.compiler.QvtCompilerOptions;

public class XtendMigrationTest extends TestCase {
	
	private static final String LF = System.getProperty("line.separator");

	private TestsResourceManager testResourceManager;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		testResourceManager = new TestsResourceManager();
	}

	// Test for primitive type translation + several parameters in query
	public void testPrimitiveTypeParameters() throws IOException, MigrationException, MdaException {
		String resourceName = "PrimitiveTypeParameters";
		String resourceContent = checkMigration(resourceName);
		checkQVTCompilation(resourceName, resourceContent);
	}

	// Test for user-definet type parameters and import migration
	public void testImportedModels() throws IOException, MigrationException {
		checkMigration("ImportedModels");
	}

	public void testImportedModelsWithUnusedImports() throws IOException, MigrationException {
		checkMigration(new XtendMigrationFacade(testResourceManager, getResourceName("ImportedModels"), true), "ImportedModelsWithUnusedImports");
	}

	public void testImportedExtensions() throws IOException, MigrationException {
		checkMigration("ImportedExtensions");
	}

	public void testImportedExtensionsWReexport() throws IOException, MigrationException {
		checkMigration("ImportedExtensionsWReexport");
	}

	public void testFeatureCall() throws IOException, MigrationException {
		String resourceName = "FeatureCall";
		checkMigration(new XtendMigrationFacade(testResourceManager, getResourceName(resourceName), new MigrationExecutionContextImpl(testResourceManager, GenModelPackage.eINSTANCE, MigrationTestsPackage.eINSTANCE)), resourceName);
	}

	public void testOperationCall() throws IOException, MigrationException {
		String resourceName = "OperationCall";
		checkMigration(new XtendMigrationFacade(testResourceManager, getResourceName(resourceName), new MigrationExecutionContextImpl(testResourceManager, MigrationTestsPackage.eINSTANCE)), resourceName);
	}
	
	public void testCollectionOperations() throws IOException, MigrationException {
		checkMigration("OperationCall_CollectionOperations");
	}
	
	public void testCollectionOperationsAdd() throws IOException, MigrationException {
		checkMigration("OperationCall_CollectionOperations_Add");
	}
	
	public void testCollectionOperationsAddAll() throws IOException, MigrationException {
		checkMigration("OperationCall_CollectionOperations_AddAll");
	}

	public void testCollectionExpression() throws IOException, MigrationException {
		checkMigration("CollectionExpression");
	}

	public void testTypeSelect() throws IOException, MigrationException {
		checkMigration("TypeSelect");
	}

	public void testCast() throws IOException, MigrationException {
		checkMigration("Cast");
	}

	public void testBooleanOperation() throws IOException, MigrationException {
		checkMigration("BooleanOperation");
	}

	public void testChainExpression() throws IOException, MigrationException {
		checkMigration("ChainExpression");
	}

	public void testConstructorCallExpression() throws IOException, MigrationException {
		checkMigration("ConstructorCallExpression");
	}

	public void testIfExpression() throws IOException, MigrationException {
		checkMigration("IfExpression");
	}

	public void testLetExpression() throws IOException, MigrationException {
		checkMigration("LetExpression");
	}

	public void testListLiteral() throws IOException, MigrationException {
		checkMigration("ListLiteral");
	}

	public void testBooleanLiteral() throws IOException, MigrationException {
		checkMigration("BooleanLiteral");
	}

	public void testIntegerLiteral() throws IOException, MigrationException {
		checkMigration("IntegerLiteral");
	}

	public void testNullLiteral() throws IOException, MigrationException {
		checkMigration("NullLiteral");
	}

	public void testRealLiteral() throws IOException, MigrationException {
		checkMigration("RealLiteral");
	}
	
	public void testIteratorVariableUniqueness() throws IOException, MigrationException {
		checkMigration("IteratorVariableUniqueness");
	}
	
	public void testFixedProblems() throws IOException, MigrationException {
		checkMigration("FixedProblems");
	}

	public void testStringLiteral() throws IOException, MigrationException {
		String resourceName = "StringLiteral";
		XtendMigrationFacade migrationFacade = new XtendMigrationFacade(testResourceManager, getResourceName(resourceName));
		String content = migrationFacade.migrateXtendResource().toString();
		assertTrue(content.length() > 0);
		String etalon = readStringContent(new InputStreamReader(testResourceManager.loadFile(getResourceName(resourceName), "qvto")));
		assertEquals(etalon, content);
	}
	
	public void testStringOperations() throws IOException, MigrationException {
		checkMigration("OperationCall_StringOperations");
	}
	
	public void testOclAnyOperations() throws IOException, MigrationException {
		String resourceName = "OperationCall_OclAnyOperations";
		checkMigration(new XtendMigrationFacade(testResourceManager, getResourceName(resourceName), true), resourceName);
	}
	
	public void testBooleanOperations() throws IOException, MigrationException {
		checkMigration("OperationCall_BooleanOperations");
	}
	
	public void testIntegerOperations() throws IOException, MigrationException {
		checkMigration("OperationCall_IntegerOperations");
	}
	
	public void testDoubleOperations() throws IOException, MigrationException {
		checkMigration("OperationCall_DoubleOperations");
	}
	
	private String readStringContent(InputStreamReader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		boolean isInString = false;
		boolean lastSymbolCR = false;
		for (char nextChar = (char) reader.read(); nextChar != (char) -1; nextChar = (char) reader.read()) {
			switch (nextChar) {
			case '\r':
				if (!isInString) {
					lastSymbolCR = true;
					sb.append(LF);
					break;
				}
			case '\n':
				if (!isInString && lastSymbolCR) {
					// replacing all \r\n pairs with single LF only outside
					// of OCL strings
					lastSymbolCR = false;
					break;
				}
			case '\'':
				isInString = !isInString;
			default:
				if (lastSymbolCR) {
					lastSymbolCR = false;
				}
				sb.append(nextChar);
			}
		}
		return sb.toString();
	}

	public void testSwitchExpression() throws IOException, MigrationException {
		checkMigration("SwitchExpression");
	}

	private String checkMigration(String xtendResourceName) throws IOException, MigrationException {
		return checkMigration(new XtendMigrationFacade(testResourceManager, getResourceName(xtendResourceName)), xtendResourceName);
	}

	private String checkMigration(XtendMigrationFacade facade, String xtendResourceName) throws IOException, MigrationException {
		String content = facade.migrateXtendResource().toString();
		assertTrue(content.length() > 0);

		BufferedReader reader = new BufferedReader(new InputStreamReader(testResourceManager.loadFile(getResourceName(xtendResourceName), "qvto")));
		StringBuilder sb = new StringBuilder();
		for (String nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine()) {
			if (sb.length() > 0) {
				sb.append(LF);
			}
			sb.append(nextLine);
		}
		String etalon = normalize(sb.toString());
		content = normalize(content.toString());
		assertEquals(etalon, content);
		return content;
	}

	private void checkQVTCompilation(String resourceName, String resourceContent) throws MdaException, UnsupportedEncodingException {
		QvtCompiler qvtCompiler = new QvtCompiler(new ImportResolver());
		QvtCompilerOptions options = new QvtCompilerOptions();
		options.setGenerateCompletionData(false);
		options.setShowAnnotations(false);
		CompiledModule module = qvtCompiler.compile(new CFileImpl(resourceName, resourceContent), options, null).getModule();
		assertTrue(module.getErrors().length == 0);
	}

	private static String normalize(String text) {
		text = text.replaceAll(" +", " ");
		text = text.replaceAll(" +" + LF, LF);
		text = text.trim();
		return text;
	}

	private static String getResourceName(String shortName) {
		return "org::eclipse::gmf::tests::xpand::migration::" + shortName;
	}

}