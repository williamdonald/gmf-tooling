/*
 * Copyright (c) 2006, 2008 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Artem Tikhomirov (Borland)
 *     Boris Blajer (Borland) - support for composite resources
 */
package org.eclipse.gmf.internal.xpand.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.gmf.internal.xpand.ResourceManager;
import org.eclipse.gmf.internal.xpand.expression.SyntaxConstants;
import org.eclipse.gmf.internal.xpand.migration.Activator;
import org.eclipse.gmf.internal.xpand.model.XpandResource;
import org.eclipse.gmf.internal.xpand.xtend.ast.XtendResource;

// FIXME it's not a good idea to parse file on every proposal computation
public abstract class ResourceManagerImpl implements ResourceManager {

	private final Map<String, XtendResource> cachedXtend = new TreeMap<String, XtendResource>();
	private final Map<String, XpandResource> cachedXpand = new TreeMap<String, XpandResource>();

	public XtendResource loadXtendResource(String fullyQualifiedName) {
		try {
			return loadXtendThroughCache(fullyQualifiedName);
		} catch (FileNotFoundException ex) {
			return null;	//Missing resource is an anticipated situation, not a error that should be handled
		} catch (IOException ex) {
			Activator.logError(ex);
		} catch (ParserException ex) {
			handleParserException(ex);
		}
		return null;
	}

	protected XtendResource loadXtendThroughCache(String qualifiedName) throws IOException, ParserException {
		if (hasCachedXtend(qualifiedName)) {
			return cachedXtend.get(qualifiedName);
		}
		final XtendResource loaded = doLoadXtendResource(qualifiedName);
		assert loaded != null; // this is the contract of loadXtendResource
		if (shouldCache()) {
			cachedXtend.put(qualifiedName, loaded);
		}
		return loaded;
	}
	
	private XtendResource doLoadXtendResource(String fullyQualifiedName) throws IOException, ParserException {
		Reader[] rs = resolveMultiple(fullyQualifiedName, XtendResource.FILE_EXTENSION);
		assert rs != null && rs.length > 0;
		XtendResource[] result = loadXtendResources(rs, fullyQualifiedName);
		if (result.length == 1) {
			return result[0];
		}
		return new CompositeXtendResource(this, result);
	}

	public XpandResource loadXpandResource(String fullyQualifiedName) {
		try {
			return loadXpandThroughCache(fullyQualifiedName);
		} catch (FileNotFoundException ex) {
			return null;	//Missing resource is an anticipated situation, not a error that should be handled
		} catch (IOException ex) {
			// XXX come up with better handling
			Activator.logWarn(ex.getMessage());
		} catch (ParserException ex) {
			handleParserException(ex);
		}
		return null;
	}

	protected XpandResource loadXpandThroughCache(String qualifiedName) throws IOException, ParserException {
		if (hasCachedXpand(qualifiedName)) {
			return cachedXpand.get(qualifiedName);
		}
		final XpandResource loaded = doLoadXpandResource(qualifiedName);
		if (shouldCache()) {
			cachedXpand.put(qualifiedName, loaded);
		}
		return loaded;
	}

	private XpandResource doLoadXpandResource(String fullyQualifiedName) throws IOException, ParserException {
		Reader[] rs1 = resolveMultiple(fullyQualifiedName, XpandResource.TEMPLATE_EXTENSION);
		assert rs1 != null && rs1.length > 0; // exception should be thrown to indicate issues with resolve
		XpandResource[] unadvised = loadXpandResources(rs1, fullyQualifiedName);
		XpandResource[] advices = null;
		try {
	    	String aspectsTemplateName = getAspectsTemplateName(fullyQualifiedName);
	    	Reader[] rs2 = resolveMultiple(aspectsTemplateName, XpandResource.TEMPLATE_EXTENSION);
	    	// XXX relax resolveMultiple to return empty array and use length==0 here instead of exception
	    	advices = loadXpandResources(rs2, aspectsTemplateName);
		} catch (FileNotFoundException e) {
		} catch (IOException ex) {
			// XXX come up with better handling
			Activator.logWarn(ex.getMessage());
		} catch (ParserException ex) {
			handleParserException(ex);
		}
		if (advices == null && unadvised.length == 1) {
			return unadvised[0];
		}
		return new CompositeXpandResource(this, unadvised, advices);
	}

	/**
	 * XXX: only to simplify tests, should be private or inlined
	 */
	protected String getAspectsTemplateName(String fullyQualifiedName) {
		return ASPECT_PREFIX + fullyQualifiedName;
	}

	/**
	 * If the given fully-qualified name is an aspect, transforms it to its "host" fully-qualified name. Otherwise,
	 * returns the given fully-qualified name.
	 */
	protected String getNonAspectsTemplateName(String possiblyAspectedFullyQualifiedName) {
		if (possiblyAspectedFullyQualifiedName == null) {
			return null;
		}
		if (possiblyAspectedFullyQualifiedName.startsWith(ASPECT_PREFIX)) {
			return possiblyAspectedFullyQualifiedName.substring(ASPECT_PREFIX.length());
		}
		return possiblyAspectedFullyQualifiedName;
	}

	protected abstract void handleParserException(ParserException ex);

	/**
	 * Readers get closed after parse attempt. 
	 */
	protected XtendResource[] loadXtendResources(Reader[] readers, String fullyQualifiedName) throws IOException, ParserException {
		XtendResource[] result = new XtendResource[readers.length];
		for (int i = 0; i < readers.length; i++) {
			assert readers[i] != null;
			try {
				result[i] = new XtendResourceParser().parse(readers[i], fullyQualifiedName);
				assert result[i] != null; // this is the contract of loadXpandResource
			} finally {
				try {
					readers[i].close();
				} catch (Exception ex) {/*IGNORE*/}
			}
		}
		return result;
	}

	/**
	 * Readers get closed after parse attempt.
	 */
	protected XpandResource[] loadXpandResources(Reader[] readers, String fullyQualifiedName) throws IOException, ParserException {
		XpandResource[] result = new XpandResource[readers.length];
		for (int i = 0; i < readers.length; i++) {
			assert readers[i] != null;
			try {
				result[i] = new XpandResourceParser().parse(readers[i], fullyQualifiedName);
				assert result[i] != null; // this is the contract of parse
			} finally {
				try {
					readers[i].close();
				} catch (Exception ex) {/*IGNORE*/}
			}
		}
		return result;
	}

	protected abstract boolean shouldCache();

	protected final boolean hasCachedXpand(String fullyQualifiedName) {
		return shouldCache() && cachedXpand.containsKey(fullyQualifiedName);
	}
	protected final boolean hasCachedXtend(String fullyQualifiedName) {
		return shouldCache() && cachedXtend.containsKey(fullyQualifiedName);
	}

	protected final void forgetCachedXpand(String fullyQualifiedName) {
		cachedXpand.remove(fullyQualifiedName);
	}
	protected final void forgetCachedXtend(String fullyQualifiedName) {
		cachedXtend.remove(fullyQualifiedName);
	}

	protected final void forgetAll() {
		cachedXpand.clear();
		cachedXtend.clear();
	}

	private static final String ASPECT_PREFIX = "aspects" + SyntaxConstants.NS_DELIM;	//$NON-NLS-1$
}
