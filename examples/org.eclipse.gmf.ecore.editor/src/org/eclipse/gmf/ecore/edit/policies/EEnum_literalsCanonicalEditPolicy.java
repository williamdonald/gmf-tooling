package org.eclipse.gmf.ecore.edit.policies;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.ecore.part.EcoreVisualIDRegistry;

/**
 * @generated
 */
public class EEnum_literalsCanonicalEditPolicy extends CanonicalEditPolicy {

	/**
	 * @generated
	 */
	protected List getSemanticChildrenList() {
		List result = new LinkedList();
		EEnum modelElement = (EEnum) ((View) getHost().getModel()).getElement();
		EObject nextValue;
		int nodeVID;
		for (Iterator it = modelElement.getELiterals().iterator(); it.hasNext();) {
			nextValue = (EObject) it.next();
			nodeVID = EcoreVisualIDRegistry.INSTANCE.getNodeVisualID((View) getHost().getModel(), nextValue, "");
			if (2009 == nodeVID) {
				result.add(nextValue);
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected boolean shouldDeleteView(View view) {
		EObject domainModelElement = view.getElement();
		return domainModelElement != null && domainModelElement != ((View) getHost().getModel()).getElement() && super.shouldDeleteView(view);
	}

	/**
	 * @generated
	 */
	protected String getFactoryHint(IAdaptable elementAdapter) {
		return "";
	}

}
