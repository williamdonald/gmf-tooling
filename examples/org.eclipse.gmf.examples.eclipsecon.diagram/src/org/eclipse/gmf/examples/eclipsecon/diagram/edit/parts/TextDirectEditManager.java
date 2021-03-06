package org.eclipse.gmf.examples.eclipsecon.diagram.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.gmf.runtime.common.ui.contentassist.ContentAssistantHelper;
import org.eclipse.gmf.runtime.diagram.ui.internal.l10n.DiagramFontRegistry;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.gef.ui.internal.parts.TextCellEditorEx;
import org.eclipse.gmf.runtime.gef.ui.internal.parts.WrapTextCellEditor;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.util.Assert;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;

/**
 * @generated
 */
public class TextDirectEditManager extends DirectEditManager {

	/**
	 * @generated
	 */
	private Color proposalPopupBackgroundColor = null;

	/**
	 * @generated
	 */
	private Color proposalPopupForegroundColor = null;

	/**
	 * @generated
	 */
	private boolean committed = false;

	/**
	 * @generated
	 */
	private boolean listenersAttached = true;

	/**
	 * @generated
	 */
	static private class WrapTextCellEditorLocator implements CellEditorLocator {

		private WrapLabel wrapLabel;

		public WrapTextCellEditorLocator(WrapLabel wrapLabel) {
			super();
			this.wrapLabel = wrapLabel;
		}

		public WrapLabel getWrapLabel() {
			return wrapLabel;
		}

		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getWrapLabel().getTextBounds().getCopy();
			getWrapLabel().translateToAbsolute(rect);

			if (getWrapLabel().isTextWrapped()
					&& getWrapLabel().getText().length() > 0)
				rect.setSize(new Dimension(text.computeSize(rect.width,
						SWT.DEFAULT)));
			else {
				int avr = FigureUtilities.getFontMetrics(text.getFont())
						.getAverageCharWidth();
				rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
						SWT.DEFAULT)).expand(avr * 2, 0));
			}

			if (!rect.equals(new Rectangle(text.getBounds())))
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
		}

	}

	/**
	 * @generated
	 */
	static private class TextCellEditorLocator implements CellEditorLocator {

		private Label label;

		public TextCellEditorLocator(Label label) {
			super();
			this.label = label;
		}

		public Label getLabel() {
			return label;
		}

		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getLabel().getTextBounds().getCopy();
			getLabel().translateToAbsolute(rect);

			int avr = FigureUtilities.getFontMetrics(text.getFont())
					.getAverageCharWidth();
			rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT,
					SWT.DEFAULT)).expand(avr * 2, 0));

			if (!rect.equals(new Rectangle(text.getBounds())))
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
		}

	}

	/**
	 * @generated
	 */
	public TextDirectEditManager(ITextAwareEditPart source) {
		super(source, getTextCellEditorClass(source),
				getCellEditorLocator(source.getFigure()));
	}

	/**
	 * @generated
	 */
	public TextDirectEditManager(GraphicalEditPart source, Class editorType,
			CellEditorLocator locator) {
		super(source, editorType, locator);
	}

	/**
	 * @generated
	 */
	private static CellEditorLocator getCellEditorLocator(IFigure label) {
		if (label instanceof Label) {
			return new TextCellEditorLocator((Label) label);
		}
		return new WrapTextCellEditorLocator((WrapLabel) label);
	}

	/**
	 * @generated
	 */
	private static Class getTextCellEditorClass(ITextAwareEditPart source) {
		IFigure label = source.getFigure();

		if (label instanceof WrapLabel && ((WrapLabel) label).isTextWrapped())
			return WrapTextCellEditor.class;

		return TextCellEditorEx.class;
	}

	/**
	 * @generated
	 */
	protected Font getScaledFont(IFigure label) {
		Font scaledFont = label.getFont();
		FontData data = scaledFont.getFontData()[0];
		Dimension fontSize = new Dimension(0, MapModeUtil.getMapMode(label)
				.DPtoLP(data.getHeight()));
		label.translateToAbsolute(fontSize);

		if (Math.abs(data.getHeight() - fontSize.height) < 2)
			fontSize.height = data.getHeight();

		data.setHeight(fontSize.height);
		Font newFont = DiagramFontRegistry.getInstance().getFont(null, data);
		return newFont;
	}

	/**
	 * @generated
	 */
	protected void initCellEditor() {
		committed = false;

		// Get the Text Compartments Edit Part
		ITextAwareEditPart textEP = (ITextAwareEditPart) getEditPart();

		setEditText(textEP.getEditText());

		IFigure label = textEP.getFigure();
		Assert.isNotNull(label);
		Text text = (Text) getCellEditor().getControl();
		// scale the font accordingly to the zoom level
		text.setFont(getScaledFont(label));

		// register a validator on the cell editor
		getCellEditor().setValidator(textEP.getEditTextValidator());

		if (textEP.getParser() != null) {
			IContentAssistProcessor processor = textEP.getCompletionProcessor();
			if (processor != null) {
				// register content assist
				proposalPopupBackgroundColor = new Color(getCellEditor()
						.getControl().getShell().getDisplay(), new RGB(254,
						241, 233));
				proposalPopupForegroundColor = new Color(getCellEditor()
						.getControl().getShell().getDisplay(), new RGB(0, 0, 0));

				ContentAssistantHelper.createTextContentAssistant(text,
						proposalPopupForegroundColor,
						proposalPopupBackgroundColor, processor);
			}
		}
	}

	/**
	 * @generated
	 */
	protected void commit() {
		Shell activeShell = Display.getCurrent().getActiveShell();
		if (activeShell != null
				&& getCellEditor().getControl().getShell().equals(
						activeShell.getParent())) {
			Control[] children = activeShell.getChildren();
			if (children.length == 1 && children[0] instanceof Table) {
				/*
				 * CONTENT ASSIST: focus is lost to the content assist pop up -
				 * stay in focus
				 */
				getCellEditor().getControl().setVisible(true);
				((TextCellEditorEx) getCellEditor()).setDeactivationLock(true);
				return;
			}
		}

		// content assist hacks
		if (committed) {
			bringDown();
			return;
		}
		committed = true;
		super.commit();
	}

	/**
	 * @generated
	 */
	protected void bringDown() {
		if (proposalPopupForegroundColor != null) {
			proposalPopupForegroundColor.dispose();
			proposalPopupForegroundColor = null;
		}
		if (proposalPopupBackgroundColor != null) {
			proposalPopupBackgroundColor.dispose();
			proposalPopupBackgroundColor = null;
		}

		// myee - RATLC00523014: crashes when queued in asyncExec()
		eraseFeedback();

		Display.getCurrent().asyncExec(new Runnable() {

			public void run() {
				// Content Assist hack - allow proper cleanup on childen
				// controls
				TextDirectEditManager.super.bringDown();
			}
		});
	}

	/**
	 * @generated
	 */
	public void setEditText(String toEdit) {

		// Get the cell editor
		CellEditor cellEditor = getCellEditor();

		// IF the cell editor doesn't exist yet...
		if (cellEditor == null) {
			// Do nothing
			return;
		}

		// Get the Text Compartment Edit Part
		ITextAwareEditPart textEP = (ITextAwareEditPart) getEditPart();

		// Get the Text control
		Text textControl = (Text) cellEditor.getControl();

		// Get the Text Edit Part's Figure (WrapLabel)
		IFigure label = textEP.getFigure();
		Assert.isNotNull(label);
		// Set the Figures text
		if (label instanceof Label) {
			((Label) label).setText(toEdit);
		} else {
			((WrapLabel) label).setText(toEdit);
		}

		// See RATLC00522324
		if (cellEditor instanceof TextCellEditorEx) {
			((TextCellEditorEx) cellEditor)
					.setValueAndProcessEditOccured(toEdit);
		} else {
			cellEditor.setValue(toEdit);
		}

		// Set the controls text and position the caret at the end of the text
		textControl.setSelection(toEdit.length());
	}

	/**
	 * @generated
	 */
	public void show(char initialChar) {

		show();
		// Set the cell editor text to the initial character
		String initialString = String.valueOf(initialChar);
		setEditText(initialString);

	}

	/**
	 * @generated
	 */
	public void show(Point location) {
		show();
		sendMouseClick(location);
	}

	/**
	 * @generated
	 */
	private void sendMouseClick(Point location) {

		final Display currDisplay = Display.getCurrent();
		final Point currLocation = location;
		new Thread() {
			Event event;

			public void run() {
				event = new Event();
				event.type = SWT.MouseDown;
				event.button = 1;
				event.x = currLocation.x;
				event.y = currLocation.y;
				currDisplay.post(event);
				event.type = SWT.MouseUp;
				currDisplay.post(event);
			}
		}.start();
	}

	/**
	 * @generated
	 */
	protected void unhookListeners() {
		if (listenersAttached) {
			listenersAttached = false;
			super.unhookListeners();
		}
	}

	/**
	 * @generated
	 */
	protected void setCellEditor(CellEditor editor) {
		super.setCellEditor(editor);
		if (editor != null) {
			listenersAttached = true;
		}
	}

	/**
	 * @generated
	 */
	public void showFeedback() {
		try {
			getEditPart().getRoot();
			super.showFeedback();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
