package org.eclipse.gmf.examples.ocldriven.toe.diagram.edit.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gmf.examples.ocldriven.toe.diagram.edit.policies.AllHolderCanonicalEditPolicy;
import org.eclipse.gmf.examples.ocldriven.toe.diagram.edit.policies.AllHolderItemSemanticEditPolicy;
import org.eclipse.gmf.examples.ocldriven.toe.diagram.part.TOEVisualIDRegistry;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.tooling.runtime.edit.policies.reparent.CreationEditPolicyWithCustomReparent;

/**
 * @generated
 */
public class AllHolderEditPart extends DiagramEditPart {

	/**
	* @generated
	*/
	public final static String MODEL_ID = "TOE"; //$NON-NLS-1$

	/**
	* @generated
	*/
	public static final int VISUAL_ID = 1000;

	/**
	* @generated
	*/
	public AllHolderEditPart(View view) {
		super(view);
	}

	/**
	* @generated
	*/
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new AllHolderItemSemanticEditPolicy());
		installEditPolicy(EditPolicyRoles.CANONICAL_ROLE, new AllHolderCanonicalEditPolicy());
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new CreationEditPolicyWithCustomReparent(TOEVisualIDRegistry.TYPED_INSTANCE));
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
	}

	/**
	* @generated
	*/
	/*package-local*/static class NodeLabelDragPolicy extends NonResizableEditPolicy {

		/**
		* @generated
		*/
		@SuppressWarnings("rawtypes")
		protected List createSelectionHandles() {
			MoveHandle h = new MoveHandle((GraphicalEditPart) getHost());
			h.setBorder(null);
			return Collections.singletonList(h);
		}

		/**
		* @generated
		*/
		public Command getCommand(Request request) {
			return null;
		}

		/**
		* @generated
		*/
		public boolean understandsRequest(Request request) {
			return false;
		}
	}

}
