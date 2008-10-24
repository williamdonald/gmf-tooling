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
package org.eclipse.gmf.internal.xpand.migration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.internal.xpand.BuiltinMetaModel;
import org.eclipse.gmf.internal.xpand.BuiltinMetaModelExt;
import org.eclipse.gmf.internal.xpand.ResourceManager;
import org.eclipse.gmf.internal.xpand.ResourceMarker;
import org.eclipse.gmf.internal.xpand.eval.EvaluationListener;
import org.eclipse.gmf.internal.xpand.expression.ExecutionContext;
import org.eclipse.gmf.internal.xpand.expression.ExecutionContextImpl;
import org.eclipse.gmf.internal.xpand.expression.Variable;
import org.eclipse.gmf.internal.xpand.expression.ast.BooleanLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.BooleanOperation;
import org.eclipse.gmf.internal.xpand.expression.ast.Case;
import org.eclipse.gmf.internal.xpand.expression.ast.Cast;
import org.eclipse.gmf.internal.xpand.expression.ast.ChainExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.CollectionExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.ConstructorCallExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.Expression;
import org.eclipse.gmf.internal.xpand.expression.ast.FeatureCall;
import org.eclipse.gmf.internal.xpand.expression.ast.IfExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.IntegerLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.LetExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.ListLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.NullLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.OperationCall;
import org.eclipse.gmf.internal.xpand.expression.ast.RealLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.StringLiteral;
import org.eclipse.gmf.internal.xpand.expression.ast.SwitchExpression;
import org.eclipse.gmf.internal.xpand.expression.ast.TypeSelectExpression;
import org.eclipse.gmf.internal.xpand.migration.MigrationException.Type;
import org.eclipse.gmf.internal.xpand.util.ClassLoadContext;
import org.eclipse.gmf.internal.xpand.xtend.ast.Extension;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;


public class ExpressionMigrationFacade {

	public static final String LF = System.getProperty("line.separator");
	
	static final EcoreEnvironmentFactory ECORE_ENV_FACTORY = new EcoreEnvironmentFactory(null);
	
	private static final Set<EOperation> infixOperations = new HashSet<EOperation>(Arrays.asList(new EOperation[] {
			BuiltinMetaModel.Boolean_NE,
			BuiltinMetaModel.Int_Unary_Minus,
			BuiltinMetaModel.Double_Unary_Minus,
			BuiltinMetaModel.Int_Minus_Double, 
			BuiltinMetaModel.Int_Minus_Int, 
			BuiltinMetaModel.Double_Minus_Double, 
			BuiltinMetaModel.Double_Minus_Int,
			BuiltinMetaModel.Int_Plus_Double, 
			BuiltinMetaModel.Int_Plus_Int, 
			BuiltinMetaModel.Double_Plus_Double, 
			BuiltinMetaModel.Double_Plus_Int,
			BuiltinMetaModel.Int_Mult_Double, 
			BuiltinMetaModel.Int_Mult_Int, 
			BuiltinMetaModel.Double_Mult_Double, 
			BuiltinMetaModel.Double_Mult_Int,
			BuiltinMetaModel.Int_Div_Double,
			BuiltinMetaModel.Double_Div_Double,
			BuiltinMetaModel.Double_Div_Int,
			BuiltinMetaModel.Int_Less,
			BuiltinMetaModel.Int_LessOrEqual,
			BuiltinMetaModel.Int_Greater,
			BuiltinMetaModel.Int_GreatOrEqual,
			BuiltinMetaModel.Object_EQ,
			BuiltinMetaModel.Object_NotEQ
		}));
	
	private static final Set<EOperation> collectionOperations = new HashSet<EOperation>(Arrays.asList(new EOperation[] {
			BuiltinMetaModel.Collection_Add,
			BuiltinMetaModel.Collection_AddAll,
			BuiltinMetaModel.Collection_Clear,
			BuiltinMetaModel.Collection_Contains,
			BuiltinMetaModel.Collection_ContainsAll, 
			BuiltinMetaModel.Collection_Flatten,
			BuiltinMetaModel.Collection_Intersect,
			BuiltinMetaModel.Collection_IsEmpty, 
			BuiltinMetaModel.Collection_Size, 
			BuiltinMetaModel.Collection_ToList,
			BuiltinMetaModel.Collection_ToSet,
			BuiltinMetaModel.Collection_Union,
			BuiltinMetaModel.Collection_Without,
			BuiltinMetaModel.List_First,
			BuiltinMetaModel.List_Get,
			BuiltinMetaModel.List_IndexOf,
			BuiltinMetaModel.List_Last,
			BuiltinMetaModel.List_PurgeDups,
			BuiltinMetaModel.List_WithoutFirst,
			BuiltinMetaModel.List_WithoutLast
	}));

	private Stack<Expression> expressionsStack = new Stack<Expression>();

	private StringBuilder output = new StringBuilder();

	private MigrationExecutionContext ctx;

	private int returnPosition;

	private VariableNameDispatcher variableDispatcher;

	private Expression rootExpression;

	private TypeManager typeManager;

	private ModelManager modelManager;
	
	private Stack<QvtExecutionContext> qvtContexts = new Stack<QvtExecutionContext>();

	private EClassifier rootExpressionType;

	ExpressionMigrationFacade(Expression expression, EClassifier requiredType, TypeManager typeManager, ModelManager modelManager, VariableNameDispatcher variableDispatcher, MigrationExecutionContext context) {
		rootExpression = expression;
		rootExpressionType = requiredType;
		this.typeManager = typeManager;
		this.modelManager = modelManager;
		this.variableDispatcher = variableDispatcher;
		ctx = context;
		markReturnPosition();
	}

	StringBuilder migrate() throws MigrationException {
		qvtContexts.push(new QvtExecutionContext());
		try {
			EClassifier expressionQvtType = migrateExpression(rootExpression);
			internalConvertTypes(expressionQvtType, rootExpressionType);
		} finally {
			qvtContexts.pop();
		}
		return output;
	}

	// TODO: similar to internalMigrateParameterCollectionToMain() ?
	private void internalConvertTypes(EClassifier actualType, EClassifier expectedType) {
		if (expectedType != BuiltinMetaModel.VOID && BuiltinMetaModel.isCollectionType(expectedType)) {
			assert BuiltinMetaModel.isCollectionType(actualType);
			
			if (BuiltinMetaModelExt.isListType(expectedType)) {
				if (BuiltinMetaModelExt.isSetType(actualType) || BuiltinMetaModelExt.isOrderedSetType(actualType) || BuiltinMetaModelExt.isBagType(actualType)) {
					write("->asSequence()");
				} else if (BuiltinMetaModelExt.isAbstractCollectionType(actualType)) {
					internalMigrateCollectionToBag(null);
					write("->asSequence()");
				}
			} else if (BuiltinMetaModelExt.isSetType(expectedType)) {
				if (BuiltinMetaModelExt.isListType(actualType) || BuiltinMetaModelExt.isBagType(actualType)) {
					write("->asSet()");
				} else if (BuiltinMetaModelExt.isAbstractCollectionType(actualType)) {
					internalMigrateCollectionToBag(null);
					write("->asSet()");
				}
			}
			// Abstract collection should be compatible with any other kind
			// on collections
		}
//		else if (EcorePackage.eINSTANCE.getEString() == expectedType && actualType != EcorePackage.eINSTANCE.getEString()) {
//			write(".repr()");
//		}
	}

	int getReturnPosition() {
		return returnPosition;
	}

	// Returning xpand types from here. This method can be modified to make use
	// of QVT type system instead.
	private EClassifier migrateExpression(Expression expression) throws MigrationException {
		expressionsStack.push(expression);
		try {
			if (expression instanceof BooleanOperation) {
				return migrateBooleanOperation((BooleanOperation) expression);
			} else if (expression instanceof Cast) {
				return migrateCast((Cast) expression);
			} else if (expression instanceof ChainExpression) {
				return migrateChainExpression((ChainExpression) expression);
			} else if (expression instanceof ConstructorCallExpression) {
				return migrateConstructorCallExpression((ConstructorCallExpression) expression);
			} else if (expression instanceof CollectionExpression) {
				return migrateCollectionExpression((CollectionExpression) expression);
			} else if (expression instanceof OperationCall) {
				return migrateOperationCall((OperationCall) expression);
			} else if (expression instanceof TypeSelectExpression) {
				return migrateTypeSelectExpression((TypeSelectExpression) expression);
			} else if (expression instanceof FeatureCall) {
				return migrateFeatureCall((FeatureCall) expression);
			} else if (expression instanceof IfExpression) {
				return migrateIfExpression((IfExpression) expression);
			} else if (expression instanceof LetExpression) {
				return migrateLetExpression((LetExpression) expression);
			} else if (expression instanceof ListLiteral) {
				return migrateListLiteral((ListLiteral) expression);
			} else if (expression instanceof BooleanLiteral) {
				return migrateBooleanLiteral((BooleanLiteral) expression);
			} else if (expression instanceof IntegerLiteral) {
				return migrateIntegerLiteral((IntegerLiteral) expression);
			} else if (expression instanceof NullLiteral) {
				return migrateNullLiteral((NullLiteral) expression);
			} else if (expression instanceof RealLiteral) {
				return migrateRealLiteral((RealLiteral) expression);
			} else if (expression instanceof StringLiteral) {
				return migrateStringLiteral((StringLiteral) expression);
			} else if (expression instanceof SwitchExpression) {
				return migrateSwitchExpression((SwitchExpression) expression);
			} else {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, expression.getClass().getName());
			}
		} finally {
			expressionsStack.pop();
		}
	}

	private EClassifier migrateSwitchExpression(SwitchExpression switchExpression) throws MigrationException {
		Collection<EClassifier> expressionTypes = new ArrayList<EClassifier>();
		if (switchExpression.getCases().size() == 0) {
			expressionTypes.add(migrateExpression(switchExpression.getDefaultExpr()));
		} else {
			// TODO: define additional variable here.
			write("switch { ");
			for (Case caseExpression : switchExpression.getCases()) {
				write("case (");
				migrateExpression(switchExpression.getSwitchExpr());
				write(" = ");
				migrateExpression(caseExpression.getCondition());
				write(") ");
				expressionTypes.add(migrateExpression(caseExpression.getThenPart()));
				write("; ");
			}
			write("else ");
			expressionTypes.add(migrateExpression(switchExpression.getDefaultExpr()));
			write("; }");
		}
		// TODO: check different types of collections was produced
		return BuiltinMetaModelExt.getCommonSuperType(expressionTypes);
	}

	private EClassifier migrateStringLiteral(StringLiteral expression) {
		write("'");
		write(escape(expression.getValue()));
		write("'");
		return EcorePackage.eINSTANCE.getEString();
	}

	private String escape(String value) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char nextChar = value.charAt(i);
			if (nextChar == '\'') {
				// escaping single quote mark with one more single quote mark.
				sb.append(nextChar);
				sb.append(nextChar);
			} else if (nextChar == '\n') {
				sb.append("\\n");
			} else if (nextChar == '\r') {
				sb.append("\\r");
			} else {
				sb.append(nextChar);
			}
		}
		return sb.toString();
	}

	private EClassifier migrateRealLiteral(RealLiteral realLiteral) {
		write(new Double(realLiteral.getLiteralValue()).toString());
		return EcorePackage.eINSTANCE.getEDouble();
	}

	private EClassifier migrateNullLiteral(NullLiteral expression) {
		write("null");
		return BuiltinMetaModel.VOID;
	}

	private EClassifier migrateIntegerLiteral(IntegerLiteral integerLiteral) {
		write(new Integer(integerLiteral.getLiteralValue()).toString());
		return EcorePackage.eINSTANCE.getEInt();
	}

	private EClassifier migrateBooleanLiteral(BooleanLiteral booleanLiteral) {
		write(Boolean.valueOf(booleanLiteral.getLiteralValue()) ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
		return EcorePackage.eINSTANCE.getEBoolean();
	}

	private EClassifier migrateListLiteral(ListLiteral listLiteral) throws MigrationException {
		Collection<EClassifier> expressionTypes = new ArrayList<EClassifier>();
		write("Sequence { ");
		for (int i = 0; i < listLiteral.getElements().length; i++) {
			if (i > 0) {
				write(", ");
			}
			expressionTypes.add(migrateExpression(listLiteral.getElements()[i]));
		}
		write(" }");
		return BuiltinMetaModelExt.getListType(BuiltinMetaModelExt.getCommonSuperType(expressionTypes));
	}

	private EClassifier migrateLetExpression(LetExpression letExpression) throws MigrationException {
		String varName = letExpression.getVarName().getValue();
		write("let ");
		write(varName);
		write(" : ");
		int typePosition = getCurrentPosition();
		write(" = ");
		EClassifier varType = migrateExpression(letExpression.getVarExpression());
		write(typeManager.getQvtFQName(varType), typePosition);
		pushContextWithVariable(varName, varType);
		try {
			write(" in ");
			return migrateExpression(letExpression.getTargetExpression());
		} finally {
			qvtContexts.pop();
		}
	}
	
	private EClassifier migrateIfExpression(IfExpression ifExpression) throws MigrationException {
		write("if ");
		migrateExpression(ifExpression.getCondition());
		write(" then ");
		EClassifier thenType = migrateExpression(ifExpression.getThenPart());
		write(" else ");
		EClassifier elseType = migrateExpression(ifExpression.getElsePart());
		write(" endif");
		// TODO: check if then/else produces different types of collections..
		return BuiltinMetaModelExt.getCommonSuperType(thenType, elseType);
	}

	private EClassifier migrateConstructorCallExpression(ConstructorCallExpression constructorCall) throws MigrationException {
		write("object ");
		EClassifier type = ctx.getTypeForName(constructorCall.getType().getValue());
		if (type == null) {
			throw new MigrationException(Type.TYPE_NOT_FOUND, constructorCall.getType().getValue());
		}

		write(typeManager.getQvtFQName(type));
		write(" {}");
		return type;
	}

	private EClassifier migrateChainExpression(ChainExpression chainExpression) throws MigrationException {
		// TODO: currently only top-level chain expressions are supported. We
		// have to develop a way to support inner chain expressions like:
		// if(a.b()->c.d()->e.f) then {...} else {...}
		// for now solution is to use separate helpers for each nested chain
		// expression
		if (expressionsStack.size() > 1 && false == expressionsStack.peek() instanceof ChainExpression) {
			throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Inner " + chainExpression.getClass().getName());
		}
		migrateExpression(chainExpression.getFirst());
		write("; ");
		if (expressionsStack.size() == 1) {
			markReturnPosition();
		}
		return migrateExpression(chainExpression.getNext());
	}

	private EClassifier migrateBooleanOperation(BooleanOperation booleanOperation) throws MigrationException {
		migrateExpression(booleanOperation.getLeft());
		if (booleanOperation.isAndOperation()) {
			write(" and ");
		} else if (booleanOperation.isOrOperation()) {
			write(" or ");
		} else if (booleanOperation.isImpliesOperation()) {
			write(" implies ");
		} else {
			throw new MigrationException(Type.UNSUPPORTED_BOOLEAN_OPERATION, booleanOperation.getOperator());
		}
		migrateExpression(booleanOperation.getRight());
		return EcorePackage.eINSTANCE.getEBoolean();
	}

	private EClassifier migrateCast(Cast cast) throws MigrationException {
		EClassifier migratedExpressionType = migrateExpression(cast.getTarget());
		EClassifier type = ctx.getTypeForName(cast.getType().getValue());
		if (type == null) {
			throw new MigrationException(Type.TYPE_NOT_FOUND, cast.getType().getValue());
		}
		if (BuiltinMetaModel.isCollectionType(type)) {
			// This operation is not supported now.
			return migratedExpressionType;
		}
		write(".oclAsType(");
		write(typeManager.getQvtFQName(type));
		write(")");
		return type;
	}

	private EClassifier migrateTypeSelectExpression(TypeSelectExpression typeSelectExpression) throws MigrationException {
		int placeholder = getCurrentPosition();
		EClassifier targetQvtType = migrateExpression(typeSelectExpression.getTarget());
		EClassifier type = ctx.getTypeForName(typeSelectExpression.getTypeLiteral().getValue());
		if (type == null) {
			throw new MigrationException(Type.TYPE_NOT_FOUND, typeSelectExpression.getTypeLiteral().getValue());
		}
		ExpressionAnalyzeTrace expressionTrace = ctx.getTraces().get(typeSelectExpression);
		if (false == expressionTrace instanceof TypeSelectExpressionTrace) {
			throw new MigrationException(Type.UNSUPPORTED_TYPE_SELECT_EXPRESSION_TRACE, String.valueOf(expressionTrace));
		}
		TypeSelectExpressionTrace trace = (TypeSelectExpressionTrace) expressionTrace;
		if (!trace.isValid()) {
			throw new MigrationException(Type.UNSUPPORTED_TYPE_SELECT_EXPRESSION, trace.toString());
		}
		internalMigrateTypeSelectCastingCollectionToBag(targetQvtType, typeManager.getQvtFQName(type), placeholder);
		if (!BuiltinMetaModelExt.isListType(targetQvtType) && !BuiltinMetaModelExt.isOrderedSetType(targetQvtType)) {
			write("->asSequence()");
		}
		return BuiltinMetaModelExt.isOrderedSetType(targetQvtType) ? BuiltinMetaModelExt.getOrderedSetType(type) : BuiltinMetaModelExt.getListType(type);
	}

	private void internalMigrateTypeSelectCastingCollectionToBag(EClassifier collectionType, String typeName, int placeholder) {
		assert BuiltinMetaModel.isCollectionType(collectionType);
		if (BuiltinMetaModelExt.isAbstractCollectionType(collectionType)) {
			internalMigrateCollectionToBag(typeName);
		} else {
			internalMigrateTypeSelect(typeName, collectionType, placeholder, getCurrentPosition());
		}
	}

	private void internalMigrateTypeSelect(String typeName, EClassifier originalCollectionType, int expressionStartPosition, int expressionEndPosition) {
		// TODO: This method should write braces around expression starting
		// at placeholder position conditionally depending on the last char
		// in output sequence.
		StringBuilder sb = new StringBuilder();
//		sb.append(")[");
//		sb.append(typeName);
//		sb.append("]");
		sb.append(")");
		sb.append(ts(typeName, originalCollectionType));
		write(sb, expressionEndPosition);
		
		write("(", expressionStartPosition);
	}
	
	private StringBuilder ts(String typeName, EClassifier originalCollectionType) {
		String it = variableDispatcher.getNextIteratorName();
		StringBuilder sb = new StringBuilder();
		sb.append("->select(");
		sb.append(it);
		sb.append(" | ");
		sb.append(it);
		sb.append(".oclIsKindOf(");
		sb.append(typeName);
		sb.append("))->collect(");
		sb.append(it);
		sb.append(" | ");
		sb.append(it);
		sb.append(".oclAsType(");
		sb.append(typeName);
		sb.append("))");
		if (BuiltinMetaModelExt.isSetType(originalCollectionType)) {
			sb.append("->asSet()");
		} else if (BuiltinMetaModelExt.isOrderedSetType(originalCollectionType)) {
			sb.append("->asOrderedSet()");
		}
		return sb;
	}

	// TODO: use ->asSequence() here in addition?
	private void internalMigrateCollectionToBag(String typeName) {
		String iteratorName = variableDispatcher.getNextIteratorName();
		write("->collect(");
		write(iteratorName);
		write(" | ");
		write(iteratorName);
		if (typeName != null) {
			write(".oclAsType(");
			write(typeName);
			write(")");
		}
		write(")");
	}
	
	private EClassifier migrateCollectionExpression(CollectionExpression collectionExpression) throws MigrationException {
		if (collectionExpression.getTarget() == null) {
			throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Collection expression without target specified: " + collectionExpression.toString());
		}
		ExpressionAnalyzeTrace expressionTrace = ctx.getTraces().get(collectionExpression);
		if (false == expressionTrace instanceof CollectionExpressionTrace) {
			throw new MigrationException(Type.UNSUPPORTED_COLLECTION_EXPRESSION_TRACE, String.valueOf(expressionTrace));
		}
		CollectionExpressionTrace trace = (CollectionExpressionTrace) expressionTrace;

		int placeholder = getCurrentPosition();
		boolean hasNegation = false;
		EClassifier targetQvtType = migrateExpression(collectionExpression.getTarget());
		assert BuiltinMetaModel.isCollectionType(targetQvtType);
		EClassifier innerTargetQvtType = BuiltinMetaModel.getInnerType(targetQvtType);

		write("->");
		switch (trace.getType()) {
		case NOTEXISTS_REF:
			hasNegation = true;
			write("not ", placeholder);
			write("exists");
			break;
		case COLLECT_REF:
		case SELECT_REF:
		case REJECT_REF:
		case EXISTS_REF:
		case FORALL_REF:
			write(collectionExpression.getName().getValue());
			break;
		case INCORRECT_EXPRESSION_TYPE:
		case UNDESOLVED_TARGET_TYPE:
			throw new MigrationException(Type.UNSUPPORTED_COLLECTION_EXPRESSION, trace.toString());
		default:
			throw new MigrationException(Type.UNSUPPORTED_COLLECTION_EXPRESSION_TRACE, "Incorrect type: " + trace.getType());
		}
		write("(");
		String varName = collectionExpression.getElementName();
		write(varName);
		pushContextWithVariable(varName, innerTargetQvtType);
		EClassifier expressionType;
		try {
			write(" | ");
			expressionType = migrateExpression(collectionExpression.getClosure());
		} finally {
			qvtContexts.pop();
		}
		write(")");
		try {
			// Determining actual result type
			switch (trace.getType()) {
			case NOTEXISTS_REF:
			case EXISTS_REF:
			case FORALL_REF:
				return EcorePackage.eINSTANCE.getEBoolean();
			case COLLECT_REF:
				if (BuiltinMetaModelExt.isSetType(trace.getResultType())) {
					// Does not work now due to the bug in xpand implementation
					// - see "TODO [AS]" comment in CollectionExpression
					write("->asSet()");
					// TODO: add flatten here?
					return BuiltinMetaModel.getSetType(expressionType);
				}else if (BuiltinMetaModelExt.isListType(trace.getResultType())) {
					return BuiltinMetaModelExt.getListType(expressionType);
				} else {
					return BuiltinMetaModelExt.getBagType(expressionType);
				}
			case SELECT_REF:
			case REJECT_REF:
				return targetQvtType;
			}
			// Unreachable
			return null;
		} finally {
			if (hasNegation) {
				addNegationBraces(placeholder);
			}
		}
	}

	private void addNegationBraces(int placeholder) {
		if (expressionsStack.size() == 1) {
			return;
		}
		// TODO: check for the type of parent expression here + add braces
		// conditionaly
		// Expression parentExpression =
		// expressionsStack.get(expressionsStack.size() - 2);
		// check for the type of parent expression;
		write("(", placeholder);
		write(")");
	}

	private EClassifier migrateOperationCall(OperationCall operationCall) throws MigrationException {
		ExpressionAnalyzeTrace expressionTrace = ctx.getTraces().get(operationCall);
		if (false == expressionTrace instanceof OperationCallTrace) {
			throw new MigrationException(Type.UNSUPPORTED_OPERATION_CALL_TRACE, String.valueOf(expressionTrace));
		}
		OperationCallTrace trace = (OperationCallTrace) expressionTrace;
		switch (trace.getType()) {
		case UNDESOLVED_PARAMETER_TYPE:
		case UNDESOLVED_TARGET_TYPE:
			throw new MigrationException(Type.UNSUPPORTED_OPERATION_CALL, trace.toString());
		case STATIC_EXTENSION_REF:
			write(modelManager.getName(operationCall, trace));
			write("(");
			internalMigrateOperationCallParameters(operationCall, trace.getParamTypes());
			write(")");
			return trace.getResultType();
		case OPERATION_REF:
			if (isInfixOperation(trace)) {
				return internalMigrateInfixOperation(trace, operationCall);
			} else if (isCollectionOperation(trace)) {
				return internalMigrateCollectionOperationCall(trace, operationCall);
			}
			// else same as IMPLICIT_COLLECT_OPERATION_REF:
		case IMPLICIT_COLLECT_OPERATION_REF:
			EOperation eOperation = trace.getEOperation();
			assert eOperation != null;
			EClassifier targetQvtType = trace.getTargetType();
			// getTarget can be null for implicit call to "self" variable in xpand
			if (operationCall.getTarget() != null) {
				targetQvtType = migrateExpression(operationCall.getTarget());
				write(".");
			}
			write(modelManager.getName(operationCall, trace));
			write("(");
			if (BuiltinMetaModel.EString_SubString_StartEnd == eOperation) {
				write("1 + ");
			}
			List<EClassifier> parameterTypes = internalMigrateOperationCallParameters(operationCall, trace.getParamTypes());
			if (BuiltinMetaModel.EString_Plus_EJavaObject == eOperation) {
				assert parameterTypes.size() == 1;
				if (parameterTypes.get(0) != EcorePackage.eINSTANCE.getEString()) {
					write(".repr()");
				}
			}
			write(")");
			if (trace.getType() == OperationCallTrace.Type.OPERATION_REF) {
				return getTypedElementQvtType(eOperation);	
			} else {
				if (!BuiltinMetaModel.isParameterizedType(targetQvtType)) {
					throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Implicit collect is not supported for simple types: " + targetQvtType.toString() + "." + operationCall.getName().getValue());
				}
				convertImplicitCollectProduct(targetQvtType);
				return BuiltinMetaModelExt.getListType(BuiltinMetaModel.getInnerType(targetQvtType));
			}
		case EXTENSION_REF:
			write(modelManager.getName(operationCall, trace));
			write("(");
			if (operationCall.getTarget() != null) {
				migrateExpression(operationCall.getTarget());
			} else {
				// in case of xpand migration substituting implicit target of static extension call
				write(Environment.SELF_VARIABLE_NAME);
			}
			if (operationCall.getParams().length > 0) {
				write(", ");
				internalMigrateOperationCallParameters(operationCall, withoutFirst(trace.getParamTypes()));
			}
			write(")");
			return trace.getResultType();
		case IMPLICIT_COLLECT_EXTENSION_REF:
			assert operationCall.getTarget() != null;
			EClassifier implicitCollectTargetQvtType = migrateExpression(operationCall.getTarget());
			String iteratorName = variableDispatcher.getNextIteratorName();
			write("->collect(");
			write(iteratorName);
			write(" | ");
			write(modelManager.getName(operationCall, trace));
			write("(");
			write(iteratorName);
			if (operationCall.getParams().length > 0) {
				write(", ");
				internalMigrateOperationCallParameters(operationCall, withoutFirst(trace.getParamTypes()));
			}
			write(")");
			write(")");
			if (!BuiltinMetaModel.isParameterizedType(implicitCollectTargetQvtType)) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Implicit collect is not supported for simple types: " + implicitCollectTargetQvtType.toString() + "." + operationCall.getName().getValue());
			}
			convertImplicitCollectProduct(implicitCollectTargetQvtType);
			return trace.getResultType();
		default:
			throw new MigrationException(Type.UNSUPPORTED_OPERATION_CALL_TRACE, "Incorrect type: " + trace.getType());
		}
	}

	private List<EClassifier> withoutFirst(List<EClassifier> parameters) {
		assert parameters.size() > 0;
		return parameters.subList(1, parameters.size());
	}

	private EClassifier internalMigrateInfixOperation(OperationCallTrace trace, OperationCall operationCall) throws MigrationException {
		EOperation eOperation = trace.getEOperation();
		assert eOperation != null;
		int placeholder = getCurrentPosition();
		internalMigrateOperationCallTarget(operationCall);
		String opName = eOperation.getName();
		if (BuiltinMetaModel.Boolean_NE == eOperation) {
			write("not ", placeholder);
		} else if (BuiltinMetaModel.Int_Unary_Minus == eOperation || BuiltinMetaModel.Double_Unary_Minus == eOperation) {
			write(opName, placeholder);
		} else if (BuiltinMetaModel.Int_Minus_Int == eOperation || BuiltinMetaModel.Int_Minus_Double == eOperation || BuiltinMetaModel.Double_Minus_Int == eOperation
				|| BuiltinMetaModel.Double_Minus_Double == eOperation || BuiltinMetaModel.Int_Plus_Int == eOperation || BuiltinMetaModel.Int_Plus_Double == eOperation
				|| BuiltinMetaModel.Double_Plus_Int == eOperation || BuiltinMetaModel.Double_Plus_Double == eOperation || BuiltinMetaModel.Int_Mult_Int == eOperation
				|| BuiltinMetaModel.Int_Mult_Double == eOperation || BuiltinMetaModel.Double_Mult_Int == eOperation || BuiltinMetaModel.Double_Mult_Double == eOperation
				|| BuiltinMetaModel.Int_Div_Double == eOperation || BuiltinMetaModel.Double_Div_Double == eOperation || BuiltinMetaModel.Double_Div_Int == eOperation
				|| BuiltinMetaModel.Int_Less == eOperation || BuiltinMetaModel.Int_LessOrEqual == eOperation || BuiltinMetaModel.Int_Greater == eOperation
				|| BuiltinMetaModel.Int_GreatOrEqual == eOperation) {
			write(" ");
			write(opName);
			write(" ");
		} else if (BuiltinMetaModel.Object_EQ == eOperation) {
			write(" = ");
		} else if (BuiltinMetaModel.Object_NotEQ == eOperation) {
			write(" <> ");
		} else {
			throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Incorrect infix operation: " + opName);
		}
		List<EClassifier> parameterTypes = internalMigrateOperationCallParameters(operationCall, null);
		if (BuiltinMetaModel.Boolean_NE == eOperation || BuiltinMetaModel.Int_Unary_Minus == eOperation || BuiltinMetaModel.Double_Unary_Minus == eOperation) {
			// Enclosing with braces for "not" expression here
			addNegationBraces(placeholder);
		}
		return getTypedElementQvtType(eOperation);
	}

	private EClassifier internalMigrateOperationCallTarget(OperationCall operationCall) throws MigrationException {
		if (operationCall.getTarget() != null) {
			return migrateExpression(operationCall.getTarget());
		} else {
			// getTarget() == null if it is an implicit self operation.
			// TODO: check if it is working with XPand
			write(Environment.SELF_VARIABLE_NAME);
			return null;
		}
	}

	// TODO: check if "internalMigrateOperationCallParameters()" can be called
	// with non-null parameters here (used to transform parameter collection
	// types too)
	private EClassifier internalMigrateCollectionOperationCall(OperationCallTrace trace, OperationCall operationCall) throws MigrationException {
		EOperation eOperation = trace.getEOperation();
		assert eOperation != null;
		EClassifier targetType = trace.getTargetType();
		assert targetType != null;
		assert BuiltinMetaModel.isCollectionType(targetType);
		EClassifier elementType = BuiltinMetaModel.getInnerType(targetType);

		int expressionStartPosition = getCurrentPosition();
		if (BuiltinMetaModel.Collection_Clear != eOperation && BuiltinMetaModel.List_WithoutFirst != eOperation && BuiltinMetaModel.List_WithoutLast != eOperation) {
			EClassifier targetQvtType = internalMigrateOperationCallTarget(operationCall);
			if (targetQvtType != null && BuiltinMetaModel.isCollectionType(targetQvtType)) {
				targetType = targetQvtType;
				elementType = BuiltinMetaModel.getInnerType(targetQvtType);
			}
		}

		if (BuiltinMetaModel.Collection_Add == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->including(");
			EClassifier parameterType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			write(")");
			EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterType);
			return internalMigrateToConcreteCollection(targetType, commonSuperType, expressionStartPosition, operationStartPosition);
		} else if (BuiltinMetaModel.Collection_AddAll == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->union(");
			EClassifier parameterCollectionType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			EClassifier parameterCollectionElementType = getCollectionElementType(parameterCollectionType);
			EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterCollectionElementType);
			internalMigrateParameterCollectionToMain(parameterCollectionType, targetType);
			write(")");
			return internalMigrateToConcreteCollection(targetType, commonSuperType, expressionStartPosition, operationStartPosition);
		} else if (BuiltinMetaModel.Collection_Union == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->union(");
			EClassifier parameterCollectionType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			EClassifier parameterCollectionElementType = getCollectionElementType(parameterCollectionType);
			EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterCollectionElementType);
			internalMigrateParameterCollectionToSet(parameterCollectionType);
			write(")");
			return internalMigrateToSet(targetType, commonSuperType, expressionStartPosition, operationStartPosition);
		} else if (BuiltinMetaModel.Collection_Intersect == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->intersection(");
			EClassifier parameterCollectionType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			EClassifier parameterCollectionElementType = getCollectionElementType(parameterCollectionType);
			EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterCollectionElementType);
			internalMigrateParameterCollectionToSet(parameterCollectionType);
			write(")");
			return internalMigrateToSet(targetType, commonSuperType, expressionStartPosition, operationStartPosition);
		} else if (BuiltinMetaModel.Collection_Without == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->-(");
			EClassifier parameterCollectionType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			EClassifier parameterCollectionElementType = getCollectionElementType(parameterCollectionType);
			EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterCollectionElementType);
			internalMigrateParameterCollectionToSet(parameterCollectionType);
			write(")");
			return internalMigrateToSet(targetType, commonSuperType, expressionStartPosition, operationStartPosition);
		} else if (BuiltinMetaModel.Collection_Contains == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->includes(");
			EClassifier parameterType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			write(")");
			if (!BuiltinMetaModel.isAssignableFrom(elementType, parameterType)) {
				EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterType);
				internalMigrateTypeSelect(typeManager.getQvtFQName(commonSuperType), targetType, expressionStartPosition, operationStartPosition);
			}
			return EcorePackage.eINSTANCE.getEBoolean();
		} else if (BuiltinMetaModel.Collection_ContainsAll == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->includesAll(");
			EClassifier parameterCollectionType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			EClassifier parameterCollectionElementType = getCollectionElementType(parameterCollectionType);
			write(")");
			if (!BuiltinMetaModel.isAssignableFrom(elementType, parameterCollectionElementType)) {
				EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterCollectionElementType);
				internalMigrateTypeSelect(typeManager.getQvtFQName(commonSuperType), targetType, expressionStartPosition, operationStartPosition);
			}
			return EcorePackage.eINSTANCE.getEBoolean();
		} else if (BuiltinMetaModel.List_IndexOf == eOperation) {
			int operationStartPosition = getCurrentPosition();
			write("->indexOf(");
			EClassifier parameterType = getSingleParameterType(internalMigrateOperationCallParameters(operationCall, null));
			write(")");
			if (!BuiltinMetaModel.isAssignableFrom(elementType, parameterType)) {
				EClassifier commonSuperType = BuiltinMetaModelExt.getCommonSuperType(elementType, parameterType);
				internalMigrateTypeSelect(typeManager.getQvtFQName(commonSuperType), targetType, expressionStartPosition, operationStartPosition);
			}
			write("(", expressionStartPosition);
			write(" - 1)");
			return EcorePackage.eINSTANCE.getEInt();
		} else if (BuiltinMetaModel.Collection_Clear == eOperation) {
			EClassifier resultType;
			if (BuiltinMetaModelExt.isSetType(targetType)) {
				write("Set{}");
				resultType = BuiltinMetaModelExt.getSetType(elementType);
			} else if (BuiltinMetaModelExt.isListType(targetType) || BuiltinMetaModelExt.isOrderedSetType(targetType)) {
				write("Sequence{}");
				resultType = BuiltinMetaModelExt.getListType(elementType);
			} else {
				write("Bag{}");
				resultType = BuiltinMetaModelExt.getBagType(elementType);
			}
			// write("Bag{}");
			if (elementType != EcorePackage.eINSTANCE.getEJavaObject()) {
//				write("[");
//				write(typeManager.getQvtFQName(elementType));
//				write("]");
				write(ts(typeManager.getQvtFQName(elementType), elementType));
			}
			return resultType;
		} else if (BuiltinMetaModel.Collection_Flatten == eOperation) {
			EClassifier resultType = internalMigrateToConcreteCollection(targetType, elementType, expressionStartPosition, getCurrentPosition());
			write("->flatten()");
			if (BuiltinMetaModel.isCollectionType(elementType)) {
				elementType = BuiltinMetaModel.getInnerType(elementType);
			}
			if (BuiltinMetaModelExt.isSetType(resultType) || BuiltinMetaModelExt.isOrderedSetType(resultType)) {
				return BuiltinMetaModelExt.getSetType(elementType);
			} else if (BuiltinMetaModelExt.isListType(resultType)) {
				return BuiltinMetaModelExt.getListType(elementType);
			} else {
				return BuiltinMetaModelExt.getBagType(elementType);
			}
		} else if (BuiltinMetaModel.Collection_ToSet == eOperation) {
			return internalMigrateToSet(targetType, elementType, expressionStartPosition, getCurrentPosition());
		} else if (BuiltinMetaModel.Collection_ToList == eOperation) {
			return internalMigrateToList(targetType, elementType, expressionStartPosition);
		} else if (BuiltinMetaModel.List_Get == eOperation) {
			write("->at(");
			internalMigrateOperationCallParameters(operationCall, null);
			write(" + 1)");
			return elementType;
		} else if (BuiltinMetaModel.List_WithoutFirst == eOperation) {
			String varName = variableDispatcher.getNextVariableName();
			write("let ");
			write(varName);
			write(" : ");
			int typePosition = getCurrentPosition();
			write(" = ");
			targetType = internalMigrateOperationCallTarget(operationCall);
			assert BuiltinMetaModelExt.isListType(targetType) || BuiltinMetaModelExt.isOrderedSetType(targetType);
			boolean isOrderedSet = BuiltinMetaModelExt.isOrderedSetType(targetType);
			write((isOrderedSet ? "OrderedSet(" : "Sequence(") + typeManager.getQvtFQName(BuiltinMetaModel.getInnerType(targetType)) + ")", typePosition);
			write(" in ");
			write("if ");
			write(varName);
			write("->size() < 2 then ");
			if (isOrderedSet) {
				write("OrderedSet");
			} else {
				write("Sequence");
			}
			write("{}");
			if (elementType != EcorePackage.eINSTANCE.getEJavaObject()) {
//				write("[");
//				write(typeManager.getQvtFQName(elementType));
//				write("]");
				ts(typeManager.getQvtFQName(elementType), targetType);
			}
			write(" else ");
			write(varName);
			write("->");
			if (isOrderedSet) {
				write("subOrderedSet");
			} else {
				write("subSequence");
			}
			write("(2, ");
			write(varName);
			write("->size()) endif");
			return targetType;			
		} else if (BuiltinMetaModel.List_WithoutLast == eOperation) {
			String varName = variableDispatcher.getNextVariableName();
			write("let ");
			write(varName);
			write(" : ");
			int typePosition = getCurrentPosition();
			write(" = ");
			targetType = internalMigrateOperationCallTarget(operationCall);
			assert BuiltinMetaModelExt.isListType(targetType) || BuiltinMetaModelExt.isOrderedSetType(targetType);
			boolean isOrderedSet = BuiltinMetaModelExt.isOrderedSetType(targetType);
			write((isOrderedSet ? "OrderedSet(" : "Sequence(") + typeManager.getQvtFQName(BuiltinMetaModel.getInnerType(targetType)) + ")", typePosition);
			write(" in ");
			write("if ");
			write(varName);
			write("->size() < 2 then ");
			if (isOrderedSet) {
				write("OrderedSet");
			} else {
				write("Sequence");
			}
			write("{}");
			if (elementType != EcorePackage.eINSTANCE.getEJavaObject()) {
//				write("[");
//				write(typeManager.getQvtFQName(elementType));
//				write("]");
				ts(typeManager.getQvtFQName(elementType), targetType);
			}
			write(" else ");
			write(varName);
			write("->");
			if (isOrderedSet) {
				write("subOrderedSet");
			} else {
				write("subSequence");
			}
			write("(1, ");
			write(varName);
			write("->size() - 1) endif");
			return targetType;
		} else if (BuiltinMetaModel.List_PurgeDups == eOperation) {
			if (BuiltinMetaModelExt.isListType(targetType)) {
				write("->asOrderedSet()->asSequence()");	
			}
			return targetType;
		} else {
			/**
			 * .isEmpty() .size() .first() .last()
			 */
			assert operationCall.getParams().length == 0;
			write("->");
			write(eOperation.getName());
			write("(");
			internalMigrateOperationCallParameters(operationCall, null);
			write(")");
			if (BuiltinMetaModel.Collection_IsEmpty == eOperation) {
				return EcorePackage.eINSTANCE.getEBoolean();
			} else if (BuiltinMetaModel.Collection_Size == eOperation) {
				return EcorePackage.eINSTANCE.getEInt();
			} else if (BuiltinMetaModel.List_First == eOperation || BuiltinMetaModel.List_Last == eOperation) {
				return elementType;
			} else {
				throw new MigrationException(Type.UNSUPPORTED_COLLECTION_OPERATION, eOperation.getName());
			}
		}
	}
	
	private EClassifier getSingleParameterType(List<EClassifier> parameterTypes) {
		assert parameterTypes.size() == 1;
		return parameterTypes.get(0);
	}
	
	private EClassifier getCollectionElementType(EClassifier collectionType) {
		assert BuiltinMetaModel.isCollectionType(collectionType);
		return BuiltinMetaModel.getInnerType(collectionType);
	}

	private EClassifier internalMigrateToConcreteCollection(EClassifier collectionType, EClassifier requestedElementType, int expressionStartPosition, int expressionEndPosition) throws MigrationException {
		assert BuiltinMetaModel.isCollectionType(collectionType);
		EClassifier elementType = BuiltinMetaModel.getInnerType(collectionType);
		if (BuiltinMetaModelExt.isAbstractCollectionType(collectionType)) {
			String iteratorName = variableDispatcher.getNextIteratorName();
			StringBuilder sb = new StringBuilder();
			sb.append("->collect(");
			sb.append(iteratorName);
			sb.append(" | ");
			sb.append(iteratorName);
			if (requestedElementType != elementType) {
				sb.append(".oclAsType(");
				sb.append(typeManager.getQvtFQName(requestedElementType));
				sb.append(")");
			}
			sb.append(")");
			write(sb, expressionEndPosition);
			return BuiltinMetaModelExt.getBagType(requestedElementType);
		} else if (requestedElementType != elementType) {
			internalMigrateTypeSelect(typeManager.getQvtFQName(requestedElementType), collectionType, expressionStartPosition, expressionEndPosition);
			return BuiltinMetaModelExt.replaceCollectionElementType(collectionType, requestedElementType);
		}
		return collectionType;
	}
	
	private EClassifier internalMigrateToSet(EClassifier collectionType, EClassifier elementSuperType, int expressionStartPosition, int expressionEndPosition) throws MigrationException {
		if (!BuiltinMetaModelExt.isSetType(collectionType) && !BuiltinMetaModelExt.isOrderedSetType(collectionType)) {
			write("->asSet()", expressionEndPosition);
		}
		internalMigrateToConcreteCollection(collectionType, elementSuperType, expressionStartPosition, expressionEndPosition);
		return BuiltinMetaModelExt.isOrderedSetType(collectionType) ? BuiltinMetaModelExt.getOrderedSetType(elementSuperType) : BuiltinMetaModelExt.getSetType(elementSuperType);
	}
	
	private EClassifier internalMigrateToList(EClassifier collectionType, EClassifier elementSuperType, int placeholder) throws MigrationException {
		internalMigrateToConcreteCollection(collectionType, elementSuperType, placeholder, getCurrentPosition());
		if (!BuiltinMetaModelExt.isListType(collectionType) && !BuiltinMetaModelExt.isOrderedSetType(collectionType)) {
			write("->asSequence()");
			return BuiltinMetaModelExt.getListType(elementSuperType);
		}
		return collectionType;
	}
	
	private void internalMigrateParameterCollectionToMain(EClassifier parameterCollectionType, EClassifier mainCollectionType) {
		assert BuiltinMetaModel.isCollectionType(parameterCollectionType);
		assert BuiltinMetaModel.isCollectionType(mainCollectionType);
		if (BuiltinMetaModelExt.isListType(mainCollectionType)) {
			if (BuiltinMetaModelExt.isSetType(parameterCollectionType) || BuiltinMetaModelExt.isOrderedSetType(parameterCollectionType)) {
				write("->asSequence()");
			} else if (BuiltinMetaModelExt.isAbstractCollectionType(parameterCollectionType)) {
				internalMigrateCollectionToBag(null);
				write("->asSequence()");
			}
		} else if (BuiltinMetaModelExt.isSetType(mainCollectionType) || BuiltinMetaModelExt.isOrderedSetType(mainCollectionType)) {
			if (BuiltinMetaModelExt.isListType(parameterCollectionType) || BuiltinMetaModelExt.isBagType(parameterCollectionType)) {
				write("->asSet()");
			} else if (BuiltinMetaModelExt.isAbstractCollectionType(parameterCollectionType)) {
				internalMigrateCollectionToBag(null);
				write("->asSet()");
			}
		} else { //For Bag/AbstractCollection (should be transformed to Bag)
			if (BuiltinMetaModelExt.isListType(parameterCollectionType)) {
				write("->asBag()");
			} else if (BuiltinMetaModelExt.isAbstractCollectionType(parameterCollectionType)) {
				internalMigrateCollectionToBag(null);
			}
		}
	}

	private void internalMigrateParameterCollectionToSet(EClassifier parameterCollectionType) {
		assert BuiltinMetaModel.isCollectionType(parameterCollectionType);
		if (BuiltinMetaModelExt.isListType(parameterCollectionType) || BuiltinMetaModelExt.isBagType(parameterCollectionType)) {
			write("->asSet()");
		} else if (BuiltinMetaModelExt.isAbstractCollectionType(parameterCollectionType)) {
			internalMigrateCollectionToBag(null);
			write("->asSet()");
		}
	}

	private boolean isCollectionOperation(OperationCallTrace trace) {
		EOperation eOperation = trace.getEOperation();
		assert eOperation != null;
		return collectionOperations.contains(eOperation);
	}
	
	private EClassifier getTypedElementQvtType(ETypedElement typedElement) {
		EClassifier type = typedElement.getEType();
		if (typedElement.isMany()) {
			if (typedElement.isOrdered() && typedElement.isUnique()) {
				type = BuiltinMetaModelExt.getOrderedSetType(type);
			} else if (typedElement.isOrdered()) {
				type = BuiltinMetaModelExt.getListType(type);
			} else if (typedElement.isUnique()) {
				type = BuiltinMetaModelExt.getSetType(type);
			} else {
				type = BuiltinMetaModelExt.getBagType(type);
			}
		}
		return type;
	}

	private void convertImplicitCollectProduct(EClassifier targetType) {
		assert targetType != null;
		if (!BuiltinMetaModelExt.isListType(targetType) && !BuiltinMetaModelExt.isOrderedSetType(targetType)) {
			write("->asSequence()");
		}
	}

	private List<EClassifier> internalMigrateOperationCallParameters(OperationCall operationCall, List<EClassifier> expectedParameterTypes) throws MigrationException {
		assert expectedParameterTypes == null || operationCall.getParams().length == expectedParameterTypes.size();
		List<EClassifier> parameterTypes = new ArrayList<EClassifier>();
		for (int i = 0; i < operationCall.getParams().length; i++) {
			if (i > 0) {
				write(", ");
			}
			parameterTypes.add(migrateExpression(operationCall.getParams()[i]));
			if (expectedParameterTypes != null) {
				internalConvertTypes(parameterTypes.get(i), expectedParameterTypes.get(i));
			}
		}
		return parameterTypes;
	}

	private boolean isInfixOperation(OperationCallTrace trace) {
		EOperation eOperation = trace.getEOperation();
		assert eOperation != null;
		return infixOperations.contains(eOperation);
	}

	private EClassifier migrateFeatureCall(FeatureCall featureCall) throws MigrationException {
		ExpressionAnalyzeTrace expressionTrace = ctx.getTraces().get(featureCall);
		if (false == expressionTrace instanceof FeatureCallTrace) {
			throw new MigrationException(Type.UNSUPPORTED_FEATURE_CALL_TRACE, String.valueOf(expressionTrace));
		}
		FeatureCallTrace trace = (FeatureCallTrace) expressionTrace;
		switch (trace.getType()) {
		case ENUM_LITERAL_REF:
			EEnumLiteral enumLiteral = trace.getEnumLiteral();
			assert enumLiteral != null;
			write(typeManager.getQvtFQName(enumLiteral));
			return enumLiteral.getEEnum();
		case ENV_VAR_REF:
			write(modelManager.getName(featureCall, trace));
			EClassifier variableType = getEnvVariableType(featureCall.getName().getValue());
			return variableType != null ? variableType : trace.getResultType();
		case UNDESOLVED_TARGET_TYPE:
		case UNSUPPORTED_CLASSIFIER_REF:
			throw new MigrationException(Type.UNSUPPORTED_FEATURE_CALL, trace.toString());
		}
		EClassifier targetType = trace.getTargetType();
		// featureCall.getTarget() == null for FeatureCall of implicit variable
		// feature
		if (featureCall.getTarget() != null) {
			migrateExpression(featureCall.getTarget());
			// Skipping EnumLiteral.value/EnumLiteral.literal features
			if (targetType instanceof EEnum && trace.getType() == FeatureCallTrace.Type.FEATURE_REF) {
				if (skipEnumLiteralFeature(trace.getFeature())) {
					return targetType;
				} else if (addEnumLiteralStringRepresentation(trace.getFeature())) {
					write(".repr()");
					return EcorePackage.eINSTANCE.getEString();
				}
			}
			write(".");
		} else {
			if (targetType instanceof EEnum && trace.getType() == FeatureCallTrace.Type.FEATURE_REF) {
				if (skipEnumLiteralFeature(trace.getFeature())) {
					write(Environment.SELF_VARIABLE_NAME);
					return targetType;
				} else if (addEnumLiteralStringRepresentation(trace.getFeature())) {
					write(Environment.SELF_VARIABLE_NAME);
					write(".repr()");
					return EcorePackage.eINSTANCE.getEString();
				}
			}
		}
		write(modelManager.getName(featureCall, trace));
		assert targetType != null;
		switch (trace.getType()) {
		case FEATURE_REF:
			if (BuiltinMetaModel.isParameterizedType(targetType)) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Attribute call is not supported for the collection types: " + targetType.toString() + "." + featureCall.getName().getValue());
			}
			return getTypedElementQvtType(trace.getFeature());
		case IMPLICIT_COLLECT_FEATURE_REF:
			if (!BuiltinMetaModel.isParameterizedType(targetType)) {
				throw new MigrationException(Type.UNSUPPORTED_EXPRESSION, "Implicit collect is not supported for simple types: " + targetType.toString() + "." + featureCall.getName().getValue());
			}
			convertImplicitCollectProduct(targetType);
			return BuiltinMetaModelExt.getListType(BuiltinMetaModel.getInnerType(targetType));
		default:
			throw new MigrationException(Type.UNSUPPORTED_FEATURE_CALL_TRACE, "Incorrect type: " + trace.getType());
		}
	}
	
	private boolean addEnumLiteralStringRepresentation(EStructuralFeature feature) {
		return EcorePackage.eINSTANCE.getENamedElement_Name() == feature;
	}
	
	private boolean skipEnumLiteralFeature(EStructuralFeature feature) {
		return EcorePackage.eINSTANCE.getEEnumLiteral_Value() == feature || EcorePackage.eINSTANCE.getEEnumLiteral_Literal() == feature;
	}

	private void markReturnPosition() {
		returnPosition = getCurrentPosition();
	}

	private int getCurrentPosition() {
		return output.length();
	}

	private void write(CharSequence cs, int index) {
		output.insert(index, cs);
	}

	private void write(CharSequence cs) {
		output.append(cs);
	}
	
	private void pushContextWithVariable(String varName, EClassifier varType) {
		qvtContexts.push(qvtContexts.peek().cloneWithVariable(varName, varType));
	}
	
	private EClassifier getEnvVariableType(String varName) {
		return qvtContexts.peek().getVariableType(varName);
	}
	
	/**
	 * This class was intended to store QVT env. variable types during
	 * expression migration. It is necessary for more intelligent QVT
	 * (collections) types transformation during expression migration.
	 * 
	 * Only following methods can be used by clients:
	 * {@link ExecutionContext#cloneWithVariable(java.util.Collection)}
	 * {@link ExecutionContext#cloneWithVariable(org.eclipse.gmf.internal.xpand.expression.Variable...)}
	 * {@link ExecutionContext#cloneContext()}
	 */
	private class QvtExecutionContext extends ExecutionContextImpl {

		public QvtExecutionContext() {
			super((ResourceManager) null);
		}

		private QvtExecutionContext(QvtExecutionContext original) {
			super(original);
		}

		public QvtExecutionContext cloneWithVariable(String name, EClassifier type) {
			return (QvtExecutionContext) super.cloneWithVariable(new Variable(name, type));
		}

		public EClassifier getVariableType(String name) {
			Variable var = getVariable(name);
			if (var != null) {
				return (EClassifier) var.getValue();
			}
			return null;
		}

		@Override
		public QvtExecutionContext cloneContext() {
			return new QvtExecutionContext(this);
		}

		@Override
		public EClassifier[] findTypesForPrefix(String prefix) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setContextClassLoader(ClassLoadContext classLoadContext) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<?> loadClass(String value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public EClassifier getTypeForName(String name) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ExecutionContext cloneWithResource(ResourceMarker ns) {
			throw new UnsupportedOperationException();
		}

		@Override
		public ResourceMarker currentResource() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<Extension> getAllExtensions() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Extension getExtension(String functionName, EClassifier[] parameterTypes) {
			throw new UnsupportedOperationException();
		}

		@Override
		public EvaluationListener getEvaluationListener() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setEvaluationListener(EvaluationListener listener) {
			throw new UnsupportedOperationException();
		}

	}

}