package gui.tree;

import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import handbook.ArticleHeader;



public class HandbookContentTree extends JTree {

	public HandbookContentTree() {}

	public HandbookContentTree(Object[] arg0) {
		super(arg0);
	}

	public HandbookContentTree(Vector<?> arg0) {
		super(arg0);
	}

	public HandbookContentTree(Hashtable<?, ?> arg0) {
		super(arg0);
	}

	public HandbookContentTree(TreeNode arg0) {
		super(arg0);
	}

	public HandbookContentTree(TreeModel arg0) {
		super(arg0);
	}

	public HandbookContentTree(TreeNode arg0, boolean arg1) {
		super(arg0, arg1);
	}
	
	@Override
	public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		// root node
		if (row == 0)
			return value.toString();
		
		return ((ArticleHeader)value).getTitle();
	}

}
