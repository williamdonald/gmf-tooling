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

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gmf.internal.xpand.expression.ast.Identifier;

public class OclKeywordManager {

	private static final Set<String> OCL_KEYWORDS = new HashSet<String>();

	static {
		OCL_KEYWORDS.add("self");
		OCL_KEYWORDS.add("inv");
		OCL_KEYWORDS.add("pre");
		OCL_KEYWORDS.add("post");
		OCL_KEYWORDS.add("context");
		OCL_KEYWORDS.add("package");
		OCL_KEYWORDS.add("endpackage");
		OCL_KEYWORDS.add("def");
		OCL_KEYWORDS.add("if");
		OCL_KEYWORDS.add("then");
		OCL_KEYWORDS.add("else");
		OCL_KEYWORDS.add("endif");
		OCL_KEYWORDS.add("and");
		OCL_KEYWORDS.add("or");
		OCL_KEYWORDS.add("xor");
		OCL_KEYWORDS.add("not");
		OCL_KEYWORDS.add("implies");
		OCL_KEYWORDS.add("let");
		OCL_KEYWORDS.add("in");
		OCL_KEYWORDS.add("true");
		OCL_KEYWORDS.add("false");
		OCL_KEYWORDS.add("body");
		OCL_KEYWORDS.add("derive");
		OCL_KEYWORDS.add("init");
		OCL_KEYWORDS.add("null");
		OCL_KEYWORDS.add("attr");
		OCL_KEYWORDS.add("oper");
		OCL_KEYWORDS.add("Set");
		OCL_KEYWORDS.add("Bag");
		OCL_KEYWORDS.add("Sequence");
		OCL_KEYWORDS.add("Collection");
		OCL_KEYWORDS.add("OrderedSet");
		OCL_KEYWORDS.add("iterate");
		OCL_KEYWORDS.add("forAll");
		OCL_KEYWORDS.add("exists");
		OCL_KEYWORDS.add("isUnique");
		OCL_KEYWORDS.add("any");
		OCL_KEYWORDS.add("one");
		OCL_KEYWORDS.add("collect");
		OCL_KEYWORDS.add("select");
		OCL_KEYWORDS.add("reject");
		OCL_KEYWORDS.add("collectNested");
		OCL_KEYWORDS.add("sortedBy");
		OCL_KEYWORDS.add("closure");
		OCL_KEYWORDS.add("oclIsKindOf");
		OCL_KEYWORDS.add("oclIsTypeOf");
		OCL_KEYWORDS.add("oclAsType");
		OCL_KEYWORDS.add("oclIsNew");
		OCL_KEYWORDS.add("oclIsUndefined");
		OCL_KEYWORDS.add("oclIsInvalid");
		OCL_KEYWORDS.add("oclIsInState");
		OCL_KEYWORDS.add("allInstances");
		OCL_KEYWORDS.add("String");
		OCL_KEYWORDS.add("Integer");
		OCL_KEYWORDS.add("UnlimitedNatural");
		OCL_KEYWORDS.add("Real");
		OCL_KEYWORDS.add("Boolean");
		OCL_KEYWORDS.add("Tuple");
		OCL_KEYWORDS.add("OclAny");
		OCL_KEYWORDS.add("OclVoid");
		OCL_KEYWORDS.add("Invalid");
		OCL_KEYWORDS.add("OclMessage");
		OCL_KEYWORDS.add("OclInvalid");
	}

	/**
	 * @return true if passed identifier was recognized as OCL keyword and
	 *         should be escaped during migration
	 */
	public boolean isOclKeyword(Identifier identifier) {
		return isOclKeyword(identifier.getValue());
	}

	public boolean isOclKeyword(String identifier) {
		return OCL_KEYWORDS.contains(identifier);
	}

	/**
	 * @return original identifier value if passed identifier is not an OCL
	 *         keyword or escaped identifier value in case it should be escaped
	 */
	public String getValidIdentifierValue(Identifier identifier) {
		return getValidIdentifierValue(identifier.getValue());
	}

	public String getValidIdentifierValue(String identifier) {
		if (!isOclKeyword(identifier)) {
			return identifier;
		}
		return OclCs.ESCAPE_PREFIX + identifier;
	}

}