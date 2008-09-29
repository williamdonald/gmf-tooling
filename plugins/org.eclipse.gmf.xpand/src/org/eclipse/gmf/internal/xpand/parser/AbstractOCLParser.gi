-- Copy of OCL CST factory methods from AbstractOCLParser
-- Only required (reused) methods were copied, hence private visibility to make sure we use all them
-- XXX ask C.Damus to split factory out of AbstractOCLParser.java 
$Headers
/.
	private OperationCS createOperationCS(
			PathNameCS pathNameCS,
			SimpleNameCS simpleNameCS,
			EList<VariableCS> list,
			TypeCS typeCS) {
		OperationCS result = CSTFactory.eINSTANCE.createOperationCS();
		result.setPathNameCS(pathNameCS);
		result.setSimpleNameCS(simpleNameCS);
		result.getParameters().addAll(list);
		result.setTypeCS(typeCS);
		return result;
	}

	private OperationCS createOperationCS(
			String simpleName,
			EList<VariableCS> list,
			TypeCS typeCS) {
		return createOperationCS(
				null,
				createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, simpleName),
				list,
				typeCS);
	}

	private OperationCallExpCS createOperationCallExpCS(
			OCLExpressionCS oclExpressionCS,
			SimpleNameCS simpleNameCS,
			IsMarkedPreCS isMarkedPreCS,
			EList<OCLExpressionCS> arguments) {
		OperationCallExpCS result = CSTFactory.eINSTANCE.createOperationCallExpCS();
		result.setSource(oclExpressionCS);
		result.setSimpleNameCS(simpleNameCS);
		result.setIsMarkedPreCS(isMarkedPreCS);
		result.getArguments().addAll(arguments);
		return result;
	}

	private OperationCallExpCS createOperationCallExpCS(
			OCLExpressionCS oclExpressionCS,
			SimpleNameCS simpleNameCS,
			EList<OCLExpressionCS> arguments) {
		return createOperationCallExpCS(oclExpressionCS, simpleNameCS,
				createIsMarkedPreCS(false), arguments);
	}

	private OperationCallExpCS createOperationCallExpCS(
			SimpleNameCS simpleNameCS,
			IsMarkedPreCS isMarkedPreCS,
			EList<OCLExpressionCS> arguments) {
		return createOperationCallExpCS(null, simpleNameCS, isMarkedPreCS, arguments);
	}

	private OperationCallExpCS createOperationCallExpCS(
			SimpleNameCS simpleNameCS,
			IsMarkedPreCS isMarkedPreCS,
			StateExpCS stateExpCS) {
		OperationCallExpCS result = CSTFactory.eINSTANCE.createOperationCallExpCS();
		result.setSimpleNameCS(simpleNameCS);
		result.setIsMarkedPreCS(isMarkedPreCS);
		result.getArguments().add(stateExpCS);
		return result;
	}
	
	private StateExpCS createStateExpCS(PathNameCS pathName) {
		StateExpCS result = CSTFactory.eINSTANCE.createStateExpCS();
		result.getSequenceOfNames().addAll(pathName.getSequenceOfNames());
		return result;
	}

	private VariableExpCS createVariableExpCS(
			SimpleNameCS simpleNameCS,
			EList<OCLExpressionCS> arguments,
			IsMarkedPreCS isMarkedPreCS) {
		VariableExpCS result = CSTFactory.eINSTANCE.createVariableExpCS();
		result.setSimpleNameCS(simpleNameCS);
		result.getArguments().addAll(arguments);
		result.setIsMarkedPreCS(isMarkedPreCS);
		return result;
	}

	private SimpleNameCS createSimpleNameCS(
			SimpleTypeEnum type,
			String value) {
		SimpleNameCS result = CSTFactory.eINSTANCE.createSimpleNameCS();
		result.setType(type);
		result.setValue(unquote(value));
		return result;
	}

	private PrimitiveTypeCS createPrimitiveTypeCS(
			SimpleTypeEnum type,
			String value) {
		PrimitiveTypeCS result = CSTFactory.eINSTANCE.createPrimitiveTypeCS();
		result.setType(type);
		result.setValue(value);
		return result;
	}

	private PathNameCS createPathNameCS(String pathName) {
		PathNameCS result = CSTFactory.eINSTANCE.createPathNameCS();
		result.getSequenceOfNames().add(unquote(pathName));
		return result;
	}
	
	private PathNameCS extendPathNameCS(PathNameCS path, String name) {
		path.getSequenceOfNames().add(unquote(name));
		return path;
	}

	private PathNameCS createPathNameCS() {
		return CSTFactory.eINSTANCE.createPathNameCS();
	}

	private EnumLiteralExpCS createEnumLiteralExpCS(
			PathNameCS pathNameCS,
			SimpleNameCS simpleNameCS) {
		EnumLiteralExpCS result = CSTFactory.eINSTANCE.createEnumLiteralExpCS();
		result.setPathNameCS(pathNameCS);
		result.setSimpleNameCS(simpleNameCS);
		return result;
	}

	private EnumLiteralExpCS createEnumLiteralExpCS(
			PathNameCS pathNameCS,
			String simpleName) {
		return createEnumLiteralExpCS(pathNameCS,
			createSimpleNameCS(SimpleTypeEnum.IDENTIFIER_LITERAL, simpleName));
	}

	private CollectionLiteralExpCS createCollectionLiteralExpCS(
			CollectionTypeIdentifierEnum type,
			EList<CollectionLiteralPartCS> collectionLiteralParts) {
		CollectionLiteralExpCS result = CSTFactory.eINSTANCE.createCollectionLiteralExpCS();
		result.setCollectionType(type);
		result.getCollectionLiteralParts().addAll(collectionLiteralParts);
		return result;
	}

	private CollectionLiteralPartCS createCollectionLiteralPartCS(OCLExpressionCS oclExpressionCS) {
		CollectionLiteralPartCS result = CSTFactory.eINSTANCE.createCollectionLiteralPartCS();
		result.setExpressionCS(oclExpressionCS);
		return result;
	}

	private CollectionRangeCS createCollectionRangeCS(
			OCLExpressionCS oclExpressionCS,
			OCLExpressionCS lastOCLExpressionCS) {
		CollectionRangeCS result = CSTFactory.eINSTANCE.createCollectionRangeCS();
		result.setExpressionCS(oclExpressionCS);
		result.setLastExpressionCS(lastOCLExpressionCS);
		return result;
	}

	private IntegerLiteralExpCS createRangeStart(
			String integerDotDot,
			boolean isNegative) {
		String intToken = integerDotDot.substring(0, integerDotDot.indexOf('.'));
		int intValue = Integer.parseInt(intToken);
		if (isNegative) {
			intValue = -intValue;
		}
		
		IntegerLiteralExpCS result = CSTFactory.eINSTANCE.createIntegerLiteralExpCS();
		result.setIntegerSymbol(new Integer(intValue));
		result.setSymbol(Integer.toString(intValue));
		
		return result;
	}

	private TupleLiteralExpCS createTupleLiteralExpCS(EList<VariableCS> variables) {
		TupleLiteralExpCS result = CSTFactory.eINSTANCE.createTupleLiteralExpCS();
		result.getVariables().addAll(variables);
		return result;
	}

	private IntegerLiteralExpCS createIntegerLiteralExpCS(String string) {
		IntegerLiteralExpCS result = CSTFactory.eINSTANCE.createIntegerLiteralExpCS();
		result.setSymbol(string);
		result.setIntegerSymbol(Integer.valueOf(string));
		return result;
	}

	@SuppressWarnings("nls")
	private UnlimitedNaturalLiteralExpCS createUnlimitedNaturalLiteralExpCS(String string) {
		UnlimitedNaturalLiteralExpCS result = CSTFactory.eINSTANCE.createUnlimitedNaturalLiteralExpCS();
		result.setSymbol(string);
		if ("*".equals(string)) {
			result.setIntegerSymbol(-1);
		} else {
			result.setIntegerSymbol(Integer.valueOf(string));
		}
		return result;
	}

	private RealLiteralExpCS createRealLiteralExpCS(String string) {
		RealLiteralExpCS result = CSTFactory.eINSTANCE.createRealLiteralExpCS();
		result.setSymbol(string);
		result.setRealSymbol(Double.valueOf(string));
		return result;
	}

	private StringLiteralExpCS createStringLiteralExpCS(String string) {
		StringLiteralExpCS result = CSTFactory.eINSTANCE.createStringLiteralExpCS();
		result.setSymbol(string);
		result.setStringSymbol(string);
		return result;
	}

	private BooleanLiteralExpCS createBooleanLiteralExpCS(String string) {
		BooleanLiteralExpCS result = CSTFactory.eINSTANCE.createBooleanLiteralExpCS();
		result.setSymbol(string);
		result.setBooleanSymbol(Boolean.valueOf(string));
		return result;
	}

	private NullLiteralExpCS createNullLiteralExpCS(String string) {
		NullLiteralExpCS result = CSTFactory.eINSTANCE.createNullLiteralExpCS();
		result.setSymbol(string);
		return result;
	}

	private InvalidLiteralExpCS createInvalidLiteralExpCS(String string) {
		InvalidLiteralExpCS result = CSTFactory.eINSTANCE.createInvalidLiteralExpCS();
		result.setSymbol(string);
		return result;
	}

	private IteratorExpCS createIteratorExpCS(
			SimpleNameCS simpleNameCS,
			VariableCS variable1,
			VariableCS variable2,
			OCLExpressionCS oclExpressionCS) {
		IteratorExpCS result = CSTFactory.eINSTANCE.createIteratorExpCS();
		result.setSimpleNameCS(simpleNameCS);
		result.setVariable1(variable1);
		result.setVariable2(variable2);
		result.setBody(oclExpressionCS);
		return result;
	}

	private IterateExpCS createIterateExpCS(
			SimpleNameCS simpleNameCS,
			VariableCS variable1,
			VariableCS variable2,
			OCLExpressionCS oclExpressionCS) {
		IterateExpCS result = CSTFactory.eINSTANCE.createIterateExpCS();
		result.setSimpleNameCS(simpleNameCS);
		result.setVariable1(variable1);
		result.setVariable2(variable2);
		result.setBody(oclExpressionCS);
		return result;
	}

	private VariableCS createVariableCS(
			String varName,
			TypeCS typeCS,
			OCLExpressionCS oclExpressionCS) {
		VariableCS result = CSTFactory.eINSTANCE.createVariableCS();
		result.setName(unquote(varName));
		result.setTypeCS(typeCS);
		result.setInitExpression(oclExpressionCS);
		return result;
	}

	private CollectionTypeCS createCollectionTypeCS(
			CollectionTypeIdentifierEnum collectionType,
			TypeCS typeCS) {
		CollectionTypeCS result = CSTFactory.eINSTANCE.createCollectionTypeCS();
		result.setCollectionTypeIdentifier(collectionType);
		result.setTypeCS(typeCS);
		return result;
	}

	private TupleTypeCS createTupleTypeCS(EList<VariableCS> variables) {
		TupleTypeCS result = CSTFactory.eINSTANCE.createTupleTypeCS();
		result.getVariables().addAll(variables);
		return result;
	}

	private FeatureCallExpCS createFeatureCallExpCS(
			SimpleNameCS simpleNameCS,
			EList<OCLExpressionCS> arguments,
			IsMarkedPreCS isMarkedPreCS) {
		FeatureCallExpCS result = CSTFactory.eINSTANCE.createFeatureCallExpCS();
		result.setSimpleNameCS(simpleNameCS);
		result.getArguments().addAll(arguments);
		result.setIsMarkedPreCS(isMarkedPreCS);
		return result;
	}

	private IsMarkedPreCS createIsMarkedPreCS(boolean isMarkedPre) {
		IsMarkedPreCS result = CSTFactory.eINSTANCE.createIsMarkedPreCS();
		result.setPre(isMarkedPre);
		return result;
	}

	private LetExpCS createLetExpCS(
			EList<VariableCS> variables,
			OCLExpressionCS oclExpressionCS) {
		LetExpCS result = CSTFactory.eINSTANCE.createLetExpCS();
		result.getVariables().addAll(variables);
		result.setInExpression(oclExpressionCS);
		return result;
	}

	private IfExpCS createIfExpCS(
			OCLExpressionCS condition,
			OCLExpressionCS thenExpression,
			OCLExpressionCS elseExpression) {
		IfExpCS result = CSTFactory.eINSTANCE.createIfExpCS();
		result.setCondition(condition);
		result.setThenExpression(thenExpression);
		result.setElseExpression(elseExpression);
		return result;
	}

	private MessageExpCS createMessageExpCS(
			boolean hasSent,
			SimpleNameCS simpleNameCS,
			EList<OCLMessageArgCS> oclMessageArgs) {
		MessageExpCS result = CSTFactory.eINSTANCE.createMessageExpCS();
		result.setKind(hasSent ? MessageExpKind.HAS_SENT_LITERAL : MessageExpKind.SENT_LITERAL);
		result.setSimpleNameCS(simpleNameCS);
		result.getArguments().addAll(oclMessageArgs);
		return result;
	}

	private OCLMessageArgCS createOCLMessageArgCS(
			TypeCS typeCS,
			OCLExpressionCS oclExpressionCS) {
		OCLMessageArgCS result = CSTFactory.eINSTANCE.createOCLMessageArgCS();
		result.setTypeCS(typeCS);
		result.setExpression(oclExpressionCS);
		return result;
	}
./
$End