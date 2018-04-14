package server;

import java.util.List;

import org.apache.thrift.TException;

import dao.DatabaseController;
import dao.DatabaseException;
import dao.EntityNotFoundException;
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
		try {
			return dbController.getArticleContentById(id);
		} catch (EntityNotFoundException e) {
			throw new NoArticleException(id);
		} catch (DatabaseException e) {
			// TODO: handle db exception
			throw new TException();
		}
	}

	@Override
	public List<ArticleHeader> getArticlesHeaders() throws TException {
		try {
			return dbController.getArticles();
		} catch (DatabaseException e) {
			// TODO: handle db exception
			throw new TException();
		}
	}

	
	@Override
	public Author getAuthor(int id) throws NoAuthorException, TException {
		try {
			return dbController.getAuthorById(id);
		} catch (EntityNotFoundException e) {
			throw new NoAuthorException(id);
		} catch (DatabaseException e) {
			// TODO: handle db exception
			throw new TException();
		}
	}
	
	@Override
	public int addAuthor(Author author) throws TException {
		// TODO: validate author
		author.setId(getNextAuthorId());
		
		try {
			dbController.addAuthor(author);
			return author.getId();
		} catch (DatabaseException e) {
			--authorId;
		}
		
		// TODO: do not return -1
		return -1;
	}
	
	private int getNextAuthorId() {
		return authorId++;
	}
	

	@Override
	public int addArticle(ArticleHeader article) throws NoArticleException, NoAuthorException, TException {
		// TODO validate article
		article.setId(getNextArticleId());
		
		try {
			dbController.addArticle(article);
			return article.getId();
		} catch (DatabaseException e) {
			--articleId;
		}

		// TODO: do not return -1
		return -1;
	}

	@Override
	public void deleteArticle(int id) throws NoArticleException, TException {
		try {
			dbController.deleteArticle(id);
		} catch (DatabaseException e) {
			// TODO: handle db exception
			throw new TException();
		}
	}
	
	private int getNextArticleId() {
		return articleId++;
	}

	
	@Override
	public void updateArticleHeader(ArticleHeader article) throws NoArticleException, NoAuthorException, TException {
		// TODO validate header and check all fields
		try {
			try {
				dbController.getAuthorById(article.getAuthorId());
			} catch (EntityNotFoundException e) {
				throw new NoAuthorException(article.getAuthorId());
			}
			
			try {
				dbController.getArticleContentById(article.getId());
			} catch (EntityNotFoundException e) {
				throw new NoArticleException(article.getId());
			}
			
			try {
				dbController.getArticleContentById(article.getParentId());
			} catch (EntityNotFoundException e) {
				throw new NoArticleException(article.getParentId());
			}
			
			dbController.updateArticle(article);

		} catch (DatabaseException dbException) {
			// TODO: handle db exception
			throw new TException();
		}
	}

	@Override
	public void updateArticleContent(int id, String content) throws NoArticleException, TException {
		try {
			dbController.getArticleContentById(id);
			
			dbController.updateArticleContent(id, content);

		} catch (EntityNotFoundException ex) {
			throw new NoArticleException(id);

		} catch (DatabaseException e) {
			// TODO: handle db exception
			throw new TException();
		}
	}

}
