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


	public HandbookService(DatabaseController dbController) {
		this.dbController = dbController;
	}

	@Override
	public String getArticleContent(int id) throws NoArticleException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ArticleHeader> getArticlesHeaders() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Author getAuthor(int id) throws NoAuthorException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addAuthor(Author author) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addArticle(ArticleHeader article) throws NoArticleException, NoAuthorException, TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteArticle(int id) throws NoArticleException, TException {
		// TODO Auto-generated method stub

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
