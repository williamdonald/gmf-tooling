/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmf.codegen.gmfgen;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Metric Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getContainer <em>Container</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.gmf.codegen.gmfgen.GMFGenPackage#getGenMetricRule()
 * @model
 * @generated
 */
public interface GenMetricRule extends GenRuleBase {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Unique key identifying this metric an abbreviation for this metric
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see #setKey(String)
	 * @see org.eclipse.gmf.codegen.gmfgen.GMFGenPackage#getGenMetricRule_Key()
	 * @model required="true"
	 * @generated
	 */
	String getKey();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see #getKey()
	 * @generated
	 */
	void setKey(String value);

	/**
	 * Returns the value of the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * Expression that calculates the value of this metric which is of EDataType that encapsulates java.lang.Number subclasses or their primitives
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Rule</em>' containment reference.
	 * @see #setRule(ValueExpression)
	 * @see org.eclipse.gmf.codegen.gmfgen.GMFGenPackage#getGenMetricRule_Rule()
	 * @model containment="true" required="true"
	 *        annotation="http://www.eclipse.org/gmf/2005/constraints/meta def='context' ocl='target.getContext()'"
	 * @generated
	 */
	ValueExpression getRule();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getRule <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule</em>' containment reference.
	 * @see #getRule()
	 * @generated
	 */
	void setRule(ValueExpression value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The target representing the context this metric is evaluated in
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Target</em>' containment reference.
	 * @see #setTarget(GenMeasurable)
	 * @see org.eclipse.gmf.codegen.gmfgen.GMFGenPackage#getGenMetricRule_Target()
	 * @model containment="true" required="true"
	 * @generated
	 */
	GenMeasurable getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getTarget <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' containment reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(GenMeasurable value);

	/**
	 * Returns the value of the '<em><b>Container</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.gmf.codegen.gmfgen.GenMetricContainer#getMetrics <em>Metrics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * The containing metric container of this metric rule
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Container</em>' container reference.
	 * @see #setContainer(GenMetricContainer)
	 * @see org.eclipse.gmf.codegen.gmfgen.GMFGenPackage#getGenMetricRule_Container()
	 * @see org.eclipse.gmf.codegen.gmfgen.GenMetricContainer#getMetrics
	 * @model opposite="metrics" required="true"
	 * @generated
	 */
	GenMetricContainer getContainer();

	/**
	 * Sets the value of the '{@link org.eclipse.gmf.codegen.gmfgen.GenMetricRule#getContainer <em>Container</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Container</em>' container reference.
	 * @see #getContainer()
	 * @generated
	 */
	void setContainer(GenMetricContainer value);

} // GenMetricRule
