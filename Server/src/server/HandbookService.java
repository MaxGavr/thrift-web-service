package server;

import java.util.List;

import org.apache.thrift.TException;

import dao.DatabaseController;

import handbook.Handbook.Iface;

import handbook.ArticleHeader;
import handbook.Author;
import handbook.NoArticleException;
import handbook.NoAuthorException;



public class HandbookService implements Iface {
	
	private DatabaseController dbController;
	
	private int authorId = 1;
	private int articleId = 1;


	public HandbookService(DatabaseController dbController) {
		this.dbController = dbController;
	}

	
	@Override
	public String getArticleContent(int id) throws NoArticleException, TException {
		return dbController.getArticleContentById(id);
	}

	@Override
	public List<ArticleHeader> getArticlesHeaders() throws TException {
		return dbController.getArticles();
	}

	
	@Override
	public Author getAuthor(int id) throws NoAuthorException, TException {
		return dbController.getAuthorById(id);
	}
	
	@Override
	public int addAuthor(Author author) throws TException {
		// TODO: validate author
		author.setId(getNextAuthorId());
		
		if (dbController.addAuthor(author)) {
			return author.getId();
		} else {
			--authorId;
			return 0;
		}
	}
	
	private int getNextAuthorId() {
		return authorId++;
	}
	

	@Override
	public int addArticle(ArticleHeader article) throws NoArticleException, NoAuthorException, TException {
		return 0;
	}

	@Override
	public void deleteArticle(int id) throws NoArticleException, TException {
		// TODO Auto-generated method stub

	}
	
	private int getNextArticleId() {
		return articleId++;
	}

	
	@Override
	public void updateArticleHeader(ArticleHeader article) throws NoArticleException, NoAuthorException, TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateArticleContent(int id, String content) throws NoArticleException, TException {
		// TODO Auto-generated method stub

	}

}
