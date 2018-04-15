package gui.tree;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import gui.AppWindow;
import handbook.ArticleHeader;



public class ContentTreePanel {
	
	private AppWindow parentWindow;
	
	private JScrollPane treeView;
	private HandbookContentTree tree;

	

	public ContentTreePanel(AppWindow parentWindow) {
		this.parentWindow = parentWindow;

		initializeControls();
	}
	
	private void  initializeControls() {
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Pascal Handbook");
		tree = new HandbookContentTree(topNode);
		
		treeView = new JScrollPane(tree);
	}
	
	public JComponent getRootComponent() {
		return treeView;
	}
	
	public void showArticles(List<ArticleHeader> articles) {
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Pascal Handbook");
		
		// article id to node
		Map<Integer, DefaultMutableTreeNode> treeNodes = new HashMap<Integer, DefaultMutableTreeNode>();
		
		ListIterator<ArticleHeader> it = articles.listIterator();

		// first, get all root articles
		while (it.hasNext()) {
			ArticleHeader article = it.next();
			if (article.getParentId() == -1) {

				DefaultMutableTreeNode articleNode = new DefaultMutableTreeNode(article);
				root.add(articleNode);

				treeNodes.put(article.getId(), articleNode);
				it.remove();
			}
		}
		
		while (!articles.isEmpty()) {
			it = articles.listIterator();
			
			while (it.hasNext()) {
				ArticleHeader article = it.next();
				
				DefaultMutableTreeNode parentNode = treeNodes.get(article.getParentId());
				if (parentNode != null) {
					DefaultMutableTreeNode articleNode = new DefaultMutableTreeNode(article);
					parentNode.add(articleNode);
					
					treeNodes.put(article.getId(), articleNode);
					it.remove();
				}
			}
		}
		
		tree = new HandbookContentTree(root);
	}

}
