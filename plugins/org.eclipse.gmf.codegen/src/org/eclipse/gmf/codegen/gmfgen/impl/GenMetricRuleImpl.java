/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.gmf.codegen.gmfgen.GenMeasurable;
import org.eclipse.gmf.codegen.gmfgen.GenMetricContainer;
import org.eclipse.gmf.codegen.gmfgen.GenMetricRule;
import org.eclipse.gmf.codegen.gmfgen.ValueExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Metric Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getContainer <em>Container</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GenMetricRuleImpl extends GenRuleBaseImpl implements GenMetricRule {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected ValueExpression rule = null;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected GenMeasurable target = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenMetricRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenMetricRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueExpression getRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRule(ValueExpression newRule, NotificationChain msgs) {
		ValueExpression oldRule = rule;
		rule = newRule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__RULE, oldRule, newRule);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRule(ValueExpression newRule) {
		if (newRule != rule) {
			NotificationChain msgs = null;
			if (rule != null)
				msgs = ((InternalEObject)rule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_METRIC_RULE__RULE, null, msgs);
			if (newRule != null)
				msgs = ((InternalEObject)newRule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_METRIC_RULE__RULE, null, msgs);
			msgs = basicSetRule(newRule, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__RULE, newRule, newRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenMeasurable getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(GenMeasurable newTarget, NotificationChain msgs) {
		GenMeasurable oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(GenMeasurable newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_METRIC_RULE__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_METRIC_RULE__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenMetricContainer getContainer() {
		if (eContainerFeatureID != GMFGenPackage.GEN_METRIC_RULE__CONTAINER) return null;
		return (GenMetricContainer)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainer(GenMetricContainer newContainer) {
		if (newContainer != eInternalContainer() || (eContainerFeatureID != GMFGenPackage.GEN_METRIC_RULE__CONTAINER && newContainer != null)) {
			if (EcoreUtil.isAncestor(this, newContainer))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainer != null)
				msgs = ((InternalEObject)newContainer).eInverseAdd(this, GMFGenPackage.GEN_METRIC_CONTAINER__METRICS, GenMetricContainer.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newContainer, GMFGenPackage.GEN_METRIC_RULE__CONTAINER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__CONTAINER, newContainer, newContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_METRIC_RULE__CONTAINER, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				return basicSetRule(null, msgs);
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				return basicSetTarget(null, msgs);
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return eBasicSetContainer(null, GMFGenPackage.GEN_METRIC_RULE__CONTAINER, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID) {
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_METRIC_CONTAINER__METRICS, GenMetricContainer.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				return getKey();
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				return getRule();
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				return getTarget();
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return getContainer();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				setKey((String)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				setRule((ValueExpression)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				setTarget((GenMeasurable)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				setContainer((GenMetricContainer)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void eUnset(int featureID) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				setRule((ValueExpression)null);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				setTarget((GenMeasurable)null);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				setContainer((GenMetricContainer)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				return rule != null;
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				return target != null;
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return getContainer() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (key: ");
		result.append(key);
		result.append(')');
		return result.toString();
	}

} //GenMetricRuleImpl
