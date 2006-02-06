/*
 * Copyright (c) 2005 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Artem Tikhomirov (Borland) - initial API and implementation
 */
package org.eclipse.gmf.graphdef.codegen;

import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.jet.JETException;
import org.eclipse.gmf.common.codegen.ImportAssistant;
import org.eclipse.gmf.common.codegen.NullImportAssistant;
import org.eclipse.gmf.gmfgraph.Figure;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.gmfgraph.PolygonDecoration;
import org.eclipse.gmf.gmfgraph.Polyline;
import org.eclipse.gmf.gmfgraph.PolylineConnection;
import org.eclipse.gmf.gmfgraph.PolylineDecoration;
import org.eclipse.gmf.gmfgraph.RoundedRectangle;
import org.eclipse.gmf.gmfgraph.Shape;
import org.eclipse.gmf.graphdef.codegen.templates.FigureAttrGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.FigureChildrenGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.LabelAttrGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.NewFigureGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.PolygonDecorationAttrGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.PolylineAttrGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.PolylineDecorationAttrGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.RoundedRectAttrGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.ShapeAttrGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.TopConnectionGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.TopFigureGenerator;
import org.eclipse.gmf.graphdef.codegen.templates.TopShapeGenerator;
import org.eclipse.gmf.internal.graphdef.codegen.DispatcherImpl;
import org.eclipse.gmf.internal.graphdef.codegen.HierarchyKeyMap;
import org.eclipse.gmf.internal.graphdef.codegen.KeyChain;
import org.eclipse.gmf.internal.graphdef.codegen.KeyMap;
import org.eclipse.gmf.internal.graphdef.codegen.StaticTemplateRegistry;
import org.eclipse.gmf.internal.graphdef.codegen.TemplateRegistry;
import org.eclipse.gmf.internal.graphdef.codegen.YAEmitterFactory;
import org.osgi.framework.Bundle;

/**
 * @author artem
 *
 */
public class FigureGenerator {
	private final String packageName;
	private Dispatcher myTopDispatcher;
	private Dispatcher myInnerDispatcher;

	public FigureGenerator() {
		this(null);
	}

	public FigureGenerator(String aPackageName) {
		packageName = aPackageName;
		final Bundle thisBundle = Platform.getBundle("org.eclipse.gmf.graphdef.codegen");
		final ArrayList variables = new ArrayList();
		variables.add("org.eclipse.gmf.graphdef");
		variables.add("org.eclipse.emf.ecore");
		variables.add("org.eclipse.emf.common");
		variables.add("org.eclipse.gmf.common");
		variables.add("org.eclipse.gmf.graphdef.codegen");

		KeyMap keyMap = new HierarchyKeyMap() {
			/*
			 * Capture knowledge that we use classes and strings as keys
			 */
			public KeyChain map(Object key) {
				if (key instanceof String) {
					return super.map(key);
				} else {
					return super.map(key.getClass());
				}
			}
		};
		YAEmitterFactory topFactory = new YAEmitterFactory(thisBundle.getEntry("/"), fillTopLevel(), true, variables, true);
		myTopDispatcher = new DispatcherImpl(topFactory, keyMap);
		YAEmitterFactory innerFactory = new YAEmitterFactory(thisBundle.getEntry("/"), fillAttrs(), true, variables, true);
		myInnerDispatcher = new DispatcherImpl(innerFactory, keyMap);
	}

	/**
	 * @return possibly <code>null</code>
	 */
	public String getPackageStatement() {
		return packageName;
	}

	private static TemplateRegistry fillTopLevel() {
		StaticTemplateRegistry tr = new StaticTemplateRegistry();
		tr.put(PolylineConnection.class, "/templates/PolylineConnection.javajet", TopConnectionGenerator.class);
		tr.put(Shape.class, "/templates/top/Shape.javajet", TopShapeGenerator.class);
		tr.put(Figure.class, "/templates/top/Figure.javajet", TopFigureGenerator.class);
		return tr;
	}

	// XXX NOTE, the fact we use "instantiate" and "Children" strings
	// helps us to postpone resolution of the next problem (one we make these twwo overridable):
	// it's not possible to tell from single dispatcher.dispatch(Figure, args) what's the intention - 
	// whether to instantiate, look for children or initialize attributes
	// Perhaps, we should have distinct methods in the Dispatcher, or add "hint" as another argument
	private static TemplateRegistry fillAttrs() {
		StaticTemplateRegistry tr = new StaticTemplateRegistry();
		tr.put(Figure.class, "/templates/attr/Figure.javajet", FigureAttrGenerator.class);
		tr.put(Shape.class, "/templates/attr/Shape.javajet", ShapeAttrGenerator.class);
		tr.put(Label.class, "/templates/attr/Label.javajet", LabelAttrGenerator.class);
		tr.put(Polyline.class, "/templates/attr/Polyline.javajet", PolylineAttrGenerator.class);
		tr.put(RoundedRectangle.class, "/templates/attr/RoundedRectangle.javajet", RoundedRectAttrGenerator.class);
		tr.put(PolygonDecoration.class, "/templates/attr/PolygonDecoration.javajet", PolygonDecorationAttrGenerator.class);
		tr.put(PolylineDecoration.class, "/templates/attr/PolylineDecoration.javajet", PolylineDecorationAttrGenerator.class);
		// instantiation templates - only single one now. FIXME - make it overridable
		tr.put("instantiate", "/templates/new/Figure.javajet", NewFigureGenerator.class);
		// children templates - only single one now. FIXME - make it overridable
		tr.put("Children", "/templates/children/Figure.javajet", FigureChildrenGenerator.class);
		// FIXME same template is registered twice
		tr.put("Shape", "/templates/attr/Shape.javajet", ShapeAttrGenerator.class);
		tr.put("Figure", "/templates/attr/Figure.javajet", FigureAttrGenerator.class);
		tr.put("PolylineDecoration", "/templates/attr/PolylineDecoration.javajet", PolylineDecorationAttrGenerator.class);
		return tr;
	}

	public String go(Figure fig) throws JETException {
		return go(fig, new NullImportAssistant());
	}

	public String go(Figure fig, ImportAssistant importManager) {
		String res = null;
		res = myTopDispatcher.dispatch(fig, new Object[] {fig, importManager, myInnerDispatcher});
		if (res == null) {
			throw new IllegalStateException();
		}
		return packageName == null ? res : "package " + packageName + ";\n" + res;
	}
}
