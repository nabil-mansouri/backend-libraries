package com.nm.documents.aspose;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

import org.apache.pdfbox.io.IOUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.aspose.words.CompositeNode;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.Node;
import com.aspose.words.NodeType;
import com.aspose.words.Paragraph;
import com.aspose.words.ParagraphAlignment;
import com.aspose.words.Run;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import com.aspose.words.Shape;
import com.aspose.words.ShapeType;
import com.aspose.words.StyleIdentifier;
import com.aspose.words.Table;
import com.aspose.words.TableStyleOptions;
import com.aspose.words.WrapType;
import com.nm.app.utils.UUIDUtils;
import com.nm.documents.PositionModel;
import com.nm.documents.TableModel;
import com.nm.documents.TableModelCell;
import com.nm.documents.WordBuilder;
import com.nm.documents.aspose.parser.PatternFinder;
import com.nm.documents.aspose.parser.PatternState;

/**
 * 
 * @author MANSOURI Nabil
 *
 */
public class WordBuilderAspose extends WordBuilder {
	private Document document;
	private DocumentBuilder builder;

	public WordBuilderAspose() {
	}

	@Override
	public void removeParagraph(PositionModel index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pushPngImage(PositionModel position, InputStream is, String name, int height) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertText(String text) throws Exception {
		builder.insertParagraph();
		builder.writeln(text);
	}

	protected void iterateInner(PatternFinder finder, CompositeNode<?> parentNode) throws Exception {
		// This is the most efficient way to loop through immediate children of
		// a node.
		for (Node childNode = parentNode.getFirstChild(); childNode != null; childNode = childNode.getNextSibling()) {
			finder.accept(new PatternState(childNode.getText(), childNode));
			// Recurse into the node if it is a composite node.
			if (childNode.isComposite())
				iterateInner(finder, (CompositeNode<?>) childNode);
		}
	}

	@Override
	public void iterate(PatternFinder finder) throws Exception {
		iterateInner(finder, document);
	}

	public void clear(PatternState state) {
		if (state.getNode().getParentNode() == null) {
			return;
		}
		state.getNode().getParentNode().removeChild(state.getNode());
	}

	public void close() {
		// TODO
	}

	@Override
	public void createDoc(InputStream input) throws Exception {
		// Blank Document
		this.document = new Document(input);
		this.builder = new DocumentBuilder(this.document);
	}

	@Override
	public void createDoc() throws Exception {
		// Blank Document
		this.document = new Document();
		this.builder = new DocumentBuilder(this.document);
	}

	@Override
	public void newPage() throws Exception {
		this.builder.insertBreak(com.aspose.words.BreakType.PAGE_BREAK);
	}

	@Override
	public void replaceTextbeforeRun(PositionModel position, String before, String after) throws Exception {
		Node first = position.getNode().iterator().next();
		if (first.getParentNode() == null) {
			System.out.println(first.getText());
			((Run) first).setText(after);
			return;
		}
		Paragraph p = (Paragraph) first.getParentNode();
		String text = "";
		for (Node n : position.getNode()) {
			Assert.isTrue(n.getNodeType() == NodeType.RUN);
			text += n.getText();
		}
		Run cloneRun = (Run) first.deepClone(true);
		System.out.println(text);
		cloneRun.setText(StringUtils.replace(text, before, after));
		System.out.println(cloneRun.getText());
		//
		p.insertBefore(cloneRun, first);
		for (Node n : position.getNode()) {
			Run r = ((Run) n);
			r.setText("");
		}
		builder.moveTo(cloneRun);
	}

	@Override
	public void insertTextbeforeRun(PositionModel position, String after) throws Exception {
		Node first = position.getNode().iterator().next();
		for (Node n : position.getNode()) {
			Assert.isTrue(n.getNodeType() == NodeType.RUN);
		}
		Run newRun = (Run) first.deepClone(true);
		newRun.setText(after);
		Paragraph p = (Paragraph) first.getParentNode();
		//
		p.insertBefore(newRun, first);
		for (Node n : position.getNode()) {
			Run r = ((Run) n);
			p.removeChild(r);
		}
		builder.moveTo(newRun);
	}

	@Override
	public void moveBeforeParagraph(PositionModel position) throws Exception {
		Node node = position.getNode().iterator().next();
		if (node.getNodeType() == NodeType.RUN) {
			node = node.getParentNode();
		}
		Paragraph p = new Paragraph(document);
		node.getParentNode().insertBefore(p, node);
		builder.moveTo(p);
	}

	@Override
	public void moveAfterParagraph(PositionModel position) throws Exception {
		Node node = position.getNode().iterator().next();
		if (node.getNodeType() == NodeType.RUN) {
			node = node.getParentNode();
		}
		Paragraph p = new Paragraph(document);
		node.getParentNode().insertAfter(p, node);
		builder.moveTo(p);
	}

	@Override
	public void pushPngImage(InputStream is, String name) throws Exception {
		double w = 0, h = 0;
		for (Section s : this.document.getSections()) {
			double marg = s.getPageSetup().getLeftMargin() + s.getPageSetup().getRightMargin();
			w = s.getPageSetup().getPageWidth();
			w -= marg;
			h = s.getPageSetup().getPageHeight();
			break;
		}
		pushPngImage(is, name, (int) w, (int) (h * 0.5));
	}

	@Override
	public void pushPngImage(PositionModel m, InputStream is, String name) throws Exception {
		moveBeforeParagraph(m);
		pushPngImage(is, name);
	}

	public void pushPngImage(InputStream is, String name, int width, int height) throws Exception {
		Shape shape = new Shape(document, ShapeType.RECTANGLE);// 75);
		shape.getImageData().setImageBytes(IOUtils.toByteArray(is));
		shape.setAllowOverlap(false);
		shape.setBehindText(false);
		shape.setDistanceLeft(0d);
		shape.setDistanceRight(0d);
		shape.setHeight(height);
		shape.setWidth(width);
		shape.setWrapType(WrapType.SQUARE);
		this.builder.insertParagraph().appendChild(shape);
		// builder.insertImage(is, RelativeHorizontalPosition.MARGIN, 0,
		// RelativeVerticalPosition.MARGIN, 0, width, height, WrapType.SQUARE);
		is.close();
	}

	@Override
	public void importHtml(String html) throws Exception {
		builder.insertHtml(html);
	}

	public void buildTable(PositionModel position, TableModel tableModel) throws Exception {
		moveBeforeParagraph(position);
		this.builder.insertParagraph();
		Table table = this.builder.startTable();
		//
		int align = builder.getParagraphFormat().getAlignment();
		builder.getParagraphFormat().setAlignment(ParagraphAlignment.CENTER);
		this.builder.getRowFormat().setHeadingFormat(true);
		for (TableModelCell name : tableModel.getColumnNames()) {
			this.builder.insertCell();
			this.builder.writeln(name.getValue());
		}
		this.builder.endRow();
		this.builder.getRowFormat().setHeadingFormat(false);
		//
		for (Collection<TableModelCell> row : tableModel.getRows()) {
			for (TableModelCell r : row) {
				this.builder.insertCell();
				this.builder.writeln(r.getValue());
			}
			this.builder.endRow();
		}
		this.builder.endTable();
		//
		table.setStyleIdentifier(StyleIdentifier.MEDIUM_SHADING_1_ACCENT_1);
		table.setStyleOptions(TableStyleOptions.ROW_BANDS | TableStyleOptions.FIRST_ROW);
		table.setAllowAutoFit(true);
		builder.getParagraphFormat().setAlignment(align);
	}

	@Override
	public void writeAsHtml(OutputStream real) throws Exception {
		ByteArrayOutputStream fake = new ByteArrayOutputStream();
		document.save(fake, SaveFormat.HTML);
		WordBuilder poi = WordBuilder.poi();
		poi.createDoc(new ByteArrayInputStream(fake.toByteArray()));
		poi.removeRowHaving("Aspose");
		poi.write(real);
	}

	@Override
	public void removeRowHaving(String pattern) {
		// TODO Auto-generated method stub
	}

	protected void prepareWrite(OutputStream real) throws Exception {
		document.updateFields();
		ByteArrayOutputStream fake = new ByteArrayOutputStream();
		document.save(fake, SaveFormat.DOCX);
		WordBuilder poi = WordBuilder.poi();
		poi.createDoc(new ByteArrayInputStream(fake.toByteArray()));
		// poi.removeRowHaving("Aspose");
		poi.write(real);
	}

	@Override
	public File writeFile() throws Exception {
		File f = File.createTempFile(UUIDUtils.uuid(16), ".tmp");
		this.write(new FileOutputStream(f));
		return f;
	}

	@Override
	public void write(OutputStream output) throws Exception {
		prepareWrite(output);
		output.close();
		System.out.println("createdocument.docx written successully");
	}
}
