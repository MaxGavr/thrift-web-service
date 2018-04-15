package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import handbook.ArticleHeader;
import handbook.Author;



public class DatabaseController {
	
	private enum QUERY_TYPE {
		AUTHOR_BY_ID,
		AUTHOR_BY_NAME,
		ADD_AUTHOR,
		ARTICLES_HEADERS,
		ARTICLE_CONTENT,
		ADD_ARTICLE,
		UPDATE_ARTICLE,
		UPDATE_CONTENT,
		DELETE_ARTICLE
	}
	
	private final String DB_NAME = "pascal_handbook";
	private final String TABLE_ARTICLES = DB_NAME + ".articles";
	private final String TABLE_AUTHORS = DB_NAME + ".authors";
	private final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	private final String DB_USER = "thrift";
	private final String DB_PASS = "thrift";
	
	private Connection connection;
	private Map<QUERY_TYPE, PreparedStatement> queries;
	
	
	public DatabaseController() {
		queries = new HashMap<QUERY_TYPE, PreparedStatement>(); 
		
		// TODO: change driver initialization method
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private boolean prepareQueries() {
		try {
			queries.put(QUERY_TYPE.AUTHOR_BY_ID,
				connection.prepareStatement("SELECT * FROM " + TABLE_AUTHORS + " WHERE author_id = ?;"));
			queries.put(QUERY_TYPE.AUTHOR_BY_NAME,
					connection.prepareStatement("SELECT * FROM " + TABLE_AUTHORS + " WHERE author_name = ?;"));
			queries.put(QUERY_TYPE.ADD_AUTHOR,
				connection.prepareStatement("INSERT INTO " + TABLE_AUTHORS + " (author_id, author_name, author_country) VALUES (?, ?, ?);"));
			queries.put(QUERY_TYPE.ARTICLES_HEADERS,
				connection.prepareStatement("SELECT * FROM " + TABLE_ARTICLES + ";"));
			queries.put(QUERY_TYPE.ARTICLE_CONTENT,
				connection.prepareStatement("SELECT article_content FROM " + TABLE_ARTICLES + " WHERE article_id = ?;"));
			queries.put(QUERY_TYPE.ADD_ARTICLE,
				connection.prepareStatement("INSERT INTO " + TABLE_ARTICLES + " (article_id, author_id, parent_id, creation_date, article_title) VALUES (?, ?, ?, ?, ?);"));
			queries.put(QUERY_TYPE.UPDATE_ARTICLE,
				connection.prepareStatement("UPDATE " + TABLE_ARTICLES + " SET article_title = ? WHERE article_id = ?;"));
			queries.put(QUERY_TYPE.UPDATE_CONTENT,
				connection.prepareStatement("UPDATE " + TABLE_ARTICLES + " SET article_content = ? WHERE article_id = ?;"));
			queries.put(QUERY_TYPE.DELETE_ARTICLE,
					connection.prepareStatement("DELETE FROM " + TABLE_ARTICLES + " WHERE article_id = ?"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean connect() {
		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			e.printStackTrace();
			
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException ignored) {
					ignored.printStackTrace();
				}
			}
			
			return false;
		}
		
		if (!prepareQueries()) {
			return false;
		}
		
		return true;
	}
	
	private void printSqlException(SQLException exception) {
		System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.err.println("SQL state: " + exception.getSQLState() + "; Error code: " + exception.getErrorCode());
		System.err.println("Message: " + exception.getMessage());
		System.err.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}


	public Author getAuthorById(int id) throws EntityNotFoundException, DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.AUTHOR_BY_ID);
		
		try {
			query.setInt(1, id);
			
			ResultSet result = query.executeQuery();
			
			if (result.next()) {
				String name = result.getString("author_name");
				String country = result.getString("author_country");
				
				return new Author(id, name, country);
			} else {
				throw new EntityNotFoundException();
			}

		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
	}
	
	public Author getAuthorByName(String name) throws EntityNotFoundException, DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.AUTHOR_BY_NAME);
		
		try {
			query.setString(1, name);
			
			ResultSet result = query.executeQuery();
			
			if (result.next()) {
				int id = result.getInt("author_id");
				String country = result.getString("author_country");
				
				return new Author(id, name, country);
			} else {
				throw new EntityNotFoundException();
			}

		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
	}

	public void addAuthor(Author author) throws DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.ADD_AUTHOR);
		
		try {
			query.setInt(1, author.getId());
			query.setString(2, author.getName());
			query.setString(3, author.getCountry());
			
			query.executeUpdate();
			
		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
	}

	public List<ArticleHeader> getArticles() throws DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.ARTICLES_HEADERS);
		
		List<ArticleHeader> articles = new ArrayList<ArticleHeader>();
		
		try {
			ResultSet result = query.executeQuery();
			
			ArticleHeader article;
			
			while (result.next()) {
				article = new ArticleHeader();
				
				article.setId(result.getInt("article_id"));
				article.setAuthorId(result.getInt("author_id"));
				article.setParentId(result.getInt("parent_id"));
				article.setDate(result.getDate("creation_date").toString());
				article.setTitle(result.getString("article_title"));
				
				articles.add(article);
			}
			
		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
		
		return articles;
	}

	public String getArticleContentById(int id) throws EntityNotFoundException, DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.ARTICLE_CONTENT);
		
		try {
			query.setInt(1, id);
			
			ResultSet result = query.executeQuery();
			
			if (result.next()) {
				return result.getString("article_content");
			} else {
				throw new EntityNotFoundException();
			}

		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
	}

	public void addArticle(ArticleHeader article) throws DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.ADD_ARTICLE);
		
		try {
			query.setInt(1, article.getId());
			query.setInt(2, article.getAuthorId());
			query.setInt(3, article.getParentId());
			query.setDate(4, Date.valueOf(article.getDate()));
			query.setString(5, article.getTitle());

			query.executeUpdate();
			
		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
	}

	public void updateArticle(ArticleHeader article) throws DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.UPDATE_ARTICLE);
		
		try {
			query.setString(1, article.getTitle());
			query.setInt(2, article.getId());
			
			query.executeUpdate();
			
		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
	}

	public void updateArticleContent(int id, String content) throws DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.UPDATE_CONTENT);
		
		try {
			query.setString(1, content);
			query.setInt(2, id);
			
			query.executeUpdate();

		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
	}

	public void deleteArticle(int id) throws DatabaseException {
		PreparedStatement query = queries.get(QUERY_TYPE.DELETE_ARTICLE);
		
		try {
			query.setInt(1, id);
			
			query.executeUpdate();
			
		} catch (SQLException e) {
			printSqlException(e);
			throw new DatabaseException();
		}
		
	}
}
