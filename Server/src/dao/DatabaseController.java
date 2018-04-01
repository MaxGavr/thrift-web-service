package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import handbook.ArticleHeader;
import handbook.Author;
import handbook.NoArticleException;
import handbook.NoAuthorException;



public class DatabaseController {
	
	private enum QUERY_TYPE {
		AUTHOR_BY_ID,
		ADD_AUTHOR,
		ARTICLES_HEADERS,
		ARTICLE_CONTENT
	}
	
	private final String DB_NAME = "pascal_handbook";
	private final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;
	
	private Connection connection;
	private Map<QUERY_TYPE, PreparedStatement> queries;
	
	
	public DatabaseController() {
		queries = new HashMap<QUERY_TYPE, PreparedStatement>(); 
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private boolean prepareStatements() {
		try {
			queries.put(QUERY_TYPE.AUTHOR_BY_ID,
				connection.prepareStatement("SELECT * FROM " + DB_NAME + ".authors WHERE author_id = ?"));
			queries.put(QUERY_TYPE.ADD_AUTHOR,
				connection.prepareStatement("INSERT INTO " + DB_NAME + ".authors (author_id, author_name, author_country) VALUES (?, ?, ?)"));
			queries.put(QUERY_TYPE.ARTICLES_HEADERS,
				connection.prepareStatement("SELECT * FROM " + DB_NAME + ".articles"));
			queries.put(QUERY_TYPE.ARTICLE_CONTENT,
					connection.prepareStatement("SELECT article_content FROM " + DB_NAME + ".articles WHERE article_id = ?"));
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean connect() {
		try {
			connection = DriverManager.getConnection(DB_URL, "thrift", "thrift");
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
		
		if (!prepareStatements()) {
			return false;
		}
		
		return true;
	}

	public Author getAuthorById(int id) throws NoAuthorException {
		PreparedStatement query = queries.get(QUERY_TYPE.AUTHOR_BY_ID);
		
		try {
			query.setInt(1, id);
			
			ResultSet result = query.executeQuery();
			
			if (result.next()) {
				String name = result.getString("author_name");
				String country = result.getString("author_country");
				
				return new Author(id, name, country);
			} else {
				throw new NoAuthorException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: find out better solution
			throw new NoAuthorException();
		}
	}

	public boolean addAuthor(Author author) {
		PreparedStatement query = queries.get(QUERY_TYPE.ADD_AUTHOR);
		
		try {
			query.setInt(1, author.getId());
			query.setString(2, author.getName());
			query.setString(3, author.getCountry());
			
			query.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return false;
	}

	public List<ArticleHeader> getArticles() {
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
			e.printStackTrace();
		}
		
		return articles;
	}

	public String getArticleContentById(int id) throws NoArticleException {
		PreparedStatement query = queries.get(QUERY_TYPE.ARTICLE_CONTENT);
		
		try {
			query.setInt(1, id);
			
			ResultSet result = query.executeQuery();
			
			if (result.next()) {
				return result.getString("article_content");
			} else {
				throw new NoArticleException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: find out better solution
			throw new NoArticleException();
		}
	}
}
