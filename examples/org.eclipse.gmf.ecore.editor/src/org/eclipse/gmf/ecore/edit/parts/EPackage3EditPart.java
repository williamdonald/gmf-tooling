package org.eclipse.gmf.ecore.edit.parts;

import org.eclipse.gmf.runtime.diagram.ui.editparts.ListItemEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.gmf.ecore.edit.policies.EPackage3ItemSemanticEditPolicy;

import org.eclipse.gmf.ecore.edit.providers.EcoreStructuralFeatureParser;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;

/**
 * @generated
 */
public class EPackage3EditPart extends ListItemEditPart {

	/**
	 * @generated
	 */
	public EPackage3EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new EPackage3ItemSemanticEditPolicy());
	}

	/**
	 * @generated
	 */
	public IParser getParser() {
		if (parser == null) {
			parser = new EcoreStructuralFeatureParser(EcorePackage.eINSTANCE.getEPackage().getEStructuralFeature("name"));
		}
		return parser;
	}
}
