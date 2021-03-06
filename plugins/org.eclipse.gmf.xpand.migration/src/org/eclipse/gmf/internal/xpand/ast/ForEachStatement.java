/*
 * <copyright>
 *
 * Copyright (c) 2005-2006 Sven Efftinge and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sven Efftinge - Initial API and implementation
 *
 * </copyright>
 */
package org.eclipse.gmf.internal.xpand.ast;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.gmf.internal.xpand.BuiltinMetaModel;
import org.eclipse.gmf.internal.xpand.expression.AnalysationIssue;
import org.eclipse.gmf.internal.xpand.expression.EvaluationException;
import org.eclipse.gmf.internal.xpand.expression.Variable;
import org.eclipse.gmf.internal.xpand.expression.ast.Expression;
import org.eclipse.gmf.internal.xpand.expression.ast.Identifier;
import org.eclipse.gmf.internal.xpand.migration.ForEachAnalyzeTrace;
import org.eclipse.gmf.internal.xpand.model.XpandExecutionContext;
import org.eclipse.gmf.internal.xpand.model.XpandIterator;

/**
 * @author Sven Efftinge
 */
public class ForEachStatement extends Statement {

    public static final String ITERATOR_VAR_NAME = "iterator";

    private final Statement[] body;

    private final Expression target;

    private final Expression separator;

    private final Identifier variable;

    private final Identifier iteratorName;

    public ForEachStatement(final int start, final int end, final int line, final int startOffset, final int endOffset, final Identifier variable,
            final Expression target, final Statement[] body, final Expression separator, final Identifier iterator) {
        super(start, end, line, startOffset, endOffset);
        this.variable = variable;
        this.target = target;
        this.body = body;
        this.separator = separator;
        iteratorName = iterator;
    }

    public Statement[] getBody() {
        return body;
    }

    public Expression getSeparator() {
        return separator;
    }

    public Expression getTarget() {
        return target;
    }

    public Identifier getVariable() {
        return variable;
    }

    public void analyze(XpandExecutionContext ctx, final Set<AnalysationIssue> issues) {
    	EClassifier t = getTarget().analyze(ctx, issues);
    	EClassifier sepT = null;
        if (getSeparator() != null) {
        	sepT = getSeparator().analyze(ctx, issues);
            if (!BuiltinMetaModel.isAssignableFrom(EcorePackage.eINSTANCE.getEString(), sepT)) {
                issues.add(new AnalysationIssue(AnalysationIssue.Type.INCOMPATIBLE_TYPES, "String expected!", target));
            }
        }
        createAnalyzeTrace(ctx, new ForEachAnalyzeTrace(t, sepT));
        if (t != null) {
            if (BuiltinMetaModel.isCollectionType(t)) {
                if (BuiltinMetaModel.isParameterizedType(t)) {
                    t = BuiltinMetaModel.getInnerType(t);
                } else {
                    t = EcorePackage.eINSTANCE.getEJavaObject();
                }
            } else {
                issues.add(new AnalysationIssue(AnalysationIssue.Type.INCOMPATIBLE_TYPES, "Collection type expected!",
                        target));
                return;
            }
        }
        ctx = ctx.cloneWithVariable(new Variable(getVariable().getValue(), t));
        if (iteratorName != null) {
            ctx = ctx.cloneWithVariable(new Variable(iteratorName.getValue(), BuiltinMetaModel.ITERATOR_TYPE));
        }
        for (Statement statement : getBody()) {
            statement.analyze(ctx, issues);
        }
    }

    @Override
    public void evaluateInternal(XpandExecutionContext ctx) {
        final Object o = getTarget().evaluate(ctx);

        if (!(o instanceof Collection)) {
			throw new EvaluationException("Collection expected!", getTarget());
		}
        final Collection<?> col = (Collection<?>) o;
        final String sep = (String) (getSeparator() != null ? getSeparator().evaluate(ctx) : null);
        final XpandIterator iterator = new XpandIterator(col.size());

        if (iteratorName != null) {
            ctx = ctx.cloneWithVariable(new Variable(iteratorName.getValue(), iterator));
        }
        for (final Iterator<?> iter = col.iterator(); iter.hasNext();) {
            final Object element = iter.next();
            ctx = ctx.cloneWithVariable(new Variable(getVariable().getValue(), element));
            for (int i = 0; i < getBody().length; i++) {
                getBody()[i].evaluate(ctx);
            }
            if ((sep != null) && iter.hasNext()) {
                ctx.getOutput().write(sep);
            }
            iterator.increment();
        }
    }
}
