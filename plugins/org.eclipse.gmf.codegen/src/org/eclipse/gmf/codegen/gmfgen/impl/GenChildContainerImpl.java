/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmf.codegen.gmfgen.impl;

import java.util.Collection;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.codegen.gmfgen.GenChildContainer;
import org.eclipse.gmf.codegen.gmfgen.GenChildNode;

import org.eclipse.gmf.codegen.gmfgen.Viewmap;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Child Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.impl.GenChildContainerImpl#getChildNodes <em>Child Nodes</em>}</li>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.impl.GenChildContainerImpl#getCanonicalEditPolicyClassName <em>Canonical Edit Policy Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class GenChildContainerImpl extends GenCommonBaseImpl implements GenChildContainer {
	/**
	 * The cached value of the '{@link #getChildNodes() <em>Child Nodes</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getChildNodes()
	 * @generated
	 * @ordered
	 */
	protected EList childNodes = null;

	/**
	 * The default value of the '{@link #getCanonicalEditPolicyClassName() <em>Canonical Edit Policy Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCanonicalEditPolicyClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCanonicalEditPolicyClassName() <em>Canonical Edit Policy Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCanonicalEditPolicyClassName()
	 * @generated
	 * @ordered
	 */
	protected String canonicalEditPolicyClassName = CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenChildContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenChildContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList getChildNodes() {
		if (childNodes == null) {
			childNodes = new EObjectContainmentWithInverseEList(GenChildNode.class, this, GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES, GMFGenPackage.GEN_CHILD_NODE__CONTAINER);
		}
		return childNodes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCanonicalEditPolicyClassNameGen() {
		return canonicalEditPolicyClassName;
	}

	public String getCanonicalEditPolicyClassName() {
		String value = getCanonicalEditPolicyClassNameGen();
		if (isEmpty(value)) {
			value = getClassNamePart() + CANONICAL_EDIT_POLICY_SUFFIX;
		}
		return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCanonicalEditPolicyClassName(String newCanonicalEditPolicyClassName) {
		String oldCanonicalEditPolicyClassName = canonicalEditPolicyClassName;
		canonicalEditPolicyClassName = newCanonicalEditPolicyClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME, oldCanonicalEditPolicyClassName, canonicalEditPolicyClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getCanonicalEditPolicyQualifiedClassName() {
		return getDiagram().getEditPoliciesPackageName() + '.' + getCanonicalEditPolicyClassName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
					return ((InternalEList)getChildNodes()).basicAdd(otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case GMFGenPackage.GEN_CHILD_CONTAINER__VIEWMAP:
					return basicSetViewmap(null, msgs);
				case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
					return ((InternalEList)getChildNodes()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case GMFGenPackage.GEN_CHILD_CONTAINER__DIAGRAM_RUN_TIME_CLASS:
				if (resolve) return getDiagramRunTimeClass();
				return basicGetDiagramRunTimeClass();
			case GMFGenPackage.GEN_CHILD_CONTAINER__VISUAL_ID:
				return new Integer(getVisualID());
			case GMFGenPackage.GEN_CHILD_CONTAINER__EDIT_PART_CLASS_NAME:
				return getEditPartClassName();
			case GMFGenPackage.GEN_CHILD_CONTAINER__ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME:
				return getItemSemanticEditPolicyClassName();
			case GMFGenPackage.GEN_CHILD_CONTAINER__NOTATION_VIEW_FACTORY_CLASS_NAME:
				return getNotationViewFactoryClassName();
			case GMFGenPackage.GEN_CHILD_CONTAINER__VIEWMAP:
				return getViewmap();
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				return getChildNodes();
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				return getCanonicalEditPolicyClassName();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case GMFGenPackage.GEN_CHILD_CONTAINER__DIAGRAM_RUN_TIME_CLASS:
				setDiagramRunTimeClass((GenClass)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__VISUAL_ID:
				setVisualID(((Integer)newValue).intValue());
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__EDIT_PART_CLASS_NAME:
				setEditPartClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME:
				setItemSemanticEditPolicyClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__NOTATION_VIEW_FACTORY_CLASS_NAME:
				setNotationViewFactoryClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__VIEWMAP:
				setViewmap((Viewmap)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				getChildNodes().clear();
				getChildNodes().addAll((Collection)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				setCanonicalEditPolicyClassName((String)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case GMFGenPackage.GEN_CHILD_CONTAINER__DIAGRAM_RUN_TIME_CLASS:
				setDiagramRunTimeClass((GenClass)null);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__VISUAL_ID:
				setVisualID(VISUAL_ID_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__EDIT_PART_CLASS_NAME:
				setEditPartClassName(EDIT_PART_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME:
				setItemSemanticEditPolicyClassName(ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__NOTATION_VIEW_FACTORY_CLASS_NAME:
				setNotationViewFactoryClassName(NOTATION_VIEW_FACTORY_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__VIEWMAP:
				setViewmap((Viewmap)null);
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				getChildNodes().clear();
				return;
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				setCanonicalEditPolicyClassName(CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case GMFGenPackage.GEN_CHILD_CONTAINER__DIAGRAM_RUN_TIME_CLASS:
				return diagramRunTimeClass != null;
			case GMFGenPackage.GEN_CHILD_CONTAINER__VISUAL_ID:
				return visualID != VISUAL_ID_EDEFAULT;
			case GMFGenPackage.GEN_CHILD_CONTAINER__EDIT_PART_CLASS_NAME:
				return EDIT_PART_CLASS_NAME_EDEFAULT == null ? editPartClassName != null : !EDIT_PART_CLASS_NAME_EDEFAULT.equals(editPartClassName);
			case GMFGenPackage.GEN_CHILD_CONTAINER__ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME:
				return ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME_EDEFAULT == null ? itemSemanticEditPolicyClassName != null : !ITEM_SEMANTIC_EDIT_POLICY_CLASS_NAME_EDEFAULT.equals(itemSemanticEditPolicyClassName);
			case GMFGenPackage.GEN_CHILD_CONTAINER__NOTATION_VIEW_FACTORY_CLASS_NAME:
				return NOTATION_VIEW_FACTORY_CLASS_NAME_EDEFAULT == null ? notationViewFactoryClassName != null : !NOTATION_VIEW_FACTORY_CLASS_NAME_EDEFAULT.equals(notationViewFactoryClassName);
			case GMFGenPackage.GEN_CHILD_CONTAINER__VIEWMAP:
				return viewmap != null;
			case GMFGenPackage.GEN_CHILD_CONTAINER__CHILD_NODES:
				return childNodes != null && !childNodes.isEmpty();
			case GMFGenPackage.GEN_CHILD_CONTAINER__CANONICAL_EDIT_POLICY_CLASS_NAME:
				return CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT == null ? canonicalEditPolicyClassName != null : !CANONICAL_EDIT_POLICY_CLASS_NAME_EDEFAULT.equals(canonicalEditPolicyClassName);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (canonicalEditPolicyClassName: ");
		result.append(canonicalEditPolicyClassName);
		result.append(')');
		return result.toString();
	}

} //GenChildContainerImpl
