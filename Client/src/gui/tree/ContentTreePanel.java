package gui.tree;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

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
	
	
	public JComponent getRootComponent() {
		return treeView;
	}
	
	public void updateArticlesTree() {
		showArticles(parentWindow.getController().getArticlesList());
	}
	
	public void showArticles(List<ArticleHeader> articles) {
		
		DefaultMutableTreeNode root = getTreeRoot();
		root.removeAllChildren();
		
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
		
		((DefaultTreeModel)tree.getModel()).reload();
	}

	public int getSelectedArticleId() {
		
		TreePath selection = tree.getSelectionPath();
		
		// root selected
		if (selection == null || selection.getPathCount() == 1) {
			return -1;
		}		
		
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selection.getLastPathComponent();
		return ((ArticleHeader)selectedNode.getUserObject()).getId();
	}
	
	
	private void initializeControls() {
		DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Pascal Handbook");
		tree = new HandbookContentTree(topNode);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				onSelectedArticleChanged();
			}
		});
		
		treeView = new JScrollPane(tree);
	}
	
	private void onSelectedArticleChanged() {
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (selectedNode == null || selectedNode == getTreeRoot()) {
			return;
		}
		
		ArticleHeader selectedArticle = (ArticleHeader) selectedNode.getUserObject();
		parentWindow.getArticlePanel().showArticle(selectedArticle);
	}

	private DefaultMutableTreeNode getTreeRoot() {
		return (DefaultMutableTreeNode) ((DefaultTreeModel)tree.getModel()).getRoot();
	}
	
}
