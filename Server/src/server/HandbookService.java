package server;

import java.util.List;

import org.apache.thrift.TException;

import dao.DatabaseController;
import dao.DatabaseException;
import dao.EntityNotFoundException;
import handbook.Handbook.Iface;
import handbook.InternalServiceException;
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
		
		try {
			articleId = dbController.getLastArticleId();
			authorId = dbController.getLastAuthorId();
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public String getArticleContent(int id) throws NoArticleException, InternalServiceException, TException {
		try {
			return dbController.getArticleContentById(id);
		} catch (EntityNotFoundException e) {
			throw new NoArticleException(id);
		} catch (DatabaseException e) {
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}

	@Override
	public List<ArticleHeader> getArticlesHeaders() throws InternalServiceException, TException {
		try {
			return dbController.getArticles();
		} catch (DatabaseException e) {
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}

	
	@Override
	public Author getAuthorById(int id) throws NoAuthorException, InternalServiceException, TException {
		try {
			return dbController.getAuthorById(id);
		} catch (EntityNotFoundException e) {
			throw new NoAuthorException(id);
		} catch (DatabaseException e) {
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}
	
	@Override
	public Author getAuthorByName(String name) throws NoAuthorException, InternalServiceException, TException {
		try {
			return dbController.getAuthorByName(name);
		} catch (EntityNotFoundException e) {
			throw new NoAuthorException(-1);
		} catch (DatabaseException e) {
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}
	
	@Override
	public int addAuthor(Author author) throws InternalServiceException, TException {
		author.setId(getNextAuthorId());
		
		try {
			dbController.addAuthor(author);
			return author.getId();
		} catch (DatabaseException e) {
			--authorId;
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}
	

	@Override
	public int addArticle(ArticleHeader article) throws NoArticleException, NoAuthorException, InternalServiceException, TException {
		article.setId(getNextArticleId());
		
		try {
			if (article.getParentId() != -1)
			{
				try {
					dbController.getArticleContentById(article.getParentId());
				} catch (EntityNotFoundException e) {
					throw new NoArticleException(article.getParentId());
				}
			}

			try {
				dbController.getAuthorById(article.getAuthorId());
			} catch (EntityNotFoundException e) {
				throw new NoAuthorException(article.getAuthorId());
			}
			
			dbController.addArticle(article);
			return article.getId();
		} catch (DatabaseException e) {
			--articleId;
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}

	@Override
	public void deleteArticle(int id) throws NoArticleException, InternalServiceException, TException {
		try {
			dbController.getArticleContentById(id);
			
			dbController.deleteArticle(id);

		} catch (EntityNotFoundException e) {
			throw new NoArticleException(id);
		} catch (DatabaseException e) {
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}
	
	@Override
	public void updateArticleHeader(ArticleHeader article) throws NoArticleException, NoAuthorException, InternalServiceException, TException {
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
			
			if (article.getParentId() != -1) {
				try {
					dbController.getArticleContentById(article.getParentId());
				} catch (EntityNotFoundException e) {
					throw new NoArticleException(article.getParentId());
				}
			}
			
			dbController.updateArticle(article);

		} catch (DatabaseException dbException) {
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}

	@Override
	public void updateArticleContent(int id, String content) throws NoArticleException, InternalServiceException, TException {
		try {
			dbController.getArticleContentById(id);
			
			dbController.updateArticleContent(id, content);

		} catch (EntityNotFoundException ex) {
			throw new NoArticleException(id);

		} catch (DatabaseException e) {
			throw new InternalServiceException("An error occured while fetching data from database.");
		}
	}
	
	
	private int getNextArticleId() {
		return ++articleId;
	}
	
	private int getNextAuthorId() {
		return ++authorId;
	}
}
