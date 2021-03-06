--
-- Copyright (c) 2006, 2007 Borland Software Corp.
-- 
-- All rights reserved. This program and the accompanying materials
-- are made available under the terms of the Eclipse Public License v1.0
-- which accompanies this distribution, and is available at
-- http://www.eclipse.org/legal/epl-v10.html
--
-- Contributors:
--    Artem Tikhomirov (Borland)
--

%options fp=ExpressionParser,prefix=TK_
%options programming_language=java
%options package=org.eclipse.gmf.internal.xpand.expression.parser
%options template=dtParserTemplateD.g
%options ast_type=Expression
%options import_terminals=ExpressionLexer.g
%options lalr=2

$Notice
/./*******************************************************************************
 * Copyright (c) 2006, 2007 Eclipse.org
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/./
$End

$Globals
	/.
	import org.eclipse.gmf.internal.xpand.expression.ast.*;

	import java.util.Collections;
	./
$End

$Headers
	/.
		private final ExpressionFactory factory;
	./
$End

$Define
	$initialization_code /.factory = new ExpressionFactory(lexStream.getFileName());./
$End

$Start
	expression
$End

$Terminals

	IDENT  STRING  INT_CONST  REAL_CONST

	let switch implies new false true null default case

	Collection List Set

	typeSelect collect select reject exists notExists forAll

	QUESTION_MARK ::= "?"
	DCOLON ::= "::"
	COLON ::= ":"
	LPAREN ::= "("
	RPAREN ::= ")"
	LCURLY ::= "{"
	RCURLY ::= "}"
	LSQUARE ::= "["
	RSQUARE ::= "]"
	ARROW   ::= "->"
	NOT ::= "!"
	AND ::= "&&"
	OR  ::= "||"

	ASSIGN ::= "="
	EQ ::= "=="
	NE ::= "!="
	GE ::= ">="
	LE ::= "<="
	GT ::= ">"
	LT ::= "<"

	PLUS ::= "+"
	MINUS ::= "-"

	MULTI ::= "*"
	DIV   ::= "/"

	DOT   ::= "."
	COMMA ::= ","

	BAR ::= "|"
$End

$Rules
	expression -> letExpression

	letExpression ::= 'let' IDENT ASSIGN castedExpression COLON castedExpression
		/.$BeginJava
			setResult(factory.createLetExpression(getLeftIToken(),getRhsIToken(2),(Expression) getRhsSym(4), (Expression) getRhsSym(6)));
		$EndJava./
	letExpression -> castedExpression

	castedExpression ::= LPAREN type RPAREN infixExpression
		/.$BeginJava
			setResult(factory.createCast(getLeftIToken(), (Identifier) getRhsSym(2),(Expression) getRhsSym(4)));
		$EndJava./
	castedExpression -> chainExpression

	chainExpression -> ifExpression
	chainExpression ::= ifExpression ARROW chainExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(1);
			Expression right = (Expression) getRhsSym(3);
			if (right instanceof ChainExpression) {
				ChainExpression rchain = (ChainExpression) right;
				Expression newFirst = factory.createChainExpression(e, rchain.getFirst()); 
				setResult(factory.createChainExpression(newFirst, rchain.getNext()));
			} else {
				setResult(factory.createChainExpression(e, right));
			}
		$EndJava./

	ifExpression -> switchExpression
	ifExpression ::= switchExpression QUESTION_MARK switchExpression COLON switchExpression
		/.$BeginJava
			setResult(factory.createIf((Expression) getRhsSym(1),(Expression) getRhsSym(3), (Expression) getRhsSym(5)));
		$EndJava./

	--------------------------
	-- unlike original xpand, do not allow switch without expression
	-- FIXME support switch without expression
	switchExpression ::= 'switch' LPAREN expression RPAREN LCURLY switchCases 'default' COLON orExpression RCURLY
		/.$BeginJava
			setResult(factory.createSwitchExpression(getLeftIToken(),getRightIToken(), (Expression) getRhsSym(3), (List) getRhsSym(6), (Expression) getRhsSym(9)));
		$EndJava./
	switchExpression -> orExpression

	switchCases ::= $empty
		/.$BeginJava
			setResult(Collections.emptyList());
		$EndJava./
	switchCases ::= 'case' orExpression COLON orExpression switchCases
		/.$BeginJava
			LinkedList r = new LinkedList();
			r.add(factory.createCase(getLeftIToken(), (Expression) getRhsSym(2), (Expression) getRhsSym(4)));
			r.addAll((List) getRhsSym(5));
			setResult(r);
		$EndJava./

	orExpression -> andExpression
	orExpression ::= andExpression OR orExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(1);
			Expression r = (Expression) getRhsSym(3);
			setResult(factory.createBooleanOperation(e.getStart(),r.getEnd(),e.getLine(),e.getStartOffset(),r.getEndOffset(),getRhsIToken(2),e,r));
		$EndJava./

	andExpression -> impliesExpression
	andExpression ::= impliesExpression AND andExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(1);
			Expression r = (Expression) getRhsSym(3);
			setResult(factory.createBooleanOperation(e.getStart(),r.getEnd(),e.getLine(),e.getStartOffset(),r.getEndOffset(),getRhsIToken(2),e,r));
		$EndJava./

	impliesExpression -> relationalExpression
	impliesExpression ::= relationalExpression 'implies' relationalExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(1);
			Expression r = (Expression) getRhsSym(3);
			setResult(factory.createBooleanOperation(e.getStart(),r.getEnd(),e.getLine(),e.getStartOffset(),e.getEndOffset(),getRhsIToken(2),e,r));
		$EndJava./

	-- unlike xpand, do not allow more than one relational operator, i.e. a>b == b<c != d is not allowed
	relationalExpression -> additiveExpression
	relationalExpression ::= additiveExpression relationalOperator additiveExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(1);
			IToken t = (IToken) getRhsSym(2);
			Expression r = (Expression) getRhsSym(3);
			setResult(factory.createOperationCall(e.getStart(),r.getEnd(),e.getLine(),e.getStartOffset(),e.getEndOffset(),t,e,Collections.singletonList(r)));
		$EndJava./

	relationalOperator ::= EQ
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	relationalOperator ::= NE
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	relationalOperator ::= GE
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	relationalOperator ::= LE
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	relationalOperator ::= GT
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	relationalOperator ::= LT
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./

	additiveExpression -> multiplicativeExpression
	additiveExpression ::= additiveExpression additiveOperator multiplicativeExpression 
		/.$BeginJava
			Expression e = (Expression) getRhsSym(1);
			IToken t = (IToken) getRhsSym(2);
			Expression r = (Expression) getRhsSym(3);
			setResult(factory.createOperationCall(e.getStart(),r.getEnd(),e.getLine(),e.getStartOffset(),e.getEndOffset(),t,e,Collections.singletonList(r)));
		$EndJava./

	additiveOperator ::= PLUS
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	additiveOperator ::= MINUS
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./

	multiplicativeExpression ::= unaryExpression 
	multiplicativeExpression ::= unaryExpression multiplicativeOperator multiplicativeExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(1);
			IToken t = (IToken) getRhsSym(2);
			Expression r = (Expression) getRhsSym(3);
			setResult(factory.createOperationCall(e.getStart(),r.getEnd(),e.getLine(),e.getStartOffset(),e.getEndOffset(),t,e,Collections.singletonList(r)));
		$EndJava./

	multiplicativeOperator ::= MULTI
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	multiplicativeOperator ::= DIV 
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./

	unaryExpression -> infixExpression
	unaryExpression ::= NOT infixExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(2);
			setResult(factory.createOperationCall(getLeftIToken().getColumn(),e.getEnd(),getLeftIToken().getLine(),getLeftIToken().getStartOffset(),e.getEndOffset(),getLeftIToken(),e,Collections.EMPTY_LIST));
		$EndJava./
	unaryExpression ::= MINUS infixExpression
		/.$BeginJava
			Expression e = (Expression) getRhsSym(2);
			setResult(factory.createOperationCall(getLeftIToken().getColumn(),e.getEnd(),getLeftIToken().getLine(),getLeftIToken().getStartOffset(),e.getEndOffset(),getLeftIToken(),e,Collections.EMPTY_LIST));
		$EndJava./

	infixExpressionSuffix ::= DOT featureCall
		/.$BeginJava
			setResult(getRhsSym(2));
		$EndJava./
	infixExpressionSuffix ::= DOT featureCall infixExpressionSuffix
		/.$BeginJava
			final FeatureCall op = (FeatureCall) getRhsSym(3);
			FeatureCall fc = op;
			while (fc.getTarget() != null) {
				fc = (FeatureCall) fc.getTarget();
			}
			fc.setTarget((FeatureCall) getRhsSym(2));
			setResult(op);
		$EndJava./

	infixExpression -> primaryExpression
	infixExpression ::= primaryExpression infixExpressionSuffix
		/.$BeginJava
			final FeatureCall op = (FeatureCall) getRhsSym(2);
			FeatureCall fc = op;
			while (fc.getTarget() != null) {
				fc = (FeatureCall) fc.getTarget();
			}
			fc.setTarget((Expression) getRhsSym(1));
			setResult(op);
		$EndJava./

	primaryExpression ::= STRING 
		/.$BeginJava
			setResult(factory.createStringLiteral(getLeftIToken()));
		$EndJava./
	primaryExpression -> booleanLiteral 
	primaryExpression -> intLiteral 
	primaryExpression -> realLiteral
	primaryExpression -> nullLiteral
	primaryExpression -> listLiteral
	primaryExpression -> constructorCall 
	primaryExpression -> featureCall 
	primaryExpression ::= LPAREN expression RPAREN
		/.$BeginJava
			Expression expr = (Expression) getRhsSym(2);
			expr.setStartOffset(getLeftIToken().getStartOffset());
			expr.setEndOffset(getRightIToken().getEndOffset());	
			setResult(expr);
		$EndJava./

	featureCall ::= IDENT LPAREN parameterList RPAREN 
		/.$BeginJava
			setResult(factory.createOperationCall(getRightIToken(),getLeftIToken(),null, (List<Expression>) getRhsSym(3)));
		$EndJava./
	featureCall ::= IDENT LPAREN RPAREN 
		/.$BeginJava
			setResult(factory.createOperationCall(getRightIToken(), getLeftIToken(), null, Collections.EMPTY_LIST));
		$EndJava./
	featureCall ::= type 
		/.$BeginJava
			setResult(factory.createFeatureCall((Identifier) getRhsSym(1),null));
		$EndJava./
	featureCall -> collectionExpression

	listLiteral ::= LCURLY parameterList RCURLY 
		/.$BeginJava
			setResult(factory.createListLiteral(getLeftIToken(),getRightIToken(), (List<Expression>) getRhsSym(2)));
		$EndJava./
	listLiteral ::= LCURLY RCURLY 
		/.$BeginJava
			setResult(factory.createListLiteral(getLeftIToken(), getRightIToken(), Collections.EMPTY_LIST));
		$EndJava./

	constructorCall ::= 'new' simpleType
		/.$BeginJava
			setResult(factory.createConstructorCall(getLeftIToken(), (Identifier) getRhsSym(2)));
		$EndJava./

	booleanLiteral ::= 'false'
		/.$BeginJava
			setResult(factory.createBooleanLiteral(getRhsIToken(1)));
		$EndJava./
	booleanLiteral ::= 'true'
		/.$BeginJava
			setResult(factory.createBooleanLiteral(getRhsIToken(1)));
		$EndJava./

	nullLiteral ::= 'null'
		/.$BeginJava
			setResult(factory.createNullLiteral(getRhsIToken(1)));
		$EndJava./

	intLiteral ::= INT_CONST
		/.$BeginJava
			setResult(factory.createIntegerLiteral(getRhsIToken(1)));
		$EndJava./

	realLiteral ::= REAL_CONST
		/.$BeginJava
			setResult(factory.createRealLiteral(getRhsIToken(1)));
		$EndJava./

	collectionExpression ::= 'typeSelect' LPAREN type RPAREN
		/.$BeginJava
			setResult(factory.createTypeSelectExpression(getRhsIToken(1), getRightIToken(), (Identifier) getRhsSym(3),null));
		$EndJava./
	collectionExpression ::= collectionExpressionName LPAREN IDENT BAR expression RPAREN 
		/.$BeginJava
			setResult(factory.createCollectionExpression((IToken) getRhsSym(1), getRightIToken(), getRhsIToken(3), (Expression) getRhsSym(5),null));
		$EndJava./
	collectionExpression ::= collectionExpressionName LPAREN expression RPAREN 
		/.$BeginJava
			setResult(factory.createCollectionExpression((IToken) getRhsSym(1), getRightIToken(), null, (Expression) getRhsSym(3),null));
		$EndJava./

	collectionExpressionName ::= 'collect'
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	collectionExpressionName ::= 'select'
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	collectionExpressionName ::= 'reject'
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	collectionExpressionName ::= 'exists'
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	collectionExpressionName ::= 'notExists'
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./
	collectionExpressionName ::= 'forAll'
		/.$BeginJava
			setResult(getLeftIToken());
		$EndJava./

-- helper

	declaredParameterListOpt ::= $empty
		/.$BeginJava
			setResult(Collections.EMPTY_LIST);
		$EndJava./
	declaredParameterListOpt -> declaredParameterList

	declaredParameterList ::= type IDENT
		/.$BeginJava
			Identifier id = factory.createIdentifier(getRightIToken());
			DeclaredParameter p = factory.createDeclaredParameter((Identifier) getRhsSym(1), id);
			setResult(Collections.singletonList(p));
		$EndJava./
	declaredParameterList ::= type IDENT COMMA declaredParameterList
		/.$BeginJava
			LinkedList r = new LinkedList();
			Identifier id = factory.createIdentifier(getRhsIToken(2));
			DeclaredParameter p = factory.createDeclaredParameter((Identifier) getRhsSym(1), id);
			r.add(p);
			r.addAll((List) getRhsSym(4));
			setResult(r);
		$EndJava./

	parameterList ::= expression
		/.$BeginJava
			setResult(Collections.singletonList((Expression) getRhsSym(1)));
		$EndJava./
	parameterList ::= expression COMMA parameterList
		/.$BeginJava
			LinkedList r = new LinkedList();
			r.add(getRhsSym(1));
			r.addAll((List) getRhsSym(3));
			setResult(r);
		$EndJava./

-- type

	type -> collectionType | simpleType

	-- unlike original xpand, do not allow lists without type
	collectionType ::= collectionTypeName LSQUARE simpleType RSQUARE
		/.$BeginJava
			Identifier id = (Identifier) getRhsSym(1);
			id = id.append(factory.createIdentifier(getRhsIToken(2)));
			id = id.append((Identifier) getRhsSym(3));
			id = id.append(factory.createIdentifier(getRhsIToken(4)));
			setResult(id);
		$EndJava./

	collectionTypeName ::= 'Collection'
		/.$BeginJava
			setResult(factory.createIdentifier(getRhsIToken(1)));
		$EndJava./
	collectionTypeName ::= 'List'
		/.$BeginJava
			setResult(factory.createIdentifier(getRhsIToken(1)));
		$EndJava./
	collectionTypeName ::= 'Set'
		/.$BeginJava
			setResult(factory.createIdentifier(getRhsIToken(1)));
		$EndJava./

	simpleType ::= IDENT NOT qualifiedType
		/.$BeginJava
			Identifier id = factory.createIdentifier(getLeftIToken());
			id = id.append(factory.createIdentifier(getRhsIToken(2)));
			id = id.append((Identifier) getRhsSym(3));
			setResult(id);
		$EndJava./
	simpleType -> qualifiedType

    qualifiedType ::= IDENT 
		/.$BeginJava
			setResult(factory.createIdentifier(getLeftIToken()));
		$EndJava./
    qualifiedType ::= IDENT DCOLON qualifiedType
		/.$BeginJava
			Identifier id = factory.createIdentifier(getLeftIToken());
			id = id.append(factory.createIdentifier(getRhsIToken(2)));
			id = id.append((Identifier) getRhsSym(3));
			setResult(id);
		$EndJava./

$End
