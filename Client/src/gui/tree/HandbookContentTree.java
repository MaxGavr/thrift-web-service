package gui.tree;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import handbook.ArticleHeader;



public class HandbookContentTree extends JTree {

	public HandbookContentTree() {}

	public HandbookContentTree(TreeNode arg0) {
		super(arg0);
	}

	
	@Override
	public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		
		// root node
		if (row == 0)
			return value.toString();
		
		return ((ArticleHeader)((DefaultMutableTreeNode)value).getUserObject()).getTitle();
	}

}
