/*
 * Copyright (c) 2006 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.internal.graphdef.codegen;

/**
 * XXX hm, it's possible to have only one JETEmitter per key. What if we'd like to reference same emitter with different keys?
 * TODO CompositeTemplateRegistry?
 * TODO move to gmf.common
 * @author artem
 */
public interface TemplateRegistry {

	String getTemplatePath(Object key);

	boolean hasGeneratorClass(Object key);

	Class getGeneratorClass(Object key);

	void clean();

}