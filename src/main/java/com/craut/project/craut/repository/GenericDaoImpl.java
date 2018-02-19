package com.craut.project.craut.repository;

import com.craut.project.craut.model.*;
import com.craut.project.craut.service.dto.CommentResponseDto;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
//import org.hibernate.search.FullTextSession;
//import org.hibernate.search.Search;
//import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class GenericDaoImpl<T>   {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(T p) {
        Session session = getSession();
        session.saveOrUpdate(p);
    }
    public void del(T p) {
        if(p == null){
            return;
        }
        Session session = getSession();
        session.delete(p);
    }
    public List<T> sortByDesc(String tableName,String columnName) {
        Session session = getSession();
        Query query=session.createSQLQuery("SELECT * FROM "+tableName+" ORDER BY " + columnName +" DESC");
        List<T> object=query.list();
        return object;
    }
    public List<T> sortByAsc(String tableName,String columnName) {
        Session session = getSession();
        Query query=session.createQuery("FROM "+tableName+" ORDER BY " + columnName +" ASC");
        List<T> object=query.list();
        return object;
    }
    public void deletObject(Generic<T> object) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(object);
        tx.commit();
        session.close();
    }
    public List<T> list(String tablename)
    {
        Session session =getSession();
        String q="FROM "+tablename;
        Query query=session.createQuery(q);
        List<T> object=query.list();
        return object;
    }

    public List<InstructionEntity> findProjectByTag(String tag, String tableName, String parametr) {
        Session session =getSession();
        String q="FROM "+tableName+" m WHERE m."+parametr+"=:"+parametr;
        Query query=session.createQuery(q);
        query.setParameter(parametr,tag);

        List<TagsEntity> object=query.list();
        List<InstructionEntity> list = new ArrayList<>();
        if(object!=null &&!object.isEmpty()){
            for(TagsEntity obj:object)
            {
                list.add(obj.getInstructionEntity());
            }
            return list;
        }else{
            return null;
        }
    }
    public ArrayList<Object> findTagByProject(InstructionEntity project, String tableName, String parametr) {
        Session session =getSession();
        String q="FROM "+tableName+" m WHERE m."+parametr+"=:"+parametr;
        Query query=session.createQuery(q);
        query.setParameter(parametr,project);
        List<TagsEntity> object=query.list();
        ArrayList<Object> list = new ArrayList<>();
        if(object!=null &&!object.isEmpty()){
            for(TagsEntity obj:object)
            {
                list.add(obj.getName());
            }
            return list;
        }else{
            return null;
        }
    }
    public CommentResponseDto findCommentByProject(InstructionEntity project, String tableName, String parametr) {
        Session session =getSession();
        String q="FROM "+tableName+" m WHERE m."+parametr+"=:"+parametr;
        Query query=session.createQuery(q);
        query.setParameter(parametr,project);
        List<CommentsEntity> object=query.list();
        ArrayList<Object> list = new ArrayList<>();
        ArrayList<UserEntity> listUsers = new ArrayList<>();
        if(object!=null &&!object.isEmpty()){
            for(CommentsEntity obj:object) {
                list.add(obj.getComment());
                listUsers.add(obj.getUserEntity());
            }
            return new CommentResponseDto(list,listUsers); //TODO fix bug with null
        }else{
            return new CommentResponseDto(null,null);
        }
    }
    public void update(String tableName,String column,T parametr,String changeColumn,T changeParametr)
    {
        Session session =getSession();
        String q = "UPDATE " + tableName + " set "+changeColumn+"="+changeParametr
                +" where "+column+" ="+parametr;
        Query query=session.createQuery(q);
        int result = query.executeUpdate();
    }
    public void delete(String tableName,String column,T parametr)
    {
        Session session =getSession();
        Query query =  session.createQuery("delete  " + tableName+"  where "+column+" ="+parametr);
        int result = query.executeUpdate();
    }

    public T findByParametr(T userName, String tableName,String parametr) {
        Session session =getSession();
        String q="FROM "+tableName+" m WHERE m."+parametr+"=:"+parametr;
        Query query=session.createQuery(q);
        query.setParameter(parametr,userName);

        List<Generic<T>> object=query.list();
        if(object!=null &&!object.isEmpty()){
            T t = (T) object.get(0);
            return t;
        }else{
            return null;
        }
    }
    public List<T> findListByParametr(T userName, String tableName,String parametr) {
        Session session =getSession();
        String q="FROM "+tableName+" m WHERE m."+parametr+"=:"+parametr;
        Query query=session.createQuery(q);
        query.setParameter(parametr,userName);

        List<T> object=query.list();
        if(object!=null &&!object.isEmpty()){
            return object;
        }else{
            return null;
        }
    }
    public void deleteList(List<T> list){
        Session session = getSession();
        if(list == null){
            return;
        }
        for(T obj:list){
            session.delete(obj);
        }
    }
    //    public List<InstructionRequestDto> fullTextSearch(String search){
//        Session session = getSession();
//        Query query = session.createSQLQuery("SELECT * FROM project WHERE MATCH (name,content,purpose) AGAINST ('*"+search+"*' IN BOOLEAN MODE)");
//        List<InstructionRequestDto> object = query.list();
//        return object;
//    }
    public InstructionEntity findById(InstructionEntity t, Long id) {
        Session session =getSession();
        return (InstructionEntity)session.get(t.getClass(),id);
    }
    public List<InstructionEntity> fullTextSearch(String search, String tableName, String param, int position, List<InstructionEntity> projectEntities){
        Session session = getSession();
        Query query = session.createSQLQuery("SELECT * FROM "+ tableName+" WHERE MATCH ("+param+") AGAINST ('*"+search+"*' IN BOOLEAN MODE)");
        List<Object[]> object = query.list();
        if(object!=null) {

            for (Object[] objects : object) {
                String stringToConvert = String.valueOf(objects[position]);
                Long convertedLong = Long.parseLong(stringToConvert);
                InstructionEntity instructionEntity = (InstructionEntity) findById(new InstructionEntity(), convertedLong);
                if(!projectEntities.contains(instructionEntity))
                {
                    projectEntities.add(instructionEntity);
                }
            }
            return projectEntities;
        }
        return null;

    }
    public T findByTwoParametr(T parametr, String tableName,String column,String column2 , T parametr2) {
        Session session =getSession();
        String q="FROM "+tableName+" m WHERE m."+column+"=:"+column + " AND m." + column2+"=:"+column2;
        Query query=session.createQuery(q);
        query.setParameter(column,parametr);
        query.setParameter(column2,parametr2);
        List<Generic<T>> object=query.list();
        if(object!=null &&!object.isEmpty()){
            T t = (T) object.get(0);
            return t;
        }else{
            return null;
        }
    }

    public T findById(T t, Long id) {
        Session session =getSession();
        return (T)session.get(t.getClass(),id);
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getSession() {
        return this.sessionFactory.getCurrentSession();

    }

//    public List<InstructionEntity> fullTextSearch(String searchQuery) {
//        FullTextSession fullTextSession = Search.getFullTextSession(getSession());
//        try {
//            fullTextSession.createIndexer().startAndWait();
//        } catch (InterruptedException e) {
//            System.out.println("An error occurred trying to build the search index: " + e.toString());
//        }
//        if("".equals(searchQuery)) {
//            return Collections.emptyList();
//        }
//        FullTextSession fullTextEntityManager = Search.getFullTextSession(sessionFactory.getCurrentSession());
//        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
//                .forEntity(com.craut.project.craut.model.InstructionEntity.class).get();
//        javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(createQuery(queryBuilder, searchQuery), InstructionEntity.class);
//        jpaQuery.setFirstResult(0);
//        jpaQuery.setMaxResults(1000);
////        "unchecked"
//        List<InstructionEntity> result = jpaQuery.getResultList();
//        return result;
//    }
//    private org.apache.lucene.search.Query createQuery(QueryBuilder queryBuilder, String searchQuery) {
//        searchQuery = searchQuery.toLowerCase();
//        return queryBuilder.keyword().wildcard().onFields("name",
//                "purpose",
//                "content")
//                .matching(searchQuery + '*').createQuery();
//    }
}
