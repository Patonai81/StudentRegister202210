package hu.webuni.studentregister202210.repository;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import hu.webuni.studentregister202210.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;

import javax.persistence.EntityManager;
import java.util.List;


public class CourseCustomSearchRepositoryImpl extends SimpleJpaRepository<Course,Long> implements CourseCustomSearchRepository {

    private final EntityManager entityManager;
    private final EntityPath<Course> path;
    private final PathBuilder<Course> builder;
    private final Querydsl querydsl;

    @Autowired
    public CourseCustomSearchRepositoryImpl(EntityManager entityManager) {
        super(Course.class,entityManager);

        CrudMethodMetadata metadata = getRepositoryMethodMetadata();
        this.entityManager = entityManager;
        this.path = SimpleEntityPathResolver.INSTANCE.createPath(Course.class);
        this.builder = new PathBuilder<>(path.getType(), path.getMetadata());
        this.querydsl = new Querydsl(entityManager, builder);
    }

    @Override
    public Page<Course> findAll(Predicate predicate, Pageable pageable, String entityGraphName) {

        JPAQuery query = (JPAQuery) querydsl.applyPagination(pageable, createQuery(predicate));

        query.setHint(EntityGraph.EntityGraphType.LOAD.getKey(),
                entityManager.getEntityGraph(entityGraphName));

        List<Course> content = query.fetch();
        content.stream().forEach(item -> System.out.println(item));
       return new PageImpl<>(content, pageable, content.size());
      //  return content;
    }

    private JPAQuery createQuery(Predicate predicate) {
        return querydsl.createQuery(path).where(predicate);
    }


}
