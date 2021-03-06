/*
 *  Copyright (c) 2006, 2009 Borland Software Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *      Borland Software Corporation - initial API and implementation
 */
package org.eclipse.gmf.graphdef.editor.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.gmfgraph.Canvas;
import org.eclipse.gmf.gmfgraph.ChildAccess;
import org.eclipse.gmf.gmfgraph.Compartment;
import org.eclipse.gmf.gmfgraph.Connection;
import org.eclipse.gmf.gmfgraph.DiagramElement;
import org.eclipse.gmf.gmfgraph.DiagramLabel;
import org.eclipse.gmf.gmfgraph.Ellipse;
import org.eclipse.gmf.gmfgraph.Figure;
import org.eclipse.gmf.gmfgraph.FigureDescriptor;
import org.eclipse.gmf.gmfgraph.FigureGallery;
import org.eclipse.gmf.gmfgraph.GMFGraphPackage;
import org.eclipse.gmf.gmfgraph.Label;
import org.eclipse.gmf.gmfgraph.Node;
import org.eclipse.gmf.gmfgraph.Point;
import org.eclipse.gmf.gmfgraph.Polygon;
import org.eclipse.gmf.gmfgraph.Polyline;
import org.eclipse.gmf.gmfgraph.RealFigure;
import org.eclipse.gmf.gmfgraph.Rectangle;
import org.eclipse.gmf.gmfgraph.RoundedRectangle;
import org.eclipse.gmf.graphdef.editor.edit.parts.CanvasEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.ChildAccessEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.CompartmentAccessorEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.CompartmentEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.ConnectionEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.DiagramElementFigureEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.DiagramLabelAccessorEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.DiagramLabelEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Ellipse2EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Ellipse3EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.EllipseEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.FigureDescriptorEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.FigureGalleryEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.FigureGalleryFiguresEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Label2EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Label3EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.LabelEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.NodeContentPaneEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.NodeEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.PointEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Polygon2EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Polygon3EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.PolygonEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Polyline2EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Polyline3EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.PolylineEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Rectangle2EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.Rectangle3EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.RectangleEditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.RoundedRectangle2EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.RoundedRectangle3EditPart;
import org.eclipse.gmf.graphdef.editor.edit.parts.RoundedRectangleEditPart;
import org.eclipse.gmf.graphdef.editor.providers.GMFGraphElementTypes;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class GMFGraphDiagramUpdater {

	/**
	 * @generated
	 */
	public static boolean isShortcutOrphaned(View view) {
		return !view.isSetElement() || view.getElement() == null || view.getElement().eIsProxy();
	}

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (GMFGraphVisualIDRegistry.getVisualID(view)) {
		case FigureDescriptorEditPart.VISUAL_ID:
			return getFigureDescriptor_3009SemanticChildren(view);
		case RectangleEditPart.VISUAL_ID:
			return getRectangle_3010SemanticChildren(view);
		case Rectangle2EditPart.VISUAL_ID:
			return getRectangle_3011SemanticChildren(view);
		case EllipseEditPart.VISUAL_ID:
			return getEllipse_3012SemanticChildren(view);
		case RoundedRectangleEditPart.VISUAL_ID:
			return getRoundedRectangle_3013SemanticChildren(view);
		case PolylineEditPart.VISUAL_ID:
			return getPolyline_3014SemanticChildren(view);
		case PolygonEditPart.VISUAL_ID:
			return getPolygon_3023SemanticChildren(view);
		case Ellipse2EditPart.VISUAL_ID:
			return getEllipse_3015SemanticChildren(view);
		case RoundedRectangle2EditPart.VISUAL_ID:
			return getRoundedRectangle_3016SemanticChildren(view);
		case Polyline2EditPart.VISUAL_ID:
			return getPolyline_3017SemanticChildren(view);
		case Polygon2EditPart.VISUAL_ID:
			return getPolygon_3024SemanticChildren(view);
		case Rectangle3EditPart.VISUAL_ID:
			return getRectangle_3018SemanticChildren(view);
		case Ellipse3EditPart.VISUAL_ID:
			return getEllipse_3019SemanticChildren(view);
		case RoundedRectangle3EditPart.VISUAL_ID:
			return getRoundedRectangle_3020SemanticChildren(view);
		case Polyline3EditPart.VISUAL_ID:
			return getPolyline_3021SemanticChildren(view);
		case Polygon3EditPart.VISUAL_ID:
			return getPolygon_3025SemanticChildren(view);
		case FigureGalleryFiguresEditPart.VISUAL_ID:
			return getFigureGalleryFigures_7008SemanticChildren(view);
		case CanvasEditPart.VISUAL_ID:
			return getCanvas_1000SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getFigureDescriptor_3009SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		FigureDescriptor modelElement = (FigureDescriptor) view.getElement();
		List result = new LinkedList();
		{
			Figure childElement = modelElement.getActualFigure();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == RectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
			}
			if (visualID == Ellipse2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
			}
			if (visualID == RoundedRectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
			}
			if (visualID == Polyline2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
			}
			if (visualID == Polygon2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
			}
			if (visualID == Label2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3010SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Rectangle modelElement = (Rectangle) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3011SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Rectangle modelElement = (Rectangle) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3012SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Ellipse modelElement = (Ellipse) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3013SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		RoundedRectangle modelElement = (RoundedRectangle) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3014SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Polyline modelElement = (Polyline) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTemplate().iterator(); it.hasNext();) {
			Point childElement = (Point) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == PointEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3023SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Polygon modelElement = (Polygon) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTemplate().iterator(); it.hasNext();) {
			Point childElement = (Point) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == PointEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3015SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Ellipse modelElement = (Ellipse) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3016SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		RoundedRectangle modelElement = (RoundedRectangle) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3017SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Polyline modelElement = (Polyline) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTemplate().iterator(); it.hasNext();) {
			Point childElement = (Point) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == PointEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3024SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Polygon modelElement = (Polygon) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTemplate().iterator(); it.hasNext();) {
			Point childElement = (Point) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == PointEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3018SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Rectangle modelElement = (Rectangle) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3019SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Ellipse modelElement = (Ellipse) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3020SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		RoundedRectangle modelElement = (RoundedRectangle) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getChildren().iterator(); it.hasNext();) {
			Figure childElement = (Figure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle2EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == EllipseEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangleEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolylineEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PolygonEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == LabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3021SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Polyline modelElement = (Polyline) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTemplate().iterator(); it.hasNext();) {
			Point childElement = (Point) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == PointEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3025SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Polygon modelElement = (Polygon) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getTemplate().iterator(); it.hasNext();) {
			Point childElement = (Point) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == PointEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFigureGalleryFigures_7008SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		FigureGallery modelElement = (FigureGallery) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getDescriptors().iterator(); it.hasNext();) {
			FigureDescriptor childElement = (FigureDescriptor) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == FigureDescriptorEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getFigures().iterator(); it.hasNext();) {
			RealFigure childElement = (RealFigure) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Rectangle3EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Ellipse3EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == RoundedRectangle3EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Polyline3EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Polygon3EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Label3EditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getCanvas_1000SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Canvas modelElement = (Canvas) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getCompartments().iterator(); it.hasNext();) {
			Compartment childElement = (Compartment) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == CompartmentEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getNodes().iterator(); it.hasNext();) {
			Node childElement = (Node) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == NodeEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getConnections().iterator(); it.hasNext();) {
			Connection childElement = (Connection) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == ConnectionEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getFigures().iterator(); it.hasNext();) {
			FigureGallery childElement = (FigureGallery) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == FigureGalleryEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getLabels().iterator(); it.hasNext();) {
			DiagramLabel childElement = (DiagramLabel) it.next();
			int visualID = GMFGraphVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == DiagramLabelEditPart.VISUAL_ID) {
				result.add(new GMFGraphNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (GMFGraphVisualIDRegistry.getVisualID(view)) {
		case CanvasEditPart.VISUAL_ID:
			return getCanvas_1000ContainedLinks(view);
		case CompartmentEditPart.VISUAL_ID:
			return getCompartment_2005ContainedLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006ContainedLinks(view);
		case ConnectionEditPart.VISUAL_ID:
			return getConnection_2007ContainedLinks(view);
		case FigureGalleryEditPart.VISUAL_ID:
			return getFigureGallery_2008ContainedLinks(view);
		case DiagramLabelEditPart.VISUAL_ID:
			return getDiagramLabel_2009ContainedLinks(view);
		case FigureDescriptorEditPart.VISUAL_ID:
			return getFigureDescriptor_3009ContainedLinks(view);
		case RectangleEditPart.VISUAL_ID:
			return getRectangle_3010ContainedLinks(view);
		case Rectangle2EditPart.VISUAL_ID:
			return getRectangle_3011ContainedLinks(view);
		case EllipseEditPart.VISUAL_ID:
			return getEllipse_3012ContainedLinks(view);
		case RoundedRectangleEditPart.VISUAL_ID:
			return getRoundedRectangle_3013ContainedLinks(view);
		case PolylineEditPart.VISUAL_ID:
			return getPolyline_3014ContainedLinks(view);
		case PointEditPart.VISUAL_ID:
			return getPoint_3022ContainedLinks(view);
		case PolygonEditPart.VISUAL_ID:
			return getPolygon_3023ContainedLinks(view);
		case LabelEditPart.VISUAL_ID:
			return getLabel_3026ContainedLinks(view);
		case Ellipse2EditPart.VISUAL_ID:
			return getEllipse_3015ContainedLinks(view);
		case RoundedRectangle2EditPart.VISUAL_ID:
			return getRoundedRectangle_3016ContainedLinks(view);
		case Polyline2EditPart.VISUAL_ID:
			return getPolyline_3017ContainedLinks(view);
		case Polygon2EditPart.VISUAL_ID:
			return getPolygon_3024ContainedLinks(view);
		case Label2EditPart.VISUAL_ID:
			return getLabel_3027ContainedLinks(view);
		case Rectangle3EditPart.VISUAL_ID:
			return getRectangle_3018ContainedLinks(view);
		case Ellipse3EditPart.VISUAL_ID:
			return getEllipse_3019ContainedLinks(view);
		case RoundedRectangle3EditPart.VISUAL_ID:
			return getRoundedRectangle_3020ContainedLinks(view);
		case Polyline3EditPart.VISUAL_ID:
			return getPolyline_3021ContainedLinks(view);
		case Polygon3EditPart.VISUAL_ID:
			return getPolygon_3025ContainedLinks(view);
		case Label3EditPart.VISUAL_ID:
			return getLabel_3028ContainedLinks(view);
		case ChildAccessEditPart.VISUAL_ID:
			return getChildAccess_4002ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (GMFGraphVisualIDRegistry.getVisualID(view)) {
		case CompartmentEditPart.VISUAL_ID:
			return getCompartment_2005IncomingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006IncomingLinks(view);
		case ConnectionEditPart.VISUAL_ID:
			return getConnection_2007IncomingLinks(view);
		case FigureGalleryEditPart.VISUAL_ID:
			return getFigureGallery_2008IncomingLinks(view);
		case DiagramLabelEditPart.VISUAL_ID:
			return getDiagramLabel_2009IncomingLinks(view);
		case FigureDescriptorEditPart.VISUAL_ID:
			return getFigureDescriptor_3009IncomingLinks(view);
		case RectangleEditPart.VISUAL_ID:
			return getRectangle_3010IncomingLinks(view);
		case Rectangle2EditPart.VISUAL_ID:
			return getRectangle_3011IncomingLinks(view);
		case EllipseEditPart.VISUAL_ID:
			return getEllipse_3012IncomingLinks(view);
		case RoundedRectangleEditPart.VISUAL_ID:
			return getRoundedRectangle_3013IncomingLinks(view);
		case PolylineEditPart.VISUAL_ID:
			return getPolyline_3014IncomingLinks(view);
		case PointEditPart.VISUAL_ID:
			return getPoint_3022IncomingLinks(view);
		case PolygonEditPart.VISUAL_ID:
			return getPolygon_3023IncomingLinks(view);
		case LabelEditPart.VISUAL_ID:
			return getLabel_3026IncomingLinks(view);
		case Ellipse2EditPart.VISUAL_ID:
			return getEllipse_3015IncomingLinks(view);
		case RoundedRectangle2EditPart.VISUAL_ID:
			return getRoundedRectangle_3016IncomingLinks(view);
		case Polyline2EditPart.VISUAL_ID:
			return getPolyline_3017IncomingLinks(view);
		case Polygon2EditPart.VISUAL_ID:
			return getPolygon_3024IncomingLinks(view);
		case Label2EditPart.VISUAL_ID:
			return getLabel_3027IncomingLinks(view);
		case Rectangle3EditPart.VISUAL_ID:
			return getRectangle_3018IncomingLinks(view);
		case Ellipse3EditPart.VISUAL_ID:
			return getEllipse_3019IncomingLinks(view);
		case RoundedRectangle3EditPart.VISUAL_ID:
			return getRoundedRectangle_3020IncomingLinks(view);
		case Polyline3EditPart.VISUAL_ID:
			return getPolyline_3021IncomingLinks(view);
		case Polygon3EditPart.VISUAL_ID:
			return getPolygon_3025IncomingLinks(view);
		case Label3EditPart.VISUAL_ID:
			return getLabel_3028IncomingLinks(view);
		case ChildAccessEditPart.VISUAL_ID:
			return getChildAccess_4002IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (GMFGraphVisualIDRegistry.getVisualID(view)) {
		case CompartmentEditPart.VISUAL_ID:
			return getCompartment_2005OutgoingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_2006OutgoingLinks(view);
		case ConnectionEditPart.VISUAL_ID:
			return getConnection_2007OutgoingLinks(view);
		case FigureGalleryEditPart.VISUAL_ID:
			return getFigureGallery_2008OutgoingLinks(view);
		case DiagramLabelEditPart.VISUAL_ID:
			return getDiagramLabel_2009OutgoingLinks(view);
		case FigureDescriptorEditPart.VISUAL_ID:
			return getFigureDescriptor_3009OutgoingLinks(view);
		case RectangleEditPart.VISUAL_ID:
			return getRectangle_3010OutgoingLinks(view);
		case Rectangle2EditPart.VISUAL_ID:
			return getRectangle_3011OutgoingLinks(view);
		case EllipseEditPart.VISUAL_ID:
			return getEllipse_3012OutgoingLinks(view);
		case RoundedRectangleEditPart.VISUAL_ID:
			return getRoundedRectangle_3013OutgoingLinks(view);
		case PolylineEditPart.VISUAL_ID:
			return getPolyline_3014OutgoingLinks(view);
		case PointEditPart.VISUAL_ID:
			return getPoint_3022OutgoingLinks(view);
		case PolygonEditPart.VISUAL_ID:
			return getPolygon_3023OutgoingLinks(view);
		case LabelEditPart.VISUAL_ID:
			return getLabel_3026OutgoingLinks(view);
		case Ellipse2EditPart.VISUAL_ID:
			return getEllipse_3015OutgoingLinks(view);
		case RoundedRectangle2EditPart.VISUAL_ID:
			return getRoundedRectangle_3016OutgoingLinks(view);
		case Polyline2EditPart.VISUAL_ID:
			return getPolyline_3017OutgoingLinks(view);
		case Polygon2EditPart.VISUAL_ID:
			return getPolygon_3024OutgoingLinks(view);
		case Label2EditPart.VISUAL_ID:
			return getLabel_3027OutgoingLinks(view);
		case Rectangle3EditPart.VISUAL_ID:
			return getRectangle_3018OutgoingLinks(view);
		case Ellipse3EditPart.VISUAL_ID:
			return getEllipse_3019OutgoingLinks(view);
		case RoundedRectangle3EditPart.VISUAL_ID:
			return getRoundedRectangle_3020OutgoingLinks(view);
		case Polyline3EditPart.VISUAL_ID:
			return getPolyline_3021OutgoingLinks(view);
		case Polygon3EditPart.VISUAL_ID:
			return getPolygon_3025OutgoingLinks(view);
		case Label3EditPart.VISUAL_ID:
			return getLabel_3028OutgoingLinks(view);
		case ChildAccessEditPart.VISUAL_ID:
			return getChildAccess_4002OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getCanvas_1000ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getCompartment_2005ContainedLinks(View view) {
		Compartment modelElement = (Compartment) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_Compartment_Accessor_4003(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getNode_2006ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_Node_ContentPane_4006(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getConnection_2007ContainedLinks(View view) {
		Connection modelElement = (Connection) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFigureGallery_2008ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDiagramLabel_2009ContainedLinks(View view) {
		DiagramLabel modelElement = (DiagramLabel) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramLabel_Accessor_4004(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Node_ContentPane_4006(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFigureDescriptor_3009ContainedLinks(View view) {
		FigureDescriptor modelElement = (FigureDescriptor) view.getElement();
		List result = new LinkedList();
		result.addAll(getContainedTypeModelFacetLinks_ChildAccess_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3010ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3011ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3012ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3013ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3014ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPoint_3022ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3023ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3026ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3015ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3016ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3017ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3024ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3027ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3018ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3019ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3020ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3021ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3025ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3028ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getChildAccess_4002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getCompartment_2005IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNode_2006IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getConnection_2007IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getFigureGallery_2008IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDiagramLabel_2009IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getFigureDescriptor_3009IncomingLinks(View view) {
		FigureDescriptor modelElement = (FigureDescriptor) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3010IncomingLinks(View view) {
		Rectangle modelElement = (Rectangle) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3011IncomingLinks(View view) {
		Rectangle modelElement = (Rectangle) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3012IncomingLinks(View view) {
		Ellipse modelElement = (Ellipse) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3013IncomingLinks(View view) {
		RoundedRectangle modelElement = (RoundedRectangle) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3014IncomingLinks(View view) {
		Polyline modelElement = (Polyline) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPoint_3022IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3023IncomingLinks(View view) {
		Polygon modelElement = (Polygon) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3026IncomingLinks(View view) {
		Label modelElement = (Label) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3015IncomingLinks(View view) {
		Ellipse modelElement = (Ellipse) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3016IncomingLinks(View view) {
		RoundedRectangle modelElement = (RoundedRectangle) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3017IncomingLinks(View view) {
		Polyline modelElement = (Polyline) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3024IncomingLinks(View view) {
		Polygon modelElement = (Polygon) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3027IncomingLinks(View view) {
		Label modelElement = (Label) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3018IncomingLinks(View view) {
		Rectangle modelElement = (Rectangle) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3019IncomingLinks(View view) {
		Ellipse modelElement = (Ellipse) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3020IncomingLinks(View view) {
		RoundedRectangle modelElement = (RoundedRectangle) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3021IncomingLinks(View view) {
		Polyline modelElement = (Polyline) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3025IncomingLinks(View view) {
		Polygon modelElement = (Polygon) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3028IncomingLinks(View view) {
		Label modelElement = (Label) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_ChildAccess_4002(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getChildAccess_4002IncomingLinks(View view) {
		ChildAccess modelElement = (ChildAccess) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingFeatureModelFacetLinks_Compartment_Accessor_4003(modelElement, crossReferences));
		result.addAll(getIncomingFeatureModelFacetLinks_DiagramLabel_Accessor_4004(modelElement, crossReferences));
		result.addAll(getIncomingFeatureModelFacetLinks_Node_ContentPane_4006(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getCompartment_2005OutgoingLinks(View view) {
		Compartment modelElement = (Compartment) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_Compartment_Accessor_4003(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getNode_2006OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_Node_ContentPane_4006(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getConnection_2007OutgoingLinks(View view) {
		Connection modelElement = (Connection) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFigureGallery_2008OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getDiagramLabel_2009OutgoingLinks(View view) {
		DiagramLabel modelElement = (DiagramLabel) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramLabel_Accessor_4004(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Node_ContentPane_4006(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFigureDescriptor_3009OutgoingLinks(View view) {
		FigureDescriptor modelElement = (FigureDescriptor) view.getElement();
		List result = new LinkedList();
		result.addAll(getContainedTypeModelFacetLinks_ChildAccess_4002(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3010OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3011OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3012OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3013OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3014OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPoint_3022OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3023OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3026OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3015OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3016OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3017OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3024OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3027OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRectangle_3018OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEllipse_3019OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRoundedRectangle_3020OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolyline_3021OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPolygon_3025OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getLabel_3028OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getChildAccess_4002OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_ChildAccess_4002(FigureDescriptor container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getAccessors().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ChildAccess) {
				continue;
			}
			ChildAccess link = (ChildAccess) linkObject;
			if (ChildAccessEditPart.VISUAL_ID != GMFGraphVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			Figure dst = link.getFigure();
			result.add(new GMFGraphLinkDescriptor(container, dst, link, GMFGraphElementTypes.ChildAccess_4002, ChildAccessEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_ChildAccess_4002(Figure target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() != GMFGraphPackage.eINSTANCE.getChildAccess_Figure() || false == setting.getEObject() instanceof ChildAccess) {
				continue;
			}
			ChildAccess link = (ChildAccess) setting.getEObject();
			if (ChildAccessEditPart.VISUAL_ID != GMFGraphVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			if (false == link.eContainer() instanceof FigureDescriptor) {
				continue;
			}
			FigureDescriptor container = (FigureDescriptor) link.eContainer();
			result.add(new GMFGraphLinkDescriptor(container, target, link, GMFGraphElementTypes.ChildAccess_4002, ChildAccessEditPart.VISUAL_ID));

		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingFeatureModelFacetLinks_Compartment_Accessor_4003(ChildAccess target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() == GMFGraphPackage.eINSTANCE.getCompartment_Accessor()) {
				result.add(new GMFGraphLinkDescriptor(setting.getEObject(), target, GMFGraphElementTypes.CompartmentAccessor_4003, CompartmentAccessorEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingFeatureModelFacetLinks_DiagramLabel_Accessor_4004(ChildAccess target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() == GMFGraphPackage.eINSTANCE.getDiagramLabel_Accessor()) {
				result.add(new GMFGraphLinkDescriptor(setting.getEObject(), target, GMFGraphElementTypes.DiagramLabelAccessor_4004, DiagramLabelAccessorEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingFeatureModelFacetLinks_Node_ContentPane_4006(ChildAccess target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() == GMFGraphPackage.eINSTANCE.getNode_ContentPane()) {
				result.add(new GMFGraphLinkDescriptor(setting.getEObject(), target, GMFGraphElementTypes.NodeContentPane_4006, NodeContentPaneEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingFeatureModelFacetLinks_DiagramElement_Figure_4005(FigureDescriptor target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() == GMFGraphPackage.eINSTANCE.getDiagramElement_Figure()) {
				result.add(new GMFGraphLinkDescriptor(setting.getEObject(), target, GMFGraphElementTypes.DiagramElementFigure_4005, DiagramElementFigureEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingFeatureModelFacetLinks_Compartment_Accessor_4003(Compartment source) {
		Collection result = new LinkedList();
		ChildAccess destination = source.getAccessor();
		if (destination == null) {
			return result;
		}
		result.add(new GMFGraphLinkDescriptor(source, destination, GMFGraphElementTypes.CompartmentAccessor_4003, CompartmentAccessorEditPart.VISUAL_ID));
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingFeatureModelFacetLinks_DiagramLabel_Accessor_4004(DiagramLabel source) {
		Collection result = new LinkedList();
		ChildAccess destination = source.getAccessor();
		if (destination == null) {
			return result;
		}
		result.add(new GMFGraphLinkDescriptor(source, destination, GMFGraphElementTypes.DiagramLabelAccessor_4004, DiagramLabelAccessorEditPart.VISUAL_ID));
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingFeatureModelFacetLinks_Node_ContentPane_4006(Node source) {
		Collection result = new LinkedList();
		ChildAccess destination = source.getContentPane();
		if (destination == null) {
			return result;
		}
		result.add(new GMFGraphLinkDescriptor(source, destination, GMFGraphElementTypes.NodeContentPane_4006, NodeContentPaneEditPart.VISUAL_ID));
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingFeatureModelFacetLinks_DiagramElement_Figure_4005(DiagramElement source) {
		Collection result = new LinkedList();
		FigureDescriptor destination = source.getFigure();
		if (destination == null) {
			return result;
		}
		result.add(new GMFGraphLinkDescriptor(source, destination, GMFGraphElementTypes.DiagramElementFigure_4005, DiagramElementFigureEditPart.VISUAL_ID));
		return result;
	}

}
