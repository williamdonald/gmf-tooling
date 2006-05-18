/*
 * Copyright (c) 2005 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *    Radek Dvorak (Borland) - initial API and implementation
 */
package org.eclipse.gmf.internal.validate;


public final class Annotations {

	/**
	 * The constraint URI which is used as the source of constraint annotations
	 */
	public static final String CONSTRAINTS_URI = "http://www.eclipse.org/gmf/2005/constraints"; //$NON-NLS-1$
	
	public static final String CONSTRAINTS_META_URI = CONSTRAINTS_URI + "/meta"; //$NON-NLS-1$
		
	/**
	 * The key of annotation detail which represents OCL expression.
	 * Corresponding value is the expression body. 
	 */	
	public static final String OCL_KEY = "ocl"; //$NON-NLS-1$
	
	public static final String DESCRIPTION = "description"; //$NON-NLS-1$	
	
	public static final class Meta {
		public static final String DEF_KEY = "def"; //$NON-NLS-1$
		public static final String VALUESPEC = "ValueSpec"; //$NON-NLS-1$	
		public static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$
		public static final String TYPE = "type"; //$NON-NLS-1$		
		public static final String BODY = "body"; //$NON-NLS-1$	
		public static final String LANG = "lang"; //$NON-NLS-1$	
		public static final String CONTEXT = "context"; //$NON-NLS-1$	
		public static final String REF = "ref"; //$NON-NLS-1$		
		public static final String OCL_KEY = "ocl"; //$NON-NLS-1$	
		public static final String VARIABLE = "variable"; //$NON-NLS-1$
		public static final String NAME = "name"; //$NON-NLS-1$
		
		public static final String IMPORT = "import"; //$NON-NLS-1$
	}
	
	private Annotations() {		
	}
}
