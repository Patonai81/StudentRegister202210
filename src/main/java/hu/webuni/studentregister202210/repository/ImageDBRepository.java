package hu.webuni.studentregister202210.repository;

import hu.webuni.studentregister202210.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ImageDBRepository extends JpaRepository<Image, Long> {}
