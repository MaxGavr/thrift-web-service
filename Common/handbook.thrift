namespace java handbook

typedef i32 ArticleID
typedef i32 AuthorID

struct ArticleHeader
{
    1: ArticleID id,
    2: ArticleID parentId,
    3: AuthorID authorId,
    4: string date,
    5: string title,
}

struct Author
{
    1: AuthorID id,
    2: string name,
    3: string country,
}

exception NoArticleException
{
    1: ArticleID missingId
}

exception NoAuthorException
{
    1: AuthorID missingId
}

exception InternalServiceException
{
    1: string message
}

service Handbook
{
    string getArticleContent(1: ArticleID id)
        throws (1: NoArticleException ex, 2: InternalServiceException error),
    list<ArticleHeader> getArticlesHeaders()
        throws (1: InternalServiceException error),
    
    Author getAuthorById(1: AuthorID id)
        throws (1: NoAuthorException ex, 2: InternalServiceException error),
    Author getAuthorByName(1: string authorName)
        throws (1: NoAuthorException ex, 2: InternalServiceException error),
    AuthorID addAuthor(1: Author author)
        throws (1: InternalServiceException error),

    ArticleID addArticle(1: ArticleHeader article)
        throws (1: NoArticleException noParentArticle, 2: NoAuthorException noAuthor, 3: InternalServiceException error),
    void deleteArticle(1: ArticleID id)
        throws (1: NoArticleException ex, 2: InternalServiceException error),

    void updateArticleHeader(1: ArticleHeader article)
        throws (1: NoArticleException noArticle, 2: NoAuthorException noAuthor, 3: InternalServiceException error),
    void updateArticleContent(1: ArticleID id, 2: string content)
        throws (1: NoArticleException ex, 2: InternalServiceException error),
}
