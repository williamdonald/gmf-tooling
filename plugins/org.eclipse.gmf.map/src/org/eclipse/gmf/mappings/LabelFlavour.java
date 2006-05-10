/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmf.mappings;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.gmf.gmfgraph.DiagramLabel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label Flavour</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.gmf.mappings.LabelFlavour#getDiagramLabel <em>Diagram Label</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LabelFlavour#getFeatures <em>Features</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LabelFlavour#isReadOnly <em>Read Only</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LabelFlavour#getViewPattern <em>View Pattern</em>}</li>
 *   <li>{@link org.eclipse.gmf.mappings.LabelFlavour#getEditPattern <em>Edit Pattern</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLabelFlavour()
 * @model interface="true" abstract="true"
 * @generated
 */
public interface LabelFlavour extends EObject {
	/**
	 * Returns the value of the '<em><b>Diagram Label</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Diagram Label</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Diagram Label</em>' reference.
	 * @see #setDiagramLabel(DiagramLabel)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLabelFlavour_DiagramLabel()
	 * @model required="true"
	 * @generated
	 */
	DiagramLabel getDiagramLabel();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LabelFlavour#getDiagramLabel <em>Diagram Label</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Diagram Label</em>' reference.
	 * @see #getDiagramLabel()
	 * @generated
	 */
	void setDiagramLabel(DiagramLabel value);

	/**
	 * Returns the value of the '<em><b>Features</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Features</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' reference list.
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLabelFlavour_Features()
	 * @model type="org.eclipse.emf.ecore.EAttribute"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints ocl='features->forAll(f | f.eContainingClass.isSuperTypeOf(mapEntry.domainMetaElement))' description='Label attributes must be available in \'Domain Element\' EClass of the labeled mapping entry'"
	 * @generated
	 */
	EList getFeatures();

	/**
	 * Returns the value of the '<em><b>Read Only</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Read Only</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Read Only</em>' attribute.
	 * @see #setReadOnly(boolean)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLabelFlavour_ReadOnly()
	 * @model
	 * @generated
	 */
	boolean isReadOnly();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LabelFlavour#isReadOnly <em>Read Only</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Read Only</em>' attribute.
	 * @see #isReadOnly()
	 * @generated
	 */
	void setReadOnly(boolean value);

	/**
	 * Returns the value of the '<em><b>View Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pattern for java.text.MessageFormat to produce label on diagram
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>View Pattern</em>' attribute.
	 * @see #setViewPattern(String)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLabelFlavour_ViewPattern()
	 * @model
	 * @generated
	 */
	String getViewPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LabelFlavour#getViewPattern <em>View Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>View Pattern</em>' attribute.
	 * @see #getViewPattern()
	 * @generated
	 */
	void setViewPattern(String value);

	/**
	 * Returns the value of the '<em><b>Edit Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Pattern for java.text.MessageFormat to produce text for inplace editor; if not specified then viewPattern should be used
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Edit Pattern</em>' attribute.
	 * @see #setEditPattern(String)
	 * @see org.eclipse.gmf.mappings.GMFMapPackage#getLabelFlavour_EditPattern()
	 * @model
	 * @generated
	 */
	String getEditPattern();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.mappings.LabelFlavour#getEditPattern <em>Edit Pattern</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Edit Pattern</em>' attribute.
	 * @see #getEditPattern()
	 * @generated
	 */
	void setEditPattern(String value);

} // LabelFlavour